// FileServlet - servlet similar to a standard httpd
//
// Copyright (C)1996,1998 by Jef Poskanzer <jef@acme.com>. All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
// 1. Redistributions of source code must retain the above copyright
//    notice, this list of conditions and the following disclaimer.
// 2. Redistributions in binary form must reproduce the above copyright
//    notice, this list of conditions and the following disclaimer in the
//    documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND
// ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
// FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
// OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
// HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
// LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
// OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE.
//
// Visit the ACME Labs Java page for up-to-date versions of this and other
// fine Java utilities: http://www.acme.com/java/
// 
// All enhancements Copyright (C)1998-2005 by Dmitriy Rogatkin
// http://tjws.sourceforge.net

package nl.dannyarends.www.http.servlets;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.dannyarends.generic.Utils;

/**
 * \brief Servlet similar to a standard httpd.<br>
 *
 * Implements the "GET" and "HEAD" methods for files and directories.Handles index.html, 
 * index.htm, default.htm, default.html. Redirects directory URLs that lack a trailing /.
 * Handles If-Modified-Since. Modified from: Acme.Serve.Serve
 * 
 * bugs: none found<br>
 */
public class FileServlet extends Servlet {
	private static final long serialVersionUID = 1L;
	public static final String DEF_USE_COMPRESSION = "tjws.fileservlet.usecompression";
	static final String[] DEFAULTINDEXPAGES = { "index.html", "index.htm", "default.htm", "default.html" };
	static final DecimalFormat lengthftm = new DecimalFormat("#");
	private Method canExecute, getFreeSpace;


	// / Constructor.
	public FileServlet() {
		super();
		try {
			canExecute = File.class.getMethod("canExecute", Utils.EMPTY_CLASSES);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}
		try {
			getFreeSpace = File.class.getMethod("getFreeSpace", Utils.EMPTY_CLASSES);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}
		setUseCompression(System.getProperty(DEF_USE_COMPRESSION) != null);
	}
	
	// / Constructor.
	public FileServlet(String path) {
		this();
		setLocal_path(getLocal_path() + "/" + path);
	}

	// / Returns a string containing information about the author, version, and
	// copyright of the servlet.
	public String getServletInfo() {
		return "File servlet similar to httpd";
	}

