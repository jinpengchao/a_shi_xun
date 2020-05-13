package com.post.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Post;

@Repository
public class PostDao {
	
	@Resource
	private SessionFactory sessionFactory;
	
	public List<Post> findPostByExamine(){
		Query query=this.sessionFactory.getCurrentSession().createQuery("select * from Post where examine=?").setParameter(0, "待审核");
		return query.list();
	}
	public List<Post> findPostByExamine1(){
		Query query=this.sessionFactory.getCurrentSession().createQuery("select * from Post where examine=?").setParameter(0, "已审核");
		return query.list();
	}
	public List<Post> findPostByExamine2(){
		Query query=this.sessionFactory.getCurrentSession().createQuery("select * from Post where examine=?").setParameter(0, "审核失败");
		return query.list();
	}
	public void updatePostExamine(Post post) {
		this.sessionFactory.getCurrentSession().update(post);
	}
}
