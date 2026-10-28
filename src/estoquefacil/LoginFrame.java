package estoquefacil;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtSenha;

    public LoginFrame() {
        setTitle("Estoque Fácil - Login");
        setSize(420, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtEmail = new JTextField();
        txtSenha = new JPasswordField();
        JButton btnEntrar = new JButton("Entrar");
        JButton btnSair = new JButton("Sair");

        painel.add(new JLabel("E-mail:"));
        painel.add(txtEmail);
        painel.add(new JLabel("Senha:"));
        painel.add(txtSenha);
        painel.add(btnEntrar);
        painel.add(btnSair);

        add(new JLabel("Acesso ao sistema Estoque Fácil", SwingConstants.CENTER), BorderLayout.NORTH);
        add(painel, BorderLayout.CENTER);

        btnEntrar.addActionListener(e -> autenticar());
        btnSair.addActionListener(e -> System.exit(0));
    }

    private void autenticar() {
        String sql = "SELECT id_usuario, nome, perfil FROM usuarios WHERE email=? AND senha=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, txtEmail.getText().trim());
            ps.setString(2, new String(txtSenha.getPassword()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String nome = rs.getString("nome");
                String perfil = rs.getString("perfil");
                JOptionPane.showMessageDialog(this, "Bem-vindo(a), " + nome + "!");
                new DashboardFrame(idUsuario, nome, perfil).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar banco: " + ex.getMessage());
        }
    }
}
