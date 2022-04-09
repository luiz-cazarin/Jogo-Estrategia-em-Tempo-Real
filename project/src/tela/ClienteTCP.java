package tela;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClienteTCP extends Thread {
	public Socket porta;
	public ObjectOutputStream saida;
	public ClienteTCP clienteRecebe;
	public boolean escutando;
	public Principal tela;
	public Vila vila;
	public Templo templo;
	public ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	public Jogador jogador;

	public ClienteTCP(Principal tela) {
		this.tela = tela;
		this.escutando = true;
	}

	public ClienteTCP(Socket porta, Principal tela) {
		this.porta = porta;
		this.tela = tela;
		this.escutando = true;
	}

	public ClienteTCP(Socket porta, Principal tela, Jogador jogador) {
		this.porta = porta;
		this.tela = tela;
		this.escutando = true;
		this.jogador = jogador;
	}

	@SuppressWarnings("unchecked")
	public void run() {
		String mensagem;
		ObjectInputStream entrada = null;
		try {
			entrada = new ObjectInputStream(this.porta.getInputStream());
			while (this.escutando) {
				mensagem = (String) entrada.readObject();
				// mensagem|comando
				switch (mensagem.substring(mensagem.indexOf("|") + 1)) {
				case "iniciarJogo":
					tela.limparJogadores();
					tela.limparMensagens();
					for (Jogador jogador : jogadores) {
						jogador.setSituacao("Jogo rodando...");

						tela.adicionarJogador(jogador.getNome(), jogador.getCivilizacao(), jogador.getIp(),
								jogador.getSituacao());

						tela.tpJogo.setEnabledAt(1, true);
						tela.tpJogo.setSelectedIndex(1);
					}

					break;
				case "enviarMensagem":
					this.tela.adicionarMensagem(mensagem.substring(0, mensagem.indexOf("|")));
					System.out.println("[ClienteTCP] EnviarMensagem: " + mensagem);
					break;
				case "ganhar":
					tela.tpJogo.setEnabledAt(1, true);
					tela.tpJogo.setSelectedIndex(0);
					this.tela.adicionarMensagem(mensagem.substring(0, mensagem.indexOf("|")) + " GANHOU");
					break;
				case "listarJogadores":
					this.jogadores = (ArrayList<Jogador>) entrada.readObject();
					this.tela.limparJogadores();
					this.tela.limparInimigos();

					for (Jogador jogador : jogadores) {

						this.tela.adicionarJogador(jogador.getNome(), jogador.getCivilizacao(), jogador.getIp(),
								jogador.getSituacao());
						this.tela.adicionarInimigo(jogador.getNome());
					}
					break;
				default:
					// Lancar

					System.out.println("LANCAR-MSG: " + mensagem);

					String jogadorPrincipal = mensagem.substring(0, mensagem.indexOf("|"));
					String jogadorInimigo = mensagem.substring(mensagem.lastIndexOf("|") + 1, mensagem.length());
					String praga = mensagem.substring(mensagem.indexOf("|") + 1, mensagem.lastIndexOf("|"));
					super.setName(jogadorPrincipal);

					if (!(jogadorPrincipal.equals(jogadorInimigo))) {
						System.out.println("Jogador principal: " + jogadorPrincipal + " lancou " + praga
								+ " no  Jogador inimigo: " + jogadorInimigo);

						switch (praga) {
						case "Nuvem de gafanhotos":
							for (Jogador jogador : jogadores) {
								if (jogador.getNome().equals(jogadorInimigo)) {
									this.tela.prefeitura.templo.lancarNuvemGafanhotos();
								}
							}
							break;
						case "Morte dos primogênitos":
							for (Jogador jogador : jogadores) {
								if (jogador.getNome().equals(jogadorInimigo)) {
									this.tela.prefeitura.templo.lancarMortePrimogênitos();
								}

							}
							break;
						case "Chuva de pedras":
							for (Jogador jogador : jogadores) {
								if (jogador.getNome().equals(jogadorInimigo)) {
									this.tela.prefeitura.templo.lancarChuvaPedras();
								}

							}
							break;
						default:
							break;
						}

					} else {
						this.tela.mostrarMensagemErro("CLIENTE[TCP]", "Voce nao pode se atacar!!!");
					}
				}
			}
			entrada.close();
		} catch (IOException |

				ClassNotFoundException e) {
		}

	}

	public boolean conectar(String host, String nome, String civilizacao, String situacao, Prefeitura prefeitura) {
		try {
			this.porta = new Socket(host, 12345);
			this.saida = new ObjectOutputStream(this.porta.getOutputStream());
			this.saida.writeObject("adicionarjogador");
			this.saida.writeObject(new Jogador(nome, civilizacao, situacao));
			this.clienteRecebe = new ClienteTCP(porta, this.tela);
			this.clienteRecebe.start();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public void desconectar() {
		try {
			// passando o comando CMD|DESCONECTAR para o servidor
			this.saida.writeObject("CMD|DESCONECTAR");
			this.clienteRecebe.escutando = false;
			this.saida.close();
			this.porta.close();

		} catch (IOException e) {
		}
	}

	public void ganharJogo() {
		try {
			this.saida.writeObject(super.getName() + "|" + "ganhar");
		} catch (IOException e) {
		}
	}

	public void enviarMensagem(String mensagem) {
		try {
			this.saida.writeObject(mensagem + "|" + "enviarMensagem");
		} catch (IOException e) {
		}
	}

	public void lancar(String strPraga, String strInimigo) {
		try {
			if (strPraga.equals("Nuvem de gafanhotos")) {
				if (this.tela.prefeitura.vila.qtdoferendasFe >= 500) {
					this.saida.writeObject(strPraga + "|" + "lancar" + "|" + strInimigo);
					this.tela.prefeitura.vila.qtdoferendasFe -= 500;
					this.tela.mostrarOferendaFe(this.tela.prefeitura.vila.qtdoferendasFe);
				} else {
					this.tela.mostrarMensagemErro("ERRO - LANCAMENTO", "Falta recursos!!! ");
				}
			} else if (strPraga.equals("Morte dos primogênitos")) {
				if (this.tela.prefeitura.vila.qtdoferendasFe >= 750) {
					this.saida.writeObject(strPraga + "|" + "lancar" + "|" + strInimigo);
					this.tela.prefeitura.vila.qtdoferendasFe -= 750;
					this.tela.mostrarOferendaFe(this.tela.prefeitura.vila.qtdoferendasFe);
				} else {
					this.tela.mostrarMensagemErro("ERRO - LANCAMENTO", "Falta recursos!!! ");
				}

			} else if (strPraga.equals("Chuva de pedras")) {
				if (this.tela.prefeitura.vila.qtdoferendasFe >= 10000) {
					this.saida.writeObject(strPraga + "|" + "lancar" + "|" + strInimigo);
					this.tela.prefeitura.vila.qtdoferendasFe -= 10000;
					this.tela.mostrarOferendaFe(this.tela.prefeitura.vila.qtdoferendasFe);
				} else {
					this.tela.mostrarMensagemErro("ERRO - LANCAMENTO", "Falta recursos!!! ");
				}
			}
		} catch (IOException e) {
		}

	}

	public void iniciarJogo() {
		try {
			this.saida.writeObject("iniciarJogo");
		} catch (Exception e) {
		}

	}
}
