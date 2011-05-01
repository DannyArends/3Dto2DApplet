package nl.dannyarends.rendering;

import nl.dannyarends.generic.ColorUtils;
import nl.dannyarends.generic.MathUtils;
import nl.dannyarends.generic.RenderWindow;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.rendering.objects.Intersection;
import nl.dannyarends.rendering.objects.Material;
import nl.dannyarends.rendering.objects.Vector;
import nl.dannyarends.rendering.objects.lighting.Light;
import nl.dannyarends.rendering.scene.Object3D;


/**
 * \brief Basic implementation of a ray tracer<br>
 *
 * Basic implementation of a ray tracer
 * bugs: none found<br>
 */
public class RayTracer {
  RenderWindow window;
  Engine engine;
  Scene scene;
  
  
	public double EPSILON = 0.00000001F;
	public int MAX_REFLECTION_RECURSION_DEPTH = 8;
	static double[] eye= new double[3];
	public static double[] rightDirection = new double[3];	
	static double[] viewplaneUp= new double[3];
	double[] upDirection= new double[]{0,1,0};
	public static double[] direction= new double[3];
	double screenDist= 3;
	double pixelWidth;
	double pixelHeight;
	static int superSampleWidth=4;
	long l1=0;	
	long l0=0;	
	double[][][] raygrid;
	boolean[][] raster;
	int num_pixels;
	int interpolation = 1;
	
	RayTracer(RenderWindow w, Engine e, Scene s){
	  window=w;
	  engine=e;
	  scene=s;
	  
	  pixelWidth = 2.0 / scene.getWidth();
	  pixelHeight = (scene.getWidth() / scene.getHeight()) * pixelWidth;
		l0 = System.nanoTime();
		raster = new boolean[scene.getWidth()][scene.getHeight()];
	}
	
	
	/**
	 * Update the ray tracers parameters used for creating rays
	 * 
	 * @param c Camera
	 * @return
	 */	
	public void update(Object3D c){
		c.update(c);
		raster = new boolean[scene.getWidth()][scene.getHeight()];
		pixelWidth = 2.0 / scene.getWidth();
		pixelHeight = (scene.getWidth() / scene.getHeight()) * pixelWidth;
		eye = c.getLocation();
		direction[0] = -c.getRotation()[6];
		direction[1] = -c.getRotation()[3];
		direction[2] = c.getRotation()[4];
		MathUtils.normalize(direction);
		// Compute a right direction and a view plane up direction (perpendicular to the look-at vector)
		rightDirection = MathUtils.crossProduct(upDirection, direction);
		MathUtils.normalize(rightDirection);
		MathUtils.oppositeVector(rightDirection);
		MathUtils.multiplyVectorByScalar(rightDirection, -1);
		viewplaneUp = MathUtils.crossProduct(rightDirection, direction);		
		MathUtils.normalize(viewplaneUp);
	}
	
	/**
	 * Render rays for 170 ms onto the back buffer
	 * 
	 */	
	public void render(){
		double[] color;
		int hits;
		double[] sampleColor;
		l1 = System.nanoTime();
		int x,y,w,h;
		int pixels = 0;
		int bpixels = 0;
		w = scene.getWidth();
		h = scene.getHeight();
		while((System.nanoTime()-l1)/1000000 < 170){
			y = (int) (Math.random() * h);
			x = (int) (Math.random() * w);
			while(x < w && raster[x][y]){
				x++;
			}
			if(x < w && y < h && !raster[x][y]){
				hits = 0;
				raster[x][y]=true;
				color = new double[3];
				for (int k = 0; k < superSampleWidth; k++) {															
					for (int l = 0; l < superSampleWidth; l++) {					
						sampleColor = null;
						Vector ray = constructRayThroughPixel(x, y, k, l);
						Intersection intersection = findIntersection(ray, null);
						if (intersection.getPrimitive() != null) {
							hits++;
							sampleColor = getColor(ray, intersection, 1);
							MathUtils.addVector(color, sampleColor);
							ray.setMagnitude(intersection.getDistance());																																						
						}
					}					
				}
				if (hits == 0) {
					color = new double[]{0,0,0};		
				}else{
					MathUtils.multiplyVectorByScalar(color, 1F / hits);
				}
				engine.getBackBufferGraphics().setColor(ColorUtils.floatArrayToColor(color));
				engine.getBackBufferGraphics().fillRect(x, y, 1, 1);
				pixels++;
			}else{
				bpixels++;
			}
		}
		num_pixels += pixels;
		//Utils.console("Rendered: "+ pixels + "/"+bpixels+" in " + (System.nanoTime()-l1)/1000000 + " ms");
		//if(((System.nanoTime()-l0)/1000000) % 10==0)Utils.console("avg: "+ num_pixels / ((System.nanoTime()-l0)/1000000) + " pix/ms");
	}

