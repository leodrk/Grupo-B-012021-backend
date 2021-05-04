import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model.Review
import ar.edu.unq.dessap.grupob012021.GrupoB012021backend.service.ReviewServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes= ReviewServiceImpl.class)
class ReviewServiceImplSpec extends Specification{

    @Autowired
    private ReviewServiceImpl reviewService
}
