import com.example.bestmatchedrestaurants.repository.ICuisineRepository
import com.example.bestmatchedrestaurants.repository.IRestaurantRepository
import com.example.bestmatchedrestaurants.utils.CsvReader
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import java.util.*

@SpringBootTest(classes = [CsvReader::class])
@ActiveProfiles("test")
class CsvReaderTest {

    @Autowired
    private lateinit var csvReader: CsvReader

    @MockBean
    private lateinit var ICuisineRepository: ICuisineRepository

    @MockBean
    private lateinit var restaurantRepository: IRestaurantRepository

    @BeforeEach
    fun setUp() {
        val cuisines = csvReader.readCuisiceCsv("/static/cuisines.csv")
        cuisines.forEach { cuisine ->
            `when`(ICuisineRepository.findById(cuisine.id)).thenReturn(Optional.of(cuisine))
        }
    }

    @Test
    fun `test readCuisineCsv`() {
        val cuisines = csvReader.readCuisiceCsv("/static/cuisines.csv")
        assertEquals(19, cuisines.size)
    }

    @Test
    fun `test readRestaurantCsv`() {
        csvReader.readCuisiceCsv("/static/cuisines.csv")
        val restaurants = csvReader.readRestaurantCsv("/static/restaurants.csv")
        assertEquals(200, restaurants.size)

    }

    @Test
    fun `test readCuisineCsv with invalid file`() {
        //If it returns a NullPointerException, it means that a file was not found
        assertThrows<NullPointerException> {
            csvReader.readCuisiceCsv("/static/cuis.csv")
        }
    }

    @Test
    fun `test readRestaurantCsv with invalid file`() {
        //If it returns a NullPointerException, it means that a file was not found
        assertThrows<NullPointerException> {
            csvReader.readRestaurantCsv("/static/rest.csv")
        }
    }
}