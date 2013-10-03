package models;

import javax.persistence.*;

import play.db.ebean.*;

import com.avaje.ebean.*;

@Entity
public class Users extends Model {
	
	@Id
	public String email;
	public String name;
	public String password;
	
	public Users (String email, String name, String password){
		this.email = email;
		this.name = name;
		this.password = password;
	
	}
	
	public static Users authenticate(String email,String password){
		return Users.find.where().eq("email",email).eq("password",password).findUnique();
	}
	
	
	public static Finder<String, Users> find = new Finder<String,Users>(String.class, Users.class);
	
	

}
