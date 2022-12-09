package Mikrokosmos.auth;

import Mikrokosmos.DAO.StaffDAO;
import Mikrokosmos.Model.UserAccountDTO;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yukinon
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    
    @Autowired
    StaffDAO accountDAO;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccountDTO account = accountDAO.getAccountByUsername(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(account.getDepart_id()));
        grantedAuthorities.add(new SimpleGrantedAuthority(account.getRole()));
        String user=account.getUsername();
        String password=account.getPassword();
        //User abc = new User(user, password, grantedAuthorities);
        return new org.springframework.security.core.userdetails.User(user, password, true,true,true,true,grantedAuthorities);
    }
}
