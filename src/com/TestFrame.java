package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

class TestFrame {
    private JFrame testFrame;
    private JButton prevButton = new JButton("Предыдущий вопрос");
    private JButton nextButton = new JButton("Следующий вопрос");
    private JButton endButton = new JButton("Закончить тест");
    private JLabel question = new JLabel();
    private JLabel picture;
    private JLabel timerLabel = new JLabel("10:00");
    private JRadioButton[] answers = new JRadioButton[10];
    private ButtonGroup groupAnswers = new ButtonGroup();
    private Timer timerTest = new Timer(600000, new EndButton());
    private Timer timer = new Timer(1000, new RefreshTimerLabel());

    private int questionNum = 1;
    private int[][] variantsAnswers = new int[10][10];
    private int[] answersStudent = new int[10];
    private int[] pictureNum = new int[10];

    private Random random = new Random();
    private ArrayList<String> listAnswers = new ArrayList<>();

    private int[] makeAnswer(int[] answersStudent){
        for (int i = 0; i < 10; i++){
            if (this.answers[i].isSelected()){
                answersStudent[questionNum - 1] = variantsAnswers[questionNum - 1][i];
                break;
            }
        }
        return answersStudent;
    }

    private ArrayList<String> fillInListAnswers() throws FileNotFoundException, UnsupportedEncodingException {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream("Test.txt"), "UTF-8"))){
            String tmp;
            while ((tmp = br.readLine()) != null) {
                list.add(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private int[][] fillInVariantsAnswers(){
        int[][] variantsAnswers = new int[10][10];
        boolean flag;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                flag = false;
                variantsAnswers[i][j] = random.nextInt(179) + 1;
                while (!flag) {
                    flag = true;
                    for (int k = 0; k < j; k++){
                        if (variantsAnswers[i][k] == variantsAnswers[i][j]){
                            flag = false;
                            variantsAnswers[i][j] = random.nextInt(179) + 1;
                            break;
                        }
                    }
                }
            }
        }
        return variantsAnswers;
    }

    private int[] fillPictureNum(){
        int[] pictureNum = new int[10];
        boolean flag;
        int num;

        for(int i = 0; i < 10; i++){
            flag = false;
            num = random.nextInt(10);
            while(!flag)
            {
                flag = true;
                for(int j = 0; j < 10; j++)
                    if(pictureNum[j] == variantsAnswers[i][num]){
                    flag = false;
                    num = random.nextInt(10);
                    break;
                }
            }
            pictureNum[i] = variantsAnswers[i][num];
        }
        return pictureNum;
    }

    void buildGUITest(boolean testing) throws FileNotFoundException, UnsupportedEncodingException {
        testFrame = new JFrame("Тест по военной топографии");
        testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Color backGround = new Color(175,224,224);

        JPanel downPanel = new JPanel();
        JPanel upPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel pictureAndTimerPanel = new JPanel();
        JPanel answersPanel = new JPanel();

        prevButton.addActionListener(new PrevButton());
        prevButton.setEnabled(false);

        nextButton.addActionListener(new NextButton());

        endButton.addActionListener(new EndButton());

        question.setText("Вопрос №" + questionNum);
        question.setFont(new Font("Dialog", Font.PLAIN, 16));

        variantsAnswers = fillInVariantsAnswers();
        pictureNum = fillPictureNum();
        listAnswers = fillInListAnswers();

        picture = new JLabel(new ImageIcon("Images/" + pictureNum[questionNum-1] + ".jpg"));

        upPanel.add(question);
        upPanel.setBackground(backGround);

        answersPanel.setLayout(new GridLayout(5, 2));
        for (int i = 0; i < 10; i++){
            answers[i] = new JRadioButton(listAnswers.get(variantsAnswers[0][i] - 1));
            answers[i].setFont(new Font("Dialog", Font.PLAIN, 16));
            answers[i].setBackground(backGround);
            answersPanel.add(answers[i]);
            groupAnswers.add(answers[i]);
            answersStudent[i] = -1;
        }
        answersPanel.setBackground(backGround);

        pictureAndTimerPanel.setLayout(new GridLayout(1,2));
        if (testing) {
            timerTest.start();
            timer.start();
            timerLabel.setFont(new Font("Dialog", Font.PLAIN, 25));
            pictureAndTimerPanel.add(timerLabel);
            timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            picture.setHorizontalAlignment(SwingConstants.LEFT);
        }
        pictureAndTimerPanel.add(picture);
        pictureAndTimerPanel.setBackground(backGround);

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(pictureAndTimerPanel);
        centerPanel.add(answersPanel);
        centerPanel.setBackground(backGround);

        downPanel.add(prevButton);
        downPanel.add(endButton);
        downPanel.add(nextButton);
        downPanel.setBackground(backGround);

        testFrame.getContentPane().add(BorderLayout.SOUTH, downPanel);
        testFrame.add(BorderLayout.NORTH, upPanel);
        testFrame.add(BorderLayout.CENTER, centerPanel);
        testFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        testFrame.setBackground(backGround);
        testFrame.setVisible(true);
    }

    private class EndButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            answersStudent = makeAnswer(answersStudent);
            new ResultFrame().buildGUIResult(pictureNum, answersStudent, listAnswers);
            testFrame.setVisible(false);

            testFrame.dispose();
            timerTest.stop();
            timer.stop();
        }
    }

    private class PrevButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            answersStudent = makeAnswer(answersStudent);
            questionNum--;
            if (questionNum == 1){
                prevButton.setEnabled(false);
            }
            else{
                nextButton.setEnabled(true);
            }

            question.setText("Вопрос №" + questionNum);
            picture.setIcon(new ImageIcon("Images/" + pictureNum[questionNum-1] + ".jpg"));
            for (int i = 0; i < 10; i++){
                answers[i].setText(listAnswers.get(variantsAnswers[questionNum - 1][i]-1));
                groupAnswers.clearSelection();
            }

            if (answersStudent[questionNum - 1] != -1) {
                for (int i = 0; i < 10; i++) {
                    if (answersStudent[questionNum - 1] == variantsAnswers[questionNum -1][i]){
                        answers[i].setSelected(true);
                        break;
                    }
                }
            }
        }
    }

    private class NextButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            answersStudent = makeAnswer(answersStudent);
            questionNum++;
            if(questionNum == 10){
                nextButton.setEnabled(false);
            }
            else {
                prevButton.setEnabled(true);
            }

            question.setText("Вопрос №" + questionNum);
            picture.setIcon(new ImageIcon("Images/" + pictureNum[questionNum - 1] + ".jpg"));
            for (int i = 0; i < 10; i++){
                answers[i].setText(listAnswers.get(variantsAnswers[questionNum - 1][i] - 1));
                groupAnswers.clearSelection();
            }

            if (answersStudent[questionNum - 1] != -1) {
                for (int i = 0; i < 10; i++) {
                    if (answersStudent[questionNum - 1] == variantsAnswers[questionNum -1][i]){
                        answers[i].setSelected(true);
                        break;
                    }
                }
            }
        }
    }

    private class RefreshTimerLabel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String time = timerLabel.getText();
            String[] temp;
            int minutes;
            int seconds;
            if (time.equals("10:00")){
                timerLabel.setText("9:59");
            }
            else{
                temp = time.split(":");
                minutes = Integer.parseInt(temp[0]);
                seconds = Integer.parseInt(temp[1]);

                if (seconds == 0){
                    minutes--;
                    seconds = 59;
                    timerLabel.setText(minutes + ":" + seconds);
                }
                else{
                    seconds--;
                    if (seconds < 10)
                        timerLabel.setText(minutes + ":0" + seconds);
                    else
                        timerLabel.setText(minutes + ":" + seconds);
                }
            }
        }
    }
}
