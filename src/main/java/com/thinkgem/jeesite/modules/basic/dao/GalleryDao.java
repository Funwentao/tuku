/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basic.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.basic.entity.Comments;
import com.thinkgem.jeesite.modules.basic.entity.Gallery;
import com.thinkgem.jeesite.modules.basic.entity.GalleryCategory;
import com.thinkgem.jeesite.modules.cms.entity.Article;

import java.util.List;


/**
 * 图库管理DAO接口
 * @version 2018-01-11
 */
@MyBatisDao
public interface GalleryDao extends CrudDao<Gallery> {

    //计算评论总数
    public Integer getCommentsNum(String id);


    //浏览量加1
    public int updateHitsAddOne(String id);

    //通过图集id返回图集内容
    public Gallery getGalleryById(String id);

    //通过图集id返图集的评论

    public List<Comments> getCommentById(String id);

    //返回分类列表

    public List<GalleryCategory> getCategoryList();

    //通过分类名返回图集列表
    public List<Gallery> getGalleryListByCategoryId(String galleryCategoryId);

    //通过galleryID汇总点赞数量
    public Integer getLikesByGalleryId(String galleryId);

    //计算收藏总数
    public Integer getCollectionsNum(String id);

    public Integer getDayHits();
    public Integer getMonthHits();
    public Integer getAllHits();


}