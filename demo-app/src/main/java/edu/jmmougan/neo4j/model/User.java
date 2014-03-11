package edu.jmmougan.neo4j.model;

import static edu.jmmougan.neo4j.constant.UserConstants.REL_KNOWS;
import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * Sample model class
 * 
 * @author juanmougan@gmail.com
 * 
 */
@NodeEntity			// annotates as graph-backed entity
public class User {

	/**
	 * Unique ID of every node
	 */
	@GraphId
	Long id;

	/**
	 * Adds an index to the field
	 */
	@Indexed
	private String login;

	private String fullName;

	private Date lastLogin;
	
	@RelatedTo(type = REL_KNOWS, direction = OUTGOING)
	Set<User> friends;
	
	public User() {}

	public User(String login, String fullName) {
		this.login = login;
		this.fullName = fullName;
		this.lastLogin = new Date();
		this.friends = new HashSet<User>();
	}
	
	public void knows(User user) {
		friends.add(user);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the friends
	 */
	public Set<User> getFriends() {
		return friends;
	}

	/**
	 * @param friends the friends to set
	 */
	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}
	
	public void setId(long id) {
		this.id = id;
	}

}
