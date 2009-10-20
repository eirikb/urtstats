/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.urtstats.utils

import org.codehaus.groovy.grails.commons.*
import grails.util.GrailsUtil
import org.apache.commons.logging.LogFactory

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */

class RCon {

    final static int BUFFERSIZE = 65000
    static boolean active = true

    public synchronized static String rcon(message) {
        return rcon(message, false)
    }

    public synchronized static String rcon(message, force) {
        def log = LogFactory.getLog("grails.app.task")
        log.info "RCon: Message: " + message + ". Force: " + force
        def recmessage
        if (GrailsUtil.environment != "test" && active) {
            def config = ConfigurationHolder.config
            def host = config.urt.rcon.host
            def port = config.urt.rcon.port
            def password = config.urt.rcon.password
            recmessage = rconSend(host, port, password, message, force)
        } else if (!active) {
            log.info "RCon: Message not sent - NOT ACTIVE!"
        }
        return recmessage
    }
    
    public static String rconSend(host, port, password, message, force) {
        def socket
        try {
            message = "rcon " + password + " " + message

            socket = new DatagramSocket()
            def buff = ("    " + message).getBytes()
            buff[0] = (byte) 0xff
            buff[1] = (byte) 0xff
            buff[2] = (byte) 0xff
            buff[3] = (byte) 0xff
            def packet = new DatagramPacket(buff, buff.length,
                InetAddress.getByName(host), port)
            socket.send(packet)
            if (force) {
                def recmessage
                buff = new byte[BUFFERSIZE]
                packet = new DatagramPacket(buff, buff.length)
                socket.receive(packet)
                while (packet.getLength() > 0) {
                    def part = new String(packet.getData(), 4, packet.getLength() - 4)
                    def splitPos = part.indexOf('\n')
                    if (splitPos >= 0) {
                        part = part.substring(splitPos + 1)
                        if (recmessage == null) {
                            recmessage = part
                        } else {
                            recmessage += part
                        }
                        socket.receive(packet)
                    }
                }
                socket?.close()
                // Sleep for 5 seconds to wait for response
                if (force) {
                    for (i in 0..40) {
                        if (recmessage != null) {
                            return recmessage
                        }
                        Thread.sleep(100)
                    }
                }
            }
        } catch(IOException io){
            println "ERRROR! IO - RCon"
        }
        return null
    }
}

