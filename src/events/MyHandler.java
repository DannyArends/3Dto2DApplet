package events;

import game.Scene;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MyHandler implements MouseMotionListener{
	int mx, my; // the most recently recorded mouse coordinates
	
	public MyHandler(){

	}
	
	public void mouseEntered(MouseEvent e) {
	
	}

	public void mouseExited(MouseEvent e) {
	
	}

	public void mouseClicked(MouseEvent e) {
	
	}

	public void mousePressed(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		// get the latest mouse position
		int new_mx = e.getX();
		int new_my = e.getY();

		// since the last event
		Scene.getCamera().setHorizontalRotation(Scene.getCamera().getHorizontalRotation() - (new_mx - mx));
		Scene.getCamera().setVerticalRotation(Scene.getCamera().getVerticalRotation() + (new_my - my));
		// update our data
		mx = new_mx;
		my = new_my;

		Scene.getParentApplet().repaint();
		e.consume();
	}
	
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
	    switch(c){
	    	case KeyEvent.VK_UP:System.out.print("UP\n");Scene.getCamera().move(1,0,0);break;
	    	case KeyEvent.VK_DOWN:System.out.print("DOWN\n");Scene.getCamera().move(-1,0,0);break;
	    	case KeyEvent.VK_LEFT:System.out.print("LEFT\n");Scene.getCamera().move(0,0,1);break;
	    	case KeyEvent.VK_RIGHT:System.out.print("RIGHT\n");Scene.getCamera().move(0,0,-1);break;
	    	case KeyEvent.VK_PAGE_UP:System.out.print("RIGHT\n");Scene.getCamera().move(0,1,0);break;
	    	case KeyEvent.VK_PAGE_DOWN:System.out.print("RIGHT\n");Scene.getCamera().move(0,-1,0);break;
	    	case KeyEvent.VK_ESCAPE:System.exit(0);break;
	      }
		Scene.getParentApplet().repaint();
	}

	public void keyTyped(KeyEvent e) {
	      char c = e.getKeyChar();
	      if(c != KeyEvent.CHAR_UNDEFINED ) {
	         System.out.print(c+"\n");
		  }
	}

}
