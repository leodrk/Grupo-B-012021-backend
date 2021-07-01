package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Platform
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.Review
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.PlatformRepository
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.SubscriberLogRepository
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = SubscriberLogServiceImpleSpec.class)
class SubscriberLogServiceImpleSpec extends Specification{

    SubscriberLogRepository subscriberLogRepository = Mock(SubscriberLogRepository)
    PlatformRepository platformRepository = Mock(PlatformRepository)
    SubscriberLogServiceImpl subscriberLogService = new SubscriberLogServiceImpl(   subscriberLogRepository: subscriberLogRepository,
                                                                                    platformRepository: platformRepository)


    def "when findByPlatform is called it should call subscriberLogRepository.findByPlatform"(){
        given:
        def platform = "Netflix"

        when:
        subscriberLogService.findByPlatform(platform)

        then:
        1 * subscriberLogRepository.findByPlatform(platform)
    }

    def "when save is called it should call subscriberLogRepository.save"(){
        given:
        def platform = new Platform()
        def review = new Review()

        when:
        subscriberLogService.save(review, platform)

        then:
        1 * subscriberLogRepository.save(_)
    }


    def "when notifySubscribers is called, it should call subscriberLogRepository findAll and subscriberLogRepository deleteAll"(){
        given:

        when:
        subscriberLogService.notifySubscribers()

        then:
        1 * subscriberLogRepository.findAll() >> []
        1 * subscriberLogRepository.deleteAll()
    }

    def "when registerLog is called, it should call platformRepository findByNameIn and subscriberService save IF the content have that the review platform"(){
        given:
        def review = new Review()

        when:
        subscriberLogService.registerLog(review)

        then:
        1 * platformRepository.findByName(_) >> maybePlatform
        calls * subscriberLogRepository.save(_)

        where:
        calls | maybePlatform
        1     | Optional.of(new Platform())
        0     | Optional.empty()
    }

}