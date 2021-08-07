package cz.grunwjir.csasdemo


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class CsasDemoApplicationTests extends Specification {

    @Autowired(required = false)
    private ApplicationContext applicationContext

    def "Application context should be loaded"() {
        expect:
        applicationContext != null
    }

}
