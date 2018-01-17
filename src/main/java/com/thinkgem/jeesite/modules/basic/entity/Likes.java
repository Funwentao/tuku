/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basic.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 点赞Entity
 * @author fandaz
 * @version 2018-01-17
 */
public class Likes extends DataEntity<Likes> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String galleryId;		// 图集ID
	private String likesStatus;		// 0表示未点赞，1表示点赞
	private String status;		// 状态
	
	public Likes() {
		super();
	}

	public Likes(String id){
		super(id);
	}

	@Length(min=1, max=255, message="用户ID长度必须介于 1 和 255 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=1, max=255, message="图集ID长度必须介于 1 和 255 之间")
	public String getGalleryId() {
		return galleryId;
	}

	public void setGalleryId(String galleryId) {
		this.galleryId = galleryId;
	}
	
	@Length(min=1, max=11, message="0表示未点赞，1表示点赞长度必须介于 1 和 11 之间")
	public String getLikesStatus() {
		return likesStatus;
	}

	public void setLikesStatus(String likesStatus) {
		this.likesStatus = likesStatus;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}