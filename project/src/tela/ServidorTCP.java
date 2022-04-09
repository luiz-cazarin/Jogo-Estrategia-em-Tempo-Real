package tela;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServidorTCP extends Thread {
	public ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	public Principal tela;
	public Jogador jogador;
	public Vila vila;
	public Templo templo;
	public Prefeitura prefeitura;
	public Socket porta;
	public List<ObjectOutputStream> saidas;

	public ServidorTCP(Socket porta, List<ObjectOutputStream> saidas, Principal tela, ArrayList<Jogador> jogadores) {
		System.out.println("[servidorTCP] Cliente conectado: " + porta.getInetAddress().getHostAddress());
		this.porta = porta;
		this.saidas = saidas;
		this.jogadores = jogadores;
		this.tela = tela;
	}

	public void run() {
		DateFormat formato = new SimpleDateFormat("HH:mm");
		try {
			ObjectInputStream entrada = new ObjectInputStream(this.porta.getInputStream());
			ObjectOutputStream saida = new ObjectOutputStream(this.porta.getOutputStream());
			synchronized (this.saidas) {
				this.saidas.add(saida);
			}
			String mensagem;
			try {
				// Pegando a mensagem/comando, comparando com o comando (CMD|DESCONECTAR)
				while (!(mensagem = (String) entrada.readObject()).equals("CMD|DESCONECTAR")) {

					// Usamos o '|' para indentificar o separador da string
					// Usamos o substring para pegar parte da string: ex: ola mundo|enviarMensagem
					// string1 = ola mundo, string2 = eviarMensagem
					// separamos a mensagem para pegar os comandos

					switch ((mensagem.substring(mensagem.indexOf("|") + 1))) {
					case "adicionarjogador":

						jogador = (Jogador) entrada.readObject();
						jogador.setIp(porta.getInetAddress().getHostAddress());
						jogador.setSituacao("Aguardando...");
						jogadores.add(jogador);
						jogador.saida = saida;

						synchronized (this.saidas) {
							for (ObjectOutputStream saida2 : this.saidas) {
								saida2.writeObject("listarJogadores");
								saida2.writeObject(new ArrayList<Jogador>(jogadores));

							}
						}
						super.setName(jogador.getNome());
						break;
					case "enviarMensagem":

						synchronized (this.saidas) {
							// Formatando a mensagem
							String msg = jogador.getCivilizacao() + " (" + formato.format(new Date()) + ") - "
									+ jogador.getNome() + ": " + mensagem.substring(0, mensagem.indexOf("|"));

							for (ObjectOutputStream saida2 : this.saidas)
								saida2.writeObject(msg + "|" + "enviarMensagem");

						}
						break;
					case "ganhar":
						synchronized (this.saidas) {
							for (Jogador jogador : jogadores) {
								if (jogador.getNome().equals(super.getName())) {
									jogador.saida.writeObject(jogador.getNome() + "|" + "ganhar");
								}
								tela.tpJogo.setEnabledAt(1, true);
								tela.tpJogo.setSelectedIndex(0);
							}

						}
						break;
					case "iniciarJogo":
						// mandando os clientes iniciarem o jogo
						synchronized (this.saidas) {
							for (ObjectOutputStream saida2 : this.saidas) {
								saida2.writeObject("iniciarJogo");
							}
						}

						break;
					default:
						// Lancamento
						// Servidor le e interpreta
						System.out.println("Servidor[TCP] Lancar: " + mensagem);
						String jogadorPrincipal = super.getName();
						String jogadorInimigo = mensagem.substring(mensagem.lastIndexOf("|") + 1);
						String praga = mensagem.substring(0, mensagem.indexOf("|"));

						for (Jogador jogador : jogadores) {
							if (jogador.getNome().equals(jogadorInimigo)) {
								jogador.saida.writeObject(jogadorPrincipal + "|" + praga + "|" + jogadorInimigo);
							}
						}

					}

				}

			} catch (SocketException e) {
			}

			// Saindo
			synchronized (this.saidas) {
				this.saidas.remove(saida);
			}

			saida.close();
			entrada.close();
			System.out.println("Cliente desconectado: " + porta.getInetAddress().getHostAddress());
			this.porta.close();
		} catch (IOException | ClassNotFoundException e) {
		}
	}

}
