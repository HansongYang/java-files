import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Scanner;

public class TFTPSim {
	//UDP datagram packets and sockets used to send / receive
	private static DatagramPacket sendPacket;
	private static DatagramPacket receivePacket;
	private static DatagramSocket receiveSocket;
	private static DatagramSocket sendSocket;
	private static DatagramSocket sendReceiveSocket;
	private int serverPort = 69;
	private InetAddress ip;
	public FileCopier simCopier;
	
	public TFTPSim()
	{
		try {
			// Construct a datagram socket and bind it to port 23
			// on the local host machine. This socket will be used to
			// receive UDP Datagram packets from clients.
			receiveSocket = new DatagramSocket(23);
			// Construct a datagram socket and bind it to any available
			// port on the local host machine. This socket will be used to
			// send and receive UDP Datagram packets from the server.
			sendReceiveSocket = new DatagramSocket();
			simCopier = new FileCopier(this);
		} catch (SocketException se) {
			se.printStackTrace();
			System.exit(1);
		}
	}

	public static int ui(){		
		Scanner sc = new Scanner(System.in);
		int answer = 0;
		System.out.println("Test Menu");
		System.out.println("0: Normal Operation.");
		System.out.println("1: Invalid TFTP opcode on RRQ (Error code 4) ");
		System.out.println("2: Invalid TFTP opcode on WRQ (Error code 4) ");
		System.out.println("3: Invalid mode (Error code 4) ");
		System.out.println("4: Unknown transfer ID (Error code 5) ");
		System.out.println("5. Lose a packet.");
		System.out.println("6. Delay a packet.");
		System.out.println("7. Duplicate a packet.");
		System.out.println("8: Quit");
		answer = sc.nextInt();
				
		return answer;
	}
		
