package entity;

import java.util.List;

import community.dao.PostDao;

public class Test {

	public static void main(String[] args) {
		PostDao postDao=new PostDao();
		PostBean postBean=new PostBean();
		postBean.setNickName("章鹏");
		postBean.setHeadimg("姬文斌大傻逼");
		postBean.setPostContent("纪文斌真几把的傻逼");
		postBean.setPersonId("3120100");
		postBean.setTime("2020-5-14 11:01");
		postBean.setImgs("image_address");
		postBean.setExamineString("待审核");
		postDao.insertPost(postBean);
//		List<PostBean>postBeans=postDao.queryPosts();
//		for(PostBean postBean:postBeans) {
//			System.out.println(postBean.getExamineString());
//		}
	}

}
