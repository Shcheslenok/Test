package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


class ResultFrame {
    private JFrame resultFrame;


    void buildGUIResult(int[] pictureNum, int[] answersStudent, ArrayList<String> list) {
        int mark = 0;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int pictureSize = (dim.height - 200) / 10;


        Color backGround = new Color(175,224,224);

        resultFrame = new JFrame("Результат");
        resultFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        resultFrame.setBackground(backGround);

        JButton endButton = new JButton("Конец");
        endButton.addActionListener(new EndButton());

        JPanel downPanel = new JPanel();
        downPanel.setBackground(backGround);
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(5,2));
        leftPanel.setBackground(backGround);
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(5,2));
        rightPanel.setBackground(backGround);
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(10,2));
        centerPanel.setBackground(backGround);

        for(int i = 0; i < 10; i++){
            if (answersStudent[i] == pictureNum[i]){
                mark++;
            }
        }

        JPanel upPanel = new JPanel();
        upPanel.setBackground(backGround);

        String result = "Ваша оценка: ";
        result = result + mark;

        JLabel resultLabel = new JLabel(result);
        if(mark < 4){
            resultLabel.setForeground(Color.RED);
        }
        else if (mark > 3 && mark < 8){
            resultLabel.setForeground(Color.BLUE);
        }
        else
            resultLabel.setForeground(Color.GREEN);

        resultLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        upPanel.add(resultLabel);

        JLabel[] picture = new JLabel[10];
        JLabel[] answers = new JLabel[10];
        for (int i = 0; i < 10; i++){
            picture[i] = new JLabel(new ImageIcon(new ImageIcon("Images/" + pictureNum[i] + ".jpg").getImage()
                    .getScaledInstance(pictureSize + 50, pictureSize,
                            new ImageIcon("Images/" + pictureNum[i] + ".jpg").getImage().SCALE_DEFAULT)));

            if (answersStudent[i] == -1)
                answers[i] = new JLabel();
            else
                answers[i] = new JLabel(list.get(answersStudent[i] - 1));

            if (answersStudent[i] == pictureNum[i])
                answers[i].setForeground(Color.GREEN);
            else
                answers[i].setForeground(Color.RED);

            //answers[i].setFont(new Font("Dialog", Font.PLAIN, 18));
            /*if (i < 5) {
                leftPanel.add(picture[i]);
                leftPanel.add(answers[i]);
            }
            else {
                rightPanel.add(picture[i]);
                rightPanel.add(answers[i]);
            }*/
            centerPanel.add(picture[i]);
            centerPanel.add(answers[i]);

        }

        downPanel.add(endButton);

        resultFrame.add(BorderLayout.NORTH, upPanel);
        //resultFrame.add(BorderLayout.WEST, leftPanel);
        resultFrame.add(BorderLayout.CENTER, centerPanel);
        //resultFrame.add(BorderLayout.EAST, rightPanel);
        resultFrame.add(BorderLayout.SOUTH, downPanel);
        resultFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        resultFrame.setVisible(true);
    }

    private class EndButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resultFrame.setVisible(false);
            resultFrame.dispose();
            new Main().buildGUI();
        }
    }
}
