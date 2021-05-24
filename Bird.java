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
  private final double GRAVITY = 1;

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
      image = ImageIO.read(url);
    }catch(Exception e) {
      System.out.println("Bird failed to load!");
    }
  }

  public void setSpeed(double s) {
    ySpeed = s;
  }

  public double getSpeed() {
    return ySpeed;
  }

  public void move() {
    ySpeed -= GRAVITY;
    setY(getY() - ySpeed);
  }

	public void draw(Graphics window) {
    window.setColor(Color.GREEN);
    //window.drawimage(image,getX(),getY(),getWidth(),getHeight(),null);
    window.fillRect(getX(), getY(), getWidth(), getHeight());
  }

  public void flap() {
    ySpeed = 100/Math.abs(ySpeed);
    //playSound(sfx);
  }

  public boolean collidesWith(MovingThing mt) {
    MovingThing mt = (MovingThing) obj;
    return (inLeftBound(mt) || inRightBound(mt)) && (inTopBound(mt) || inBottomBound(mt)) && (getX() + getWidth() >= mt.getX() && getX() <= mt.getX()) && 
  }

  public String toString() {
    return super.toString() + " " + ySpeed;
  }
}