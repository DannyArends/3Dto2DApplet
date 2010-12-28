package webserver.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Servlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private String charSet;
	private static boolean logenabled = false;
	
	public Servlet(){
		setCharSet("UTF8");
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public String getCharSet() {
		return charSet;
	}
	
	abstract public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;

	public static boolean isLogenabled() {
		return logenabled;
	}
	
	public static void setLogenabled(boolean b) {
		logenabled=b;
	}
}
