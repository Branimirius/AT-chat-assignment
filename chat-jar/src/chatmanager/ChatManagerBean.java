package chatmanager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Singleton;

import model.Message;
import model.User;

@Singleton
@LocalBean
@Remote(ChatManager.class)
public class ChatManagerBean implements ChatManager {

		private Set<User> registeredUsers = new HashSet<User>(); 
		private Set<User> loggedInUsers = new HashSet<User>(); 
		private List<Message> messages = new ArrayList<Message>();
	
		@Override
		public boolean registerUser(String username, String password) {
			if (existsUserRegistered(username))
				return false;
			else {
				registeredUsers.add(new User(username, password));
				return true;
			}
		}
	
		@Override
		public void addRegisteredUser(User user) {
			registeredUsers.add(user);
		}

	
		@Override
		public boolean logInUser(String username, String password) {
			if (!existsRegistered(username, password))
				return false;
			else {
				loggedInUsers.add(getRegistered(username, password));
				return true;
			}
		}

		@Override
		public boolean logOutUser(String username) {
			if (existsUserLogged(username)) {
				loggedInUsers.remove(getLoggedIn(username));
				return true;
			}
			else {
				return false;
			}
		}
		
		@Override
		public List<User> getLoggedInUsers() {
			return loggedInUsers.stream().collect(Collectors.toList());
		}
	
		@Override
		public boolean existsUserLogged(String username) {
			return loggedInUsers.stream().anyMatch(u -> u.getUsername().equals(username));
		}
	
	
	
		private User getLoggedIn(String username) {
			return loggedInUsers.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
		}
	
		@Override
		public List<User> getRegisteredUser() {
			return registeredUsers.stream().collect(Collectors.toList());
		}
		
		@Override
		public boolean existsUserRegistered(String username) {
			return registeredUsers.stream().anyMatch(u -> u.getUsername().equals(username));
		}
		
		private User getRegistered(String username, String password) {
			return registeredUsers.stream().filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password)).findFirst().orElse(null);
		}
		
		private User getRegistered(String username) {
			return registeredUsers.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
		}

	
	
		@Override
		public Message saveMessage(String sender, String receiver, String subject, String content) {
			User senderUser = getRegistered(sender);
			User receiverUser = getRegistered(receiver);
			Message message = new Message(senderUser, receiverUser, LocalDateTime.now(), subject, content);
			messages.add(message);
			return message;
		}
	
		@Override
		public List<Message> getMessages(String username) {
			List<Message> userMessages = new ArrayList<Message>();
			for(Message m : messages)
				if (m.getSender().getUsername().equals(username) || m.getReceiver().getUsername().equals(username))
					userMessages.add(m);
			userMessages.sort((m1, m2) -> m1.getCreated().compareTo(m2.getCreated()));
			return userMessages;
		}
		
		
	
		private boolean existsRegistered(String username, String password) {
			return registeredUsers.stream().anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
		}
	
		@Override
		public void addMessage(Message message) {
			messages.add(message);
		}
	
		
	
		@Override
		public void addLoggedIn(User user) {
			loggedInUsers.add(user);
		}
	
		@Override
		public void removeLoggedIn(User user) {
			loggedInUsers.removeIf(u -> u.getUsername().equals(user.getUsername()));
		}
}
