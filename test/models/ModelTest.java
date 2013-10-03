package models;

import java.util.List;

import org.junit.*;

import static org.junit.Assert.*;
import static play.test.Helpers.*;
import play.test.WithApplication;



public class ModelTest extends WithApplication {
	@Before
	public void setUp(){
		start(fakeApplication(inMemoryDatabase()));
	}
	@Test
	public void createAndRetrieveUser(){
		new Users ("bob@gmail.com" , "Bob","secret").save();
		Users bob = Users.find.where().eq("email","bob@gmail.com").findUnique();
		assertNotNull(bob);
		assertEquals("Bob",bob.name);
	}
	
	
	

	@Test
	public void tryAuthenticateUser() {
		new Users ("bob@gmail.com" ,"bob" , "secret").save();
		assertNotNull(Users.authenticate("bob@gmail.com","secret"));
		assertNull("abi@gmail.com","abi");
		assertNull("bob@gmail.com","secret");
	
	}
	
	@Test
	public void findProjectInvolving(){
		new Users ("bob@gmail.com","Bob" , "secrets").save();
		new Users ("abi@gmail.com","abi","play").save();
		new Users ("nelson@gmail.com" , "nelson" , "dribbler").save();
		
		Project.create("Play 2", "play", "bob@gmail.com");
		Project.create("Play 1", "play", "abi@gmial.com");
		
		List<Project> results = Project.findInvolving("bob@gmail.com");
		assertEquals(1, results.size());
		assertEquals("Play 2", results.get(0).name);
		
	}	
      @Test
	    public void findTodoTasksInvolving() {
	        Users bob = new Users("bob@gmail.com", "Bob", "secret");
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

		
				
				
		
	}
	


