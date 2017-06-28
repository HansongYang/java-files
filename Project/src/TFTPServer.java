import java.io.*;
import java.net.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Scanner.*;

public class TFTPServer extends Thread {
	public static String name = "";
	public static String drive = "";
	public static String folderName = "";
	public static int verb = 0; 
	public Path path;
	public TFTPServer mainServer = null;
	private DatagramPacket sendPacket, receivePacket;
	private DatagramSocket receiveSocket, sendSocket;
	private int port = 69;
	private listener l;
	private boolean readReq;
	private int numberOfThreads;
	public static FileCopier copier;
	public String currentFilename;
	public static boolean verbose;
	// types of requests we can receive
	public static enum Request {
		READ, WRITE, ERROR
	};

	// responses for valid requests
	public static final byte[] readResp = new byte[] { 0, 3, 0, 1 };
	public static final byte[] writeResp = new byte[] { 0, 4, 0, 0 };

	public TFTPServer() {
		try {
			// Construct a datagram socket and bind it to port 69
			// on the local host machine. This socket will be used to
			// receive UDP Datagram packets.
			numberOfThreads = 0;
			l = new listener(this);
			l.start();
			receiveSocket = new DatagramSocket(port);
		} catch (SocketException se) {
			se.printStackTrace();
			System.exit(1);
		}
	}

