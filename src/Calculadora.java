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
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "×", 
        "4", "5", "6", "-", 
        "1", "2", "3", "+", 
        "0", ".", " √", "=", 
    };
    String[] simbolosDireita = {"÷", "×", "-", "+", "="};
    String[] simbolosTopo = {"AC", "+/-", "%"};

    // Título da janela
    JFrame frame = new JFrame("Calculadora");

    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();


    // Construtor para inicializar a janela
    Calculadora() {
        frame.setVisible(true);
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

        buttonsPanel.setLayout(new GridLayout(5,4));
        buttonsPanel.setBackground(preto);
        frame.add(buttonsPanel);

        for(int i = 0; i < botoes.length; i++){
            JButton botao = new JButton();
            String valorBotao = botoes[i];
            botao.setFont(new Font("Arial", Font.PLAIN, 30));
            botao.setText(valorBotao);
            botao.setFocusable(false);

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
        }
    }
}