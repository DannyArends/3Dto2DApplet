package nl.dannyarends.generator.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Attribute extends ModelObject{

//	private JLabel namelbl = new JLabel("Name:");
//    private JTextField nameIn = new JTextField(15);
//   	private JButton UpdateBtn = new JButton("Update");

	private boolean nillable = true;
	Value value = new Value();
	
	Attribute(){
//		setLayout(new GridLayout(2,2));
//		add(namelbl);
//		add(nameIn);
//		add(value);
//		add(UpdateBtn);
	}
	
	Attribute(String n,Value v){
		name=n;
		value=v;
	}
	
	public String getName(){
		return name;
	}
	
	public String getCpptype(){
		return value.type.getCppName();
	}
	
	public String getJavatype(){
		return value.type.getJavaName();
	}
	
	public String getJnitype(){
		return value.type.getJniName();
	}
	
	public String getHsqltype(){
		return value.type.getHsqlName();
	}
	
	public Value getValue(){
		return value;
	}
	
	@Override
	public void writeToStream(BufferedWriter out) throws IOException {
		out.append(name + sep + value.type.getName() + sep + value.getValue() + sep + isNillable()+ "\0");
	}

  @Override
  public int readFromStream(BufferedReader in) {
    PassValues v = toValues(in);
	if(v.values.length == 4){
      name = v.values[0];
      value= new Value(v.values[1],v.values[2]);
      setNillable(Boolean.parseBoolean(v.values[3]));
      return v.length;
    }else{
      return -1;
    }
  }

	public void setNillable(boolean nillable) {
		this.nillable = nillable;
	}

	public boolean isNillable() {
		return nillable;
	}
}

