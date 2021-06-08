import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import static java.lang.Character.*;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Image;
import java.net.URL;

public class FlappyWorld extends Canvas implements KeyListener, Runnable
{

  private Bird bird;

  private BufferedImage back;
  private Apple apple;
  private int backgroundColorChange;
  private int appleMove;
  /*private String flap;
  private String fall;
  private String hit;
  private String scoreUp;
  private String whoosh;*/
  private String gameState;
  private GateArray gates;
  private int score;
  private ScoreSaver scoreSaver;
  private String name;
  private Image image;

  public FlappyWorld()
  {
    bird = new Bird();
    apple = new Apple(500,300);
    setBackground(Color.black);
    appleMove = 1;
    gameState = "menu";
    gates = new GateArray();
    scoreSaver = new ScoreSaver();

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

    backgroundColorChange = 0;
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
    graphToBack.drawString("Flappy Bird ", 25, 50);
    graphToBack.setColor(Color.BLACK); //i'm going to try and put an image as the background, let's maybe try and make the apples have no black box liek the bird
    graphToBack.fillRect(0,0,800,600);


    try{
      URL url = getClass().getResource("FlappyBirdBackground.png");
      image = ImageIO.read(url);
    }catch(Exception e) {
      System.out.println("Background failed to load!");
    }
    




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
    else if(state.equals("HighScore"))
    {
      paintHighScore(graphToBack);
    }

    
    twoDGraph.drawImage(back, null, 0, 0);
  }

  public void paintMenu(Graphics window) {
    window.drawImage(image, 0, 0, 800, 600, null);
    window.setFont(new Font("Trebuchet MS", Font.BOLD, 100));
    window.setColor(Color.CYAN);
    window.drawString("FLAPPY BIRD", 20, 100);
    window.setFont(new Font("Trebuchet MS", Font.PLAIN, 30));
    window.drawString("Press I for instructions", 200, 360);
    window.drawString("Press SPACE to begin", 210, 400);
    window.setColor(Color.YELLOW);
    window.drawString("Press S for Leader Board", 195, 440);
    bird.draw(window);
  }
  public void paintHighScore(Graphics window) {
    window.drawImage(image, 0, 0, 800, 600, null);
    window.setFont(new Font("Trebuchet MS", Font.BOLD, 75));
    window.setColor(Color.YELLOW);
    window.drawString("High Score", 180, 100);

    window.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
    window.drawString("Name:", 200, 200);
    window.drawString("Score:", 480, 200);

    //put the sorted top names here

    window.setFont(new Font("Trebuchet MS", Font.BOLD, 30));
    window.drawString("Press S again to go back", 200, 550);
    bird.draw(window);
  }

  public void paintInstructions(Graphics window) {
    window.drawImage(image, 0, 0, 800, 600, null);
    window.setFont(new Font("Trebuchet MS", Font.BOLD, 90));
    window.setColor(Color.CYAN);
    window.drawString("INSTRUCTIONS", 15, 100);
    window.setFont(new Font("Trebuchet MS", Font.PLAIN, 30));
    window.drawString("Use the SPACE bar to help Flappy avoid the pipes.", 20, 270);
    window.drawString("Pick up the golden apples for extra points!", 30, 300);

    window.drawString("Press I again to go back", 190, 360);
    window.drawString("Press SPACE to begin", 210, 400);
  }

  public void paintGame(Graphics window) {
    //Randomly generated background
    window.drawImage(image, 0, 0, 800, 600, null);


    
    

    //do we still need this
    if(backgroundColorChange%5==0){
      //paintBackground(window);
    }

    bird.move();
    if(bird.getY() > 570 - bird.getHeight() || bird.getY() < 0)
    {
      gameState = "gameOver";
    }

    
    //Gate generation and stuff
    if(gates.getGateArray().size() == 0)
    {
      gates.generateGate();
    }
    
    if(gates.getGateArray().get(gates.getGateArray().size()-1).getBlocks().get(1).getX() <= 350)
    {
      gates.generateGate();
    }
    if(gates.getGateArray().get(0).getBlocks().get(1).getX() <= -120)
    {
      //increase score when gate despawns
      score++;
      gates.deleteGate();
    }

    gates.moveAll();

     //collision between bird and Gates
    for(int i = 0; i < gates.getGateArray().size(); i++) {
      for(Block b : gates.getBlocks(i)) {
        if(bird.collidesWith(b))
        {
          gameState = "gameOver";
        }
      }
    }

    appleMove++;
    backgroundColorChange++;

    if(appleMove%2==0){
    apple.moves(-1);
    }
    else{
      apple.moves(1);
    }
    apple.scrollLeft();

    if(apple.getX()<=-100){
      apple.respawn();
    }
    
    //bird collision with apple test
    if(bird.collidesWith(apple)) {
      //score increases
      score += 2;
      apple.respawn();
    }

    gates.draw(window);
    bird.draw(window);
    apple.draw(window);

    window.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
    window.setColor(Color.WHITE);
    window.drawString(score + "", 400, 100);
  }

  public void paintGameOver(Graphics window) {
    window.drawImage(image, 0, 0, 800, 600, null);
    window.setFont(new Font("Trebuchet MS", Font.BOLD, 100));
    window.setColor(Color.RED);
    window.drawString("Game Over", 100, 200);
    window.setFont(new Font("Trebuchet MS", Font.PLAIN, 40));
    window.setColor(Color.CYAN);
    window.drawString("Your score was: " + score, 205, 300);
    window.setColor(Color.ORANGE);
    window.drawString("Press SPACE to restart", 180, 350);
     window.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
     window.setColor(Color.GREEN);
    window.drawString("If you want to save your score, Press H", 200, 400);
    //Enlarge final score
    //Prompt player if they would like to save their score
    //Write input nickname into file and save
    //*Maybe prompt for nickname before game starts so that player doesn't have to input it every time they restart? Since games can run really short* 
  }

  public void reset() {
    bird.reset();
    apple.respawn();
    score = 0;
    for(int i = gates.getGateArray().size() - 1; i >= 0; i--) {
      gates.deleteGate();
    }
  }

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
  }

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
    if (e.getKeyCode() == KeyEvent.VK_H)
    {
      //Key H for saving your nickname as well as showing leader board?, do we have one?
      System.out.print("Enter Your Name to Save Your Score: ");
      Scanner input = new Scanner(System.in);
      name = input.nextLine();
      System.out.println("Thanks we recorded your name " + name + " along with your score of " + score); 
      scoreSaver.addNewScore(score, name);
      gameState = "menu";
      reset();
      
    }
    if (e.getKeyCode() == KeyEvent.VK_S)
    {
      if(gameState.equals("menu"))
        gameState = "HighScore";
      else if(gameState.equals("HighScore"))
        gameState = "menu";
      
      
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
        Thread.currentThread().sleep(5);
        repaint();
      }
    }catch(Exception e)
    {
    }
  }
}



