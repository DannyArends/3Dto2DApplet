package rendering;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.Timer;

import events.ServerConnection;

public class MyTimer implements ActionListener{
	Timer timer;
	Date timeholder;
	DateFormat df = DateFormat.getDateInstance();
	DateFormat tf = DateFormat.getTimeInstance();
	ServerConnection server;
	
	public MyTimer(ServerConnection s) {
		server=s;
		timer = new Timer(100, this);
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		timeholder = new Date(System.currentTimeMillis());
		//server.commandToServer("function=online");
		Scene.updateScene(false,false); //So this means, only the ray tracer
		//Scene.updateGraphics(Engine.getParentApplet().getGraphics());
		
	}

}
