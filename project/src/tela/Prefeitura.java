package tela;

import java.util.ArrayList;

public class Prefeitura extends Thread {
	private AcaoPrefeitura acaoPrefeitura;
	public ArrayList<MinaDeOuro> minas = new ArrayList<MinaDeOuro>();
	public ArrayList<Fazenda> fazendas = new ArrayList<Fazenda>();
	public ArrayList<Aldeao> aldeoes = new ArrayList<Aldeao>();

	public Templo templo;
	public Fazenda fazenda;
	public Maravilha maravilha;
	public Principal tela;
	public Aldeao aldeao;
	public boolean vivo;

	public Vila vila = new Vila(minas, fazendas, aldeoes, maravilha, templo, tela);

	public Prefeitura(Principal tela) {
		this.vivo = true;
		this.tela = tela;
		this.templo = (new Templo(tela, vila));

		// Pre criando fazenda
		fazendas.add(new Fazenda(1, this.tela, this.vila));
		this.tela.adicionarFazenda("" + 1, "Fazenda");
		// Pre criando Mina de Ouro
		minas.add(new MinaDeOuro(1, this.tela, this.vila));
		this.tela.adicionarMinaOuro("" + 1, "Mina");

		vila.contFazenda++;
		vila.contMina++;

		// Pre Criando Aldeoes
		for (int i = 0; i < 5; i++) {
			Aldeao aldeao = new Aldeao(i + 1, this, templo, maravilha, vila, tela);
			aldeoes.add(aldeao);
			tela.adicionarAldeao("" + (i + 1), "Aldeao");
			aldeao.start();
			vila.contAldeao = (i);
		}

		this.acaoPrefeitura = AcaoPrefeitura.PARAR;
	}

	@Override
	public synchronized void run() {
		while (this.vivo) {
			switch (this.acaoPrefeitura) {
			case CRIAR_ALDEAO:
				this.criarAldeao();
				break;
			case EVOLUIR_ALDEAO:
				this.evoluirAldeao();
				break;
			case EVOLUIR_FAZENDA:
				this.evoluirFazenda();
			case EVOLUIR_MINA_OURO:
				this.evoluirMinaDeOuro();
				break;
			case PARAR:
				this.parar();
				break;
			}
		}
	}

	public void criarAldeao() {
		if (this.vila.qtdComida >= 100) {
			try {
				this.vila.contAldeao++;
				Aldeao aldeao = new Aldeao(aldeoes.size() + 1, this, templo, maravilha, vila, tela);
				aldeoes.add(aldeao);
				this.vila.qtdComida = this.vila.qtdComida - 100;
				this.tela.mostrarComida(vila.qtdComida);
				this.tela.adicionarAldeao("" + (aldeao.id), "parado");
				aldeao.start();
				System.out.println("CRIOU O ALDEAO ........." + this.aldeoes.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.parar();
		} else {
			this.tela.mostrarMensagemErro("Prefeitura - Aldeao", "Comida insuficiente");
			this.parar();
		}

	}

	// EVOLUCOES
	public void evoluirAldeao() {
		// Evolucao do aldeao
		// Custo: comida=5.000 + ouro=5.000 (pré-pago)
		// Função: dobra a produção do aldeão fazendeiro e minerador no mesmo intervalo
		// de tempo e
		// reduz pela metade o tempo de transporte da produção da fazenda e da mina de
		// ouro.
		// Tempo de evolução: 100 horas
		if (vila.qtdComida >= 5000 && vila.qtdOuro >= 5000) {
			for (Aldeao aldeao : aldeoes) {
				aldeao.level++;
			}
			this.vila.qtdComida = this.vila.qtdComida - 5000;
			this.vila.qtdOuro = this.vila.qtdOuro - 5000;
			this.tela.mostrarComida(vila.qtdComida);
			this.tela.mostrarOuro(vila.qtdOuro);
			try {
				Thread.sleep(100 * vila.HORA); // 100
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.parar();
		} else {
			this.tela.mostrarMensagemErro("Erro - Evolucao Aldeao", "Recursos insuficientes para evoluir os aldeoes");
			this.parar();
		}
	}

	public void evoluirFazenda() {
		// Evolucao da fazenda
		// Custo: comida=500 + ouro=5.000 (pré-pago)
		// Função: dobra a capacidade da fazenda de 5 aldeões fazendeiros simultâneos
		// para 10
		// Tempo de evolução: 100 horas
		if (vila.qtdComida >= 500 && vila.qtdOuro >= 5000) {
			this.vila.setLimiteAldFazenda(10);
			for (Fazenda fazenda : fazendas) {
				fazenda.limite = vila.getLimiteAldFazenda();
				System.out.println("fazenda limite = " + fazenda.limite);
			}

			this.vila.qtdComida = this.vila.qtdComida - 500;
			this.vila.qtdOuro = this.vila.qtdOuro - 5000;

			this.tela.mostrarComida(vila.qtdComida);
			this.tela.mostrarOuro(vila.qtdOuro);

			try {
				Thread.sleep(100 * vila.HORA); // 100
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.parar();
		} else {
			this.tela.mostrarMensagemErro("Erro - Evolucao Fazenda",
					"Recursos insuficientes para evoluir as fazendas ");
			this.parar();
		}

	}

	public void evoluirMinaDeOuro() {
		// Evolucao da mina de ouro
		// Custo: comida=2.000 + ouro=1.000 (pré-pago)
		// Função: dobra a capacidade da fazenda de 5 aldeões mineradores simultâneos
		// para 10
		// Tempo de evolução: 100 horas
		if (vila.qtdComida >= 2000 && vila.qtdOuro >= 1000) {
			this.vila.setLimiteAldMina(10);
			for (MinaDeOuro mina : minas) {
				mina.limite = vila.getLimiteAldMina();
				System.out.println("mina limite = " + mina.limite);
			}
			this.vila.qtdComida = this.vila.qtdComida - 2000;
			this.vila.qtdOuro = this.vila.qtdOuro - 1000;

			this.tela.mostrarComida(vila.qtdComida);
			this.tela.mostrarOuro(vila.qtdOuro);

			try {
				Thread.sleep(100 * vila.HORA); // 100
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.parar();
		} else {
			this.tela.mostrarMensagemErro("Erro - Evolucao Mina de Ouro",
					"Recursos insuficientes para evoluir as minas de ouro ");
			this.parar();
		}

	}

	public synchronized void fazer(AcaoPrefeitura acao) {
		this.acaoPrefeitura = acao;
		this.notify();
	}

	public synchronized void parar() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Prefeitura EMPIRE";
	}

}
