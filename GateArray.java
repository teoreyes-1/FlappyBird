
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

//the point of this class is to generate gates every so often
public class GateArray
{
  private ArrayList<Gate> gates;

  
  public GateArray()
  {
    gates = new ArrayList<Gate>();
  }

  public ArrayList<Gate> getGateArray()
  {
    return gates;
  }

  public void generateGate()
  {
    gates.add(new Gate());
  }
  
  public void deleteGate()
  {
    gates.remove(0);
  }
  public void moveAll()
  {
    for(int i = 0; i < gates.size(); i++)
    {
        gates.get(i).moveBoth();
    }
  }

  public ArrayList<Block> getBlocks(int GateIndex)
  {
    return gates.get(GateIndex).getBlocks();
  }
  
  public boolean BirdCollision(Bird bird, ArrayList<Block> blocks)
  {
    return (didCollide(bird, blocks.get(0)) && didCollide(bird, blocks.get(1)));
  }

  public boolean didCollide(Bird bird, Block blocks)
  {
    //System.out.print("yes");
    int x1 = bird.getX();
    int y1 =  bird.getY();
    int x2 =  bird.getX() +  bird.getWidth();
    int y2 =  bird.getY() +  bird.getHeight();

    if( ((x1 <= blocks.getX()) && (blocks.getX() <= x2)) && ((y1 <= blocks.getY()) && (blocks.getY() <= y2)) )
    {
      return true;
    }
    else if( ((x1 <= (blocks.getX() + blocks.getWidth())) && ((blocks.getX() + blocks.getWidth()) <= x2)) && ((y1 <= (blocks.getY() + blocks.getHeight())) && ((blocks.getY() + blocks.getHeight()) <= y2)) )
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  public void draw(Graphics window)
  {
    for(Gate x : gates)
    {
      x.draw(window);
    }
  }
}
