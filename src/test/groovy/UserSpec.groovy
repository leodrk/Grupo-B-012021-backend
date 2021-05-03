package groovy

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.User
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes= User.class)
class UserSpec extends Specification {


    void setup(){
    }

    @Test
    def "cuando se setea el nombre de un usuario este cambia por el nombre dado"(){
        given:
        User user = new User(id:1, name: "jorge", lastName: "perez", platform: "nefli")

        when:
        user.setName("Juan")

        then:
        user.getName() == "Juan"
    }

    @Test
    def "cuando se setea el apellido de un usuario este cambia por el nombre dado"(){
        given:
        User user = new User(id:1, name: "jorge", lastName: "perez", platform: "nefli")

        when:
        user.setLastName("Garcia")

        then:
        user.getLastName() == "Garcia"
    }
}