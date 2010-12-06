package game;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class Engine {
	private static Image backBuffer;
	private static Graphics backBufferGraphics;
	public static int width;
	public static int height;
	private static Applet parentApplet;
	
	Engine(Applet parent){
		width = parent.getSize().width;
		height = parent.getSize().height;
		backBuffer = parent.createImage(width, height);
		setBackBufferGraphics(backBuffer.getGraphics());
		parentApplet=parent;
	}

	public static Image getBackBuffer() {
		return backBuffer;
	}

	public void setBackBuffer(Image backBuffer) {
		Engine.backBuffer = backBuffer;
	}

	public void setBackBufferGraphics(Graphics backbufferGraphics) {
		Engine.backBufferGraphics = backbufferGraphics;
	}

	public static Graphics getBackBufferGraphics() {
		return backBufferGraphics;
	}

	public void setParentApplet(Applet parentApplet) {
		Engine.parentApplet = parentApplet;
	}

	public static Applet getParentApplet() {
		return parentApplet;
	}
	
	public static int getWidth() {
		return parentApplet.getSize().width;
	}
	
	public static int getHeight() {
		return parentApplet.getSize().height;
	}
}
