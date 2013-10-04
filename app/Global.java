import java.util.*;

import com.avaje.ebean.Ebean;

import models.Project;
import models.Task;
import models.User;
import play.*;
import play.libs.Yaml;



public class Global extends GlobalSettings {
	@SuppressWarnings("unchecked")
	@Override 
	public void onStart(Application app){
		Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data.yml");
		
		if(User.find.findRowCount() == 0){
			Ebean.save(all.get("users")); 	
			
		}if(Project.find.findRowCount() == 0){
			
			Ebean.save(all.get("projects"));
			Logger.info("Just saved projects");	
			
		}if(Task.find.findRowCount() == 0){
			
			Ebean.save(all.get("tasks"));
			Logger.info("Just saved tasks");
		}
	}

}
