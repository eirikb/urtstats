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

class RCon {

    final static int BUFFERSIZE = 65507
    static long lastUsed

    public synchronized static String rcon(message) {
        if (System.currentTimeMillis() - lastUsed < 1000) {
            Thread.sleep(1000)
        }
        try {
            def config = ConfigurationHolder.config
            def host = config.urt.rcon.host
            def port = config.urt.rcon.port
            def password = config.urt.rcon.password
            message = "rcon\r" + password + "\r\"" + message + "\"\0";
            DatagramSocket ds;
            DatagramPacket dp;
            InetAddress ia;
            ds = new DatagramSocket();
            ia = InetAddress.getByName(host);
            String out = "xxxx" + message;
            byte[] buff = out.getBytes();
            buff[0] = (byte) 0xff;
            buff[1] = (byte) 0xff;
            buff[2] = (byte) 0xff;
            buff[3] = (byte) 0xff;
            dp = new DatagramPacket(buff, buff.length, ia, port);
            ds.send(dp);
            byte[] data = new byte[BUFFERSIZE];
            dp = new DatagramPacket(data, data.length);
            ds.receive(dp);
            int length = dp.getLength();
            String s = new String(data, 4, length)
            ds.close()
            return s
        } catch (Exception e) {
            println "Error!"
        } finally {
            lastUsed = System.currentTimeMillis()
        }
        return null
    }
}

