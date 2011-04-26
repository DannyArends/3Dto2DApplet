package nl.dannyarends.generator.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class Model extends ModelObject{
	ArrayList<Relation> relations = new ArrayList<Relation>();
	ArrayList<WindowModel> windows = new ArrayList<WindowModel>();
	
	Model(){
		Entity danny = new Entity("Danny",null,"Whatever");
		danny.addAttribute(new Attribute("my_int",new Value("integer",1)));
		danny.addAttribute(new Attribute("my_double",new Value("double",2.5)));
		danny.addAttribute(new Attribute("my_string",new Value("string","Hello World")));
		danny.addAttribute(new Attribute("my_array",new Value("collection",new ArrayList<Attribute>())));
		relations.add(new Relation(danny, new Entity("love","eternal"), new Entity("Anna")));
		WindowModel test = new WindowModel("About","nl.dannyarends.rendering.objects.hud.window.");
		test.addText(new WindowModel().getText(10,10,"QTL viewing applet",test.getTextType(0)));
		windows.add(test);
	}
	
	public boolean loadModel(String file) throws IOException{
		File f = new File(file);
		if(f.exists()){
			relations.clear();
			BufferedReader r = new BufferedReader(new FileReader(f));
			readFromStream(r);
			r.close();
			return true;
		}else{
			System.err.println("File not found: " + file);
			return false;
		}
	}
	
	public boolean saveModel(String file) throws IOException{
		BufferedWriter w = new BufferedWriter(new FileWriter(new File(file)));
		writeToStream(w);
		w.flush();
		w.close();
		return true;
	}
	
	ArrayList<Relation> getRelations() {
		return relations;
	}
	
	public ArrayList<WindowModel> getWindowModels(){
		return windows;
	}
	
	public void addRelation(Relation r){
		relations.add(r);
	}

	public ArrayList<Entity> getEntities() {
		ArrayList<Entity> allEntities = new ArrayList<Entity>();
		for(Relation r : relations){
			if(r.origin != null) allEntities.add(r.origin);
			if(r.relation != null) allEntities.add(r.relation);
			allEntities.add(r.target);
		}
		return allEntities;
	}
	
	public ArrayList<Entity> getUniqueEntities() {
		ArrayList<Entity> allEntities = new ArrayList<Entity>();
		for(Relation r : relations){
			if(r.origin != null && !hasEntity(r.origin,allEntities)) allEntities.add(r.origin);
			if(r.relation != null && !hasEntity(r.relation,allEntities)) allEntities.add(r.relation);
			if(!hasEntity(r.target,allEntities))allEntities.add(r.target);
		}
		return allEntities;
	}
	
	public Boolean equalsEntity(Entity s,Entity c){
		if(s.getName().equals(c.getName())){
			if(s.getNamespace().equals(c.getNamespace())){
				return true;
			}
		}
		return false;
	}
	
	public Boolean hasEntity(Entity s, ArrayList<Entity> list) {
		for(Entity c : list){
			if(equalsEntity(c,s)) return true;
		}
		return false;
	}
	
	@Override
	public void writeToStream(BufferedWriter out) throws IOException {
		for(Relation r : relations){
			r.writeToStream(out);
			out.append("\n");
		}
	}
	
	@Override
	public int readFromStream(BufferedReader in) {
		String line=null;
		try {
			while((line = in.readLine())!=null){
				Relation r = new Relation(new BufferedReader(new StringReader(line)));
				this.addRelation(r);
			}
			return 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
	}

}
