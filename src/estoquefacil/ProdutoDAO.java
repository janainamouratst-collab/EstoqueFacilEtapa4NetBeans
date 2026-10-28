package estoquefacil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void inserir(Produto produto) throws SQLException {
        String sql = "INSERT INTO produtos (nome_produto, codigo_barras, preco_custo, preco_venda, quantidade_estoque, data_validade, id_fornecedor) VALUES (?, ?, ?, ?, ?, ?, 1)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getCodigoBarras());
            ps.setDouble(3, produto.getPrecoCusto());
            ps.setDouble(4, produto.getPrecoVenda());
            ps.setInt(5, produto.getQuantidade());
            ps.setString(6, produto.getValidade());
            ps.executeUpdate();
        }
    }

    public void atualizar(Produto produto) throws SQLException {
        String sql = "UPDATE produtos SET nome_produto=?, codigo_barras=?, preco_custo=?, preco_venda=?, quantidade_estoque=?, data_validade=? WHERE id_produto=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getCodigoBarras());
            ps.setDouble(3, produto.getPrecoCusto());
            ps.setDouble(4, produto.getPrecoVenda());
            ps.setInt(5, produto.getQuantidade());
            ps.setString(6, produto.getValidade());
            ps.setInt(7, produto.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id_produto=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Produto> listarTodos() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id_produto, nome_produto, codigo_barras, preco_custo, preco_venda, quantidade_estoque, data_validade FROM produtos ORDER BY id_produto";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                produtos.add(new Produto(
                        rs.getInt("id_produto"),
                        rs.getString("nome_produto"),
                        rs.getString("codigo_barras"),
                        rs.getDouble("preco_custo"),
                        rs.getDouble("preco_venda"),
                        rs.getInt("quantidade_estoque"),
                        rs.getString("data_validade")
                ));
            }
        }
        return produtos;
    }

    public Produto buscarPorId(int id) throws SQLException {
        String sql = "SELECT id_produto, nome_produto, codigo_barras, preco_custo, preco_venda, quantidade_estoque, data_validade FROM produtos WHERE id_produto=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                            rs.getInt("id_produto"),
                            rs.getString("nome_produto"),
                            rs.getString("codigo_barras"),
                            rs.getDouble("preco_custo"),
                            rs.getDouble("preco_venda"),
                            rs.getInt("quantidade_estoque"),
                            rs.getString("data_validade")
                    );
                }
            }
        }
        return null;
    }

    public List<Produto> listarEstoqueBaixo() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id_produto, nome_produto, codigo_barras, preco_custo, preco_venda, quantidade_estoque, data_validade FROM produtos WHERE quantidade_estoque <= 10 ORDER BY quantidade_estoque";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                produtos.add(new Produto(
                        rs.getInt("id_produto"),
                        rs.getString("nome_produto"),
                        rs.getString("codigo_barras"),
                        rs.getDouble("preco_custo"),
                        rs.getDouble("preco_venda"),
                        rs.getInt("quantidade_estoque"),
                        rs.getString("data_validade")
                ));
            }
        }
        return produtos;
    }

    public List<Produto> listarValidadeProxima() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id_produto, nome_produto, codigo_barras, preco_custo, preco_venda, quantidade_estoque, data_validade FROM produtos WHERE data_validade IS NOT NULL AND data_validade <= DATE_ADD(CURDATE(), INTERVAL 30 DAY) ORDER BY data_validade";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                produtos.add(new Produto(
                        rs.getInt("id_produto"),
                        rs.getString("nome_produto"),
                        rs.getString("codigo_barras"),
                        rs.getDouble("preco_custo"),
                        rs.getDouble("preco_venda"),
                        rs.getInt("quantidade_estoque"),
                        rs.getString("data_validade")
                ));
            }
        }
        return produtos;
    }
}
