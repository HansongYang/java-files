import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.imageio.IIOException;

public class TFTPClient {
	private DatagramPacket sendPacket, receivePacket;
	private DatagramSocket sendReceiveSocket, sendSocket;
	public int request;
	public String filename;
	public int setting;
	private int serverPort = 69;
	private int simErr = 23;
	private FileCopier copier1;
	private int sendPort;
	public byte[] data;
	public String mode = "octet";
	public boolean verbose = true;
	public String name = "";
	public String drive = "";
	public String folderName = "";
	public static boolean received = true;
	public Path path = null;
	public InetAddress ip;

	// we can run in normal (send directly to server) or test
	// (send to simulator) mode
	public static enum Mode { NORMAL, TEST};

	public TFTPClient() {
		try {
			// Construct a datagram socket and bind it to any available
			// port on the local host machine. This socket will be used to
			// send and receive UDP Datagram packets.
			sendReceiveSocket = new DatagramSocket();
			sendReceiveSocket.setSoTimeout(6000);
			sendSocket = new DatagramSocket();
			sendSocket.setSoTimeout(6000);
			copier1 = new FileCopier(this);
		} catch (SocketException se) { // Can't create the socket.
			se.printStackTrace();
			System.exit(1);
		}
	}

	public void sendAndReceive() throws IOException {
		boolean receivedACK = false;
		int tryCount = 0;
		byte[] msg = filename.getBytes(); // message we send
		data = formPacket(msg);
		String filepath = "";

		if (drive.equals("c") || drive.equals("C")) {
			filepath = "C:/Users/" + name + "/workspace/" + folderName + "/client/";
		}
		if (drive.equals("M") || drive.equals("m")) {
			filepath = "M:/" + folderName + "/";
		}
		String absolutefilepath = filepath.concat(filename);


		if (!new File(absolutefilepath).isAbsolute()) {
			accessViolationError();
			return;
		}
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
			return;
		}
		if (request == 1) {
			path = Paths.get(absolutefilepath);
			if (Files.exists(path)) {
				fileAlreadyExistsError();
				return;
			}
		} else {
			path = Paths.get(absolutefilepath);
			if (!Files.exists(path)) {
				fileNotFoundError();
				return;
			}
		}

		sendPacket = new DatagramPacket(data, data.length, ip, sendPort);
		
		if (request == 1) { // Read
			try {
				sendReceiveSocket.send(sendPacket);
				FileCopier.printSendPacket(sendPacket, "Client", verbose);
			} catch (IOException e) {}

			BufferedOutputStream out;
			try {
				out = new BufferedOutputStream(new FileOutputStream(absolutefilepath));
				copier1.write(out, filename, sendReceiveSocket);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				fileNotFoundError();
			} catch (SocketTimeoutException e) {
				System.err.println("Packet lost.");
			}
		} else { // Write
			while (!receivedACK) {
				byte[] data1 = new byte[516];
				receivePacket = new DatagramPacket(data1, data1.length);

				try {
					sendReceiveSocket.send(sendPacket);
					FileCopier.printSendPacket(sendPacket, "Client", verbose);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}

				try {
					sendReceiveSocket.receive(receivePacket);
					FileCopier.printReceivePacket(receivePacket, "Client", verbose);
					receivedACK = true;
				} catch (IOException e) {
					if (tryCount < 4 && verbose) {
						System.out.println("Socket Timeout. Resending: ");
					}
					tryCount++;
				}
				
				if (tryCount >= 5) {
					System.out.println("Error. Sent 5 times. Server not responding.");
					return;
				}
			}

			byte opcode = receivePacket.getData()[1];
			if (opcode == 5) {
				copier1.handleError(receivePacket);
			} else {
				BufferedInputStream in;
				filepath = filepath.concat(filename);
				File file = new File(filepath);
				if (!file.isAbsolute()) {
					System.out.println("Access violation.");
					return;
				}

				try {
					in = new BufferedInputStream(new FileInputStream(filepath));
					copier1.read(in, filename, sendReceiveSocket, ip, receivePacket.getPort());
					in.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					fileNotFoundError();
				} catch (SocketTimeoutException e) {
				} catch (IllegalArgumentException e) {
				}
			}
		}
	}

	public void ui() throws IOException {
		System.out.println("Client: ");
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome, please enter your user name.");
		name = sc.next();
		System.out.println("Please enter your drive <C/M>: ");
		drive = sc.next();

		if (drive.equals("C") || drive.equals("c")) {
			System.out.print("Please enter your folder name: ");
			folderName = sc.next();
		}

		if (!drive.equals("C") && !drive.equals("c") && !drive.equals("M") && !drive.equals("m")) {
			accessViolationError();
			System.exit(1);
		}

		System.out.println("If you want to implement the File Transfer between different computers, please enter IP Address.");
		System.out.println("Otherwise, please enter -1.");
		String IP = sc.next();
		if (IP.equals("-1")) {
			ip = InetAddress.getLocalHost();
		} else {
			ip = InetAddress.getByName(IP);
		}

		while (true) {
			System.out.println("What is your request? (1. read or 2.write)");
			System.out.println("Enter " + "-1" + " to quit.");
			request = sc.nextInt();
			if (request == -1) {
				sc.close();
				sendReceiveSocket.close();
				System.exit(0);
			} else if (request < 1 || request > 2) {
				System.err.println("Error. Invalid input.");
				System.exit(0);
			}
			System.out.println("What is the filename?");
			filename = sc.next();
			System.out.println("What is the mode? (1) Normal or (2) Test");
			setting = sc.nextInt();
			if (setting < 1 || setting > 2) {
				System.err.println("Error. Invalid input.");
				System.exit(0);
			} else {
				setMode(setting);
			}
			System.out.println("Verbose mode (1) or Quiet mode (2)?");
			int temp = sc.nextInt();
			if (temp == 1) {
				this.verbose = true;
				copier1.setVerbose(verbose);
			} else if (temp == 2) {
				this.verbose = false;
				copier1.setVerbose(verbose);
			} else {
				System.err.println("Error. Invalid input.");
				System.exit(0);
			}
			sendAndReceive();
		}
	}

	public void diskFullError() {
		String errormsg = "Error. Disk full or allocation exceeded.";
		System.err.println(errormsg);
	}

	public void accessViolationError() {
		String errormsg = "Error. Access Violation.";
		System.err.println(errormsg);
	}

	public void fileNotFoundError() {
		String errormsg = "Error. File not found.";
		System.err.println(errormsg);
	}

	public void fileAlreadyExistsError() {
		String errormsg = "Error. File already exists.";
		System.err.println(errormsg);
	}

	public void setMode(int setting) {
		if (setting == 1) {
			sendPort = serverPort;
		}
		if (setting == 2) {
			sendPort = simErr;
		}
	}

	public void deleteCreatedFile() {
		try {
			Files.delete(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isVerbose() {
		return verbose;
	}

	public byte[] formPacket(byte[] msg) {
		byte[] data2 = new byte[filename.length() + mode.length() + 4];
		data2[0] = 0;

		if (request == 1) {
			data2[1] = 1;
		} else {
			data2[1] = 2;
		}

		for (int i = 0; i < filename.length(); i++) {
			data2[2 + i] = msg[i];
		}

		data2[filename.length() + 2] = 0;

		for (int i = 0; i < mode.length(); i++) {
			data2[filename.length() + 3 + i] = mode.getBytes()[i];
		}

		data2[filename.length() + mode.length() + 3] = 0;
		return data2;
	}

	public static void main(String args[]) throws IOException {
		TFTPClient c = new TFTPClient();
		c.ui();
	}
}