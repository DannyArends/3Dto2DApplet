package objects;

public class Edge {
	public int a, b;
	public int faceflag;

	public Edge(int A, int B) {
		a = A;
		b = B;
		faceflag = 0;
	}
	
	public Edge(int A, int B,int flag) {
		a = A;
		b = B;
		faceflag = flag;
	}
}
