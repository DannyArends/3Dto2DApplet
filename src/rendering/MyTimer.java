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
		timer = new Timer(1000, this);
		timer.start();
		server=s;
	}
	
	public void actionPerformed(ActionEvent e) {
		timeholder = new Date(System.currentTimeMillis());
		//server.commandToServer("online=true");
		Scene.updateGraphics(Engine.getParentApplet().getGraphics());
		Scene.updateScene();
	}

}
