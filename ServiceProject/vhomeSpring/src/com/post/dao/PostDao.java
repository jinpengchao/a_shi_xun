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
		Query query=this.sessionFactory.getCurrentSession().createQuery("from Post");
		return query.list();
	}
	public List<Post> findPostByExamine1(){
		Query query=this.sessionFactory.getCurrentSession().createQuery("from Post");
		return query.list();
	}
	public List<Post> findPostByExamine2(){
		Query query=this.sessionFactory.getCurrentSession().createQuery("from Post");
		return query.list();
	}
	public void updatePostExamine(Post post) {
		this.sessionFactory.getCurrentSession().update(post);
	}
}
