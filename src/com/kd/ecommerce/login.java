package com.kd.ecommerce;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String query = "";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("<h1>Unsupported get request</h1>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String uname = request.getParameter("username");
		String pwd = request.getParameter("password");
		if(uname != null && pwd != null && uname != "" && pwd != ""){
			if(logged(request.getParameter("username"), request.getParameter("password"))) {
				session.setAttribute("uname", uname);
				response.sendRedirect("main.jsp?login=true");
			}else{
				request.setAttribute("msg","Invalid Login");
	            request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}else {
        	request.setAttribute("msg","Invalid Login");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
	}
	
	public static boolean logged(String usr, String pass) {
        query = "select * from customers where username = '"+usr+"' and password = '"+pass+"'";
        Boolean found = false;
        try {
            Connection cn = DBConnect.getInstance();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
            	found = true;
            }
            return found;
        } catch (SQLException e) {
            e.printStackTrace();
            return found;
        }

    }

}
