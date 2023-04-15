package booking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Register")
public class Register extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String mobileno = req.getParameter("mb");
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String pass = req.getParameter("pass");
		
		String url="jdbc:mysql://localhost:3306/booking?user=root&password=12345";
		
		
		PrintWriter pw = resp.getWriter();
		try {
			String update = "insert into acounts(mobileno,name,email,password) values(?,?,?,?)";
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement ps = connect.prepareStatement(update);
			ps.setString(1, mobileno);
			ps.setString(2, name);
			ps.setString(3, email);
			ps.setString(4, pass);
			
			int num = ps.executeUpdate();
			if(num!=0)
			{
				String mobileNo = "sp"+mobileno;
				String create = "create table "+mobileNo+" (bookingid varchar(10),"
						+ "fromlocation varchar(20),tolocation varchar(20),bookingdate varchar(20),seatno varchar(4))";
				Statement s = connect.createStatement();
				int num1 = s.executeUpdate(create);
				RequestDispatcher rd = req.getRequestDispatcher("login.html");
				rd.forward(req, resp);
				
			}
		} catch (Exception e) {
			RequestDispatcher rd = req.getRequestDispatcher("register.html");
			rd.include(req, resp);
			pw.println("<p style='color:red'>Account Already Exists Plzz Login</p>");
			
		}	
	}
}
