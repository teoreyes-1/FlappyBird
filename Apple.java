import java.awt.Graphics;
import java.io.File;
import java.awt.Image;
import java.awt.Color;
import java.net.URL;
import javax.imageio.ImageIO;

public class Apple extends MovingThing {

  private int ySpeed;
  private Image image;
  private Image rainbow;
  private int cycle;

  public Apple() {
    this(50,250);
  }

  public Apple(int x, int y) {
    this(x, y, 75, 75);
  }

  public Apple(int x, int y, int w, int h) {
    super(x, y, w, h);
    cycle = 20;
    ySpeed = 0;
    try{
      URL url = getClass().getResource("AppleDrawing2.png");
      image = ImageIO.read(url);
      URL url2 = getClass().getResource("rainbow.png");
      rainbow = ImageIO.read(url2);
    }catch(Exception e) {
      System.out.println("Apple failed to load!");
    }
  }

  public void setSpeed(int s) {
    ySpeed = s;
  }

  public int getSpeed() {
    return ySpeed;
  }

  public void move() {
    setY(getY() + ySpeed);
  }
  
  public void scrollLeft(){
    setX(getX()-2);
  }

  public void moves(int direction) {
    ySpeed = direction;
    move();
  }

	public void draw(Graphics window) {
    if(cycle < 20)
      window.drawImage(rainbow,getX()-925,getY()-30,125,60,null);
    else if(cycle == 20)
      setY((int) (Math.random()*500 + 50));
    else
      window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
    cycle++;
  }
  
  public void respawn(boolean didCollide) 
  {
    if(didCollide) {
      cycle = 0;
      setX(getX() + 900);
    }
    else
    {
      cycle = 20;
      setX(900);
    }
  }

  public String toString() {
    return super.toString() + " " + ySpeed;
  }
}