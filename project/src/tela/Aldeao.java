package tela;

import java.awt.Color;

public class Aldeao extends Thread {

	private AcaoAldeao acao;
	public Principal tela;
	public Prefeitura prefeitura;
	public Templo templo;
	public Maravilha maravilha;

	public Fazenda fazenda;
	public MinaDeOuro mina;

	private boolean vivo;
	public int level;

	private int capacidadeCultivo;
	private int capacidadeMineracao;

	private int tempoProducaoCultivo;
	private int tempoTransporteCultivo;
	private int tempoTransporteMineracao;
	private int tempoProducaoMineracao;

	public Vila vila;
	public int id;

	public Aldeao(int id, Prefeitura prefeitura, Templo templo, Maravilha maravilha, Vila vila, Principal tela) {
		this.id = id;
		this.prefeitura = prefeitura;
		this.templo = templo;
		this.maravilha = maravilha;
		this.vila = vila;
		this.tela = tela;
		this.vivo = true;
		this.level = 0;
		this.capacidadeCultivo = 10;
		this.capacidadeMineracao = 5;
		this.acao = AcaoAldeao.PARADO;
	}

	public void run() {
		while (this.vivo) {
			switch (acao) {
			case CONSTRUINDO_FAZENDA:
				this.construirFazenda();
				break;
			case CONSTRUINDO_MINA:
				this.construirMinaDeOuro();
				break;
			case CONSTRUINDO_TEMPLO:
				this.construirTemplo();
				break;
			case CONSTRUINDO_MARAVILHA:
				this.construirMaravilha();
				break;
			case ORANDO:
				this.orar();
				break;
			case SACRIFICANDO:
				this.sacrificar();
				break;
			case CULTIVANDO:
				this.cultivar();
				break;
			case MINERANDO:
				this.minerar();
				break;
			case PARADO:
				this.parar();
				break;
			}
		}

	}

