package com.community.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.community.service.AttentionService;
import com.community.service.CommentService;
import com.community.service.GoodPostService;
import com.community.service.PostService;
import com.community.service.ReplyService;
import com.entity.AttentionBean;
import com.entity.CommentDetailBean;
import com.entity.PostBean;
import com.entity.ReplyDetailBean;
import com.google.gson.Gson;
import com.user.service.UserService;


/**
 * Servlet implementation class GetMessagesServlet
 */
@WebServlet("/GetMessagesServlet")
public class GetMessagesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMessagesServlet() {
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
		List<String> list = new ArrayList<String>();
		String data = null;
		List<String> personList = null;
		String info = null;
		Gson gson = new Gson();
		String personId = request.getParameter("personId");
		//获得的赞
		personList = (new GoodPostService()).findGoodPersonId(personId);
		for(int i=0;i<personList.size();i++) {
			String name = ((new UserService()).selectUserInfo(personList.get(i))).getNikeName();
			info = name+" 赞了你的帖子";
			list.add(info);
		}
		//获得的关注
		List<AttentionBean> attList = (new AttentionService()).findFuns(personId);
		for(int i=0;i<attList.size();i++) {
			String name = (new UserService()).selectUserInfo(attList.get(i).getPersonId()).getNikeName();
			info = name+" 关注了你";
			list.add(info);
		}
		//获得的评论回复
		List<PostBean> postList = (new PostService()).findPost(personId);
		for(int i=0;i<postList.size();i++) {
			List<CommentDetailBean> comList = (new CommentService()).findComment(postList.get(i).getId());
			for(int j=0;j<comList.size();j++) {
				info = comList.get(j).getNickName()+" 评论了你："+comList.get(j).getContent();
				list.add(info);
			}
		}
		//回复
		List<Integer> comList = (new CommentService()).findComment(personId);
		for(int i=0;i<comList.size();i++) {
			List<ReplyDetailBean> replyList = (new ReplyService()).findReply(comList.get(i));
			for(int j=0;j<replyList.size();j++) {
				info = replyList.get(j).getNickName()+" 回复了你的评论："+replyList.get(j).getContent();
				list.add(info);
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
