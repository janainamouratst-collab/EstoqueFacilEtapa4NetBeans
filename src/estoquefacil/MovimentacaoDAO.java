package estoquefacil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovimentacaoDAO {

    public void registrarMovimentacao(int idProduto, String tipo, int quantidade, int idUsuario) throws SQLException {
        String select = "SELECT quantidade_estoque FROM produtos WHERE id_produto=?";
        String update = "UPDATE produtos SET quantidade_estoque=? WHERE id_produto=?";
        String insert = "INSERT INTO movimentacoes_estoque (tipo_movimentacao, quantidade, data_movimentacao, id_produto, id_usuario) VALUES (?, ?, CURDATE(), ?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement psSelect = con.prepareStatement(select)) {
                psSelect.setInt(1, idProduto);
                ResultSet rs = psSelect.executeQuery();
                if (!rs.next()) {
                    throw new SQLException("Produto não encontrado.");
                }
                int estoqueAtual = rs.getInt("quantidade_estoque");
                int novoEstoque;

                if ("Saída".equalsIgnoreCase(tipo)) {
                    if (quantidade > estoqueAtual) {
                        throw new SQLException("Estoque insuficiente para realizar a saída.");
                    }
                    novoEstoque = estoqueAtual - quantidade;
                } else {
                    novoEstoque = estoqueAtual + quantidade;
                }

                try (PreparedStatement psUpdate = con.prepareStatement(update)) {
                    psUpdate.setInt(1, novoEstoque);
                    psUpdate.setInt(2, idProduto);
                    psUpdate.executeUpdate();
                }

                try (PreparedStatement psInsert = con.prepareStatement(insert)) {
                    psInsert.setString(1, tipo);
                    psInsert.setInt(2, quantidade);
                    psInsert.setInt(3, idProduto);
                    psInsert.setInt(4, idUsuario);
                    psInsert.executeUpdate();
                }

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }
}
