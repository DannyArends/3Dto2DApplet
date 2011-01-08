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

/// Servlet to serve perl cgi-scripts from the cgi-bin directory
//<p>
//class to server perl cgi-script from the cgi-bin directory
//</p>

public class PerlServlet extends Servlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Thread myInterpreter;
		String arguments = "";
		String tempstring = "";
		long length = 0;
		CommandExecutor myCommandExe = new CommandExecutor();
		String filename ="";
		
		filename = req.getPathTranslated() != null ? req.getPathTranslated().replace('/', File.separatorChar) : "";
		filename = "." + File.separator + "cgi-bin" + ((filename.substring(0).equals("") || filename.substring(1).equals(""))? File.separator + "index.cgi" : filename.substring(1));
		//Utils.console("Need to interpret file: " + filename);
		
		for (Enumeration<?> e = req.getParameterNames() ; e.hasMoreElements() ;) {
			if((tempstring = (String) e.nextElement()) !=  null){
				arguments += tempstring + "=" + req.getParameter(tempstring) + ";";
			}
	    }
		Utils.console("With arguments: " + "perl -w " + filename + " " + arguments);

		File file = new File(filename);
		if(!file.exists()){
			Utils.console("No Such File: \n");
			arguments = "error=Page%20not%20found;page=" + filename + ";";
			file = new File("." + File.separator + "cgi-bin"+ File.separator +"index.cgi");
		}
		myCommandExe.addCommand("perl -X " + file.getAbsolutePath() + " " + arguments);
		myInterpreter = new Thread(myCommandExe);
		myInterpreter.start();
		try {
			Utils.log("Interpretation of " + file.getAbsolutePath() + " started",System.err);
			myInterpreter.join();
		} catch (InterruptedException e) {
			Utils.log("Interpretation of " + file.getAbsolutePath() + " didn't finish correctly",System.err);
		}
		OutputStream o = res.getOutputStream();
		tempstring = "" + myCommandExe.res;
		boolean contenttype = false;
		for(String s : tempstring.split("\n")){
			if(s != null && !s.equals("")){
				if(s.toLowerCase().startsWith("content-type:")){
					if(isLogenabled())Utils.console("Possible content type: " + s.split(": ")[1]);
					res.setContentType(s.split(": ")[1]);
					contenttype=true;
				}else{
					if(contenttype){
						o.write((s += "\n").getBytes());
						o.flush();
						length += (s.getBytes().length + 1);
					}
				}
			}
		}
		o.close();
		Utils.console("Served file: " + file + " " +  length + " bytes");

	}

}
