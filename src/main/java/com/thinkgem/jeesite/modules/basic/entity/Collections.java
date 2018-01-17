/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basic.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 收藏Entity
 * @author fandaz
 * @version 2018-01-17
 */
public class Collections extends DataEntity<Collections> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String galleryId;		// 图集ID
	private String status;		// 状态
	
	public Collections() {
		super();
	}

	public Collections(String id){
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}