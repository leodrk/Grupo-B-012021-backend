package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.configs;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.SubscriberLogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationJob implements Job {

    @Autowired
    private SubscriberLogService subscriberLogService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("*************************************************************************");
        log.info("Notifying platforms about new reviews...");
        subscriberLogService.notifySubscribers();
        log.info("*************************************************************************");

    }
}