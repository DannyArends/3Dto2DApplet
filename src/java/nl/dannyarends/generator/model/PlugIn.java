package nl.dannyarends.generator.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class PlugIn extends ModelObject{
	private String mainFolder = "";
	private Boolean loadonStartUp = false;
		
	PlugIn(String path){
		setMainFolder(path);
	}

	@Override
	public void writeToStream(BufferedWriter out) throws IOException {
		out.append(name + sep + mainFolder + sep + loadonStartUp + "\0");
	}

	@Override
	public int readFromStream(BufferedReader in) {
	    PassValues v = toValues(in);
		if(v.values.length == 3){
	      name = v.values[0];
	      mainFolder= v.values[1];
	      loadonStartUp = Boolean.parseBoolean(v.values[2]);
	      return v.length;
	    }else{
	      return -1;
	    }
	}

	public void setMainFolder(String mainFolder) {
		this.mainFolder = mainFolder;
	}

	public String getMainFolder() {
		return mainFolder;
	}

}
