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
    this(x, y, 75, 75);
  }

  public Apple(int x, int y, int w, int h) {
    super(x, y, w, h);
    ySpeed = 0;
    try{
      URL url = getClass().getResource("AppleDrawing2.png");
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
    setX(getX()-2);
  }

  public void moves(int direction) {
    ySpeed = direction;
    move();
  }

	public void draw(Graphics window) {
    window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
  }
  
  public void respawn() {
    setX(900);
    int randy = (int) (Math.random()*500+50);
    setY(randy);
  }

  public String toString() {
    return super.toString() + " " + ySpeed;
  }
}