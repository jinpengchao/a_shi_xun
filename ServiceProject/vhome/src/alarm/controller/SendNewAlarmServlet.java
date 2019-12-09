package alarm.controller;

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

import alarm.service.AlarmService;
import entity.AlarmBean;
import entity.User;
import user.service.UserService;

/**
 * Servlet implementation class SendNewAlarmServlet
 */
@WebServlet("/sendnew")
public class SendNewAlarmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendNewAlarmServlet() {
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
		InputStream is = request.getInputStream();
		PrintWriter out = response.getWriter();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
		String data = br.readLine();
		Gson gson = new Gson();
		AlarmBean alarmBean = null;
		alarmBean = gson.fromJson(data, AlarmBean.class);
		if(null==alarmBean) {
			System.out.println("闹钟消息未获取");
		}
		int alarmId = alarmBean.getAlarmId();
		String alarmTime = alarmBean.getAlarmTime();
		String sendPersonId = alarmBean.getSendPersonId();
		String receivePersonId = alarmBean.getReceivePersonId();
		String content = alarmBean.getContent();
		
		AlarmService alarmService = new AlarmService();
		alarmService.insertNewSendAlarm(alarmId, alarmTime, sendPersonId, receivePersonId, content);
		System.out.println("SendNewAlarmServlet--发送新闹钟成功！");
		out.write("发送新闹钟成功!");
		out.flush();
		out.close();
		br.close();
		is.close();
	}

}