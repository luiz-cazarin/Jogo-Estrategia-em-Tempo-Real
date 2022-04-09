package tela;

import java.util.ArrayList;

public class Vila {

	public final int HORA = 500;

	public int qtdOuro = 100; // 100
	public int qtdComida = 150; // 150
	public int qtdoferendasFe = 0; // 0

	public int qtdtijolo = 0;

	public int contPrefeitura = 0;
	public int contAldeao = 0;
	public int contFazenda = 0;
	public int contMina = 0;
	public int contTemplo = 0;
	public int contMaravilha = 0;

	public int contJogador = 0;

	private int limiteAldFazenda = 5;
	private int limiteAldMina = 5;

	public boolean lancNuvemGarfanhoto;
	public boolean lancMortePrimogenito;
	public boolean lancChuvaPedra;

	public boolean protecaoContraNuvemGafanhotos;
	public boolean protecaoContraMortePrimogenito;
	public boolean protecaoContraChuvaPedra;

	public Principal tela;
	public Aldeao aldeao;
	public Prefeitura prefeitura;
	public Templo templo;
	public Fazenda fazenda;
	public MinaDeOuro mina;
	public Maravilha maravilha;
	public Jogador jogador;
	public ArrayList<MinaDeOuro> minas = new ArrayList<MinaDeOuro>();
	public ArrayList<Fazenda> fazendas = new ArrayList<Fazenda>();
	public ArrayList<Aldeao> aldeoes = new ArrayList<Aldeao>();

	public Vila(Principal tela) {
		this.tela = tela;
	}

	public Vila(ArrayList<MinaDeOuro> minas, ArrayList<Fazenda> fazendas, ArrayList<Aldeao> aldeoes,
			Maravilha maravilha, Templo templo, Principal tela) {
		this.aldeoes = aldeoes;
		this.fazendas = fazendas;
		this.minas = minas;
		this.templo = templo;
		this.maravilha = maravilha;
		this.tela = tela;

	}

	public int getLimiteAldFazenda() {
		return limiteAldFazenda;
	}

	public int getLimiteAldMina() {
		return limiteAldMina;
	}

	public void setLimiteAldFazenda(int limiteAldFazenda) {
		this.limiteAldFazenda = limiteAldFazenda;
	}

	public void setLimiteAldMina(int limiteAldMina) {
		this.limiteAldMina = limiteAldMina;
	}

	@Override
	public String toString() {
		return "";
	}

}
