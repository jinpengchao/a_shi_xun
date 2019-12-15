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

import community.service.CollectionService;
import community.service.CommentService;
import community.service.PostService;
import community.service.ReplyService;
import entity.CollectionBean;
import entity.CommentDetailBean;

/**
 * Servlet implementation class RemoveMyPost
 */
@WebServlet("/RemoveMyPost")
public class RemoveMyPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveMyPost() {
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
		int postId = Integer.parseInt(request.getParameter("postId"));
		//根据id删除帖子
		//先查找评论回复，删除
		List<CommentDetailBean> commentList = (new CommentService()).findComment(postId);
		ReplyService rs = new ReplyService();
		CommentService cs = new CommentService();
		for(int i=0;i<commentList.size();i++) {
			rs.delCommentReply(commentList.get(i).getId());
			cs.delComment(commentList.get(i).getId());
		}
		//再删除收藏表含有的
		new CollectionService().delCollectionByPostId(postId);
		//再删除帖子
		int data = (new PostService()).delPost(postId);
		out.write(data+"");
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
