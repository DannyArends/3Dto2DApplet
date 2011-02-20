package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import rendering.Scene;


public class MyTimer implements ActionListener{
	Timer timer;
	ServerConnection server;
	MyHandler eventHandler;
	private boolean renderWindowUpdate = false;
	
	public MyTimer(ServerConnection s,MyHandler handler) {
		server=s;
		eventHandler=handler;
		timer = new Timer(250, this);
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(renderWindowUpdate){
			Scene.updateScene(true, true);
		}
		if(eventHandler.getTimeSinceLastMouseMove() > 2500){
			eventHandler.showMouseOver();
		}
	}

	public void setRenderWindowUpdate(boolean u) {
		renderWindowUpdate = u;
	}

	public boolean isRenderWindowUpdate() {
		return renderWindowUpdate;
	}

}
