package nl.dannyarends.rendering.objects.renderables.light;

import nl.dannyarends.generic.MathUtils;


public class PointLight extends Light{
	double[] attenuation = {1, 0, 0};
	
	public PointLight(double x, double y, double z){
		super(x,y,z);
	}
	
	public PointLight(double x, double y, double z,double c1,double c2,double c3){
		super(x,y,z,c1,c2,c3);
	}
	
	public double[] getAmountOfLight(double[] point) {
		double d = MathUtils.norm(MathUtils.calcPointsDiff(location, point));
		double totalAttenuation = 1 / (attenuation[2] * Math.sqrt(d) + attenuation[1] * d + attenuation[0]);
		double[] result = { color[0] * totalAttenuation, color[1] * totalAttenuation, color[2] * totalAttenuation };  
		return result;
	}

	@Override
	public double[] getVectorToLight(double[] pointOfIntersection){
		double[] vec = MathUtils.calcPointsDiff(location,pointOfIntersection);
		MathUtils.normalize(vec);
		return vec;
	}
}
