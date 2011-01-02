package rendering;

import generic.ColorUtils;
import generic.MathUtils;
import generic.Utils;
import objects.Camera;
import objects.Material3DS;
import objects.Vector3D;
import objects.renderables.Object3D;
import objects.renderables.light.Light;

public class RayTracer {
	public final double EPSILON = 0.00000001F;
	public final int MAX_REFLECTION_RECURSION_DEPTH = 8;
	// These are some of the camera's properties for easy (fast) access
	double[] eye= new double[3];
	double[] rightDirection = new double[3];	
	double[] viewplaneUp= new double[3];
	double[] upDirection= new double[]{0,1,0};
	double[] direction= new double[3];
	double screenDist= 3;
	double pixelWidth = 2.0 / Engine.getWidth();
	double pixelHeight = (Engine.getWidth() / Engine.getHeight()) * pixelWidth;
	int superSampleWidth=3;
	boolean[] raster;
	long l1=0;
	long l2=0;
	long l3=0;
	
	public void update(Camera c){
		c.update(c);
		raster = new boolean[(Engine.getWidth() * Engine.getHeight())];
		eye = c.location;
		direction[0] = -c.rotation[6];
		direction[1] = -c.rotation[3];
		direction[2] = c.rotation[4];
		MathUtils.normalize(direction);
		// Compute a right direction and a view plane up direction (perpendicular to the look-at vector)
		rightDirection = MathUtils.crossProduct(upDirection, direction);
		MathUtils.normalize(rightDirection);
		MathUtils.oppositeVector(rightDirection);
		MathUtils.multiplyVectorByScalar(rightDirection, -1);
		viewplaneUp = MathUtils.crossProduct(rightDirection, direction);		
		MathUtils.normalize(viewplaneUp);	
		for(int i = 0; i < (Engine.getHeight()*Engine.getWidth()); i+=(Math.random()* 250 )){
			raster[i] = false;
		}
	}
	
	public void render(){
		l1 = System.nanoTime();
		int y=0;
		int x=0;
		int width = Engine.getWidth();
		int cnt_rh = 0;
		int cnt_pixels = 0;
		while((l2-l1)/1000000 < 175){
			int i = (int) (Math.random()*(Engine.getHeight()*Engine.getWidth())-1);
			if(!raster[i]){
				raster[i] = true;
				x = i % width;
				y = i / width;
				int hits = 0;
				double[] color = new double[3];
				// Supersampling loops
				for (int k = 0; k < superSampleWidth; k++) {															
					for (int l = 0; l < superSampleWidth; l++) {					
						double[] sampleColor = null;
						// Create the ray
						Vector3D ray = constructRayThroughPixel(x, y, k, l);
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
				Engine.getBackBufferGraphics().setColor(ColorUtils.floatArrayToColor(color));
				Engine.getBackBufferGraphics().fillRect(x, y, 1, 1);
				cnt_pixels++;
			}else{
				cnt_rh++;
			}
			l2 = System.nanoTime();
		}
		//Utils.console("Rendered one image: " + (l2-l1)/1000000 + " ms + " + cnt_pixels + "/" + cnt_rh);
	}

	public Vector3D constructRayThroughPixel(int x, int y, double sampleXOffset, double sampleYOffset){										 																
		Vector3D ray = new Vector3D(eye, direction, screenDist);
		double[] endPoint = ray.getEndPoint();		
		
		double upOffset = -1 * (y - (Engine.getHeight() / 2) - (sampleYOffset / superSampleWidth)) * pixelHeight;
		double rightOffset = (x - (Engine.getWidth() / 2) + (sampleXOffset / superSampleWidth)) * pixelWidth;
		
		MathUtils.addVectorAndMultiply(endPoint, rightDirection, rightOffset);
		MathUtils.addVectorAndMultiply(endPoint, viewplaneUp, upOffset);
		
		ray.setDirection(MathUtils.calcPointsDiff(eye, endPoint));						
		ray.normalize();
		return ray;
	}
	
	// Finds an intersecting primitive. Will ignore the one specificed by ignorePrimitive
	public Intersection findIntersection(Vector3D ray, Object3D ignorePrimitive)
	{
		// Start off with infinite distance and no intersecting primitive
		double minDistance = Double.POSITIVE_INFINITY;
		Object3D minPrimitive = null;
		
		for(Object3D primitive : Scene.getObjects()){
			double t = primitive.intersect(ray);
			// If we found a closer intersecting primitive, keep a reference to and it
			if (t < minDistance && t > EPSILON && primitive != ignorePrimitive){
				if(primitive.getTransparant() == 0 | primitive.getTransparant() < Math.random()){
					minPrimitive = primitive;
					minDistance = t;
				}
			}
		}
		return new Intersection(minDistance, minPrimitive);
	}
	
	public double[] getColor(Vector3D ray, Intersection intersection, int recursionDepth){
		// Avoid infinite loops and help performance by limiting the recursion depth
		if (recursionDepth > MAX_REFLECTION_RECURSION_DEPTH){
			return new double[]{0.0,0.0,1.0};
		}
		Object3D primitive = intersection.getPrimitive();
		if (primitive == null){
			return Scene.getBackgroundColor();
		}
		double[] pointOfIntersection = ray.getEndPoint();
		
		Material3DS surface = primitive.getMaterialAt(pointOfIntersection);			
		
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
		for (Light light: Scene.getLights()) {
			double[] vectorToLight = light.getVectorToLight(pointOfIntersection);			
			
			Vector3D rayToLight = new Vector3D(pointOfIntersection, vectorToLight, 1);
			rayToLight.normalize();
			
			// Light is visible if there's no intersection with an object at least epsilon away			
			double distanceToBlockingPrimitive = findIntersection(rayToLight, null).getDistance();
			double distanceToLight = MathUtils.norm(MathUtils.calcPointsDiff(pointOfIntersection, light.getLocation()));
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
		double[] sceneAmbient = Scene.getAmbientLight(); 
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
		Vector3D reflectionRay = new Vector3D(pointOfIntersection, reflectionDirection, 1);
		reflectionRay.normalize();
		
		Intersection reflectionIntersection = findIntersection(reflectionRay, null);		
		double[] reflectionColor = getColor(reflectionRay, reflectionIntersection, recursionDepth + 1);								
		
		MathUtils.addVectorAndMultiply(color, reflectionColor, surface.getReflectance());				
		
		return color;
	}
}
