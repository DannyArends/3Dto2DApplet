package nl.dannyarends.eventHandling;

import java.awt.event.KeyEvent;

import nl.dannyarends.generic.Utils;

public class KeyBoardHandler {

  public void keyPressed(KeyEvent e) {
    int c = e.getKeyCode();
    Utils.console("key: "+c + " " + KeyEvent.VK_UP);
    switch(c){
      case KeyEvent.VK_UP:Utils.console("UP");break;
      case KeyEvent.VK_DOWN:Utils.console("DOWN");break;
      case KeyEvent.VK_LEFT:Utils.console("LEFT");break;
      case KeyEvent.VK_RIGHT:Utils.console("RIGHT");break;
      case KeyEvent.VK_PAGE_UP:Utils.console("UP");break;
      case KeyEvent.VK_PAGE_DOWN:Utils.console("DOWN");break;
      case KeyEvent.VK_ESCAPE:System.exit(0);break;
      case KeyEvent.VK_M:Utils.console("Modelonly");break;
      case KeyEvent.VK_EQUALS:Utils.console("+");break;
      case KeyEvent.VK_MINUS:Utils.console("-");break;
      }
    e.consume();
  }

  public void keyReleased(KeyEvent e) {
    int c = e.getKeyCode();
    Utils.console("key: "+c + " " + KeyEvent.VK_UP);
    switch(c){
      case KeyEvent.VK_UP:Utils.console("UP");break;
      case KeyEvent.VK_DOWN:Utils.console("DOWN");break;
      case KeyEvent.VK_LEFT:Utils.console("LEFT");break;
      case KeyEvent.VK_RIGHT:Utils.console("RIGHT");break;
      case KeyEvent.VK_PAGE_UP:Utils.console("UP");break;
      case KeyEvent.VK_PAGE_DOWN:Utils.console("DOWN");break;
      case KeyEvent.VK_ESCAPE:System.exit(0);break;
      case KeyEvent.VK_M:Utils.console("Modelonly");break;
      case KeyEvent.VK_EQUALS:Utils.console("+");break;
      case KeyEvent.VK_MINUS:Utils.console("-");break;
      }
    e.consume();
  }
  
  public void keyTyped(KeyEvent e) {
    int c = e.getKeyCode();
    Utils.console("key: "+c + " " + KeyEvent.VK_UP);
    switch(c){
      case KeyEvent.VK_UP:Utils.console("UP");break;
      case KeyEvent.VK_DOWN:Utils.console("DOWN");break;
      case KeyEvent.VK_LEFT:Utils.console("LEFT");break;
      case KeyEvent.VK_RIGHT:Utils.console("RIGHT");break;
      case KeyEvent.VK_PAGE_UP:Utils.console("UP");break;
      case KeyEvent.VK_PAGE_DOWN:Utils.console("DOWN");break;
      case KeyEvent.VK_ESCAPE:System.exit(0);break;
      case KeyEvent.VK_M:Utils.console("Modelonly");break;
      case KeyEvent.VK_EQUALS:Utils.console("+");break;
      case KeyEvent.VK_MINUS:Utils.console("-");break;
      }
    e.consume();
  }
}
