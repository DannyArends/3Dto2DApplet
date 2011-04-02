package events;

import generic.Utils;

import java.io.PrintStream;

import javax.servlet.http.HttpServlet;

public abstract class Traceable extends HttpServlet {
	private static final long serialVersionUID = 7830552890641739735L;
	private boolean logEnabled;
	private int priority = 0;
	private PrintStream out = System.out;
	
	public Traceable(){
		
	}
	
	public Traceable(PrintStream to){
		out=to;
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
			if(priority <= p)
			Utils.log(m, out);
		}
	}
}
