import java.awt.Graphics;
import java.io.File;
import java.awt.Image;
import java.awt.Color;
import java.net.URL;
import javax.imageio.ImageIO;


public class Bird extends MovingThing {

  private double ySpeed;
  private Image image;
  private Image image2;
  private final double GRAVITY = 0.1;
  private int cycle;

  public Bird() {
    this(50,250);
  }

  public Bird(int x, int y) {
    this(x, y, 80, 50);
  }

  public Bird(int x, int y, int w, int h) {
    super(x, y, w, h);
    cycle = 5;
    ySpeed = 0;
    try{
      URL url = getClass().getResource("bird.png");
      image = ImageIO.read(url);
      URL url2 = getClass().getResource("bird2.png");
      image2 = ImageIO.read(url2);
    }catch(Exception e) {
      System.out.println("Bird failed to load!");
    }
  }

  public void setSpeed(double s) {
    ySpeed = s;
  }

  public void setSpeed(int s) {
    ySpeed = (double)s;
  }

  public int getSpeed() {
    return (int)ySpeed;
  }

  public void move() {
    ySpeed -= GRAVITY;
    setY(getY() - (int)ySpeed);
  }

	public void draw(Graphics window) {
    if(cycle < 30)
      window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
    else
      window.drawImage(image2,getX(),getY(),getWidth(),getHeight(),null);
    cycle++;
  }

  public void flap() {
    double absSpeed = Math.abs(ySpeed);
    ySpeed = 5.5;
    cycle = 0;
  }

  public void reset() {
    setX(50);
    setY(250);
    ySpeed = 0;
  }

  public String toString() {
    return super.toString() + " " + ySpeed;
  }
}