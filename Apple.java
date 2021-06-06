import java.awt.Graphics;
import java.io.File;
import java.awt.Image;
import java.awt.Color;
import java.net.URL;
import javax.imageio.ImageIO;

//look at the alien and ship classes in Starfighter for reference of importing images

public class Apple extends MovingThing {

  private int ySpeed;
  private Image image;

  public Apple() {
    this(50,250);
  }

  public Apple(int x, int y) {
    this(x, y, 100, 100);
  }

  public Apple(int x, int y, int w, int h) {
    super(x, y, w, h);
    ySpeed = 0;
    try{
      URL url = getClass().getResource("apple.jpg");
      image = ImageIO.read(url);
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
    setX(getX()-5);
  }

  public void moves(int direction) {
    ySpeed = direction;
    move();
  }

	public void draw(Graphics window) {
    window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
  }

  public boolean collidesWith(Object obj) {
    MovingThing mt = (MovingThing) obj;
    return (getX() + getWidth() >= mt.getX() && getX() <= mt.getX()) && (getX() <= mt.getX() + mt.getWidth() && getX() + getWidth() >= mt.getX() + mt.getWidth()) && (getY() + getHeight() >= mt.getY() && getY() <= mt.getY()) && (getY() <= mt.getY() + mt.getHeight() && getY() + getHeight() >= mt.getY() + mt.getHeight());
  }

  public String toString() {
    return super.toString() + " " + ySpeed;
  }
}