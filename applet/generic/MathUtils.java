package generic;

public class MathUtils {
		
		// Returns the square of a
		public static double sqr(double a) {
			return a * a;
		}
		
		// Returns the square of a - b
		public static double sqrDiff(double a, double b) {
			return (a - b) * (a - b);
		}
		
		// Vector addition, adds addition to vec
		public static void addVector(double[] vec, double addition[]) {
			vec[0] += addition[0]; 
			vec[1] += addition[1];
			vec[2] += addition[2];
		}
		
		// Multiplies addition by a scalar and then adds the result to vec
		public static void addVectorAndMultiply(double[] vec, double addition[], double scalar) {
			vec[0] += addition[0] * scalar; 
			vec[1] += addition[1] * scalar;
			vec[2] += addition[2] * scalar;
		}
		
		// Multiplies vec by a scalar
		public static void multiplyVectorByScalar(double[] vec, double scalar) {
			vec[0] *= scalar; 
			vec[1] *= scalar;
			vec[2] *= scalar;
		}
		
		
		/**
		 * Calculates a dot product between two given vectors
		 * @param vec1
		 * @param vec2
		 * @return
		 */
		public static double dotProduct(double[] vec1, double[] vec2) {
			return vec1[0] * vec2[0]  +  vec1[1] * vec2[1]  +  vec1[2] * vec2[2] ;
		}	
		
		/**
		 * Calculates the difference between two point in 3D space
		 * @param p1
		 * @param p2
		 * @return
		 */
		public static double[] calcPointsDiff(double[] p1, double[] p2) {
			return new double [] { p2[0] - p1[0] , p2[1] - p1[1] , p2[2] - p1[2] };
		}

		// Returns the norm of the difference between this vector's position point and another point
		public static double norm(double[] p) {					
			return Math.sqrt(sqr(p[0]) + sqr(p[1]) + sqr(p[2]));
		}

		// Normalizes a vector
		public static void normalize(double[] vec){
			double norm = norm(vec);
			
			if (norm == 0)
				return;
			vec[0] /= norm;
			vec[1] /= norm;
			vec[2] /= norm;
		}

		// Returns the cross product of 2 vectors
		public static double[] crossProduct(double[] d1, double[] d2) {				
			double[] result = { (d1[1] * d2[2]) - (d1[2] * d2[1]), (d1[2] * d2[0]) - (d1[0] * d2[2]), (d1[0] * d2[1]) - (d1[1] * d2[0]) };
			
			return result;  		
		}	
		
		// Reflects a vector around a normal vector. both vectors are assumed to have the same shift from the origin
		public static double[] reflectVector(double[] vec, double[] normal) {
			double dotProduct = MathUtils.dotProduct(vec, normal);
			double[] r = new double[] { 
					-vec[0] + 2 * normal[0] * dotProduct,
					-vec[1] + 2 * normal[1] * dotProduct,
					-vec[2] + 2 * normal[2] * dotProduct };		
			return r;
		}
		
		// Returns the vector opposite to vec
		public static double[] oppositeVector(double[] vec) {				
			double[] r = new double[] { -vec[0], -vec[1], -vec[2] };
							
			return r;
		}
		
}
