package mikrokosmos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mikrokosmos.model.User;

@Repository
public interface  UserRepository extends JpaRepository<User,String>{

}
