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
class LogParser {
	File logFile
    List parseListeners
    boolean tail
    long filePointer

    LogParser(File logFile, boolean tail, boolean gotoEOF) {
        this.logFile = logFile
        this.tail = tail
        parseListeners = []
        if (tail && gotoEOF) {
            filePointer = logFile.length()
        }
    }

    void parse() {
        while (filePointer == 0 || tail) {
            if (logFile.length() > filePointer) {
                def raf = new RandomAccessFile(logFile, "r")
                raf.skipBytes((int) filePointer)
                def line
                while ((line = raf.readLine()) != null) {
                    readLine(line)
                    filePointer = raf.getFilePointer()
                }
            }
            if (filePointer == 0 || tail) {
                println "done! Sleep!"
                Thread.sleep(1000);
            }
        } 
    }

    private void readLine(String line) {
        StringTokenizer st = new StringTokenizer(line, " ")
        if (st.hasMoreTokens()) {
            String cmdString = st.nextToken()
            if (st.hasMoreTokens()) {
                cmdString = cmdString.substring(0, cmdString.length() - 1).toUpperCase()
                try {
                    def id = Integer.parseInt(st.nextToken())
                    switch (cmdString) {

                        case "CLIENTCONNECT":
                        //println "CONNECT " + id + "(care)"
                        break

                        case "CLIENTUSERINFO":
                        if (line.indexOf("challenge") > 0) {
                            def userInfoString = line.substring(line.indexOf('\\'))
                            def userInfo = getUserInfo(userInfoString)
                            parseListeners.each() { it.userInfo(id, userInfo) }
                        }
                        break

                        case "CLIENTUSERINFOCHANGED":
                        def userInfoString = st.nextToken()
                        def userInfo = getUserInfo(userInfoString)
                        parseListeners.each() { it.userInfoChange(id, userInfo) }
                        break

                        case "CLIENTDISCONNECT":
                        parseListeners.each() { it.leave(id) }
                        break

                        case "KILL":

                        def killedID = getInt(st.nextToken())
                        def type = getInt(st.nextToken())
                        parseListeners.each() { it.kill(id, killedID, type)}
                        break

                        case "SAY":
                        def message = line.substring(line.indexOf(':') + 2)
                        parseListeners.each() { it.chat(id, false, message) }
                        break

                        case "TEAMSAY":
                        def message = line.substring(line.indexOf(':') + 2)
                        parseListeners.each() { it.chat(id, true, message) }

                        case "HIT":
                        def hitterID = getInt(st.nextToken())
                        def hitpoint = getInt(st.nextToken())
                        def weapon = getInt(st.nextToken())
                        parseListeners.each() { it.hit(hitterID, id, hitpoint, weapon) }
                        break
                    }
                } catch (NumberFormatException e) {}
            }
        }
    }

    private Map getUserInfo(userInfoString) {
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

