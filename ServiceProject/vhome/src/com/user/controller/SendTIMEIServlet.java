package com.user.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.user.service.GetInfo;

/**
 * Servlet implementation class SendParentIMEI
 */
@WebServlet("/SendIMEI")
public class SendTIMEIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendTIMEIServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		String phone = request.getParameter("phone");
		System.out.println(phone);
		GetInfo service = new GetInfo();
		String[] imeis=null;
		if(phone!=null&&(!phone.equals(" "))) {
			imeis =  service.getInform(phone);
			if(!imeis[0].equals(" ")) {
			System.out.println(imeis[0].toString());
			}else {
				System.out.println("查询数据为空");
			}
			writer.write(imeis[0]+"/n"+imeis[1]);
			writer.flush();
			writer.close();
		}else {
			System.out.println("数据为空");
		}
	}

}
