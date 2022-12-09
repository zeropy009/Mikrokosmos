package mikrokosmos.Impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Service;

import mikrokosmos.model.User;
import mikrokosmos.repository.UserRepository;
import mikrokosmos.service.UserService;

@Service
public class UserImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List findAll(){
		/* 
		 * Store không tham số
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("cutePhuongVien");
		query.execute();
		List<Object[]> list =query.getResultList();
		for(int i=0;i<list.size();i++) {
			System.out.println("ID: "+list.get(i)[0]);
			System.out.println("Pass: "+list.get(i)[1]);
		}
		*/
		/*
		 * Store với tham số truyền vào
		 */
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("getUser",User.class);
		query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		query.setParameter(1, "");
		query.execute();
		List<User> list =query.getResultList();
		for(int i=0;i<list.size();i++) {
			System.out.println("ID: "+list.get(i).getId());
			System.out.println("Pass: "+list.get(i).getPass());
		}
		return list;
		//return (List<User>)userRepository.findAll();
	}
	
	public User findById(String id){
		return (User) userRepository.findOne(id);
	}
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	public void delete(User user) {
		userRepository.delete(user);
	}
}
