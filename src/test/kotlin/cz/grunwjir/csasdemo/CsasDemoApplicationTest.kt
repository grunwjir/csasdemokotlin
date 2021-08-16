package cz.grunwjir.csasdemo


import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
class CsasDemoApplicationTest(val applicationContext : ApplicationContext?) {

    @Test
    fun `Application context should be loaded`() {
        assert(applicationContext != null)
    }

}
