package nl.dannyarends.www.http.servlets;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.dannyarends.generic.CommandExecutor;
import nl.dannyarends.options.DatabaseOptions;
import nl.dannyarends.options.WebOptions;


/**
 * \brief Small administrator servlet to see the options and test the CGIServlet<br>
 *
 * Small administrator servlet to see the options and test the CGIServlet
 * bugs: none found<br>
 */
public class RplotServlet extends Servlet {
	private static final long serialVersionUID = 9001493560790657038L;
	File f;
	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		OutputStream o = res.getOutputStream();
		res.setContentType("text/html");
		o.write(("<html>" +
				 "<head>" +
				 "	<title>Rplot-page</title>" +
				 "</head>" +
				 "<body>" +
				 "	<form method='post'>").getBytes());
		
		if(req.getParameterValues("GeneName") != null){
    	  String genename = req.getParameterValues("GeneName")[0];
		  o.write(("		<input name='GeneName' value='"+genename+"'></input>" +
				 "		<input name='Submit' type='submit' value='doPlot'></input>" +
				 "	</form>").getBytes());
		  f = new File("websites/homepage/images/R/"+genename+".png");
		  //if(!f.exists()){
		    System.out.println(genename);
		    CommandExecutor c = new CommandExecutor();
		    //c.addCommand("cat plotcode.r | R --slave --args "+genename+" > test.Rout");
		    c.addCommand("cat src/R/plotcode.r | R --slave --args "+genename+" > test.Rout");
		    c.run();
		  //}
		  o.write(( "<img src='/images/R/"+genename+".png' alt='plot'><br>").getBytes());
		  if(new File("test.Rout").exists()){
			  BufferedReader bufRead = new BufferedReader(new FileReader("test.Rout"));
			  String s;
			  while((s = bufRead.readLine())!= null){
				  o.write((s + "<br>").getBytes());
			  }
		  }
		}else{
			  o.write(("		<input name='GeneName' value='AT1G01280'></input>" +
					   "		<input name='Submit' type='submit' value='doPlot'></input>" +
					   "	</form>").getBytes());
		}
		o.write(( "</body>").getBytes());
		o.flush();
		o.close();
	}

}
