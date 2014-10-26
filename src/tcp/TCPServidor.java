package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
	
	public String getMensagem(int numeroMsg) {
		String msg = "";
		int x;
		List<String> mensagens = new ArrayList<String>();
		mensagens.add("Uma v�s eu respirei� e havia notado que eu respirava.");
		mensagens.add("A vida n�o se baseia na base do baseado!");
		mensagens.add("Estava com tanto sono que fiquei com pregui�a de dormir!");
		mensagens.add("N�o me recordo de ver o rosto daquele que n�o vi uma vez�");
		mensagens.add("Me lembrei que estava com tanta fome que devorei meus pensamentos.");
		mensagens.add("A primeira vez que te vi tive a impress�o de que nunca tinha te visto.");
		mensagens.add("De cada dez pessoas que assistem TV cinco � a metade.");
		mensagens.add("A beleza me persegue mas eu sou mais r�pido.");
		mensagens.add("Hoje lembrei que havia lembrado de uma lembran�a na qual n�o me lembro!");
		mensagens.add("Melhor um peito na m�o, do que dois no suti�!");
		mensagens.add("Melhor um ovo verde na m�o, do que duas batatas voadoras, nadando!");
		
		if (numeroMsg == 0) {
			x = (int)Math.round(Math.random() * (mensagens.size() - 1));
			msg = (String) mensagens.get(x);
		} else if (numeroMsg < 0 || numeroMsg > mensagens.size()) {
			msg = "N�mero solicitado n�o � v�lido. Apenas s�o v�lidos n�meros de 1 a 10 ou 0 para "
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
