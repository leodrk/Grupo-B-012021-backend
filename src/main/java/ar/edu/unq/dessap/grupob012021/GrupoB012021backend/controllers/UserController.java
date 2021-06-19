package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.controllers;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.SubscriberLog;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content.Content;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.user.User;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.user.UserDTO;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.ContentService;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.SubscriberLogService;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.UserService;
import com.google.common.hash.Hashing;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ContentService contentService;
    @Autowired
    private SubscriberLogService subscriberLogService;
    @Autowired
    private HttpServletRequest request;

    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody UserDTO userDTO){
        User user = getUserfromDTO(userDTO);
        try {
            user.setPassword(Hashing.sha256()
                            .hashString(user.getPassword(), StandardCharsets.UTF_8)
                            .toString());
            userService.save(user);
        }
        catch (Exception e){
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<User> login(@RequestBody UserDTO userDTO) {
        String hashedPassword = Hashing.sha256()
                .hashString(userDTO.getPassword(), StandardCharsets.UTF_8)
                .toString();
        Optional<User> savedUser = userService.findByUsernameAndPassword(userDTO.getUsername(), hashedPassword);
        if (savedUser.isPresent()) {
            User returnUser = savedUser.get();
            returnUser.setToken(getJWTToken(userDTO.getUsername()));
            request.getSession().setAttribute("user", returnUser);
            return new ResponseEntity(returnUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/subscribe/{contentId}")
    public ResponseEntity subscribeToContent (@PathVariable(value="contentId") int contentId){
        try {
            User user = (User) request.getSession().getAttribute("user");
            String platform = user.getPlatform();
            Optional<Content> optionalContent = contentService.findById(contentId);
            if (optionalContent.isPresent()) {
                Content content = optionalContent.get();
                if (!content.getSubscribers().contains(platform)) {
                    content.addSubscriber(platform);
                    contentService.save(content);
                }
                return new ResponseEntity("Suscriptor agregado", HttpStatus.OK);
            }
            return new ResponseEntity("No se ha encontrado el contenido asociado al id ingresado", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity("Ha ocurrido un error al intentar agregar el suscriptor", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getNotifications")
    public ResponseEntity<List<SubscriberLog>> getNotifications(HttpServletRequest request) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            String platform = user.getPlatform();
            List<SubscriberLog> subscriberLog = subscriberLogService.findByPlatform(platform);
            return new ResponseEntity(subscriberLog, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Ha ocurrido un error al intentar obtener las notificaciones", HttpStatus.BAD_REQUEST);
        }
    }


    private String getJWTToken(String username) {
        String secretKey = "grupob012021BackendDappSecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("grupoB")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS256,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    private User getUserfromDTO(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setPlatform(userDTO.getPlatform());
        return user;
    }
}

