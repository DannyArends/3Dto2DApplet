package nl.dannyarends.generator.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class Entity extends ModelObject{
	private Entity parent = null;
	private String namespace = "";

	private boolean has_collection = false;
	private ArrayList<Attribute> attributes = new ArrayList<Attribute>();
//	private JLabel namelbl = new JLabel("Name:");
//    private JTextField descriptionIn = new JTextField(15);
//	private JLabel descriptionlbl = new JLabel("Description:");
//	private JLabel parentlbl = new JLabel("Parent:");
//   	private JComboBox parentIn = new JComboBox();
//    private JTextField nameIn = new JTextField(15);
//   	private JButton UpdateBtn = new JButton("Update");
   	
   	public Entity(){
   		name = "";
   		sep = "~";
   		description = ".";
//		setLayout(new GridLayout(4,2));
//		add(namelbl);
//		add(nameIn);
//		add(descriptionlbl);
//		add(descriptionIn);
//		add(parentlbl);
//		add(parentIn);
//		add(UpdateBtn);
	}
	
	public Entity(String n){
		this();
		name=n;
	}
	
	public Entity(String n,String ns){
		this(n);
		if(ns!=null) namespace+= (ns + ".");
	}
	
	Entity(String n,String ns,String desc){
		this(n,ns);
		description=desc;
	}
	
	public ArrayList<Attribute> getAttributes(){
		return attributes;
	}
	
	public void addAttribute(Attribute a){
		if(a.getValue().type==Value.Type.COLLECTION)has_collection=true;
		attributes.add(a);
	}

	public void setParent(Entity p) {
		parent = p;
	}
	
	public Entity getMyparent() {
		return parent;
	}
	
	public String getNamespace() {
		if(namespace.length() > 1)return namespace;
		return "";
	}
	
	public String getJavanamespace() {
		if(namespace.length() > 2) return namespace.substring(0,namespace.length()-1);
		return " ";
	}
	
	public boolean hasCollection(){
		return has_collection;
	}
	
	public String getDescription() {
		return description;
	}

	@Override
	public void writeToStream(BufferedWriter out) throws IOException {
		out.append("{" + name+sep);
		if(parent!=null){
			parent.writeToStream(out);
		}else{
			out.append("{null}");
		}
		out.append(sep+getJavanamespace()+sep+description+sep);
		for(Attribute a: attributes){
			a.writeToStream(out);
		}
		out.append("}");
	}

	@Override
	public int readFromStream(BufferedReader in) {
		try{
			String entity = "";
			String p = "";
			int c = in.read();
			int p_cnt = 0;
			if((char)c != '{') p_cnt++;
			do{
				if((char)c == '}') p_cnt--;
				if(p_cnt==1)entity += (char)c;
				if(p_cnt>1)p += (char)c;
				if((char)c == '{') p_cnt++;
			}while(((c = in.read()) != -1 && p_cnt != 0));
			String[] values = entity.split(sep);
			name = values[0];
			if((values.length >= 4 )){
				if(!p.equals("null")){
					parent = new Entity(new BufferedReader(new StringReader("{" + p + "}")));
				}
				namespace= ((values[2].equals(" "))? "" : values[2] + ".");
				description= values[3];
				if((values.length == 5 )){
					int skip = -1;
					do{
						Attribute a = new Attribute();
						skip = a.readFromStream(new BufferedReader(new StringReader(values[4])));
						if(skip > 0){
							addAttribute(a);
							values[4] = values[4].substring(skip+1);
						}
					}while(skip > 0);
				}
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	public Entity(BufferedReader in) {
		readFromStream(in);
	}
}
