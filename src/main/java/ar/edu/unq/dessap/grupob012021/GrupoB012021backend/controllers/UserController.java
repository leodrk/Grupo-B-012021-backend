package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.controllers;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Platform;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.content.Content;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.repositories.PlatformRepository;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.ContentService;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.UserService;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.user.User;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.user.UserDTO;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.SubscriberLogService;
import com.google.common.hash.Hashing;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Scope;
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
@Scope("session")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ContentService contentService;
    @Autowired
    private SubscriberLogService subscriberLogService;
    @Autowired
    private PlatformRepository platformRepository;
    @Autowired
    private HttpServletRequest request;

    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody UserDTO userDTO){
        try {
            userDTO.setPassword(Hashing.sha256()
                            .hashString(userDTO.getPassword(), StandardCharsets.UTF_8)
                            .toString());
            Optional<User> currentUser = userService.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
            if (currentUser.isPresent()){
                throw new IllegalAccessException();
            }
            userService.save(userDTO);
        }
        catch (Exception e){
            return new ResponseEntity("ocurrio un error en la creacion del usuario", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("usuario creado satisfactoriamente", HttpStatus.OK);
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
        return new ResponseEntity("ocurrio un error en el login", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "api/user/subscribe/{contentId}")
    public ResponseEntity subscribeToContent (@PathVariable(value="contentId") int contentId){
        User user = (User) request.getSession().getAttribute("user");
        String platform = user.getPlatform();
        Optional<Content> optionalContent = contentService.findById(contentId);
        if (optionalContent.isPresent() && !optionalContent.get().getSubscribers().contains(platform)) {
            Content content = optionalContent.get();
            content.addSubscriber(platform);
            contentService.save(content);
            return new ResponseEntity("Suscriptor agregado", HttpStatus.OK);
        }
        return new ResponseEntity("Ha ocurrido un error al intentar suscribir la plataforma al contenido seleccionado," +
                                        "verifique los datos ingresados", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "api/user/setNotificationEndpoint/{endpoint}")
    public ResponseEntity setNotificationEndpoint (@PathVariable(value="endpoint") String endpoint){
        try {
            User user = (User) request.getSession().getAttribute("user");
            String platform = user.getPlatform();
            Platform newPlatform = new Platform(platform, endpoint);
            platformRepository.save(newPlatform);
            return new ResponseEntity(newPlatform, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity("Ha ocurrido un error al intentar guardar el endpoint", HttpStatus.BAD_REQUEST);
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
}

