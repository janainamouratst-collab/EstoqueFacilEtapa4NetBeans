package estoquefacil;

import java.awt.GridLayout;
import javax.swing.*;

public class DashboardFrame extends JFrame {
    private final int idUsuario;
    private final String nomeUsuario;
    private final String perfil;

    public DashboardFrame(int idUsuario, String nomeUsuario, String perfil) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.perfil = perfil;

        setTitle("Estoque Fácil - Dashboard");
        setSize(500, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        JPanel painel = new JPanel(new GridLayout(5, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Usuário: " + nomeUsuario + " | Perfil: " + perfil, SwingConstants.CENTER);
        JButton btnProdutos = new JButton("Cadastro de Produtos");
        JButton btnMovimentacao = new JButton("Movimentação de Estoque");
        JButton btnRelatorios = new JButton("Relatórios e Alertas");
        JButton btnSair = new JButton("Sair");

        painel.add(titulo);
        painel.add(btnProdutos);
        painel.add(btnMovimentacao);
        painel.add(btnRelatorios);
        painel.add(btnSair);

        add(painel);

        btnProdutos.addActionListener(e -> new ProdutoFrame().setVisible(true));
        btnMovimentacao.addActionListener(e -> new MovimentacaoFrame(idUsuario).setVisible(true));
        btnRelatorios.addActionListener(e -> new RelatorioFrame().setVisible(true));
        btnSair.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }
}
