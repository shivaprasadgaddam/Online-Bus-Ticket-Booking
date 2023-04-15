package booking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Driver;


@WebServlet("/BookingHistory")
public class BookingHistory extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String mobileno = (String)session.getAttribute("mobileno");
		
		String url = "jdbc:mysql://localhost:3306/booking?user=root&password=12345";
		String select ="select * from "+mobileno;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url);
			Statement s = connect.createStatement();
			ResultSet rs = s.executeQuery(select);
			
			PrintWriter pw = resp.getWriter();
			
			if(rs.last())
			{
				rs.beforeFirst();
				while(rs.next())
				{
					pw.println("Booking Id : "+rs.getString(1));
					pw.println("From : "+rs.getString(2));
					pw.println("To : "+rs.getString(3));
					pw.println("Journey Date : "+rs.getString(4));
					pw.println("Your Seat No : "+rs.getInt(5));
					pw.println();
					
				}
			}
			else
			{
				pw.println("<p style='color:blue'>No Tickets Booked...</p>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
