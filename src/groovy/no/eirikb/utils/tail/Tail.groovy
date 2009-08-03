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
    final long MAXBUFFER = 65000

    Tail(File logFile, boolean gotoEOF) {
        this.logFile = logFile
        if (gotoEOF) {
            filePointer = logFile.length()
        }
    }

    synchronized String parse() {
        if (logFile.length() > filePointer) {
            def raf = new RandomAccessFile(logFile, "r")
            def rafString = ""
            raf.seek((int) filePointer)
            def bufferLength = logFile.length() - raf.getFilePointer()

            def c = { buffer ->
                raf.readFully(buffer)
                def newLinePos = getNewLinePos(buffer)
                if (newLinePos >= 0) {
                    filePointer += newLinePos + 1
                    def s = new String(buffer, 0, newLinePos)
                    rafString += s
                    return true
                } else {
                    rafString += new String(buffer)
                }
                return false
            }

            while (bufferLength > MAXBUFFER) {
                if (c(new byte[MAXBUFFER])) {
                    raf.close()
                    return rafString
                }
                bufferLength = bufferLength - MAXBUFFER
            }
            if (c(new byte[bufferLength])) {
                raf.close()
                return rafString
            }
            raf.close()
            return rafString
            raf.close()
        }
        return null
    }

    int getNewLinePos(buffer) {
        for (i in 0..buffer.length - 1) {
            if (buffer[i] == 10 || buffer[i] == 13) {
                return i
            }
        }
        return -1
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
