package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.time.Year;

public class Main {

    JFrame mainFrame;
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();


    public static void main(String[] args){
        new Main().buildGUI();
    }

    public void buildGUI() {
        Color backGround = new Color(175,224,224);
        mainFrame = new JFrame("Тест по военной топографии");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBackground(backGround);

        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel upPanel = new JPanel();
        JPanel downPanel = new JPanel();

        JLabel title = new JLabel("Тест по военной топографии");
        title.setFont(new Font("Dialog",Font.BOLD, 22));
        JLabel year = new JLabel("2017", SwingConstants.CENTER);
        year.setFont(new Font("Dialog", Font.BOLD, 16));

        JLabel testDescription = new JLabel("<html>Тест по военной топографии сотоит из 10 вопросов.<br>" +
                "Один правильный ответ равен одному балу.<br>" +
                "На выпонение теста дается 10 минут.</html>",SwingConstants.CENTER);
        testDescription.setFont(new Font("Dialog", Font.PLAIN, 18));
        testDescription.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel author = new JLabel("<html>Разработал студент второго курса<br>" +
                "Щесленок Петр Николаевич<br>" +
                "тел. +375 29 852 16 38<br>" +
                "Руководитель: Савчук С.В.</html>",SwingConstants.RIGHT);

        JButton startButton = new JButton("Начать тест");
        startButton.addActionListener(new StartButton());
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton exitButton = new JButton("Выход");
        exitButton.addActionListener(new ExitButton());
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton trainingButton = new JButton("Пройти тренировку");
        trainingButton.addActionListener(new TrainingButton());
        trainingButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel logoMF = new JLabel(new ImageIcon("Images/Logo_MF.png"));
        JLabel logoBSU = new JLabel(new ImageIcon("Images/Logo_BSU.png"));

        leftPanel.add(logoBSU);
        leftPanel.setBackground(backGround);
        rightPanel.add(logoMF);
        rightPanel.setBackground(backGround);

        upPanel.add(title);
        upPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        upPanel.setBackground(backGround);

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(testDescription);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(author);
        centerPanel.add(Box.createVerticalStrut(100));
        centerPanel.add(startButton);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(trainingButton);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(exitButton);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.setBackground(backGround);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        downPanel.add(year);
        downPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        downPanel.setBackground(backGround);

        mainFrame.add(BorderLayout.NORTH, upPanel);
        mainFrame.add(BorderLayout.CENTER, centerPanel);
        mainFrame.add(BorderLayout.EAST, rightPanel);
        mainFrame.add(BorderLayout.WEST, leftPanel);
        mainFrame.add(BorderLayout.SOUTH, downPanel);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);
    }

    private class StartButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.setVisible(false);
            try {
                new TestFrame().buildGUITest(true);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            mainFrame.dispose();
        }
    }

    private class ExitButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.dispose();
            System.exit(0);
        }
    }

    private class TrainingButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.setVisible(false);
            try {
                new TestFrame().buildGUITest(false);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            mainFrame.dispose();
        }
    }
}
