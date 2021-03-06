import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class ScoreSaver {

  private static List<Integer> scoresList = new ArrayList<Integer>();
  private static List<String> namesList = new ArrayList<String>();
  private static List<Integer> sortedScoresList = new ArrayList<Integer>();
  private static List<String> sortedNamesList = new ArrayList<String>();

  //Display top X
  private static int topPlacements = 5;

  private String userName = "";
  private int userScore = 0;
  
  //why static?
  public void addNewScore(int score, String name){
    try {
      FileWriter myWriter = new FileWriter("scores.txt", true);
      myWriter.write(score + " " + name + "\n");
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("ERROR: FileNotFoundException occured; Scores.txt not found for addNewScore.");
      e.printStackTrace();
    }
  }

  public List<Integer> getSortedScores() {
    return sortedScoresList;
  }

  public List<String> getSortedNames() {
    return sortedNamesList;
  }

  public static void createLeaderboard(boolean gameEnded){

    sortedScoresList.clear();
    sortedNamesList.clear();
    scoresList.clear();
    namesList.clear();

    try {
      Scanner scanner = new Scanner(new File("scores.txt"));
      while (scanner.hasNextLine()){ //fill scoresList
        String lineRead = scanner.nextLine();
        Scanner lineReadScannerForm = new Scanner(lineRead);
        int spaceOf = lineRead.indexOf(' ');
        String name = lineRead.substring(spaceOf);
        scoresList.add(lineReadScannerForm.nextInt());
        namesList.add(name);
      }

      while (scoresList.size() > 0)
      { //in case scores size is too small
        int indexHighest = 0;
        int highestScore = 0;
        for (int i = 0; i < scoresList.size(); i++){
          if (scoresList.get(i) > highestScore) {
            indexHighest = i;
            highestScore = scoresList.get(i);
          }
        }
        sortedScoresList.add(highestScore);
        sortedNamesList.add(namesList.get(indexHighest));
        scoresList.remove(indexHighest);
        namesList.remove(indexHighest);
      } 

    } catch (FileNotFoundException e) {
      System.out.println("ERROR: FileNotFoundException occured; Scores.txt not found for findTopFive.");
			e.printStackTrace();
		}
  }
}