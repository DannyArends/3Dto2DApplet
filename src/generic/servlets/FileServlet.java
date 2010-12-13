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

package generic.servlets;

import generic.Utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/// Servlet similar to a standard httpd.
// <P>
// Implements the "GET" and "HEAD" methods for files and directories.
// Handles index.html, index.htm, default.htm, default.html.
// Redirects directory URLs that lack a trailing /.
// Handles If-Modified-Since.
// <P>
// <A HREF="/resources/classes/Acme/Serve/FileServlet.java">Fetch the software.</A><BR>
// <A HREF="/resources/classes/Acme.tar.Z">Fetch the entire Acme package.</A>
// <P>
// @see Acme.Serve.Serve

public class FileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String DEF_USE_COMPRESSION = "tjws.fileservlet.usecompression";

	static final String[] DEFAULTINDEXPAGES = { "index.html", "index.htm", "default.htm", "default.html" };

	static final DecimalFormat lengthftm = new DecimalFormat("#");

	static final String BYTES_UNIT = "bytes";

	private static final boolean logenabled = false;

	//	 true;

	private Method canExecute, getFreeSpace;

	private boolean useCompression;

	// / Constructor.
	public FileServlet() {
		super();
		setCharSet("UTF8");
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
		useCompression = System.getProperty(DEF_USE_COMPRESSION) != null;
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
		dispatchPathname(req, res, headOnly, path);
	}
	
	private String charSet;

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public String getCharSet() {
		return charSet;
	}

	private void dispatchPathname(HttpServletRequest req, HttpServletResponse res, boolean headOnly, String path)
			throws IOException {
		//Utils.console("Canonical path: " + path + " from " + req.getPathInfo() + " translated: " + req.getPathTranslated());
		String filename = req.getPathTranslated() != null ? req.getPathTranslated().replace('/', File.separatorChar) : "";
		File file = new File(filename);
		if (logenabled) Utils.console("retrieving '" + filename + "' for path " + path);
		if (file.exists()) {
			if (!file.isDirectory())
				serveFile(req, res, headOnly, path, file);
			else {
				if (logenabled) Utils.console("showing dir " + file);
				if (redirectDirectory(req, res, path, file) == false)
					showIdexFile(req, res, headOnly, path, filename);
			}
		} else
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	private void showIdexFile(HttpServletRequest req, HttpServletResponse res, boolean headOnly, String path,
			String parent) throws IOException {
		if (logenabled) Utils.console("Showing index in directory " + parent);
		for (int i = 0; i < DEFAULTINDEXPAGES.length; i++) {
			File indexFile = new File(parent, DEFAULTINDEXPAGES[i]);
			if (indexFile.exists()) {
				serveFile(req, res, headOnly, path, indexFile);
				return;
			}
		}
		// index not found
		serveDirectory(req, res, headOnly, path, new File(parent));
	}

	private void serveFile(HttpServletRequest req, HttpServletResponse res, boolean headOnly, String path, File file)
			throws IOException {
		if (logenabled) {
			Utils.console("Getting " + file);
			Enumeration<?> enh = req.getHeaderNames();
			while (enh.hasMoreElements()) {
				String hn = (String) enh.nextElement();
				Utils.console("hdr:" + hn + ":" + req.getHeader(hn));
			}
		}
		if (!file.canRead()) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		} else{
			try {
				file.getCanonicalPath();
			} catch (Exception e) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden, exception:" + e);
				return;
			}
		}
		// Handle If-Modified-Since.
		res.setStatus(HttpServletResponse.SC_OK);
		long lastMod = file.lastModified();
		long ifModSince = req.getDateHeader("If-Modified-Since");
		if (ifModSince != -1 && ifModSince >= lastMod) {
			res.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			headOnly = true;
		}
		// TODO add processing If-None-Match, If-Unmodified-Since and If-Match
		String contentType = getServletContext().getMimeType(file.getName());
		if (contentType != null) res.setContentType(contentType);
		long flen = file.length();
		String range = req.getHeader("Range");
		long sr = 0;
		long er = -1;
		if (range != null) {
			if (logenabled) Utils.console("Range:" + range);
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
			if (logenabled) Utils.console("range values " + sr + " to " + er);
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
			if (logenabled) Utils.console("content-range:" + BYTES_UNIT + " " + sr + '-' + er + '/' + flen);
		}
		// String ifRange = req.getHeader("If-Range");
		// res.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
		boolean doCompress = false;
		if (useCompression && contentType != null && contentType.startsWith("text")) {
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
					if (sl > 0)
						sr -= sl;
					else {
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
			}
		}
	}

	private void serveDirectory(HttpServletRequest req, HttpServletResponse res, boolean headOnly, String path,	File file) throws IOException {
		if (logenabled) Utils.console("Indexing directory: " + file);
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
			p.println("<HTML><HEAD>");
			p.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=" + getCharSet() + "\">");
			p.println("<TITLE>Index of " + path + "</TITLE>");
			p.println("</HEAD><BODY>");
			p.println("<H2>Index of " + path + "</H2>");
			p.println("<PRE>");
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
				String aFileDate = generic.Utils.lsDateStr(new Date(aFile.lastModified()));
				while (aFileDate.length() < 14)
					aFileDate += " ";
				String aFileDirsuf = (aFile.isDirectory() ? "/" : "");
				String aFileSuf = (aFile.isDirectory() ? "/" : "");
				p.println(aFileType + aFileRead + aFileWrite + aFileExe + "  " + aFileSize + "  " + aFileDate + "  "
						+ "<A HREF=\"" + URLEncoder.encode(names[i], getCharSet()) /* 1.4 */
						+ aFileDirsuf + "\">" + names[i] + aFileSuf + "</A>");
			}
			p.println("Used: " + total + " KB of " + (freespace/1024) + " MB");
			p.println("</PRE>");
			p.println("<HR>");
			p.println("</BODY></HTML>");
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
			if (logenabled) Utils.console("redirecting dir " + path);
			res.sendRedirect(path);
			return true;
		}
		return false;
	}
}