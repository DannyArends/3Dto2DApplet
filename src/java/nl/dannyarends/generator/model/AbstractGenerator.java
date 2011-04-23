package nl.dannyarends.generator.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public abstract class AbstractGenerator {
	String name = "";
	String description = "";
	public Model model = new Model();
	
	public abstract boolean generate() throws Exception;
	
	public Template createTemplate(String path) throws Exception{
		Configuration cfg = new Configuration();
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		ClassTemplateLoader loader1 = new ClassTemplateLoader(getClass(), "");
		TemplateLoader[] loaders = new TemplateLoader[]{loader1};
		MultiTemplateLoader mLoader = new MultiTemplateLoader(loaders);
		cfg.setTemplateLoader(mLoader);
		return cfg.getTemplate(path);
	}
	
	public Map<String, Object> createArguments(){
		Map<String, Object> args = new TreeMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		args.put("year", calendar.get(Calendar.YEAR));
		DateFormat formatter = new SimpleDateFormat("MMMM d, yyyy, HH:mm:ss", Locale.US);
		args.put("datetime", formatter.format(new Date()));
		formatter = new SimpleDateFormat("MMMM d, yyyy", Locale.US);
		args.put("date", formatter.format(new Date()));
		return args;
	}
}
