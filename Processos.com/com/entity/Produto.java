package com.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Produto {

	private long id;
	
	private String nome;
	
	private int quantidade;
	
	private int qtd_max;
	
	private int qtd_min;
	
	private double valor;
	
	private LocalDate validade;
	
	
	
	
	

	public Produto(long id, String nome, int quantidade, int qtd_max, int qtd_min, double valor, LocalDate validade) {
		this.id = id;
		this.nome = nome;
		this.quantidade = quantidade;
		this.qtd_max = qtd_max;
		this.qtd_min = qtd_min;
		this.valor = valor;
		this.validade = validade;
	}
	
	

	@Override
	public String toString() {
		return "==========="+"\nProduto"+"\nID:"+ this.id + "\nNome:" + this.nome + "\nQuantidade:" + this.quantidade + "\nQuantidade maxima:" + this.qtd_max
				+ "\nQuantidade minima:" + this.qtd_min + "\nValor:" + this.valor + "\nValidade:" + this.validade.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"\n===========";
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getQtd_max() {
		return qtd_max;
	}

	public void setQtd_max(int qtd_max) {
		this.qtd_max = qtd_max;
	}

	public int getQtd_min() {
		return qtd_min;
	}

	public void setQtd_min(int qtd_min) {
		this.qtd_min = qtd_min;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public LocalDate getValidade() {
		return validade;
	}

	public void setValidade(LocalDate validade) {
		this.validade = validade;
	}



	@Override
	public int hashCode() {
		return Objects.hash(id, nome, qtd_max, qtd_min, quantidade, validade, valor);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return id == other.id && Objects.equals(nome, other.nome) && qtd_max == other.qtd_max
				&& qtd_min == other.qtd_min && quantidade == other.quantidade
				&& Objects.equals(validade, other.validade)
				&& Double.doubleToLongBits(valor) == Double.doubleToLongBits(other.valor);
	}
	
	
	
	
	
	
}
