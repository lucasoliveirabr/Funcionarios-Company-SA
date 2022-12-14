package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import sistema.BancoDeDados;
import sistema.Navegador;
import sistema.entidades.Cargo;
import sistema.entidades.Funcionario;

public class FuncionariosInserir extends JPanel {

    JLabel labelTitulo, labelNome, labelSobrenome, labelDataNascimento, labelEmail, labelCargo, labelSalario;
    JTextField campoNome, campoSobrenome, campoEmail;
    JFormattedTextField campoDataNascimento, campoSalario;
    JComboBox<Cargo> comboboxCargo;
    JButton botaoGravar;
    MaskFormatter mkSalario;

    public FuncionariosInserir() {
        criarComponentes();
        criarEventos();
        Navegador.habilitarMenu();
    }

    private void criarComponentes() {
        // Sem gerenciador de layout.
        setLayout(null);

        // Nas linhas abaixo instanciamos os componentes da tela.
        labelTitulo = new JLabel("Cadastro de Funcionário", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelNome = new JLabel("Nome:", JLabel.LEFT);
        campoNome = new JTextField();
        labelSobrenome = new JLabel("Sobrenome:", JLabel.LEFT);
        campoSobrenome = new JTextField();

        // Nas linhas abaixo definimos uma máscara para o campo de data de nascimento.
        labelDataNascimento = new JLabel("Data de Nascimento:", JLabel.LEFT);
        campoDataNascimento = new JFormattedTextField();

        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.install(campoDataNascimento);
        } catch (ParseException ex) {
            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }

        labelEmail = new JLabel("E-mail", JLabel.LEFT);
        campoEmail = new JTextField();
        labelCargo = new JLabel("Cargo", JLabel.LEFT);

        // Na linha abaixo definimos o formato do campo de salário
        comboboxCargo = new JComboBox();
        labelSalario = new JLabel("Salário:", JLabel.LEFT);
        DecimalFormat formatter = new DecimalFormat("###0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        campoSalario = new JFormattedTextField(formatter);
        campoSalario.setValue(0.00);
        botaoGravar = new JButton("Adicionar");

        labelTitulo.setBounds(20, 20, 660, 40);
        labelNome.setBounds(150, 80, 400, 20);
        campoNome.setBounds(150, 100, 400, 40);
        labelSobrenome.setBounds(150, 140, 400, 20);
        campoSobrenome.setBounds(150, 160, 400, 40);
        labelDataNascimento.setBounds(150, 200, 400, 20);
        campoDataNascimento.setBounds(150, 220, 400, 40);
        labelEmail.setBounds(150, 260, 400, 20);
        campoEmail.setBounds(150, 280, 400, 40);
        labelCargo.setBounds(150, 320, 400, 20);
        comboboxCargo.setBounds(150, 340, 400, 40);
        labelSalario.setBounds(150, 380, 400, 20);
        campoSalario.setBounds(150, 400, 400, 40);
        botaoGravar.setBounds(560, 400, 130, 40);

        add(labelTitulo);
        add(labelNome);
        add(campoNome);
        add(labelSobrenome);
        add(campoSobrenome);
        add(labelDataNascimento);
        add(campoDataNascimento);
        add(labelEmail);
        add(campoEmail);
        add(labelCargo);
        add(comboboxCargo);
        add(labelSalario);
        add(campoSalario);
        add(botaoGravar);

        sqlCarregarCargos();

        setVisible(true);

    }

    private void criarEventos() {
        botaoGravar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Funcionario novoFuncionario = new Funcionario();
                novoFuncionario.setNome(campoNome.getText());
                novoFuncionario.setSobrenome(campoSobrenome.getText());
                novoFuncionario.setDataNascimento(campoDataNascimento.getText());
                novoFuncionario.setEmail(campoEmail.getText());
                Cargo cargoSelecionado = (Cargo) comboboxCargo.getSelectedItem();
                if (cargoSelecionado != null) {
                    novoFuncionario.setCargo(cargoSelecionado.getId());
                }

                novoFuncionario.setSalario(Double.valueOf(campoSalario.getText().replace(",", ".")));

                sqlInserirFuncionario(novoFuncionario);
            }
        });
    }

    private void sqlCarregarCargos() {
        // Conexão
        Connection conexao;
        // Instrução SQL
        Statement instrucaoSQL;
        // Resultados
        ResultSet resultados;

        try {
            // Conectando ao banco de dados
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);

            // Criando a instrução SQL
            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultados = instrucaoSQL.executeQuery("SELECT * FROM cargos ORDER BY nome ASC");
            comboboxCargo.removeAll();
            
            while(resultados.next()) {
                Cargo cargo = new Cargo();
                cargo.setId(resultados.getInt("Id"));
                cargo.setNome(resultados.getString("Nome"));
                comboboxCargo.addItem(cargo);
            }
            comboboxCargo.updateUI();
            
            conexao.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao carregar os cargos.\nConsulte a integridade e funcionalidade do código ou banco de dados.");
            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sqlInserirFuncionario(Funcionario novoFuncionario) {
        
        // Validando nome
        if (campoNome.getText().length() <= 3) {
            JOptionPane.showMessageDialog(null, "Erro: É necessário que o nome tenha pelo menos 4 caracteres.");
            return;
        }
        
        // Validando sobrenome
        if (campoSobrenome.getText().length() <= 3) {
            JOptionPane.showMessageDialog(null, "Erro: É necessário que o sobrenome tenha pelo menos 4 caracteres.");
            return;
        }
        
        // Validando salário
        if (Double.parseDouble(campoSalario.getText().replace(",", ".")) <= 100) {
            JOptionPane.showMessageDialog(null, "Erro: É necessário que o salário tenha pelo menos 100 (talvez) caracteres.");
            return;
        }
        
        // Validando email
        Boolean emailValidado = false;
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(campoEmail.getText());
        emailValidado = m.matches();
        
        if (!emailValidado) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o email corretamente.");
        }
        
        // Conexão
        Connection conexao;
        // Instrução SQL
        PreparedStatement instrucaoSQL;
        
        try {
            // Conectando ao banco de dados
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
            
            String template = "INSERT INTO funcionarios (nome,sobrenome,dataNascimento,email,cargo,salario)";
            template = template + " VALUES (?,?,?,?,?,?)";
            instrucaoSQL = conexao.prepareStatement(template);
            instrucaoSQL.setString(1, novoFuncionario.getNome());
            instrucaoSQL.setString(2, novoFuncionario.getSobrenome());
            instrucaoSQL.setString(3, novoFuncionario.getDataNascimento());
            instrucaoSQL.setString(4, novoFuncionario.getEmail());
            if (novoFuncionario.getCargo() > 0) {
                instrucaoSQL.setInt(5, novoFuncionario.getCargo());
            } else {
                instrucaoSQL.setNull(5, java.sql.Types.INTEGER);
            }
            instrucaoSQL.setString(6, Double.toString(novoFuncionario.getSalario()));
            instrucaoSQL.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Funcionário adicionado com sucesso!");
            Navegador.inicio();
            
            conexao.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar o funcionário.\nConsulte a integridade e funcionalidade do código ou banco de dados.");
            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
