package booking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Profile")
public class Profile extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String mb = (String)session.getAttribute("mobileno");
		String mobileno = mb.substring(2);
		
		String url ="jdbc:mysql://localhost:3306/booking?user=root&password=12345";
		
		String select = "select * from acounts where mobileno = ?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conntec = DriverManager.getConnection(url);
			PreparedStatement ps = conntec.prepareStatement(select);
			ps.setString(1, mobileno);
			ResultSet rs = ps.executeQuery();
			PrintWriter pw = resp.getWriter();
			if(rs.last())
			{
				pw.println("Your Name Is : "+rs.getString(2));
				pw.println("Your Mobile No : "+rs.getString(1));
				pw.println("Your Email Id  : "+rs.getString(3));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
