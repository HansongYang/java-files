import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class FileCopier {

	TFTPServer serverOwner = null;
	TFTPClient clientOwner = null;
	TFTPSim simOwner = null;
	boolean verbose;

	public static final String[] opcode = { "", "RRQ", "WRQ", "DATA", "ACK", "ERROR" };

	public FileCopier(TFTPClient owner) {
		this.clientOwner = owner;
		this.verbose = owner.isVerbose();
	}

	public FileCopier(TFTPServer owner) {
		this.serverOwner = owner;
		this.verbose = owner.isVerbose();
	}

	public FileCopier(TFTPSim owner) {
		this.simOwner = owner;
		this.verbose = true;
	}

	public void read(BufferedInputStream in, String inputFilename, DatagramSocket sendReceiveSocket, InetAddress ip, int port)throws IOException { // DATA
		int n;
		int tryCount = 0;
		boolean receivedACK;
		byte[] data = new byte[512];
		byte[] resp = new byte[4];
		DatagramPacket sendPacket, receivePacket;
		byte block1 = 0;
		byte block2 = 0;
		boolean duplicate = false;
		int currentBlock = 1;

		try {
			sendPacket = new DatagramPacket(resp, resp.length);
			while (((n = in.read(data)) != -1)) {
				receivedACK = false;
				if ((int) block2 == -1)
					block1++;
				block2++;
				byte[] message = new byte[n + 4];
				message[0] = 0;
				message[1] = 3;
				message[2] = block1;
				message[3] = block2;

				for (int i = 0; i < n; i++) {
					message[i + 4] = data[i];
				}

				sendPacket = new DatagramPacket(message, n + 4, ip, port);

				while (!receivedACK) {
					receivePacket = new DatagramPacket(resp, resp.length);
					if (duplicate == false) {
						sendReceiveSocket.send(sendPacket);
						printSendPacket(sendPacket, "Read", verbose);
					}

					try {
						sendReceiveSocket.receive(receivePacket);
						printReceivePacket(receivePacket, "Read", verbose);
						if (receivePacket.getData()[1] == 5) {
							handleError(receivePacket);
							return;
						} else if (receivePacket.getData()[1] == 1 || receivePacket.getData()[1] == 2
								|| receivePacket.getData()[1] == 3) {
							String errormsg = "Error. Illegal TFTP operation.";
							byte[] error = new byte[errormsg.length() + 5];
							error[0] = 0;
							error[1] = 5;
							error[2] = 0;
							error[3] = 4;

							for (int i = 0; i < errormsg.length(); i++) {
								error[4 + i] = errormsg.getBytes()[i];
							}

							error[4 + errormsg.length()] = 0;

							sendPacket = new DatagramPacket(error, error.length, receivePacket.getAddress(),receivePacket.getPort());
							printSendPacket(sendPacket, "Read", verbose);
							sendReceiveSocket.send(sendPacket);
							return;
						}
						if (currentBlock == getBlock(receivePacket.getData())) {
							currentBlock++;
							receivedACK = true;
							duplicate = false;
							tryCount = 0;
						} else {
							duplicate = true;
						}
					} catch (SocketTimeoutException e) {
						duplicate = false;
						// sendReceiveSocket.send(sendPacket);
						if (tryCount < 4 && verbose) {
							System.out.println("Socket Timeout. Resending: ");
						}
						tryCount++;
					}

					if (tryCount >= 5) {
						System.out.println("Error. Server not responding.");
						return;
					}
				}
			}

			if ((n == -1 && sendPacket.getLength() == 516) || (n == -1 && sendPacket.getLength() == 4)) {
				if ((int) block2 == -1)
					block1++;
				block2++;
				receivedACK = false;
				byte[] message = new byte[4];
				message[0] = 0;
				message[1] = 3;
				message[2] = block1;
				message[3] = block2;
				sendPacket = new DatagramPacket(message, 4, ip, port);
				receivePacket = new DatagramPacket(resp, 4);
				while (!receivedACK) {
					if (duplicate == false) {
						sendReceiveSocket.send(sendPacket);
						printSendPacket(sendPacket, "Read", verbose);
					} 

					try {
						sendReceiveSocket.receive(receivePacket);
						printReceivePacket(receivePacket, "Read", verbose);
						if (receivePacket.getData()[1] == 5) {
							handleError(receivePacket);
							return;
						} else if (receivePacket.getData()[1] == 1 || receivePacket.getData()[1] == 2
								|| receivePacket.getData()[1] == 3) {
							String errormsg = "Error. Illegal TFTP operation.";
							byte[] error = new byte[errormsg.length() + 5];
							error[0] = 0;
							error[1] = 5;
							error[2] = 0;
							error[3] = 4;

							for (int i = 0; i < errormsg.length(); i++) {
								error[4 + i] = errormsg.getBytes()[i];
							}

							error[4 + errormsg.length()] = 0;

							sendPacket = new DatagramPacket(error, error.length, receivePacket.getAddress(),
									receivePacket.getPort());
							printSendPacket(sendPacket, "Read", verbose);
							sendReceiveSocket.send(sendPacket);
							return;
						}
						if (currentBlock == getBlock(receivePacket.getData())) {
							receivedACK = true;
							duplicate = false;
							tryCount = 0;
						}else{
							duplicate = true;
						}
					} catch (SocketTimeoutException e) {
						duplicate = false;
						if (tryCount < 4 && verbose) {
							System.out.println("Resending: ");
						}
						tryCount++;
					}
					if (tryCount >= 5) {
						if (clientOwner == null) {
							System.out.println("Error. Client not responding.");
							return;
						} else {
							System.out.println("Error. Server not responding.");
							return;
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void write(BufferedOutputStream out, String filename, DatagramSocket commSocket)throws FileNotFoundException, IOException { // ACK
		byte[] response = new byte[4];
		boolean receivedDATA = false;
		int tryCount = 0;
		response[0] = 0;
		response[1] = 4;
		byte[] data = new byte[516];
		byte opcode;
		DatagramPacket sendPacket, receivePacket;
		int currentBlock = 1;

		do {
			receivePacket = new DatagramPacket(data, data.length);
			while (!receivedDATA) {
				try {
					commSocket.receive(receivePacket);
					opcode = receivePacket.getData()[1];
					if (opcode == 5) {
						printReceivePacket(receivePacket, "Write", verbose);
						handleError(receivePacket);
						out.close();
						sendPacket = new DatagramPacket(receivePacket.getData(), receivePacket.getData().length,
								receivePacket.getAddress(), receivePacket.getPort());
						if (clientOwner == null) {
							serverOwner.deleteCreatedFile();
						} else {
							clientOwner.deleteCreatedFile();
						}
						printSendPacket(sendPacket, "Write", verbose);
						commSocket.send(sendPacket);
						return;
					} else if (opcode == 1 || opcode == 2 || opcode == 4) {
						printReceivePacket(receivePacket, "Write", verbose);
						String errormsg = "Error. Illegal TFTP operation.";
						byte[] error = new byte[errormsg.length() + 5];
						error[0] = 0;
						error[1] = 5;
						error[2] = 0;
						error[3] = 4;

						for (int i = 0; i < errormsg.length(); i++) {
							error[4 + i] = errormsg.getBytes()[i];
						}

						error[4 + errormsg.length()] = 0;

						sendPacket = new DatagramPacket(error, error.length, receivePacket.getAddress(),
								receivePacket.getPort());
						printSendPacket(sendPacket, "Write", verbose);
						commSocket.send(sendPacket);
						if (clientOwner == null) {
							serverOwner.deleteCreatedFile();
						} else {
							clientOwner.deleteCreatedFile();
						}
						return;
					} else {
						printReceivePacket(receivePacket, "Write", verbose);
						tryCount = 0;
						receivedDATA = true;
					}
				} catch (SocketTimeoutException e) {
					System.err.println("Packet lost.");
					System.out.println("Sending again!");
					tryCount++;
				}

				if (tryCount >= 5) {
					if (clientOwner == null) {
						System.out.println("Error. Client not responding.");
						return;
					} else {
						System.out.println("Error. Server not responding.");
						return;
					}
				}
			}

			if (currentBlock == getBlock(receivePacket.getData())) {
				out.write(data, 4, receivePacket.getLength() - 4);
				currentBlock++;
			}

			System.arraycopy(receivePacket.getData(), 2, response, 2, 2);

			sendPacket = new DatagramPacket(response, response.length, receivePacket.getAddress(), receivePacket.getPort());

			commSocket.send(sendPacket);
			printSendPacket(sendPacket, "Write", verbose);
			receivedDATA = false;
		} while (receivePacket.getLength() == 516);
		out.close();
	}

	public static void printReceivePacket(DatagramPacket p, String source, boolean verbose) {
		if (verbose) {
			int op = p.getData()[1];
			System.out.println(source + ": packet received.");
			System.out.println("From host: " + p.getAddress());
			System.out.println("Host port: " + p.getPort());
			int len = p.getLength();
			System.out.println("Length: " + len);
			System.out.println("Packet type: " + opcode[op]);
			if (op < 3) {
				System.out.println("Filename: " + getFilename(p));
			} else if (op == 3) {
				System.out.println("Number of bytes: " + (len - 4));
				System.out.println("Block number " + getBlock(p.getData()));
			} else if (op == 4) {
				System.out.println("Block number " + getBlock(p.getData()));
			} else {
				System.out.println("Error Code: " + p.getData()[3]);
			}
			System.out.println();
		}
	}

	public static void printSendPacket(DatagramPacket p, String source, boolean verbose) {
		if (verbose) {
			int op = p.getData()[1];
			System.out.println(source + ": packet sent.");
			System.out.println("To host: " + p.getAddress());
			System.out.println("Destination host port: " + p.getPort());
			int len = p.getLength();
			System.out.println("Length: " + len);
			System.out.println("Packet type: " + opcode[op]);
			if (op < 3) {
				System.out.println("Filename: " + getFilename(p));
			} else if (op == 3) {
				System.out.println("Number of bytes: " + (len - 4));
				System.out.println("Block number " + getBlock(p.getData()));
			} else if (op == 4) {
				System.out.println("Block number " + getBlock(p.getData()));
			} else {
				System.out.println("Error Code: " + p.getData()[3]);
			}
			System.out.println();
		}
	}

	public void handleError(DatagramPacket packet) {
		byte[] errorPacket = packet.getData();
		int i;
		String errorMessage;

		for (i = 4; i < errorPacket.length; i++) {
			if (errorPacket[i] == 0) {
				break;
			}
		}
		errorMessage = new String(errorPacket, 4, i - 4);
		System.out.println(errorMessage);
		System.out.println();
	}

	public static int getBlock(byte[] data) {
		int x = (int) data[2];
		int y = (int) data[3];
		if (x < 0) {
			x = 256 + x;
		}
		if (y < 0) {
			y = 256 + y;
		}
		return 256 * x + y;
	}

	public static String getFilename(DatagramPacket receivePacket) {
		// TODO Auto-generated method stub
		int r = receivePacket.getLength();
		byte[] data = receivePacket.getData();
		int j;
		for (j = 2; j < r; j++) {
			if (data[j] == 0)
				break;
		}
		return new String(data, 2, j - 2);
	}

	public void setVerbose(boolean v) {
		this.verbose = v;
	}
}