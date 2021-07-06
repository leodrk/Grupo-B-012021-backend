package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.UserRepository;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.user.User;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService  {

    @Autowired
    private UserRepository userRepository;

    public void save(UserDTO userDTO){
        User user = getUserfromDTO(userDTO);
        userRepository.save(user);
    }

    public User findByUsernameAndPassword(String username, String password) throws NoSuchElementException {
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new NoSuchElementException();
    }

    private User getUserfromDTO(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setPlatform(userDTO.getPlatform());
        return user;
    }
}
