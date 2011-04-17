package nl.dannyarends.www.http.servlets;

import nl.dannyarends.generic.CommandExecutor;
import nl.dannyarends.generic.Utils;

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
			{"perl -X ","perl -X ","php -c php.ini -f ","php -c php.ini -f ","python -u "}
	};
	String mainpage = "index.cgi";
	String short_localPath = "";
	boolean inCGI = false;
	
	public CGIServlet(){
		super();
	}
	
	public CGIServlet(String path, boolean inCGI){
		this();
		short_localPath = getLocal_path() + File.separator + path;
		setLocal_path(getLocal_path() + File.separator + path);
		this.inCGI=inCGI;
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
	
	//Basic escaping of strings into something we kind of know
	String escapeString(String s){
		//http://www.w3schools.com/TAGS/ref_urlencode.asp
		String param = s;
		param = param.replace("%", "%25");	// '%' first or we'll be screwed
		param = param.replace("<", "%3C");
		param = param.replace("#", "%23");
		param = param.replace("=", "%3D");
		param = param.replace(">", "%3E");
		param = param.replace("=", "%3D");
		param = param.replace("+", "%2B");
		param = param.replace(":", "%3A");
		param = param.replace("-", "%2D");
		param = param.replace(";", "%3B");
		param = param.replace("\"", "%27");
		param = param.replace("\\", "%5C");
		param = param.replace("/", "%2F");
		param = param.replace(" ", "%20");
		param = param.replace("\t", "%20%20");
		param = param.replace("&", "%26");
		param = param.replace("\'", "%27");
		param = param.replace("?", "%3F");
		return param;
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
		if(req.getPathTranslated()==null){
			filename = "./index.cgi";
		}else{
			filename = req.getPathTranslated() != "." ? req.getPathTranslated().replace('/', File.separatorChar) : "./index.cgi";
		}
		extension = filename.substring(filename.indexOf(".", 2)+1);
		if(isLogEnabled()) Utils.log("filename:" + filename + " Extension:"+extension + " Path:" + path, System.err);
		filename = filename.substring(filename.indexOf(".", 0)+1);
		filename = path + filename;

		for (Enumeration<?> e = req.getParameterNames() ; e.hasMoreElements() ;) {
			if((tempstring = (String) e.nextElement()) !=  null){
				arguments += tempstring + "=" + escapeString(req.getParameter(tempstring)) + ";";
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
			//Utils.console("Creating command: " + command + "I "+ path + " " + file.getCanonicalPath() + " " + arguments);
			myCommandExe.addCommand("cd " + (inCGI?short_localPath:getLocal_path()) + " && "+ command + file.getCanonicalPath() + " " + arguments);
		}else{
			if(isLogEnabled()) Utils.log("No interpreter for: " + extension,System.err);
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
					if(isLogEnabled())Utils.console("Possible content type: " + s.split(": ")[1]);
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
			Utils.console("Served file: " + req.getPathTranslated() + " " +  length + " bytes to " + req.getLocalName());
		}
	}

}
