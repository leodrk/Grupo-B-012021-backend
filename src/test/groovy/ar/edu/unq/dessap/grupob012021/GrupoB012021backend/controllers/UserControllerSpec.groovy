package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.controllers

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Platform
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content.Content
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.user.User
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.user.UserDTO
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.PlatformRepository
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.ContentService
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.SubscriberLogService
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.UserService
import com.google.common.hash.Hashing
import org.json.JSONObject
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import java.nio.charset.StandardCharsets

@SpringBootTest(classes = UserControllerSpec.class)
class UserControllerSpec extends Specification{

    UserService userService = Mock(UserService)
    ContentService contentService = Mock(ContentService)
    SubscriberLogService subscriberLogService = Mock(SubscriberLogService)
    HttpServletRequest request = Mock(HttpServletRequest)
    PlatformRepository platformRepository = Mock(PlatformRepository)
    UserController userController = new UserController( userService: userService,
                                                        contentService: contentService,
                                                        subscriberLogService: subscriberLogService,
                                                        request: request,
                                                        platformRepository: platformRepository)

    def "when register is called properly, it should return a responseEntity"(){
        given:
        def userDTO = new UserDTO()
        userDTO.setUsername("test")
        userDTO.setPassword("testPassword")
        def password = Hashing.sha256()
                .hashString(userDTO.getPassword(), StandardCharsets.UTF_8)
                .toString()

        when:
        def result = userController.register(userDTO)

        then:
        result == new ResponseEntity("usuario creado satisfactoriamente", HttpStatus.OK)
        1 * userService.save(userDTO);
        1 * userService.findByUsernameAndPassword(userDTO.getUsername(),password) >> {throw new NoSuchElementException()}
    }

    def "when register is called with an existint user, it should return a responseEntity with the error info"(){
        given:
        def userDTO = new UserDTO()
        userDTO.setPassword("test")
        userService.findByUsernameAndPassword(_,_) >> new User()

        when:
        def result = userController.register(userDTO)

        then:
        result == new ResponseEntity("ocurrio un error en la creacion del usuario", HttpStatus.BAD_REQUEST)
    }

    def "when login is called properly, it should return a responseEntity with the logged user data"(){
        given:
        def userDTO = new UserDTO()
        userDTO.setUsername("test")
        userDTO.setPassword("testPassword")
        def user = new User()
        user.setPlatform("Netflix")
        user.setUsername("test")
        user.setPassword("testPassword")
        def returnJson = new JSONObject()
        returnJson.put("token",userController.getJWTToken("test"))
        returnJson.put("platform",user.getPlatform());
        returnJson.put("username",user.getUsername());
        userService.findByUsernameAndPassword(_,_) >> user
        def responseEntity = new ResponseEntity(returnJson, HttpStatus.OK)
        def session = Mock(HttpSession)
        request.getSession() >> session

        when:
        def result = userController.login(userDTO)

        then:
        result.getBody()["platform"] == responseEntity.getBody()["platform"]
        result.getBody()["username"] == responseEntity.getBody()["username"]
        result.getStatusCode() == responseEntity.getStatusCode()
    }

    def "when login is called with bad parameters, it should return a responseEntity with the error data"(){
        given:
        def userDTO = new UserDTO()
        userDTO.setUsername("test")
        userDTO.setPassword("testPassword")
        userService.findByUsernameAndPassword(_,_) >> Optional.empty()
        def responseEntity = new ResponseEntity("ocurrio un error en el login", HttpStatus.BAD_REQUEST)

        when:
        def result = userController.login(userDTO)

        then:
        result == responseEntity
    }

    def "when subscribeToContent is called with an existing contentId, it should return a responseEntity.OK"(){
        given:
        def session = Mock(HttpSession)
        request.getSession() >> session
        session.getAttribute(_) >> "Netflix"
        def content = new Content()
        content.setSubscribers([])
        contentService.findById(_) >> Optional.of(content)
        def responseEntity = new ResponseEntity("Suscriptor agregado", HttpStatus.OK)

        when:
        def result = userController.subscribeToContent(1)

        then:
        result == responseEntity
        1 * contentService.save(_)
    }

    def "when subscribeToContent is called with an already subscribed platform, it should return a responseEntity.BAD_REQUEST"(){
        given:
        def session = Mock(HttpSession)
        request.getSession() >> session
        session.getAttribute(_) >> "Netflix"
        contentService.findById(_) >> Optional.of(new Content(subscribers: ["Netflix"]))
        def responseEntity = new ResponseEntity("Ha ocurrido un error al intentar suscribir la plataforma al contenido seleccionado," +
                "verifique los datos ingresados", HttpStatus.BAD_REQUEST)

        when:
        def result = userController.subscribeToContent(1)

        then:
        result == responseEntity
    }

    def "when subscribeToContent is called with a non eisting content, it should return a responseEntity.BAD_REQUEST"(){
        given:
        def session = Mock(HttpSession)
        request.getSession() >> session
        session.getAttribute(_) >> "Netflix"
        contentService.findById(_) >> Optional.empty()
        def responseEntity = new ResponseEntity("Ha ocurrido un error al intentar suscribir la plataforma al contenido seleccionado," +
                "verifique los datos ingresados", HttpStatus.BAD_REQUEST)

        when:
        def result = userController.subscribeToContent(1)

        then:
        result == responseEntity
    }

    def "when setNotificationEndpoint is called properly, it should return a responseEntity.OK"() {
        given:
        def session = Mock(HttpSession)
        request.getSession() >> session
        def user = new User()
        user.setPlatform("Netflix")
        session.getAttribute(_) >> "Netflix"
        def endpoint = "www.google.com"
        def platform = new Platform(user.getPlatform(), endpoint)
        def responseEntity = new ResponseEntity(platform, HttpStatus.OK)

        when:
        userController.setNotificationEndpoint(endpoint)

        then:
        platform.class == responseEntity.getBody().class
        1 * platformRepository.save(_)
    }

    def "when setNotificationEndpoint is called not properly, it should return a responseEntity.BAD_REQUEST"() {
        given:
        def session = Mock(HttpSession)
        request.getSession() >> session
        session.getAttribute(_) >> "Netflix"
        def endpoint = "www.google.com"
        def responseEntity = new ResponseEntity("Ha ocurrido un error al intentar guardar el endpoint", HttpStatus.BAD_REQUEST)

        when:
        def result = userController.setNotificationEndpoint(endpoint)

        then:
        result == responseEntity
        1 * platformRepository.save(_) >> {throw new ConnectException()}
    }

}