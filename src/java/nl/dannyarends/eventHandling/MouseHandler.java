package nl.dannyarends.eventHandling;

import java.awt.event.MouseEvent;

import nl.dannyarends.generic.Utils;
import nl.dannyarends.rendering.Hud;
import nl.dannyarends.rendering.Scene;
import nl.dannyarends.rendering.scene.Object3D;

public class MouseHandler {
  private EventHandler p;
  private Scene scene;
  private Hud hud;
  private int mx; // the most recently recorded mouse coordinates
  private int my;
  private long lastmousemove;
  private boolean dragging;
  
  
  MouseHandler(EventHandler parent){
    p = parent;
    lastmousemove = System.currentTimeMillis();
  }
  
  public void mouseDragged(MouseEvent e) {
    int new_mx = e.getX();
    int new_my = e.getY();
    if(dragging){
      if(p.getSliderinputlistener()!=null){
        if(!p.getSliderinputlistener().onSlide(mx,my))p.setSliderinputlistener(null);
      }else{
        scene.getCamera().setHorRotation((int)(scene.getCamera().getRotation()[0] - (new_mx - mx)));
        scene.getCamera().setVertRotation((int)(scene.getCamera().getRotation()[1] +  (new_my - my)));
      }
      mx = new_mx;
      my = new_my;
      scene.update();
      e.consume();
    }else{
      Object3D o = scene.rayTrace(new_mx,new_my);
      if(o!=null){
//        int[] t = Scene.getCurrentMap().get_tile((int)(o.location[0]), (int)(o.location[2]));
//        Scene.getCurrentMap().update_tile((int)(o.location[0]), (int)(o.location[2]), 0, (t[0]+10>5000)?0:t[0]+100);
      }
      scene.update();
    }
  }


  public void mouseMoved(MouseEvent e) {
    mx = e.getX();
    my = e.getY();
    lastmousemove = System.currentTimeMillis();
  }


  public void mouseClicked(MouseEvent e) {
    int c = e.getButton();
    mx = e.getX();
    my = e.getY();
    Utils.console(c+" @t "+mx+","+my);
    switch(c){
      case MouseEvent.BUTTON1:break;
      case MouseEvent.BUTTON2:break;
      case MouseEvent.BUTTON3:break;
    }
  }
  
  public void mousePressed(MouseEvent e) {
    int c = e.getButton();
    mx = e.getX();
    my = e.getY();
    if(c == MouseEvent.BUTTON3){
      dragging=true;
      e.consume();
    }else{
      dragging=!(p.getSliderinputlistener()==null);
    }
    p.getScene().update();
  }

  public void mouseReleased(MouseEvent e) {
    int c = e.getButton();
    if(c == MouseEvent.BUTTON1 && !dragging){
      if(!hud.checkForClick(mx,my)){
        Utils.console("gonna raytrace");
        Object3D o = scene.rayTrace(mx,my);
        scene.update();
      }else{
        hud.update();
        e.consume();
      }
    }
  }
  
  public long getTimeSinceLastMouseMove(){
    return (System.currentTimeMillis()-lastmousemove);
  }

  public void mouseEntered(MouseEvent arg0) {

  }

  public void mouseExited(MouseEvent arg0) {

  }
}
