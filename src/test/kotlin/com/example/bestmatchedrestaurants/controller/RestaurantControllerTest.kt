import com.example.bestmatchedrestaurants.controller.RestaurantController
import com.example.bestmatchedrestaurants.service.RestaurantService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType

@SpringBootTest(classes = [RestaurantController::class])
class RestaurantControllerTest () {

    @MockBean
    private lateinit var service: RestaurantService

    private val restTemplate = TestRestTemplate()
    private val urlApi = "http://localhost:8080/restaurants"
    private val urlApiFilter = "http://localhost:8080/restaurants/filter"

    @Test
    fun `test findAll`() {
        val response = restTemplate.getForEntity(urlApi, String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findFiltered`() {
        val params = mapOf(
            "name" to "",
            "customerRating" to "3",
            "distance" to "10",
            "price" to "30",
            "cuisine" to ""
        )

        val response = restTemplate.getForEntity(buildUrlWithParams(urlApiFilter, params), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    private fun buildUrlWithParams(url: String, params: Map<String, String>): String {
        val queryParams = params.entries.joinToString("&") { (key, value) -> "$key=$value" }
        return if (queryParams.isNotEmpty()) "$url?$queryParams" else url
    }

    @Test
    fun `method not allowed`() {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestBody = "{\n" +
                "    \"name\": \"\",\n" +
                "    \"price\": aaa\n" +
                "}"
        val httpEntity = HttpEntity(requestBody, headers)
        val response = restTemplate.exchange(urlApiFilter, HttpMethod.POST, httpEntity, String::class.java)
        assertEquals(405, response.statusCode.value())
    }

    @Test
    fun `fails with invalid data`() {
        val url = "$urlApiFilter?name=yummy&customerRating=abc"
        val response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String::class.java)
        assertEquals(400, response.statusCode.value())
    }

    @Test
    fun `test with no filters`() {
        val response = restTemplate.getForEntity(urlApiFilter, String::class.java)
        val jsonArray = org.json.JSONArray(response.body)
        assert(jsonArray.length() == 5)
    }
}