	public TFTPServer(DatagramPacket received, TFTPServer main) {
		this.mainServer = main;
		receivePacket = received;
		byte readOrWrite = received.getData()[1];
		copier = new FileCopier(this);
		currentFilename = FileCopier.getFilename(receivePacket);

		if (readOrWrite == 1) {
			readReq = true;
		} else if (readOrWrite == 2) {
			readReq = false;
		}

		try {
			sendSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void run() {
		if (checkRequest(receivePacket)) {
			FileCopier.printReceivePacket(receivePacket, "Server", verbose);
			if (readReq) {
				read();
			} else {
				write();
			}
		} else {
			FileCopier.printReceivePacket(receivePacket, "Server", verbose);
			illegalTFTPOperationError();
		}
		decrementNumberOfThreads();
		sendSocket.close();
	}

	public void quit() {
		System.out.println("Finishing threads...");
		while (numberOfThreads > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll();
		receiveSocket.close();
		System.out.println("Closing");
		System.exit(0);
	}

	public void decrementNumberOfThreads() {
		mainServer.numberOfThreads--;
	}

	public void read() {
		String filepath = "";
		if (drive.equals("c") || drive.equals("C")) {
			filepath = "C:/Users/" + name + "/workspace/" + folderName + "/server/";
		}

		if (drive.equals("M") || drive.equals("m")) {
			filepath = "M:/" + folderName + "/";
		}
		BufferedInputStream in;
		filepath = filepath.concat(currentFilename);

		File file = new File(filepath);
		if (!file.isAbsolute()) {
			System.out.println("Access violation.");
			return;
		}

		try {
			in = new BufferedInputStream(new FileInputStream(filepath));
			copier.read(in, currentFilename, sendSocket, receivePacket.getAddress(), receivePacket.getPort());
			in.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			fileNotFoundError();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Server: packet sent using port " + sendSocket.getLocalPort());
		System.out.println();
	}

	public void write() {
		String filepath = "";
		if (drive.equals("c") || drive.equals("C")) {
			filepath = "C:/Users/" + name + "/workspace/" + folderName + "/";
		}

		if (drive.equals("M") || drive.equals("m")) {
			filepath = "M:/" + folderName + "/";
		}

		BufferedOutputStream out;
		filepath = "C:/Users/" + name + "/workspace/" + folderName + "/server/";
		String absolutefilepath = filepath.concat(currentFilename);

		if (!new File(filepath).isAbsolute()) {
			accessViolationError();
			return;
		}

		path = Paths.get(absolutefilepath);

		long totalFreeSpace;
		if (drive.equals("c") || drive.equals("C")) {
			totalFreeSpace = new File("C:").getUsableSpace();
		} else if (drive.equals("M") || drive.equals("m")) {
			totalFreeSpace = new File("M:").getUsableSpace();
		} else {
			totalFreeSpace = new File(drive + ":").getUsableSpace();
		}

		if (totalFreeSpace < new File(absolutefilepath).length()) {
			diskFullError();
		}

		if (Files.exists(path)) {
			fileAlreadyExistsError();
			return;
		} else {
			sendPacket = new DatagramPacket(writeResp, writeResp.length, receivePacket.getAddress(),
					receivePacket.getPort());
			try {
				out = new BufferedOutputStream(new FileOutputStream(absolutefilepath));
				sendSocket.send(sendPacket);
				FileCopier.printSendPacket(sendPacket, "Server", verbose);
				copier.write(out, currentFilename, sendSocket);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				System.err.println("File not Found!!! Please try again!");
				fileNotFoundError();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Server: packet sent using port " + sendSocket.getLocalPort());
		System.out.println();
	}

	public void receiveAndSendTFTP() throws Exception {
		byte[] data;
		for (;;) { // loop forever
			// Construct a DatagramPacket for receiving packets up
			// to 100 bytes long (the length of the byte array).
			data = new byte[512];
			receivePacket = new DatagramPacket(data, data.length);

			System.out.println("Server: Waiting for packet.");
			// Block until a datagram packet is received from receiveSocket.
			try {
				receiveSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			numberOfThreads++;
			new TFTPServer(receivePacket, this).start();
		} // end of loop
	}

	public static void main(String args[]) throws Exception {
		System.out.println("Server:");
		System.out.println("Press q to quit.");
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome, please enter your user name.");
		name = sc.next();
		System.out.println("Please enter your drive <C/M>: ");
		drive = sc.next();
		
		if (drive.equals("C") || drive.equals("c") || drive.equals("M") || drive.equals("m")) {
			System.out.println("Please enter your folder name: ");
			folderName = sc.next();
		} else {
			System.err.println("Access violation");
			System.exit(1);
		}

		System.out.println("Verbose mode (1) or Quiet mode (2)?");
		verb = sc.nextInt();
		if (verb == 1) {
			verbose = true;
		} else if (verb == 2) {
			verbose = false;
		} else {
			System.err.println("Error. Invalid input.");
			System.exit(0);
		}
		
		TFTPServer c = new TFTPServer();
		c.receiveAndSendTFTP();
	}

	public void diskFullError() {
		String errormsg = "Disk full or allocation exceeded.";
		byte[] error = new byte[5 + errormsg.length()];
		byte[] resp = new byte[errormsg.length() + 5];
		boolean confirm = false;
		error[0] = 0;
		error[1] = 5;
		error[2] = 0;
		error[3] = 3;
		for (int i = 0; i < errormsg.length(); i++) {
			error[4 + i] = errormsg.getBytes()[i];
		}
		error[4 + errormsg.length()] = 0;
		sendPacket = new DatagramPacket(error, error.length, receivePacket.getAddress(), receivePacket.getPort());

		receivePacket = new DatagramPacket(resp, resp.length);

		while (confirm == false) {
			try {
				FileCopier.printSendPacket(sendPacket, "Server", verbose);
				sendSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				sendSocket.receive(receivePacket);
				confirm = true;
			} catch (IOException e) {
		
			}
			
			if(confirm == false){
				System.out.println("Resending: ");
			}
		}
	}

	public void accessViolationError() {
		String errormsg = "Error. Access Violation.";
		byte[] error = new byte[errormsg.length() + 5];
		byte[] resp = new byte[errormsg.length() + 5];
		boolean confirm = false;
		error[0] = 0;
		error[1] = 5;
		error[2] = 0;
		error[3] = 2;

		for (int i = 0; i < errormsg.length(); i++) {
			error[4 + i] = errormsg.getBytes()[i];
		}

		error[4 + errormsg.length()] = 0;
		sendPacket = new DatagramPacket(error, error.length, receivePacket.getAddress(), receivePacket.getPort());
		receivePacket = new DatagramPacket(resp, resp.length);

		while (confirm == false) {
			try {
				FileCopier.printSendPacket(sendPacket, "Server", verbose);
				sendSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				sendSocket.receive(receivePacket);
				confirm = true;
			} catch (IOException e) {
	
			}
			if(confirm == false){
				System.out.println("Resending: ");
			}
		}
	}

	public void illegalTFTPOperationError() {
		String errormsg = "Error. Illegal TFTP operation.";
		byte[] error = new byte[errormsg.length() + 5];
		byte[] resp = new byte[errormsg.length() + 5];
		boolean confirm = false;
		error[0] = 0;
		error[1] = 5;
		error[2] = 0;
		error[3] = 4;

		for (int i = 0; i < errormsg.length(); i++) {
			error[4 + i] = errormsg.getBytes()[i];
		}

		error[4 + errormsg.length()] = 0;
		sendPacket = new DatagramPacket(error, error.length, receivePacket.getAddress(), receivePacket.getPort());
		receivePacket = new DatagramPacket(resp, resp.length);

		while (confirm == false) {
			try {
				FileCopier.printSendPacket(sendPacket, "Server", verbose);
				sendSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				sendSocket.receive(receivePacket);
				confirm = true;
			} catch (IOException e) {

			}
			if(confirm == false){
				System.out.println("Resending: ");
			}
		}
	}

	public void fileNotFoundError() {
		String errormsg = "Error. File not found.";
		byte[] error = new byte[errormsg.length() + 5];
		byte[] resp = new byte[errormsg.length() + 5];
		boolean confirm = false;
		error[0] = 0;
		error[1] = 5;
		error[2] = 0;
		error[3] = 1;

		for (int i = 0; i < errormsg.length(); i++) {
			error[4 + i] = errormsg.getBytes()[i];
		}

		error[4 + errormsg.length()] = 0;
		sendPacket = new DatagramPacket(error, error.length, receivePacket.getAddress(), receivePacket.getPort());
		receivePacket = new DatagramPacket(resp, resp.length);

		while (confirm == false) {
			try {
				FileCopier.printSendPacket(sendPacket, "Server", verbose);
				sendSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				sendSocket.receive(receivePacket);
				confirm = true;
			} catch (IOException e) {

			}
			if(confirm == false){
				System.out.println("Resending: ");
			}
		}
	}

	public void fileAlreadyExistsError() {
		String errormsg = "Error. File already exists.";
		byte[] resp = new byte[errormsg.length() + 5];
		byte[] error = new byte[errormsg.length() + 5];
		boolean confirm = false;
		error[0] = 0;
		error[1] = 5;
		error[2] = 0;
		error[3] = 6;

		for (int i = 0; i < errormsg.length(); i++) {
			error[4 + i] = errormsg.getBytes()[i];
		}

		error[4 + errormsg.length()] = 0;
		sendPacket = new DatagramPacket(error, error.length, receivePacket.getAddress(), receivePacket.getPort());
		receivePacket = new DatagramPacket(resp, resp.length);

		while (confirm == false) {
			try {
				FileCopier.printSendPacket(sendPacket, "Server", verbose);
				sendSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				sendSocket.receive(receivePacket);
				confirm = true;
			} catch (IOException e) {

			}
			if(confirm == false){
				System.out.println("Resending: ");
			}
		}
	}

	public void toggleVerbose() {
		verbose = (!verbose);
	}

	public boolean isVerbose() {
		return verbose;
	}

	public boolean checkRequest(DatagramPacket Packet) {
		byte[] data;
		Request req; // READ, WRITE or ERROR

		String filename, mode;
		int len, j = 0, k = 0;

		len = Packet.getLength();

		data = Packet.getData();

		// If it's a read, send back DATA (03) block 1
		// If it's a write, send back ACK (04) block 0
		// Otherwise, ignore it
		if (data[0] != 0)
			req = Request.ERROR; // bad
		else if (data[1] == 1)
			req = Request.READ; // could be read
		else if (data[1] == 2)
			req = Request.WRITE; // could be write
		else
			req = Request.ERROR; // bad

		if (req != Request.ERROR) { // check for filename
			// search for next all 0 byte
			for (j = 2; j < len; j++) {
				if (data[j] == 0)
					break;
			}
			if (j == len)
				req = Request.ERROR; // didn't find a 0 byte
			if (j == 2)
				req = Request.ERROR; // filename is 0 bytes long
			// otherwise, extract filename
			filename = new String(data, 2, j - 2);
		}

		if (req != Request.ERROR) { // check for mode
			// search for next all 0 byte
			for (k = j + 1; k < len; k++) {
				if (data[k] == 0)
					break;
			}
			if (k == len)
				req = Request.ERROR; // didn't find a 0 byte
			if (k == j + 1)
				req = Request.ERROR; // mode is 0 bytes long
			mode = new String(data, j + 1, k - j - 1);
			if (mode.toLowerCase().equals("netascii") || mode.toLowerCase().equals("octet")) {

			} else {
				req = Request.ERROR;
			}
		}

		if (k != len - 1)
			req = Request.ERROR; // other stuff at end of packet

		// Create a response.
		if (req == Request.READ) { // for Read it's 0301
			return true;
		} else if (req == Request.WRITE) { // for Write it's 0400
			return true;
		} else { // it was invalid, just quit
			return false;
		}
	}

	public void deleteCreatedFile() {
		// TODO Auto-generated method stub
		try {
			Files.delete(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}