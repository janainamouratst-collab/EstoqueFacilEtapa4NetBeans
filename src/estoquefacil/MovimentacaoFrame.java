package estoquefacil;

import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

public class MovimentacaoFrame extends JFrame {
    private JComboBox<String> cbProduto;
    private JComboBox<String> cbTipo;
    private JTextField txtQuantidade;
    private final int idUsuario;
    private final ProdutoDAO produtoDAO;
    private final MovimentacaoDAO movimentacaoDAO;
    private java.util.Map<String, Integer> mapaProdutos;

    public MovimentacaoFrame(int idUsuario) {
        this.idUsuario = idUsuario;
        this.produtoDAO = new ProdutoDAO();
        this.movimentacaoDAO = new MovimentacaoDAO();
        setTitle("Movimentação de Estoque");
        setSize(450, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iniciarComponentes();
        carregarProdutos();
    }

    private void iniciarComponentes() {
        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        cbProduto = new JComboBox<>();
        cbTipo = new JComboBox<>(new String[]{"Entrada", "Saída"});
        txtQuantidade = new JTextField();
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnFechar = new JButton("Fechar");

        painel.add(new JLabel("Produto:"));
        painel.add(cbProduto);
        painel.add(new JLabel("Tipo de movimentação:"));
        painel.add(cbTipo);
        painel.add(new JLabel("Quantidade:"));
        painel.add(txtQuantidade);
        painel.add(btnRegistrar);
        painel.add(btnFechar);

        add(painel);

        btnRegistrar.addActionListener(e -> registrar());
        btnFechar.addActionListener(e -> dispose());
    }

    private void carregarProdutos() {
        mapaProdutos = new java.util.HashMap<>();
        cbProduto.removeAllItems();
        try {
            List<Produto> produtos = produtoDAO.listarTodos();
            for (Produto p : produtos) {
                String chave = p.getId() + " - " + p.getNome();
                mapaProdutos.put(chave, p.getId());
                cbProduto.addItem(chave);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + ex.getMessage());
        }
    }

    private void registrar() {
        try {
            String selecionado = (String) cbProduto.getSelectedItem();
            if (selecionado == null) {
                JOptionPane.showMessageDialog(this, "Cadastre produtos antes de movimentar o estoque.");
                return;
            }
            int idProduto = mapaProdutos.get(selecionado);
            String tipo = (String) cbTipo.getSelectedItem();
            int quantidade = Integer.parseInt(txtQuantidade.getText());
            movimentacaoDAO.registrarMovimentacao(idProduto, tipo, quantidade, idUsuario);
            JOptionPane.showMessageDialog(this, "Movimentação registrada com sucesso.");
            txtQuantidade.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar movimentação: " + ex.getMessage());
        }
    }
}
