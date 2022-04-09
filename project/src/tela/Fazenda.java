package tela;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Fazenda {

	public Principal tela;
	public Vila vila;
	public int novaComida = 10;
	public Semaphore semaforo;
	public List<Integer> ids;
	public int limite;
	public int level;
	public int id;

	public boolean vivo = true;

	public Fazenda(int id, Principal tela, Vila vila) {
		this.vivo = true;
		this.id = id;
		this.tela = tela;
		this.vila = vila;
		this.limite = vila.getLimiteAldFazenda();
		this.level = 0;
		this.ids = new ArrayList<Integer>();
		this.semaforo = new Semaphore(limite);
	}

	public int produzirComida(Vila vila, int id, int novaComida) {
		this.viewIds(id);
		this.novaComida = novaComida;
		synchronized (this) {
			return vila.qtdComida = vila.qtdComida + novaComida;
		}
	}

	public void viewIds(int id) {
		try {
			semaforo.acquire();
			ids.add(id);
			tela.mostrarFazenda(this.id, ids.toString());
			Thread.sleep(3 * vila.HORA);
			ids.remove(0);
			semaforo.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String toString() {
		return "FAZENDA: " + id;
	}

}
