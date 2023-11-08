import com.example.bestmatchedrestaurants.controller.CuisineController
import com.example.bestmatchedrestaurants.service.CuisineService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.MediaType

@SpringBootTest(classes = [CuisineController::class])
class CuisineControllerTest {

    @MockBean
    private lateinit var service: CuisineService

    private val restTemplate = TestRestTemplate()
    private val urlApi = "http://localhost:8080/cuisines"

    @Test
    fun `test findAll`() {
        val response = restTemplate.getForEntity(urlApi, String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }
}