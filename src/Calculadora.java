import java.awt.*;
import java.awt.event.*;
import java.text.CollationElementIterator;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculadora {
    // Tamanhos da janela
    int boardWidth = 360;
    int boardHeight = 540;

    // Armazenando as cores da calculadora
    Color cinzaClaro = new Color(212, 212, 210);
    Color cinzaEscuro = new Color(80, 80 ,80);
    Color preto = new Color(28,28,28);
    Color laranja = new Color(255, 149, 0);

    // Definindo o layout dos botões
    String [] botoes = {
        "", "","","←",
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "×", 
        "4", "5", "6", "-", 
        "1", "2", "3", "+", 
        "0", ".", "√", "=", 
    };
     // Definindo os operadores à direita e os botões topo da calculadora
    String[] simbolosDireita = {"÷", "×", "-", "+", "="};
    String[] simbolosTopo = {"AC", "+/-", "%", "←"};

    // Título da janela
    JFrame frame = new JFrame("Calculadora");

    // Criando o painel para exibir os números e resultados
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    // Variáveis para armazenar os números inseridos
    String A = "0";
    String operador = null;
    String B = null;


    // Construtor para inicializar a janela
    Calculadora() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Configuração do display (painel onde aparece o número)
        displayLabel.setBackground(preto);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        // Painel do display (onde o número é exibido)
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        // Configuração do painel de botões
        buttonsPanel.setLayout(new GridLayout(6,4));
        buttonsPanel.setBackground(preto);
        frame.add(buttonsPanel);

        // Loop para adicionar cada botão à interface
        for(String valorBotao : botoes){
            
            //tratamento de botões vazios
            if (valorBotao.equals("") || valorBotao == null) {
                JButton botaoVazio = new JButton();
                botaoVazio.setBackground(preto);
                botaoVazio.setBorder(null);
                botaoVazio.setFocusable(false);
                botaoVazio.setEnabled(false);
                buttonsPanel.add(botaoVazio);
                continue;
            }
            // Criando o botão normal
            JButton botao = new JButton(valorBotao);
            botao.setFont(new Font("Arial", Font.PLAIN, 30));
            botao.setFocusable(false);
            botao.setBorder(new LineBorder(preto));

            // Definindo o estilo dos botões com base no valor 
            if(Arrays.asList(simbolosTopo).contains(valorBotao)){
                botao.setBackground(cinzaClaro);
                botao.setForeground(preto);
            } else if (Arrays.asList(simbolosDireita).contains(valorBotao)){
                botao.setBackground(laranja);
                botao.setForeground(Color.white);
            } else {
                botao.setBackground(cinzaEscuro);
                botao.setForeground(Color.white);
            }
            // Adicionando o botão ao painel de botões
            buttonsPanel.add(botao);

             // Ação do botão
            botao.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    JButton botao = (JButton) e.getSource();
                    String valorBotao = botao.getText();

                    // Lógica para os botões
                    if(Arrays.asList(simbolosDireita).contains(valorBotao)){
                        if(valorBotao.equals("=")){
                            if(A != null){
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                if(operador.equals("+")){
                                    displayLabel.setText(removerZeroDecimal(numA+numB));
                                }
                                else if (operador.equals("-")){
                                    displayLabel.setText(removerZeroDecimal(numA-numB));
                                }
                                else if (operador.equals("×")){
                                    displayLabel.setText(removerZeroDecimal(numA*numB));
                                }
                                else if(operador.equals("÷")) {
                                    if(numB == 0){
                                        displayLabel.setText("Erro");
                                    } else {
                                        displayLabel.setText(removerZeroDecimal(numA/numB));
                                    }
                                }
                                limpar(); // Limpar as variáveis A, B e operador
                            }
                        }
                        else if ("+-×÷".contains(valorBotao)){
                            // Se for um operador (+, -, ×, ÷), guarda o valor de A e seleciona o operador
                            if(operador == null){
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operador = valorBotao;
                        }
                    } 
                     // Ações para os botões da parte superior (AC, +/-, %, ←)
                    else if (Arrays.asList(simbolosTopo).contains(valorBotao)){
                        if (valorBotao.equals("AC")){
                            limpar();
                            displayLabel.setText("0");
                        }
                        else if (valorBotao.equals("+/-")){
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removerZeroDecimal(numDisplay));
                        }
                        else if (valorBotao.equals("%")){
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removerZeroDecimal(numDisplay));
                        }
                        // Remove o último dígito
                        else if (valorBotao.equals("←")) {  
                            String textoAtual = displayLabel.getText();
                            if (textoAtual.length() > 1) {
                                displayLabel.setText(textoAtual.substring(0, textoAtual.length() - 1));
                            } else {
                                displayLabel.setText("0");
                            }
                        }
                    } 
                    else { // Lógica para números e ponto
                        if (valorBotao.equals(".")){
                            if (!displayLabel.getText().contains(valorBotao)) {
                                displayLabel.setText(displayLabel.getText() + valorBotao);
                            }
                        }
                        // Calcula a raiz quadrada
                        else if (valorBotao.equals("√")){
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            String resultado = raiz(numDisplay);
                            displayLabel.setText(resultado);
                        }
                        else if("0123456789".contains(valorBotao)){

                            if (displayLabel.getText().equals("0")){
                                displayLabel.setText(valorBotao);
                            } else {
                                displayLabel.setText(displayLabel.getText() + valorBotao);
                            }
                        }
                    }
                }
            });
        }
        frame.setVisible(true); // Tornar a janela visível
    }
     // Função para limpar as variáveis
    void limpar() {
        A = "0";
        operador = null;
        B = null;
    }
    // Função para remover o zero decimal (caso o número seja inteiro)
    String removerZeroDecimal (double numDisplay){
        if(numDisplay % 1 == 0){
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
    // Função para calcular a raiz quadrada
    String raiz(double numDisplay){
        if (numDisplay < 0){
            return "Erro";
        } else {
            numDisplay = Math.sqrt(numDisplay);
            return removerZeroDecimal(numDisplay);
        }
    }
}