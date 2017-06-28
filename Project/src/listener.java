import java.net.DatagramPacket;
import java.util.Scanner;

public class listener extends Thread {
	Scanner sc;
	TFTPServer s;

	public listener(TFTPServer s) {
		this.s = s;
		sc = new Scanner(System.in);
	}

	@Override
	public void run() {
		while (true) {
			if (sc.hasNext()) {
				String t = sc.next();
				if (t.contains("q")) {
					s.quit();
					return;
				} else if (t.equals("v")){
					s.toggleVerbose();
					if(s.isVerbose()){
						System.out.println("Verbose mode is on.");
					} else {
						System.out.println("Verbose mode is off.");
					}
				} else {
					System.out.println("Invalid command.");
				}
				sc.reset();
			}
		}
	}
}