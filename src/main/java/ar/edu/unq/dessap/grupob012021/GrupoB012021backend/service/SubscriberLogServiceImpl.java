package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Platform;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.PlatformRepository;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.SubscriberLogRepository;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.SubscriberLog;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@Slf4j
public class SubscriberLogServiceImpl implements SubscriberLogService {

    private String NOTIFICATION_LOG =    System.lineSeparator() +
                                        "*************************************************************************" + System.lineSeparator() +
                                        "Platform {} got a new review! " + System.lineSeparator() +
                                        "Endpoint: {}" + System.lineSeparator() +
                                        "Review ID: {}" + System.lineSeparator() +
                                        "Content title: {}" + System.lineSeparator() +
                                        "User: {}" + System.lineSeparator() +
                                        "*************************************************************************" +
                                        System.lineSeparator();
    @Autowired
    private SubscriberLogRepository subscriberLogRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Override
    public List<SubscriberLog> findByPlatform(String platform) {
        return subscriberLogRepository.findByPlatform(platform);
    }

    public void save(Review review, Platform platform) {
        subscriberLogRepository.save(new SubscriberLog(review, platform));
    }

    @Async
    public void notifySubscribers() {
        List<SubscriberLog> allLogs = subscriberLogRepository.findAll();
        allLogs.forEach(l -> log.info(NOTIFICATION_LOG, l.getPlatform().getName(),
                                                        l.getPlatform().getEndpoint(),
                                                        l.getReview().getId(),
                                                        l.getReview().getContent().getTitle(),
                                                        l.getReview().getUserName()));
        subscriberLogRepository.deleteAll();
    }

    @Async
    public void registerLog(Review review) {
        Optional<Platform> optionalPlatform = platformRepository.findByName(review.getPlatform());
        if (optionalPlatform.isPresent()) {
            subscriberLogRepository.save(new SubscriberLog(review, optionalPlatform.get()));
        }
    }
}
