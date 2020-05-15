package community.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import community.service.AttentionService;
import community.service.CollectionService;
import community.service.CommentService;
import community.service.GoodPostService;
import community.service.PostService;
import entity.AttentionBean;
import entity.CollectionBean;
import entity.GoodPostBean;
import entity.PostBean;
import entity.PostExamineBean;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ShowExamine
 */
@WebServlet("/ShowExamine")
public class ShowExamine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowExamine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/text;charset=utf-8");
		PrintWriter out = response.getWriter();
		List<PostExamineBean> postBeans1=(new PostService()).findBeansByExamine("待审核");
		List<PostExamineBean> postBeans2=(new PostService()).findBeansByExamine("已审核");
		List<PostExamineBean> postBeans3=(new PostService()).findBeansByExamine("审核失败");
		out.print(postBeans2.get(0).getExamineString());
		request.setAttribute("examine", postBeans1);
		request.setAttribute("examine1", postBeans2);
		request.setAttribute("examine2", postBeans3);
		
		//接收android端发送的数据personId,查找对应的帖子
//		String personId=request.getParameter("personId");
		String personId="491602";
		List<PostExamineBean> list=(new PostService()).findAll(personId);
		
		//		获取当前人的所有收藏表
		List<CollectionBean> collections = (new CollectionService()).findCollection(personId);
		//获取当前人的所有点赞表
		List<GoodPostBean> goodPosts = (new GoodPostService()).findGoodPost(personId);
		//获取当前人的所有关注
		List<AttentionBean> attentions = (new AttentionService()).findAttention(personId);
		
		for(int i=0;i<list.size();i++) {
			PostExamineBean post = list.get(i);
			int likeNum = (new GoodPostService()).findGoodPostCount(post.getId());
			post.setLikeNum(likeNum);
			int commentNum = (new CommentService()).findCommentCount(post.getId());
			post.setCommentNum(commentNum);
			
			for(int j=0;j<collections.size();j++) {
				CollectionBean collection = collections.get(j);
				if(collection.getPostId() == post.getId()) {
					post.setSave_status(1);
					break;
				}
			}
			for(int k=0;k<goodPosts.size();k++) {
				GoodPostBean goodPost = goodPosts.get(k);
				if(goodPost.getPostId() == post.getId()) {
					post.setLike_status(1);
					break;
				}
			}
			for(int m=0;m<attentions.size();m++) {
				AttentionBean att = attentions.get(m);
				if(att.getAttentionPersonId().equals(post.getPersonId())) {
					post.setAttention_status(1);
					break;
				}
			}
		}
		
		//发送对应personId所有待审核、已审核、审核失败帖子数据
		JSONObject jsonobject=new JSONObject();
		jsonobject.put("examineString", list);
		String jdata=jsonobject.toString();
		out.write(jdata);
		out.flush();
		out.close();
		System.out.println(jsonobject.toString());
		
		//进行帖子的审核
//		request.getRequestDispatcher("/list.jsp").forward(request,response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
