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

    String [] botoes = {
        "", "","","←",
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "×", 
        "4", "5", "6", "-", 
        "1", "2", "3", "+", 
        "0", ".", "√", "=", 
    };
    String[] simbolosDireita = {"÷", "×", "-", "+", "="};
    String[] simbolosTopo = {"AC", "+/-", "%", "←"};

    // Título da janela
    JFrame frame = new JFrame("Calculadora");

    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    // A+B, A-B, A*B, A/B
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

        displayLabel.setBackground(preto);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(6,4));
        buttonsPanel.setBackground(preto);
        frame.add(buttonsPanel);

        for(String valorBotao : botoes){
            
            JButton botao = new JButton(valorBotao);
            if (valorBotao.equals("") || valorBotao == null) {
                JButton botaoVazio = new JButton();
                botaoVazio.setBackground(preto);
                botaoVazio.setBorder(null);
                botaoVazio.setFocusable(false);
                botaoVazio.setEnabled(false);
                buttonsPanel.add(botaoVazio);
                continue;
            }
            botao.setFont(new Font("Arial", Font.PLAIN, 30));
            botao.setFocusable(false);
            botao.setBorder(new LineBorder(preto));

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
                                limpar();
                            }
                        }
                        else if ("+-×÷".contains(valorBotao)){
                            if(operador == null){
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operador = valorBotao;
                        }
                    } 
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
                        else if (valorBotao.equals("←")) {  
                            String textoAtual = displayLabel.getText();
                            if (textoAtual.length() > 1) {
                                displayLabel.setText(textoAtual.substring(0, textoAtual.length() - 1));
                            } else {
                                displayLabel.setText("0");
                            }
                        }
                    } 
                    else { //números ou .
                        if (valorBotao.equals(".")){
                            if (!displayLabel.getText().contains(valorBotao)) {
                                displayLabel.setText(displayLabel.getText() + valorBotao);
                            }
                        }
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
        frame.setVisible(true);
    }

    void limpar() {
        A = "0";
        operador = null;
        B = null;
    }

    String removerZeroDecimal (double numDisplay){
        if(numDisplay % 1 == 0){
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
    String raiz(double numDisplay){
        if (numDisplay < 0){
            return "Erro";
        } else {
            numDisplay = Math.sqrt(numDisplay);
            return removerZeroDecimal(numDisplay);
        }
    }
}