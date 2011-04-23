package nl.dannyarends.generator.model;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Value extends JComponent{
	private static final long serialVersionUID = 1L;
	
	private JLabel typelbl = new JLabel("Type:");
	private JLabel valuelbl = new JLabel("Value:");
   	private JComboBox  typeIn = new JComboBox();
    private JTextField valueIn = new JTextField(15);
   	private JButton UpdateBtn = new JButton("Update");

	public enum Type{
		UNDEFINED("undefined","''","V","void","void*"),
		BOOLEAN("bool","INTEGER","Ljava/lang/Boolean;","Boolean","bool"),
		INTEGER("integer","INTEGER","Ljava/lang/Integer;","Integer","int"),
		DOUBLE("double","DECIMAL(65,30)","Ljava/lang/Double;","Double","double"),
		STRING("string","TEXT","Ljava/lang/String;","String","char*"),
		HREF("href","TEXT","Ljava/lang/String;","String","string"),
		DATE("date","DATETIME","Ljava/util/Date;","Date","time*"),
		COLLECTION("collection","TEXT","Ljava/util/List;","ArrayList<Attribute>","vector<Attribute>"),
		FILEPATH("file","TEXT","Ljava/lang/String;","String","char*"),
		DIRPATH("dir","TEXT","Ljava/lang/String;","String","char*");
		
		private String internalname;
		private String hsqlname;
		private String jniname;
		private String javaname;
		private String cppname;
		
		
		Type(String ourname,String hsql,String jni,String java,String cpp) {
			internalname = ourname;
			hsqlname=hsql;
			jniname=jni;
			javaname=java;
			cppname=cpp;
		}
		 
		String getName(){
			return internalname;
		}
		 
		String getHsqlName(){
			return hsqlname; 
		}
		 
		String getJniName(){
			return jniname;
		}
		 
		String getJavaName(){
			return javaname;
		}
		 
		String getCppName(){
			return cppname;
		}
		 
		static Type getType(String s){
			if(s.equals("undefined")){
				return Type.UNDEFINED;
			}
			if(s.equals("bool")){
				return Type.BOOLEAN;
			}
			if(s.equals("integer")){
				return Type.INTEGER;
			}
			if(s.equals("double")){
				return Type.DOUBLE;
			}
			if(s.equals("string")){
				return Type.STRING;
			}
			if(s.equals("href")){
				return Type.HREF;
			}
			if(s.equals("date")){
				return Type.DATE;
			}
			if(s.equals("collection")){
				return Type.COLLECTION;
			}
			if(s.equals("file")){
				return Type.FILEPATH;
			}
			if(s.equals("dir")){
				return Type.DIRPATH;
			}
			return Type.UNDEFINED;
		}
	};
	
	Type type;
	Object val;
	
	Value(){
		setLayout(new FlowLayout());
		for(Type t : Type.values()){
			typeIn.addItem(t.internalname);
		}
		typeIn.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        System.out.println(((JComboBox) e.getSource()).getSelectedItem());
		      }
		    });
		UpdateBtn.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  type = Type.getType((String)typeIn.getSelectedItem());
		    	  val = valueIn.getText();
		      }
		    });
		add(typelbl);
		add(typeIn);
		add(valuelbl);
		add(valueIn);
		add(UpdateBtn);
	}
	
	Value(String n,Object v){
		type = Type.getType(n);
		val = v;
	}
	
	String getStringValue() throws Exception{
		if(type.getJavaName().equals("String")){
			return String.valueOf(val);
		}else{
			throw new Exception("Value mismatch, expected: string/href/file/dir got:" + type.getJavaName());
		}
	}
	
	Integer getIntValue() throws Exception{
		if(type.getJavaName().equals("Integer")){
			return Integer.parseInt((String) val);
		}else{
			throw new Exception("Value mismatch, expected: integer got:" + type.getJavaName());
		}
	}
	
	Boolean getBooleanValue() throws Exception{
		if(type.getJavaName().equals("Boolean")){
			return Boolean.parseBoolean((String) val);
		}else{
			throw new Exception("Value mismatch, expected: bool got:" + type.getJavaName());
		}
	}
	Double getDoubleValue() throws Exception{
		if(type.getJavaName().equals("Double")){
			return Double.parseDouble((String) val);
		}else{
			throw new Exception("Value mismatch, expected: double got:" + type.getJavaName());
		}
	}
	
	
	String getValue(){
		return "'"+String.valueOf(val)+"'";
	}
}
