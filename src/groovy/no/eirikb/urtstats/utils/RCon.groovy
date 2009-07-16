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

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */

class RCon {

    final static int BUFFERSIZE = 65000
    final static int SLEEPTIME = 1000
    static long lastUsed
    static String recmessage

    public synchronized static String rcon(message) {
        return rcon(message, false)
    }

    public synchronized static String rcon(message, force) {
        def recmessage
        if (GrailsUtil.environment != "test") {
            def config = ConfigurationHolder.config
            def host = config.urt.rcon.host
            def port = config.urt.rcon.port
            def password = config.urt.rcon.password
            if (force) {
                while ((recmessage = rconSend(host, port, password, message))?.length() == 6);
            } else {
                recmessage = rconSend(host, port, password, message)
            }
        }
        return recmessage
    }
    
    private static String rconSend(host, port, password, message) {
        while (System.currentTimeMillis() - lastUsed < SLEEPTIME) {
            Thread.sleep(SLEEPTIME)
        }
        def socket
        try {
            message = "rcon\r" + password + "\r\"" + message + "\"\0";

            socket = new DatagramSocket()
            def buff = ("    " + message).getBytes()
            buff[0] = (byte) 0xff
            buff[1] = (byte) 0xff
            buff[2] = (byte) 0xff
            buff[3] = (byte) 0xff
            def packet = new DatagramPacket(buff, buff.length,
                InetAddress.getByName(host), port)
            socket.send(packet)
            new Thread() {
                buff = new byte[BUFFERSIZE]
                packet = new DatagramPacket(buff, buff.length)
                socket.receive(packet)
                recmessage = new String(packet.getData(), 4, packet.getLength() - 4)
                socket?.close()
            }.start()
            // Sleep for 5 seconds to wait for response
            for (i in 0..4) {
                if (recmessage != null) {
                    return recmessage
                }
                Thread.sleep(1000)
            }
        } catch(IOException io){
            println "ERRROR! IO - RCon"
        } finally {
            lastUsed = System.currentTimeMillis()
        }
        return null
    }
}

