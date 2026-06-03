package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.entity.Produto;

public class Consulta_imp implements Consulta {

	private Connection connection;
	
	public Consulta_imp(Connection connection) {
		this.connection = connection;
	}
	
	
	private static List<Produto>prod = new ArrayList<>();
	@Override
	public void add(Produto produto) {
		String sql = "INSERT into produto (nome,quantidade,qtd_max,qtd_min,valor,validade) VALUES(?,?,?,?,?,?)";
		
		try { PreparedStatement stmt = connection.prepareStatement(sql);
		
		stmt.setString(1, produto.getNome());
		stmt.setInt(2, produto.getQuantidade());
		stmt.setInt(3, produto.getQtd_max());
		stmt.setInt(4, produto.getQtd_min());
		stmt.setDouble(5, produto.getValor());
		stmt.setDate(6, java.sql.Date.valueOf(produto.getValidade()));
		
		stmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void remove(long id) {
	String sql = "DELETE FROM produto WHERE id =?";
	
	try {PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setLong(1, id);
		stmt.executeUpdate();
		
	}catch(SQLException e) {
		e.printStackTrace();
	}
		
	}

	@Override
	public void atualizar(Produto produto) {
		String sql = "UPDATE produto SET nome = ?, valor = ? WHERE id =?";
		
		try {PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, produto.getNome());
			stmt.setDouble(2, produto.getValor());
			stmt.setLong(3, produto.getId());
			stmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	@Override
	public List<Produto> consultas() {
		String sql = "SELECT * FROM produto";
		List<Produto> prod = new ArrayList<>();
		try { PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Produto produto = new Produto(rs.getLong("id"), rs.getString("nome"),rs.getInt("quantidade"),rs.getInt("qtd_max") ,rs.getInt("qtd_min"),rs.getDouble("valor"),rs.getDate("validade").toLocalDate());
			prod.add(produto);
		}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return prod;
	}

	@Override
	public Produto procuraid(Long id) {
	String sql = "SELECT * FROM produto WHERE id=?";
	Produto produto = null;
	try {PreparedStatement stmt = connection.prepareStatement(sql);
	stmt.setLong(1, id);
	
	ResultSet rs = stmt.executeQuery();
	while(rs.next()) {
		produto = new Produto(rs.getLong("id"), rs.getString("nome") ,rs.getInt("quantidade"), rs.getInt("qtd_max"), rs.getInt("qtd_min"), rs.getDouble("valor"), rs.getDate("validade").toLocalDate());
	}
		
	}catch(SQLException e) {
		e.printStackTrace();
	}
		return produto;
	}

	@Override
	public void atualizaEstoque(Produto produto) {
		String sql = "UPDATE produto SET quantidade = ? WHERE id=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1,produto.getQuantidade() );
			stmt.setLong(2, produto.getId());
			stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

}
