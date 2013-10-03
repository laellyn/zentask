package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.Model;


@Entity
public class Project extends Model {
	
	@Id
	public Long id;
	public String name;
	public String folder;
	@ManyToMany (cascade = CascadeType.REMOVE)
	public List<Users> members = new ArrayList<Users>(); 
	
	
public Project(String name,String folder,Users owner){
	this.name = name;
	this.folder = folder;
	this.members.add(owner);
}
public static Model.Finder<Long,Project> find = new Model.Finder(Long.class,Project.class);

public static Project create(String name,String folder,String owner){
		Project project = new Project(name,folder,Users.find.ref(owner));
		project.save();
		project.saveManyToManyAssociations("members");
		return project;
	}
	public static List<Project> findInvolving (String user){
		return find.where().eq("members.email", user).findList();
	}
}
