package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sistema.BancoDeDados;
import sistema.entidades.Cargo;

public class CargosInserir extends JPanel {
    
    JLabel labelTitulo, labelCargo;
    JTextField campoCargo;
    JButton botaoGravar;
    
    public CargosInserir() {
        criarComponentes();
        criarEventos();
    }

    private void criarComponentes() {
        //Na linha 34, estamos definindo que não usaremos nenhum gerenciador de layout.
        setLayout(null);
        
        /*Nas linhas 39 a 43 estamos instanciando os componentes da tela:
         - Os componentes JLabel estão sendo inicializados com textos e alinhamentos especificos.
         - Já o componente JButton, está sendo inicializado somente com seu texto de exibição.*/
        labelTitulo = new JLabel("Cadastro de Cargos", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelCargo = new JLabel("Nome do Cargo", JLabel.LEFT);
        campoCargo = new JTextField();
        botaoGravar = new JButton("Adicionar Cargo");
        
        //Na linha 46 a 49, Definimos o posicionamento e o tamanho dos componentes na tela.
        labelTitulo.setBounds(20, 20, 660, 40);
        labelCargo.setBounds(150, 120, 400, 20);
        campoCargo.setBounds(150, 140, 400, 40);
        botaoGravar.setBounds(250, 380, 200, 40);
        
        //Na linha 52 a 55, adicionamos os componentes à tela.
        add(labelTitulo);
        add(labelCargo);
        add(campoCargo);
        add(botaoGravar);
        
        //Na linha 58, tornamos a tela visivel.
        setVisible(true);
        
    }
    
    /*Nas linhas 66 a 80,  estamos definindo que, ao ser acionado, o botão
     Adicionar Cargo irá criar uma instância da entidade Cargo atribuíndo ao cargo
     o valor do texto digitado no JTextField campoCargo e por fim chamar o
     método sqlInserirCargo.*/
    private void criarEventos() {
        
        botaoGravar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Cargo novoCargo = new Cargo();
                novoCargo.setNome(campoCargo.getText());
                
                sqlInserirCargo(novoCargo);
                
            }
        });
        
    }
    
    // Nas linhas 83 a 89, validamos o conteúdo do campo Nome do Cargo
    private void sqlInserirCargo(Cargo novoCargo) {
        
        // Validando nome
        if(campoCargo.getText().length() <= 3) {
            JOptionPane.showMessageDialog(null, "Erro: É necessário que o nome tenha pelo menos 4 caracteres.");
            return;
        }
        
        /*Nas linhas 95 a 115, realizamos a conexão com o banco de dados para inserir um novo cargo,
         de acordo com os dados que foram adicionados ao campo Nome do Cargo.*/
        
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
            instrucaoSQL.executeUpdate("INSERT INTO cargos (nome) VALUES ('"+novoCargo.getNome()+"') ");
            
            JOptionPane.showMessageDialog(null, "Cargo adicionado com sucesso!");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar o cargo.\nConsulte a integridade e funcionalidade do código ou banco de dados.");
            Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
