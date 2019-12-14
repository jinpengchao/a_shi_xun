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

/**
 * Servlet implementation class GetCollectionsServlet
 */
@WebServlet("/GetCollectionsPostServlet")
public class GetCollectionsPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetCollectionsPostServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String personId = null;
		personId = request.getParameter("personId");
		System.out.println("GetCollectionServlet8personId " + personId);
		List<PostBean> postList = new ArrayList<PostBean>();
		//获取当前收藏信息
		List<CollectionBean> collections = (new CollectionService()).findCollection(personId);
		for(int i=0;i<collections.size();i++) {
			CollectionBean collectionBean = collections.get(i);
			PostBean post = (new PostService()).findPost(collectionBean.getPostId());
			postList.add(post);
		}
		//获取当前人的所有点赞表
		List<GoodPostBean> goodPosts = (new GoodPostService()).findGoodPost(personId);
		//获取当前人的所有关注
		List<AttentionBean> attentions = (new AttentionService()).findAttention(personId);
		for(int i=0;i<postList.size();i++) {
			PostBean post = postList.get(i);
			int likeNum = (new GoodPostService()).findGoodPostCount(post.getId());
			post.setLikeNum(likeNum);
			int commentNum = (new CommentService()).findCommentCount(post.getId());
			post.setCommentNum(commentNum);
			
			post.setSave_status(1);//所有的都是收藏的
			
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
		
		Gson gson = new Gson();
		String data = gson.toJson(postList);
		out.write(data);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
