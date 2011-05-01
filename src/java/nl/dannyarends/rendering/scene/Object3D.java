package nl.dannyarends.rendering.scene;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

import nl.dannyarends.generic.MathUtils;
import nl.dannyarends.rendering.Engine;
import nl.dannyarends.rendering.interfaces.Renderable;
import nl.dannyarends.rendering.objects.Edge;
import nl.dannyarends.rendering.objects.Location;
import nl.dannyarends.rendering.objects.Material;
import nl.dannyarends.rendering.objects.Rotation;
import nl.dannyarends.rendering.objects.Vector;

public class Object3D implements Renderable{
  Engine engine;
  private Location location = new Location();
  private Rotation rotation = new Rotation();
  
  private Location[] vertices = null;
  private Edge[] edges = null;
  
  private int[][] points;
  
  private double scale;
  
  
  Object3D(Engine e){
    engine=e;
  }
  
  public void update(Object3D camera){
    rotation.update();
    int width = (int) engine.getWindow().getSize().getWidth();
    int height = (int) engine.getWindow().getSize().getHeight();
    int scaleFactor_w = (int) ((width / 1.90));
    int scaleFactor_h = (int) ((height / 1.40));
    if(vertices != null){
      int[][] points = new int[vertices.length][2];
      int vertex = 0;
      for(Location l : vertices) {
        l.multiply(scale);
        l.add(location);
        double[] d = l.computeOrtogonalProjection(rotation);
        d = l.computeOrtogonalProjection(MathUtils.calcPointsDiff(camera.location.l,d),camera.rotation);
        d = l.computePerspectiveProjection(d);
        points[vertex] = new int[]{(int)(width/2 - scaleFactor_w * d[0]),(int)(height/2  - scaleFactor_h * d[1])};
      }
    }
  }
  
  @Override
  public void render(Graphics2D g) {
    GeneralPath path = null;
    g.setPaint(Color.green);
    for(int j=0; j < (edges.length-1);j+=3){
      if(points[edges[j].a] != null && points[edges[j+1].a] != null && points[edges[j+2].a] != null){
      if(points[edges[j].b] != null && points[edges[j+1].b] != null && points[edges[j+2].b] != null){
        path = new GeneralPath(GeneralPath.WIND_NON_ZERO);
        path.moveTo(points[edges[j].a][0], points[edges[j].a][1]);
        path.lineTo(points[edges[j].a][0], points[edges[j].a][1]);
        path.lineTo(points[edges[j].b][0], points[edges[j].b][1]);
        path.lineTo(points[edges[j+1].a][0], points[edges[j+1].a][1]);
        path.lineTo(points[edges[j+1].b][0], points[edges[j+1].b][1]);
        path.lineTo(points[edges[j+2].a][0], points[edges[j+2].a][1]);
        path.lineTo(points[edges[j+2].b][0], points[edges[j+2].b][1]);
        path.closePath();
        g.draw(path);
        g.fill(path);  
      }
      }
    }
  }

  public double[] getLocation() {
    return location.l;
  }

  public double[] getRotation() {
    return rotation.getRotation();
  }

  public double intersect(Vector ray) {
    return 0;
  }

  public Material getMaterialAt(double[] pointOfIntersection) {
    return null;
  }
  
  public double[] getNormalAt(double[] pointOfIntersection) {
    return null;
  }

  public double[] getColorAt(double[] pointOfIntersection) {
    return null;
  }


}
