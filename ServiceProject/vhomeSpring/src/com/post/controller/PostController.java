package com.post.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Post;
import com.post.service.PostService;

@Controller
@RequestMapping("/post")
public class PostController {	
	
	@Resource
	private PostService postService;
	
	@RequestMapping(value="/list")
	public String list(Model model) {
		List<Post> posts=this.postService.findPostByExamine();//展示待审核的帖子
		model.addAttribute("post",posts);
		return "list";
	}
	@RequestMapping(value="/list1")
	public String list1(Model model) {
		List<Post> posts=this.postService.findPostByExamine1();//展示已审核的帖子
		model.addAttribute("post1",posts);
		return "list1";
	}
	@RequestMapping(value="/list2")
	public String list2(Model model) {
		List<Post> posts=this.postService.findPostByExamine2();//展示审核失败的帖子
		model.addAttribute("post2",posts);
		return "list2";
	}
	@RequestMapping("/update")
	public String updateExamine(Post post,@RequestParam(value="examine")String examine) {
		post.setExamine(examine);
		postService.updatePost(post);
		return "list";
	}
}
