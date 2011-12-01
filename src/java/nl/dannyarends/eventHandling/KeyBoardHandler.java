package nl.dannyarends.eventHandling;

import java.awt.event.KeyEvent;

import nl.dannyarends.generic.Utils;

public class KeyBoardHandler {
  private EventHandler p;
  
  KeyBoardHandler(EventHandler parent){
    p = parent;
  }

  public void keyPressed(KeyEvent e) {
    int c = e.getKeyCode();
    Utils.console("key: " + c + "pressed");
    e.consume();
  }

  public void keyReleased(KeyEvent e) {
    int c = e.getKeyCode();
    Utils.console("key: " + c + "released");
    e.consume();
  }
  
  public void keyTyped(KeyEvent e) {
    if(p.getKeyinputlistener()!=null)p.getKeyinputlistener().onKey(e.getKeyChar());
    e.consume();
  }
  
}
