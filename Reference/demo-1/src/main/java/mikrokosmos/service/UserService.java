package mikrokosmos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mikrokosmos.model.User;
import mikrokosmos.repository.UserRepository;


public interface UserService {
	public List<User> findAll();
	
	public User findById(String id);
	
	public void save(User user);
	
	public void delete(User user);
}
