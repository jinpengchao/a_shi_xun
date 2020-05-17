package community.dao;

import java.util.List;

import entity.PostExamineBean;

public class Test {

	public static void main(String[] args) {
		System.out.println("cnmd");
		PostExamineDao examineDao=new PostExamineDao();
		List<PostExamineBean> postExamineBeans=examineDao.findBeansByExamine("待审核");
		for(PostExamineBean postExamineBean:postExamineBeans) {
			System.out.println(postExamineBean.getExamine());
		}
	}

}
