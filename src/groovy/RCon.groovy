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
class RCon {

    static final int BUFFERSIZE = 65507

    static void rcon(host, port, password, message) {
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
        //byte[] data = new byte[BUFFERSIZE];
        //dp = new DatagramPacket(data, data.length);
        //ds.receive(dp);
        //int length = dp.getLength();
        //String s = new String(data, 4, length)
        //ds.close()
        //return s;
    }
}

