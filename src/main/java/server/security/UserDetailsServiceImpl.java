package server.security;

import server.dao.UserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserCrudRepository userCrudRepository;

    /* Переопределяю метод интерфейса UserDetailService,
    *  возвращающий объект пользователя, реализующего интерфейс UserDetails */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails user = userCrudRepository.findByUsername(s);
        return user;
    }
}
