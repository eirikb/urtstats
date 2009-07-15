/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.utils.tail

/**
 * This class is made for "tailing" files.
 * It read one and one line from a file,
 * starting from beginning or EOF.
 * Can also parse backwards
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class Tail {
    File logFile
    long filePointer

    Tail(File logFile, boolean gotoEOF) {
        this.logFile = logFile
        if (gotoEOF) {
            filePointer = logFile.length()
        }
    }

    String parse() {
        def line
        if (logFile.length() > filePointer) {
            def raf = new RandomAccessFile(logFile, "r")
            raf.seek((int) filePointer)
            line = raf.readLine()
            filePointer = raf.getFilePointer()
            raf.close()
        }
        return line
    }


    String parseReverse(searchFor, stopAt) {
        def raf = new RandomAccessFile(logFile, "r")
        raf.seek(filePointer)
        def line
        def tempPos = filePointer
        while (tempPos > 0 && (line == null || line.length() == 0 ||
                filePointer - (tempPos + line.length()) <= 2)) {
            line = raf.readLine()
            tempPos--
            raf.seek(tempPos)
            if (line != null) {
                if (filePointer - (tempPos + line.length()) <= 2) {
                    line2 = line
                }
            }
        }
        raf.close()
        filePointer = tempPos
        return line
    }

    long getFilePointer() {
        return filePointer
    }

    void setFilePointer(filePointer) {
        this.filePointer = filePointer
    }

    void readLine(line) {
        tailListeners.each {
            it.readLine(line)
        }
    }
}
