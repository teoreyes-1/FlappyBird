
import javax.swing.JFrame;
import java.awt.Component;
//import java.awt.*;

public class FlappyBird extends JFrame
{
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	public FlappyBird()
	{
		super("FLAPPY BIRD");
		setSize(WIDTH,HEIGHT);

    //JLabel background;
    
		FlappyWorld theGame = new FlappyWorld();
		((Component)theGame).setFocusable(true);

		getContentPane().add(theGame);

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

    /*
    ImageIcon img = new ImageIcon("FlappyBirdBackground.png");

    background = new jLabel(" ", img, JLabel.CENTER);
    add(background);*/
	}

	public static void main( String args[] )
	{
		FlappyBird run = new FlappyBird();
	}
}