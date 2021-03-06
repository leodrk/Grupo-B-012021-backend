package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.user.User;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.user.UserDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    void save(UserDTO userDTO);

    User findByUsernameAndPassword(String username, String password);
}
