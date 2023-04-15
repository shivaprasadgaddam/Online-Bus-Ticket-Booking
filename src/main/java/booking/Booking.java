package booking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Driver;


@WebServlet("/Booking")
public class Booking extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String from = req.getParameter("from");
		String to = req.getParameter("to");
		String date = req.getParameter("date");
		
		Random r = new Random();
		int sid=r.nextInt(10000);
		if(sid<1000)
		{
			sid+=1000;
		}
		
		String bookingId = "JSP"+sid;
		int seat = r.nextInt(100);
		if(seat<10)
		{
			seat+=10;
		}
		HttpSession session = req.getSession();
		String mobileno = (String)session.getAttribute("mobileno");
		
		String mb=(String)session.getAttribute("mb");
		
		
		String url="jdbc:mysql://localhost:3306/booking?user=root&password=12345";
		
		//email, name, password, mobileno, bookingid, fromlocation, tolocation, bookingdate, bookingtime
		String insert = "insert into "+mobileno+" (bookingid, fromlocation, tolocation, bookingdate,seatno)values(?,?,?,?,?)";
		PrintWriter pw = resp.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement ps = connect.prepareStatement(insert);
			ps.setString(1, bookingId);
			ps.setString(2, from);
			ps.setString(3, to);
			ps.setString(4, date);
			ps.setInt(5, seat);
			
			int num = ps.executeUpdate();
			
			if(num!=0)
			{
				pw.println("Your Booking Id : "+bookingId);
				pw.println("Your Journey Date : "+date);
				pw.println("From Location : "+from);
				pw.println("Your Destination : "+to);
				pw.println("Your Seat No  : "+seat);
				pw.println("Journey Time : 2hrs<br>");
				pw.println("HAPPY JOURNEY....");
			}
			else
			{
				RequestDispatcher rd = req.getRequestDispatcher("booking.html");
				rd.include(req, resp);
				pw.write("<p style='color:red'>Booking Failed...</p>");
			}
		} catch (Exception e) {
			RequestDispatcher rd = req.getRequestDispatcher("booking.html");
			rd.include(req, resp);
			pw.write("<p style='color:red'>Booking Failed...</p>");
		}
		
	}

}
