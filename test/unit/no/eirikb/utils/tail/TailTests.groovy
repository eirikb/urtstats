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
        logFile?.delete()
    }

    void testParse() {
        createTestFile(testData)
        def tail = new Tail(logFile, false)
        assertNotNull tail

        assertEquals tail.parse(), testData[0]
        assertEquals tail.parse(), testData[1]
        assertEquals tail.parse(), testData[2]
        tail.setFilePointer(testData[0].length() + 1)
        assertEquals tail.parse(), testData[1]
        tail.setFilePointer(0)
        for (i in 0..3 ) {
            assertEquals tail.parse(), testData[i]
        }
        assertEquals tail.parse(), null
        assertEquals tail.parse(), null
    }

    void testParseReverse() {
        createTestFile(testData)
        def tail = new Tail(logFile, false)
        assertNotNull tail

        assertEquals tail.parseReverse(), null

        tail = new Tail(logFile, true)
        assertNotNull tail

        assertEquals tail.parseReverse(), testData[testData.size() - 1]

        tail.setFilePointer(logFile.length())
        for (i in 3..0) {
            assertEquals tail.parseReverse(), testData[i]
        }
    }

    void testFlood() {
        createTestFile(testData)
        def tail = new Tail(logFile, true)
        Thread.start {
            for (i in 0..100000) {
                logFile << "test ${i}\n"
                sleep(2)
            }
        }
        Thread.start {
            for (i in 0..100000) {
                println tail.parse()
                sleep(1)
            }
        }
        Thread.sleep(1000)
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
