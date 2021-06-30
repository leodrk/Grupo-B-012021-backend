package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.configs;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
@Slf4j
public class AspectRequestLogger {

    @Autowired(required = false)
    private HttpServletRequest request;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
              "@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void endpointMapping() {

    }

    @Before("endpointMapping()")
    public void loggerBefore() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        log.info("*************************************************************************");
        log.info(timeStamp);
        log.info("Request for endpoint " + request.getMethod() + " - " + request.getRequestURL().toString() + " started.");
    }

    @After("endpointMapping()")
    public void loggerAfter() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        log.info("*************************************************************************");
        log.info(timeStamp);
        log.info("Request for endpoint " + request.getMethod() + " - " + request.getRequestURL().toString() + " finished.");
    }
}