package rendering;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import events.ServerConnection;

public class MyTimer implements ActionListener{
	Timer timer;
	ServerConnection server;
	private boolean renderWindowUpdate = false;
	
	public MyTimer(ServerConnection s) {
		server=s;
		timer = new Timer(250, this);
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(renderWindowUpdate){
			Scene.updateScene(true, true);
		}
	}

	public void setRenderWindowUpdate(boolean u) {
		renderWindowUpdate = u;
	}

	public boolean isRenderWindowUpdate() {
		return renderWindowUpdate;
	}

}