	void doLineairInterpolation(){
		int py = 0;
		for(int y=0;py+interpolation<scene.getHeight();y+=interpolation/3){
			int px=0;
			for(int x=0;px+interpolation<scene.getWidth();x+=interpolation/3){
				//Utils.console("("+ x + "," + y +")=" + px +" "+ (px + 10) + " " + py +" "+ (py + 10));
				if(!(px == x && py == y)){
				raygrid[x][y]= interpretcolors(
						raygrid[px][py],
						raygrid[px+interpolation][py],
						raygrid[px][py+interpolation],
						raygrid[px+interpolation][py+interpolation],
						(px+interpolation)-x + (py+interpolation)-y, 
						x-px + (py+interpolation)-y,
						(px+interpolation)-x + y-py,
						x-px + y-py);
				}
				try{
				engine.getBackBufferGraphics().setColor(ColorUtils.floatArrayToColor(raygrid[x][y]));
				}catch(Exception e){
					Utils.console(""+x+ " "+y +  " " +raygrid[x][y][0]+  " " +raygrid[x][y][1] +  " "+raygrid[x][y][2]);
				}
				engine.getBackBufferGraphics().fillRect(x, y, 3, 3);
				px = (int)(Math.floor(x/interpolation) * interpolation);
			}
			py = (int)(Math.floor(y/interpolation) * interpolation);
		}
		Utils.console("Rendered one image: " + (System.nanoTime()-l1)/1000000 + " ms");
	}
	
	double[] interpretcolors(double[] c1, double[] c2,double[] c3, double[] c4,int d1, int d2,int d3, int d4){
		double denom= d1+d2+d3+d4;
		return new double[]{(d1*c1[0] + d2*c2[0] + d3*c3[0] + d4*c4[0])/denom,(d1*c1[1] + d2*c2[1] + d3*c3[1] + d4*c4[1])/denom,(d1*c1[2] + d2*c2[2] + d3*c3[2] + d4*c4[2])/denom};
	}

	/**
	 * Construct a ray projecting into the scene
	 * 
	 * @param x x-pixel location to start the ray from
	 * @param y y-pixel location to start the ray from
	 * @param sampleXOffset sampling offset in x direction of the ray
	 * @param sampleYOffset sampling offset in y direction of the ray
	 * @return
	 */	
	public Vector constructRayThroughPixel(int x, int y, double sampleXOffset, double sampleYOffset){										 																
		Vector ray = new Vector(eye, direction, screenDist);
		double[] endPoint = ray.getEndPoint();		
		
		double upOffset = -1 * (y - (scene.getHeight() / 2) - (sampleYOffset / superSampleWidth)) * pixelHeight;
		double rightOffset = (x - (scene.getWidth() / 2) + (sampleXOffset / superSampleWidth)) * pixelWidth;
		
		MathUtils.addVectorAndMultiply(endPoint, rightDirection, rightOffset);
		MathUtils.addVectorAndMultiply(endPoint, viewplaneUp, upOffset);
		
		ray.setDirection(MathUtils.calcPointsDiff(eye, endPoint));						
		ray.normalize();
		return ray;
	}
	
	/**
	 * Find an intersection with an object in scene
	 * 
	 * @param ray ray into scene
	 * @param ignorePrimitive ignore this primitive
	 * @return
	 */	
	public Intersection findIntersection(Vector ray, Object3D ignorePrimitive){
		// Start off with infinite distance and no intersecting primitive
		double minDistance = Double.POSITIVE_INFINITY;
		Object3D minPrimitive = null;
		
		for(Object3D primitive : scene.getObjects()){
			if(primitive !=null){
				double t = primitive.intersect(ray);
				// If we found a closer intersecting primitive, keep a reference to and it
				if (t < minDistance && t > EPSILON && primitive != ignorePrimitive){
					minPrimitive = primitive;
					minDistance = t;
				}
			}
		}
		return new Intersection(minDistance, minPrimitive);
	}
	
