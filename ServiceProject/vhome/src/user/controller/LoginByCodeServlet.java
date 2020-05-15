package user.controller;

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

import com.google.gson.Gson;

import entity.User;
import net.sf.json.JSONObject;
import user.service.UserService;

/**
 * Servlet implementation class LoginByCodeServlet
 */
@WebServlet("/LoginByCodeServlet")
public class LoginByCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginByCodeServlet() {
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
		doGet(request, response);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/text;charset=utf-8");
		InputStream is = request.getInputStream();
		PrintWriter out = response.getWriter();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
		String data = br.readLine();
		Gson gson = new Gson();
		User user = null;
		user = gson.fromJson(data, User.class);
		if(null==user) {
			System.out.println("客户端登录消息未获取");
		}
		String phone = user.getPhone();
		
		UserService userService = new UserService();
		User u = userService.loginBycode(phone);
		if(u != null) {
			int type = u.getType();
			String p = u.getPhone();
			String pwd = u.getPassword();
			JSONObject json = new JSONObject();
			json.put("p", p);
			json.put("pwd", pwd);
			json.put("type", type);
			out.write(json.toString());
		}
		out.flush();
		out.close();
		br.close();
		is.close();
	}

}
