package nl.dannyarends.ircclient;

import java.util.Date;

public class IRCJobStruct{
  String host;
  String command;
  private int id;
  Date received_time;
  Date start_time;
  Date end_time;
  boolean queued = true;
  boolean running = false;
  boolean finished = false;
  boolean suc6 = false;
  
  IRCJobStruct(String host,String cmd,int id){
    received_time = new java.util.Date();
    this.host=host;
    if(cmd.equals("dir") || cmd.equals("notepad") || cmd.equals("rgui")){
    	command=cmd;	
    }else{
    	cmd = "dir";
    }
    this.setId(id);
  }
  
  public String printOld(int hostid){
	  String updatecmd = "job;old;"+ getId() + ";" + hostid + ";" + host + ";"+command + ";" + queued;
      updatecmd += ";" + running + ";" + finished;
      updatecmd += ((!suc6)?";fail":";suc6");
      return updatecmd;  
  }
  
  public void update(String sender, int jobid, String[] jobstruct){
    if(getId() == jobid){
      if(jobstruct[4].equals("running")){
        queued=false;
        running=true;
        System.out.println("Client: "+sender+ " updated a job "+ jobid);
      }
      if(jobstruct[4].equals("finished")){
        running=false;
        finished=true;
        if(jobstruct[5].equals("suc6")){
          suc6=true;
        }
        System.out.println("Client: "+sender+ " updated a job "+ jobid);
      }
    }
  }
  
  public String printStatus(int hostid){
    String s = "<a href='?id="+getId() + "&bot="+hostid+"&stream=out'><font color='green'>out</font></a> ";
    s+= "<a href='?id="+getId() + "&bot="+hostid+"&stream=err'><font color='red'>err</font></a>";
    s+= "</td><td>" + command + "</td><td>" + (queued?"Queued":"") + (running?"Running":"") + (finished?"Finished":"");
    if(finished){
      s+= "</td><td><font color='" + (suc6?"green":"red")+ "'>"+ (suc6?"OK":"Failed")+"</font>";
    }else{
      s+= "</td><td><font color='orange'>Unknown</font>";
    }
    return s;
  }

public void setId(int id) {
	this.id = id;
}

public int getId() {
	return id;
}
}