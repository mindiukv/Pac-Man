import javax.swing.*;

import acm.graphics.*;
import acm.program.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.*;
import java.util.List;

public class PacMan extends GraphicsProgram {

    int WIDTH = 500;
    int HEIGHT = 500;
    static int scoreLevel;
    GImage background;
    KeyListener keyLevel1, keyLevel2;
    protected GImage level1, level2, level3, store, instruction, exit, playMusic, stopMusic;
    protected GImage gameName;
    protected static GRect backgroundLab;
    protected static GRect border;
    protected static GImage pacMan;
    protected GImage cherries;
    protected GImage strawberry;
    protected GImage pizza;
    protected static GLabel score;
    protected static GLabel allScore;
    protected static GImage ghost1, ghost2, ghost3, ghost4, ghost5, heart1, heart2, heart3,heart4, portal1, portal2, gun1,gun2, ball;
    protected static GImage storeLabel, exitStore;
    protected GImage buy100, buy350, buy500, buy700, buy1k;
    private boolean buy100Purchased = false;
    private boolean buy350Purchased = false;
    private boolean buy500Purchased = false;
    private boolean buy700Purchased = false;
    private boolean buy1kPurchased = false;
    private boolean buy100Active = false;
    private boolean buy350Active = false;
    private boolean buy500Active = false;
    private boolean buy700Active = false;
    private boolean buy1kActive = false;
    protected int balance = 0;
    protected static GLabel balanceLabel;
    protected static GLine l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17;
    protected static List<GImage> pointList;
    protected static Clip clip;
    protected GOval pill1, pill2, pill3;
    protected static int scoreNumber = 0;
    boolean flag_level2 = false;
    MoveGhost Ghost1,Ghost2,Ghost3,Ghost4,Ghost5;
    protected static List<MoveGhost> ghostList;
    public int hearts = 3;
    protected static boolean powerUpActive = false;
    protected static int powerUpTimer = 0;
    private Timer vulnerabilityTimer;
    private boolean ghostsVulnerable = false;
    int step = 5;
    private boolean flag_level3 = false;
    private int ballState = 1;
    private int numberLevel2 = 1;


    TextArea s;
    boolean isSafety = false;

    public void run() {
        this.setSize(WIDTH, HEIGHT);
        startProgram();
    }

