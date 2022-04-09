package tela;

public class Templo extends Thread {

	public AcaoTemplo acao;
	private Principal tela;
	private Vila vila;
	private boolean vivo;

	public Templo() {
	}

	public Templo(Principal tela, Vila vila) {
		this.tela = tela;
		this.vila = vila;
		this.vivo = true;
		this.acao = AcaoTemplo.PARAR;
	}

	@Override
	public synchronized void run() {

		while (this.vivo) {
			switch (acao) {
			case EVOLUCAO_CHUVA_PEDRA:
				this.evolucaoChuvaPedras();
				break;
			case EVOLUCAO_MORTE_PRIMOGENITO:
				this.evolucaoMortePrimogenitos();
				break;
			case EVOLUCAO_NUVEM_GARFANHOTO:
				this.evolucaoNuvemGafanhotos();
				break;
			case EVOLUCAO_PROTECAO_CHUVA_PEDRA:
				this.evolucaoProtecaoContraChuvaPedras();
				break;
			case EVOLUCAO_PROTECAO_MORTE_PRIMOGENITO:
				this.evolucaoProtecaoContraMortePrimogenitos();
				break;
			case EVOLUCAO_PROTECAO_NUVEM_GARFANHOTO:
				this.evolucaoProtecaoContraNuvemGafanhotos();
				break;
			case PARAR:
				this.parar();
				break;
			}
		}
	}

