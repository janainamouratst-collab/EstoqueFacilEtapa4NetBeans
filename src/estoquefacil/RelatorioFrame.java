package estoquefacil;

import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

public class RelatorioFrame extends JFrame {
    private JTextArea area;
    private ProdutoDAO dao;

    public RelatorioFrame() {
        dao = new ProdutoDAO();
        setTitle("Relatórios e Alertas");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        JPanel topo = new JPanel(new GridLayout(1, 3, 10, 10));
        topo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton btnTodos = new JButton("Inventário Completo");
        JButton btnBaixo = new JButton("Estoque Baixo");
        JButton btnValidade = new JButton("Validade Próxima");
        topo.add(btnTodos);
        topo.add(btnBaixo);
        topo.add(btnValidade);

        area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);

        add(topo, java.awt.BorderLayout.NORTH);
        add(scroll, java.awt.BorderLayout.CENTER);

        btnTodos.addActionListener(e -> carregarTodos());
        btnBaixo.addActionListener(e -> carregarEstoqueBaixo());
        btnValidade.addActionListener(e -> carregarValidadeProxima());
    }

    private void carregarTodos() {
        try {
            mostrarProdutos("INVENTÁRIO COMPLETO", dao.listarTodos());
        } catch (SQLException ex) {
            area.setText("Erro: " + ex.getMessage());
        }
    }

    private void carregarEstoqueBaixo() {
        try {
            mostrarProdutos("ALERTA DE ESTOQUE BAIXO", dao.listarEstoqueBaixo());
        } catch (SQLException ex) {
            area.setText("Erro: " + ex.getMessage());
        }
    }

    private void carregarValidadeProxima() {
        try {
            mostrarProdutos("PRODUTOS COM VALIDADE PRÓXIMA", dao.listarValidadeProxima());
        } catch (SQLException ex) {
            area.setText("Erro: " + ex.getMessage());
        }
    }

    private void mostrarProdutos(String titulo, List<Produto> produtos) {
        StringBuilder sb = new StringBuilder();
        sb.append(titulo).append("\n\n");
        if (produtos.isEmpty()) {
            sb.append("Nenhum registro encontrado.");
        } else {
            for (Produto p : produtos) {
                sb.append("ID: ").append(p.getId())
                  .append(" | Nome: ").append(p.getNome())
                  .append(" | Quantidade: ").append(p.getQuantidade())
                  .append(" | Validade: ").append(p.getValidade())
                  .append("\n");
            }
        }
        area.setText(sb.toString());
    }
}
