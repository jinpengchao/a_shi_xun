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

/**
 * Servlet implementation class DeleteSendenAlarmServlet
 */
@WebServlet("/delAlarm")
public class DeleteSendenAlarmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteSendenAlarmServlet() {
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
	
		int alarmId = Integer.parseInt(data);
		
		AlarmService alarmService = new AlarmService();
		alarmService.deleteSendedAlarm(alarmId);
		System.out.println("delAlarm--删除闹钟成功！");
		out.write("删除成功!");
		out.flush();
		out.close();
		br.close();
		is.close();
	}

}
