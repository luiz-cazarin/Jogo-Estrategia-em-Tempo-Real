package tela;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Subir_Servidor extends Thread {
	public Principal tela;
	boolean levantado = true;
	ServerSocket servidor;

	public Subir_Servidor(Principal tela) {
		this.tela = tela;
		this.levantado = true;
	}

	public void run() {
		ArrayList<Jogador> jogador = new ArrayList<>();
		List<ObjectOutputStream> saidas = new ArrayList<ObjectOutputStream>();
		System.out.println("Servidor levantado...");
		try {
			this.servidor = new ServerSocket(12345);
			while (levantado == true) {
				Socket conexao = servidor.accept();
				(new ServidorTCP(conexao, saidas, tela, jogador)).start();
			}
			for (ObjectOutputStream saida : saidas) {
				saida.close();
			}

		} catch (IOException e) {
		}
	}

	public void encerrar_servidor() {
		try {
			this.levantado = false;
			this.servidor.close();
		} catch (IOException e) {

		}
	}

}
