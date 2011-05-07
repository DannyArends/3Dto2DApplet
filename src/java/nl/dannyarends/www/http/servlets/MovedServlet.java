package nl.dannyarends.www.http.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.dannyarends.generic.Utils;

public class MovedServlet extends Servlet{
	private static final long serialVersionUID = 6495004336068621278L;

	@Override
	public void service(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
		if(isVerbose()) Utils.console(req.getRequestURL() + " -> " +req.getRequestURL().toString().replace("http://","http://www."));
		res.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		res.sendRedirect(req.getRequestURL().toString().replace("http://","http://www."));
	}

}
