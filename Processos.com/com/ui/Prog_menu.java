package com.ui;

import java.awt.Font;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.dao.Consulta_imp;
import com.entity.Produto;

import bd_conn.Connect_bd;

public class Prog_menu extends JFrame {

private Consulta_imp consulta;

public Prog_menu() {

    try {
        Connection con = Connect_bd.getConnection();
        consulta = new Consulta_imp(con);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
                "Erro ao conectar ao banco.");
        System.exit(0);
    }

    setTitle("Controle de Estoque");
    setSize(800, 550);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(null);

    JLabel titulo = new JLabel("CONTROLE DE ESTOQUE");
    titulo.setFont(new Font("Arial", Font.BOLD, 20));
    titulo.setBounds(250, 30, 300, 30);

    JButton btnCadastrar = new JButton("Cadastrar Produto");
    JButton btnBuscar = new JButton("Buscar Produto");
    JButton btnAtualizar = new JButton("Atualizar Produto");
    JButton btnEstoque = new JButton("Alterar Estoque");
    JButton btnListar = new JButton("Listar Produtos");
    JButton btnRemover = new JButton("Remover Produto");
    JButton btnSair = new JButton("Sair");

    btnCadastrar.setBounds(280, 90, 220, 40);
    btnBuscar.setBounds(280, 140, 220, 40);
    btnAtualizar.setBounds(280, 190, 220, 40);
    btnEstoque.setBounds(280, 240, 220, 40);
    btnListar.setBounds(280, 290, 220, 40);
    btnRemover.setBounds(280, 340, 220, 40);
    btnSair.setBounds(280, 390, 220, 40);

    add(titulo);
    add(btnCadastrar);
    add(btnBuscar);
    add(btnAtualizar);
    add(btnEstoque);
    add(btnListar);
    add(btnRemover);
    add(btnSair);

    btnCadastrar.addActionListener(e -> abrirCadastro());
    btnBuscar.addActionListener(e -> abrirBusca());
    btnAtualizar.addActionListener(e -> abrirAtualizacao());
    btnEstoque.addActionListener(e -> abrirEstoque());
    btnRemover.addActionListener(e -> abrirRemocao());
    btnListar.addActionListener(e -> abrirListagem());

