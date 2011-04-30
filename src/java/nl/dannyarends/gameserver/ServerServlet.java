package nl.dannyarends.gameserver;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.dannyarends.generic.Utils;
import nl.dannyarends.generic.JavaCompiler.CompileUnit;
import nl.dannyarends.options.GameOptions;
import nl.dannyarends.www.http.servlets.Servlet;


/**
 * \brief Class used to test the compiled Applets<br>
 *
 * bugs: none found<br>
 */
public class ServerServlet extends Servlet{
	private static final long serialVersionUID = 3365213563240648229L;
	GameServer server;
	ServerServlet(GameServer s){
		server = s;
	}
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		OutputStream o = res.getOutputStream();
		o.write(("<html>\n").getBytes());
		o.write(("\t<head>\n").getBytes());
		o.write(("\t<title>Applets</title>\n").getBytes());
		o.write(("\t</head>\n").getBytes());
		o.write(("\t<body>\n").getBytes());
		o.write(("\tOverview:<br>\n").getBytes());
		for(CompileUnit applet : server.appletCompiler.getApplets()){
			String filename = applet.getJarName().substring(applet.getJarName().lastIndexOf("/")+1);
			o.write(("\t\t<APPLET codebase='data/' code='"+applet.mainClass.replace(".", "/")+".class' archive='" + filename + "'></APPLET><br/>\n").getBytes());
		}
		o.write(("\t</body>\n").getBytes());
		o.write(("</html>\n").getBytes());
		o.flush();
		o.close();
	}

}
