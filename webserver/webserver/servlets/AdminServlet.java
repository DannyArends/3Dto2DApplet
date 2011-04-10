package webserver.servlets;

import generic.options.ServerOptions;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdminServlet extends Servlet {
	private static final long serialVersionUID = 9001493560790657038L;

	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		OutputStream o = res.getOutputStream();
		res.setContentType("text/html");
		o.write(("<html>" +
				 "<head>" +
				 "	<title>Admin-page</title>" +
				 "</head>" +
				 "<body>" +
				 "	<table width=700>" +
				 "		<tr><th colspan=2>Web Settings</th></tr>" +
				 "		<tr><td>Websites in directory:</td><td>" + ServerOptions.web_dir + "</td></tr>" +
				 "		<tr><td>DB driver:</td><td>" + ServerOptions.db_driver + "</td></tr>" +
				 "		<tr><td>DB user:</td><td>" + ServerOptions.db_user + "</td></tr>" +
				 "		<tr><td>DB uri:</td><td>" + ServerOptions.db_uri + "</td></tr>" +
				 "		<tr><td>Trace/lvl:</td><td>" + ServerOptions.trace_enabled + "/" + ServerOptions.trace_priority + "</td></tr>" +
				 "	</table>" +
				 "	<table width=700>" +
				 "		<tr><th colspan=4>Interpreter setting and tests</th></tr>" +
				 "		<tr><td>Perl:</td><td>" + ServerOptions.perl_enabled + "</td><td>" + ServerOptions.perl_bin_location + "</td><td><iframe width=150 height=40 src='admin/tests/perl.cgi'></iframe></td></tr>" +
				 "		<tr><td>PHP:</td><td>" + ServerOptions.php_enabled + "</td><td>" + ServerOptions.php_bin_location + "</td><td><iframe width=150 height=40 src='admin/tests/php.php'></iframe></td></tr>" +
				 "		<tr><td>Python:</td><td>" + ServerOptions.python_enabled + "</td><td>" + ServerOptions.python_bin_location + "</td><td><iframe width=150 height=40 src='admin/tests/python.py'></iframe></td></tr>" +
				 "	</table>" +
				 "</body>").getBytes());
		o.flush();
		o.close();
	}

}
