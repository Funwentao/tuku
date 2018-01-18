package com.thinkgem.jeesite.modules.basic.web.front;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.basic.entity.*;
import com.thinkgem.jeesite.modules.basic.service.*;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fandaz on 2018/1/15
 */
@Controller
@RequestMapping(value = "gg")
public class GallerysFrontController extends BaseController {

    @Autowired
    private WeixinUserInfoService weixinUserInfoService;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private LikesService likesService;

    @Autowired
    private CollectionsService collectionsService;

    /**
     * 首页功能实现
     * @param gallery
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getIndexList")
    public List<Gallery> getIndexList(Gallery gallery, HttpServletRequest request, HttpServletResponse response) {
        List<Gallery> galleryList = galleryService.findList(gallery);
        for (int i = 0; i < galleryList.size(); i++) {
            String id = galleryList.get(i).getId();
            Integer commentsNum = galleryService.getCommentsNum(id);
            String commentsNumString = Integer.toString(commentsNum);
            Integer likesNum = galleryService.getLikesByGalleryId(id);
            Integer collectionsNum = galleryService.getCollectionsNum(id);
            String collectionsNumString = Integer.toString(collectionsNum);
            galleryList.get(i).setLikes(likesNum);
            galleryList.get(i).setCommentId(commentsNumString);
            galleryList.get(i).setStatus(collectionsNumString);
        }
        return galleryList;
    }

    /**
     * 通过传入的galleryID查询图集详情
     * 浏览量功能实现
     * @param id
     * @param session
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getGalleryById")
    public HashMap<String, Object> getGalleryById(String id, HttpSession session,String openId, HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> jsonMap = new HashMap<String, Object>();
        Gallery g = galleryService.getGalleryById(id);
//        jsonMap.put("collections",g.getStatus());
//        jsonMap.put("cover_gallery", g.getCoverGallery());
//        jsonMap.put("imgs", g.getImgs());
//        jsonMap.put("title", g.getTitle());
//        jsonMap.put("create_date", g.getCreateDate());
//        jsonMap.put("category", g.getGalleryCategory());
        List<Gallery> galleryList = galleryService.findList(g);
        for (int i = 0; i < galleryList.size(); i++) {
            String gId = galleryList.get(i).getId();
            Integer commentsNum = galleryService.getCommentsNum(gId);
            String commentsNumString = Integer.toString(commentsNum);
            Integer likesNum = galleryService.getLikesByGalleryId(gId);
            Integer collectionsNum = galleryService.getCollectionsNum(gId);
            String collectionsNumString = Integer.toString(collectionsNum);
            galleryList.get(i).setLikes(likesNum);
            galleryList.get(i).setCommentId(commentsNumString);
            galleryList.get(i).setStatus(collectionsNumString);
        }

        List<Comments> commentsList = galleryService.getCommentById(id);
        for (int i = 0 ; i < commentsList.size(); i++){
            String openIds = commentsList.get(i).getUserId();
            String names = weixinUserInfoService.getNameByOpenId(openIds);
            String headImgUrl = weixinUserInfoService.getHeadImgUrlByOpenId(openIds);
            commentsList.get(i).setStatus(names);
            commentsList.get(i).setRemarks(headImgUrl);
        }
        WeixinUserInfo wxUser = (WeixinUserInfo)session.getAttribute("wxUser");
//        String openId = wxUser.getOpenid();
//        String grade = weixinUserInfoService.getGradeByUserId(openId);
        galleryService.updateHitsAddOne(id);
//        jsonMap.put("grade", grade);
        jsonMap.put("galleryList",galleryList);
        jsonMap.put("comments", commentsList);

        return jsonMap;
    }

    /**
     * 通过openid获取用户所有信息
     * @param session
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getUserInfoByOpenId")
    public WeixinUserInfo getUserInfoByOpenId(HttpSession session,HttpServletRequest request, HttpServletResponse response) {
        WeixinUserInfo wxUser = (WeixinUserInfo)session.getAttribute("wxUser");
        String openId = wxUser.getOpenid();
        WeixinUserInfo weixinUserInfo = weixinUserInfoService.getUserInfoByOpenId(openId);
        return weixinUserInfo;

    }

    /**
     *
     * 获取分类json数据
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getCategoryList")
    public List<GalleryCategory> getCategoryList(HttpServletRequest request, HttpServletResponse response) {
        List<GalleryCategory> categoryList = galleryService.getCategoryList();
        return categoryList;
    }

    /**
     * 通过分类id获取图集信息
     * @param CategoryId
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getGalleryListByCategoryId")
    public List<Gallery> getGalleryListByCategoryId(String CategoryId,HttpServletRequest request, HttpServletResponse response) {
        List<Gallery> galleryList = galleryService.getGalleryListByCategoryId(CategoryId);
        return galleryList;
    }

    /**
     * 插入评论功能的实现
     * @param session
     * @param commentsContent
     * @param galleryId
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "insertComment",method = RequestMethod.POST)
    public void insertComment(HttpSession session, String commentsContent,String galleryId,String openId,HttpServletRequest request, HttpServletResponse response){
        WeixinUserInfo wxUser = (WeixinUserInfo)session.getAttribute("wxUser");
        Comments c = new Comments();
//        String openId = wxUser.getOpenid();
        c.setGalleryId(galleryId);
        c.setUserId(openId);
        c.setCommentsContent(commentsContent);
        commentsService.save(c);

    }

    /**
     *
     * 点赞功能的实现
     * @param session
     * @param galleryId
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "selectLikesByOpenidAndGalleryId",method = RequestMethod.POST)
    public void selectLikesByOpenidAndGalleryId(HttpSession session,String galleryId,String openId,HttpServletRequest request, HttpServletResponse response){
        WeixinUserInfo wxUser = (WeixinUserInfo)session.getAttribute("wxUser");
//        String openId = wxUser.getOpenid();
        Likes l = new Likes();
        l.setUserId(openId);
        l.setGalleryId(galleryId);
        Likes tempLike = likesService.selectLikesByOpenidAndGalleryId(l);
        if(tempLike == null){
            likesService.save(l);
        }else{
            if(tempLike.getDelFlag().equals("1")){
                l.setDelFlag("0");
                likesService.updateLikesByOpenidAndGalleryId(l);
            }else{
                l.setDelFlag("1");
                likesService.updateLikesByOpenidAndGalleryId(l);
            }
        }

    }

    /**
     * TODO 插入功能已实现 当数据存在时，无法将del修改为 “0” 或者 “1”提示转换类型错误 (错误已修复)
     * 收藏功能的实现
     * @param session
     * @param galleryId
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "selectCollectionsByOpenidAndGalleryId",method = RequestMethod.POST)
    public void selectCollectionsByOpenidAndGalleryId(HttpSession session,String galleryId,String openId,HttpServletRequest request, HttpServletResponse response){
        WeixinUserInfo wxUser = (WeixinUserInfo)session.getAttribute("wxUser");
//        String openId = wxUser.getOpenid();
        Collections c = new Collections();
        c.setUserId(openId);
        c.setGalleryId(galleryId);
        Collections tempCollection = collectionsService.selectCollectionsByOpenidAndGalleryId(c);
        if(tempCollection == null){
            collectionsService.save(c);
        }else{
            if(tempCollection.getDelFlag().equals("1")){
                c.setDelFlag("0");
                collectionsService.updateCollectionsByOpenidAndGalleryId(c);
            }else{
                c.setDelFlag("1");
                collectionsService.updateCollectionsByOpenidAndGalleryId(c);
            }
        }
    }

    /**
     * 获取用户的收藏表
     * @param session
     * @param request
     * @param response
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "selectCollectionsByGalleryid")
    public ArrayList<HashMap>  selectCollectionsByGalleryid(HttpSession session,String openId,HttpServletRequest request, HttpServletResponse response) {
        WeixinUserInfo wxUser = (WeixinUserInfo) session.getAttribute("wxUser");
//        String openId = wxUser.getOpenid();
        List<Collections> collectionList = collectionsService.selectCollectionsByOpenid(openId);
        ArrayList<HashMap> jsonList = new ArrayList<HashMap>();
        for (int i = 0; i < collectionList.size(); i++) {
            HashMap<String,Object> item = new HashMap<String, Object>();
            item.put("collection",collectionList.get(i));
            String galleryId = collectionList.get(i).getGalleryId();
            item.put("gallery",collectionsService.selectgalleryBygalleryId(galleryId));
            jsonList.add(item);
        }
        return  jsonList;
    }


    /**
     * 微信授权登陆
     *
     * @param returnUrl
     * @return
     */
    @RequestMapping(value = "authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {

        String url = "http://fadaz.natapp1.cc/gg/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        return "redirect:" + redirectUrl;
    }

    /**
     * 微信授权模块，把用户数据存进数据库，并把openid存到seesion中
     * @param code
     * @param returnUrl
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl, HttpSession session) {
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
        session.setAttribute("openId",openId);
        //将登陆用户信息存入session
        WeixinUserInfo wxUser = new WeixinUserInfo();
        wxUser.setOpenid(openId);
        session.setAttribute("wxUser",wxUser);
        return"redirect:"+returnUrl+"?openid"+openId;
    }

}
