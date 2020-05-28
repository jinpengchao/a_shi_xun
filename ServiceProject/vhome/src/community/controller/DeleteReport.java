package community.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import community.service.PostService;
import jpushutil.JpushClientUtil;
import user.service.UserService;

/**
 * Servlet implementation class DeleteReport
 */
@WebServlet("/DeleteReport")
public class DeleteReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteReport() {
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
		UserService us = new UserService();
		String id1=request.getParameter("id");
		String rId=request.getParameter("currentrId");
		String phone = request.getParameter("currentphone");
		String content = request.getParameter("currentcontent");
		if(content.length()>14) {
			content = content.substring(0,14);
		}
		int id=Integer.parseInt(id1);
		(new PostService()).delPostReport(id);
		(new PostService()).delPost(id);
		(new PostService()).delPost2(id);
		if (JpushClientUtil.sendToRegistrationId(rId, "",
				"", "", "") == 1) {
			System.out.println("success");
		}
		us.insertAdminMessage(0,"您的帖子 \""+content+"\" 被其他用户举报，现已被删除，请注意遵守社区规定！",phone,"","-");
		response.sendRedirect("/vhome/PostReport");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
