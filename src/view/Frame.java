package view;

import dal.ProdutoDAO;
import model.Produto;

import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JFrame {
    // Atributos e Panels
    private JLabel lblDescricao, lblQtd, lblPreco;
    private JTextField txtDescricao, txtQtd, txtPreco, txtBusca;
    private JButton btnCadastrar, btnExcluir, btnAtualizar, btnBuscar;
    private DefaultTableModel modeloTabela;
    private JTable tabelaResultados;
    private JPanel p1, p2, p3;

    // Construtor
    public Frame() {
        // Define atributos da janela
        super("Gerenciamento de Produtos");
        this.setSize(700, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Container principal
        Container c = getContentPane();
        BoxLayout box = new BoxLayout(c, BoxLayout.Y_AXIS);
        c.setLayout(box);

        // Inicializa componentes e painéis
        lblDescricao = new JLabel("Descrição:");
        txtDescricao = new JTextField(20);
        lblQtd = new JLabel("Quantidade:");
        txtQtd = new JTextField(10);
        lblPreco = new JLabel("Preço:");
        txtPreco = new JTextField(15);
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(this::eventoCadastrar);
        btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(this::eventoExcluir);
        btnAtualizar = new JButton("Atualizar");
        txtBusca = new JTextField(15);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this::eventoBuscar);
        btnAtualizar.addActionListener(this::eventoAtualizar);
        modeloTabela = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabela.addColumn("Id");
        modeloTabela.addColumn("Descrição");
        modeloTabela.addColumn("Quantidade");
        modeloTabela.addColumn("Preço");
        tabelaResultados = new JTable(modeloTabela);
        tabelaResultados.setPreferredScrollableViewportSize(new Dimension(550, 300));
        readJTable();


        tabelaResultados.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tabelaResultadosMouseClicked(evt);
            }
        });
        tabelaResultados.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                tabelaResultadosKeyReleased(evt);
            }
        });

        p1 = new JPanel(new GridBagLayout());
        p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p3 = new JPanel();

        // Adiciona componentes nos painéis
        int espHoriz = 30;
        int espVert = 5;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, espVert, espHoriz);
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridx = 0;
        gbc.gridy = 0;
        p1.add(lblDescricao, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        p1.add(lblQtd, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        p1.add(lblPreco, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        p1.add(txtDescricao, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        p1.add(txtQtd, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        p1.add(txtPreco, gbc);

        p2.add(btnCadastrar);
        p2.add(btnExcluir);
        p2.add(btnAtualizar);
        p2.add(txtBusca);
        p2.add(btnBuscar);

        p3.add(new JScrollPane(tabelaResultados));

        // Adiciona painéis no frame principal
        c.add(p1);
        c.add(p2);
        c.add(p3);
    }

    private void tabelaResultadosMouseClicked(MouseEvent evt) {
        if(tabelaResultados.getSelectedRow() != -1) {
            txtDescricao.setText(tabelaResultados.getValueAt(tabelaResultados.getSelectedRow(), 1).toString());
            txtQtd.setText(tabelaResultados.getValueAt(tabelaResultados.getSelectedRow(), 2).toString());
            txtPreco.setText(tabelaResultados.getValueAt(tabelaResultados.getSelectedRow(), 3).toString());
        }
    }

    private void tabelaResultadosKeyReleased(KeyEvent evt) {
        if(tabelaResultados.getSelectedRow() != -1) {
            txtDescricao.setText(tabelaResultados.getValueAt(tabelaResultados.getSelectedRow(), 1).toString());
            txtQtd.setText(tabelaResultados.getValueAt(tabelaResultados.getSelectedRow(), 2).toString());
            txtPreco.setText(tabelaResultados.getValueAt(tabelaResultados.getSelectedRow(), 3).toString());
        }
    }

    private void eventoCadastrar(ActionEvent actionEvent) {
        Produto p = new Produto();
        ProdutoDAO dao = new ProdutoDAO();

        p.setDescricao(txtDescricao.getText());
        p.setQtd(Integer.parseInt(txtQtd.getText()));
        p.setPreco(Double.parseDouble(txtPreco.getText()));

        dao.create(p);

        txtDescricao.setText("");
        txtQtd.setText("");
        txtPreco.setText("");

        readJTable();
    }

    private void eventoExcluir(ActionEvent actionEvent) {
        if(tabelaResultados.getSelectedRow() != -1) {
            Produto p = new Produto();
            ProdutoDAO dao = new ProdutoDAO();

            p.setId((int)tabelaResultados.getValueAt(tabelaResultados.getSelectedRow(), 0));

            dao.delete(p);

            txtDescricao.setText("");
            txtQtd.setText("");
            txtPreco.setText("");

            readJTable();
        }
    }

    private void eventoAtualizar(ActionEvent actionEvent) {
        if(tabelaResultados.getSelectedRow() != -1) {
            Produto p = new Produto();
            ProdutoDAO dao = new ProdutoDAO();

            p.setDescricao(txtDescricao.getText());
            p.setQtd(Integer.parseInt(txtQtd.getText()));
            p.setPreco(Double.parseDouble(txtPreco.getText()));
            p.setId((int)tabelaResultados.getValueAt(tabelaResultados.getSelectedRow(), 0));

            dao.update(p);

            txtDescricao.setText("");
            txtQtd.setText("");
            txtPreco.setText("");

            readJTable();
        }
    }

    private void eventoBuscar(ActionEvent actionEvent) {
        readJTableDesc(txtBusca.getText());
    }

    private void readJTable() {
        DefaultTableModel modelo = (DefaultTableModel) tabelaResultados.getModel();
        modelo.setNumRows(0);

        ProdutoDAO pdao = new ProdutoDAO();

        for(Produto p: pdao.read()) {
            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getDescricao(),
                    p.getQtd(),
                    p.getPreco()
            });
        }
    }

    private void readJTableDesc(String desc) {
        DefaultTableModel modelo = (DefaultTableModel) tabelaResultados.getModel();
        modelo.setNumRows(0);

        ProdutoDAO pdao = new ProdutoDAO();

        for(Produto p: pdao.readDesc(desc)) {
            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getDescricao(),
                    p.getQtd(),
                    p.getPreco()
            });
        }
    }

    public static void main(String[] args) {
        Frame frame = new Frame();
        frame.setVisible(true);
    }
}
