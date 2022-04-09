package tela;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class MinaDeOuro {

	public Principal tela;
	public Vila vila;
	public int novoOuro = 5;
	public Semaphore semaforo;
	public List<Integer> ids;
	public int limite;
	public int level;
	public boolean vivo;
	public int id;

	public MinaDeOuro(int id, Principal tela, Vila vila) {
		this.vivo = true;
		this.id = id;
		this.tela = tela;
		this.vila = vila;
		this.limite = vila.getLimiteAldMina();
		this.level = 0;
		this.ids = new ArrayList<Integer>();
		this.semaforo = new Semaphore(limite);
	}

	public int produzirOuro(Vila vila, int id, int novoOuro) {
		this.viewIds(id);
		this.novoOuro = novoOuro;
		synchronized (this) {
			return vila.qtdOuro = vila.qtdOuro + this.novoOuro;
		}
	}

	public void viewIds(int id) {
		try {
			semaforo.acquire();
			ids.add(id);
			tela.mostrarMinaOuro(this.id, ids.toString());
			Thread.sleep(5 * vila.HORA);
			ids.remove(0);
			semaforo.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}