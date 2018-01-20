/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basic.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.basic.entity.Services;
import com.thinkgem.jeesite.modules.basic.service.GalleryService;
import com.thinkgem.jeesite.modules.basic.service.OrdersService;
import com.thinkgem.jeesite.modules.basic.service.ServicesService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.basic.entity.WeixinUserInfo;
import com.thinkgem.jeesite.modules.basic.service.WeixinUserInfoService;

import java.net.URLEncoder;
import java.util.List;

/**
 * 微信用户管理Controller
 * @version 2018-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/basic/weixinUserInfo")
public class WeixinUserInfoController extends BaseController {

	@Autowired
	private WeixinUserInfoService weixinUserInfoService;

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private GalleryService galleryService;

	@Autowired
	private WxMpService wxMpService;

	@Autowired
	private ServicesService servicesService;
	
	@ModelAttribute
	public WeixinUserInfo get(@RequestParam(required=false) String id) {
		WeixinUserInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weixinUserInfoService.get(id);
		}
		if (entity == null){
			entity = new WeixinUserInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("basic:weixinUserInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(WeixinUserInfo weixinUserInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeixinUserInfo> page = weixinUserInfoService.findPage(new Page<WeixinUserInfo>(request, response), weixinUserInfo);
		List<Services> servicesList = servicesService.findList(new Services());
		model.addAttribute("servicesList",servicesList);
		model.addAttribute("page", page);
		return "modules/basic/weixinUserInfoList";
	}

	@RequiresPermissions("basic:weixinUserInfo:view")
	@RequestMapping(value = "form")
	public String form(WeixinUserInfo weixinUserInfo, Model model) {
		List<Services> servicesList = servicesService.findList(new Services());
		model.addAttribute("servicesList",servicesList);
		model.addAttribute("weixinUserInfo", weixinUserInfo);
		return "modules/basic/weixinUserInfoForm";
	}

	@RequiresPermissions("basic:weixinUserInfo:edit")
	@RequestMapping(value = "save")
	public String save(WeixinUserInfo weixinUserInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, weixinUserInfo)){
			return form(weixinUserInfo, model);
		}
		weixinUserInfoService.save(weixinUserInfo);
		addMessage(redirectAttributes, "保存微信用户成功");
		return "redirect:"+Global.getAdminPath()+"/basic/weixinUserInfo/?repage";
	}
	
	@RequiresPermissions("basic:weixinUserInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(WeixinUserInfo weixinUserInfo, RedirectAttributes redirectAttributes) {
		weixinUserInfoService.delete(weixinUserInfo);
		addMessage(redirectAttributes, "删除微信用户成功");
		return "redirect:"+Global.getAdminPath()+"/basic/weixinUserInfo/?repage";
	}


	/**
	 * 微信授权登陆
	 * @param returnUrl
	 * @return
	 */
	@RequiresPermissions("basic:weixinUserInfo:view")
	@RequestMapping(value = "authorize")
	public String authorize(@RequestParam("returnUrl")String returnUrl){

		String url = "http://fadaz.natapp1.cc/a/basic/weixinUserInfo/userInfo";
		String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(returnUrl));
		return "redirect:" + redirectUrl;

	}

	@RequestMapping(value = "userInfo")
	public String userInfo(@RequestParam("code") String code,
						 @RequestParam("state") String returnUrl){

		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();

		WxMpUser  wxMpUser= new WxMpUser();

		try {

			wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
			wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken,null);
		}catch (WxErrorException e){
			logger.error("【微信网页授权】{}",e);
		}

		String openId = wxMpOAuth2AccessToken.getOpenId();
		return "redirect:"+returnUrl+"?openid" + openId;

	}

}