package com.thinkgem.jeesite.modules.basic.web.front;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.basic.entity.*;
import com.thinkgem.jeesite.modules.basic.entity.Collections;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ServicesService servicesService;
    /**
     * 网站首页
     */
    @RequestMapping(value = "index")
    public String index() {
        return "modules/basic/index";
    }

    /**
     * 删除收藏
     */
    @RequestMapping("deleteCollections")
    public void deleteCollections(HttpSession session,String openId,String galleryId,HttpServletRequest request, HttpServletResponse response) {
        Collections collections = new Collections();
        collections.setUserId(openId);
        collections.setGalleryId(galleryId);
//        Collections collections1 = collectionsService.selectCollectionsByOpenidAndGalleryId(collections);
        collections.setDelFlag("1");
        collectionsService.updateCollectionsByOpenidAndGalleryId(collections);
    }


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

    @RequestMapping("createTest")
    public ModelAndView pays() {
        return new ModelAndView("modules/basic/create");
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
    public HashMap<String, String> getUserInfoByOpenId(HttpSession session,HttpServletRequest request, HttpServletResponse response) {
        HashMap<String,String> jsonMap = new HashMap<String, String>();
        WeixinUserInfo wxUser = (WeixinUserInfo)session.getAttribute("wxUser");
        String openId = wxUser.getOpenid();
        WeixinUserInfo weixinUserInfo = weixinUserInfoService.getUserInfoByOpenId(openId);

        jsonMap.put("img",weixinUserInfo.getHeadimgurl());
        jsonMap.put("nickName",weixinUserInfo.getNickname());
        jsonMap.put("id",openId);

        if(weixinUserInfo.getGrade().equals("普通会员")){
            jsonMap.put("grade","你尚未开通会员");
        }else{
            Orders orders = new Orders();
            orders.setUserId(openId);
            //需要根据openId查询状态为1，然后根据update_date排序，返回第一个
            List<Orders> ordersList = ordersService.findList(orders);

            java.util.Collections.sort(ordersList, new Comparator<Orders>() {
                    @Override
                    public int compare(Orders o1, Orders o2) {
                        try {
                            Date dt1 = o1.getUpdateDate();
                            Date dt2 = o1.getUpdateDate();
                            if (dt1.getTime() > dt2.getTime()) {
                                return 1;
                            } else if (dt1.getTime() < dt2.getTime()) {
                                return -1;
                            } else {
                                return 0;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                }
            );
            Orders o = ordersList.get(0);


            Services s = servicesService.get(o.getServiceId());
            int temp = Integer.valueOf(s.getServiceTime());

            if(temp==0){
                jsonMap.put("grade",o.getServiceContent());
            }else{
                Long time1 = o.getUpdateDate().getTime();
                Long time2 = new Date().getTime();
                if(time1 + temp*30*24*60*60 - time2 <= 0){
                    jsonMap.put("grade","你尚未开通会员");
                    weixinUserInfo.setGrade("普通会员");
                    weixinUserInfoService.save(weixinUserInfo);
                }else{
                    if(time1 + temp*30*24*60*60 - time2 < 10*24*60*60){
                        int dateNum = (int)(time1 + temp*30*24*60*60 - time2)/(24*60*60);
                        jsonMap.put("grade",o.getServiceContent()+",还有"+dateNum+"过期");
                    }else{
                        jsonMap.put("grade",o.getServiceContent());
                    }
                }

            }

        }

        return jsonMap;

    }


    /**
     * 获取服务列表
     * @param services
     * @return
     */

    @ResponseBody
    @RequestMapping("getServicesList")
    public List<Services> getServiceList(Services services){
        List<Services> servicesList =  servicesService.findList(services);
        return servicesList;
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

        String url = "http://p.handanyida.top/gg/userInfo";
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
                    return "redirect:http://p.handanyida.top/gg/index" ;
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
        return "redirect:http://p.handanyida.top/gg/index";
    }

}
