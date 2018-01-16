package com.thinkgem.jeesite.modules.basic.web.front;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.basic.entity.Comments;
import com.thinkgem.jeesite.modules.basic.entity.Gallery;
import com.thinkgem.jeesite.modules.basic.entity.WeixinUserInfo;
import com.thinkgem.jeesite.modules.basic.service.GalleryService;
import com.thinkgem.jeesite.modules.basic.service.WeixinUserInfoService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fandaz on 2018/1/15
 */
@Controller
@RequestMapping(value = "${frontPath}")
public class GallerysFrontController extends BaseController {

    @Autowired
    private WeixinUserInfoService weixinUserInfoService;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private WxMpService wxMpService;

    @ResponseBody
    @RequestMapping(value = "getIndexList")
    public List<Gallery> getIndexList(Gallery gallery, HttpServletRequest request, HttpServletResponse response) {
        List<Gallery> galleryList = galleryService.findList(gallery);
        for (int i = 0; i < galleryList.size(); i++) {
            String id = galleryList.get(i).getId();
            Integer commentsNum = galleryService.getCommentsNum(id);
            String commentsNumString = Integer.toString(commentsNum);
            galleryList.get(i).setCommentId(commentsNumString);
        }
        return galleryList;
    }

    @ResponseBody
    @RequestMapping(value = "getGalleryById")
    public HashMap<String, Object> getGalleryById(String id, String openId, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> jsonMap = new HashMap<String, Object>();
        Gallery g = galleryService.getGalleryById(id);
        jsonMap.put("cover_gallery", g.getCoverGallery());
        jsonMap.put("imgs", g.getImgs());
        jsonMap.put("title", g.getTitle());
        jsonMap.put("create_date", g.getCreateDate());
        jsonMap.put("category", g.getGalleryCategory());

        List<Comments> commentsList = galleryService.getCommentById(id);

        String grade = weixinUserInfoService.getGradeByUserId(openId);

        jsonMap.put("grade", grade);

        jsonMap.put("comments", commentsList);

        return jsonMap;
    }

    /**
     * 微信授权登陆
     *
     * @param returnUrl
     * @return
     */
    @RequestMapping(value = "authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {

        String url = "http://fadaz.natapp1.cc/f/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        return "redirect:" + redirectUrl;

    }

    @ResponseBody
    @RequestMapping(value = "userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        WxMpUser wxMpUser = new WxMpUser();

        try {

            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);



            // TODO 与数据库进行对比，如果openid不存在，就插入，如果存在，则不插入
            WeixinUserInfo weixinUserInfos = new WeixinUserInfo();
            List<WeixinUserInfo> weixinUserInfoList = weixinUserInfoService.findList(weixinUserInfos);
            for (int i = 0; i < weixinUserInfoList.size(); i++) {
                String openid = weixinUserInfoList.get(i).getOpenid();
                if (wxMpUser.getOpenId().equals(openid)) {
                    return "false";
                }

            }
            WeixinUserInfo weixinUserInfo = new WeixinUserInfo();
            weixinUserInfo.setOpenid(wxMpUser.getOpenId());
            weixinUserInfo.setCity(wxMpUser.getCity());
            weixinUserInfo.setCountry(wxMpUser.getCountry());
            weixinUserInfo.setHeadimgurl(wxMpUser.getHeadImgUrl());
            weixinUserInfo.setSex(wxMpUser.getSex());
            weixinUserInfo.setLanguage(wxMpUser.getLanguage());
            weixinUserInfo.setNickname(wxMpUser.getNickname());
            weixinUserInfo.setProvince(wxMpUser.getProvince());
            weixinUserInfo.setSubscribe(wxMpUser.getSubscribe());
            weixinUserInfo.setSubscribetime(wxMpUser.getSubscribeTime());

            weixinUserInfoService.save(weixinUserInfo);


        } catch (WxErrorException e) {
            logger.error("【微信网页授权】{}", e);
        }

        String openId = wxMpOAuth2AccessToken.getOpenId();
        return"redirect:"+returnUrl+"?openid"+openId;
    }

}
