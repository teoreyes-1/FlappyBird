import java.awt.Graphics;
import java.io.File;
import java.awt.Image;
import java.awt.Color;
import java.net.URL;
import javax.imageio.ImageIO;

//look at the alien and ship classes in Starfighter for reference of importing images

public class Bird extends MovingThing {

  private double ySpeed;
  private Image image;
  private final double GRAVITY = 0.25;

  public Bird() {
    this(50,250);
  }

  public Bird(int x, int y) {
    this(x, y, 50, 50);
  }

  public Bird(int x, int y, int w, int h) {
    super(x, y, w, h);
    ySpeed = 0;
    try{
      //URL url = getClass().getResource("bird.jpg");
      //image = ImageIO.read(url);
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
    window.setColor(Color.GREEN);
    //window.drawimage(image,getX(),getY(),getWidth(),getHeight(),null);
    window.fillRect(getX(), getY(), getWidth(), getHeight());
  }

  public void flap() {
    //ySpeed = 50/Math.abs(ySpeed);
    ySpeed = 10;
    //playSound(sfx);
  }

  public boolean collidesWith(Object obj) {
    MovingThing mt = (MovingThing) obj;
    return (getX() + getWidth() >= mt.getX() && getX() <= mt.getX()) && (getX() <= mt.getX() + mt.getWidth() && getX() + getWidth() >= mt.getX() + mt.getWidth()) && (getY() + getHeight() >= mt.getY() && getY() <= mt.getY()) && (getY() <= mt.getY() + mt.getHeight() && getY() + getHeight() >= mt.getY() + mt.getHeight());
  }

  public String toString() {
    return super.toString() + " " + ySpeed;
  }
}