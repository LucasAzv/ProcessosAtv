package com.dao;

import java.util.List;

import com.entity.Produto;

public interface Consulta {

	void add (Produto produto);
	
	void remove(long id);
	
	void atualizar(Produto produto);
	
	Produto procuraid(Long id);
	
	void atualizaEstoque(Produto produto);
	
	List<Produto> consultas();
	
}
