package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MPanel extends JPanel implements KeyListener, ActionListener {
    ImageIcon title = new ImageIcon("title.jpg");
    ImageIcon right = new ImageIcon("right.png");
    ImageIcon left = new ImageIcon("left.png");
    ImageIcon up = new ImageIcon("up.png");
    ImageIcon down = new ImageIcon("down.png");
    ImageIcon body = new ImageIcon("body.png");
    ImageIcon food = new ImageIcon("food.png");


    int len = 3;
    int score = 0;
    boolean isFailed = false;
    int[] snakex = new int[750];
    int[] snakey = new int[750];
    String fx = "R";//R,L,U,D
    boolean isStart = false;
    Timer time = new Timer(100, this);
    int foodx;
    int foody;
    Random random = new Random();


    public MPanel()
    {
        initSnake();
        this.setFocusable(true);
        this.addKeyListener(this);
        time.start();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setBackground(Color.white);

        //打印标题
        title.paintIcon(this,g,25,11);
        g.fillRect(25,75,850,600);
        g.setColor(Color.white);
        g.drawString("len: "+len,750,35 );
        g.drawString("score: "+score,750,50 );


        food.paintIcon(this,g,foodx,foody);


        //打印头的方向
        if(fx == "R")
        {
            right.paintIcon(this,g,snakex[0],snakey[0]);
        }
        else if(fx == "L")
        {
            left.paintIcon(this,g,snakex[0],snakey[0]);
        }else if(fx == "U")
        {
            up.paintIcon(this,g,snakex[0],snakey[0]);
        }else if(fx == "D")
        {
            down.paintIcon(this,g,snakex[0],snakey[0]);
        }


        //打印头的身体
        for(int i=1; i<len; i++)
        {
            body.paintIcon(this,g,snakex[i],snakey[i]);
        }

        if(isStart == false)
        {
            //打印提示信息
            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.BOLD,40));
            g.drawString("Press to Start!",300,300);
        }
        else if(isFailed)
        {
            g.setColor(Color.red);
            g.setFont(new Font("arial", Font.BOLD,40));
            g.drawString("Failed: Press to restart!",200,300);
        }
    }


    public void initSnake()
    {
        len = 3;
        snakex[0] = 100;
        snakey[0] = 100;
        snakex[1] = 75;
        snakey[1] = 100;
        snakex[2] = 50;
        snakey[2] = 100;
        foodx = 25 + 25*random.nextInt(34);
        foody = 75 + 25*random.nextInt(24);
        fx ="R";
        score = 0;
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int Keycode = e.getKeyCode();
        if(Keycode == KeyEvent.VK_SPACE)
        {
            if(isFailed)
            {
                isFailed = false;
                initSnake();
            }
            else
            {
                isStart = !isStart;
            }
            repaint();
        }else if(Keycode == KeyEvent.VK_LEFT)
        {
            fx = "L";
        }else if(Keycode == KeyEvent.VK_RIGHT)
        {
            fx = "R";
        }else if(Keycode == KeyEvent.VK_UP)
        {
            fx = "U";
        }else if(Keycode == KeyEvent.VK_DOWN)
        {
            fx = "D";
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(isStart && !isFailed)
        {
            for(int i = len-1; i>0; i--)
            {
                snakex[i] = snakex[i-1];
                snakey[i] = snakey[i-1];
            }
            if(fx == "R")
            {
                snakex[0] = snakex[0] + 25;
                if(snakex[0] > 850)
                {
                    snakex[0] = 25;
                }
            }else if(fx == "L")
            {
                snakex[0] = snakex[0] - 25;
                if(snakex[0] < 25)
                {
                    snakex[0] = 850;
                }
            }else if(fx == "U")
            {
                snakey[0] = snakey[0] - 25;
                if(snakey[0] < 75)
                {
                    snakey[0] = 650;
                }
            }
            else if(fx == "D")
            {
                snakey[0] = snakey[0] + 25;
                if(snakey[0] > 650)
                {
                    snakey[0] = 75;
                }
            }
            if(foodx == snakex[0] && foody == snakey[0])
            {
                len++;
                score = score + 10;
                foodx = 25 + 25*random.nextInt(34);
                foody = 75 + 25*random.nextInt(24);
            }

            for(int i = 1; i < len; i++)
            {
                if(snakex[0] == snakex[i] && snakey[0] == snakey[i])
                {
                    isFailed = true;
                }
            }
            repaint();
        }
        time.start();
    }
}
