import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class Gate 
{
  private int blockSpeed;

  private ArrayList<Blocks> blocks;


  //Creates the two pillars, and asigns their location, size etc.
  public Gate()
  {
    int gapPlace = (int)(Math.random()*301) + 200; //This works, by generating a number inbetween 200 and 500, gap is 200 pixels big to generate the upper block location.

    blocks.add(new Block(600, gapPlace, 50, gapPlace, "TOP"));
    blocks.add(new Block(600, gapPlace+200, 50, 800-(gapPLace+200), "BOTTOM" ));
  }

  public void setBlockSpeed(int blockSpeed)
  {
    this.blockSpeed = blockSpeed;
  }

  public void move()
  {
    setX(getX() - blockSpeed);
  }

  public int getBlockSpeed()
  {
    return blockSpeed;
  }

  public void draw(Graphics window)
  {
    for(ArrayList x : blocks)
    {
      x.draw(window);
    }
  }
}