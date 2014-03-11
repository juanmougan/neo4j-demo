/**
 * 
 */
package edu.jmmougan.neo4j.repo;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.jmmougan.neo4j.model.User;

/**
 * @author juanmougan@gmail.com
 * 
 */
public class Neo4jRepoTest {

	private static final int USER_COUNT = 4;
	
	@Autowired
	UserRepository repo;
	
	private long rootId;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		User root = new User("root", "Superuser");
		User[] user = new User[USER_COUNT];

		// Construct the users
		for (int i = 0; i < user.length; i++) {
			user[i] = new User(String.format("user%02d", i), "User" + i);
		}

		// Build the graph
		for (int i = 0; i < user.length; i++) {
			// Root knows every other user
			root.knows(user[i]);
			// Each user knows the next user
			for (int j = i; j < user.length; j++) {
				user[i].knows(user[j]);
			}
		}
		
		// Save the nodes
		for (int i = 0; i < user.length; i++) {
			repo.save(user[i]);
		}
		repo.save(root);
		rootId = root.getId();
		System.out.println("Root ID: " + rootId);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
