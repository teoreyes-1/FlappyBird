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

public class FlappyWorld extends Canvas implements KeyListener, Runnable
{

  private Bird bird;
  private boolean[] keys;
  private BufferedImage back;

  public FlappyWorld()
  {
    bird = new Bird();
    setBackground(Color.black);

    keys = new boolean[3];


    this.addKeyListener(this);
    new Thread(this).start();

    setVisible(true);
  }

  public void update(Graphics window)
  {
    paint(window);
  }

  public void paint(Graphics window)
  {
    Graphics2D twoDGraph = (Graphics2D)window;

    if(back == null)
      back = (BufferedImage)(createImage(getWidth(),getHeight()));

    Graphics graphToBack = back.createGraphics();

    graphToBack.setColor(Color.BLUE);
    graphToBack.drawString("FlappyWorld ", 25, 50 );
    graphToBack.setColor(Color.BLACK);
    graphToBack.fillRect(0,0,800,600);

    //Randomly generated background
    for(int i = 0;i<30;i++){
      int red = (int) Math.random()*255;
      int green = (int) Math.random()*255;
      int blue = (int) Math.random()*255;
      Color clr =  new Color(red, green, blue);
      graphToBack.setColor(clr);
      int width = (int) Math.random()*150;
      int height = (int) Math.random()*150;
      int x = (int) Math.random()*400+100;
      int y = (int) Math.random()*400+100;
      graphToBack.fillRect(x,y,width,height);
    }
    bird.move();
    if(bird.getY() > 600 - bird.getHeight()) {
      bird.setY(600 - bird.getHeight());
      bird.setSpeed(0);
    }
    bird.draw(graphToBack);

    //
    twoDGraph.drawImage(back, null, 0, 0);
  }


  public void keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_SPACE)
    {
      //Space key for starting game and controlling bird
      //keys[0] = true;
      System.out.println("flap");
      bird.flap();
    }
    if (e.getKeyCode() == KeyEvent.VK_Q)
    {
      //Key Q for quitting instructions screen, going to menu, or something else
      keys[1] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_I)
    {
      //Key I for an instructions screen or an alternative button for extra functions
      keys[2] = true;
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



