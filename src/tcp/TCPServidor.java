package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TCPServidor {

	public static void main(String[] args) throws IOException {
		final int PORTA_SERVIDOR = 7896;
		ServerSocket servidor = new ServerSocket(PORTA_SERVIDOR);
		System.out.println("Porta 7896 aberta!");
		while(true) {
			Socket cliente = servidor.accept();
			Connection conn = new Connection(cliente);
		}
	}

}

class Connection extends Thread {
	DataInputStream input;
	DataOutputStream output;
	Socket cliente;

	public Connection(Socket cliente) {
		try {
			this.cliente = cliente;
	    	this.input = new DataInputStream(this.cliente.getInputStream());
	    	this.output = new DataOutputStream(this.cliente.getOutputStream());
	    	this.start();
		} catch(IOException ex) {
			System.out.println("Connection:" + ex.getMessage());
		}
	}
	
	public String getMensagem(int numeroMsg) throws FileNotFoundException {
		String msg = "";
		int x;
		
		List<String> mensagens = new ArrayList<String>();
		
		// Professora mudar o Path ;)
		
		Scanner scanner = new Scanner(new FileReader("C:\\Users\\Breno\\workspace-java\\LabSocketsMsg\\src\\tcp\\arquivo.txt")).useDelimiter("\n");
		while (scanner.hasNext()) {
			mensagens.add(scanner.next());
		}		
		
		if (numeroMsg == 0) {
			x = (int)Math.round(Math.random() * (mensagens.size() - 1));
			msg = (String) mensagens.get(x);
		} else if (numeroMsg < 0 || numeroMsg > mensagens.size()) {
			msg = "Número solicitado não é válido. Apenas são válidos números de 1 a 10 ou 0 para "
					+ "solicitar uma mensagem qualquer.";
		} else {
			msg = (String) mensagens.get(numeroMsg);
		}
		
		return msg;
	}
	
	public void run() {
		try {
			int numeroMsg = input.readInt();
			String mensagem = getMensagem(numeroMsg); 
			output.writeUTF(mensagem);
	    } catch(EOFException ex) {
	    	System.out.println("EOF:" + ex.getMessage());
	    } catch(IOException ex) {
	    	System.out.println("IO:" + ex.getMessage());
	    } finally { 
	    	try {
	    		cliente.close();
	    	} catch (IOException ex) {
	    		System.out.println("IO:" + ex.getMessage());
	    	}
	    }
	}
}
