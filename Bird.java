import java.awt.Graphics;
import java.io.File;
import java.awt.Image;
import java.awt.Color;
import java.net.URL;
import javax.imageio.ImageIO;


public class Bird extends MovingThing {

  private double ySpeed;
  private Image image;
  private final double GRAVITY = 0.15;
  //private final int speedFactor = 6;
  //private SFXPlayer flap = new SFXPlayer();

  public Bird() {
    this(50,250);
  }

  public Bird(int x, int y) {
    this(x, y, 80, 50);
  }

  public Bird(int x, int y, int w, int h) {
    super(x, y, w, h);
    ySpeed = 0;
    try{
      URL url = getClass().getResource("bird.png");
      image = ImageIO.read(url);
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
  public void moveLeft(){
      setX(getX()-1);
  }

	public void draw(Graphics window) {
    //window.setColor(Color.GREEN); 
    window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
    //window.fillRect(getX(), getY(), getWidth(), getHeight());
  }

  public void flap() {
    double absSpeed = Math.abs(ySpeed);
    /*if(ySpeed < -1*speedFactor)
      ySpeed = speedFactor*5/absSpeed;
    else if(ySpeed < speedFactor)
      ySpeed = speedFactor - absSpeed;
    else
      ySpeed = speedFactor*5/absSpeed;*/
    ySpeed = 6.5;
  }

  public void reset() {
    setX(50);
    setY(250);
    ySpeed = 0;
  }

  public boolean collidesWith(Object obj) {
    MovingThing mt = (MovingThing) obj;
    //confirms that bird's right side is within object horizontal
    return ((getX() + getWidth() >= mt.getX() && getX() <= mt.getX() || (getX() <= mt.getX() + mt.getWidth() && getX() + getWidth() >= mt.getX() + mt.getWidth())) && ((getY() + getHeight() >= mt.getY() && getY() <= mt.getY()) || (getY() <= mt.getY() + mt.getHeight() && getY() + getHeight() >= mt.getY() + mt.getHeight())));
  }

  public String toString() {
    return super.toString() + " " + ySpeed;
  }
}