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
 * Servlet implementation class ShowUserList
 */
@WebServlet("/ShowUserList")
public class ShowUserList extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session = null;
    private UserService userService = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowUserList() {
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
		List<ParentUserInfo> list = userService.findParentInfo();
		session = request.getSession();
		session.setAttribute("userParents", list);
		response.sendRedirect(request.getContextPath()+"/page/user/allUsers.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		userService = new UserService();
		session = request.getSession();
		PrintWriter out = response.getWriter();
		session.setAttribute("total", userService.getTotalParentUserNum("tbl_user"));
		session.setAttribute("usersReported", userService.getTotalParentUserNum("tbl_parentuser_reported"));
		out.write(1);//返回成功标志，进行ajax跳转
	}

}
