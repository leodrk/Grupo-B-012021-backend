package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.User;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService  {

    @Autowired
    private UserRepository userRepository;

    public void save(String user, String password, String platform){
        User newUser = new User();
        newUser.setName(user);
        newUser.setPlatform(platform);
        userRepository.save(newUser);
    }
}
