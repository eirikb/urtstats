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
    boolean tailing

    Tail(File logFile, boolean gotoEOF) {
        this.logFile = logFile
        if (gotoEOF) {
            filePointer = logFile.length()
        }
        tailing = false
    }

    synchronized String parse() {
        if (!tailing) {
            tailing = true
            def line
            if (logFile.length() > filePointer) {
                def raf = new RandomAccessFile(logFile, "r")
                try {
                    raf.seek((int) filePointer)
                    line = raf.readLine()
                    filePointer = raf.getFilePointer()
                } finally {
                    tailing = false
                    raf.close()
                }
            }
            return line
        }
    }


    String parseReverse() {
        def raf = new RandomAccessFile(logFile, "r")
        def line
        while (filePointer > 0 && (line == null || line.length() == 0)) {
            filePointer--
            raf.seek((int) filePointer)
            line = raf.readLine()
        }
        def tempLine = line
        while (filePointer > 0 && line.length() <= tempLine.length()) {
            filePointer--
            raf.seek((int) filePointer)
            line = tempLine
            tempLine = raf.readLine()
            if (filePointer == 0) {
                line = tempLine
            }
        }
        raf.close()
        return line
    }

    long getFilePointer() {
        return filePointer
    }

    void setFilePointer(filePointer) {
        this.filePointer = filePointer
    }
}
