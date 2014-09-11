package edu.miamioh.cse283.lab2;

import java.io.*;
import java.net.*;

/**
 * Template server for CSE283 Lab2, FS2014.
 * 
 * This server should respond to a client with a sequence of packets 
 * sent at a rate and size determined by the client. 
 * 
 * @author Sam Bowdler, with assistance from my colleague Kyle Richardson
 */
public class Lab2Server {
	public static final int PORT=4242;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		DatagramSocket s=null;

		try {
			// construct a datagram socket listening on port PORT:
			s = new DatagramSocket(PORT);
			
			// for convenience, the server should tell us what addresses it's listening on;
			// see DatagramSocket.getLocalSocketAddress() and InetAddress.getLocalHost().
			System.out.printf("Lab2Server listening on %s:%s \n", InetAddress.getLocalHost(), PORT);
			
			// you will probably want to output something like:
			//   "Lab2Server listening on: <ip address>:<port>"
			
			while(true) {
				// receive a datagram packet that tells the server how many packets to send, their size in bytes, and their rate:
				byte[] but = new byte[5];
				DatagramPacket puck = new DatagramPacket(but, 5);
				s.receive(puck);
				InetAddress ia = puck.getAddress();
				int cPort = puck.getPort();

				// for each packet you're supposed to send:
				ByteArrayInputStream bais = new ByteArrayInputStream(puck.getData());
				DataInputStream dis = new DataInputStream(bais);
				byte delur = dis.readByte();
				short numPack = dis.readShort();
				short len = dis.readShort();
				
				// - assemble the packet
				
				// - wait the right amount of time to hit the requested sending rate
				// see: Object.wait(long millis) and the concurrency lesson listed in the lab description
				
				// - send the packet
				// end loop
				for (int i = 0; i < numPack; i++){
					DatagramPacket goPackers = new DatagramPacket(new byte[len], len, ia, cPort);
					Thread.sleep(delur);
					s.send(goPackers);
				}
				
				byte[] bits = {-1};
				for (int i = 0; i < 5; i++)
					s.send(new DatagramPacket(bits, 1, ia, cPort));
				
				System.out.println("Finished sending packets");
			}
		} catch(SocketException ex) { // this will not compile until you start filling in the socket code
			System.out.println("Could not open socket (is the server already running?).");
		} finally {
			if(s != null) {
				s.close();
			}
		}
	} 
}
