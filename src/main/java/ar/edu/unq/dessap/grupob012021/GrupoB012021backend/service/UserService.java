package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void save(String user, String password, String platform);
}