	// / Services a single request from the client.
	// @param req the servlet request
	// @param req the servlet response
	// @exception ServletException when an exception has occurred
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		boolean headOnly;
		if (req.getMethod().equalsIgnoreCase("get") || req.getAttribute("javax.servlet.forward.request_uri") != null
				|| req.getAttribute("javax.servlet.include.request_uri") != null)
			headOnly = false;
		else if (req.getMethod().equalsIgnoreCase("head"))
			headOnly = true;
		else {
			res.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}
		req.setCharacterEncoding(getCharSet());
		String path = Utils.canonicalizePath(req.getPathInfo());
		if(!getLocal_path().equals("")){
			path = getLocal_path() + "/" + path;
		}
		dispatchPathname(req, res, headOnly, path);
	}

	private void dispatchPathname(HttpServletRequest req, HttpServletResponse res, boolean headOnly, String path) throws IOException {
		String filename = path != null ? path.replace('/', File.separatorChar) : "";
		File file = new File(filename);
		trace(0,"retrieving '" + filename + "' for path " + path);
		if (file.exists()) {
			if (!file.isDirectory()){
				serveFile(req, res, headOnly, file);
				trace("Served file: " + file + " " +  file.length()/1024 + " Kb");
			} else {
				trace("Showing dir: " + file);
				if (redirectDirectory(req, res, path, file) == false){
					showIdexFile(req, res, headOnly, path, filename);
				}
			}
		} else{
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
			
	}

	private void showIdexFile(HttpServletRequest req, HttpServletResponse res, boolean headOnly, String path,
			String parent) throws IOException {
		if (isLogEnabled()) Utils.console("Showing index in directory " + parent);
		for (int i = 0; i < DEFAULTINDEXPAGES.length; i++) {
			File indexFile = new File(parent, DEFAULTINDEXPAGES[i]);
			if (indexFile.exists()) {
				serveFile(req, res, headOnly, indexFile);
				return;
			}
		}
		// index not found
		serveDirectory(req, res, headOnly, path, new File(parent));
	}



	private void serveDirectory(HttpServletRequest req, HttpServletResponse res, boolean headOnly, String path,	File file) throws IOException {
		if (isLogEnabled()) Utils.console("Indexing directory: " + file);
		if (!file.canRead()) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		res.setStatus(HttpServletResponse.SC_OK);
		res.setContentType("text/html;charset=" + getCharSet());
		OutputStream out = res.getOutputStream();
		Long freespace =(long) 0;
		if (!headOnly) {
			String[] names = file.list();
			if (names == null) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN, "Can't access " + req.getRequestURI());
				return;
			}
			PrintStream p = new PrintStream(new BufferedOutputStream(out), false, getCharSet()); // 1.4
			p.println("<html><head>");
			p.println("<meta HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=" + getCharSet() + "\">");
			p.println("<title>Index of " + path + "</title>");
			p.println("<link rel=\"stylesheet\" type=\"text/css\" href=\""+req.getServletPath()+"/themes/default.css\"></style>");
			p.println("</head><body>");
			p.println("<div align=\"center\"><h1>Index of " + path + "</h1></div>");
			p.println("<pre>");
			p.println("mode         bytes  last-changed    name");
			p.println("<HR>");
			Arrays.sort(names);
			long total = 0;
			for (int i = 0; i < names.length; ++i) {
				File aFile = new File(file, names[i]);
				String aFileType;
				long aFileLen;
				if (aFile.isDirectory())
					aFileType = "d";
				else if (aFile.isFile())
					aFileType = "-";
				else
					aFileType = "?";
				String aFileRead = (aFile.canRead() ? "r" : "-");
				String aFileWrite = (aFile.canWrite() ? "w" : "-");
				String aFileExe = "-";
				if (canExecute != null)
					try {
						if (((Boolean) canExecute.invoke(aFile, Utils.EMPTY_OBJECTS)).booleanValue())
							aFileExe = "x";
					} catch (IllegalArgumentException e) {
					} catch (IllegalAccessException e) {
					} catch (InvocationTargetException e) {
					}
				if(getFreeSpace != null)
					try {
						freespace = ((Long) getFreeSpace.invoke(aFile, Utils.EMPTY_OBJECTS)).longValue() / 1024;
					} catch (IllegalArgumentException e) {
					} catch (IllegalAccessException e) {
					} catch (InvocationTargetException e) {
					}
				String aFileSize = lengthftm.format(aFileLen = aFile.length());
				total += Math.round(((aFileLen) + 1023) / 1024); // 
				while (aFileSize.length() < 12)
					aFileSize = " " + aFileSize;
				String aFileDate = Utils.lsDateStr(new Date(aFile.lastModified()));
				while (aFileDate.length() < 14)
					aFileDate += " ";
				String aFileDirsuf = (aFile.isDirectory() ? "/" : "");
				String aFileSuf = (aFile.isDirectory() ? "/" : "");
				p.println(aFileType + aFileRead + aFileWrite + aFileExe + "  " + aFileSize + "  " + aFileDate + "  "
						+ "<a href=\"" + URLEncoder.encode(names[i], getCharSet()) /* 1.4 */
						+ aFileDirsuf + "\"><font color=\"black\">" + names[i] + aFileSuf + "</font></a>");
			}
			p.println("Used: " + total + " KB of " + (freespace/1024) + " MB");
			p.println("</pre>");
			p.println("<hr><ul><li><a href=\"/\"><font color=\"black\">Back to index</font></a></li></ul>");
			p.println("</body></html>");
			p.flush();
		}
		out.close();
	}

	/**
	 * 
	 * @param req
	 *            http request
	 * @param res
	 *            http response
	 * @param path
	 *            web path
	 * @param file
	 *            file system path
	 * @return true if redirection required and happened
	 * @throws IOException
	 *             in redirection
	 */
	private boolean redirectDirectory(HttpServletRequest req, HttpServletResponse res, String path, File file)
			throws IOException {
		int pl = path.length();
		if (pl > 0 && path.charAt(pl - 1) != '/') {
			// relative redirect
			int sp = path.lastIndexOf('/');
			if (sp < 0)
				path += '/';
			else
				path = path.substring(sp + 1) + '/';
			if (isLogEnabled()) Utils.console("Redirecting dir " + path);
			res.sendRedirect(path);
			return true;
		}
		return false;
	}
}