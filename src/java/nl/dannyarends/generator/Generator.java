package nl.dannyarends.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import nl.dannyarends.generator.model.AbstractGenerator;
import nl.dannyarends.generator.model.Entity;
import nl.dannyarends.generic.Utils;
import nl.dannyarends.options.GeneratorOptions;
import freemarker.template.Template;

public class Generator extends AbstractGenerator {

	public ArrayList<File> getTemplates(File path) {
		ArrayList<File> r = new ArrayList<File>();
	    if( path.exists() ) {
	      File[] files = path.listFiles();
	      for(int i=0; i<files.length; i++) {
	         if(files[i].isDirectory()) {
	        	 r.addAll(getTemplates(files[i]));
	         }
	         else {
	           r.add(files[i]);
	         }
	      }
	    }
	    return r;
	  }
	
	String toRelativePath(String path){
		path = path.replace("\\", "/");
		return path.substring(path.indexOf(GeneratorOptions.templates));
	}
	
	String getExtension(String path){
		path = path.replace("\\", "/");
		path = path.substring(0,path.lastIndexOf("."));
		return path.substring(path.lastIndexOf("."));
	}
	
	String getLocation(String path){
		path = path.replace("\\", "/");
		path = path.substring(0,path.lastIndexOf("/"));
		return path.substring(path.indexOf("/"));
	}
	
	@Override
	public boolean generate() throws Exception {
		URL u = this.getClass().getResource(GeneratorOptions.templates);
		File f = new File(u.getPath());
		ArrayList<File> templates = getTemplates(f);
		System.out.print("Generating using " + templates.size() + " template and ");
		System.out.print(model.getUniqueEntities().size() + "/" + model.getEntities().size() + " entities\n");
		
		for(File t : templates){
			String relativePath = toRelativePath(t.getPath());
			Template template = createTemplate(relativePath);
			String outputDir = GeneratorOptions.output + getLocation(relativePath).substring(1) + "/";
			Map<String, Object> arguments = createArguments();
			File targetOut;
			
			if(t.getName().contains("entity")){
				for (Entity entity : model.getUniqueEntities()){
					targetOut = new File(outputDir + entity.getNamespace().replace(".", "/"));
					targetOut.mkdirs();
					targetOut = new File(outputDir + entity.getNamespace().replace(".", "/") + entity.getName() + getExtension(t.getPath()));
					
					arguments.put("file", targetOut.toString());
					arguments.put("template", toRelativePath(t.getPath()));
					arguments.put("model", model);
					arguments.put("entity", entity);
					
					OutputStreamWriter OutStream =  new OutputStreamWriter(new FileOutputStream(targetOut));
					template.process(arguments,OutStream);
				}
			}
			if(t.getName().contains("model")){
				String name = t.getName();
				targetOut = new File(outputDir + name.substring(name.indexOf("model")+5,name.lastIndexOf(".")));
				arguments.put("file", targetOut.toString());
				arguments.put("template", toRelativePath(t.getPath()));
				arguments.put("model", model);
				OutputStreamWriter OutStream =  new OutputStreamWriter(new FileOutputStream(targetOut));
				template.process(arguments,OutStream);
			}
		}
		Utils.console("Done with generating");
		return false;
	}
}
