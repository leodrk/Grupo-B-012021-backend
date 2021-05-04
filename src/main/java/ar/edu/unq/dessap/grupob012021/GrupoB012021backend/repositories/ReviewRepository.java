package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Content;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findById(int id);

    Optional<Review> findByReferedContent(Content content);
}