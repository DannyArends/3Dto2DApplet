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
  
  public EventHandler(RenderWindow w,Engine e){
    window = w;
    engine = e;
   
    client = new NetworkHandler("localhost",2000);
    mouse = new MouseHandler();
    keyboard = new KeyBoardHandler();
    new Thread(client).start();
  }
  
  @Override
  public void mouseDragged(MouseEvent arg0) {
    mouse.mouseDragged(arg0);
  }

  @Override
  public void mouseMoved(MouseEvent arg0) {
    mouse.mouseMoved(arg0);
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {
    mouse.mouseClicked(arg0);
  }

  @Override
  public void mouseEntered(MouseEvent arg0) {
    mouse.mouseEntered(arg0);
  }

  @Override
  public void mouseExited(MouseEvent arg0) {
    mouse.mouseExited(arg0);
  }

  @Override
  public void mousePressed(MouseEvent arg0) {
    mouse.mousePressed(arg0);
  }

  @Override
  public void mouseReleased(MouseEvent arg0) {
    mouse.mouseReleased(arg0);
  }

  @Override
  public void keyPressed(KeyEvent arg0) {
    keyboard.keyTyped(arg0);
  }

  @Override
  public void keyReleased(KeyEvent arg0) {
    keyboard.keyTyped(arg0);
  }

  @Override
  public void keyTyped(KeyEvent arg0) {
    keyboard.keyTyped(arg0);
  }

  public NetworkHandler getClient() {
    return client;
  }

  public void setClient(NetworkHandler c) {
   client=c;
  }
}