    public void startProgram() {
        play("src\\Music\\sound.wav");

        background = new GImage("src\\gameBackground.jpg");
        background.setSize(getWidth(), getHeight());
        add(background);

        gameName = new GImage("src\\PacMan title.png");
        gameName.setLocation(getWidth() / 2 - gameName.getWidth() / 2, 30);
        add(gameName);

        level1 = new GImage("src\\level1But.png");
        level1.setLocation(getWidth()/2-level1.getWidth()/2, 125);
        add(level1);

        level2 = new GImage("src\\level2But.png");
        level2.setLocation(getWidth()/2-level2.getWidth()/2, 175);
        add(level2);

        level3 = new GImage("src\\level3But.png");
        level3.setLocation(getWidth()/2-level3.getWidth()/2, 225);
        add(level3);

        store = new GImage("src\\storeBut.png");
        store.setLocation(getWidth()/2-store.getWidth()/2, 300);
        add(store);

        instruction = new GImage("src\\info.png");
        instruction.setLocation(450, 20);
        instruction.scale(0.6);
        add(instruction);

        stopMusic = new GImage("src\\musicOn.png");
        stopMusic.setLocation(getWidth()/2-stopMusic.getWidth()/2, 400);
        add(stopMusic);

        playMusic = new GImage("src\\musicOff.png");
        playMusic.setLocation(getWidth()/2-playMusic.getWidth()/2, 400);

        allScore = new GLabel("Score: " + scoreNumber);
        allScore.setColor(Color.WHITE);
        allScore.setFont("Rubik-32");
        allScore.setLocation(getWidth() / 2 - allScore.getWidth() / 2, 380);

        GRect rect2 = new GRect(getWidth() / 2 - allScore.getWidth() / 2, 355, allScore.getWidth(), allScore.getHeight() - 10);
        rect2.setColor(Color.decode("#109FFE"));
        rect2.setFillColor(Color.decode("#D091DB"));
        rect2.setFilled(true);
        add(rect2);
        add(allScore);

        exit = new GImage("src\\exitBut.png");
        exit.setSize(120, 30);
        exit.setLocation(0, getHeight()-exit.getHeight());

        stopMusic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isFocusable()){
                    clip.stop();
                    remove(stopMusic);
                    add(playMusic);
                }
            }
        });

        playMusic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isFocusable()){
                    play("src\\Music\\sound.wav");
                    remove(playMusic);
                    add(stopMusic);
                }
            }
        });

        instruction.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                remove(instruction);
                removeAll();
                remove(level1);
                remove(level2);
                remove(level3);
                remove(store);
                remove(stopMusic);

                GImage key = new GImage("src\\key.png");
                key.setLocation(0, 10);
                add(key);

                GImage point1 = new GImage("src\\point.gif");
                point1.setSize(15, 15);
                point1.setLocation(30, key.getHeight()+30);
                GImage point2 = new GImage("src\\point.gif");
                point2.setSize(15, 15);
                point2.setLocation(55, key.getHeight()+30);
                GImage point3 = new GImage("src\\point.gif");
                point3.setSize(15, 15);
                point3.setLocation(80, key.getHeight()+30);
                add(point1);
                add(point2);
                add(point3);

                ghost1 = new GImage("src\\ghost.gif");
                ghost1.setLocation(25, 145);
                add(ghost1);

                ghost2 = new GImage("src\\ghost.gif");
                ghost2.setLocation(50, 145);
                add(ghost2);

                ghost3 = new GImage("src\\ghost.gif");
                ghost3.setLocation(75, 145);
                add(ghost3);

                heart1 = new GImage("src\\heart.png");
                heart1.setLocation(25,185);
                heart1.setSize(20,20);
                add(heart1);

                heart2 = new GImage("src\\heart.png");
                heart2.setLocation(50,185);
                heart2.setSize(20,20);
                add(heart2);

                heart3 = new GImage("src\\heart.png");
                heart3.setLocation(75,185);
                heart3.setSize(20,20);
                add(heart3);

                pill1 = new GOval(25, 230, 15, 15);
                pill1.setColor(Color.red);
                pill1.setFilled(true);

                pill2 = new GOval(50, 230, 15, 15);
                pill2.setColor(Color.red);
                pill2.setFilled(true);

                pill3 = new GOval(75, 230, 15, 15);
                pill3.setColor(Color.red);
                pill3.setFilled(true);

                add(pill1);
                add(pill2);
                add(pill3);

                portal1 = new GImage("src\\portal-removebg-preview.png");
                portal1.setLocation(30,265);
                portal1.scale(0.065);
                add(portal1);

                portal2 = new GImage("src\\portal-removebg-preview.png");
                portal2.setLocation(60, 265);
                portal2.scale(0.065);
                add(portal2);

                cherries = new GImage("src\\cherries.png");
                cherries.setLocation(25, 315);
                cherries.scale(1.5);
                add(cherries);

                strawberry = new GImage("src\\strawberry.png");
                strawberry.setLocation(50, 315);
                strawberry.scale(1.5);
                add(strawberry);

                pizza = new GImage("src\\pizza.png");
                pizza.setLocation(75, 310);
                pizza.scale(1.5);
                add(pizza);

                GImage gh1 = new GImage("store\\JayGhost.png");
                gh1.scale(1.4);
                gh1.setLocation(25, 375);
                add(gh1);

                GImage gh2 = new GImage("store\\FryGhost.png");
                gh2.scale(1.4);
                gh2.setLocation(50, 372);
                add(gh2);

                GImage gh3 = new GImage("store\\SplinterGhost.png");
                gh3.scale(1.4);
                gh3.setLocation(75, 375);
                add(gh3);

                s = new TextArea("\n\n - Керування Пакменом відбувається за допомогою \nзображених кнопок клавіатури. " +
                        "\nСтрілки вказують напрям руху Пакмена. \n \n \n" +
                        "\n- Завданням Пакмена є зірбати усі поінти в лабіринті." +
                        "\n \n -Руху Пакмена заважають привиди. \nПри зіткненні з ними він втрачає одне життя. " +
                        "\n \n -Всього у Пакмена є 3 життя. \nПри втраті усіх життів користувач програє." +
                        "\n \n -На другому та третьому рівнях з'являються таблетки, з'ївши\nяку Пакмен стає невразливим до привидів на 30 секунд. " +
                        "\n \n -На третьому рівні з'являється портал, коли Пакмен \nзаходить в одне кільце порталу, то виходить з іншого кільця." +
                        "\n \n -На третьому рівні з'являються бонуси, з'ївши які\nПакмен стає невразливим до пострілів гармати на 20 секунд." +
                        "\n \n \n -В грі є можливість змінювати стандартний вигляд\nпривидів, купуючи їх в магазині за зароблені під час гри\nпоінти.");
                s.setLocation(120,0);
                s.setSize(getWidth()-120,getHeight());
                add(s);

                add(exit);
                exit.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        clip.stop();
                        remove(exit);
                        remove(s);
                        removeAll();
                        startProgram();
                    }
                });
            }
        });

        store.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isFocusable()){
                    removeAll();
                    remove(playMusic);
                    remove(stopMusic);
                    remove(level1);
                    remove(level2);
                    remove(level3);
                    remove(store);
                    remove(instruction);
                    drawStore();
                }
            }
        });

        level1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isFocusable()) {
                    clip.stop();
                    //play("src\\Music\\sound.wav");

                    hearts = 3;
                    flag_level2 = true;
                    step = firstSpeed;
                    removeAll();
                    remove(playMusic);
                    remove(stopMusic);
                    remove(level1);
                    remove(level2);
                    remove(level3);
                    remove(store);
                    remove(instruction);
                    drawLabyrinthLevel1();
                    removeKeyListener(keyLevel1);
                    scoreLevel = 0;
                    numberLevel2 = 1;

                    addKeyListeners(keyLevel2 = new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {}
                        @Override
                        public void keyPressed(KeyEvent a) {

                            if(numberLevel2 == 1) {
                                ghostMoveLevel2();
                            }

                            int keyCode = a.getKeyCode();
                            double x = pacMan.getX();
                            double y = pacMan.getY();

                            if (pointList.size() == 0) {
                                clip.stop();
                                winLevel();
                                return;
                            }

                            if(hearts == 0) {
                                clip.stop();
                                looseLevel();
                                return;
                            }

                            if (keyCode == KeyEvent.VK_UP) {
                                remove(pacMan);
                                pacMan = new GImage("src\\up.gif");
                                pacMan.setLocation(x, y);
                                add(pacMan);

                                if (y - step > border.getY() && !checkCollisionLevel(pacMan)) {
                                    pacMan.move(0, -step);
                                } else { play("src\\Music\\udar.wav"); }
                                if(checkCollisionLevel(pacMan))
                                {
                                    play("src\\Music\\udar.wav");
                                    pacMan.move(0,step);
                                }

                            } else if (keyCode == KeyEvent.VK_DOWN) {
                                remove(pacMan);
                                pacMan = new GImage("src\\down.gif");
                                pacMan.setLocation(x, y);
                                add(pacMan);

                                if (y + step + pacMan.getHeight() < border.getY() + border.getHeight() && !checkCollisionLevel(pacMan)) {
                                    pacMan.move(0, step);
                                } else { play("src\\Music\\udar.wav"); }
                                if(checkCollisionLevel(pacMan)){
                                    play("src\\Music\\udar.wav");
                                    pacMan.move(0,-step);
                                }

                            } else if (keyCode == KeyEvent.VK_RIGHT) {
                                remove(pacMan);
                                pacMan = new GImage("src\\right.gif");
                                pacMan.setLocation(x, y);
                                add(pacMan);

                                if (x + step + pacMan.getWidth() < border.getX() + border.getWidth()&& !checkCollisionLevel(pacMan)) {
                                    pacMan.move(step, 0);
                                } else { play("src\\Music\\udar.wav"); }
                                if (checkCollisionLevel(pacMan))
                                {
                                    play("src\\Music\\udar.wav");
                                    pacMan.move(-step,0);
                                }

                            } else if (keyCode == KeyEvent.VK_LEFT) {
                                remove(pacMan);
                                pacMan = new GImage("src\\left.gif");
                                pacMan.setLocation(x, y);
                                add(pacMan);

                                if (x - step > border.getX() && !checkCollisionLevel(pacMan)) {
                                    pacMan.move(-step, 0);
                                } else { play("src\\Music\\udar.wav"); }
                                if(checkCollisionLevel(pacMan))
                                {
                                    play("src\\Music\\udar.wav");
                                    pacMan.move(step,0);
                                }
                            }
                            try{
                            for (/*GOval*/ GImage point : pointList) {
                                if (pacMan.getBounds().intersects(point.getBounds())) {
                                    remove(point);
                                    pointList.remove(point);
                                    scoreNumber++;
                                    scoreLevel++;
                                    score.setLabel("Score: " + scoreLevel);
                                    play("src\\Music\\collision.wav");
                                }
                            }
                            }catch(ConcurrentModificationException ex){}

                            for (MoveGhost point : ghostList) {
                                if (pacMan.getBounds().intersects(point.getGhost().getBounds())) {

                                    hearts--;

                                    remove(pacMan);
                                    pacMan = new GImage("src\\pacman.png");
                                    pacMan.setLocation(12,42);
                                    add(pacMan);
                                    play("src\\Music\\udar.wav");

                                    if(hearts == 2) {
                                        remove(heart1);
                                    }
                                    else if(hearts == 1) {
                                        remove(heart2);
                                    }
                                    else if(hearts == 0){
                                        remove(heart3);
                                    }
                                }
                                if(point.getGhost().getX()<=10) {
                                    point.set();
                                }
                                if(point.getGhost().getX()>getWidth()-point.getGhost().getWidth()-35) {
                                    point.set();
                                }
                                if(point.getGhost().getY()<=40) {
                                    point.set();
                                }
                                if (point.getGhost().getY()>getHeight()-point.getGhost().getHeight()-110) {
                                    point.set();
                                }
                                if(checkCollisionLevel(point.getGhost())) {
                                    point.set();
                                }
                            }
                        }

                        @Override
                        public void keyReleased(KeyEvent e) {}
                    });

                }
            }
        });

        level2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isFocusable()) {
                    clip.stop();
                    //play("src\\Music\\sound.wav");

                    step = firstSpeed;
                    hearts = 3;
                    flag_level2 = true;
                    removeAll();
                    remove(playMusic);
                    remove(stopMusic);
                    remove(level1);
                    remove(level2);
                    remove(level3);
                    remove(store);
                    remove(instruction);
                    numberLevel2 = 1;
                    drawLabyrinthLevel2();
                    removeKeyListener(keyLevel1);
                    scoreLevel = 0;
                    pill1Active = true;
                    pill2Active = true;
                    pill3Active = true;

                    addKeyListeners(keyLevel2 = new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {}
                        @Override
                        public void keyPressed(KeyEvent a) {

                            if(hearts == 0) {
                                clip.stop();
                                looseLevel();
                                return;
                            }

                            if (pointList.size() == 0) {
                                clip.stop();
                                winLevel();
                                return;
                            }

                            if(numberLevel2 == 1)
                            {
                                ghostMoveLevel2();
                            }

                            step = firstSpeed;

                            int keyCode = a.getKeyCode();
                            double x = pacMan.getX();
                            double y = pacMan.getY();

                            if (keyCode == KeyEvent.VK_UP) {
                                remove(pacMan);
                                pacMan = new GImage("src\\up.gif");
                                pacMan.setLocation(x, y);
                                add(pacMan);

                                if (y - step > border.getY() && !checkCollisionLevel(pacMan)) {
                                    pacMan.move(0, -step);
                                } else { play("src\\Music\\udar.wav"); }
                                if(checkCollisionLevel(pacMan))
                                {
                                    play("src\\Music\\udar.wav");
                                    pacMan.move(0,step);
                                }

                            } else if (keyCode == KeyEvent.VK_DOWN) {
                                remove(pacMan);
                                pacMan = new GImage("src\\down.gif");
                                pacMan.setLocation(x, y);
                                add(pacMan);

                                if (y + step + pacMan.getHeight() < border.getY() + border.getHeight() && !checkCollisionLevel(pacMan)) {
                                    pacMan.move(0, step);
                                } else { play("src\\Music\\udar.wav"); }
                                if(checkCollisionLevel(pacMan)){
                                    play("src\\Music\\udar.wav");
                                    pacMan.move(0,-step);
                                }

                            } else if (keyCode == KeyEvent.VK_RIGHT) {
                                remove(pacMan);
                                pacMan = new GImage("src\\right.gif");
                                pacMan.setLocation(x, y);
                                add(pacMan);

                                if (x + step + pacMan.getWidth() < border.getX() + border.getWidth()&& !checkCollisionLevel(pacMan)) {
                                    pacMan.move(step, 0);
                                } else { play("src\\Music\\udar.wav"); }
                                if (checkCollisionLevel(pacMan))
                                {
                                    play("src\\Music\\udar.wav");
                                    pacMan.move(-step,0);
                                }

                            } else if (keyCode == KeyEvent.VK_LEFT) {
                                remove(pacMan);
                                pacMan = new GImage("src\\left.gif");
                                pacMan.setLocation(x, y);
                                add(pacMan);

                                if (x - step > border.getX() && !checkCollisionLevel(pacMan)) {
                                    pacMan.move(-step, 0);
                                } else { play("src\\Music\\udar.wav"); }
                                if(checkCollisionLevel(pacMan))
                                {
                                    play("src\\Music\\udar.wav");
                                    pacMan.move(step,0);
                                }
                            }
                            try {
                                for (GImage point : pointList) {
                                    if (pacMan.getBounds().intersects(point.getBounds())) {
                                        remove(point);
                                        pointList.remove(point);
                                        scoreNumber++;
                                        scoreLevel++;
                                        score.setLabel("Score: " + scoreLevel);
                                        play("src\\Music\\collision.wav");

                                    }
                                }
                            }catch(ConcurrentModificationException ex){}


                            if (pacMan.getBounds().intersects(pill1.getBounds())&&pill1Active) {
                                remove(pill1);
                                pill1Active=false;
                                powerUpActive = true;
                                powerUpTimer = 15000;
                                startVulnerabilityTimer();
                            }

                            if (pacMan.getBounds().intersects(pill2.getBounds())&&pill2Active) {
                                remove(pill2);
                                pill2Active=false;
                                powerUpActive = true;
                                powerUpTimer = 15000;
                                startVulnerabilityTimer();
                            }

                            if (pacMan.getBounds().intersects(pill3.getBounds())&&pill3Active) {
                                remove(pill3);
                                pill3Active=false;
                                powerUpActive = true;
                                powerUpTimer = 15000;
                                startVulnerabilityTimer();
                            }

                            if (powerUpActive) {
                                powerUpTimer-=50;
                                if (powerUpTimer <= 0) {
                                    powerUpActive = false;
                                }
                            }
                            for (MoveGhost point : ghostList) {
                                if (pacMan.getBounds().intersects(point.getGhost().getBounds())) {
                                    if (powerUpActive&&ghostsVulnerable) {
                                        remove(point.getGhost());
                                        ghostList.remove(point);
                                        scoreNumber+=200;
                                        scoreLevel += 200;
                                        score.setLabel("Score: " + scoreLevel);
                                        play("src\\ghost_eaten.wav");
                                        break;
                                    } else {
                                        hearts--;
                                        remove(pacMan);
                                        pacMan = new GImage("src\\pacman.png");
                                        pacMan.setLocation(12, 42);
                                        add(pacMan);
                                        play("src\\Music\\udar.wav");

                                        if (hearts == 2) {
                                            remove(heart1);
                                        } else if (hearts == 1) {
                                            remove(heart2);
                                        } else if (hearts == 0) {
                                            remove(heart3);
                                        }
                                    }
                                }

                            }
                        }
                        @Override
                        public void keyReleased(KeyEvent e) {}
                    });

                }

            }
        });
        level3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isFocusable()) {
                    clip.stop();
                    //play("src\\Music\\sound.wav");

                    step = firstSpeed;
                    hearts = 3;
                    flag_level2 = true;
                    removeAll();
                    remove(playMusic);
                    remove(stopMusic);
                    remove(level1);
                    remove(level2);
                    remove(level3);
                    remove(store);
                    remove(instruction);
                    ballState = 1;
                    drawLabyrinthLevel3();
                    removeKeyListener(keyLevel1);
                    scoreLevel = 0;
                    flag_level3 = true;
                    pill1Active=true;
                    pill2Active=true;
                    pill3Active=true;

                    addKeyListeners(keyLevel2 = new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {}
                        @Override
                        public void keyPressed(KeyEvent a) {

                            if(hearts == 0) {
                                remove(ball);
                                looseLevel();
                                return;
                            }

                            if(ballState==1) {
                                ballState1();
                                startBullet();
                            }

                            int keyCode = a.getKeyCode();
                            double x = pacMan.getX();
                            double y = pacMan.getY();

                            if (pointList.size() == 0) {
                                winLevel();
                                flag_level3 = false;
                                return;
                            }

                            if (keyCode == KeyEvent.VK_UP) {
                                remove(pacMan);
                                pacMan = new GImage("src\\up.gif");
                                pacMan.setLocation(x, y);
                                add(pacMan);

                                if (y - step > border.getY() && !checkCollisionLevel(pacMan)) {
                                    pacMan.move(0, -step);
                                } else { play("src\\Music\\udar.wav"); }
                                if(checkCollisionLevel(pacMan))
                                {
                                    play("src\\Music\\udar.wav");
                                    pacMan.move(0,step);
                                }

                            } else if (keyCode == KeyEvent.VK_DOWN) {
                                remove(pacMan);
                                pacMan = new GImage("src\\down.gif");
                                pacMan.setLocation(x, y);
                                add(pacMan);

                                if (y + step + pacMan.getHeight() < border.getY() + border.getHeight() && !checkCollisionLevel(pacMan)) {
                                    pacMan.move(0, step);
                                } else { play("src\\Music\\udar.wav"); }
                                if(checkCollisionLevel(pacMan)){
                                    play("src\\Music\\udar.wav");
                                    pacMan.move(0,-step);
                                }

                            } else if (keyCode == KeyEvent.VK_RIGHT) {
                                remove(pacMan);
                                pacMan = new GImage("src\\right.gif");
                                pacMan.setLocation(x, y);
                                add(pacMan);

                                if (x + step + pacMan.getWidth() < border.getX() + border.getWidth()&& !checkCollisionLevel(pacMan)) {
                                    pacMan.move(step, 0);
                                } else { play("src\\Music\\udar.wav"); }
                                if (checkCollisionLevel(pacMan))
                                {
                                    play("src\\Music\\udar.wav");
                                    pacMan.move(-step,0);
                                }

                            } else if (keyCode == KeyEvent.VK_LEFT) {
                                remove(pacMan);
                                pacMan = new GImage("src\\left.gif");
                                pacMan.setLocation(x, y);
                                add(pacMan);

                                if (x - step > border.getX() && !checkCollisionLevel(pacMan)) {
                                    pacMan.move(-step, 0);
                                } else { play("src\\Music\\udar.wav"); }
                                if(checkCollisionLevel(pacMan))
                                {
                                    play("src\\Music\\udar.wav");
                                    pacMan.move(step,0);
                                }
                            }
                            try {
                                for (/*GOval*/ GImage point : pointList) {
                                    if (pacMan.getBounds().intersects(point.getBounds())) {
                                        remove(point);
                                        pointList.remove(point);
                                        scoreNumber++;
                                        scoreLevel++;
                                        score.setLabel("Score: " + scoreLevel);
                                        play("src\\Music\\collision.wav");

                                    }
                                }
                            }catch(ConcurrentModificationException ex){}

                            if(pacMan.getBounds().intersects(cherries.getBounds())){
                                safeActive = true;
                                safeBallTimer = 20000;
                                scoreNumber += 100;
                                scoreLevel += 100;
                                remove(cherries);
                                startSafeTimer();
                            }

                            if(pacMan.getBounds().intersects(strawberry.getBounds())){
                                safeActive = true;
                                safeBallTimer = 20000;
                                scoreNumber += 100;
                                scoreLevel += 100;
                                remove(strawberry);
                                startSafeTimer();
                            }


                            if(pacMan.getBounds().intersects(pizza.getBounds())){
                                safeActive = true;
                                safeBallTimer = 20000;
                                scoreNumber += 100;
                                scoreLevel += 100;
                                remove(pizza);
                                startSafeTimer();
                            }


                            if (pacMan.getBounds().intersects(pill1.getBounds())&&pill1Active) {
                                remove(pill1);
                                pill1Active=false;
                                powerUpActive = true;
                                powerUpTimer = 15000;
                                startVulnerabilityTimer();
                            }

                            if (pacMan.getBounds().intersects(pill2.getBounds())&&pill2Active) {
                                remove(pill2);
                                pill2Active=false;
                                powerUpActive = true;
                                powerUpTimer = 15000;
                                startVulnerabilityTimer();
                            }

                            if (pacMan.getBounds().intersects(pill3.getBounds())&&pill3Active) {
                                remove(pill3);
                                pill3Active=false;
                                powerUpActive = true;
                                powerUpTimer = 15000;
                                startVulnerabilityTimer();
                            }

                            if (powerUpActive) {
                                powerUpTimer-=50;
                                if (powerUpTimer <= 0) {
                                    powerUpActive = false;
                                }
                            }

                            if (safeActive) {
                                safeBallTimer-=50;
                                if (safeBallTimer <= 0) {
                                    safeActive = false;
                                }
                            }

                            if(pacMan.getBounds().intersects(portal1.getBounds()))
                            {

                                pacMan.move(portal2.getX() - pacMan.getX()+portal2.getWidth()*1.7,portal2.getY()-pacMan.getY()+portal2.getHeight()/2);

                            }
                            if(pacMan.getBounds().intersects(portal2.getBounds()))
                            {

                                pacMan.move(-(portal2.getX()-portal1.getX()+pacMan.getWidth()*2.5),-(portal2.getY()-portal1.getY()+pacMan.getHeight()/2));
                            }
                            if(pacMan.getBounds().intersects(ball.getBounds()) && !isSafety){
                                ballState = 1;
                                remove(pacMan);
                                pacMan = new GImage("src\\pacman.png");
                                pacMan.setLocation(12, 42);
                                add(pacMan);
                                play("src\\Music\\bax.wav");
                                hearts--;
                                if (hearts == 2) {
                                    remove(heart1);
                                } else if (hearts == 1) {
                                    remove(heart2);
                                } else if (hearts == 0) {
                                    remove(heart3);
                                }
                            }
                        }
                        @Override
                        public void keyReleased(KeyEvent e) {}
                    });

                }

            }
        });

    }


    boolean safeActive = false;
    int safeBallTimer = 0;
    Timer safeTimer;
    private void startSafeTimer(){
        isSafety = true;

        safeTimer = new Timer(20000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopSafeTimer();
            }
        });
        safeTimer.setRepeats(false);
        safeTimer.start();
    }

    private void stopSafeTimer(){
        isSafety = false;
    }

    private boolean pill1Active = true;
    private boolean pill2Active = true;
    private boolean pill3Active = true;

    private int firstSpeed  = step;

    private void startVulnerabilityTimer() {
        ghostsVulnerable = true;

        ghost1.setImage("src\\vulnerableGhost.png");
        ghost2.setImage("src\\vulnerableGhost.png");
        ghost3.setImage("src\\vulnerableGhost.png");
        ghost4.setImage("src\\vulnerableGhost.png");
        ghost5.setImage("src\\vulnerableGhost.png");
        ghost1.scale(0.065);
        ghost2.scale(0.065);
        ghost3.scale(0.065);
        ghost4.scale(0.065);
        ghost5.scale(0.065);


        vulnerabilityTimer = new Timer(15000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopVulnerabilityTimer();
            }
        });
        vulnerabilityTimer.setRepeats(false);
        vulnerabilityTimer.start();
    }
    public void ghostMoveLevel2()
    {
        if(flag_level2)
        {
            if(hearts == 0) {
                looseLevel();
                flag_level2 = false;
                return;
            }
            for(MoveGhost point: ghostList) {
                if (point.getGhost().getX() < 10) {
                    point.getGhost().move(point.getSpeed() + 3, 0);
                    point.set();
                }
                if (point.getGhost().getX() + point.getGhost().getWidth() > 475) {
                    point.getGhost().move(-point.getSpeed() - 3, 0);
                    point.set();
                }
                if (point.getGhost().getY() < 40) {
                    point.getGhost().move(0, point.getSpeed() + 3);
                    point.set();
                }
                if (point.getGhost().getY() + point.getGhost().getHeight() > 430) {
                    point.getGhost().move(0, -point.getSpeed() - 3);
                    point.set();
                }
                if (checkCollisionLevel(point.getGhost())) {
                    point.set();
                }
                if (pacMan.getBounds().intersects(point.getGhost().getBounds())) {
                    if (powerUpActive && ghostsVulnerable) {
                        remove(point.getGhost());
                        ghostList.remove(point);
                        scoreNumber += 200;
                        scoreLevel += 200;
                        score.setLabel("Score: " + scoreLevel);
                        play("src\\ghost_eaten.wav");
                        break;
                    } else {
                        hearts--;
                        remove(pacMan);
                        pacMan = new GImage("src\\pacman.png");
                        pacMan.setLocation(12, 42);
                        add(pacMan);
                        play("src\\Music\\udar.wav");

                        if (hearts == 2) {
                            remove(heart1);
                        } else if (hearts == 1) {
                            remove(heart2);
                        } else if (hearts == 0) {
                            remove(heart3);
                        }
                    }
                }
            }
            ghostMove();
            Timer ballTimer = new Timer(25, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ghostMoveLevel2_1();
                }
            });
            ballTimer.setRepeats(false);
            ballTimer.start();
            numberLevel2++;
        }
    }
    public void ghostMoveLevel2_1()
    {
        if(flag_level2)
        {
            if(hearts == 0) {
                looseLevel();
                flag_level2 = false;
                remove(pacMan);
                return;
            }
            for(MoveGhost point: ghostList) {
                if (point.getGhost().getX() < 10) {
                    point.getGhost().move(point.getSpeed() + 3, 0);
                    point.set();
                }
                if (point.getGhost().getX() + point.getGhost().getWidth() > 475) {
                    point.getGhost().move(-point.getSpeed() - 3, 0);
                    point.set();
                }
                if (point.getGhost().getY() < 40) {
                    point.getGhost().move(0, point.getSpeed() + 3);
                    point.set();
                }
                if (point.getGhost().getY() + point.getGhost().getHeight() > 430) {
                    point.getGhost().move(0, -point.getSpeed() - 3);
                    point.set();
                }
                if (checkCollisionLevel(point.getGhost())) {
                    point.set();
                }
                if (pacMan.getBounds().intersects(point.getGhost().getBounds())) {
                    if (powerUpActive && ghostsVulnerable) {
                        remove(point.getGhost());
                        ghostList.remove(point);
                        scoreNumber += 200;
                        scoreLevel += 200;
                        score.setLabel("Score: " + scoreLevel);
                        play("src\\ghost_eaten.wav");
                        break;
                    } else {
                        hearts--;
                        remove(pacMan);
                        pacMan = new GImage("src\\pacman.png");
                        pacMan.setLocation(12, 42);
                        add(pacMan);
                        play("src\\Music\\udar.wav");

                        if (hearts == 2) {
                            remove(heart1);
                        } else if (hearts == 1) {
                            remove(heart2);
                        } else if (hearts == 0) {
                            remove(heart3);
                        }
                    }
                }
            }
            ghostMove();
            Timer ballTimer = new Timer(25, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ghostMoveLevel2();
                }
            });
            ballTimer.setRepeats(false);
            ballTimer.start();
        }
    }
   public void startBullet()
   {
       if(flag_level3)
       {
           if(hearts == 0) {
               looseLevel();
               flag_level3 = false;
               remove(pacMan);
               remove(ball);
               return;
           }
           for(MoveGhost point: ghostList) {
               if (point.getGhost().getX() < 10) {
                   point.getGhost().move(point.getSpeed() + 3, 0);
                   point.set();
               }
               if (point.getGhost().getX() + point.getGhost().getWidth() > 475) {
                   point.getGhost().move(-point.getSpeed() - 3, 0);
                   point.set();
               }
               if (point.getGhost().getY() < 40) {
                   point.getGhost().move(0, point.getSpeed() + 3);
                   point.set();
               }
               if (point.getGhost().getY() + point.getGhost().getHeight() > 430) {
                   point.getGhost().move(0, -point.getSpeed() - 3);
                   point.set();
               }
               if (checkCollisionLevel(point.getGhost())) {
                   point.set();
               }
               if (pacMan.getBounds().intersects(point.getGhost().getBounds())) {
                   if (powerUpActive && ghostsVulnerable) {
                       remove(point.getGhost());
                       ghostList.remove(point);
                       scoreNumber += 200;
                       scoreLevel += 200;
                       score.setLabel("Score: " + scoreLevel);
                       play("src\\ghost_eaten.wav");
                       break;
                   } else {
                       hearts--;
                       remove(pacMan);
                       pacMan = new GImage("src\\pacman.png");
                       pacMan.setLocation(12, 42);
                       add(pacMan);
                       play("src\\Music\\udar.wav");

                       if (hearts == 2) {
                           remove(heart1);
                       } else if (hearts == 1) {
                           remove(heart2);
                       } else if (hearts == 0) {
                           remove(heart3);
                       }
                   }
               }
           }
           ghostMoveLevel3();
           Timer ballTimer = new Timer(25, new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   startBullet1();
               }
           });
           ballTimer.setRepeats(false);
           ballTimer.start();


       }
   }
    public void startBullet1()
    {
        if(flag_level3)
        {
            ghostMoveLevel3();
            Timer ballTimer = new Timer(25, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startBullet();
                }
            });
            ballTimer.setRepeats(false);
            ballTimer.start();

        }
    }

   private void ballState1()
   {
       if(flag_level3)
       {
           remove(ball);
           ball = new GImage("src\\ballState1.png");
           ball.setLocation(435-ball.getWidth()  , 410);
           ball.scale(0.7);
           add(ball);
           ballState++;
           Timer ballTimer = new Timer(200, new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   ballState2();
               }
           });
           ballTimer.setRepeats(false);
           ballTimer.start();
           ballState++;
       }

   }
    private void ballState2()
    {
        remove(ball);
        ball = new GImage("src\\ballState2.png");
        ball.setLocation(435-ball.getWidth()  -30, 400);
        ball.scale(0.7);
        add(ball);
        ballState++;
        Timer ballTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ballState3();
            }
        });
        ballTimer.setRepeats(false);
        ballTimer.start();

    }
    private void ballState3()
    {
        remove(ball);
        ball = new GImage("src\\ballState3.png");
        ball.setLocation(435-ball.getWidth()  -30 - 30, 400);
        ball.scale(0.7);
        add(ball);
        ballState++;
        Timer ballTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ballState4();
            }
        });
        ballTimer.setRepeats(false);
        ballTimer.start();

    }
    private void ballState4()
    {
        remove(ball);
        ball = new GImage("src\\ballState4.png");
        ball.setLocation(435-ball.getWidth()  -30 - 30 -30, 400);
        ball.scale(0.7);
        add(ball);
        ballState++;
        Timer ballTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ballState5();
            }
        });
        ballTimer.setRepeats(false);
        ballTimer.start();

    }
    private void ballState5()
    {
        remove(ball);
        ball = new GImage("src\\ballState5.png");
        ball.setLocation(435-ball.getWidth()  -30 - 30 -30 -30, 400);
        ball.scale(0.7);
        add(ball);
        ballState++;
        Timer ballTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ballState1();
            }
        });
        ballTimer.setRepeats(false);
        ballTimer.start();

    }

    private void stopVulnerabilityTimer() {
        ghostsVulnerable = false;

        /*ghost1.setImage("src\\ghost.gif");
        ghost2.setImage("src\\ghost.gif");
        ghost3.setImage("src\\ghost.gif");
        ghost4.setImage("src\\ghost.gif");
        ghost5.setImage("src\\ghost.gif");*/

        if (buy100Purchased && buy100Active) {
            ghost1.setImage("src\\store\\blueGhost.png");
            ghost1.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost1.setImage("src\\store\\prideGhost1.png");
            ghost1.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost1.setImage("src\\store\\DonatelloGhost.png");
            ghost1.scale(1.5);
        }
        else if (buy700Purchased && buy700Active) {
            ghost1.setImage("src\\store\\ZaneGhost.png");
            ghost1.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost1.setImage("src\\store\\BenderGhost.png");
            ghost1.scale(1.5);
        }

        if (buy100Purchased && buy100Active) {
            ghost2.setImage("src\\store\\redGhost.png");
            ghost2.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost2.setImage("src\\store\\prideGhost2.png");
            ghost2.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost2.setImage("src\\store\\LeonardoGhost.png");
            ghost2.scale(1.5);
        }
        else if (buy700Purchased && buy700Active) {
            ghost2.setImage("src\\store\\JayGhost.png");
            ghost2.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost2.setImage("src\\store\\FryGhost.png");
            ghost2.scale(1.5);
        }

        if (buy100Purchased && buy100Active) {
            ghost3.setImage("src\\store\\pinkGhost.png");
            ghost3.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost3.setImage("src\\store\\prideGhost3.png");
            ghost3.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost3.setImage("src\\store\\MichelangeloGhost.png");
            ghost3.scale(1.5);
        }
        else if (buy700Purchased && buy700Active) {
            ghost3.setImage("src\\store\\KaiGhost.png");
            ghost3.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost3.setImage("src\\store\\LeelaGhost.png");
            ghost3.scale(1.5);
        }

        if (buy100Purchased && buy100Active) {
            ghost4.setImage("src\\store\\orangeGhost.png");
            ghost4.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost4.setImage("src\\store\\prideGhost4.png");
            ghost4.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost4.setImage("src\\store\\SplinterGhost.png");
            ghost4.scale(1.5);
        }
        else if (buy700Purchased && buy700Active) {
            ghost4.setImage("src\\store\\LloydGhost.png");
            ghost4.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost4.setImage("src\\store\\AmyGhost.png");
            ghost4.scale(1.5);
        }

        if (buy100Purchased && buy100Active) {
            ghost5.setImage("src\\store\\greenGhost.png");
            ghost5.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost5.setImage("src\\store\\prideGhost5.png");
            ghost5.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost5.setImage("src\\store\\ShredderGhost.png");
            ghost5.scale(1.6);
        }
        else if (buy700Purchased && buy700Active) {
            ghost5.setImage("src\\store\\WuGhost.png");
            ghost5.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost5.setImage("src\\store\\OctopusGhost.png");
            ghost5.scale(1.5);
        }

        else {
            ghost1.setImage("src\\ghost.gif");
            ghost2.setImage("src\\ghost.gif");
            ghost3.setImage("src\\ghost.gif");
            ghost4.setImage("src\\ghost.gif");
            ghost5.setImage("src\\ghost.gif");
        }

    }

    public void drawStore() {
        addMouseListeners();
        remove(store);

        GImage storeScreen = new GImage("src\\screen1.jpg");

        storeScreen.setLocation(0, 0);
        add(storeScreen);

        storeLabel= new GImage("src\\store\\storeLabel.png");
        storeLabel.setLocation(192, 25);
        storeLabel.scale(2.15);
        add(storeLabel);

        exitStore=new GImage("src\\exitBut.png");
        exitStore.setLocation(20, 25);
        exitStore.scale(0.7);
        add(exitStore);

        GImage ordinarySet = new GImage("src\\store\\OrdinaryGhostsSet.png");
        ordinarySet.setLocation(20, 94);
        ordinarySet.scale(0.16);
        add(ordinarySet);

        buy100 = new GImage("src\\store\\buy100.png");
        buy100.setLocation(54, 187);
        buy100.scale(2.3);
        add(buy100);

        GImage prideMonthSet = new GImage("src\\store\\PrideGhostsSet.png");
        prideMonthSet.setLocation(182, 94);
        prideMonthSet.scale(0.16);
        add(prideMonthSet);

        buy350 = new GImage("src\\store\\buy350.png");
        buy350.setLocation(216, 187);
        buy350.scale(2.3);
        add(buy350);

        GImage ninjaTurtlesSet = new GImage("src\\store\\NinjaTurtlesSet.png");
        ninjaTurtlesSet.setLocation(340, 94);
        ninjaTurtlesSet.scale(0.16);
        add(ninjaTurtlesSet);

        buy500 = new GImage("src\\store\\buy500.png");
        buy500.setLocation(374, 187);
        buy500.scale(2.3);
        add(buy500);

        GImage ninjagoSet = new GImage("src\\store\\NinjagoSet.png");
        ninjagoSet.setLocation(80, 230);
        ninjagoSet.scale(0.16);
        add(ninjagoSet);

        buy700 = new GImage("src\\store\\buy700.png");
        buy700.setLocation(114, 323);
        buy700.scale(2.3);
        add(buy700);

        GImage futuramaSet = new GImage("src\\store\\FuturamaSet.png");
        futuramaSet.setLocation(270, 225);
        futuramaSet.scale(0.225);
        add(futuramaSet);

        buy1k = new GImage("src\\store\\buy1k.png");
        buy1k.setLocation(310, 323);
        buy1k.scale(2.3);
        add(buy1k);


//        retrieveButtonStates();

        balance = 500 + scoreNumber;

        if (buy100Purchased && buy100Active) {
            setSelectedButton(buy100);
            balance-=100;
        } else if (buy100Purchased) {
            setUnselectedButton(buy100);
            balance-=100;
        }
        if (buy350Purchased && buy350Active) {
            setSelectedButton(buy350);
            balance-=350;
        } else if (buy350Purchased) {
            setUnselectedButton(buy350);
            balance-=350;
        }
        if (buy500Purchased && buy500Active) {
            setSelectedButton(buy500);
            balance-=500;
        } else if (buy500Purchased) {
            setUnselectedButton(buy500);
            balance-=500;
        }
        if (buy700Purchased && buy700Active) {
            setSelectedButton(buy700);
            balance-=700;
        } else if (buy700Purchased) {
            setUnselectedButton(buy700);
            balance-=700;
        }
        if (buy1kPurchased && buy1kActive) {
            setSelectedButton(buy1k);
            balance-=1000;
        } else if (buy1kPurchased) {
            setUnselectedButton(buy1k);
            balance-=1000;
        }

        displayBalance();
    }


    public void mouseClicked(MouseEvent e) {

        if(getElementAt(e.getX(), e.getY()) == exitStore){
            removeAll();
            remove(exitStore);
            clip.stop();
            startProgram();
        }

        if (getElementAt(e.getX(), e.getY()) == buy100 && !buy100.getImage().equals("src\\store\\selectedButton.png")) {
            if(balance >= 100 && !buy100Purchased) {
                buy100Purchased = true;
                buy100Active = true;
                balance -= 100;
                displayBalance();
            }
            if(buy100Purchased){
                setSelectedButton(buy100);
                buy100Active=true;
                if (buy350Purchased) {
                    setUnselectedButton(buy350);
                    buy350Active=false;
                }
                if (buy500Purchased) {
                    setUnselectedButton(buy500);
                    buy500Active=false;
                }
                if (buy700Purchased) {
                    setUnselectedButton(buy700);
                    buy700Active=false;
                }
                if (buy1kPurchased) {
                    setUnselectedButton(buy1k);
                    buy1kActive=false;
                }
            }
            if (balance < 100 && !buy100Purchased) {
                JOptionPane.showMessageDialog(new Frame(), "Insufficient balance for this purchase!");
            }
//            storeButtonStates();
        } else if (getElementAt(e.getX(), e.getY()) == buy350 && !buy350.getImage().equals("src\\store\\selectedButton.png")) {
            if (balance >= 350&&!buy350Purchased) {
                buy350Purchased = true;
                balance -= 350;
                displayBalance();

            }
            if(buy350Purchased){
                setSelectedButton(buy350);
                buy350Active=true;
                if (buy100Purchased) {
                    setUnselectedButton(buy100);
                    buy100Active=false;
                }
                if (buy500Purchased) {
                    setUnselectedButton(buy500);
                    buy500Active=false;
                }
                if (buy700Purchased) {
                    setUnselectedButton(buy700);
                    buy700Active=false;
                }
                if (buy1kPurchased) {
                    setUnselectedButton(buy1k);
                    buy1kActive=false;
                }
            }
            if (balance < 350&&!buy350Purchased) {
                JOptionPane.showMessageDialog(new Frame(), "Insufficient balance for this purchase!");
            }
//            storeButtonStates();
        }
        else if (getElementAt(e.getX(), e.getY()) == buy500 && !buy500.getImage().equals("src\\store\\selectedButton.png")) {
            if(balance>=500&&!buy500Purchased) {
                buy500Purchased = true;
                balance -= 500;
                displayBalance();


            }
            if(buy500Purchased){
                setSelectedButton(buy500);
                buy500Active=true;
                if (buy100Purchased) {
                    setUnselectedButton(buy100);
                    buy100Active=false;
                }
                if (buy350Purchased) {
                    setUnselectedButton(buy350);
                    buy350Active=false;
                }
                if (buy700Purchased) {
                    setUnselectedButton(buy700);
                    buy700Active=false;
                }
                if (buy1kPurchased) {
                    setUnselectedButton(buy1k);
                    buy1kActive=false;
                }
            }
            if (balance < 500&&!buy500Purchased) {
                JOptionPane.showMessageDialog(new Frame(), "Insufficient balance for this purchase!");
            }
//            storeButtonStates();
        } else if (getElementAt(e.getX(), e.getY()) == buy700 && !buy700.getImage().equals("src\\store\\selectedButton.png")) {
            if(balance>=700&&!buy700Purchased) {
                buy700Purchased = true;
                balance -= 700;
                displayBalance();

            }
            if(buy700Purchased){
                setSelectedButton(buy700);
                buy700Active=true;
                if (buy100Purchased) {
                    setUnselectedButton(buy100);
                    buy100Active=false;
                }
                if (buy350Purchased) {
                    setUnselectedButton(buy350);
                    buy350Active=false;
                }
                if (buy500Purchased) {
                    setUnselectedButton(buy500);
                    buy500Active=false;
                }
                if (buy1kPurchased) {
                    setUnselectedButton(buy1k);
                    buy1kActive=false;
                }
            }
            if (balance < 700&&!buy700Purchased) {
                JOptionPane.showMessageDialog(new Frame(), "Insufficient balance for this purchase!");
            }
//            storeButtonStates();
        } else if (getElementAt(e.getX(), e.getY()) == buy1k && !buy1k.getImage().equals("src\\store\\selectedButton.png")) {
            if(balance>=1000&&!buy1kPurchased) {
                buy1kPurchased = true;
                balance -= 1000;
                displayBalance();
            }

            if(buy1kPurchased){
            setSelectedButton(buy1k);
                buy1kActive=true;
                if (buy100Purchased) {
                    setUnselectedButton(buy100);
                    buy100Active=false;
                }
                if (buy350Purchased) {
                    setUnselectedButton(buy350);
                    buy350Active=false;
                }
                if (buy500Purchased) {
                    setUnselectedButton(buy500);
                    buy500Active=false;
                }
                if (buy700Purchased) {
                    setUnselectedButton(buy700);
                    buy700Active=false;
                }
            }

            if (balance < 1000&&!buy1kPurchased) {
                JOptionPane.showMessageDialog(new Frame(), "Insufficient balance for this purchase!");
            }
//            storeButtonStates();
        }

    }


    private void setSelectedButton(GImage button) {
        button.setImage("src\\store\\selectedButton.png");
        button.scale(2.3);
        if (button == buy100) {
            button.setLocation(44, 187);
        } else if (button == buy350) {
            button.setLocation(206, 187);
        } else if (button == buy500) {
            button.setLocation(367, 187);
        } else if (button == buy700) {
            button.setLocation(106, 323);
        } else if (button == buy1k) {
            button.setLocation(300, 323);
        }
    }

    private void setUnselectedButton(GImage button) {
        button.setImage("src\\store\\selectButton.png");
        button.scale(2.3);
        if (button == buy100) {
            button.setLocation(54, 187);
        } else if (button == buy350) {
            button.setLocation(216, 187);
        } else if (button == buy500) {
            button.setLocation(377, 187);
        } else if (button == buy700) {
            button.setLocation(114, 323);
        } else if (button == buy1k) {
            button.setLocation(310, 323);
        }
    }

    private void displayBalance() {
        if (balanceLabel != null) {
            remove(balanceLabel);
        }
        balanceLabel = new GLabel("Balance: " + balance + " coins");
        balanceLabel.setFont(new Font("Rubik", Font.PLAIN, 18));
        balanceLabel.setColor(Color.WHITE);
        double x = getWidth() - balanceLabel.getWidth() - 20;
        double y = getHeight() - balanceLabel.getAscent() - 15;
        balanceLabel.setLocation(x, y);
        add(balanceLabel);
    }

    private void retrieveButtonStates() {
        // Load the button states from storage (e.g., a file or database)
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream("src\\data.txt")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Retrieve the stored states and update the variables
        buy100Purchased = Boolean.parseBoolean(properties.getProperty("buy100Purchased", "false"));
        buy350Purchased = Boolean.parseBoolean(properties.getProperty("buy350Purchased", "false"));
        buy500Purchased = Boolean.parseBoolean(properties.getProperty("buy500Purchased", "false"));
        buy700Purchased = Boolean.parseBoolean(properties.getProperty("buy700Purchased", "false"));
        buy1kPurchased = Boolean.parseBoolean(properties.getProperty("buy1kPurchased", "false"));

        buy100Active = Boolean.parseBoolean(properties.getProperty("buy100Active", "false"));
        buy350Active = Boolean.parseBoolean(properties.getProperty("buy350Active", "false"));
        buy500Active = Boolean.parseBoolean(properties.getProperty("buy500Active", "false"));
        buy700Active = Boolean.parseBoolean(properties.getProperty("buy700Active", "false"));
        buy1kActive = Boolean.parseBoolean(properties.getProperty("buy1kActive", "false"));
    }

    private void storeButtonStates() {
        // Store the button states to storage (e.g., a file or database)
        Properties properties = new Properties();
        properties.setProperty("buy100Purchased", String.valueOf(buy100Purchased));
        properties.setProperty("buy350Purchased", String.valueOf(buy350Purchased));
        properties.setProperty("buy500Purchased", String.valueOf(buy500Purchased));
        properties.setProperty("buy700Purchased", String.valueOf(buy700Purchased));
        properties.setProperty("buy1kPurchased", String.valueOf(buy1kPurchased));

        properties.setProperty("buy100Active", String.valueOf(buy100Active));
        properties.setProperty("buy350Active", String.valueOf(buy350Active));
        properties.setProperty("buy500Active", String.valueOf(buy500Active));
        properties.setProperty("buy700Active", String.valueOf(buy700Active));
        properties.setProperty("buy1kActive", String.valueOf(buy1kActive));

        try (OutputStream outputStream = new FileOutputStream("src\\data.txt")) {
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void drawLabyrinthLevel2() {

        score = new GLabel("Score: " + scoreNumber);
        score.setLocation(10,25);
        score.setFont("Courier New Bold-25");
        add(score);

        heart1 = new GImage("src\\heart.png");
        heart1.setLocation(score.getWidth() + 100,0);
        heart1.setSize(30,30);
        add(heart1);

        heart2 = new GImage("src\\heart.png");
        heart2.setLocation(score.getWidth() + heart1.getWidth() + 15 + 100,0);
        heart2.setSize(30,30);
        add(heart2);

        heart3 = new GImage("src\\heart.png");
        heart3.setLocation(score.getWidth() + 2 * heart1.getWidth() + 30 + 100,0);
        heart3.setSize(30,30);
        add(heart3);

        heart4 = new GImage("src\\heart.png");
        heart4.setLocation(100,400);
        heart4.setSize(30,30);
        add(heart4);


        backgroundLab = new GRect(0,30,500,470);
        backgroundLab.setFilled(true);
        backgroundLab.setColor(Color.BLACK);
        add(backgroundLab);

        border = new GRect(10, 40, 465, 390);
        border.setColor(Color.BLUE);
        add(border);

        exit = new GImage("src\\exitBut.png");
        exit.setSize(80, 30);
        exit.setLocation(getWidth()-exit.getWidth(), 0);
        add(exit);

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clip.stop();
                if(isFocusable()){
                    scoreLevel = 0;
                    remove(exit);
                    removeAll();
                    startProgram();
                    flag_level2=false;


                }
            }
        });

        pacMan = new GImage("src\\pacman.png");
        pacMan.setLocation(12,42);
        add(pacMan);
        ghost1 = new GImage("src\\ghost.gif");
        ghost1.setLocation(getWidth()/2-(ghost1.getWidth()*5)/2+15, getHeight()/2-ghost1.getHeight()/2+10+30);
        if (buy100Purchased && buy100Active) {
            ghost1.setImage("src\\store\\violetGhost.png");
            ghost1.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost1.setImage("src\\store\\prideGhost1.png");
            ghost1.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost1.setImage("src\\store\\RaphaelGhost.png");
            ghost1.scale(1.5);
        }
        else if (buy700Purchased && buy700Active) {
            ghost1.setImage("src\\store\\LloydGhost.png");
            ghost1.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost1.setImage("src\\store\\BenderGhost.png");
            ghost1.scale(1.5);
        }
        add(ghost1);
        Ghost1 = new MoveGhost(ghost1,false,false,false,true,4);
        ghost2 = new GImage("src\\ghost.gif");
        ghost2.setLocation(getWidth()/2-(ghost1.getWidth()*5)/2+ghost1.getWidth()+15-200, getHeight()/2-ghost1.getHeight()/2+10);
        if (buy100Purchased && buy100Active) {
            ghost2.setImage("src\\store\\redGhost.png");
            ghost2.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost2.setImage("src\\store\\prideGhost2.png");
            ghost2.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost2.setImage("src\\store\\LeonardoGhost.png");
            ghost2.scale(1.5);
        }
        else if (buy700Purchased && buy700Active) {
            ghost2.setImage("src\\store\\JayGhost.png");
            ghost2.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost2.setImage("src\\store\\FryGhost.png");
            ghost2.scale(1.5);
        }
        add(ghost2);
        Ghost2 = new MoveGhost(ghost2,true,false,false,false,6);
        ghost3 = new GImage("src\\ghost.gif");
        ghost3.setLocation(getWidth()/2-(ghost1.getWidth()*5)/2+2*ghost1.getWidth()+15+100, getHeight()/2-ghost1.getHeight()/2+10-150);
        if (buy100Purchased && buy100Active) {
            ghost3.setImage("src\\store\\pinkGhost.png");
            ghost3.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost3.setImage("src\\store\\prideGhost3.png");
            ghost3.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost3.setImage("src\\store\\MichelangeloGhost.png");
            ghost3.scale(1.5);
        }
        else if (buy700Purchased && buy700Active) {
            ghost3.setImage("src\\store\\KaiGhost.png");
            ghost3.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost3.setImage("src\\store\\LeelaGhost.png");
            ghost3.scale(1.5);
        }
        add(ghost3);
        Ghost3 = new MoveGhost(ghost3,false,true,false,false,8);
        ghost4 = new GImage("src\\ghost.gif");
        ghost4.setLocation(getWidth()/2-(ghost1.getWidth()*5)/2+3*ghost1.getWidth()+15-150, getHeight()/2-ghost1.getHeight()/2+10+100+40);

      //  add(ghost4);
        Ghost4 = new MoveGhost(ghost4,true,false,false,false,10);
        ghost5 = new GImage("src\\ghost.gif");
        ghost5.setLocation(getWidth()/2-(ghost1.getWidth()*5)/2+4*ghost1.getWidth()+15, getHeight()/2-ghost1.getHeight()/2+10+40);
        if (buy100Purchased && buy100Active) {
            ghost5.setImage("src\\store\\greenGhost.png");
            ghost5.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost5.setImage("src\\store\\prideGhost5.png");
            ghost5.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost5.setImage("src\\store\\SplinterGhost.png");
            ghost5.scale(1.6);
        }
        else if (buy700Purchased && buy700Active) {
            ghost5.setImage("src\\store\\WuGhost.png");
            ghost5.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost5.setImage("src\\store\\OctopusGhost.png");
            ghost5.scale(1.5);
        }
        add(ghost5);
        Ghost5 = new MoveGhost(ghost5,true,false,false,false,11);

        pointList = null;
        pointList = new ArrayList<>();
        ghostList = new ArrayList<>();
        ghostList.add(Ghost1);
        ghostList.add(Ghost2);
        ghostList.add(Ghost3);
        //ghostList.add(Ghost4);
        ghostList.add(Ghost5);

        for (int x = 20; x <= 465; x += 20) {
            for (int y = 50; y <= 420; y += 20) {
                GImage point = new GImage("src\\point.gif");
                point.setSize(10, 10);
                point.setLocation(x, y);
                if (!pacMan.getBounds().contains(point.getBounds().getLocation())
                        ) {
                    add(point);
                    pointList.add(point);
                }
            }
        }

        l1 = new GLine(75, 65, 295, 65);
        l1.setColor(Color.BLUE);
        add(l1);

        l2 = new GLine(295, 405, 450, 405);
        l2.setColor(Color.BLUE);
        add(l2);

        l3 = new GLine(450, 285, 450, 405);
        l3.setColor(Color.BLUE);
        add(l3);

        l4 = new GLine(255, 205, 255, 225);
        l4.setColor(Color.BLUE);
        add(l4);

        l5 = new GLine(155, 225, 255, 225);
        l5.setColor(Color.BLUE);
        add(l5);

        l6 = new GLine(155, 225, 155, 325);
        l6.setColor(Color.BLUE);
        add(l6);

        l7 = new GLine(155, 325, 295, 325);
        l7.setColor(Color.BLUE);
        add(l7);

        l8 = new GLine(295, 325, 295, 405);
        l8.setColor(Color.BLUE);
        add(l8);

        l9 = new GLine(295, 65, 295, 165);
        l9.setColor(Color.BLUE);
        add(l9);

        l10 = new GLine(295, 165, 375, 165);
        l10.setColor(Color.BLUE);
        add(l10);

        l11 = new GLine(375, 165, 375, 335);
        l11.setColor(Color.BLUE);
        add(l11);

        l12 = new GLine(55, 95, 55, 265);
        l12.setColor(Color.BLUE);
        add(l12);

        l13 = new GLine(55, 265, 155, 265);
        l13.setColor(Color.BLUE);
        add(l13);

        l14 = new GLine(95, 265, 95, 385);
        l14.setColor(Color.BLUE);
        add(l14);

        l15 = new GLine(135, 65, 135, 145);
        l15.setColor(Color.BLUE);
        add(l15);

        l16 = new GLine(55, 145, 135, 145);
        l16.setColor(Color.BLUE);
        add(l16);

        l17 = new GLine(295, 105, 425, 105);
        l17.setColor(Color.BLUE);
        add(l17);

        pill1 = new GOval(437, 67, 16, 16);
        pill1.setColor(Color.red);
        pill1.setFilled(true);

        pill2 = new GOval(117, 287, 16, 16);
        pill2.setColor(Color.red);
        pill2.setFilled(true);

        pill3 = new GOval(77, 167, 16, 16);
        pill3.setColor(Color.red);
        pill3.setFilled(true);

        add(pill1);
        add(pill2);
        add(pill3);
    }

    public void drawLabyrinthLevel1() {

        score = new GLabel("Score: " + scoreNumber);
        score.setLocation(10,25);
        score.setFont("Courier New Bold-25");
        add(score);

        heart1 = new GImage("src\\heart.png");
        heart1.setLocation(score.getWidth() + 100,0);
        heart1.setSize(30,30);
        add(heart1);

        heart2 = new GImage("src\\heart.png");
        heart2.setLocation(score.getWidth() + heart1.getWidth() + 15 + 100,0);
        heart2.setSize(30,30);
        add(heart2);

        heart3 = new GImage("src\\heart.png");
        heart3.setLocation(score.getWidth() + 2 * heart1.getWidth() + 30 + 100,0);
        heart3.setSize(30,30);
        add(heart3);


        backgroundLab = new GRect(0,30,500,470);
        backgroundLab.setFilled(true);
        backgroundLab.setColor(Color.BLACK);
        add(backgroundLab);

        border = new GRect(10, 40, 465, 390);
        border.setColor(Color.BLUE);
        add(border);

        exit = new GImage("src\\exitBut.png");
        exit.setSize(80, 30);
        exit.setLocation(getWidth()-exit.getWidth(), 0);
        add(exit);

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clip.stop();
                if(isFocusable()){
                    scoreLevel = 0;
                    remove(exit);
                    removeAll();
                    startProgram();
                    flag_level2=false;

                }
            }
        });

        pacMan = new GImage("src\\pacman.png");
        pacMan.setLocation(12,42);
        add(pacMan);

        ghost1 = new GImage("src\\ghost.gif");
        ghost1.setLocation(getWidth()/2-(ghost1.getWidth()*5)/2-27, getHeight()/2-ghost1.getHeight()/2+10+20);
        if (buy100Purchased && buy100Active) {
            ghost1.setImage("src\\store\\blueGhost.png");
            ghost1.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost1.setImage("src\\store\\prideGhost1.png");
            ghost1.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost1.setImage("src\\store\\DonatelloGhost.png");
            ghost1.scale(1.5);
        }
        else if (buy700Purchased && buy700Active) {
            ghost1.setImage("src\\store\\ZaneGhost.png");
            ghost1.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost1.setImage("src\\store\\BenderGhost.png");
            ghost1.scale(1.5);
        }
        add(ghost1);
        Ghost1 = new MoveGhost(ghost1,false,false,false,true);
        ghost2 = new GImage("src\\ghost.gif");
        ghost2.setLocation(getWidth()/2-(ghost1.getWidth()*5)/2+ghost1.getWidth()+15-150+10+5, getHeight()/2-ghost1.getHeight()/2-100-10+2);
        if (buy100Purchased && buy100Active) {
            ghost2.setImage("src\\store\\redGhost.png");
            ghost2.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost2.setImage("src\\store\\prideGhost2.png");
            ghost2.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost2.setImage("src\\store\\LeonardoGhost.png");
            ghost2.scale(1.5);
        }
        else if (buy700Purchased && buy700Active) {
            ghost2.setImage("src\\store\\JayGhost.png");
            ghost2.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost2.setImage("src\\store\\FryGhost.png");
            ghost2.scale(1.5);
        }
        add(ghost2);
        Ghost2 = new MoveGhost(ghost2,false,false,true,false);
        ghost3 = new GImage("src\\ghost.gif");
        ghost3.setLocation(getWidth()/2-(ghost1.getWidth()*5)/2+2*ghost1.getWidth()+15+100+35, getHeight()/2-ghost1.getHeight()/2+10);
        if (buy100Purchased && buy100Active) {
            ghost3.setImage("src\\store\\pinkGhost.png");
            ghost3.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost3.setImage("src\\store\\prideGhost3.png");
            ghost3.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost3.setImage("src\\store\\MichelangeloGhost.png");
            ghost3.scale(1.5);
        }
        else if (buy700Purchased && buy700Active) {
            ghost3.setImage("src\\store\\KaiGhost.png");
            ghost3.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost3.setImage("src\\store\\LeelaGhost.png");
            ghost3.scale(1.5);
        }
        add(ghost3);
        Ghost3 = new MoveGhost(ghost3,false,true,false,false);
        ghost4 = new GImage("src\\ghost.gif");
        ghost4.setLocation(getWidth()/2-(ghost1.getWidth()*5)/2+3*ghost1.getWidth()+15-150, getHeight()/2-ghost1.getHeight()/2+10+130);
        if (buy100Purchased && buy100Active) {
            ghost4.setImage("src\\store\\orangeGhost.png");
            ghost4.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost4.setImage("src\\store\\prideGhost4.png");
            ghost4.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost4.setImage("src\\store\\SplinterGhost.png");
            ghost4.scale(1.5);
        }
        else if (buy700Purchased && buy700Active) {
            ghost4.setImage("src\\store\\LloydGhost.png");
            ghost4.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost4.setImage("src\\store\\AmyGhost.png");
            ghost4.scale(1.5);
        }
        add(ghost4);
        Ghost4 = new MoveGhost(ghost4,true,false,false,false);
        ghost5 = new GImage("src\\ghost.gif");
        ghost5.setLocation(getWidth()/2-(ghost1.getWidth()*5)/2+4*ghost1.getWidth()+15-85, getHeight()/2-ghost1.getHeight()/2+10-150);
        if (buy100Purchased && buy100Active) {
            ghost5.setImage("src\\store\\greenGhost.png");
            ghost5.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost5.setImage("src\\store\\prideGhost5.png");
            ghost5.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost5.setImage("src\\store\\ShredderGhost.png");
            ghost5.scale(1.6);
        }
        else if (buy700Purchased && buy700Active) {
            ghost5.setImage("src\\store\\WuGhost.png");
            ghost5.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost5.setImage("src\\store\\OctopusGhost.png");
            ghost5.scale(1.5);
        }
        add(ghost5);
        Ghost5 = new MoveGhost(ghost5,true,false,false,false);

        pointList = null;
        pointList = new ArrayList<>();
        ghostList = new ArrayList<>();
        ghostList.add(Ghost1);
        ghostList.add(Ghost2);
        ghostList.add(Ghost3);
        ghostList.add(Ghost4);
        ghostList.add(Ghost5);

        for (int x = 20; x <= 465; x += 20) {
            for (int y = 50; y <= 420; y += 20) {
                GImage point = new GImage("src\\point.gif");
                point.setSize(10, 10);
                point.setLocation(x, y);
                if (!pacMan.getBounds().contains(point.getBounds().getLocation())
                        && !ghost2.getBounds().contains(point.getBounds().getLocation())
                        ) {
                    add(point);
                    pointList.add(point);
                }
            }
        }

        l1 = new GLine(75, 65, 295, 65);
        l1.setColor(Color.BLUE);
        add(l1);

        l2 = new GLine(295, 405, 450, 405);
        l2.setColor(Color.BLUE);
        add(l2);

        l3 = new GLine(450, 285, 450, 405);
        l3.setColor(Color.BLUE);
        add(l3);

        l4 = new GLine(255, 205, 255, 225);
        l4.setColor(Color.BLUE);
        add(l4);

        l5 = new GLine(155, 225, 255, 225);
        l5.setColor(Color.BLUE);
        add(l5);

        l6 = new GLine(155, 225, 155, 325);
        l6.setColor(Color.BLUE);
        add(l6);

        l7 = new GLine(155, 325, 295, 325);
        l7.setColor(Color.BLUE);
        add(l7);

        l8 = new GLine(295, 325, 295, 405);
        l8.setColor(Color.BLUE);
        add(l8);

        l9 = new GLine(295, 65, 295, 165);
        l9.setColor(Color.BLUE);
        add(l9);

        l10 = new GLine(295, 165, 375, 165);
        l10.setColor(Color.BLUE);
        add(l10);

        l11 = new GLine(375, 165, 375, 335);
        l11.setColor(Color.BLUE);
        add(l11);

        l12 = new GLine(55, 95, 55, 265);
        l12.setColor(Color.BLUE);
        add(l12);

        l13 = new GLine(55, 265, 155, 265);
        l13.setColor(Color.BLUE);
        add(l13);

        l14 = new GLine(95, 265, 95, 385);
        l14.setColor(Color.BLUE);
        add(l14);

        l15 = new GLine(135, 65, 135, 145);
        l15.setColor(Color.BLUE);
        add(l15);

        l16 = new GLine(55, 145, 135, 145);
        l16.setColor(Color.BLUE);
        add(l16);

        l17 = new GLine(295, 105, 425, 105);
        l17.setColor(Color.BLUE);
        add(l17);
    }


    protected boolean checkCollisionLevel(GImage pacMan) {
        if (pacMan.getBounds().intersects(l1.getBounds()) || pacMan.getBounds().intersects(l2.getBounds())
                || pacMan.getBounds().intersects(l3.getBounds()) || pacMan.getBounds().intersects(l4.getBounds())
                || pacMan.getBounds().intersects(l5.getBounds()) || pacMan.getBounds().intersects(l6.getBounds())
                || pacMan.getBounds().intersects(l7.getBounds()) || pacMan.getBounds().intersects(l8.getBounds())
                || pacMan.getBounds().intersects(l9.getBounds()) || pacMan.getBounds().intersects(l10.getBounds())
                || pacMan.getBounds().intersects(l11.getBounds()) || pacMan.getBounds().intersects(l12.getBounds())
                || pacMan.getBounds().intersects(l13.getBounds()) || pacMan.getBounds().intersects(l14.getBounds())
                || pacMan.getBounds().intersects(l15.getBounds()) || pacMan.getBounds().intersects(l16.getBounds())
                || pacMan.getBounds().intersects(l17.getBounds())) {
            return true;
        }
        return false;
    }

    public void drawLabyrinthLevel3() {

        score = new GLabel("Score: " + scoreNumber);
        score.setLocation(10,25);
        score.setFont("Courier New Bold-25");
        add(score);

        heart1 = new GImage("src\\heart.png");
        heart1.setLocation(score.getWidth() + 100,0);
        heart1.setSize(30,30);
        add(heart1);

        heart2 = new GImage("src\\heart.png");
        heart2.setLocation(score.getWidth() + heart1.getWidth() + 15 + 100,0);
        heart2.setSize(30,30);
        add(heart2);

        heart3 = new GImage("src\\heart.png");
        heart3.setLocation(score.getWidth() + 2 * heart1.getWidth() + 30 + 100,0);
        heart3.setSize(30,30);
        add(heart3);

        backgroundLab = new GRect(0,30,500,470);
        backgroundLab.setFilled(true);
        backgroundLab.setColor(Color.BLACK);
        add(backgroundLab);

        border = new GRect(10, 40, 465, 390);
        border.setColor(Color.BLUE);
        add(border);

        exit = new GImage("src\\exitBut.png");
        exit.setSize(80, 30);
        exit.setLocation(getWidth()-exit.getWidth(), 0);
        add(exit);

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clip.stop();
                if(isFocusable()){
                    scoreLevel = 0;
                    remove(pacMan);
                    remove(ball);
                    flag_level3 = false;
                    remove(exit);
                    removeAll();
                    startProgram();
                    flag_level2=false;

                }
            }
        });

        pacMan = new GImage("src\\pacman.png");
        pacMan.setLocation(12,42);
        add(pacMan);

        ghost1 = new GImage("src\\ghost.gif");
        ghost1.setLocation(getWidth()/2-(ghost1.getWidth()*5)/2+15, getHeight()/2-ghost1.getHeight()/2+10+30);
        if (buy100Purchased && buy100Active) {
            ghost1.setImage("src\\store\\blueGhost.png");
            ghost1.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost1.setImage("src\\store\\prideGhost1.png");
            ghost1.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost1.setImage("src\\store\\DonatelloGhost.png");
            ghost1.scale(1.5);
        }
        else if (buy700Purchased && buy700Active) {
            ghost1.setImage("src\\store\\ZaneGhost.png");
            ghost1.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost1.setImage("src\\store\\BenderGhost.png");
            ghost1.scale(1.5);
        }
        add(ghost1);
        Ghost1 = new MoveGhost(ghost1,false,false,false,true,4);
        ghost2 = new GImage("src\\ghost.gif");
        ghost2.setLocation(getWidth()/2-(ghost1.getWidth()*5)/2+ghost1.getWidth()+15-200, getHeight()/2-ghost1.getHeight()/2+10);
        if (buy100Purchased && buy100Active) {
            ghost2.setImage("src\\store\\redGhost.png");
            ghost2.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost2.setImage("src\\store\\prideGhost2.png");
            ghost2.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost2.setImage("src\\store\\LeonardoGhost.png");
            ghost2.scale(1.5);
        }
        else if (buy700Purchased && buy700Active) {
            ghost2.setImage("src\\store\\JayGhost.png");
            ghost2.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost2.setImage("src\\store\\FryGhost.png");
            ghost2.scale(1.5);
        }
        add(ghost2);

        Ghost2 = new MoveGhost(ghost2,true,false,false,false,6);
        ghost3 = new GImage("src\\ghost.gif");
        ghost3.setLocation(getWidth()/2-(ghost1.getWidth()*5)/2+2*ghost1.getWidth()+15+100, getHeight()/2-ghost1.getHeight()/2+10-150);

        if (buy100Purchased && buy100Active) {
            ghost3.setImage("src\\store\\pinkGhost.png");
            ghost3.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost3.setImage("src\\store\\prideGhost3.png");
            ghost3.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost3.setImage("src\\store\\MichelangeloGhost.png");
            ghost3.scale(1.5);
        }
        else if (buy700Purchased && buy700Active) {
            ghost3.setImage("src\\store\\KaiGhost.png");
            ghost3.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost3.setImage("src\\store\\LeelaGhost.png");
            ghost3.scale(1.5);
        }
        add(ghost3);
        Ghost3 = new MoveGhost(ghost3,false,true,false,false,8);
        ghost4 = new GImage("src\\ghost.gif");
        ghost4.setLocation(getWidth()/2-(ghost1.getWidth()*5)/2+3*ghost1.getWidth()+15-150, getHeight()/2-ghost1.getHeight()/2+10+100);
        if (buy100Purchased && buy100Active) {
            ghost4.setImage("src\\store\\orangeGhost.png");
            ghost4.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost4.setImage("src\\store\\prideGhost4.png");
            ghost4.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost4.setImage("src\\store\\SplinterGhost.png");
            ghost4.scale(1.5);
        }
        else if (buy700Purchased && buy700Active) {
            ghost4.setImage("src\\store\\LloydGhost.png");
            ghost4.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost4.setImage("src\\store\\AmyGhost.png");
            ghost4.scale(1.5);
        }
        add(ghost4);
        Ghost4 = new MoveGhost(ghost4,true,false,false,false,10);
        ghost5 = new GImage("src\\ghost.gif");
        ghost5.setLocation(getWidth()/2-(ghost1.getWidth()*5)/2+4*ghost1.getWidth()+15, getHeight()/2-ghost1.getHeight()/2+10);
        if (buy100Purchased && buy100Active) {
            ghost5.setImage("src\\store\\greenGhost.png");
            ghost5.scale(1.5);
        }
        else if(buy350Purchased&&buy350Active){
            ghost5.setImage("src\\store\\prideGhost5.png");
            ghost5.scale(1.5);
        }
        else if (buy500Purchased && buy500Active) {
            ghost5.setImage("src\\store\\ShredderGhost.png");
            ghost5.scale(1.6);
        }
        else if (buy700Purchased && buy700Active) {
            ghost5.setImage("src\\store\\WuGhost.png");
            ghost5.scale(1.5);
        }
        else if (buy1kPurchased && buy1kActive) {
            ghost5.setImage("src\\store\\OctopusGhost.png");
            ghost5.scale(1.5);
        }
        add(ghost5);
        Ghost5 = new MoveGhost(ghost5,true,false,false,false,10);

        portal1 = new GImage("src\\portal-removebg-preview.png");
        portal1.setLocation(90,90);
        portal1.scale(0.05);
        add(portal1);

        portal2 = new GImage("src\\portal-removebg-preview.png");
        portal2.setLocation(320, 345);
        portal2.scale(0.065);
        add(portal2);

        gun1 = new GImage("src\\gun.png");
        gun1.setLocation(435,410 );
        gun1.scale(0.065);
        add(gun1);

        ball = new GImage("src\\ballState1.png");
        ball.setLocation(435-ball.getWidth()  , 410);
        ball.scale(0.7);

        cherries = new GImage("src\\cherries.png");
        cherries.setLocation(getWidth()/2-10, getHeight()/2-15);
        cherries.scale(1.5);
        add(cherries);

        strawberry = new GImage("src\\strawberry.png");
        strawberry.setLocation(120, getHeight()-30);
        strawberry.scale(1.5);
        add(strawberry);

        pizza = new GImage("src\\pizza.png");
        pizza.setLocation(320, 42);
        pizza.scale(1.5);
        add(pizza);

        pointList = null;
        pointList = new ArrayList<>();
        ghostList = new ArrayList<>();
        ghostList.add(Ghost1);
        ghostList.add(Ghost2);
        ghostList.add(Ghost3);
        ghostList.add(Ghost4);
        ghostList.add(Ghost5);

        for (int x = 20; x <= 465; x += 20) {
            for (int y = 50; y <= 420; y += 20) {
                GImage point = new GImage("src\\point.gif");
                point.setSize(10, 10);
                point.setLocation(x, y);
                if (!pacMan.getBounds().contains(point.getBounds().getLocation() )&&
                        !portal1.getBounds().contains(point.getBounds().getLocation())&&
                        !gun1.getBounds().contains(point.getBounds().getLocation())&&
                       // !gun2.getBounds().contains(point.getBounds().getLocation())&&
                        !portal2.getBounds().contains(point.getBounds().getLocation())&&
                        !cherries.getBounds().contains(point.getBounds().getLocation())&&
                        !strawberry.getBounds().contains(point.getBounds().getLocation())&&
                        !pizza.getBounds().contains(point.getBounds().getLocation())
                ) {
                    add(point);
                    pointList.add(point);
                }
            }
        }

        l1 = new GLine(75, 65, 295, 65);
        l1.setColor(Color.BLUE);
        add(l1);

        l2 = new GLine(295, 405, 450, 405);
        l2.setColor(Color.BLUE);
        add(l2);

        l3 = new GLine(450, 285, 450, 405);
        l3.setColor(Color.BLUE);
        add(l3);

        l4 = new GLine(255, 205, 255, 225);
        l4.setColor(Color.BLUE);
        add(l4);

        l5 = new GLine(155, 225, 255, 225);
        l5.setColor(Color.BLUE);
        add(l5);

        l6 = new GLine(155, 225, 155, 325);
        l6.setColor(Color.BLUE);
        add(l6);

        l7 = new GLine(155, 325, 295, 325);
        l7.setColor(Color.BLUE);
        add(l7);

        l8 = new GLine(295, 325, 295, 405);
        l8.setColor(Color.BLUE);
        add(l8);

        l9 = new GLine(295, 65, 295, 165);
        l9.setColor(Color.BLUE);
        add(l9);

        l10 = new GLine(295, 165, 375, 165);
        l10.setColor(Color.BLUE);
        add(l10);

        l11 = new GLine(375, 165, 375, 335);
        l11.setColor(Color.BLUE);
        add(l11);

        l12 = new GLine(55, 95, 55, 265);
        l12.setColor(Color.BLUE);
        add(l12);

        l13 = new GLine(55, 265, 155, 265);
        l13.setColor(Color.BLUE);
        add(l13);

        l14 = new GLine(95, 265, 95, 385);
        l14.setColor(Color.BLUE);
        add(l14);

        l15 = new GLine(135, 65, 135, 145);
        l15.setColor(Color.BLUE);
        add(l15);

        l16 = new GLine(55, 145, 135, 145);
        l16.setColor(Color.BLUE);
        add(l16);

        l17 = new GLine(295, 105, 425, 105);
        l17.setColor(Color.BLUE);
        add(l17);

        pill1 = new GOval(437, 67, 16, 16);
        pill1.setColor(Color.red);
        pill1.setFilled(true);

        pill2 = new GOval(117, 287, 16, 16);
        pill2.setColor(Color.red);
        pill2.setFilled(true);

        pill3 = new GOval(77, 167, 16, 16);
        pill3.setColor(Color.red);
        pill3.setFilled(true);

        add(pill1);
        add(pill2);
        add(pill3);
    }

    public void winLevel(){
        clip.stop();
        play("src\\Music\\winMusic.wav");
        remove(pacMan);
        remove(exit);
        removeAll();

        GImage winBackground = new GImage("src\\win-fon.png");
        winBackground.setSize(getWidth(), getHeight());
        add(winBackground);
        GLabel cong = new GLabel("Congratulations!");
        cong.setColor(Color.white);
        cong.setFont("Rubik-45");
        cong.setLocation(getWidth()/2-cong.getWidth()/2, 150);
        GLabel pointsNumber = new GLabel("You have scored " + scoreLevel + " points!");
        pointsNumber.setColor(Color.white);
        pointsNumber.setFont("Courier New Bold-25");
        pointsNumber.setLocation(getWidth()/2-pointsNumber.getWidth()/2, 200);
        add(cong);
        add(pointsNumber);

        exit = new GImage("src\\exitBut.png");
        exit.setLocation(getWidth()/2-exit.getWidth()/2, 300);
        add(exit);

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isFocusable()){
                    remove(exit);
                    removeAll();
                    startProgram();
                }
            }
        });
    }

    public static void play(String path){
        try {
            File soundFile = new File(path);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception ex) {
            System.out.println();
        }
    }
    private void ghostMove() {
        for (MoveGhost point : ghostList) {
            point.getGhost().move(point.moveX(),point.moveY());
        }
    }

    private void ghostMoveLevel3() {
        int i = 1;
        for (MoveGhost point : ghostList) {
            if(i!=4)
            {
                point.getGhost().move(point.moveX(),point.moveY());
            }

            i++;
        }
    }
    private void looseLevel() {
        clip.stop();
        play("src\\Music\\looseMusic.wav");
        remove(pacMan);
        remove(exit);
        removeAll();

        GImage winBackground = new GImage("src\\win-fon.png");
        winBackground.setSize(getWidth(), getHeight());
        add(winBackground);
        GLabel loose = new GLabel("You lost!");
        loose.setColor(Color.white);
        loose.setFont("Rubik-45");
        loose.setLocation(getWidth()/2-loose.getWidth()/2, 150);
        GLabel pointsNumber = new GLabel("You have scored " + scoreLevel + " points!");
        pointsNumber.setColor(Color.white);
        pointsNumber.setFont("Courier New Bold-25");
        pointsNumber.setLocation(getWidth()/2-pointsNumber.getWidth()/2, 200);
        add(loose);
        add(pointsNumber);

        exit = new GImage("src\\exitBut.png");
        exit.setLocation(getWidth()/2-exit.getWidth()/2, 300);
        add(exit);

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isFocusable()){
                    remove(exit);
                    removeAll();
                    startProgram();
                }
            }
        });
    }
}