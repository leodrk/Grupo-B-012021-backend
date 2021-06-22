package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.SubscriberLog;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.SubscriberLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SubscriberLogServiceImpl implements SubscriberLogService {

    @Autowired
    private SubscriberLogRepository subscriberLogRepository;

    @Override
    public List<SubscriberLog> findByPlatform(String platform) {
        return subscriberLogRepository.findByPlatform(platform);
    }

    public void save(Review review, String platform) {
        SubscriberLog subscriberLog = new SubscriberLog(review, platform);
        subscriberLogRepository.save(subscriberLog);
    }

    public void saveAll(List<SubscriberLog> subscriberLogs){
        subscriberLogRepository.saveAll(subscriberLogs);
    }

    public void notifySubscribers(Review review, String platform) {
        List<SubscriberLog> subscriberLogs = new ArrayList<>();
        review.getContent().getSubscribers().stream().forEach(s -> subscriberLogs.add(new SubscriberLog(review, platform)));
        subscriberLogRepository.saveAll(subscriberLogs);
    }
}
