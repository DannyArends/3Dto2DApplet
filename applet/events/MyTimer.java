package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import rendering.Scene;


public class MyTimer implements ActionListener{
	private Timer timer;
	private ServerConnection server;
	private MyHandler eventHandler;
	private int mouseOverTime = 2000;
	private int pollTime = 250;
	private boolean renderWindowUpdate = false;
	
	public MyTimer(ServerConnection s,MyHandler handler) {
		server = s;
		eventHandler = handler;
		timer = new Timer(pollTime, this);
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(renderWindowUpdate){
			Scene.updateScene(true, true);
		}
		if(eventHandler.getTimeSinceLastMouseMove() > mouseOverTime){
			eventHandler.showMouseOver();
		}
	}

	public void setRenderWindowUpdate(boolean u) {
		renderWindowUpdate = u;
	}

	public boolean isRenderWindowUpdate() {
		return renderWindowUpdate;
	}

	public ServerConnection getServer() {
		return server;
	}

}
