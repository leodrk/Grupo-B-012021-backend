package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.SubscriberLog;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriberLogService {

    List<SubscriberLog> findByPlatform(String platform);

    void save(Review review, String platform);

    void saveAll(List<SubscriberLog> subscriberLogs);

    void notifySubscribers(Review review, String platform);
}
