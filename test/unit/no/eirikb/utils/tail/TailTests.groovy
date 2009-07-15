package no.eirikb.utils.tail

import grails.test.*

class TailTests extends GrailsUnitTestCase {
    def logFile
    def testData = ["This is a", "test string", "for testing the Tail", " class"]

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
        logFile.delete()
    }

    void testParse() {
        createTestFile(testData)
        def tail = new Tail(logFile, false)
        assertNotNull tail
        assert tail.parse() == testData[0]
        assert tail.parse() == testData[1]
        assert tail.parse() == testData[2]
        tail.setFilePointer(testData[0].length() + 1)
        assert tail.parse() == testData[1]
        tail.setFilePointer(0)
        for (i in 0..3 ) {
            assert tail.parse() == testData[i]
        }
        assert tail.parse() == null
        assert tail.parse() == null
    }

    void createTestFile(testData) {
        logFile = new File("testLogfile.log")
        logFile.delete()
        assert logFile.createNewFile()
        assert logFile.getName() == "testLogfile.log"
        def length = 0
        testData.each {
            length += it.length()
            logFile << it + '\n'
        }
        assert logFile.length() == length + testData.size()
    }
}