	// Evolucao Ataque
	public void evolucaoNuvemGafanhotos() {
		if (this.vila.qtdoferendasFe >= 1000) {
			this.vila.qtdoferendasFe -= 1000;
			this.vila.lancNuvemGarfanhoto = true;
			this.tela.evolucoes.add("NUVEM_GAFANHOTOS");

			try {
				Thread.sleep(50 * vila.HORA);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			this.tela.mostrarAtaques(this.tela.evolucoes);

			System.out.println("NuvemGafanhotos - FIM DA EVOLUCAO!");
			this.tela.mostrarOferendaFe(this.vila.qtdoferendasFe);
			this.parar();
		} else {
			this.tela.mostrarMensagemErro("ERRO - EVOLUCAO", "Oferendas de fe insuficiente");
			this.parar();
		}
	}

	public void evolucaoMortePrimogenitos() {
		if (this.vila.qtdoferendasFe >= 1500) {
			this.vila.qtdoferendasFe -= 1500;

			this.vila.lancMortePrimogenito = true;

			this.tela.evolucoes.add("MORTE_PRIMOGENITOS");

			try {
				Thread.sleep(100 * vila.HORA);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			this.tela.mostrarAtaques(this.tela.evolucoes);

			System.out.println("MortePrimogenitos - FIM DA EVOLUCAO!");
			this.tela.mostrarOferendaFe(this.vila.qtdoferendasFe);
			this.parar();
		} else {
			this.tela.mostrarMensagemErro("ERRO - EVOLUCAO", "Oferendas de fe insuficiente");
			this.parar();
		}

	}

	public void evolucaoChuvaPedras() {
//		Tempo de evolução: 200 horas

		if (this.vila.qtdoferendasFe >= 2000) {
			this.vila.qtdoferendasFe -= 2000;

			this.vila.lancChuvaPedra = true;

			this.tela.evolucoes.add("CHUVA_PEDRAS");

			try {
				Thread.sleep(2 * vila.HORA); // 200
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			this.tela.mostrarAtaques(this.tela.evolucoes);

			System.out.println("ChuvaPedras - FIM DA EVOLUCAO!");
			this.tela.mostrarOferendaFe(this.vila.qtdoferendasFe);
			this.parar();
		} else {
			this.tela.mostrarMensagemErro("ERRO - EVOLUCAO", "Oferendas de fe insuficiente");
			this.parar();
		}
	}

//-----------------------------------------------------------------------------------------------------------

	// Evolucao Defesa

	public void evolucaoProtecaoContraNuvemGafanhotos() {
//		Custo: oferendas de fé=5.000 (pré-pago)
//		Função: proteção total contra o lançamento de nuvem de gafanhoto
//		Tempo de evolução: 500 horas

		if (this.vila.qtdoferendasFe >= 5000) {
			this.vila.qtdoferendasFe -= 5000;
			this.vila.protecaoContraNuvemGafanhotos = true;

			try {
				Thread.sleep(5 * vila.HORA); // 500
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("ProtecaoContraNuvemGafanhotos - FIM DA EVOLUCAO!");
			this.tela.mostrarOferendaFe(this.vila.qtdoferendasFe);
			this.parar();
		} else {
			this.tela.mostrarMensagemErro("ERRO - EVOLUCAO", "Oferendas de fe insuficiente");
			this.parar();
		}
	}

	public void evolucaoProtecaoContraMortePrimogenitos() {
//		Custo: oferendas de fé=6.000 (pré-pago)
//		Função: proteção total contra o lançamento de morte dos primogênitos
//		Tempo de evolução: 600 horas

		if (this.vila.qtdoferendasFe >= 6000) {
			this.vila.qtdoferendasFe -= 6000;
			this.vila.protecaoContraMortePrimogenito = true;

			try {
				Thread.sleep(6 * vila.HORA); // 600
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("ProtecaoContraMortePrimogenitos - FIM DA EVOLUCAO!");
			this.tela.mostrarOferendaFe(this.vila.qtdoferendasFe);
			this.parar();
		} else {
			this.tela.mostrarMensagemErro("ERRO - EVOLUCAO", "Oferendas de fe insuficiente");
			this.parar();
		}
	}

	public void evolucaoProtecaoContraChuvaPedras() {
//		Custo: oferendas de fé=7.000 (pré-pago)
//		Função: proteção total contra o lançamento de chuva de pedras
//		Tempo de evolução: 700 horas

		if (this.vila.qtdoferendasFe >= 7000) {
			this.vila.qtdoferendasFe -= 7000;
			this.vila.protecaoContraChuvaPedra = true;

			try {
				Thread.sleep(7 * vila.HORA); // 700
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("ProtecaoContraChuvaPedras - FIM DA EVOLUCAO!");
			this.tela.mostrarOferendaFe(this.vila.qtdoferendasFe);
			this.parar();
		} else {
			this.tela.mostrarMensagemErro("ERRO - EVOLUCAO", "Oferendas de fe insuficiente");
			this.parar();
		}
	}

// -----------------------------------------------------------------------------------------------------------

	// Segunda Parte

	public void lancarNuvemGafanhotos() {
//		Custo: oferendas de fé=500 (pré-pago)
//		Função: Destruir metade das fazendas do inimigo selecionado
//		Tempo: imediato
		if (this.vila.contFazenda >= 2 && this.vila.protecaoContraNuvemGafanhotos == false) {
			// Matar metade das fazendas

			int aux = this.vila.contFazenda = this.vila.contFazenda / 2;

			for (int i = 0; i < aux; i++) {
				if (this.vila.fazendas.get(i).vivo == true) {
					this.vila.fazendas.get(i).vivo = false;
					this.tela.mostrarFazenda(this.vila.fazendas.get(i).id, "Destruida!");
				}
			}

		} else if (this.vila.protecaoContraNuvemGafanhotos != false) {
			this.tela.mostrarMensagemErro("ERRO - LANCAMENTO", "FAZENDAS PROTEGIDAS!!! ");
		} else {
			this.tela.mostrarMensagemErro("ERRO - LANCAMENTO", "Nao tem fazendas o suficiente ");
		}

	}

	public void lancarMortePrimogênitos() {
//		Custo: oferendas de fé=750 (pré-pago)
//		Função: Matar metade dos aldeoes do inimigo selecionado
//		Tempo: imediato
		if (this.vila.contAldeao >= 2 && this.vila.protecaoContraMortePrimogenito == false) {
			int aux = this.vila.contAldeao = this.vila.contAldeao / 2;
			for (int i = 0; i < aux; i++) {
				this.vila.aldeoes.get(i).matar();
			}
		} else if (this.vila.protecaoContraMortePrimogenito != false) {
			this.tela.mostrarMensagemErro("ERRO - LANCAMENTO", "ALDEOES PROTEGIDOS!!! ");
		} else {
			this.tela.mostrarMensagemErro("ERRO - LANCAMENTO", "Nao tem aldeoes o suficiente");
		}

	}

	public void lancarChuvaPedras() {
//		Custo: oferendas de fé=10.000 (pré-pago)
//		Função: Destruir metade das fazendas, metade das minas de ouro e metade da maravilha. 
//		Também matar metade dos aldeões do inimigo.
//		Tempo: imediato

		if ((this.vila.contFazenda >= 2 || this.vila.contMina >= 2 || this.vila.contAldeao >= 2)
				&& this.vila.protecaoContraChuvaPedra == false) {
			int auxFazenda = this.vila.fazendas.size() / 2;
			int auxMina = this.vila.minas.size() / 2;
			int auxAldeao = this.vila.aldeoes.size() / 2;

			// Fazenda
			for (int i = 0; i < auxFazenda; i++) {
				if (this.vila.fazendas.get(i).vivo == true) {
					this.vila.fazendas.get(i).vivo = false;
					this.tela.mostrarFazenda(this.vila.fazendas.get(i).id, "Destruida!");
				}
			}
			// Mina
			for (int i = 0; i < auxMina; i++) {
				if (this.vila.minas.get(i).vivo == true) {
					this.vila.minas.get(i).vivo = false;
					this.tela.mostrarMinaOuro(this.vila.minas.get(i).id, "Destruida!");
				}
			}
			// Aldeoes
			for (int i = 0; i < auxAldeao; i++) {
				this.vila.aldeoes.get(i).matar();
			}

			// Maravilha

		} else if (this.vila.protecaoContraChuvaPedra != false) {
			this.tela.mostrarMensagemErro("ERRO - LANCAMENTO", "VILA PROTEGIDA CONTRA CHUVA DE PEDRAS!!! ");
		} else {
			this.tela.mostrarMensagemErro("ERRO - LANCAMENTO", "O ataque nao foi efetivo!");
		}

	}

// -----------------------------------------------------------------------------------------------------------

	public synchronized void fazer(AcaoTemplo acao) {
		this.acao = acao;
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
		return "Templo Wat Arun";
	}

}