    btnSair.addActionListener(e -> {

        int op = JOptionPane.showConfirmDialog(
                this,
                "Deseja sair do sistema?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION);

        if (op == JOptionPane.YES_OPTION) {
            dispose();
        }
    });
    for (Produto p : consulta.consultas()) {
        verificarEstoque(p);
    }
    
    setVisible(true);
}

private void abrirCadastro() {

    JFrame tela = new JFrame("Cadastro de Produto");

    tela.setSize(450, 450);
    tela.setLocationRelativeTo(this);
    tela.setLayout(null);

    JTextField txtNome = new JTextField();
    JTextField txtQuantidade = new JTextField();
    JTextField txtMax = new JTextField();
    JTextField txtMin = new JTextField();
    JTextField txtValor = new JTextField();
    JTextField txtValidade = new JTextField();

    tela.add(new JLabel("Nome")).setBounds(30, 30, 100, 25);
    tela.add(new JLabel("Quantidade")).setBounds(30, 70, 100, 25);
    tela.add(new JLabel("Qtd Máx")).setBounds(30, 110, 100, 25);
    tela.add(new JLabel("Qtd Mín")).setBounds(30, 150, 100, 25);
    tela.add(new JLabel("Valor")).setBounds(30, 190, 100, 25);
    tela.add(new JLabel("Validade")).setBounds(30, 230, 100, 25);

    txtNome.setBounds(150, 30, 200, 25);
    txtQuantidade.setBounds(150, 70, 200, 25);
    txtMax.setBounds(150, 110, 200, 25);
    txtMin.setBounds(150, 150, 200, 25);
    txtValor.setBounds(150, 190, 200, 25);
    txtValidade.setBounds(150, 230, 200, 25);

    tela.add(txtNome);
    tela.add(txtQuantidade);
    tela.add(txtMax);
    tela.add(txtMin);
    tela.add(txtValor);
    tela.add(txtValidade);

    JButton salvar = new JButton("Salvar");
    salvar.setBounds(140, 300, 150, 40);

    tela.add(salvar);

    salvar.addActionListener(e -> {

        try {

            Produto p = new Produto(
                    0,
                    txtNome.getText(),
                    Integer.parseInt(txtQuantidade.getText()),
                    Integer.parseInt(txtMax.getText()),
                    Integer.parseInt(txtMin.getText()),
                    Double.parseDouble(txtValor.getText()),
                    LocalDate.parse(
                            txtValidade.getText(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            );

            consulta.add(p);

            verificarEstoque(p);

            JOptionPane.showMessageDialog(
                    tela,
                    "Produto cadastrado com sucesso!");

            tela.dispose();
        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    tela,
                    ex.getMessage());
        }
    });

    tela.setVisible(true);
}

private void abrirBusca() {

    String texto = JOptionPane.showInputDialog(
            this,
            "Informe o ID do produto:");

    if (texto == null)
        return;

    try {

        Produto p = consulta.procuraid(
                Long.parseLong(texto));

        if (p == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Produto não encontrado.");

            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "ID: " + p.getId()
                        + "\nNome: " + p.getNome()
                        + "\nQuantidade: " + p.getQuantidade()
                        + "\nQtd Máx: " + p.getQtd_max()
                        + "\nQtd Mín: " + p.getQtd_min()
                        + "\nValor: R$ " + p.getValor()
                        + "\nValidade: " + p.getValidade());

    } catch (Exception ex) {

        JOptionPane.showMessageDialog(
                this,
                ex.getMessage());
    }
}

private void abrirAtualizacao() {

    String texto = JOptionPane.showInputDialog(
            this,
            "Informe o ID do produto:");

    if (texto == null)
        return;

    Produto p = consulta.procuraid(
            Long.parseLong(texto));

    if (p == null) {

        JOptionPane.showMessageDialog(
                this,
                "Produto não encontrado.");

        return;
    }

    JFrame tela = new JFrame("Atualizar Produto");

    tela.setSize(500, 300);
    tela.setLocationRelativeTo(this);
    tela.setLayout(null);

    JLabel info = new JLabel(
            "Produto Atual: "
                    + p.getNome()
                    + " | Valor Atual: R$ "
                    + p.getValor());

    info.setBounds(20, 20, 450, 25);

    JTextField txtNome =
            new JTextField(p.getNome());

    JTextField txtValor =
            new JTextField(
                    String.valueOf(
                            p.getValor()));

    txtNome.setBounds(150, 80, 200, 25);
    txtValor.setBounds(150, 120, 200, 25);

    tela.add(info);

    tela.add(new JLabel("Novo Nome"))
            .setBounds(30, 80, 100, 25);

    tela.add(new JLabel("Novo Valor"))
            .setBounds(30, 120, 100, 25);

    tela.add(txtNome);
    tela.add(txtValor);

    JButton atualizar =
            new JButton("Atualizar");

    atualizar.setBounds(
            170,
            180,
            120,
            35);

    tela.add(atualizar);

    atualizar.addActionListener(e -> {

        Produto novo =
                new Produto(
                        p.getId(),
                        txtNome.getText(),
                        p.getQuantidade(),
                        p.getQtd_max(),
                        p.getQtd_min(),
                        Double.parseDouble(
                                txtValor.getText()),
                        p.getValidade());

        consulta.atualizar(novo);

        JOptionPane.showMessageDialog(
                tela,
                "Produto atualizado!");

        tela.dispose();
    });

    tela.setVisible(true);
}
private void verificarEstoque(Produto p) {

    if (p == null) {
        return;
    }

    if (p.getQuantidade() < 0) {

        JOptionPane.showMessageDialog(
                this,
                "Quantidade inválida.",
                "Erro",
                JOptionPane.ERROR_MESSAGE);

        return;
    }

    if (p.getQuantidade() < p.getQtd_min()) {

        JOptionPane.showMessageDialog(
                this,
                "⚠ ESTOQUE BAIXO\n\n"
                        + "Produto: " + p.getNome()
                        + "\nQuantidade Atual: " + p.getQuantidade()
                        + "\nQuantidade Mínima: " + p.getQtd_min(),
                "Alerta de Estoque",
                JOptionPane.WARNING_MESSAGE);

    } else if (p.getQuantidade() >= p.getQtd_max()) {

        JOptionPane.showMessageDialog(
                this,
                "⚠ ESTOQUE NO LIMITE MÁXIMO\n\n"
                        + "Produto: " + p.getNome()
                        + "\nQuantidade Atual: " + p.getQuantidade()
                        + "\nQuantidade Máxima: " + p.getQtd_max(),
                "Alerta de Estoque",
                JOptionPane.WARNING_MESSAGE);
    }
}

private void abrirEstoque() {

    String texto = JOptionPane.showInputDialog(
            this,
            "Informe o ID do produto:");

    if (texto == null)
        return;

    Produto p =
            consulta.procuraid(
                    Long.parseLong(texto));

    if (p == null) {

        JOptionPane.showMessageDialog(
                this,
                "Produto não encontrado.");

        return;
    }

    String novoEstoque =
            JOptionPane.showInputDialog(
                    this,
                    "Produto: "
                            + p.getNome()
                            + "\nEstoque Atual: "
                            + p.getQuantidade()
                            + "\n\nNovo estoque:");

    if (novoEstoque == null)
        return;

    int quantidadeNova =
            Integer.parseInt(novoEstoque);

    if (quantidadeNova < 0) {

        JOptionPane.showMessageDialog(
                this,
                "A quantidade não pode ser negativa.");

        return;
    }

    Produto novo =
            new Produto(
                    p.getId(),
                    p.getNome(),
                    quantidadeNova,
                    p.getQtd_max(),
                    p.getQtd_min(),
                    p.getValor(),
                    p.getValidade());

    consulta.atualizaEstoque(novo);

    verificarEstoque(novo);

    JOptionPane.showMessageDialog(
            this,
            "Estoque atualizado.");
}

private void abrirRemocao() {

    String texto =
            JOptionPane.showInputDialog(
                    this,
                    "Informe o ID do produto:");

    if (texto == null)
        return;

    Produto p =
            consulta.procuraid(
                    Long.parseLong(texto));

    if (p == null) {

        JOptionPane.showMessageDialog(
                this,
                "Produto não encontrado.");

        return;
    }

    int op =
            JOptionPane.showConfirmDialog(
                    this,
                    "Produto: "
                            + p.getNome()
                            + "\nQuantidade: "
                            + p.getQuantidade()
                            + "\n\nDeseja remover?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION);

    if (op ==
            JOptionPane.YES_OPTION) {

        consulta.remove(
                p.getId());

        JOptionPane.showMessageDialog(
                this,
                "Produto removido.");
    }
}

private void abrirListagem() {

    JFrame tela =
            new JFrame(
                    "Produtos Cadastrados");

    tela.setSize(900, 500);
    tela.setLocationRelativeTo(this);

    DefaultTableModel model =
            new DefaultTableModel();

    model.addColumn("ID");
    model.addColumn("Nome");
    model.addColumn("Quantidade");
    model.addColumn("Qtd Máx");
    model.addColumn("Qtd Mín");
    model.addColumn("Valor");
    model.addColumn("Validade");
    model.addColumn("Status");

    JTable tabela =
            new JTable(model);

    for (Produto p :
            consulta.consultas()) {

    	String status;

    	if (p.getQuantidade() < p.getQtd_min()) {

    	    status = "ESTOQUE BAIXO";

    	} else if (p.getQuantidade() >= p.getQtd_max()) {

    	    status = "ESTOQUE CHEIO";

    	} else {

    	    status = "NORMAL";
    	}

    	model.addRow(new Object[]{
    	        p.getId(),
    	        p.getNome(),
    	        p.getQuantidade(),
    	        p.getQtd_max(),
    	        p.getQtd_min(),
    	        p.getValor(),
    	        p.getValidade(),
    	        status
    	});
    }

    tela.add(
            new JScrollPane(
                    tabela));

    tela.setVisible(true);
}

public static void main(String[] args) {
    new Prog_menu();
}


}
