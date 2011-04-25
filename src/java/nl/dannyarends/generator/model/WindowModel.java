package nl.dannyarends.generator.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import nl.dannyarends.generator.model.WindowModel.Text;
import nl.dannyarends.generator.model.WindowModel.TextType;

public class WindowModel extends ModelObject{
	private boolean update;
	private boolean onopen;
	private String namespace = "";
	private int w;
	private int h;
	
	ArrayList<Text> texts = new ArrayList<Text>();
	ArrayList<Icon> icons = new ArrayList<Icon>();
	
	WindowModel(){
		name="Undefined";
	}
	
	WindowModel(int x,int y,String windowname, String ns){
		super(x,y,windowname);
		namespace=ns;
	}
	
	WindowModel(String windowname, String ns){
		super(windowname);
		namespace=ns;
	}
	
	enum TextType{
		STATIC,
		SERVER,
		LIVE;
	}
	
	public class Text extends ModelObject{
		public TextType type;
		
		Text(int x, int y, String text,TextType t){
			super(x,y,text);
			type=t;
		}
		
		public String getString(){
			return name;
		}

		@Override
		public void writeToStream(BufferedWriter out) throws IOException {
		}

		@Override
		public int readFromStream(BufferedReader in) {
			return 0;
		}
	}
	
	public class Icon extends ModelObject{
		Icon(int x, int y, String name){
			super(x,y,name);
		}
		
		public String getFilename(){
			return name;
		}

		@Override
		public void writeToStream(BufferedWriter out) throws IOException {
		}

		@Override
		public int readFromStream(BufferedReader in) {
			return 0;
		}
	}
	
	public boolean hasTexts(){
		return !(texts.isEmpty());
	}
	
	public boolean hasIcons(){
		return !(icons.isEmpty());
	}
	
	public void addText(Text t){
	  texts.add(t);
	}
	
	public ArrayList<Text> getTexts(TextType type){
		ArrayList<Text> n = new ArrayList<Text>();
		for(Text t : texts){
			if(t.type==type){
				n.add(t);
			}
		}
		return n;		
	}
	
	public ArrayList<Text> getTexts(){
		return getTexts(TextType.STATIC);
	}
	
	public ArrayList<Text> getServerTexts(){
		return getTexts(TextType.SERVER);
	}
	
	public ArrayList<Text> getLiveTexts(){
		return getTexts(TextType.LIVE);
	}
	
	public ArrayList<Icon> getIcons(){
		return icons;
	}
	
	public String getNamespace() {
		if(namespace.length() > 1)return namespace;
		return "";
	}
	
	public String getJavanamespace() {
		if(namespace.length() > 2) return namespace.substring(0,namespace.length()-1);
		return " ";
	}
	
	@Override
	public void writeToStream(BufferedWriter out) throws IOException {

	}

	@Override
	public int readFromStream(BufferedReader in) {
		return 0;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getW() {
		return w;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getH() {
		return h;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setOnopen(boolean onopen) {
		this.onopen = onopen;
	}

	public boolean isOnopen() {
		return onopen;
	}

  public TextType getTextType(int id) {
    switch(id){
    case 0: return TextType.STATIC;
    case 1: return TextType.SERVER;
    case 2: return TextType.LIVE;
    default: return TextType.STATIC;
  }
  }

  public Text getText(int i, int j, String string, TextType t) {
    return new Text(i,j,string,t);
  }

}
