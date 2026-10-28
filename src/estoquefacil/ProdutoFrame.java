package estoquefacil;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProdutoFrame extends JFrame {
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtCodigo;
    private JTextField txtPrecoCusto;
    private JTextField txtPrecoVenda;
    private JTextField txtQuantidade;
    private JTextField txtValidade;
    private JTable tabela;
    private ProdutoDAO dao;

    public ProdutoFrame() {
        dao = new ProdutoDAO();
        setTitle("Cadastro de Produtos");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iniciarComponentes();
        carregarTabela();
    }

    private void iniciarComponentes() {
        JPanel formulario = new JPanel(new GridLayout(7, 2, 8, 8));
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtId = new JTextField();
        txtNome = new JTextField();
        txtCodigo = new JTextField();
        txtPrecoCusto = new JTextField();
        txtPrecoVenda = new JTextField();
        txtQuantidade = new JTextField();
        txtValidade = new JTextField();
        txtId.setEditable(false);

        formulario.add(new JLabel("ID:")); formulario.add(txtId);
        formulario.add(new JLabel("Nome:")); formulario.add(txtNome);
        formulario.add(new JLabel("Código de barras:")); formulario.add(txtCodigo);
        formulario.add(new JLabel("Preço de custo:")); formulario.add(txtPrecoCusto);
        formulario.add(new JLabel("Preço de venda:")); formulario.add(txtPrecoVenda);
        formulario.add(new JLabel("Quantidade:")); formulario.add(txtQuantidade);
        formulario.add(new JLabel("Validade (AAAA-MM-DD):")); formulario.add(txtValidade);

        JPanel botoes = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar");

        botoes.add(btnSalvar);
        botoes.add(btnAtualizar);
        botoes.add(btnExcluir);
        botoes.add(btnLimpar);

        tabela = new JTable();
        JScrollPane scroll = new JScrollPane(tabela);

        add(formulario, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limparCampos());
        tabela.getSelectionModel().addListSelectionListener(e -> preencherCamposDaTabela());
    }

    private Produto montarProdutoDosCampos(boolean incluirId) {
        Produto p = new Produto();
        if (incluirId && !txtId.getText().isBlank()) {
            p.setId(Integer.parseInt(txtId.getText()));
        }
        p.setNome(txtNome.getText());
        p.setCodigoBarras(txtCodigo.getText());
        p.setPrecoCusto(Double.parseDouble(txtPrecoCusto.getText()));
        p.setPrecoVenda(Double.parseDouble(txtPrecoVenda.getText()));
        p.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
        p.setValidade(txtValidade.getText());
        return p;
    }

    private void salvar() {
        try {
            dao.inserir(montarProdutoDosCampos(false));
            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso.");
            carregarTabela();
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }

    private void atualizar() {
        try {
            dao.atualizar(montarProdutoDosCampos(true));
            JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso.");
            carregarTabela();
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage());
        }
    }

    private void excluir() {
        if (txtId.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Selecione um produto na tabela.");
            return;
        }
        try {
            dao.excluir(Integer.parseInt(txtId.getText()));
            JOptionPane.showMessageDialog(this, "Produto excluído com sucesso.");
            carregarTabela();
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
        }
    }

    private void carregarTabela() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nome", "Código", "Custo", "Venda", "Quantidade", "Validade"}, 0);
        try {
            List<Produto> produtos = dao.listarTodos();
            for (Produto p : produtos) {
                model.addRow(new Object[]{p.getId(), p.getNome(), p.getCodigoBarras(), p.getPrecoCusto(), p.getPrecoVenda(), p.getQuantidade(), p.getValidade()});
            }
            tabela.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + ex.getMessage());
        }
    }

    private void preencherCamposDaTabela() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            txtId.setText(tabela.getValueAt(linha, 0).toString());
            txtNome.setText(tabela.getValueAt(linha, 1).toString());
            txtCodigo.setText(tabela.getValueAt(linha, 2).toString());
            txtPrecoCusto.setText(tabela.getValueAt(linha, 3).toString());
            txtPrecoVenda.setText(tabela.getValueAt(linha, 4).toString());
            txtQuantidade.setText(tabela.getValueAt(linha, 5).toString());
            txtValidade.setText(tabela.getValueAt(linha, 6) == null ? "" : tabela.getValueAt(linha, 6).toString());
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCodigo.setText("");
        txtPrecoCusto.setText("");
        txtPrecoVenda.setText("");
        txtQuantidade.setText("");
        txtValidade.setText("");
        tabela.clearSelection();
    }
}
