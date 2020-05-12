package com.community.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.community.service.CommentService;
import com.entity.CommentDetailBean;
import com.google.gson.Gson;


/**
 * Servlet implementation class SaveCommentServlet
 */
@WebServlet("/SaveCommentServlet")
public class SaveCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		InputStream is = request.getInputStream();
		PrintWriter out = response.getWriter();
		CommentDetailBean comment = null;
		Gson gson = new Gson();
		String dataIn = null;
		String dataOut = null;
		int i = 0;//保存返回数据
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
		dataIn = br.readLine();
		br.close();
		is.close();
		
		comment = gson.fromJson(dataIn, CommentDetailBean.class);
		i = (new CommentService()).saveComment(comment);
		if(i>0) {
			System.out.println("saveCommentServlet保存数据成功！");
			out.write(i+"");
			out.flush();
			out.close();
		}else {
			System.out.println("saveCommentServlet保存数据失败！");
		}
	}

}
