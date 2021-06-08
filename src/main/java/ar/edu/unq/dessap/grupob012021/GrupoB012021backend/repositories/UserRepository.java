package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {}