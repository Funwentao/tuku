/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basic.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.basic.entity.Comments;
import com.thinkgem.jeesite.modules.basic.entity.Gallery;

import java.util.List;

/**
 * 评论表DAO接口
 * @version 2018-01-10
 */
@MyBatisDao
public interface CommentsDao extends CrudDao<Comments> {
//    public int insert(Comments c);

    //通过图集名称和用户的openID返回评论列表
    public List<Comments> getCommentsByCategoryId(String galleryId);
}