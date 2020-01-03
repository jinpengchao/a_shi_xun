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
import entity.ParentUserInfo;

import org.json.JSONException;
import org.json.JSONObject;
import user.service.UserService;

/**
 * Servlet implementation class SearchUserInfoServlet
 */
@WebServlet("/searchUserInfo")
public class SearchUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchUserInfoServlet() {
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
		if(null==data) {
			System.out.println("SearchUserInfoServlet--客户端信息未获取");
		}else {
			try {
				JSONObject json = new JSONObject(data);
				String phone = json.getString("phone");
				int type = json.getInt("type");
				System.out.println("SearchUserInfoServlet--:"+type+phone);
				UserService userService = new UserService();
				ParentUserInfo ui = userService.selectUserInfo(phone, type);
				if(ui != null) {
					Gson gson = new Gson(); 
					String userInfos = gson.toJson(ui);
					System.out.println("SearchUserInfoServlet--userInfos:"+userInfos);
					out.write(userInfos);
				}else
					System.out.println("SearchUserInfoServlet--userInfos:怎么回事小老弟");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		out.flush();
		out.close();
		br.close();
		is.close();
	}

}
