package nl.dannyarends.rendering.objects;

public class Location {
  private static double near = 3.0f;
  private static double nearToObj = 0.1f;
  public double[] l = new double[3];

  public void multiply(double scale) {
    l[0] *= scale;
    l[1] *= scale;
    l[2] *= scale;
  }

  public void add(Location location) {
    l[0] += location.l[0];
    l[1] += location.l[1];
    l[2] += location.l[2];
  }
  
  public double[] computeOrtogonalProjection(Rotation r){
    return computeOrtogonalProjection(l,r);
  }
  
  public double[] computeOrtogonalProjection(double[] loc, Rotation r){
    double[] rotation = r.getRotation();
    double[] t = new double[3];
    t[0] = rotation[0] * loc[0] + rotation[1] * loc[2];
    t[1] = -rotation[7] * loc[0] + rotation[2] * loc[1] + rotation[5] * loc[2];
    t[2] = rotation[4] * loc[2] - rotation[6] * loc[0] - rotation[3] * loc[1];
    return t;
  }
  
  public double[] computePerspectiveProjection(double[] x){
    double[] d = new double[2];
    double t = near / (x[2] + near + nearToObj);
    d[0] = x[0] * t;
    d[1] = x[1] * t;
    return d;
  }

}
