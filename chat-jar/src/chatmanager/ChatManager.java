package chatmanager;

import java.util.List;

import javax.ejb.Remote;

import model.Message;
import model.User;

@Remote
public interface ChatManager {

	public boolean registerUser(String username, String password);
	
	public boolean logInUser(String username, String password);
	
	public boolean logOutUser(String username);
	
	public List<User> getLoggedInUsers();
	
	public boolean existsUserLogged(String username);
	
	
	public List<User> getRegisteredUser();
	
	public boolean existsUserRegistered(String username);
	
	public void addRegisteredUser(User user);
	
	
	
	
	public Message saveMessage(String sender, String receiver, String subject, String content);
	
	public List<Message> getMessages(String username);
	
	
	
	
	
	public void addMessage(Message message);
	
	
	
	
	public void removeLoggedIn(User user);
	
	public void addLoggedIn(User user);
	
	
}
