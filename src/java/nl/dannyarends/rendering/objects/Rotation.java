package nl.dannyarends.rendering.objects;

public class Rotation {
  private int horizontal = 0;
  private int vertical = 0;
  private double[] r = new double[8];
  
  public Rotation(){
    update();
  }
  
  public void update(){
    double theta = Math.PI * horizontal / 180.0;
    double phi = Math.PI * vertical / 180.0;
    r[0] = Math.cos(theta);
    r[1] = Math.sin(theta);
    r[2] = Math.cos(phi);
    r[3] = Math.sin(phi);
    r[4] = r[0] * r[2]; 
    r[5] = r[0] * r[3];
    r[6] = r[1] * r[2];
    r[7] = r[1] * r[3];
  }
  
  public double[] getRotation(){
    return r;
  }
}
