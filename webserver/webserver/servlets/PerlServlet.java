package webserver.servlets;

import generic.CommandExecutor;
import generic.Utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PerlServlet extends Servlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Thread myInterpreter;
		String arguments = "";
		String tempstring = "";
		CommandExecutor myCommandExe = new CommandExecutor();
		String filename = req.getPathTranslated() != null ? req.getPathTranslated().replace('/', File.separatorChar) : "";
		
		for (Enumeration<?> e = req.getParameterNames() ; e.hasMoreElements() ;) {
			if((tempstring = (String) e.nextElement()) !=  null){
				arguments += tempstring + "=" + req.getParameter(tempstring) + ";";
			}
	    }
		
		filename = "." + File.separator + "cgi-bin" + filename.substring(1);
//		Utils.console("Need to interpret file: " + filename);
//		Utils.console("With arguments: " + arguments);
		File f = new File(filename);
		if(f.exists()){
			myCommandExe.addCommand("perl -w " + filename + " " + arguments);
			myInterpreter = new Thread(myCommandExe);
			myInterpreter.run();
			try {
				myInterpreter.join();
			} catch (InterruptedException e) {
				throw new ServletException("Interpretation of " + filename + " didn't finish");
			}
			OutputStream o = res.getOutputStream();
			tempstring = "" + myCommandExe.res;
			for(String s : tempstring.split("\n")){
				if(s != null && !s.equals("")){
					if(s.startsWith("Content-type:")){
						Utils.console("Possible content type: " + s.split(": ")[1]);
						res.setContentType(s.split(": ")[1]);
					}else{
						o.write((s += "\n").getBytes());
						o.flush();
					}
				}
			}
		}else{
			Utils.log("No file: " + filename,System.err);
		}
	}

}
