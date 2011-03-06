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

/// Servlet to serve CGI script from the cgi-bin directory
//<p>
//The languages tested are:
//<ul>
//<li>Perl</li>
//<li>PHP</li>
//<li>Python</li>
//</ul>
//Python and PHP receive the input as a single string in the ARGV[1],
//parameters separated by ; then p=v for an example to parse these out see cgi-bin/test<br/>
//Perl users can 'use CGI' and then parse the param(), also an example in cgi-bin/test.<br/>
//Files not matching the known extensions are served via the common serveFile function using the mime.properties
//</p>

public class CGIServlet extends Servlet {
	private static final long serialVersionUID = 1L;
	String[][] extensions = new String[][]{
			{"pl","cgi","php","php3","py"},
			{"perl -X ","perl -X ","php -c php.ini -f","php -c php.ini -f","python -u"}
	};
	String mainpage = "index.cgi";
	String short_localPath = "";
	
	public CGIServlet(){
		super();
	}
	
	public CGIServlet(String path,boolean inCGI){
		this();
		short_localPath = getLocal_path() + File.separator + path;
		setLocal_path(getLocal_path() + File.separator + path);
		if(inCGI)setLocal_path(getLocal_path() + File.separator + "cgi-bin");
	}
	
	String matchExtension(String ext){
		String r = "";
		int cnt  = 0;
		for(String s : extensions[0]){
			if(s.equals(ext)){
				r = extensions[1][cnt];
			}
			cnt++;
		}
		return r;
	}
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		CommandExecutor myCommandExe = new CommandExecutor();
		Thread myInterpreter;
		String arguments = "";
		String tempstring = "";
		long length = 0;
		String filename = "";
		String extension = "";
		String command = "";
		String path = "";
		if(!getLocal_path().equals("")){
			path = getLocal_path() + File.separator + path;
		}
		filename = req.getPathTranslated() != "." ? req.getPathTranslated().replace('/', File.separatorChar) : "./index.cgi";
		extension = filename.substring(filename.indexOf(".", 2)+1);
		Utils.log("filename:" + filename + " Extension:"+extension + " Path:" + path, System.err);
		filename = filename.substring(filename.indexOf(".", 0)+1);
		filename = path + filename;

		for (Enumeration<?> e = req.getParameterNames() ; e.hasMoreElements() ;) {
			if((tempstring = (String) e.nextElement()) !=  null){
				arguments += tempstring + "=" + req.getParameter(tempstring) + ";";
			}
	    }

		File file = new File(filename);
		if(!file.exists()){
			Utils.console("No Such File: "+filename+"\n");
			arguments = "error=Page%20not%20found;page=" + filename + ";";
			file = new File(path + File.separator + "cgi-bin"+ File.separator + mainpage);
			extension = mainpage.substring(mainpage.indexOf(".", 2)+1);
		}
		if(!(command = matchExtension(extension)).equals("")){
			Utils.console("Creating command: " + command + "I "+ path + " " + file.getCanonicalPath() + " " + arguments);
			myCommandExe.addCommand("cd " + short_localPath + " && "+ command + file.getCanonicalPath() + " " + arguments);
		}else{
			Utils.log("No interpreter for: " + extension,System.err);
			serveFile(req, res, false, file);
			return;
		}
		myInterpreter = new Thread(myCommandExe);
		myInterpreter.start();
		try {
			myInterpreter.join(3000);
		} catch (InterruptedException e) {
			Utils.log("Interpretation of " + file.getAbsolutePath() + " didn't finish correctly",System.err);
		}
		OutputStream o = res.getOutputStream();
		tempstring = "" + myCommandExe.res;
		boolean contenttype = false;
		for(String s : tempstring.split("\n")){
			if(s != null && !s.equals("")){
				if(!contenttype && s.toLowerCase().startsWith("content-type:")){
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
		if(!contenttype){
			Utils.log("Unexpected output when serving file: " + file + ", no page", System.err);
		}else{
			Utils.console("Served file: " + file + " " +  length + " bytes");
		}
	}

}
