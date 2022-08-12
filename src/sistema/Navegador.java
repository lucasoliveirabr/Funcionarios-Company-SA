package sistema;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import sistema.entidades.Cargo;
import sistema.entidades.Funcionario;
import sistema.telas.CargosConsultar;
import sistema.telas.CargosEditar;
import sistema.telas.CargosInserir;
import sistema.telas.FuncionariosConsultar;
import sistema.telas.FuncionariosEditar;
import sistema.telas.FuncionariosInserir;
import sistema.telas.Inicio;
import sistema.telas.Login;
import sistema.telas.RelatoriosCargos;
import sistema.telas.RelatoriosSalarios;

public class Navegador {
    
    // Menu
    private static boolean menuConstruido;
    private static boolean menuHabilitado;
    private static JMenuBar menuBar;
    private static JMenu menuArquivo, menuFuncionarios, menuCargos, menuRelatorios;
    private static JMenuItem miSair, miMenu, miFuncionariosConsultar, miFuncionariosCadastrar, miCargosConsultar;
    private static JMenuItem miCargosCadastrar, miRelatoriosCargos, miRelatoriosSalarios;
    
    public static void login() {
        Sistema.tela = new Login();
        Sistema.frame.setTitle("Funcionários Company SA");
        Navegador.atualizarTela();
    }
    
    public static void inicio() {
        Sistema.tela = new Inicio();
        Sistema.frame.setTitle("Funcionários Company SA");
        Navegador.atualizarTela();
    }
    
    private static void funcionariosCadastrar() {
        Sistema.tela = new FuncionariosInserir();
        Sistema.frame.setTitle("Funcionários Company SA - Cadastrar Funcionários");
        Navegador.atualizarTela();
    }
    
    private static void funcionariosConsultar() {
        Sistema.tela = new FuncionariosConsultar();
        Sistema.frame.setTitle("Funcionários Company SA - Consultar Funcionários");
        Navegador.atualizarTela();
    }
    
    public static void funcionariosEditar(Funcionario funcionario) {
        Sistema.tela = new FuncionariosEditar(funcionario);
        Sistema.frame.setTitle("Funcionários Company SA - Editar Funcionários");
        Navegador.atualizarTela();
    }
    
    public static void cargosCadastrar() {
        Sistema.tela = new CargosInserir();
        Sistema.frame.setTitle("Funcionários Company SA - Cadastrar Cargos");
        Navegador.atualizarTela();
    }
    
    public static void cargosConsultar() {
        Sistema.tela = new CargosConsultar();
        Sistema.frame.setTitle("Funcionários Company SA - Consultar Cargos");
        Navegador.atualizarTela();
    }
    
    public static void cargosEditar(Cargo cargo) {
        Sistema.tela = new CargosEditar(cargo);
        Sistema.frame.setTitle("Funcionários Company SA - Editar Cargos");
        Navegador.atualizarTela();
    }
    
    public static void relatoriosCargos() {
        Sistema.tela = new RelatoriosCargos();
        Sistema.frame.setTitle("Funcionários Company SA - Relatórios de Cargos");
        Navegador.atualizarTela();
    }
    
    public static void relatoriosSalarios() {
        Sistema.tela = new RelatoriosSalarios();
        Sistema.frame.setTitle("Funcionários Company SA - Relatórios de Salários");
        Navegador.atualizarTela();
    }
    
    // Método que remove a tela atual e adiciona a próxima tela
    private static void atualizarTela() {
        Sistema.frame.getContentPane().removeAll();
        Sistema.tela.setVisible(true);
        Sistema.frame.add(Sistema.tela);
        
        Sistema.frame.setVisible(true);
    }
    
    
    
    private static void construirMenu() {
        if (!menuConstruido) {
            menuConstruido = true;
            
            menuBar = new JMenuBar();
            
            // Menu Arquivo
            menuArquivo = new JMenu("Arquivo");
            menuBar.add(menuArquivo);
            miMenu = new JMenuItem("Menu");
            menuArquivo.add(miMenu);
            miSair = new JMenuItem("Sair");
            menuArquivo.add(miSair);
            
            // Menu Funcionários
            menuFuncionarios = new JMenu("Funcionários");
            menuBar.add(menuFuncionarios);
            miFuncionariosCadastrar = new JMenuItem("Cadastrar");
            menuFuncionarios.add(miFuncionariosCadastrar);
            miFuncionariosConsultar = new JMenuItem("Consultar");
            menuFuncionarios.add(miFuncionariosConsultar);
            
            // Menu Cargos
            menuCargos = new JMenu("Cargos");
            menuBar.add(menuCargos);
            miCargosCadastrar = new JMenuItem("Cadastrar");
            menuCargos.add(miCargosCadastrar);
            miCargosConsultar = new JMenuItem("Consultar");
            menuCargos.add(miCargosConsultar);
            
            // Menu Relatórios
            menuRelatorios = new JMenu("Relatórios");
            menuBar.add(menuRelatorios);
            miRelatoriosCargos = new JMenuItem("Funcionários por Cargos");
            menuRelatorios.add(miRelatoriosCargos);
            miRelatoriosSalarios = new JMenuItem("Salários dos Funcionários");
            menuRelatorios.add(miRelatoriosSalarios);
            
            criarEventosMenu();
            
        }
    }
    
    public static void habilitarMenu() {
        if (!menuConstruido) construirMenu();
        if (!menuHabilitado) {
            menuHabilitado = true;
            Sistema.frame.setJMenuBar(menuBar);
        }
    }
    
    private static void desabilitarMenu() {
        if (menuHabilitado) {
            menuHabilitado = false;
            Sistema.frame.setJMenuBar(null);
        }
    }
    
    /*private static void criarMenu() {
        miMenu.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navegador.inicio();
            }
            
        });*/
    
    private static void criarEventosMenu() {
        miSair.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
            
        });
        
        // Funcionários
        miFuncionariosCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcionariosCadastrar();
            }
        });
        miFuncionariosConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcionariosConsultar();
            }
        });
        
        // Cargos
        miCargosCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargosCadastrar();
            }
        });
        miCargosConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargosConsultar();
            }
        });
        
        miRelatoriosCargos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                relatoriosCargos();
            }
        });
        miRelatoriosSalarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                relatoriosSalarios();
            }
        });
        
    }
    
}
