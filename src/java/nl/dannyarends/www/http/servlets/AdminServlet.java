package nl.dannyarends.www.http.servlets;

import nl.dannyarends.options.DatabaseOptions;
import nl.dannyarends.options.WebOptions;

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
				 "		<tr><td>Websites in directory:</td><td>" + WebOptions.web_dir + "</td></tr>" +
				 "		<tr><td>DB driver:</td><td>" + DatabaseOptions.db_driver + "</td></tr>" +
				 "		<tr><td>DB user:</td><td>" + DatabaseOptions.db_user + "</td></tr>" +
				 "		<tr><td>DB uri:</td><td>" + DatabaseOptions.db_uri + "</td></tr>" +
				 "		<tr><td>Trace/lvl:</td><td>" + WebOptions.trace_enabled + "/" + WebOptions.trace_priority + "</td></tr>" +
				 "	</table>" +
				 "	<table width=700>" +
				 "		<tr><th colspan=4>Interpreter setting and tests</th></tr>" +
				 "		<tr><td>Perl:</td><td>" + WebOptions.perl_enabled + "</td><td>" + WebOptions.perl_bin_location + "</td><td><iframe width=150 height=40 src='admin/tests/perl.cgi'></iframe></td></tr>" +
				 "		<tr><td>PHP:</td><td>" + WebOptions.php_enabled + "</td><td>" + WebOptions.php_bin_location + "</td><td><iframe width=150 height=40 src='admin/tests/php.php'></iframe></td></tr>" +
				 "		<tr><td>Python:</td><td>" + WebOptions.python_enabled + "</td><td>" + WebOptions.python_bin_location + "</td><td><iframe width=150 height=40 src='admin/tests/python.py'></iframe></td></tr>" +
				 "	</table>" +
				 "</body>").getBytes());
		o.flush();
		o.close();
	}

}
