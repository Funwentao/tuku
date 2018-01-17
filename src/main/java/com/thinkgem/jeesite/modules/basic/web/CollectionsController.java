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
import com.thinkgem.jeesite.modules.basic.entity.Collections;
import com.thinkgem.jeesite.modules.basic.service.CollectionsService;

/**
 * 收藏Controller
 * @author fandaz
 * @version 2018-01-17
 */
@Controller
@RequestMapping(value = "${adminPath}/basic/collections")
public class CollectionsController extends BaseController {

	@Autowired
	private CollectionsService collectionsService;
	
	@ModelAttribute
	public Collections get(@RequestParam(required=false) String id) {
		Collections entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = collectionsService.get(id);
		}
		if (entity == null){
			entity = new Collections();
		}
		return entity;
	}
	
	@RequiresPermissions("basic:collections:view")
	@RequestMapping(value = {"list", ""})
	public String list(Collections collections, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Collections> page = collectionsService.findPage(new Page<Collections>(request, response), collections); 
		model.addAttribute("page", page);
		return "modules/basic/collectionsList";
	}

	@RequiresPermissions("basic:collections:view")
	@RequestMapping(value = "form")
	public String form(Collections collections, Model model) {
		model.addAttribute("collections", collections);
		return "modules/basic/collectionsForm";
	}

	@RequiresPermissions("basic:collections:edit")
	@RequestMapping(value = "save")
	public String save(Collections collections, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, collections)){
			return form(collections, model);
		}
		collectionsService.save(collections);
		addMessage(redirectAttributes, "保存收藏成功");
		return "redirect:"+Global.getAdminPath()+"/basic/collections/?repage";
	}
	
	@RequiresPermissions("basic:collections:edit")
	@RequestMapping(value = "delete")
	public String delete(Collections collections, RedirectAttributes redirectAttributes) {
		collectionsService.delete(collections);
		addMessage(redirectAttributes, "删除收藏成功");
		return "redirect:"+Global.getAdminPath()+"/basic/collections/?repage";
	}

}