package nl.dannyarends.www.http.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.dannyarends.generic.Utils;



/// Abstract class to mimic a Java Servlet.
//<p>
// Abstract class to remove deprecation warning in WebServer class.
// Holds the serveFile function
//</p>
//@see javax.servlet.http.HttpServlet

public abstract class Servlet extends javax.servlet.http.HttpServlet{
	private static final long serialVersionUID = 1L;
	private boolean useCompression;
	private String charSet;
	static final String BYTES_UNIT = "bytes";
	private String website_root_path = "websites";
	private boolean logEnabled;
	private int priority = 0;
	
	public Servlet(){
		setCharSet("UTF8");
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public String getCharSet() {
		return charSet;
	}
	
	public boolean isLogEnabled() {
		return logEnabled;
	}

	public void setLogEnabled(boolean logEnabled) {
		this.logEnabled = logEnabled;
	}
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void trace(String m){
		trace(1,m);
	}
	
	public void trace(int p, String m){
		if(logEnabled){
			if(p==-1){
				Utils.log(m, System.err);
			}else{
				if(priority <= p) Utils.log(m, System.out);	
			}
		}
	}
	
	abstract public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;

	protected void serveFile(HttpServletRequest req, HttpServletResponse res, boolean headOnly, File file)throws IOException {
		trace("Getting: " + file);
		Enumeration<?> enh = req.getHeaderNames();
		while (enh.hasMoreElements()) {
			String hn = (String) enh.nextElement();
			trace(0,"hdr:" + hn + ":" + req.getHeader(hn));
		}
		if (!file.canRead()) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN);
			trace(0,"Error reading file");
			return;
		} else{
			try {
				file.getCanonicalPath();
			} catch (Exception e) {
				trace(0,"Forbidden file");
				res.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden, exception:" + e);
				return;
			}
		}
		//Handle If-Modified-Since.
		res.setStatus(HttpServletResponse.SC_OK);
		long lastMod = file.lastModified();
		long ifModSince = req.getDateHeader("If-Modified-Since");
		if (ifModSince != -1 && ifModSince >= lastMod) {
			res.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			headOnly = true;
		}
		//TODO add processing If-None-Match, If-Unmodified-Since and If-Match
		String contentType = getServletContext().getMimeType(file.getName());
		if (contentType != null) res.setContentType(contentType);
		long flen = file.length();
		String range = req.getHeader("Range");
		long sr = 0;
		long er = -1;
		if (range != null) {
			trace("Range:" + range);
			if (range.regionMatches(true, 0, BYTES_UNIT, 0, BYTES_UNIT.length())) {
				int i = range.indexOf('-');
				if (i > 0) {
					try {
						sr = Long.parseLong(range.substring(BYTES_UNIT.length() + 1, i));
						if (sr < 0)
							throw new NumberFormatException("Invalid start range value:" + sr);
						try {
							er = Long.parseLong(range.substring(i + 1));
						} catch (NumberFormatException nfe) {
							er = flen - 1;
						}
					} catch (NumberFormatException nfe) {
				}
			} // else invalid range? ignore?
			} // else other units not supported
			trace("range values " + sr + " to " + er);
		}
		long clen = er < 0 ? flen : (er - sr + 1);
		res.setDateHeader("Last-modified", lastMod);
		if (er > 0) {
			if (sr > er || er >= flen) {
				res.setHeader("Content-Range", BYTES_UNIT + " */" + flen);
				res.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
				return;
			}
			res.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
			res.setHeader("Content-Range", BYTES_UNIT + " " + sr + '-' + er + '/' + flen);
			trace("content-range:" + BYTES_UNIT + " " + sr + '-' + er + '/' + flen);
		}
		//String ifRange = req.getHeader("If-Range");
		//res.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
		boolean doCompress = false;
		if (isUseCompression() && contentType != null && contentType.startsWith("text")) {
			if (Utils.isGzipAccepted(req.getHeader("Accept-Encoding")) > 0) {
				res.setHeader("Content-Encoding", "gzip");
				doCompress = true;
			}
		}
		if (doCompress == false || headOnly) {
			if (clen < Integer.MAX_VALUE)
				res.setContentLength((int) clen);
			else
				res.setHeader("Content-Length", Long.toString(clen));
		}
		OutputStream out = null;
		InputStream in = null;
		try {
			if (!headOnly) {
				out = doCompress ? new GZIPOutputStream(res.getOutputStream()) : (OutputStream) res.getOutputStream();
				in = new FileInputStream(file);
				while (sr > 0) {
					long sl = in.skip(sr);
					if (sl > 0){
						sr -= sl;
					}else {
						res.sendError(HttpServletResponse.SC_CONFLICT, "Conflict");
						return;
					}
				}
				Utils.copyStream(in, out, clen);
				if (doCompress)
					((GZIPOutputStream) out).finish();
			}
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException ioe) {
			}
			if (out != null) {
				out.flush();
				out.close();
				Utils.console("Served file: " + req.getPathTranslated() + " " +  Long.toString(clen) + " bytes to " + req.getLocalName() + ":" + req.getMethod());
			}
		}
	}

	public void setUseCompression(boolean useCompression) {
		this.useCompression = useCompression;
	}

	public boolean isUseCompression() {
		return useCompression;
	}

	public void setLocal_path(String website_root_path) {
		this.website_root_path = website_root_path;
	}

	public String getLocal_path() {
		return website_root_path;
	}
}
