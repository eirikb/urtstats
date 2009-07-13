/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */

import org.codehaus.groovy.grails.commons.*

class LogParser {
    File logFile
    List parseListeners
    long filePointer
    long filePointerReverse

    LogParser(File logFile, boolean gotoEOF) {
        this.logFile = logFile
        parseListeners = []
        if (gotoEOF) {
            filePointer = logFile.length()
            filePointerReverse = filePointer
        }
    }

    void parse() {
        if (logFile.length() > filePointer) {
            def raf = new RandomAccessFile(logFile, "r")
            raf.skipBytes((int) filePointer)
            def line
            while ((line = raf.readLine()) != null) {
                readLine(line)
                filePointer = raf.getFilePointer()
            }
            raf.close()
        }
    }

    String parseReverse(searchFor, stopAt) {
        def search = true
        def line2
        while (filePointerReverse > 0 && search) {
            def raf = new RandomAccessFile(logFile, "r")
            raf.seek(filePointerReverse)
            def line
            def tempPos = filePointerReverse
            while (tempPos > 0 && (line == null || line.length() == 0 ||
                    filePointerReverse - (tempPos + line.length()) <= 2)) {
                line = raf.readLine()
                tempPos--
                raf.seek(tempPos)
                if (line != null) {
                    if (filePointerReverse - (tempPos + line.length()) <= 2) {
                        line2 = line
                    }
                }
            }
            raf.close()
            filePointerReverse = tempPos
            if (line2.indexOf(searchFor) >= 0) {
                search = false
            } else if (filePointerReverse == 0) {
                println("While parseReverse: EOF")
                line2 = null
            } else if (line2.indexOf(stopAt) >= 0) {
                println("While parseReverse: Found stopAt (" + stopAt + ")")
                search = false
                line2 = null
            }
        }
        return line2
    }

    private void readLine(String line) {
        StringTokenizer st = new StringTokenizer(line, " ")
        if (st.hasMoreTokens()) {
            String cmdString = st.nextToken()
            if (st.hasMoreTokens()) {
                cmdString = cmdString.substring(0, cmdString.length() - 1).toUpperCase()

                switch (cmdString) {
                    case "CLIENTCONNECT":
                    //println "CONNECT " + id + "(care)"
                    break

                    case "CLIENTUSERINFO":
                    def id = getId(st)
                    if (line.indexOf("cl_guid") > 0) {
                        def userInfoString = line.substring(line.indexOf('\\'))
                        def userInfo = getUserInfo(userInfoString)
                        parseListeners.each() { it.userInfo(id, userInfo) }
                    }
                    break

                    case "CLIENTUSERINFOCHANGED":
                    def id = getId(st)
                    def userInfoString = st.nextToken()
                    def userInfo = getUserInfo(userInfoString)
                    parseListeners.each() { it.userInfoChange(id, userInfo) }
                    break

                    case "CLIENTDISCONNECT":
                    def id = getId(st)
                    parseListeners.each() { it.leave(id) }
                    break

                    case "KILL":
                    def id = getId(st)
                    def killedID = getInt(st.nextToken())
                    def type = getInt(st.nextToken())
                    parseListeners.each() { it.kill(id, killedID, type)}
                    break

                    case "SAY":
                    def id = getId(st)
                    def message = line.substring(line.indexOf(':') + 1)
                    message = message.substring(message.indexOf(':') + 2)
                    parseListeners.each() { it.chat(id, false, message) }
                    break

                    case "TEAMSAY":
                    def id = getId(st)
                    def message = line.substring(line.indexOf(':') + 1)
                    message = message.substring(message.indexOf(':') + 2)
                    parseListeners.each() { it.chat(id, true, message) }

                    case "HIT":
                    def id = getId(st)
                    def hitterID = getInt(st.nextToken())
                    def hitpoint = getInt(st.nextToken())
                    def weapon = getInt(st.nextToken())
                    parseListeners.each() { it.hit(hitterID, id, hitpoint, weapon) }
                    break

                    case "INITROUND":
                    def roundInfo = getUserInfo(st.nextToken())
                    parseListeners.each() { it.initRound(roundInfo) }
                    break

                    case "SERVER":
                    parseListeners.each() { it.serverStart() }
                    break
                }
              
            }
        }
    }

    private int getId(st) {
        try {
            return Integer.parseInt(st.nextToken())
        } catch (NumberFormatException e) {}
        return -1;
    }

    public Map getUserInfo(userInfoString) {
        if (userInfoString.charAt(0) == '\\') {
            userInfoString = userInfoString.substring(1)
        }
        def userInfo = [:]
        def st = userInfoString.split("\\\\")
        def i = 0
        while (i < st.length) {
            def s = st[i++]
            def s2 = st[i++]
            userInfo.put(s, s2);
        }
        return userInfo;
    }

    private  Integer getInt(token) {
        int i = token.indexOf(':')
        if (i > 0) {
            token = token.substring(0, i)
        }
        try {
            return Integer.parseInt(token.trim());
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    void addParseListener(parseListener) {
        parseListeners.add(parseListener)
    }
}

