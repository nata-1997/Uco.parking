package co.edu.uco.UcoParking.Initializer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import co.edu.uco.UcoParking.testsupport.TestCacheConfiguration;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestCacheConfiguration.class)
class ucoParkingApplicationTests {

	@Test
	void contextLoads() {
	}

}
