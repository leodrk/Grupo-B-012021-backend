package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.SubscriberLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional(readOnly = true)
public interface SubscriberLogRepository extends JpaRepository<SubscriberLog, Long> {

    List<SubscriberLog> findByPlatform (String platform);
}