package nl.dannyarends.www.http.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.dannyarends.ircclient.IRCClientData;
import nl.dannyarends.ircclient.IRCHandler;
import nl.dannyarends.ircclient.IRCJobStruct;

public class BotServlet extends Servlet{
  private static final long serialVersionUID = 943772779565141864L;
  private int limit = 2;
  private int refresh =10;
  IRCHandler getBotEntryPoint(){
      return (IRCHandler)getServletContext().getAttribute("bot");
  }
  
  private void printJobQueue(OutputStream o,IRCClientData d, int limit) throws IOException{
    if(d.getJobQueue().size() > 0){
      o.write(("<table>").getBytes());
      o.write(("<tr><td><b>ID</b></td><td width=50><b>Stream</b></td><td width=350><b>Command</b></td><td width=120><b>Status</b></td></tr>").getBytes());
      if(limit==0)limit = d.getJobQueue().size();
      for(int jid = d.getJobQueue().size()-1;jid >= 0 && jid >= (d.getJobQueue().size()-limit);jid--){
  	    IRCJobStruct j = d.getJobQueue().get(jid);
        o.write(("<tr><td>"+j.getId()+"</td><td>" + j.printStatus(d.getMyid()) + "</td></tr>").getBytes());
      }
      o.write(("</table>").getBytes());
    }else{
      o.write(("No jobs").getBytes());
    }
  }
  
  @Override
  public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    OutputStream o = res.getOutputStream();
    boolean botinfo = true;
    boolean jobinfo = true;
    int botid = -1;
    int jobid = -1;
    String stream ="";
    try{
        limit = Integer.parseInt(req.getParameter("l"));
    }catch(Exception e){ }
    try{
        refresh = Integer.parseInt(req.getParameter("r"));
    }catch(Exception e){ }
    try{
      botid = Integer.parseInt(req.getParameter("bot"));
    }catch(Exception e){
      botinfo=false;
    }
    try{
      jobid = Integer.parseInt(req.getParameter("id"));
      stream = req.getParameter("stream");
      botinfo=false;
    }catch(Exception e){
      jobinfo=false;
    }
    res.setContentType("text/html");
    if(botinfo){
      o.write(("<html><head><title>BOT Network</title></head><body><h2>Bot: "+botid+"</h2>").getBytes());
      for(IRCClientData d : getBotEntryPoint().getAllConnectedHosts()){
    	if(d.getMyid()==botid){
    		o.write(("Name:" + d.getFullName()+"<br>").getBytes());
    		o.write(("Since:" + d.getStart_time()+"<br>").getBytes());
    		o.write(("Jobs:" + d.getJobQueue().size()+"<br>").getBytes());
    		o.write(("Next:" + d.getFirstQueuedJob()+"<br>").getBytes());
    		printJobQueue(o,d,0);
    	}
      }
      o.write(("</body>").getBytes());
      o.flush();
      o.close();
    }else if(jobinfo){
      o.write(("<html><head><title>BOT Network</title></head><body><h2>Bot: " + botid + ", job: "+jobid + ", "+ stream + "</h2>").getBytes());
      o.write(("</body>").getBytes());
      o.flush();
      o.close();
    }else{
      o.write(("<html><head><title>BOT Network</title><meta http-equiv='refresh' content='"+refresh+";URL=/bot' /></head><body>" +
           "<h2>Bot Network Overview</h2>" +
           "Nr of bots:" + getBotEntryPoint().getAllConnectedHosts().size() + "<br>").getBytes());
      o.write(("<a href='dist/Bot.jar'><font color='green'>Download BOT Jar</font></a><br>").getBytes());
      o.write(("<table>").getBytes());
      o.write(("<tr><td width=150><b>Host</b></td><td width=150><b>Started</b></td><td width=500><b>Jobs</b></td></tr>").getBytes());
      for(IRCClientData d : getBotEntryPoint().getAllConnectedHosts()){
        o.write(("<tr>").getBytes());
        o.write((d.getHTMLString()).getBytes());
        o.write(("<td>").getBytes());
        printJobQueue(o,d,limit);
        o.write(("</td></tr>").getBytes());
      }
      o.write(("</table>").getBytes());
      o.write(("<font size='-1'>Refresh = <a href='?r=5'>5 secs</a> | <a href='?r=10'>10 secs</a> | <a href='?r=60'>1 min</a><br>").getBytes());
      o.write(("Job limit = <a href='?l=2'>2</a> | <a href='?l=5'>5</a> | <a href='?l=10'>10</a></font><br>").getBytes());
      o.write(("</body>").getBytes());
      o.flush();
      o.close();
    }
  }
}
