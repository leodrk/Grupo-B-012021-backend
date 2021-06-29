package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.user.UserDTO
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.UserRepository
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest (classes = UserServiceImplSpec.class)
class UserServiceImplSpec extends Specification{

    UserRepository userRepository = Mock(UserRepository)
    UserServiceImpl userService = new UserServiceImpl(userRepository: userRepository)

    def "when save is called it should call userRepository.save"(){
        given:
        def userDTO = new UserDTO()

        when:
        userService.save(userDTO)

        then:
        1 * userRepository.save(_)
    }

    def "when findByUsernameAndPassword is called it should call userRepository.findByUsernameAndPassword" (){
        given:
        def username = "test"
        def password = "passTest"

        when:
        userService.findByUsernameAndPassword(username,password)

        then:
        1 * userRepository.findByUsernameAndPassword(username,password)
    }

}