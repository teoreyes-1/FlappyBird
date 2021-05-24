
import javax.swing.JFrame;
import java.awt.Component;

public class FlappyBird extends JFrame
{
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	public FlappyBird()
	{
		super("FLAPPY BIRD");
		setSize(WIDTH,HEIGHT);

		FlappyWorld theGame = new FlappyWorld();
		((Component)theGame).setFocusable(true);

		getContentPane().add(theGame);

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main( String args[] )
	{
		FlappyBird run = new FlappyBird();
	}
}