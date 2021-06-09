
import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;

public class Block extends MovingThing
{
  private String direction;
  private Image image1;
  private Image image2;
  private int speed;

  public Block()
  {
    this(100, 100, 100, 100, "TOP", 5);
    
  }
  public Block(int x, int y, int w, int h, String direction, int speed)
  {
    super(x, y, w, h);

    /*
     try{
      URL url = getClass().getResource("GateUpsideDown.png");
      image1 = ImageIO.read(url);
    } catch(Exception e) {
      System.out.println("Gate1 failed to load!");
    }
    try{
      URL url = getClass().getResource("Gate");
      image2 = ImageIO.read(url);
    } catch(Exception e) {
      System.out.println("Gate2 failed to load!");
    }
    */

    this.direction = direction;
    this.speed = speed;
  }

  public void setDirection (String direction)
  {
    this.direction = direction;
  }

  public String getDirection()
  {
    return direction;
  }
  
  public void move()
  {
    setX(getX() - speed);
  }

  public int getSpeed()
  {
    return speed;
  }

  public void setSpeed(int speed)
  {
    this.speed = speed;
  }

  //Still need to change this either into a rectangle, or into a image adn figure out how to make it go upsidedown
  public void draw(Graphics window)
  {
    Color myColor1 = new Color(103, 219, 101);
    window.setColor(myColor1);
    window.fillRect(getX(), getY(), getWidth(), getHeight());
    /*
    if(direction.equals("TOP"))
    {
      //window.drawImage(image1,getX(),getY(),getWidth(),getHeight(),null); 
      Color myColor1 = new Color(103, 219, 101);
      window.setColor(myColor1);
      window.fillRect(getX(), getY(), getWidth(), getHeight());
    }
    else
    {
      //window.drawImage(image2,getX(),getY(),getWidth(),getHeight(),null);
      Color myColor1 = new Color(103, 219, 101);
      window.setColor(myColor1);
      window.fillRect(getX(), getY(), getWidth(), getHeight());
    }
    */
  }

  public String toString()
  {
    return getX() + " " + getY() + " " + getWidth() + " "  + getHeight();
  }

}
