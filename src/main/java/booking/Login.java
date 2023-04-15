package booking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet
{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String email = req.getParameter("email");
		String pass = req.getParameter("pass");
		
		HttpSession session = req.getSession();
	
		String url="jdbc:mysql://localhost:3306/booking?user=root&password=12345";
		String eselect = "select * from acounts where email = ?";
		String pselect = "select * from acounts where password = ?";
		PrintWriter pw = resp.getWriter();
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement ps = connect.prepareStatement(eselect);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.last())
			{
				if(rs.getString(4).equals(pass))
				{
				PreparedStatement ps1 = connect.prepareStatement(pselect);
				ps1.setString(1, pass);
				ResultSet rs1 = ps1.executeQuery();
				if(rs1.last())
				{
					String mb = rs.getString(1);
					String mobileNo="sp"+mb;
					session.setAttribute("mobileno", mobileNo);
					RequestDispatcher rd = req.getRequestDispatcher("home.html");
					rd.forward(req, resp);
				}
				}
				else
				{
					RequestDispatcher rd = req.getRequestDispatcher("login.html");
					rd.include(req, resp);
					pw.write("<p style='color:red'>Invalid Password</p>");
				}
				
			}
			else
			{
				RequestDispatcher rd = req.getRequestDispatcher("login.html");
				rd.include(req, resp);
				pw.write("<p style='color:red'>Invalid UserName</p>");
			}
					
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}	
		
	}
}
