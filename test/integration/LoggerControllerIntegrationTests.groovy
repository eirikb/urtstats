import grails.test.*

class LoggerControllerIntegrationTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSomething() {
        def logger = new LoggerController()
        logger.index()
    }
}
