package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.configs;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.CronScheduleBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzBean {

    @Bean
    public JobDetail jobNotificationTurn() {
        return JobBuilder.newJob(NotificationJob.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail notificationJob) {
        return TriggerBuilder.newTrigger().forJob(notificationJob)
                .withIdentity("Job Notificate for new reviews")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 */2 * ? * *"))
                .build();
    }
}