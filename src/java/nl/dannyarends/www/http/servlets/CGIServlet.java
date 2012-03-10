package nl.dannyarends.www.http.servlets;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.dannyarends.generic.CommandExecutor;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.options.WebOptions;

/**
 * \brief Servlet to serve CGI script from the cgi-bin directory<br>
 *
 * The languages tested are:
 * <ul>
 * <li>Perl</li>
 * <li>PHP</li>
 * <li>Python</li>
 * </ul>
 * Python and PHP receive the input as a single string in the ARGV[1],parameters 
 * separated by ; then p=v for an example to parse these out see cgi-bin/test<br/>
 * Perl users can 'use CGI' and then parse the param(), also an example in cgi-bin/test.
 * <br/>Files not matching the known extensions are served via the common serveFile 
 * function using the mime.properties
 * 
 * bugs: none found<br>
 */
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
		this.HostsNotAllowed.add("static.giga-dns.com");
		this.HostsNotAllowed.add("96.44.142.250");
		this.HostsNotAllowed.add("68.169.86.222");
		this.HostsNotAllowed.add("68.169.86.220");
		this.HostsNotAllowed.add("96.44.143.234");
		this.HostsNotAllowed.add("216.45.48.210");
		this.HostsNotAllowed.add("178.150.142.210");
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
	
	ArrayList<String> HostsNotAllowed = new ArrayList<String>();
	
	boolean checkOrigin(HttpServletRequest req, HttpServletResponse res){
		String origin = req.getRemoteHost();
		if(req.getParameter("p") != null){
			if(req.getParameter("p").contains("\"")){
				if(!HostsNotAllowed.contains(origin))HostsNotAllowed.add(origin);
				return false;
			}
			if(req.getParameter("p").contains("unBlockMe")){
				ArrayList<String> newNotAllowed= new ArrayList<String>();
				for(String host:HostsNotAllowed){
					if(!host.equals(origin)){
						newNotAllowed.add(host);
					}
				}
				HostsNotAllowed = newNotAllowed;
			}
		}
		for(String host:HostsNotAllowed){
		  if(origin.contains(host)) return false;
		}
		return true;
	}
	
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if(!checkOrigin(req,res)){
			Utils.log("Origin check failed: " + req.getRemoteHost() + ", no page", System.err);
			OutputStream o = res.getOutputStream();
			o.write("<h1>Hacking me, Hacking you. Something we shouldn't do</h1>IP/Domains Blocked:<ul>".getBytes());
			for(String host:HostsNotAllowed){
				o.write(("<li>" + host + "</li>").getBytes());
			}
			o.write("</ul>You can write a msg to Danny.Arends@gmail.com to get unblocked".getBytes());
			o.close();
			return;
		}
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
			System.out.println("redirecting to index");
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
			if(isVerbose()) System.out.println("No Such File: "+filename);
			arguments = "error=Page%20not%20found;p=" + filename + ";";
			file = new File(path + File.separator + mainpage);
			extension = mainpage.substring(mainpage.indexOf(".", 2)+1);
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		if(!(command = matchExtension(extension)).equals("")){
			boolean docommand = false;
			if(command.startsWith("perl") && WebOptions.perl_enabled) docommand=true;
			if(command.startsWith("php") && WebOptions.php_enabled) docommand=true;
			if(command.startsWith("python") && WebOptions.python_enabled) docommand=true;
			if(docommand){
				myCommandExe.addCommand("cd " + (inCGI?short_localPath:getLocal_path()) + " && "+ command + file.getCanonicalPath() + " " + arguments);
			}else{
				Utils.log("Interpretation of " + file.getAbsolutePath() + " refused due to WebOptions",System.err);
				return;
			}

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
		tempstring = "" + myCommandExe.getResult();
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
						//System.out.println(s);
						o.flush();
						length += (s.getBytes().length + 1);
					}
				}
			}
		}
		o.flush();
		o.close();
		if(!contenttype){
			Utils.log("Unexpected output when serving file: " + file + ", no page", System.err);
		}else{
			Utils.console("Handled: " + req.getRequestURI() + (req.getParameter("p")!=null?" "+req.getParameter("p")+" ":" ") + " " + req.getQueryString() + " " +  length + " bytes to " + req.getRemoteHost());
		}
	}

}
