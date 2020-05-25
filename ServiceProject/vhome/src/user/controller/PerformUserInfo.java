package user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.ParentUserInfo;
import user.service.UserService;

/**
 * Servlet implementation class PerformUser
 */
@WebServlet("/PerformUserInfo")
public class PerformUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerformUserInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		userService = new UserService();
		String id = request.getParameter("id");
		System.out.println(id);
		userService.delParentUserInfo(id);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		userService = new UserService();
		ParentUserInfo pf = new ParentUserInfo();
		pf.setId(request.getParameter("userId"));
		pf.setArea(request.getParameter("userArea"));
		pf.setHeaderImg(request.getParameter("userImg"));
		pf.setImei(request.getParameter("userImei"));
		pf.setNikeName(request.getParameter("userNick"));
		pf.setPersonalWord(request.getParameter("userWord"));
		pf.setPhone(request.getParameter("userPhone"));
		pf.setSex(request.getParameter("userSex"));
		pf.setStatus(request.getParameter("userStatus"));
		userService.updateParentUserInfo(pf);
		out.write(1);//返回成功标志
	}

}
