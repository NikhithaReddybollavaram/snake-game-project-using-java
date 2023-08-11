import javax.swing.*;

public class Snakegame extends JFrame {
    board board;
    Snakegame(){
        board=new board();
        add(board);
        pack();
        //setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args) {
       //initialize the snake game
        Snakegame snakegame=new Snakegame();
    }
}