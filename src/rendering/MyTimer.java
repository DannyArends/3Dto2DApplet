package rendering;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.Timer;

import objects.hud.Text2D;

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
		Scene.updateGraphics(Engine.getParentApplet().getGraphics());
		Scene.updateScene();
	}

}
