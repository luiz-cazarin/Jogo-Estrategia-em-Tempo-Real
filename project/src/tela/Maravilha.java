package tela;

public class Maravilha {

	// Maravilha
	// Custo: 100.000 tijolos (pago tijolo a tijolo). Cada tijolo custa comida=1 +
	// ouro=1
	// Função: Ganhar o jogo
	// Necessidade de construtores: ilimitados aldeões construtores simultâneos
	// Tempo de construção: Indefinido
	// Produção por construtor: 1 tijolo a cada 10 horas

	public Principal tela;
	public Vila vila;
	public int id;

	public Maravilha(Vila vila, Principal tela) {
		this.vila = vila;
		this.tela = tela;
	}

	public void inserirTijolo() {
		synchronized (this) {
			this.vila.qtdtijolo = this.vila.qtdtijolo + 1; // 1
			System.out.println(this.vila.qtdtijolo);
			this.tela.mostrarMaravilha(this.vila.qtdtijolo);

		}
	}

}
