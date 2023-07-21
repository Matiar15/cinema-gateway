import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import pl.szudor.CinemaController
import spock.lang.Specification

@AutoConfigureMockMvc
@WebMvcTest
class CinemaControllerTest extends Specification {
    @Autowired
    private CinemaController cinemaController

    @Autowired
    private MockMvc mvc

    def "test get cinema"() {


    }
}
