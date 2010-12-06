package objects;

public class TriangleMesh {
	String materialname;
	int[] targets;
	
	public TriangleMesh(String materialname, int[] t){
		this.materialname=materialname;
		targets=t;
	}
}