	public void InvalidRRQ(){
		byte[] data;
		int clientPort;

		for(;;) { // loop forever
			data = new byte[512];
			receivePacket = new DatagramPacket(data, data.length);

			System.out.println("Simulator: Waiting for packet.");
			// Block until a datagram packet is received from receiveSocket.
			try {
				receiveSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			// Process the received datagram.
			System.out.println("Simulator: Packet received:");
			System.out.println("From host: " + receivePacket.getAddress());
			clientPort = receivePacket.getPort();
			System.out.println("Host port: " + clientPort);
			ip = receivePacket.getAddress();
			// Form a String from the byte array, and print the string.
			String received = new String(data,0,receivePacket.getLength());
			System.out.println(received);
		
			//send error
			byte error [] = data;
			if (error[1] == 1){
				error[1] = 5;
				System.out.println("Invalid Read request. Error code 4");
				try{
					sendPacket = new DatagramPacket(error, error.length,
						InetAddress.getLocalHost(), serverPort);
				}catch (UnknownHostException e) {
				     e.printStackTrace();
				     System.exit(1);
				  }
			}			
			else {
				try{
					sendPacket = new DatagramPacket(data, receivePacket.getLength(),
					InetAddress.getLocalHost(), serverPort);
				}catch (UnknownHostException e) {
				     e.printStackTrace();
				     System.exit(1);
				}
			}
		
			System.out.println("Simulator: sending packet.");

		// Send the datagram packet to the server via the send/receive socket.
			try {
				sendReceiveSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			// Construct a DatagramPacket for receiving packets up
			// to 512 bytes long (the length of the byte array).
			data = new byte[512];
			receivePacket = new DatagramPacket(data, data.length);

			System.out.println("Simulator: Waiting for packet.");
			try {
				// Block until a datagram is received via sendReceiveSocket.
				sendReceiveSocket.receive(receivePacket);
			} catch(IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			// Process the received datagram.
			System.out.println("Simulator: Packet received:");
			System.out.println("From host: " + receivePacket.getAddress());
			System.out.println("Host port: " + receivePacket.getPort());

			sendPacket = new DatagramPacket(data, receivePacket.getLength(),
					ip, clientPort);

			System.out.println("Simulator: Sending packet.");

			// Send the datagram packet to the client via a new socket.
			try {
				// Construct a new datagram socket and bind it to any port
				// on the local host machine. This socket will be used to
				// send UDP Datagram packets.
				sendSocket = new DatagramSocket();
			} catch (SocketException se) {
				se.printStackTrace();
				System.exit(1);
			}

			try {
				sendSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			System.out.println("Simulator: packet sent using port " + sendSocket.getLocalPort());
			System.out.println();

			// We're finished with this socket, so close it.
			sendSocket.close();
		} // end of loop
	}
	
	public void InvalidWRQ () {
		byte[] data;
		int clientPort;

		for(;;) { // loop forever
			data = new byte[512];
			receivePacket = new DatagramPacket(data, data.length);

			System.out.println("Simulator: Waiting for packet.");
			// Block until a datagram packet is received from receiveSocket.
			try {
				receiveSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			// Process the received datagram.
			System.out.println("Simulator: Packet received:");
			System.out.println("From host: " + receivePacket.getAddress());
			clientPort = receivePacket.getPort();
			System.out.println("Host port: " + clientPort);
			ip = receivePacket.getAddress();
			
			// Form a String from the byte array, and print the string.
			String received = new String(data,0,receivePacket.getLength());
			System.out.println(received);
		
			//send error
			byte error [] = data;
			if (error[1] == 2){
				error[1] = 5;
				System.out.println("Invalid Write request. Error code 4.");
				try{
					sendPacket = new DatagramPacket(error, error.length,
					InetAddress.getLocalHost(), serverPort);
				}catch (UnknownHostException e) {
				     e.printStackTrace();
				     System.exit(1);
				}
			}else{
				try{
					sendPacket = new DatagramPacket(data, receivePacket.getLength(), InetAddress.getLocalHost(), serverPort);
				}catch (UnknownHostException e) {
				     e.printStackTrace();
				     System.exit(1);
				}
			}
					
			System.out.println("Simulator: sending packet.");
			// Send the datagram packet to the server via the send/receive socket.

			try {
				sendReceiveSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			// Construct a DatagramPacket for receiving packets up
			// to 512 bytes long (the length of the byte array).
			data = new byte[512];
			receivePacket = new DatagramPacket(data, data.length);

			System.out.println("Simulator: Waiting for packet.");
			try {
				// Block until a datagram is received via sendReceiveSocket.
				sendReceiveSocket.receive(receivePacket);
			} catch(IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			// Process the received datagram.
			System.out.println("Simulator: Packet received:");

			sendPacket = new DatagramPacket(data, receivePacket.getLength(), ip, clientPort);
			System.out.println( "Simulator: Sending packet:");
			System.out.println("To host: " + sendPacket.getAddress());
			System.out.println("Destination host port: " + sendPacket.getPort());

			// Send the datagram packet to the client via a new socket.
			try {
				// Construct a new datagram socket and bind it to any port
				// on the local host machine. This socket will be used to
				// send UDP Datagram packets.
				sendSocket = new DatagramSocket();
			} catch (SocketException se) {
				se.printStackTrace();
				System.exit(1);
			}

			try {
				sendSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			System.out.println("Simulator: packet sent using port " + sendSocket.getLocalPort());
			System.out.println();

			// We're finished with this socket, so close it.
			sendSocket.close();
		} // end of loop
	}
	
	public void InvalidMode () {
		byte[] data;
		int clientPort;

		for(;;) { // loop forever
			data = new byte[512];
			receivePacket = new DatagramPacket(data, data.length);
			System.out.println("Simulator: Waiting for packet.");
			// Block until a datagram packet is received from receiveSocket.
			try {
				receiveSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			// Process the received datagram.
			System.out.println("Simulator: Packet received:");
			System.out.println("From host: " + receivePacket.getAddress());
			clientPort = receivePacket.getPort();
			System.out.println("Host port: " + clientPort);
			ip = receivePacket.getAddress();

			// Form a String from the byte array, and print the string.
			String received = new String(data,0,receivePacket.getLength());
			System.out.println(received);
	
			//send packet with error mode
			byte error[] = data;
			int count = 0;
			for(int i = 0; i < error.length; i++){
				if (error[i] == 0){
					if(count == 1){
						for(int k = i+1; k < error.length; k++){
							if(error[k] == 0){
								System.out.println("Invalid mode!!! Error code 4");
								break;
							}
							error[k] = 0;
						}
						break;
					}
					count++;
				}
			}
			count = 0;
			
			try{
				sendPacket = new DatagramPacket(error, receivePacket.getLength(), InetAddress.getLocalHost(), serverPort);
			}catch (UnknownHostException e) {
			     e.printStackTrace();
			     System.exit(1);
			}
					
			System.out.println("Simulator: sending packet.");
			System.out.println("To host: " + sendPacket.getAddress());
			System.out.println("Destination host port: " + sendPacket.getPort());

			// Send the datagram packet to the server via the send/receive socket.
			try {
				sendReceiveSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			// Construct a DatagramPacket for receiving packets up
			// to 512 bytes long (the length of the byte array).
			data = new byte[512];
			receivePacket = new DatagramPacket(data, data.length);

			System.out.println("Simulator: Waiting for packet.");
			try {
				// Block until a datagram is received via sendReceiveSocket.
				sendReceiveSocket.receive(receivePacket);
			} catch(IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			// Process the received datagram.
			System.out.println("Simulator: Packet received:");
			System.out.println("From host: " + receivePacket.getAddress());
			System.out.println("Host port: " + receivePacket.getPort());

			sendPacket = new DatagramPacket(data, receivePacket.getLength(),
					ip, clientPort);

			System.out.println( "Simulator: Sending packet:");
			System.out.println("To host: " + sendPacket.getAddress());
			System.out.println("Destination host port: " + sendPacket.getPort());

			// Send the datagram packet to the client via a new socket.

			try {
				// Construct a new datagram socket and bind it to any port
				// on the local host machine. This socket will be used to
				// send UDP Datagram packets.
				sendSocket = new DatagramSocket();
			} catch (SocketException se) {
				se.printStackTrace();
				System.exit(1);
			}

			try {
				sendSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			System.out.println("Simulator: packet sent using port " + sendSocket.getLocalPort());
			System.out.println();

			// We're finished with this socket, so close it.
			sendSocket.close();
		} // end of loop
	}
	
	public void UnknownID () {
		byte[] data;
		int clientPort;

		for(;;) { // loop forever
			data = new byte[512];
			receivePacket = new DatagramPacket(data, data.length);

			System.out.println("Simulator: Waiting for packet.");
			// Block until a datagram packet is received from receiveSocket.
			try {
				receiveSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			// Process the received datagram.
			System.out.println("Simulator: Packet received:");
			System.out.println("From host: " + receivePacket.getAddress());
			clientPort = receivePacket.getPort();
			System.out.println("Host port: " + clientPort);

			// Form a String from the byte array, and print the string.
			String received = new String(data,0,receivePacket.getLength());
			System.out.println(received);			
			
			System.out.println("Send packet to Unknown transfer ID. Error code 5.");			
			
			try{
				sendPacket = new DatagramPacket(data, receivePacket.getLength(),
					InetAddress.getLocalHost(), 70);
			}catch (UnknownHostException e) {
			     e.printStackTrace();
			     System.exit(1);
			}
		}		
	}
	
	public void normal(){
		byte[] data;
		int clientPort;

		for(;;) { // loop forever
			data = new byte[512];
			receivePacket = new DatagramPacket(data, data.length);

			System.out.println("Simulator: Waiting for packet.");
			// Block until a datagram packet is received from receiveSocket.
			try {
				receiveSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			// Process the received datagram.
			System.out.println("Simulator: Packet received:");
			System.out.println("From host: " + receivePacket.getAddress());
			clientPort = receivePacket.getPort();
			System.out.println("Host port: " + clientPort);
            ip = receivePacket.getAddress();
            
			// Form a String from the byte array, and print the string.
			String received = new String(data,0,receivePacket.getLength());
			System.out.println(received);
		
			try{
				sendPacket = new DatagramPacket(data, receivePacket.getLength(), InetAddress.getLocalHost(), serverPort);
			}catch (UnknownHostException e) {
			     e.printStackTrace();
			     System.exit(1);
			}
			
			System.out.println("Simulator: sending packet.");

			// Send the datagram packet to the server via the send/receive socket.
			try {
				sendReceiveSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			// Construct a DatagramPacket for receiving packets up
			// to 512 bytes long (the length of the byte array).
			data = new byte[512];
			receivePacket = new DatagramPacket(data, data.length);

			System.out.println("Simulator: Waiting for packet.");
			try {
				// Block until a datagram is received via sendReceiveSocket.
				sendReceiveSocket.receive(receivePacket);
			} catch(IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			// Process the received datagram.
			System.out.println("Simulator: Packet received:");
			System.out.println("From host: " + receivePacket.getAddress());
			System.out.println("Host port: " + receivePacket.getPort());

			sendPacket = new DatagramPacket(data, receivePacket.getLength(),
					ip, clientPort);

			System.out.println( "Simulator: Sending packet.");

			// Send the datagram packet to the client via a new socket.
			try {
				// Construct a new datagram socket and bind it to any port
				// on the local host machine. This socket will be used to
				// send UDP Datagram packets.
				sendSocket = new DatagramSocket();
			} catch (SocketException se) {
				se.printStackTrace();
				System.exit(1);
			}

			try {
				sendSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			System.out.println("Simulator: packet sent using port " + sendSocket.getLocalPort());
			System.out.println();

			// We're finished with this socket, so close it.
			sendSocket.close();
		} // end of loop
	}
	
	public void losePacket(){
		Scanner sc = new Scanner(System.in);
		System.out.println("which packet do you want to lose?");
		System.out.println("1. Read Request Packet.");
		System.out.println("2. Write Request Packet.");
		System.out.println("3. DATA Packet.");
		System.out.println("4. ACK Packet.");
		int choice = sc.nextInt();
		byte[] data;
		int clientPort;

		if(choice == 1){
			for(;;) { // loop forever
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
	
				System.out.println("Simulator: Waiting for packet.");
				// Block until a datagram packet is received from receiveSocket.
				try {
					receiveSocket.receive(receivePacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				clientPort = receivePacket.getPort();
				System.out.println("Host port: " + clientPort);
	
				// Form a String from the byte array, and print the string.
				String received = new String(data,0,receivePacket.getLength());
				System.out.println(received);
				
				System.out.println("Simulator: RRQ Packet Lost.");	
			}
		}else if(choice == 2){
			for(;;) { // loop forever
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
	
				System.out.println("Simulator: Waiting for packet.");
				// Block until a datagram packet is received from receiveSocket.
				try {
					receiveSocket.receive(receivePacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				clientPort = receivePacket.getPort();
				System.out.println("Host port: " + clientPort);
	
				// Form a String from the byte array, and print the string.
				String received = new String(data,0,receivePacket.getLength());
				System.out.println(received);
				
				System.out.println("Simulator: WRQ Packet Lost.");	
			}
		}else if(choice == 3){
			System.out.println("Please enter the Block Number for this lost DATA packet. (00-99)");
			String num = sc.next();
			for(;;) { // loop forever
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
	
				System.out.println("Simulator: Waiting for packet.");
				System.out.println("Simulator: DATA Packet Lost.");
				// Block until a datagram packet is received from receiveSocket.
				try {
					receiveSocket.receive(receivePacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				clientPort = receivePacket.getPort();
				System.out.println("Host port: " + clientPort);
				ip = receivePacket.getAddress();
				// Form a String from the byte array, and print the string.
				String received = new String(data,0,receivePacket.getLength());

				if(data[1] == 3 && data[2] == num.charAt(0) && data[3] == num.charAt(1)){
					System.out.println("DATA Packet Block # " + num + " Lost.");	
				}else{
					try{
						sendPacket = new DatagramPacket(data, receivePacket.getLength(), InetAddress.getLocalHost(), serverPort);
					}catch (UnknownHostException e) {
					     e.printStackTrace();
					     System.exit(1);
					}						
					
					System.out.println("Simulator: sending packet.");
	
				// Send the datagram packet to the server via the send/receive socket.	
					try {
						sendReceiveSocket.send(sendPacket);
					} catch (IOException e) {
						e.printStackTrace();
						System.exit(1);
					}
				}
				
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
	
				System.out.println("Simulator: Waiting for packet.");
				// Block until a datagram packet is received from receiveSocket.
				try {
					receiveSocket.receive(receivePacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				clientPort = receivePacket.getPort();
				System.out.println("Host port: " + clientPort);
	
				if(data[1] == 3 && data[2] == num.charAt(0) && data[3] == num.charAt(1)){
					System.out.println("DATA Packet Block # " + num + " Lost.");	
				}
				else{
					sendPacket = new DatagramPacket(data, receivePacket.getLength(),
							ip, clientPort);

					System.out.println( "Simulator: Sending packet.");

					// Send the datagram packet to the client via a new socket.

					try {
						// Construct a new datagram socket and bind it to any port
						// on the local host machine. This socket will be used to
						// send UDP Datagram packets.
						sendSocket = new DatagramSocket();
					} catch (SocketException se) {
						se.printStackTrace();
						System.exit(1);
					}

					try {
						sendSocket.send(sendPacket);
					} catch (IOException e) {
						e.printStackTrace();
						System.exit(1);
					}

					System.out.println("Simulator: packet sent using port " + sendSocket.getLocalPort());
					System.out.println();
				}
			}
		}else if(choice == 4){
			System.out.println("Please enter the Block Number for this lost ACK packet. (00-99)");
			String num = sc.next();
			for(;;) { // loop forever
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
	
				System.out.println("Simulator: Waiting for packet.");
				System.out.println("Simulator: ACK Packet Lost.");
				// Block until a datagram packet is received from receiveSocket.
				try {
					receiveSocket.receive(receivePacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				clientPort = receivePacket.getPort();
				System.out.println("Host port: " + clientPort);
				ip = receivePacket.getAddress();
				// Form a String from the byte array, and print the string.
				String received = new String(data,0,receivePacket.getLength());
				System.out.println(received);
				if(data[1] == 4 && data[2] == num.charAt(0) && data[3] == num.charAt(1)){
					System.out.println("ACK Packet Block # " + num + " Lost.");	
				}else{
					try{
						sendPacket = new DatagramPacket(data, receivePacket.getLength(), InetAddress.getLocalHost(), serverPort);
					}catch (UnknownHostException e) {
					     e.printStackTrace();
					     System.exit(1);
					}
						
					System.out.println("Simulator: sending packet.");
	
				// Send the datagram packet to the server via the send/receive socket.
	
					try {
						sendReceiveSocket.send(sendPacket);
					} catch (IOException e) {
						e.printStackTrace();
						System.exit(1);
					}
				}
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
	
				System.out.println("Simulator: Waiting for packet.");
				// Block until a datagram packet is received from receiveSocket.
				try {
					receiveSocket.receive(receivePacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				clientPort = receivePacket.getPort();
				System.out.println("Host port: " + clientPort);
				
				if(data[1] == 4 && data[2] == num.charAt(0) && data[3] == num.charAt(1)){
					System.out.println("ACK Packet Block # " + num + " Lost.");
				}
				else{
					sendPacket = new DatagramPacket(data, receivePacket.getLength(),
							ip, clientPort);						

					System.out.println( "Simulator: Sending packet.");

					// Send the datagram packet to the client via a new socket.
					try {
						// Construct a new datagram socket and bind it to any port
						// on the local host machine. This socket will be used to
						// send UDP Datagram packets.
						sendSocket = new DatagramSocket();
					} catch (SocketException se) {
						se.printStackTrace();
						System.exit(1);
					}

					try {
						sendSocket.send(sendPacket);
					} catch (IOException e) {
						e.printStackTrace();
						System.exit(1);
					}

					System.out.println("Simulator: packet sent using port " + sendSocket.getLocalPort());
					System.out.println();
				}
			}
		}else{
			System.out.println("Invalid input!");
		}
		sendSocket.close();
	}

	public void delayPacket(){
		Scanner sc = new Scanner(System.in);
		System.out.println("which packet do you want to delay?");
		System.out.println("1. Read Request Packet.");
		System.out.println("2. Write Request Packet.");
		System.out.println("3. DATA Packet.");
		System.out.println("4. ACK Packet.");
		int choice = sc.nextInt();
		System.out.println("How much of a delay do you want? 1000 is 1 second. Please bigger than 3 seconds and smaller than 6 seconds.");
		int time = sc.nextInt();		
		byte[] data;

		int clientPort;
		if(choice == 1){
			for(;;) { // loop forever
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
	
				System.out.println("Simulator: Waiting for packet.");
				// Block until a datagram packet is received from receiveSocket.
				try {
					receiveSocket.receive(receivePacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				clientPort = receivePacket.getPort();
				System.out.println("Host port: " + clientPort);
				ip  = receivePacket.getAddress();
				// Form a String from the byte array, and print the string.
				String received = new String(data,0,receivePacket.getLength());
				System.out.println(received);
			
				try{
					sendPacket = new DatagramPacket(data, receivePacket.getLength(), InetAddress.getLocalHost(), serverPort);
				}catch (UnknownHostException e) {
				     e.printStackTrace();
				     System.exit(1);
				}
					
				System.out.println("Simulator: sending packet.");
	
				// Send the datagram packet to the server via the send/receive socket.	
				try {
					System.out.println("Simulator: Delay RRQ Packet.");
					Thread.sleep(time);
					sendReceiveSocket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}catch(InterruptedException e){
					System.exit(1);
				}
				// Construct a DatagramPacket for receiving packets up
				// to 100 bytes long (the length of the byte array).

				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);

				System.out.println("Simulator: Waiting for packet.");
				try {
					// Block until a datagram is received via sendReceiveSocket.
					sendReceiveSocket.receive(receivePacket);
				} catch(IOException e) {
					e.printStackTrace();
					System.exit(1);
				}

				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				System.out.println("Host port: " + receivePacket.getPort());

				sendPacket = new DatagramPacket(data, receivePacket.getLength(),
						ip, clientPort);

				System.out.println( "Simulator: Sending packet.");

				// Send the datagram packet to the client via a new socket.
				try {
					// Construct a new datagram socket and bind it to any port
					// on the local host machine. This socket will be used to
					// send UDP Datagram packets.
					sendSocket = new DatagramSocket();
				} catch (SocketException se) {
					se.printStackTrace();
					System.exit(1);
				}

				try {
					sendSocket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}

				System.out.println("Simulator: packet sent using port " + sendSocket.getLocalPort());
				System.out.println();

				// We're finished with this socket, so close it.
				sendSocket.close();
			}
		}else if(choice == 2){
			for(;;) { // loop forever
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
	
				System.out.println("Simulator: Waiting for packet.");
				// Block until a datagram packet is received from receiveSocket.
				try {
					receiveSocket.receive(receivePacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				clientPort = receivePacket.getPort();
				System.out.println("Host port: " + clientPort);
				ip = receivePacket.getAddress();
				// Form a String from the byte array, and print the string.
				String received = new String(data,0,receivePacket.getLength());
				System.out.println(received);
			
				try{
					sendPacket = new DatagramPacket(data, receivePacket.getLength(), InetAddress.getLocalHost(), serverPort);
				}catch (UnknownHostException e) {
				     e.printStackTrace();
				     System.exit(1);
				}
					
				System.out.println("Simulator: sending packet.");
	
				// Send the datagram packet to the server via the send/receive socket.	
				try {
					System.out.println("Simulator: Delay WRQ Packet.");
					Thread.sleep(time);
					sendReceiveSocket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}catch(InterruptedException e){
					System.exit(1);
				}
			
				// Construct a DatagramPacket for receiving packets up
				// to 100 bytes long (the length of the byte array).		
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
		
				System.out.println("Simulator: Waiting for packet.");
				try {
					// Block until a datagram is received via sendReceiveSocket.
					sendReceiveSocket.receive(receivePacket);
				} catch(IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
		
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				System.out.println("Host port: " + receivePacket.getPort());
		
		
				sendPacket = new DatagramPacket(data, receivePacket.getLength(),
						ip, clientPort);
		
				System.out.println( "Simulator: Sending packet.");
		
				// Send the datagram packet to the client via a new socket.
				try {
					// Construct a new datagram socket and bind it to any port
					// on the local host machine. This socket will be used to
					// send UDP Datagram packets.
					sendSocket = new DatagramSocket();
				} catch (SocketException se) {
					se.printStackTrace();
					System.exit(1);
				}
		
				try {
					sendSocket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
		
				System.out.println("Simulator: packet sent using port " + sendSocket.getLocalPort());
				System.out.println();
		
				// We're finished with this socket, so close it.
				sendSocket.close();
			}
		}else if(choice == 3){
			System.out.println("Please enter the Block Number of this delayed DATA packet. (00-99)");
			String num = sc.next();
			for(;;) { // loop forever
				data = new byte[516];
				receivePacket = new DatagramPacket(data, data.length);	
				System.out.println("Simulator: Waiting for packet.");
				System.out.println("Simulator: Delay DATA Packet .");
				// Block until a datagram packet is received from receiveSocket.
				try {
					receiveSocket.receive(receivePacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}

				clientPort = receivePacket.getPort();
				ip = receivePacket.getAddress();
				FileCopier.printReceivePacket(receivePacket, "Sim", true);
				
				try{
					sendPacket = new DatagramPacket(data, receivePacket.getLength(), InetAddress.getLocalHost(), serverPort);
				}catch (UnknownHostException e) {
				     e.printStackTrace();
				     System.exit(1);
				}
					
				System.out.println("Simulator: sending packet.");
	
				// Send the datagram packet to the server via the send/receive socket.	
				try {
					if(data[1] == 3 && data[2] == num.charAt(0) && data[3] == num.charAt(1)){
						System.out.println("The DATA Packet Block # "+ num + " delayed.");
						Thread.sleep(time);
					}
					sendReceiveSocket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}catch(InterruptedException e){
					System.exit(1);
				}
			
				FileCopier.printSendPacket(sendPacket, "Sim", true);
				
				// Construct a DatagramPacket for receiving packets up
				// to 100 bytes long (the length of the byte array).		
				byte[] data1 = new byte[516];
				receivePacket = new DatagramPacket(data1, data1.length);
		
				System.out.println("Simulator: Waiting for packet.");
				try {
					// Block until a datagram is received via sendReceiveSocket.
					sendReceiveSocket.receive(receivePacket);
				} catch(IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
		
				FileCopier.printReceivePacket(receivePacket, "Sim", true);
				// Process the received datagram.		
				sendPacket = new DatagramPacket(receivePacket.getData(), receivePacket.getLength(),
						ip, clientPort);
		
				System.out.println("Simulator: Sending packet.");
		
				// Send the datagram packet to the client via a new socket.		
				try {
					// Construct a new datagram socket and bind it to any port
					// on the local host machine. This socket will be used to
					// send UDP Datagram packets.
					sendSocket = new DatagramSocket();
				} catch (SocketException se) {
					se.printStackTrace();
					System.exit(1);
				}
		
				try {
					if(data[1] == 3 && data[2] == num.charAt(0) && data[3] == num.charAt(1)){
						System.out.println("The DATA Packet Blcok # " + num + " delayed.");
						Thread.sleep(time);
					}
					sendSocket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}catch(InterruptedException e){
					System.exit(1);
				}
								
				FileCopier.printSendPacket(sendPacket, "Sim", true);
				System.out.println();		
				// We're finished with this socket, so close it.
				//sendSocket.close();
			}
		}else if(choice == 4){
			System.out.println("Please enter the Block Number of this delayed ACK packet.(00-99)");
			String num = sc.next();
			for(;;) { // loop forever
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
	
				System.out.println("Simulator: Waiting for packet.");
				System.out.println("Simulator: Delay ACK Packet.");
				// Block until a datagram packet is received from receiveSocket.
				try {
					receiveSocket.receive(receivePacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				clientPort = receivePacket.getPort();
				System.out.println("Host port: " + clientPort);
				ip = receivePacket.getAddress();
				// Form a String from the byte array, and print the string.
				String received = new String(data,0,receivePacket.getLength());
				System.out.println(received);
			
				try{
					sendPacket = new DatagramPacket(data, receivePacket.getLength(), InetAddress.getLocalHost(), serverPort);
				}catch (UnknownHostException e) {
				     e.printStackTrace();
				     System.exit(1);
				}
					
				System.out.println("Simulator: sending packet.");
	
				// Send the datagram packet to the server via the send/receive socket.
	
				try {
					if(data[1] == 4 && data[2] == num.charAt(0) && data[3] == num.charAt(1)){
						System.out.println("The ACK Packet delayed.");
						Thread.sleep(time);
					}
					sendReceiveSocket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}catch(InterruptedException e){
					System.exit(1);
				}
			
				// Construct a DatagramPacket for receiving packets up
				// to 100 bytes long (the length of the byte array).
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
		
				System.out.println("Simulator: Waiting for packet.");
				try {
					// Block until a datagram is received via sendReceiveSocket.
					sendReceiveSocket.receive(receivePacket);
				} catch(IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
		
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				System.out.println("Host port: " + receivePacket.getPort());
		
				sendPacket = new DatagramPacket(data, receivePacket.getLength(),
						ip, clientPort);
		
				System.out.println( "Simulator: Sending packet.");
		
				// Send the datagram packet to the client via a new socket.
				try {
					// Construct a new datagram socket and bind it to any port
					// on the local host machine. This socket will be used to
					// send UDP Datagram packets.
					sendSocket = new DatagramSocket();
				} catch (SocketException se) {
					se.printStackTrace();
					System.exit(1);
				}
		
				try {
					if(data[1] == 4 && data[2] == num.charAt(0) && data[3] == num.charAt(1)){
						System.out.println("The ACK Packet delayed.");
						Thread.sleep(time);
					}
					sendSocket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}catch(InterruptedException e){
					System.exit(1);
				}
		
				System.out.println("Simulator: packet sent using port " + sendSocket.getLocalPort());
				System.out.println();
		
				// We're finished with this socket, so close it.
				sendSocket.close();
			}
		}else{
			System.out.println("Invalid input!");
		}
	}
	
	public void duplicatePacket(){
		Scanner sc = new Scanner(System.in);
		System.out.println("which packet do you want to duplicate?");
		System.out.println("1. Read Request Packet.");
		System.out.println("2. Write Request Packet.");
		System.out.println("3. DATA Packet.");
		System.out.println("4. ACK Packet.");
		int choice = sc.nextInt();
		byte[] data;

		int clientPort;
		if(choice == 1){
			for(;;) { // loop forever
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
	
				System.out.println("Simulator: Waiting for packet.");
				// Block until a datagram packet is received from receiveSocket.
				try {
					receiveSocket.receive(receivePacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				clientPort = receivePacket.getPort();
				System.out.println("Host port: " + clientPort);
				ip = receivePacket.getAddress();
				// Form a String from the byte array, and print the string.
				String received = new String(data,0,receivePacket.getLength());
				System.out.println(received);
			
				try{
					sendPacket = new DatagramPacket(data, receivePacket.getLength(), InetAddress.getLocalHost(), serverPort);
				}catch (UnknownHostException e) {
				     e.printStackTrace();
				     System.exit(1);
				}
				
				System.out.println("Simulator: sending packet.");
	
				// Send the datagram packet to the server via the send/receive socket.
	
				try {
					System.out.println("Simulator: Duplicate RRQ Packet.");
					sendReceiveSocket.send(sendPacket);
					sendReceiveSocket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Construct a DatagramPacket for receiving packets up
				// to 100 bytes long (the length of the byte array).
	
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
	
				System.out.println("Simulator: Waiting for packet.");
				try {
					// Block until a datagram is received via sendReceiveSocket.
					sendReceiveSocket.receive(receivePacket);
				} catch(IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				System.out.println("Host port: " + receivePacket.getPort());
	
	
				sendPacket = new DatagramPacket(data, receivePacket.getLength(),
						ip, clientPort);
	
				System.out.println("Simulator: Sending packet.");
	
				// Send the datagram packet to the client via a new socket.
	
				try {
					// Construct a new datagram socket and bind it to any port
					// on the local host machine. This socket will be used to
					// send UDP Datagram packets.
					sendSocket = new DatagramSocket();
				} catch (SocketException se) {
					se.printStackTrace();
					System.exit(1);
				}
	
				try {
					sendSocket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				System.out.println("Simulator: packet sent using port " + sendSocket.getLocalPort());
				System.out.println();
	
				// We're finished with this socket, so close it.
				sendSocket.close();
			} // end of loop
		}else if(choice == 2){
			for(;;) { // loop forever
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
	
				System.out.println("Simulator: Waiting for packet.");
				// Block until a datagram packet is received from receiveSocket.
				try {
					receiveSocket.receive(receivePacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				clientPort = receivePacket.getPort();
				System.out.println("Host port: " + clientPort);
				ip = receivePacket.getAddress();
				// Form a String from the byte array, and print the string.
				String received = new String(data,0,receivePacket.getLength());
				System.out.println(received);
				
				try{
					sendPacket = new DatagramPacket(data, receivePacket.getLength(), InetAddress.getLocalHost(), serverPort);
				}catch (UnknownHostException e) {
				     e.printStackTrace();
				     System.exit(1);
				}					
				System.out.println("Simulator: sending packet.");
	
				// Send the datagram packet to the server via the send/receive socket.
	
				try {
					System.out.println("Simulator: Duplicate WRQ Packet.");
					sendReceiveSocket.send(sendPacket);
					sendReceiveSocket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Construct a DatagramPacket for receiving packets up
				// to 100 bytes long (the length of the byte array).
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
	
				System.out.println("Simulator: Waiting for packet.");
				try {
					// Block until a datagram is received via sendReceiveSocket.
					sendReceiveSocket.receive(receivePacket);
				} catch(IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				System.out.println("Host port: " + receivePacket.getPort());
	
	
				sendPacket = new DatagramPacket(data, receivePacket.getLength(),
						ip, clientPort);
	
				System.out.println("Simulator: Sending packet.");
	
				// Send the datagram packet to the client via a new socket.
				try {
					// Construct a new datagram socket and bind it to any port
					// on the local host machine. This socket will be used to
					// send UDP Datagram packets.
					sendSocket = new DatagramSocket();
				} catch (SocketException se) {
					se.printStackTrace();
					System.exit(1);
				}
	
				try {
					sendSocket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				System.out.println("Simulator: packet sent using port " + sendSocket.getLocalPort());
				System.out.println();
	
				// We're finished with this socket, so close it.
				sendSocket.close();
			} // end of loop
		}else if(choice == 3){
			System.out.println("Please enter the Block # of this duplicate DATA Packet. (00-99)");
			String num = sc.next();
			for(;;) { // loop forever
				data = new byte[516];
				receivePacket = new DatagramPacket(data, data.length);
	
				System.out.println("Simulator: Waiting for packet.");
				System.out.println("Simulator: Duplicate DATA Packet.");
				// Block until a datagram packet is received from receiveSocket.
				try {
					receiveSocket.receive(receivePacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
				clientPort = receivePacket.getPort();
				ip = receivePacket.getAddress();
				FileCopier.printReceivePacket(receivePacket, "Sim", true);
			
				try{
					sendPacket = new DatagramPacket(data, receivePacket.getLength(), InetAddress.getLocalHost(), serverPort);
				}catch (UnknownHostException e) {
				     e.printStackTrace();
				     System.exit(1);
				}
					
				System.out.println("Simulator: sending packet.");
	
				// Send the datagram packet to the server via the send/receive socket.
				try {
					if(data[1] == 3 && data[2] == num.charAt(0) && data[3] == num.charAt(1)){
						System.out.println("Duplicate DATA Packet Block #" + num + ".");
						sendReceiveSocket.send(sendPacket);
						FileCopier.printSendPacket(sendPacket, "Sim", true);
					}
					sendReceiveSocket.send(sendPacket);
					FileCopier.printSendPacket(sendPacket, "Sim", true);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
				
				// Construct a DatagramPacket for receiving packets up
				// to 100 bytes long (the length of the byte array).
				byte[] data1 = new byte[516];
				receivePacket = new DatagramPacket(data1, data1.length);
	
				System.out.println("Simulator: Waiting for packet.");
				try {
					// Block until a datagram is received via sendReceiveSocket.
					sendReceiveSocket.receive(receivePacket);
				} catch(IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
				
				FileCopier.printReceivePacket(receivePacket, "Sim", true);
				// Process the received datagram.
	
				sendPacket = new DatagramPacket(receivePacket.getData(), receivePacket.getLength(),
						ip, clientPort);
	
				System.out.println("Simulator: Sending packet.");
	
				try {
					if(data[1] == 3 && data[2] == num.charAt(0) && data[3] == num.charAt(1)){
						System.out.println("Duplicate DATA Packet Block # " + num + ".");
						receiveSocket.send(sendPacket);
						FileCopier.printSendPacket(sendPacket, "Sim", true);
					}
					receiveSocket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
				FileCopier.printSendPacket(sendPacket, "Sim", true);
			} // end of loop
		}else if(choice == 4){
			System.out.println("Please enter the Block # of this Duplicate ACK Packet.(00-99)");
			String num = sc.next();
			for(;;) { // loop forever
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
	
				System.out.println("Simulator: Waiting for packet.");
				System.out.println("Simulator: Duplicate ACK Packet.");
				// Block until a datagram packet is received from receiveSocket.
				try {
					receiveSocket.receive(receivePacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				clientPort = receivePacket.getPort();
				System.out.println("Host port: " + clientPort);
				ip = receivePacket.getAddress();
				// Form a String from the byte array, and print the string.
				String received = new String(data,0,receivePacket.getLength());
				System.out.println(received);
			
				try{
					sendPacket = new DatagramPacket(data, receivePacket.getLength(), InetAddress.getLocalHost(), serverPort);
				}catch (UnknownHostException e) {
				     e.printStackTrace();
				     System.exit(1);
				}
					
				System.out.println("Simulator: sending packet.");
	
				// Send the datagram packet to the server via the send/receive socket.
	
				try {
					if(data[1] == 4 && data[2] == num.charAt(0) && data[3] == num.charAt(1)){
						System.out.println("Duplicate ACK Packet Block # " + num + ".");
						sendReceiveSocket.send(sendPacket);
					}
					sendReceiveSocket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Construct a DatagramPacket for receiving packets up
				// to 100 bytes long (the length of the byte array).
	
				data = new byte[512];
				receivePacket = new DatagramPacket(data, data.length);
	
				System.out.println("Simulator: Waiting for packet.");
				try {
					// Block until a datagram is received via sendReceiveSocket.
					sendReceiveSocket.receive(receivePacket);
				} catch(IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				// Process the received datagram.
				System.out.println("Simulator: Packet received:");
				System.out.println("From host: " + receivePacket.getAddress());
				System.out.println("Host port: " + receivePacket.getPort());
	
	
				sendPacket = new DatagramPacket(data, receivePacket.getLength(),
						ip, clientPort);
	
				System.out.println("Simulator: Sending packet.");
	
				// Send the datagram packet to the client via a new socket.
				try {
					// Construct a new datagram socket and bind it to any port
					// on the local host machine. This socket will be used to
					// send UDP Datagram packets.
					sendSocket = new DatagramSocket();
				} catch (SocketException se) {
					se.printStackTrace();
					System.exit(1);
				}
	
				try {
					if(data[1] == 4 && data[2] == num.charAt(0) && data[3] == num.charAt(1)){
						System.out.println("Simulator: Duplicate ACK Packet Block # " + num + ".");
						sendReceiveSocket.send(sendPacket);
					}
					sendSocket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
	
				System.out.println("Simulator: packet sent using port " + sendSocket.getLocalPort());
				System.out.println();
	
				// We're finished with this socket, so close it.
				sendSocket.close();
			} // end of loop
		}else{
			System.out.println("Invalid input!");
		}
	}
	
	public static void main( String args[] )
	{
		int answer = -1;
		TFTPSim s = new TFTPSim();
		answer = ui();
		
		switch (answer){
			case 0:
				s.normal();
				break;
			case 1: 
				s.InvalidRRQ();
				break;
			case 2:
				s.InvalidWRQ();
				break;
			case 3:
				s.InvalidMode ();
				break;
			case 4:
				s.UnknownID();
				break;
			case 5:
				s.losePacket();
				break;
			case 6: 
				s.delayPacket();
				break;
			case 7:
				s.duplicatePacket();
				break;
			case 8:
				break;
			default:
				System.out.println("Invalid Input.");
				break;
		}
	}
}