package dal;

import connection.ConnectionFactory;
import model.Produto;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // CRUD
    public void create(Produto p) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO produto (descricao, qtd, preco) VALUES (?, ?, ?)");
            stmt.setString(1, p.getDescricao());
            stmt.setInt(2, p.getQtd());
            stmt.setDouble(3, p.getPreco());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salvo com sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Produto> read() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Produto> listaProdutos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM produto");
            rs = stmt.executeQuery();

            while(rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setDescricao(rs.getString("descricao"));
                p.setQtd(rs.getInt("qtd"));
                p.setPreco(rs.getDouble("preco"));

                listaProdutos.add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar produtos: " + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listaProdutos;
    }

    public List<Produto> readDesc(String desc) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Produto> listaProdutos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM produto WHERE descricao LIKE ?");
            stmt.setString(1, "%" + desc + "%");
            rs = stmt.executeQuery();

            while(rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setDescricao(rs.getString("descricao"));
                p.setQtd(rs.getInt("qtd"));
                p.setPreco(rs.getDouble("preco"));

                listaProdutos.add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar produtos: " + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listaProdutos;
    }

    public void update(Produto p) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE produto SET descricao = ?, qtd = ?, preco = ? WHERE id = ?");
            stmt.setString(1, p.getDescricao());
            stmt.setInt(2, p.getQtd());
            stmt.setDouble(3, p.getPreco());
            stmt.setInt(4, p.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(Produto p) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM produto WHERE id = ?");
            stmt.setInt(1, p.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Excluído com sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
