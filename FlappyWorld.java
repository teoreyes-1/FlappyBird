import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Image;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.io.File;
import java.net.URL;
import javax.swing.JOptionPane;
import static java.lang.Character.*;

public class FlappyWorld extends Canvas implements KeyListener, Runnable
{
  private Bird bird;
  private Apple apple;
  private BufferedImage back;
  private int appleMove;
  private String gameState;
  private GateArray gates;
  private int score;
  private ScoreSaver scoreSaver;
  private String name;
  private Image image;
  private int imageScroll;
  private int frameCounter;
  //private int backgroundColorChange;

  /*private String flap;
  private String fall;
  private String hit;
  private String scoreUp;
  private String whoosh;*/

  public FlappyWorld()
  {
    bird = new Bird();
    apple = new Apple(500,300);
    setBackground(Color.black);
    appleMove = 1;
    gameState = "menu";
    gates = new GateArray();
    scoreSaver = new ScoreSaver();
    imageScroll = 0;
    frameCounter = 0;

    /*
    SFXPlayer actually works and compiles, but it seems that Repl.it does not support 
    audio playback. Other people have made it work in Python but I can't find any guides on making it work in Java :(
    flap = "flap.wav";
    fall = "fall.wav";
    hit = "hit.wav";
    scoreUp = "scoreUp.wav";
    whoosh = "whoosh.wav";
    
    SFXPlayer.playAudio(hit);
    */

    //backgroundColorChange = 0;
    try{
      URL url = getClass().getResource("FlappyBirdBackground.png");
      image = ImageIO.read(url);
    }catch(Exception e) {
      System.out.println("Background failed to load!");
    }
    
    this.addKeyListener(this);
    new Thread(this).start();

    setVisible(true);
  }

  public void update(Graphics window)
  {
    paint(window, gameState);
  }

  public void paint(Graphics window, String state) {
  
    Graphics2D twoDGraph = (Graphics2D)window;

    if(back == null)
      back = (BufferedImage)(createImage(getWidth(),getHeight()));

    Graphics graphToBack = back.createGraphics();

    graphToBack.setColor(Color.BLUE);
    graphToBack.drawString("Flappy Bird", 25, 50);
    graphToBack.setColor(Color.BLACK); 
    graphToBack.fillRect(0,0,800,600);

    if(state.equals("menu")) {
      paintMenu(graphToBack);
    }
    else if(state.equals("instructions")) {
      paintInstructions(graphToBack);
    }
    else if(state.equals("game")) {
      paintGame(graphToBack);
    }
    else if(state.equals("gameOver")){
      paintGameOver(graphToBack);
    }
    else if(state.equals("highScore"))
    {
      paintHighScore(graphToBack);
    }
    twoDGraph.drawImage(back, null, 0, 0);
  }

  public void paintMenu(Graphics window) {
    window.drawImage(image, imageScroll, 0, 800, 600, null);
    window.drawImage(image, imageScroll + 800, 0, 800, 600, null);
    window.setFont(new Font("DialogInput", Font.BOLD, 113));
    window.setColor(Color.BLUE);
    window.drawString("FLAPPY BIRD", 20, 100);
    window.setFont(new Font("DialogInput", Font.BOLD, 25));
    window.drawString("Press I for instructions", 190, 410);
    window.drawString("Press SPACE to begin", 210, 440);
    window.drawString("Press S to view leaderboard", 170, 470);
    bird.draw(window);
  }

  public void paintInstructions(Graphics window) {
    window.drawImage(image, imageScroll, 0, 800, 600, null);
    window.drawImage(image, imageScroll + 800, 0, 800, 600, null);
    window.setFont(new Font("DialogInput", Font.BOLD, 105));
    window.setColor(Color.BLUE);
    window.drawString("INSTRUCTIONS", 15, 100);
    window.setFont(new Font("DialogInput", Font.BOLD, 25));
    window.drawString("Use the SPACE bar to help Flappy avoid the pipes.", 30, 250);
    window.drawString("Pick up the golden apples for extra points!", 50, 280);
    window.drawString("Press I again to go back", 190, 410);
    window.drawString("Press SPACE to begin", 210, 440);
  }

  public void paintHighScore(Graphics window) {
    window.drawImage(image, imageScroll, 0, 800, 600, null);
    window.drawImage(image, imageScroll + 800, 0, 800, 600, null);
    window.setColor(new Color(0, 153, 204));
    window.fillRect(75,100,625,350);
    window.setFont(new Font("DialogInput", Font.BOLD, 75));
    window.setColor(Color.ORANGE);
    window.drawString("High Scores", 150, 100);
    window.setFont(new Font("DialogInput", Font.BOLD, 25));
    window.drawString("Name:", 170, 150);
    window.drawString("Score:", 500, 150);
    for(int i = 1; (i <= 10) && (i <= scoreSaver.getSortedNames().size()); i++) {
      window.drawString(i + ". " + scoreSaver.getSortedNames().get(i-1), 120, 150 + (i*27));
      window.drawString("" + scoreSaver.getSortedScores().get(i-1), 510, 150 + (i*27));
    }
    window.setColor(Color.BLUE);
    window.setFont(new Font("DialogInput", Font.BOLD, 30));
    window.drawString("Press S again to go back", 180, 550);
  }

