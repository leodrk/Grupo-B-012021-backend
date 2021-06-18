package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.controllers;

import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.user.User;
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.review.user.UserDTO;
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
            return new ResponseEntity(returnUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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

