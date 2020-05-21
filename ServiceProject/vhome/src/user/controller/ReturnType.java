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
 * Servlet implementation class ReturnType
 */
@WebServlet("/ReturnType")
public class ReturnType extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnType() {
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
		System.out.println("returntype"+data);
		String phone = data;
		UserService userService = new UserService();
		int type = userService.getUserType(phone);
		out.write(type+"");
		out.flush();
		out.close();
		br.close();
		is.close();
	}

}
