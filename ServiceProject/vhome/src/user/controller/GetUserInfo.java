package user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import entity.ParentUserInfo;

/**
 * Servlet implementation class GetUserInfo
 */
@WebServlet("/GetUserInfo")
public class GetUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/text;charset=utf-8");
		PrintWriter out = response.getWriter();
		String param = "空空空空如也";
		param = request.getParameter("param");
		System.out.println(param);
		HttpSession session = request.getSession();
			Gson json = new Gson();
			List<ParentUserInfo> storeList = new ArrayList<ParentUserInfo>();
			List<ParentUserInfo> list = (List<ParentUserInfo>) session.getAttribute("userParents");
			for(int i=0;i<list.size();i++) {
				String index = list.get(i).toString().trim();
				if(index.contains(param)) {
					storeList.add(list.get(i));
				}
			}
			session.setAttribute("userParents",storeList);
			request.getRequestDispatcher("/page/user/allUsers.jsp").forward(request,response);
	}
}