  public void paintGame(Graphics window) {
    frameCounter++;
    if(frameCounter % 3 == 0)
      imageScroll--;
    if(imageScroll == -800)
      imageScroll = 0;
    window.drawImage(image, imageScroll, 0, 800, 600, null);
    window.drawImage(image, imageScroll + 800, 0, 800, 600, null);
    
    /*
    if(backgroundColorChange%5==0){
      paintBackground(window);  
      //we can still add this if you guys want i just thought it looked cleaner without
    }
    */

    //Gate generation and management
    if(gates.getGateArray().size() == 0 || (gates.getGateArray().get(gates.getGateArray().size()-1).getBlocks().get(1).getX() <= 350))
      gates.generateGate();
    if(gates.getGateArray().get(0).getBlocks().get(1).getX() <= -120)
    {
      //increase score when gate despawns
      score++;
      gates.deleteGate();
    }

    //move the objects
    bird.move();
    gates.moveAll();
    apple.scrollLeft();
    
    //check that bird has not hit top or bottom
    if(bird.getY() > 570 - bird.getHeight() || bird.getY() < 0)
      gameState = "gameOver";

    //collision between bird and Gates
    for(int i = 0; i < gates.getGateArray().size(); i++) {
      for(Block b : gates.getBlocks(i)) {
        while(apple.collidesWith(b))
          apple.setY((int) (Math.random()*500+50));
        if(bird.collidesWith(b))
          gameState = "gameOver";
      }
    }

    /*appleMove++;
    backgroundColorChange++;

    if(appleMove%2==0){
    apple.moves(-1);
    }
    else{
      apple.moves(1);
    }*/

    if(apple.getX()<=-100)
      apple.respawn();
    
    //bird collision with apple test
    if(bird.collidesWith(apple)) {
      //score increases
      score += 2;
      apple.respawn();
    }
    
    gates.draw(window);
    bird.draw(window);
    apple.draw(window); 

    window.setFont(new Font("DialogInput", Font.BOLD, 40));
    window.setColor(Color.WHITE);
    window.drawString(score + "", 380, 100);
  }

  public void paintGameOver(Graphics window) {
    //window.drawImage(image, 0, 0, 800, 600, null);
    window.setFont(new Font("DialogInput", Font.BOLD, 113));
    window.setColor(Color.RED);
    window.drawString("Game Over", 100, 200);
    window.setFont(new Font("DialogInput", Font.BOLD, 40));
    window.setColor(Color.GREEN);
    window.drawString("Your score was: " + score, 185, 300);
    window.drawString("Press SPACE to restart", 140, 350);
    window.setFont(new Font("DialogInput", Font.BOLD, 20));
    window.setColor(Color.ORANGE);
    window.drawString("If you want to save your score, Press H", 160, 400);
  }

  public void reset() {
    bird.reset();
    apple.respawn();
    score = 0;
    for(int i = gates.getGateArray().size() - 1; i >= 0; i--) {
      gates.deleteGate();
    }
  }

  //no longer used
  /*
  public void paintBackground(Graphics window) {
    for(int i = 0;i<30;i++){
      int red = (int) (Math.random()*50)+30;
      int green = (int) (Math.random()*100)+150;
      int blue = (int) (Math.random()*100)+150;
      Color clr =  new Color(red, green, blue);
      window.setColor(clr);
      int width = (int)(Math.random()*60)+40;
      int height = (int)(Math.random()*60)+40;
      int x = (int)(Math.random()*800);
      int y = (int)(Math.random()*800);
      window.fillRect(x,y,width,height);
    }
  }*/

  public void keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_SPACE)
    {
      //Space key for starting game and controlling bird
      if(gameState.equals("menu") || gameState.equals("instructions"))
        gameState = "game";
      else if(gameState.equals("game"))
        bird.flap();
      else if(gameState.equals("gameOver")) {
        gameState = "menu";
        reset();
      }
    }
    if (e.getKeyCode() == KeyEvent.VK_I)
    {
      //Key I for an instructions screen or an alternative button for extra functions
      if(gameState.equals("menu"))
        gameState = "instructions";
      else if(gameState.equals("instructions"))
        gameState = "menu";
    }
    if (e.getKeyCode() == KeyEvent.VK_H && gameState.equals("gameOver"))
    {
      //Key H for saving your nickname
      //System.out.print("Enter Your Name to Save Your Score: ");
      //Scanner input = new Scanner(System.in);
      //name = input.nextLine();
      String name = JOptionPane.showInputDialog("Enter Your Name to Save Your Score: ");
      System.out.println("We have recorded your name '" + name + "' along with your score of " + score); 
      scoreSaver.addNewScore(score, name); 
      gameState = "menu";
      reset();
    }
    if (e.getKeyCode() == KeyEvent.VK_S)
    {
      //Key S for viewing the leaderboard
      if(gameState.equals("menu")) 
      {
        scoreSaver.createLeaderboard(true);
        gameState = "highScore";
      }
      else if(gameState.equals("highScore"))
      {
        gameState = "menu";
      }
    }
    repaint();
  }

  public void keyReleased(KeyEvent e)
  {

  }

  public void keyTyped(KeyEvent e)
  {

  }

  public void run()
  {
    try
    {
      while(true)
      {
        Thread.currentThread().sleep(7);
        repaint();
      }
    }catch(Exception e)
    {
    }
  }
}



