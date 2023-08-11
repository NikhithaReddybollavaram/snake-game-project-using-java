import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class board extends JPanel implements ActionListener{
    //initialize board coordinates
    int b_height = 400;
    int b_width = 400;
    //initialize snake
    int max_dots = 1600;
    int dot_size = 10;
    int dots;
    //initialize board coordinates
    int[] x = new int[max_dots];
    int[] y = new int[max_dots];
    //initializing the apple's position
    int apple_x;
    int apple_y;
    //images
    Image apple, head, body;
    Timer timer;
    int DELAY=150;
    boolean leftdirection=true;
    boolean updirection=false;
    boolean downdirection=false;
    boolean rightdirection=false;

    boolean ingame=true;

    //declaring the board
    board() {
        TAdapter tAdapter= new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(b_width, b_height));
        setBackground(Color.black);
        initigame();
        loadimages();
    }

    public void initigame() {
        dots = 3;
        //initialize snake position
        x[0] = 250;
        y[0] = 250;
        for (int i = 0; i < dots; i++) {
            x[i] = x[0] + dot_size *i;
            y[i] = y[0];
        }
        //initializing apple's position

        locateapple();
        timer=new Timer(DELAY,this);
        timer.start();
    }

    //load images from resource folder to image object
    public void loadimages() {
        ImageIcon bodyicon = new ImageIcon("src/resourses/dot.png");
        body = bodyicon.getImage();
        ImageIcon headicon = new ImageIcon("src/resourses/head.png");
        head = headicon.getImage();
        ImageIcon appleicon = new ImageIcon("src/resourses/apple.png");
        apple = appleicon.getImage();
    }

    //draw images of snake and apple at their positions
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        dodrawing(g);
    }

    public void dodrawing(Graphics g) {
        if(ingame){
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else {
                    g.drawImage(body, x[i], y[i], this);
                }
            }
        }else{
            timer.stop();
            gameover(g);
        }

    }
    //randomize the position of apple
    public void locateapple(){
        apple_x=((int)(Math.random()*39))*dot_size;
        apple_y=((int)(Math.random()*39))*dot_size;

    }
//check collisions with body and border
    public void checkcollisions(){
        //collision with body
        for(int i=0;i<dots;i++){
            if(i>4&&x[0]==x[i]&&y[0]==y[i]){
                ingame=false;
            }
        }
        //collision with border
        if(x[0]<0){
            ingame=false;
        }
        if(y[0]<0){
            ingame=false;
        }
        if(x[0]>b_width){
            ingame=false;
        }
        if(y[0]>b_height){
            ingame=false;
        }

    }
    //display gameover msg and score
    public void gameover(Graphics g){
        String msg="Game Over";
        int score=(dots-3)*100;
        String scoremsg="score:"+Integer.toString(score);
        Font small=new Font("Helivatica",Font.BOLD,24);
        FontMetrics fontmatrics=getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg,(b_width-fontmatrics.stringWidth(msg))/2,b_height/4);
        g.drawString(scoremsg,(b_width-fontmatrics.stringWidth(scoremsg))/2,2*(b_height)/4);
    }
 @Override
 public void actionPerformed(ActionEvent actionEvent){
        if(ingame){
            checkcollisions();
            checkapple();
            move();
        }

        repaint();
 }
    //make the snake move
    public void move(){
for(int i=dots-1;i>0;i--){
    x[i]=x[i-1];
    y[i]=y[i-1];
}
if(leftdirection){
    x[0]-=dot_size;
}
if(rightdirection){
    x[0]+=dot_size;
}
if(updirection){
    y[0]-=dot_size;
}if(downdirection){
    y[0]+=dot_size;
        }
    }
    //make snake eat food
    public void checkapple(){
        if(apple_x==x[0]&&apple_y==y[0]){
            dots++;
            locateapple();
        }

    }
//implements controls
    private class TAdapter extends KeyAdapter{
        @Override
    public void keyPressed(KeyEvent keyevent){
            int key = keyevent.getKeyCode();
            if(key==KeyEvent.VK_LEFT&&!rightdirection){
                leftdirection=true;
                updirection=false;
                downdirection=false;
            }
            if(key==KeyEvent.VK_RIGHT&&!leftdirection){
                rightdirection=true;
                updirection=false;
                downdirection=false;
            }
            if(key==KeyEvent.VK_UP&&!downdirection){
                updirection=true;
                rightdirection=false;
                leftdirection=false;
            }
            if(key==KeyEvent.VK_DOWN&&!updirection){
                downdirection=true;
                rightdirection=false;
                leftdirection=false;
            }
        }
}


}

