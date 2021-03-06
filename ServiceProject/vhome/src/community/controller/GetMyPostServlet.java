package community.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class GetMyPostServlet
 */
@WebServlet("/GetMyPostServlet")
public class GetMyPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMyPostServlet() {
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
		Gson gson = new Gson();
		String data = null;
		String personId = request.getParameter("personId");
		//获取当前人的帖子
		List<PostBean> list = (new PostService()).findPost(personId);
//		获取当前人的所有收藏表
		List<CollectionBean> collections = (new CollectionService()).findCollection(personId);
		//获取当前人的所有点赞表
		List<GoodPostBean> goodPosts = (new GoodPostService()).findGoodPost(personId);
		//获取当前人的所有关注
		List<AttentionBean> attentions = (new AttentionService()).findAttention(personId);
		System.out.println("getMypostservlet中获取个人帖子数"+list.size());
		for(int i=0;i<list.size();i++) {
			PostBean post = list.get(i);
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
				AttentionBean attention = attentions.get(m);
				if(attention.getPersonId().equals(post.getPersonId())) {
					post.setAttention_status(1);
					break;
				}
			}
		}
		data = gson.toJson(list);
		out.write(data);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
