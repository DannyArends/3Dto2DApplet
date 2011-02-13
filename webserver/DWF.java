import events.MyHandler;
import events.ServerConnection;
import generic.RenderWindow;
import generic.Utils;

import java.awt.Color;
import java.awt.Graphics;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

import rendering.Engine;
import rendering.Scene;
import webserver.WWWServer;



public class DWF extends JFrame implements RenderWindow,Runnable {
	private static final long serialVersionUID = 1L;
	static String title = "DWF v0.0.1 with CGI support";
	static WWWServer webserver = new WWWServer();
	MyHandler eventListener= new MyHandler(this);
	ServerConnection server = new ServerConnection();
	Engine engine; 
	
	DWF(int w, int h,String title){
	    setSize(w,h); 
		setTitle(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	    server.commandToServer("function=online");
	    engine = new Engine(this, server,eventListener);
	    engine.setRenderWindowUpdate(true);
		addKeyListener(eventListener);
		addMouseListener(eventListener);
		addMouseMotionListener(eventListener);
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(),getHeight());
		Scene.updateScene(true,true);
	}
	
	public void paint(Graphics g) {
		paintComponent(g);
	}
	
	protected void clear(Graphics g) {
		//super.paintComponents(g);
	}
	
	public URL getDocumentBase(){
		return getCodeBase();
	}

	public static void main(String[] args) throws Exception{
		Utils.log("-- Starting webserver --",System.err);
		new Thread(webserver).start();
		Utils.log("-- Serving: http://localhost:8080/ --",System.err);
		new Thread(new DWF(800,600,title));
	}

	@Override
	public void showStatus(String message) {
		setTitle(title + " : "+ message);
	}

	@Override
	public URL getCodeBase() {
		try {
			return new URL("http://localhost:8080/dist/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void run() {
		while(engine==null){

		}
		engine.update(getGraphics());
	}
}
