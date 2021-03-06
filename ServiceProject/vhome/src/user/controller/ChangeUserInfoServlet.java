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

import org.json.JSONException;
import org.json.JSONObject;

import community.service.CommentService;
import community.service.PostService;
import community.service.ReplyService;
import entity.ParentUserInfo;
import user.service.UserService;

/**
 * Servlet implementation class ChangeUserInfoServlet
 */
@WebServlet("/changeInfo")
public class ChangeUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeUserInfoServlet() {
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
		String d = br.readLine();
		UserService userService = new UserService();
		try {
			JSONObject json = new JSONObject(d);
			String phone = json.getString("phone");
			int type = json.getInt("type");
			String flag = json.getString("flag");
			if(flag.equals("nickName")) {
				String nickName = json.getString("data");
				userService.updateUserInfo(phone, type, flag , nickName);
				ParentUserInfo pui = userService.selectUserInfo(phone, type);
				String id = pui.getId();
				(new PostService()).changeNameById(nickName, id);
				(new CommentService()).changeName(nickName, id);
				(new ReplyService()).changeName(nickName, id);
			}
			if(flag.equals("sex")) {
				String sex = json.getString("data");
				userService.updateUserInfo(phone, type, flag , sex);
			}
			if(flag.equals("area")) {
				String area = json.getString("data");
				userService.updateUserInfo(phone, type, flag , area);
			}
			if(flag.equals("birthday")) {
				String birth = json.getString("data");
				userService.updateUserInfo(phone, type, flag , birth);
			}
			if(flag.equals("personalWord")) {
				String sign = json.getString("data");
				userService.updateUserInfo(phone, type, flag , sign);
			}
			out.write("昵称修改成功");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.flush();
		out.close();
		br.close();
		is.close();
	}

}
