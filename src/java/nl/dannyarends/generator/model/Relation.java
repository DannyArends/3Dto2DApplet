package nl.dannyarends.generator.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class Relation extends ModelObject{
	Entity origin = null;
	Entity relation = null; 
	Entity target = null;
	
	Relation(Entity o, Entity r){
		this(null,r,o);
	}
	
	Relation(Entity o){
		this(null,null,o);
	}
	
	Relation(Entity o, Entity r,Entity t){
		origin=o;
		if(r!=null)r.setParent(o);
		relation=r;
		if(t!=null)t.setParent(r);
		target=t;
	}
	
	public Relation() {

	}

	public Relation(BufferedReader in) {
		readFromStream(in);
	}

	Entity getOrigin(){
		return origin;
	}
	
	Entity getRelation(){
		return relation;
	}
	
	Entity getTarget(){
		return target;
	}

	@Override
	public void writeToStream(BufferedWriter out) throws IOException {
		System.out.println("Writing relation to stream");
		target.writeToStream(out);
	}

	@Override
	public int readFromStream(BufferedReader in) {
		try{
			target = new Entity(in);
			System.out.print("Read: Target");
			if(target != null){
				relation = target.getMyparent();
			}
			if(relation != null){
				System.out.print(", Relation");
				origin = relation.getMyparent();
				if(origin != null){
					System.out.print(", Origin");
				}
			}
			System.out.print("\n");
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}
}
