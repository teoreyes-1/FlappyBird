import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.GraphicsEnvironment;


public class FlappyWorld extends Canvas implements KeyListener, Runnable
{

  private Bird bird;

  private BufferedImage back;
  private Apple apple;
  private int backgroundColorChange;
  private int appleMove;
  private String flap;
  private String fall;
  private String hit;
  private String scoreUp;
  private String whoosh;
  private String gameState;
  private Block block;
  private GateArray gates;


  public FlappyWorld()
  {
    bird = new Bird();
    apple = new Apple(500,300);
    setBackground(Color.black);
    appleMove = 1;
    gameState = "menu";
    block = new Block();
    gates = new GateArray();

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
    else if(state.equals("results")) {
      paintResults(graphToBack);
    }
    
    
    twoDGraph.drawImage(back, null, 0, 0);
  }

  public void paintMenu(Graphics window) {
    window.setFont(new Font("Trebuchet MS", Font.BOLD, 100));
    window.setColor(Color.CYAN);
    window.drawString("FLAPPY BIRD", 100, 100);
    window.setFont(new Font("Trebuchet MS", Font.PLAIN, 30));
    window.drawString("Press I for instructions", 200, 360);
    window.drawString("Press SPACE to begin", 210, 400);
    bird.draw(window);
  }

  public void paintInstructions(Graphics window) {
    window.setFont(new Font("Trebuchet MS", Font.BOLD, 100));
    window.setColor(Color.CYAN);
    window.drawString("INSTRUCTIONS", 95, 100);
    window.setFont(new Font("Trebuchet MS", Font.PLAIN, 30));
    window.drawString("Use the SPACE bar to help Flappy avoid the pipes.", 120, 270);
    window.drawString("Pick up the golden apples for extra points!", 150, 300);

    window.drawString("Press I again to go back", 190, 360);
    window.drawString("Press SPACE to begin", 210, 400);
  }

  public void paintGame(Graphics window) {
    //Randomly generated background
    if(backgroundColorChange%5==0){
      for(int i = 0;i<30;i++){
        int red = (int) (Math.random()*50)+30;
        int green = (int) Math.random()*100+150;
        int blue = (int) Math.random()*100+150;
        Color clr =  new Color(red, green, blue);
        window.setColor(clr);
        int width = (int)(Math.random()*60)+40;
        int height = (int)(Math.random()*60)+40;
        int x = (int)(Math.random()*800);
        int y = (int)(Math.random()*800);
        window.fillRect(x,y,width,height);
      }
      
    }

    bird.move();
    if(bird.getY() > 570 - bird.getHeight()) {
      //bonk ceiling block, lose game
      bird.setY(570 - bird.getHeight());
      bird.setSpeed(0);
      //gameState = "results";
    }
    if(bird.getY() < 0) {
      //bonk floor block, lose game
      bird.setY(0);
      bird.setSpeed(0);
      //gameState = "results";
    }
    
    
    gates.draw(window);
    //gates.generateGate();
    
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
      gates.deleteGate();
    }

    gates.moveAll();
    
    
    //eventually randomly generate apples and begin moving them left

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
      apple.setX(900);
      int randy = (int) (Math.random()*500+50);
      apple.setY(randy);
    }
    
    bird.draw(window);
    apple.draw(window);
  }

  public void paintResults(Graphics window) {
    //Enlarge final score
    //Prompt player if they would like to save their score
    //Write input nickname into file and save
    //*Maybe prompt for nickname before game starts so that player doesn't have to input it every time they restart? Since games can run really short*
    gameState = "menu";
  }


  public void keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_SPACE)
    {
      //Space key for starting game and controlling bird
      if(gameState.equals("menu"))
        gameState = "game";
      else if(gameState.equals("game"))
        bird.flap();
      else if(gameState.equals("results"))
        gameState = "results";
    }
    if (e.getKeyCode() == KeyEvent.VK_I)
    {
      //Key I for an instructions screen or an alternative button for extra functions
      if(gameState.equals("menu"))
        gameState = "instructions";
      else if(gameState.equals("instructions"))
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



