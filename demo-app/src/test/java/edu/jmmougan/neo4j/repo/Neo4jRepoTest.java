/**
 * 
 */
package edu.jmmougan.neo4j.repo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.jmmougan.neo4j.model.User;

/**
 * @author juanmougan@gmail.com
 * 
 * @change Fixed context loading error
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)						// Class used to run the tests
@ContextConfiguration("/neo4j/Neo4jRepoTest-context.xml")	// Load the context
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
		repo.deleteAll();
	}
	
	@Test
	public void shouldFindAll() {
		// when
		long n = repo.count();

		// then
		assertEquals("User count mismatch", USER_COUNT + 1, n);
	}

	@Test
	public void shouldFindRootUserById() {
		// when
		User root = repo.findOne(rootId);

		// then
		assertNotNull("Root user not found", root);
	}
	
	@Test
	public void shouldFindGivenUserByLogin() {
		// Given
		String givenLogin = String.format("user%02d", USER_COUNT - 1);
		// When
		User givenUser = repo.findByLogin(givenLogin);
		// Then
		assertNotNull("User with login " + givenLogin + " not found", givenUser);
	}

}
