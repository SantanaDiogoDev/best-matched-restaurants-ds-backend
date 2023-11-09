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

    private fun buildUrlWithParams(url: String, params: Map<String, String>): String {
        val queryParams = params.entries.joinToString("&") { (key, value) -> "$key=$value" }
        return if (queryParams.isNotEmpty()) "$url?$queryParams" else url
    }

    @Test
    fun `test findAll`() {
        val response = restTemplate.getForEntity(urlApi, String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
        val jsonArray = org.json.JSONArray(response.body)
        assert(jsonArray.length() == 200)
    }

    @Test
    fun `test findByFilter`() {
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
    fun `fails with invalid data in JSON BODY`() {
        val requestBody = """
            {
                "name": "Yum"
                "customerRating": 3,
                "distance": abc
                "price": 30,
                "cuisine": ""
            }
        """.trimIndent()

        val response = restTemplate.exchange(urlApiFilter, HttpMethod.GET, HttpEntity(requestBody), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test with no filters`() {
        val response = restTemplate.getForEntity(urlApiFilter, String::class.java)
        val jsonArray = org.json.JSONArray(response.body)
        assert(jsonArray.length() == 5)
    }
    @Test
    fun `test findByFilter with empty values in URL, name`() {
        val params = mapOf(
            "name" to "",
            "customerRating" to "3",
            "distance" to "10",
            "price" to "30",
            "cuisine" to "Mex"
        )

        val response = restTemplate.getForEntity(buildUrlWithParams(urlApiFilter, params), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with empty values in URL, customerRating`() {
        val params = mapOf(
            "name" to "Deli",
            "customerRating" to "",
            "distance" to "10",
            "price" to "30",
            "cuisine" to "Mex"
        )

        val response = restTemplate.getForEntity(buildUrlWithParams(urlApiFilter, params), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with empty values in URL, distance`() {
        val params = mapOf(
            "name" to "Deli",
            "customerRating" to "3",
            "distance" to "",
            "price" to "30",
            "cuisine" to "Mex"
        )

        val response = restTemplate.getForEntity(buildUrlWithParams(urlApiFilter, params), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with empty values in URL, price`() {
        val params = mapOf(
            "name" to "Deli",
            "customerRating" to "3",
            "distance" to "10",
            "price" to "",
            "cuisine" to "Mex"
        )

        val response = restTemplate.getForEntity(buildUrlWithParams(urlApiFilter, params), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with empty values in URL, cuisine`() {
        val params = mapOf(
            "name" to "Deli",
            "customerRating" to "3",
            "distance" to "10",
            "price" to "30",
            "cuisine" to ""
        )

        val response = restTemplate.getForEntity(buildUrlWithParams(urlApiFilter, params), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with missing values in URL`() {
        val params = mapOf(
            "name" to "Deli",
            "customerRating" to "3",
            "price" to "30"
        )

        val response = restTemplate.getForEntity(buildUrlWithParams(urlApiFilter, params), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with missing values in URL, just name`() {
        val params = mapOf(
            "name" to "Deli"
        )

        val response = restTemplate.getForEntity(buildUrlWithParams(urlApiFilter, params), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with missing values in URL, just customerRating`() {
        val params = mapOf(
            "customerRating" to "3"
        )

        val response = restTemplate.getForEntity(buildUrlWithParams(urlApiFilter, params), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with missing values in URL, just distance`() {
        val params = mapOf(
            "distance" to "5"
        )

        val response = restTemplate.getForEntity(buildUrlWithParams(urlApiFilter, params), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with missing values in URL, just price`() {
        val params = mapOf(
            "price" to "30"
        )

        val response = restTemplate.getForEntity(buildUrlWithParams(urlApiFilter, params), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with missing values in URL, just cuisine`() {
        val params = mapOf(
            "cuisine" to "Mex"
        )

        val response = restTemplate.getForEntity(buildUrlWithParams(urlApiFilter, params), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with JSON in body`() {
        val requestBody = """
            {
                "name": "yummy",
                "customerRating": 3,
                "price": 30,
                "distance": 5,
                "cuisine": "Mex"
            }
        """.trimIndent()

        val response = restTemplate.exchange(urlApiFilter, HttpMethod.GET, HttpEntity(requestBody), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with JSON in body with empty values, name`() {
        val requestBody = """
            {
                "name": "yummy",
                "customerRating": 3,
                "price": 30,
                "distance": 5,
                "cuisine": "Mex"
            }
        """.trimIndent()

        val response = restTemplate.exchange(urlApiFilter, HttpMethod.GET, HttpEntity(requestBody), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with JSON in body with empty values, customerRating`() {
        val requestBody = """
            {
                "name": "yummy",
                "customerRating": ,
                "price": 30,
                "distance": 5,
                "cuisine": "Mex"
            }
        """.trimIndent()

        val response = restTemplate.exchange(urlApiFilter, HttpMethod.GET, HttpEntity(requestBody), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with JSON in body with empty values, price`() {
        val requestBody = """
            {
                "name": "yummy",
                "customerRating": 3,
                "price": ,
                "distance": 5,
                "cuisine": "Mex"
            }
        """.trimIndent()

        val response = restTemplate.exchange(urlApiFilter, HttpMethod.GET, HttpEntity(requestBody), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with JSON in body with empty values, distance`() {
        val requestBody = """
            {
                "name": "yummy",
                "customerRating": 3,
                "price": 30,
                "distance": ,
                "cuisine": "Mex"
            }
        """.trimIndent()

        val response = restTemplate.exchange(urlApiFilter, HttpMethod.GET, HttpEntity(requestBody), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with JSON in body with empty values, cuisine`() {
        val requestBody = """
            {
                "name": "yummy",
                "customerRating": 3,
                "price": 30,
                "distance": 5,
                "cuisine": ""
            }
        """.trimIndent()

        val response = restTemplate.exchange(urlApiFilter, HttpMethod.GET, HttpEntity(requestBody), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }

    @Test
    fun `test findByFilter with JSON in body with missin values`() {
        val requestBody = """
            {
                "customerRating": 3,
                "price": 30,
                "cuisine": ""
            }
        """.trimIndent()

        val response = restTemplate.exchange(urlApiFilter, HttpMethod.GET, HttpEntity(requestBody), String::class.java)

        assertEquals(200, response.statusCode.value())
        assertEquals(MediaType.APPLICATION_JSON, response.headers.contentType)
    }
}