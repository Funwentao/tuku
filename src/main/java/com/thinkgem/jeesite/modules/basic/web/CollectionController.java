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
import com.thinkgem.jeesite.modules.basic.entity.Collection;
import com.thinkgem.jeesite.modules.basic.service.CollectionService;

/**
 * 收藏Controller
 * @author fandaz
 * @version 2018-01-17
 */
@Controller
@RequestMapping(value = "${adminPath}/basic/collection")
public class CollectionController extends BaseController {

	@Autowired
	private CollectionService collectionService;
	
	@ModelAttribute
	public Collection get(@RequestParam(required=false) String id) {
		Collection entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = collectionService.get(id);
		}
		if (entity == null){
			entity = new Collection();
		}
		return entity;
	}
	
	@RequiresPermissions("basic:collection:view")
	@RequestMapping(value = {"list", ""})
	public String list(Collection collection, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Collection> page = collectionService.findPage(new Page<Collection>(request, response), collection); 
		model.addAttribute("page", page);
		return "modules/basic/collectionList";
	}

	@RequiresPermissions("basic:collection:view")
	@RequestMapping(value = "form")
	public String form(Collection collection, Model model) {
		model.addAttribute("collection", collection);
		return "modules/basic/collectionForm";
	}

	@RequiresPermissions("basic:collection:edit")
	@RequestMapping(value = "save")
	public String save(Collection collection, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, collection)){
			return form(collection, model);
		}
		collectionService.save(collection);
		addMessage(redirectAttributes, "保存收藏成功");
		return "redirect:"+Global.getAdminPath()+"/basic/collection/?repage";
	}
	
	@RequiresPermissions("basic:collection:edit")
	@RequestMapping(value = "delete")
	public String delete(Collection collection, RedirectAttributes redirectAttributes) {
		collectionService.delete(collection);
		addMessage(redirectAttributes, "删除收藏成功");
		return "redirect:"+Global.getAdminPath()+"/basic/collection/?repage";
	}

}