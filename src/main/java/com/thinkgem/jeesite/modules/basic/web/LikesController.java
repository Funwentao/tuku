/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basic.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.modules.basic.entity.Likes;
import com.thinkgem.jeesite.modules.basic.service.LikesService;

/**
 * 点赞Controller
 * @author fandaz
 * @version 2018-01-17
 */
@Controller
@RequestMapping(value = "${adminPath}/basic/likes")
public class LikesController extends BaseController {

	@Autowired
	private LikesService likesService;
	
	@ModelAttribute
	public Likes get(@RequestParam(required=false) String id) {
		Likes entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = likesService.get(id);
		}
		if (entity == null){
			entity = new Likes();
		}
		return entity;
	}
	
	@RequiresPermissions("basic:likes:view")
	@RequestMapping(value = {"list", ""})
	public String list(Likes likes, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Likes> page = likesService.findPage(new Page<Likes>(request, response), likes); 
		model.addAttribute("page", page);
		return "modules/basic/likesList";
	}

	@RequiresPermissions("basic:likes:view")
	@RequestMapping(value = "form")
	public String form(Likes likes, Model model) {
		model.addAttribute("likes", likes);
		return "modules/basic/likesForm";
	}

	@RequiresPermissions("basic:likes:edit")
	@RequestMapping(value = "save")
	public String save(Likes likes, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, likes)){
			return form(likes, model);
		}
		likesService.save(likes);
		addMessage(redirectAttributes, "保存点赞成功");
		return "redirect:"+Global.getAdminPath()+"/basic/likes/?repage";
	}
	
	@RequiresPermissions("basic:likes:edit")
	@RequestMapping(value = "delete")
	public String delete(Likes likes, RedirectAttributes redirectAttributes) {
		likesService.delete(likes);
		addMessage(redirectAttributes, "删除点赞成功");
		return "redirect:"+Global.getAdminPath()+"/basic/likes/?repage";
	}

}