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

//might want to switch to a regular array
  private ArrayList<Block> blocks;


  //Creates the two pillars, and asigns their location, size etc.
  public Gate()
  {
    blocks = new ArrayList<Block>();

    int topBlock = (int)(Math.random()*201)+100; //This works, by generating a number inbetween 200 and 500, gap is 200 pixels big to generate the upper block location.

    blocks.add(new Block(800, 0, 120, topBlock, "TOP", 3));
    blocks.add(new Block(800, topBlock+200, 120, 600-(topBlock+200), "BOTTOM" , 3));
    
  }

  public ArrayList<Block> getBlocks()
  {
    return blocks;
  }

  public void moveBoth()
  {
    for(int i = 0; i < blocks.size(); i++) 
    {
      blocks.get(i).move();
    }
  }
  
  public void draw(Graphics window)
  {
    for(Block x : blocks)
    {
      x.draw(window);
    }
  }
}
