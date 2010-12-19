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

	
	public MyTimer() {
		timer = new Timer(3000, this);
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		timeholder = new Date(System.currentTimeMillis());
		ServerConnection s = new ServerConnection();
		s.commandToServer("online=true");
		Scene.updateGraphics(Engine.getParentApplet().getGraphics());
		Scene.updateScene();
	}

}
