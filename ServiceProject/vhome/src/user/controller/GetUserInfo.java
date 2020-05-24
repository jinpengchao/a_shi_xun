package user.controller;

import java.io.IOException;
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
		String param = "空空空空如也";
		param = request.getParameter("param");
		System.out.println(param);
		HttpSession session = request.getSession();
			Gson json = new Gson();
			List<String> strList = new ArrayList<String>();
			List<ParentUserInfo> list = (List<ParentUserInfo>) session.getAttribute("userParents");
			for(int i=0;i<list.size();i++) {
				strList.add(list.get(i).toString());
			}
			list.clear();
			for(String index:strList) {
				if(index.contains(param)) {
					String str = json.toJson(index);//防止返回值有空格
					ParentUserInfo pf= json.fromJson(str,ParentUserInfo.class);
					list.add(pf);
				}
			}
			request.setAttribute("userParents",list);
			request.getRequestDispatcher("/page/user/allUsers.jsp").forward(request, response);
	}
}
