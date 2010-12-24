package objects.renderables.light;

import generic.MathUtils;


public class PointLight extends Light{
	double[] attenuation = {1, 0, 0};
	
	public PointLight(double x, double y, double z){
		super(x,y,z);
	}
	
	public double[] getAmountOfLight(double[] point) {
		double d = MathUtils.norm(MathUtils.calcPointsDiff(location, point));
		double totalAttenuation = 1 / (attenuation[2] * d * d + attenuation[1] * d + attenuation[0]);
		double[] result = { getColor()[0] * totalAttenuation, getColor()[1] * totalAttenuation, getColor()[2] * totalAttenuation };  
		return result;
	}

	@Override
	public double[] getVectorToLight(double[] pointOfIntersection){
		double[] vec = difference(pointOfIntersection);
		MathUtils.normalize(vec);
		return vec;
	}
}
