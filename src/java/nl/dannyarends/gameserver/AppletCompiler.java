package nl.dannyarends.gameserver;

import java.io.File;
import java.util.ArrayList;

import nl.dannyarends.generic.JavaCompiler;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.generic.JavaCompiler.CompileUnit;



public class AppletCompiler {
	JavaCompiler j = new JavaCompiler();
	CompileUnit source;
	ArrayList<CompileUnit> applets = new ArrayList<CompileUnit>();
	
	public void compileAll(String location,String output){
		File f = new File(location);
		if(!f.exists()){
			System.err.println("No such location "+location);
		}else{
			System.out.println("Applet location: "+location + " found");
		}
		if(f.isDirectory()){
			for(String applet : f.list()){
				File a = new File(location + File.separator + applet);
				if(a.isDirectory()){
					j = new JavaCompiler();
					source = j.newCompileUnit(a.getPath(),"build");
					source.addDependencies(new String[]{"src\\java"});
					String mainClass = a.getPath().substring(a.getPath().indexOf("src\\java")+9);
					mainClass = mainClass.replace("\\", ".") +"."+ Utils.firstLetterUpperCase(applet);
					source.setMainClass(mainClass);
					source.setCustomJarName(output + a.getName());
					j.CompileTarget(source);
					applets.add(source);
					Utils.console("Mapped applet: " + a.getName() + " at " + a.getPath() + ", main: " + mainClass);
				}else{
					System.err.println("Warning found a file '"+a.getName()+"' in the applet directory");
				}
			}
		}else{
			System.err.println("Please supply a directory");
		}
	}
	
	public ArrayList<CompileUnit> getApplets(){
		return applets;
	}
	
}
