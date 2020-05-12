package com.community.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.community.service.CommentService;
import com.community.service.ReplyService;
import com.entity.CommentDetailBean;
import com.entity.ReplyDetailBean;
import com.google.gson.Gson;


/**
 * Servlet implementation class GetCommentsServlet
 */
@WebServlet("/GetCommentsServlet")
public class GetCommentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCommentsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		List<CommentDetailBean> commentList = null;
		List<ReplyDetailBean> replyList = null;
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		//先查询评论数据
		int postId = Integer.parseInt(request.getParameter("postId"));
		commentList = (new CommentService()).findComment(postId);
		if(commentList.size()>0) {
			for(int i=0;i<commentList.size();i++) {
				replyList = (new ReplyService()).findReply(commentList.get(i).getId());
				commentList.get(i).setReplyList(replyList);
			}
		}else {
			System.out.println("没有评论数据");
		}
		String data = gson.toJson(commentList);
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
