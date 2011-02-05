import events.MyHandler;
import events.ServerConnection;
import generic.RenderWindow;
import generic.Utils;

import java.awt.Graphics;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

import rendering.Engine;
import rendering.Scene;
import webserver.WWWServer;



public class DWF extends JFrame implements RenderWindow {
	static String title = "DWF v0.1 with Perl";
	private static final long serialVersionUID = 1L;
	static WWWServer webserver = new WWWServer();
	MyHandler eventListener= new MyHandler(this);
	ServerConnection s = new ServerConnection();
	Engine e; 
	
	DWF(int w, int h,String title){
	    setSize(w,h); 
		setTitle(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	    e = new Engine(this, s);
		addKeyListener(eventListener);
		addMouseListener(eventListener);
		addMouseMotionListener(eventListener);
		Scene.render_3d=true;
		Scene.updateScene(true,true);
	}
	
	public void paintComponent(Graphics g) {
		Scene.updateScene(true,true);
		Scene.updateGraphics(g);
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
		new DWF(800,600,title);
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
}
