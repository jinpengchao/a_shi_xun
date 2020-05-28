package user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.ParentUserInfo;
import user.service.UserService;

/**
 * Servlet implementation class ControlUserPosted
 */
@WebServlet("/ControlUserReposted")
public class ControlUserReposted extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session = null;
	private UserService userService = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControlUserReposted() {
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
		List<ParentUserInfo> list = userService.getParentUserReposted();
		session = request.getSession();
		session.setAttribute("userReposted", list);
		response.sendRedirect(request.getContextPath()+"/page/links/bugList.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		userService = new UserService();
		String nameCheck = request.getParameter("nameCheck");
		System.out.println(nameCheck);
		String headCheck = request.getParameter("headerCheck");
		String pwCheck = request.getParameter("pwCheck");
		int dayOff = Integer.valueOf(request.getParameter("time"));
		String phone = request.getParameter("phone");
		if((nameCheck!=null&&!nameCheck.equals(" "))&&(headCheck!=null&&!headCheck.equals(" "))&&(pwCheck!=null&&!pwCheck.equals(" "))&&(phone!=null&&!phone.equals(" "))) {
			userService.closeDays(nameCheck,headCheck,pwCheck,dayOff,phone);
			out.write(1);//返回成功标志
		}else {
			System.out.println("参数错误");
		}
	}

}
