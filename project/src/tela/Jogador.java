package tela;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Jogador implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nome;
	private String civilizacao;
	private String ip;
	public Principal tela;
	private String situacao;
	private int id;
	public Vila vila;
	transient ObjectOutputStream saida;

	public Jogador(Principal tela) {
		this.tela = tela;
	}

	public Jogador(String nome, String civilizacao, String situacao) {
		setNome(nome);
		setCivilizacao(civilizacao);
		this.situacao = situacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCivilizacao() {
		return civilizacao;
	}

	public void setCivilizacao(String civilizacao) {
		this.civilizacao = civilizacao;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
