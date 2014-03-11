package edu.jmmougan.neo4j.repo;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import edu.jmmougan.neo4j.model.User;

/**
 * neo4j repository
 * 
 * @author juanmougan@gmail.com
 *
 */
public interface UserRepository extends GraphRepository<User> {
	
	/**
	 * Finds a User, provided its login
	 * @param login User login
	 * @return an User
	 */
	User findByLogin(String login);

	/**
	 * Finds the root user list of friends
	 * @return the list of Users who are friends with the root User
	 */
	@Query("START root=node:User(login = 'root') MATCH root-[:knows]->friends RETURN friends")
	List<User> findFriendsOfRoot();

}
