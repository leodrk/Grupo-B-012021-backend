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
import java.util.*;
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
            userService.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
        }
        catch (NoSuchElementException e){
            userService.save(userDTO);
            return new ResponseEntity("usuario creado satisfactoriamente", HttpStatus.OK);
        }
        return new ResponseEntity("ocurrio un error en la creacion del usuario", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody UserDTO userDTO) {
        HashMap<String, String> returnMap = new HashMap<>();
        try {
            String hashedPassword = Hashing.sha256()
                .hashString(userDTO.getPassword(), StandardCharsets.UTF_8)
                .toString();
            User savedUser = userService.findByUsernameAndPassword(userDTO.getUsername(), hashedPassword);
            request.getSession().setAttribute("userPlatform", savedUser.getPlatform() );
            returnMap.put("token",getJWTToken(savedUser.getUsername()));
            returnMap.put("platform",savedUser.getPlatform());
            returnMap.put("username",savedUser.getUsername());
        } catch (Exception e) {
            return new ResponseEntity("ocurrio un error en el login", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(returnMap, HttpStatus.OK);

    }

    @PostMapping(value = "api/user/subscribe/{contentId}")
    public ResponseEntity subscribeToContent (@PathVariable(value="contentId") int contentId){
        String platform = (String) request.getSession().getAttribute("userPlatform");
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
            String platform = (String) request.getSession().getAttribute("userPlatform");
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

