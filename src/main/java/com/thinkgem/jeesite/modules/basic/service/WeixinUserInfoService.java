/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basic.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.basic.entity.WeixinUserInfo;
import com.thinkgem.jeesite.modules.basic.dao.WeixinUserInfoDao;

/**
 * 微信用户管理Service
 * @version 2018-01-15
 */
@Service
@Transactional(readOnly = true)
public class WeixinUserInfoService extends CrudService<WeixinUserInfoDao, WeixinUserInfo> {

	public WeixinUserInfo get(String id) {
		return super.get(id);
	}
	
	public List<WeixinUserInfo> findList(WeixinUserInfo weixinUserInfo) {
		return super.findList(weixinUserInfo);
	}
	
	public Page<WeixinUserInfo> findPage(Page<WeixinUserInfo> page, WeixinUserInfo weixinUserInfo) {
		return super.findPage(page, weixinUserInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(WeixinUserInfo weixinUserInfo) {
		super.save(weixinUserInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeixinUserInfo weixinUserInfo) {
		super.delete(weixinUserInfo);
	}

	@Transactional(readOnly = false)
	public String getGradeByUserId(String openId) {
		return dao.getGradeByUserId(openId);
	}

	@Transactional(readOnly = false)
	public String getNameByOpenId(String openId) {
		return dao.getNameByOpenId(openId);
	}

	//根据openId查询用户头像
	@Transactional(readOnly = false)
	public String getHeadImgUrlByOpenId(String openId){
		return dao.getHeadImgUrlByOpenId(openId);
	}

	//根据用户的openid查询用户详细信息
	@Transactional(readOnly = false)
	public WeixinUserInfo getUserInfoByOpenId(String openId){
		return dao.getUserInfoByOpenId(openId);
	}


	@Transactional(readOnly = false)
	public Integer getNewUserDay(){
		return dao.getNewUserDay();
	}

	public Integer getNewUserMonth(){
		return dao.getNewUserMonth();
	}
	public Integer getNewUserAll(){
		return dao.getNewUserAll();
	}

	public Integer getUserHZGradeDay(){
		return dao.getUserHZGradeDay();
	}
	public Integer getUserZSGradeDay(){
		return dao.getUserZSGradeDay();
	}
	public Integer getUserBJGradeDay(){
		return dao.getUserBJGradeDay();
	}
	public Integer getUserZZGradeDay(){
		return dao.getUserZZGradeDay();
	}

	public Integer getUserHZGradeMonth(){
		return dao.getUserHZGradeMonth();
	}
	public Integer getUserZSGradeMonth(){
		return dao.getUserZSGradeMonth();
	}
	public Integer getUserBJGradeMonth(){
		return dao.getUserBJGradeMonth();
	}
	public Integer getUserZZGradeMonth(){
		return dao.getUserZZGradeMonth();
	}

	public Integer getUserHZGradeAll(){
		return dao.getUserHZGradeAll();
	}
	public Integer getUserZSGradeAll(){
		return dao.getUserZSGradeAll();
	}
	public Integer getUserBJGradeAll(){
		return dao.getUserBJGradeAll();
	}
	public Integer getUserZZGradeAll(){
		return dao.getUserZZGradeAll();
	}





}