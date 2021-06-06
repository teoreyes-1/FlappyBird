//Class works but does not produce sound in repl.it :(
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class SFXPlayer {
  public static void main(String[] args) {

  }

  public static void playAudio(String filePath)
  {
    try
    {
      File audioPath = new File(filePath);
      if(audioPath.exists())
      {
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioPath);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
        //clip.loop(Clip.LOOP_CONTINUOUSLY);

        //JOptionPane.showMessageDialog(null, "Press OK to stop playing");
      }
      else
      {
        System.out.println("Can't find audio file");
      }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
