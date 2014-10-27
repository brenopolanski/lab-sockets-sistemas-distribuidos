package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPCliente {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		final String IP_CLIENTE = "127.0.0.1";
		final int PORTA_CLIENTE = 7896;
		
		String resultado;
		
		System.out.println("Deseja se conectar ao servidor de mensagem (S/N)?");
		resultado = new Scanner(System.in).next();
		
		while(!resultado.equals("n") && !resultado.equals("N")) {
			Socket cliente = new Socket(IP_CLIENTE, PORTA_CLIENTE);
			DataInputStream input = new DataInputStream(cliente.getInputStream());
			DataOutputStream output = new DataOutputStream(cliente.getOutputStream());
			
			System.out.println("Digite um número de 1 a 10 para solicitar ao servidor uma mensagem. "
					+ "Digite 0 para solicitar uma mensagem qualquer:");
			int numeroMsg = new Scanner(System.in).nextInt();
			
			output.writeInt(numeroMsg);
			
			try {
				cliente.setSoTimeout(3000);
			} catch (Exception ex) {
				cliente.close();
			}
			
			String msgServidor = input.readUTF();
			
			System.out.println("Mensagem retornada do servidor: " + msgServidor);
			
			System.out.println("Deseja outra mensagem (S/N)?");
			resultado = new Scanner(System.in).next();
			
			cliente.close();
		}
	}

}
