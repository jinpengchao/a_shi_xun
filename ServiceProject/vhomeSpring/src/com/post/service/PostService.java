package com.post.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Post;
import com.post.dao.PostDao;


@Service
@Transactional(readOnly =true)
public class PostService {
	
	@Resource
	private PostDao postDao;
	
	@Transactional(readOnly =true)
	public List<Post> findPostByExamine(){
		return this.postDao.findPostByExamine();
	}
	@Transactional(readOnly =true)
	public List<Post> findPostByExamine1(){
		return this.postDao.findPostByExamine();
	}
	@Transactional(readOnly =true)
	public List<Post> findPostByExamine2(){
		return this.postDao.findPostByExamine2();
	}
	@Transactional(readOnly =false)
	public void updatePost(Post post) {
		this.postDao.updatePostExamine(post);
	}
}
