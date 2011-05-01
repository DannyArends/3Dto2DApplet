package nl.dannyarends.eventHandling;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import nl.dannyarends.generic.RenderWindow;
import nl.dannyarends.rendering.Engine;

public class EventHandler implements KeyListener, MouseListener,MouseMotionListener{
  private RenderWindow window;
  private Engine engine;
  
  private NetworkHandler client;
  private MouseHandler mouse;
  private KeyBoardHandler keyboard;
  
  EventHandler(Engine e,RenderWindow w){
    engine = e;
    window = w;
    
    client = new NetworkHandler("localhost",2000);
    new Thread(client).start();
  }
  
  @Override
  public void mouseDragged(MouseEvent arg0) {

  }

  @Override
  public void mouseMoved(MouseEvent arg0) {

  }

  @Override
  public void mouseClicked(MouseEvent arg0) {

  }

  @Override
  public void mouseEntered(MouseEvent arg0) {

  }

  @Override
  public void mouseExited(MouseEvent arg0) {

  }

  @Override
  public void mousePressed(MouseEvent arg0) {

  }

  @Override
  public void mouseReleased(MouseEvent arg0) {

  }

  @Override
  public void keyPressed(KeyEvent arg0) {

  }

  @Override
  public void keyReleased(KeyEvent arg0) {

  }

  @Override
  public void keyTyped(KeyEvent arg0) {

  }

  public NetworkHandler getClient() {
    return client;
  }

  public void setClient(NetworkHandler c) {
   client=c;
  }
}
