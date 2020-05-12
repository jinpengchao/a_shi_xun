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
import com.entity.AttentionBean;
import com.entity.ParentUserInfo;
import com.google.gson.Gson;
import com.user.service.UserService;

/**
 * Servlet implementation class GetMyFunsServlet
 */
@WebServlet("/GetMyFunsServlet")
public class GetMyFunsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMyFunsServlet() {
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
		List<ParentUserInfo> userList = new ArrayList<ParentUserInfo>();
		List<AttentionBean> attentions = (new AttentionService()).findFuns(personId);
		System.out.println("getMyFunsServlet中获得"+attentions.size()+"条数据");
		for(int i=0;i<attentions.size();i++) {
			ParentUserInfo info = new ParentUserInfo();
			info = (new UserService()).selectUserInfo(attentions.get(i).getPersonId());
			userList.add(info);
		}
		data = gson.toJson(userList);
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
