package user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.NewTicketBody;
import jpushutil.JpushClientUtil;
import user.service.UserService;

/**
 * Servlet implementation class SaveAnswer
 */
@WebServlet("/SaveAnswer")
public class SaveAnswer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveAnswer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
  		request.setCharacterEncoding("utf-8");
  		response.setContentType("text/text;charset=utf-8");
  		UserService us = new UserService();
  	
		String phone = request.getParameter("phone");
		String content = request.getParameter("content");
		String i = request.getParameter("id");
		String registrationID =  request.getParameter("registrationID");
		if (JpushClientUtil.sendToRegistrationId(registrationID, "",
				"微家官方", "您的反馈已被回复，点击查看", "") == 1) {
			System.out.println("success");
		}
		int id = Integer.parseInt(i);
		us.insertAnsewer(id,phone, content,registrationID);
		us.insertAdminMessage(id,phone,content);
		us.changeQuestionsType(id);
    	
  	}

  	/**
  	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  	 */
  	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		doGet(request, response);
  	}

  }
