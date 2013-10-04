package models;

import java.util.*;

import org.junit.*;

import com.avaje.ebean.Ebean;

import static org.junit.Assert.*;
import static play.test.Helpers.*;
import play.libs.Yaml;
import play.test.WithApplication;



public class ModelTest extends WithApplication {
	@Before
	public void setUp(){
		start(fakeApplication(inMemoryDatabase()));
	}
	@Test
	public void createAndRetrieveUser(){
		new User ("bob@gmail.com" , "Bob","secret").save();
		User bob = User.find.where().eq("email","bob@gmail.com").findUnique();
		assertNotNull(bob);
		assertEquals("Bob",bob.name);
	}
	
	
	

	@Test
	public void tryAuthenticateUser() {
		new User ("bob@gmail.com" ,"bob" , "secret").save();
		assertNotNull(User.authenticate("bob@gmail.com","secret"));
		assertNull("abi@gmail.com","abi");
		assertNull("bob@gmail.com","secret");
	
	}
	
	@Test
	public void findProjectInvolving(){
		new User ("bob@gmail.com","Bob" , "secrets").save();
		new User ("abi@gmail.com","abi","play").save();
		new User ("nelson@gmail.com" , "nelson" , "dribbler").save();
		
		Project.create("Play 2", "play", "bob@gmail.com");
		Project.create("Play 1", "play", "abi@gmial.com");
		
		List<Project> results = Project.findInvolving("bob@gmail.com");
		assertEquals(1, results.size());
		assertEquals("Play 2", results.get(0).name);
		
	}	
      @Test
	    public void findTodoTasksInvolving() {
	        User bob = new User("bob@gmail.com", "Bob", "secret");
	        bob.save();

	        Project project = Project.create("Play 2", "play", "bob@gmail.com");
	        Task t1 = new Task();
	        t1.title = "Write tutorial";
	        t1.assignedTo = bob;
	        t1.done = true;
	        t1.save();

	        Task t2 = new Task();
	        t2.title = "Release next version";
	        t2.project = project;
	        t2.save();

	        List<Task> result = Task.findTodoInvolving("bob@gmail.com");
	        assertEquals(1, result.size());
	        assertEquals("Release next version", result.get(0).title);
	    }
      
      @Test
      public void fullTest() {
          Ebean.save((List) Yaml.load("initial-data.yml"));

          // Count things
          assertEquals(3, User.find.findRowCount());
          assertEquals(7, Project.find.findRowCount());
          assertEquals(5, Task.find.findRowCount());

          // Try to authenticate as users
          assertNotNull(User.authenticate("bob@example.com", "secret"));
          assertNotNull(User.authenticate("jane@example.com", "secret"));
          assertNull(User.authenticate("jeff@example.com", "badpassword"));
          assertNull(User.authenticate("tom@example.com", "secret"));

          // Find all Bob's projects
          List<Project> bobsProjects = Project.findInvolving("bob@example.com");
          assertEquals(5, bobsProjects.size());

          // Find all Bob's todo tasks
          List<Task> bobsTasks = Task.findTodoInvolving("bob@example.com");
          assertEquals(4, bobsTasks.size());
      }

		
				
				
		
	}
	


