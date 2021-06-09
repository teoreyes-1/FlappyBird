import java.awt.Graphics;
import java.io.File;
import java.awt.Image;
import java.awt.Color;
import java.net.URL;
import javax.imageio.ImageIO;


public class Bird extends MovingThing {

  private double ySpeed;
  private Image spriteOne;
  private Image spriteTwo;
  private final double GRAVITY = 0.1;
  private int cycle;
  //private String fall = "fall.wav";
  //private String flap = "flap.wav";
  //private SoundEffect sfx;

  public Bird() {
    this(50,250);
  }

  public Bird(int x, int y) {
    this(x, y, 80, 50);
  }

  public Bird(int x, int y, int w, int h) {
    super(x, y, w, h);
    wing = new Wing();
    cycle = 5;
    ySpeed = 0;
    //sfx = new SoundEffect();
    try{
      URL urlOne = getClass().getResource("bird1.png");
      spriteOne = ImageIO.read(urlOne);
      URL urlTwo = getClass().getResource("bird2.png");
      spriteTwo = ImageIO.read(urlTwo);
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
      window.drawImage(sprite1,getX(),getY(),getWidth(),getHeight(),null);
    else
      window.drawImage(sprite2,getX(),getY(),getWidth(),getHeight(),null);
    cycle++;
  }

  public void flap() {
    //sfx.playAudio(flap);
    double absSpeed = Math.abs(ySpeed);
    ySpeed = 5.5;
    cycle = 0;
  }

  public void reset() {
    //sfx.playAudio(fall);
    setX(50);
    setY(250);
    ySpeed = 0;
  }

  public String toString() {
    return super.toString() + " " + ySpeed;
  }
}