	// CONSTRUCAO
	public void construirFazenda() {
		if (vila.qtdComida >= 100 && vila.qtdOuro >= 500) {
			vila.contFazenda++;
			this.tela.mostrarAldeao(id, "construindo");
			Fazenda f = new Fazenda(vila.contFazenda, tela, vila);
			vila.fazendas.add(f);

			this.vila.qtdComida -= 100;
			this.vila.qtdOuro -= 500;

			tela.mostrarComida(vila.qtdComida);
			tela.mostrarOuro(vila.qtdOuro);
			this.tela.adicionarFazenda("" + (vila.fazendas.size()), "Fazenda");

			try {
				Thread.sleep(3 * vila.HORA); // 30
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.tela.mostrarFazenda(vila.fazendas.size(), "Fazenda");
			this.parar();
		} else {
			this.tela.mostrarMensagemErro("Aldeao - Construir Fazenda", "Nao ha Comida e Ouro suficiente");
			this.parar();
		}

	}

	public void construirMinaDeOuro() {
		if (vila.qtdComida >= 1000) {
			vila.contMina++;
			this.tela.mostrarAldeao(id, "construindo");
			MinaDeOuro m = new MinaDeOuro(vila.contMina, tela, vila);
			vila.minas.add(m);

			this.vila.qtdComida -= 1000;

			this.tela.mostrarComida(vila.qtdComida);
			this.tela.adicionarMinaOuro("" + vila.minas.size(), "Mina");

			try {
				Thread.sleep(4 * vila.HORA); // 40
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			this.tela.mostrarMinaOuro(vila.minas.size(), "Mina");
			this.parar();
		} else {
			this.tela.mostrarMensagemErro("Aldeao - Construir Mina", "Nao ha Comida suficiente");
			this.parar();
		}
	}

	public void construirTemplo() {
		if (vila.qtdComida >= 2000 && vila.qtdOuro >= 2000 && vila.contTemplo < 1) {

			vila.contTemplo++;
			this.tela.mostrarAldeao(id, "construindo");
			this.vila.qtdComida -= 2000;
			this.vila.qtdOuro -= 2000;
			Templo templo = new Templo(tela, vila);

			try {
				Thread.sleep(1 * vila.HORA); // 100
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			this.tela.habilitarTemplo();
			this.tela.mostrarTemplo(templo.toString(), Color.MAGENTA);
			this.tela.mostrarComida(vila.qtdComida);
			this.tela.mostrarOuro(vila.qtdOuro);

			this.vila.templo = templo;
			templo.start();
			this.parar();
		} else if (!(vila.contTemplo < 1)) {
			this.tela.mostrarMensagemErro("Aldeao - Construir Templo", "Maximo 1 templo");
			this.parar();
		} else {
			this.tela.mostrarMensagemErro("Aldeao - Construir Templo", "Nao ha Comida e Ouro suficiente");
			this.parar();
		}
	}

	public void construirMaravilha() {
		if (this.vila.qtdComida >= 1 && this.vila.qtdOuro >= 1) {
			try {
				Maravilha maravilha = new Maravilha(vila, tela);
				this.tela.mostrarAldeao(id, "construindo");
				this.vila.qtdComida -= 1; // 1
				this.vila.qtdOuro -= 1; // 1
				maravilha.inserirTijolo();
				Thread.sleep(1 * vila.HORA); // 10
				this.tela.habilitarMaravilha();
				this.tela.mostrarComida(vila.qtdComida);
				this.tela.mostrarOuro(vila.qtdOuro);
				if (this.vila.qtdtijolo >= 100000) {
					this.tela.ganharJogo();
					this.parar();
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			this.tela.mostrarMensagemErro("Erro[ALDEAO] Maravilha", "Nao a recursos.");
		}

	}

	// ACAO
	// As acoes serao executadas infinitamente ate que as condicoes sejam
	// satisfeitas ou ate o wait
	public synchronized void cultivar() {
		// selecionar fazenda
		// Função: produção de comida
		// Necessidade de construtores: 1
		if (this.fazenda.vivo == true) {

			this.tempoTransporteCultivo = 2 * vila.HORA;
			this.tempoProducaoCultivo = 1 * vila.HORA;

			this.tela.mostrarAldeao(id, "cultivando");
			if (level == 0) {
				this.fazenda.produzirComida(vila, id, capacidadeCultivo);
			} else if (level >= 1) {
				this.fazenda.produzirComida(vila, id, capacidadeCultivo * (level + 1));
				this.tempoTransporteCultivo = (this.tempoTransporteCultivo / 2);
			}

			System.out.println("Cultivando - Level : " + level);
			this.tela.mostrarComida(vila.qtdComida);

			try {
				Thread.sleep(tempoTransporteCultivo + tempoProducaoCultivo); // 1 + 2
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			this.tela.mostrarMensagemErro("Aldeao", "Fazenda MORTA!!!");
			this.parar();
		}
	}

	public synchronized void minerar() {
		// selecionar mina de ouro
		// Função: produção de comida
		// Necessidade de construtores: 1
		this.tempoTransporteMineracao = 3 * vila.HORA;
		this.tempoProducaoMineracao = 2 * vila.HORA;

		this.tela.mostrarAldeao(id, "minerando");
		if (level == 0) {
			this.mina.produzirOuro(vila, id, capacidadeMineracao);
		} else if (level >= 1) {
			this.mina.produzirOuro(vila, id, capacidadeMineracao * (level + 1));
			this.tempoTransporteMineracao = (this.tempoTransporteMineracao / 2);
		}

		System.out.println("Minerando - Level : " + level);
		this.tela.mostrarOuro(vila.qtdOuro);

		try {
			Thread.sleep(this.tempoProducaoMineracao + this.tempoTransporteMineracao); // 2 + 3
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void orar() {
		// Função 1: produção de oferendas de fé
		// Produção por religioso na função 1: 2 unidades a cada 5 hora
		if (vila.contTemplo >= 1) {

			this.tela.mostrarAldeao(id, "orando");
			this.vila.qtdoferendasFe = this.vila.qtdoferendasFe + 2;

			try {
				Thread.sleep(5 * vila.HORA);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.tela.mostrarOferendaFe(vila.qtdoferendasFe);
		} else {
			this.tela.mostrarMensagemErro("Aldeao - Orar", "Crie um Templo primeiro");
			this.parar();
		}

	}

	public void sacrificar() {
		// Matar thread
		if (vila.contTemplo >= 1) {
			this.tela.mostrarAldeao(this.id, "sacrificado");
			this.vila.qtdoferendasFe = this.vila.qtdoferendasFe + 100;
			this.tela.mostrarOferendaFe(vila.qtdoferendasFe);
			this.vivo = false;
		} else {
			this.tela.mostrarMensagemErro("Aldeao - Sacrificar", "Crie um Templo primeiro");
			this.parar();
		}
	}

	public void matar() {
		if (this.vila.contAldeao >= 1) {
			this.tela.mostrarAldeao(this.id, "MORTO!");
			this.vivo = false;
		} else {
			this.tela.mostrarMensagemErro("Aldeao - MATAR!", "Nao ha aldeoes");
			this.parar();
		}
	}

	public void parar() {
		synchronized (this) {
			try {
				this.tela.mostrarAldeao(this.id, "parado");
				System.out.println("----------------------> Parando aldeao: " + this.id);
				this.wait(); // Manda dormir
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void fazer(AcaoAldeao acao) {
		synchronized (this) {
			this.acao = acao;
			this.notify(); // Desperta
		}
	}

	@Override
	public String toString() {
		return "" + this.id;
	}

}
