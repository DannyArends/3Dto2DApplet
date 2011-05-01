package nl.dannyarends.eventHandling;

import java.awt.event.MouseEvent;

import nl.dannyarends.generic.Utils;

public class MouseHandler {

  public void mouseDragged(MouseEvent arg0) {

  }


  public void mouseMoved(MouseEvent arg0) {

  }


  public void mouseClicked(MouseEvent e) {
    int c = e.getButton();
    int mx = e.getX();
    int my = e.getY();
    Utils.console(c+" @t "+mx+","+my);
    switch(c){
      case MouseEvent.BUTTON1:break;
      case MouseEvent.BUTTON2:break;
      case MouseEvent.BUTTON3:break;
    }
  }
  
  public void mousePressed(MouseEvent e) {

  }

  public void mouseReleased(MouseEvent e) {

  }


  public void mouseEntered(MouseEvent arg0) {

  }

  public void mouseExited(MouseEvent arg0) {

  }
}
