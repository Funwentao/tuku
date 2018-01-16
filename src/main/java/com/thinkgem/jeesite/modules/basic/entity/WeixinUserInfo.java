/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basic.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 微信用户管理Entity
 * @version 2018-01-15
 */
public class WeixinUserInfo extends DataEntity<WeixinUserInfo> {
	
	private static final long serialVersionUID = 1L;
	private String openid;		// 用户标识
	private Integer userId;		// 用户ID
	private Boolean subscribe;		// 关注状态
	private Long subscribetime;		// 加入时间
	private String grade;		// 等级
	private String nickname;		// 名称
	private String sex;		// 性别
	private String country;		// 国家
	private String province;		// 省份
	private String city;		// 城市
	private String gossip;		// 禁言
	private String language;		// 用户的语言，简体中文为zh_CN
	private String headimgurl;		// 用户头像
	private String status;		// 状态

	private Date beginInDate; //开始时间
	private Date endInDate; //结束时间

	public Date getBeginInDate() {
		return beginInDate;
	}

	public void setBeginInDate(Date beginInDate) {
		this.beginInDate = beginInDate;
	}

	public Date getEndInDate() {
		return endInDate;
	}

	public void setEndInDate(Date endInDate) {
		this.endInDate = endInDate;
	}

	private String serviceContent;

	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	public WeixinUserInfo() {
		super();
	}

	public WeixinUserInfo(String id){
		super(id);
	}

	@Length(min=0, max=255, message="用户标识长度必须介于 0 和 255 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Boolean getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Boolean subscribe) {
		this.subscribe = subscribe;
	}

	public Long getSubscribetime() {
		return subscribetime;
	}

	public void setSubscribetime(Long subscribetime) {
		this.subscribetime = subscribetime;
	}
	//	@Length(min=0, max=11, message="关注状态长度必须介于 0 和 11 之间")
//	public String getSubscribe() {
//		return subscribe;
//	}
//
//	public void setSubscribe(String subscribe) {
//		this.subscribe = subscribe;
//	}
//
//	@Length(min=0, max=255, message="加入时间长度必须介于 0 和 255 之间")
//	public String getSubscribetime() {
//		return subscribetime;
//	}
//
//	public void setSubscribetime(String subscribetime) {
//		this.subscribetime = subscribetime;
//	}
	
//	@Length(min=0, max=255, message="等级长度必须介于 0 和 255 之间")
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	@Length(min=0, max=255, message="名称长度必须介于 0 和 255 之间")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Length(min=0, max=11, message="性别长度必须介于 0 和 11 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=255, message="国家长度必须介于 0 和 255 之间")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Length(min=0, max=255, message="省份长度必须介于 0 和 255 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=255, message="城市长度必须介于 0 和 255 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=11, message="禁言长度必须介于 0 和 11 之间")
	public String getGossip() {
		return gossip;
	}

	public void setGossip(String gossip) {
		this.gossip = gossip;
	}
	
	@Length(min=0, max=255, message="用户的语言，简体中文为zh_CN长度必须介于 0 和 255 之间")
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	@Length(min=0, max=255, message="用户头像长度必须介于 0 和 255 之间")
	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}