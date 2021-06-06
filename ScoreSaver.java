//currently in beta stages. can write name and score to a file, will need to write methods to determine Top X number of scores, and how to display them.
/*

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ScoreSaver {

  private List<Integer> scoresList;
  private List<String> namesList;

  private String first = "";
  private String secnd = "";
  private String third = "";
  private String fourth = "";
  private String fifth = "";
  private int one = 0;
  private int two = 0;
  private int three = 0;
  private int four = 0;
  private int five = 0;


  public static void addScore(String n, int s) {
    try {
      FileWriter myWriter = new FileWriter("Scores.txt", true);
      myWriter.write(s + " " + n + "\n");
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("ERROR: IOException occured.");
      e.printStackTrace();
    }
  }

  public static void printAllScores(){
    try {
      Scanner scanner = new Scanner(new File("Scores.txt"));
		  while (scanner.hasNextLine()) {
		    System.out.println(scanner.nextLine());
		  }
		  scanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("ERROR: FileNotFoundException occured; Scores.txt not found for printAllScores.");
			e.printStackTrace();
		}
  }

  public static void findTopFive(){

    try {
      Scanner scanner = new Scanner(new File("scores.txt"));
      while (scanner.hasNextLine()){ //fill scoresList
        Scanner lineRead = new Scanner(scanner.nextLine());
        if (lineRead.hasNextInt()) {
          scoresList.add(lineRead.nextInt());
        }
      }
      // for (int i = 0; i < scoresList.size(); i++) {
      //   System.out.println(scoresList.get(i));
      // }
    } catch (FileNotFoundException e) {
      System.out.println("ERROR: FileNotFoundException occured; Scores.txt not found for findTopFive.");
			e.printStackTrace();
		}
  }
  

  //This should traverse the file and pull numbers out of it, then pull the names out of it, find the index of top 5 scores and pull corresponding name index.

  public static void displayTopFive(){
    try {
      Scanner scanner = new Scanner(new File("Scores.txt"));

      
    } catch (FileNotFoundException e) {
      System.out.println("ERROR: FileNotFoundException occured.");
			e.printStackTrace();
		}
  }
}
*/