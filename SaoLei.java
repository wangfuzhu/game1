package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SaoLei implements ActionListener {
    JFrame frame = new JFrame("Mine sweeper");
    JButton reset = new JButton("again");
    Container container = new Container();

    //数据结构
    final int row = 20;
    final int col = 20;
    final int num = 30;
    JButton[][] buttons = new JButton[row][col];
    int[][] counts = new int[row][col];
    final int  lei = 8;


    //构造函数
    public SaoLei(){
        frame.setSize(700,600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        reset.setBackground(Color.lightGray);
        reset.addActionListener(this);
        frame.add(reset, BorderLayout.NORTH);
        addButtons();
        addLei();
        calNeibollei();
        frame.setVisible(true);
    }
    void addButtons(){
        frame.add(container,BorderLayout.CENTER);
        container.setLayout(new GridLayout(row,col));
        for (int i=0; i<row;i++){
            for (int j=0;j<col;j++) {
                JButton button = new JButton();
                //shijian
                button.addActionListener(this);
                button.setMargin(new Insets(0,0,0,0));
                buttons[i][j] = button;
                container.add(button);
            }
        }
    }
    void addLei(){
        Random rand  = new Random();
        int randRow,randCol;
        for (int i = 0;i<num;i++){
            randRow = rand.nextInt(row);
            randCol = rand.nextInt(col);
            if (counts[randRow][randCol] == lei){
                i--;
            }else {
                counts[randRow][randCol] = lei;
                //buttons[randRow][randCol].setText("m");
            }
        }
    }
    void calNeibollei(){
        int count;
        for (int i=0; i<row;i++){
            for (int j=0;j<col;j++) {
                count = 0;
                if (counts[i][j] == lei) continue;
                if (i > 0 && j> 0 && counts[i-1][j-1] == lei) count++;
                if (i > 0 && counts[i-1][j] == lei) count++;
                if (i > 0 && j < 19 &&counts[i-1][j+1] == lei) count++;
                if (j > 0 && counts[i][j-1] == lei) count++;
                if (j < 19 && counts[i][j+1] == lei) count++;
                if (i < 19 && j > 0 &&counts[i+1][j-1] == lei) count++;
                if (i < 19 && counts[i+1][j] == lei) count++;
                if (i < 19 && j < 19 &&counts[i+1][j+1] == lei) count++;
                counts[i][j] = count;
                //buttons[i][j].setText(counts[i][j] + "");
            }
        }

    }

    void openCell(int i,int j){
        if(buttons[i][j].isEnabled() == false) return;
        //yijingbeidakai zhijiefanhui
        buttons[i][j].setEnabled(false);
        if(counts[i][j]==0){
            if (i > 0 && j> 0 && counts[i-1][j-1] != lei) openCell(i-1,j-1);
            if (i > 0 && counts[i-1][j] != lei)openCell(i-1,j);
            if (i > 0 && j < 19 &&counts[i-1][j+1] != lei) openCell(i-1,j+1);
            if (j > 0 && counts[i][j-1] != lei) openCell(i,j-1);
            if (j < 19 && counts[i][j+1] != lei) openCell(i,j+1);
            if (i < 19 && j > 0 &&counts[i+1][j-1] != lei) openCell(i+1,j-1);
            if (i < 19 && counts[i+1][j] != lei) openCell(i+1,j);
            if (i < 19 && j < 19 &&counts[i+1][j+1] != lei) openCell(i+1,j+1);
        }else {
            buttons[i][j].setText(counts[i][j]+"");
        }
    }
    void checkWin(){
        for (int i=0; i<row;i++){
            for (int j=0;j<col;j++) {
                if (buttons[i][j].isEnabled() == true && counts[i][j] !=lei){
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame,"WIN");
            return;
        }
    }
    void loseGame(){
        for (int i=0; i<row;i++){
            for (int j=0;j<col;j++) {
                int count = counts[i][j];
                if (count == lei) {
                    buttons[i][j].setBackground(Color.RED);
                    buttons[i][j].setText("X");
                    buttons[i][j].setEnabled(false);
                } else {

                    buttons[i][j].setText(count + "");
                    buttons[i][j].setEnabled(false);
                }
            }
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.equals(reset)) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    buttons[i][j].setText("");
                    buttons[i][j].setEnabled(true);
                    counts[i][j] = 0;
                    reset.setBackground(Color.lightGray);
                }
            }
            addLei();
            calNeibollei();
        } else {
            int count = 0;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (button.equals(buttons[i][j])) {
                        count = counts[i][j];
                        if (count == lei) {
                            loseGame();
                        } else {
                            openCell(i,j);
                            checkWin();
                        }
                        return;
                    }
                }
            }
        }
    }
    public static void main(String[] args){
        SaoLei lei = new SaoLei();
    }
}