	/**
	 * Returns the color of this ray on an object in a single recursion step (ambient, specular, diffuse)
	 * 
	 * @param ray ray into scene
	 * @param intersection Intersection location on the object
	 * @param recursionDepth Depth of recursion of this ray (used in reflection) 
	 * @return double[] color in RGB float [0..1]
	 */	
	public double[] getColor(Vector ray, Intersection intersection, int recursionDepth){
		// Avoid infinite loops and help performance by limiting the recursion depth
		if (recursionDepth > MAX_REFLECTION_RECURSION_DEPTH){
			return new double[]{0.0,0.0,1.0};
		}
		Object3D primitive = intersection.getPrimitive();
		if (primitive == null){
			return ColorUtils.ColorToDouble(scene.bgcolor);
		}
		double[] pointOfIntersection = ray.getEndPoint();
		
		Material surface = primitive.getMaterialAt(pointOfIntersection);			
		
		double[] color = new double[]{0,0,0};		
		double[] specular = surface.getSpecular();
				
		// Stretch the ray to the point of intersection
		ray.setMagnitude(intersection.getDistance());
		
		double[] diffuse = primitive.getColorAt(pointOfIntersection);
		
		if (diffuse==null) Utils.log("No diffuse color on object",System.err);
		
		// Stretch the ray to the point of intersection - 1 to we can get a viewing vector
		ray.setMagnitude(intersection.getDistance() - 1);
								
		// Obtain the normal at the point of intersection
		double[] normal = primitive.getNormalAt(pointOfIntersection);
		
		// Shoot rays towards each light source and see if it's visible
		for (Light light: scene.getLights()) {
			double[] vectorToLight = light.getVectorToLight(pointOfIntersection);			
			
			Vector rayToLight = new Vector(pointOfIntersection, vectorToLight, 1);
			rayToLight.normalize();
			
			// Light is visible if there's no intersection with an object at least epsilon away			
			double distanceToBlockingPrimitive = findIntersection(rayToLight, null).getDistance();
			double distanceToLight = MathUtils.norm(MathUtils.calcPointsDiff(pointOfIntersection, light.l));
			//Utils.console("Distance to light:" + distanceToLight + "loc x: " + pointOfIntersection[0] +",y: " + pointOfIntersection[1] + ",z: " + pointOfIntersection[2]);
			boolean lightVisible = distanceToBlockingPrimitive <= EPSILON || distanceToBlockingPrimitive >= distanceToLight;
			if (lightVisible) {
				// Measure the distance to the light and find the amount of light hitting the primitive				
				double[] amountOfLightAtIntersection = light.getAmountOfLight(pointOfIntersection);								
				
				// The amount of light visible on the surface, determined by the angle to the light source
				double visibleDiffuseLight = MathUtils.dotProduct(vectorToLight, normal);	
				if (visibleDiffuseLight > 0) {
					// Diffuse
					color[0] += diffuse[0] * amountOfLightAtIntersection[0] * visibleDiffuseLight;
					color[1] += diffuse[1] * amountOfLightAtIntersection[1] * visibleDiffuseLight;
					color[2] += diffuse[2] * amountOfLightAtIntersection[2] * visibleDiffuseLight;
				}
				// Specular light : The reflection around the normal 
				double[] reflectedVectorToLight = MathUtils.reflectVector(vectorToLight, normal);																			 			
				MathUtils.normalize(reflectedVectorToLight);													
				double visibleSpecularLight = MathUtils.dotProduct(reflectedVectorToLight, ray.getDirection());
				if (visibleSpecularLight < 0) {
					visibleSpecularLight = Math.pow(Math.abs(visibleSpecularLight), surface.getShininess());
					color[0] += specular[0] * amountOfLightAtIntersection[0] * visibleSpecularLight;
					color[1] += specular[1] * amountOfLightAtIntersection[1] * visibleSpecularLight;
					color[2] += specular[2] * amountOfLightAtIntersection[2] * visibleSpecularLight;
				}
			}else{
				//Utils.console("No light source");
			}
		}								
		
		// Ambient		
		double[] sceneAmbient = scene.getAmbientLight(); 
		double[] surfaceAmbient = surface.getAmbient();
		
		color[0] += sceneAmbient[0] * surfaceAmbient[0]; 		
		color[1] += sceneAmbient[1] * surfaceAmbient[1];
		color[2] += sceneAmbient[2] * surfaceAmbient[2];
		
		// Emission
		double[] surfaceEmission = surface.getEmission();
		
		color[0] += surfaceEmission[0];
		color[1] += surfaceEmission[1];
		color[2] += surfaceEmission[2];		
		
		// Reflection Ray
		double[] reflectionDirection = MathUtils.reflectVector(MathUtils.oppositeVector(ray.getDirection()), normal);
		Vector reflectionRay = new Vector(pointOfIntersection, reflectionDirection, 1);
		reflectionRay.normalize();
		
		Intersection reflectionIntersection = findIntersection(reflectionRay, null);		
		double[] reflectionColor = getColor(reflectionRay, reflectionIntersection, recursionDepth + 1);								
		
		MathUtils.addVectorAndMultiply(color, reflectionColor, surface.getReflectance());				
		
		return color;
	}
}
