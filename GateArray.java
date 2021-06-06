
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

  public void draw(Graphics window)
  {
    for(Gate x : gates)
    {
      x.draw(window);
    }
  }
}
