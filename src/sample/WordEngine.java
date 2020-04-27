package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class WordEngine {
  private File msgFile = new File("./src/sample/messages.txt");
  private Map<String, Integer> bag = new HashMap<>();
  private NavigableMap<String, Integer> allWordsByKey = new TreeMap<>();
  int lineNum;

  protected void loadFile() {
    String line = null;
    try {
      BufferedReader fileReader = new BufferedReader(new FileReader(msgFile));
      while ((line = fileReader.readLine()) != null) {
        createTreeMap(line);
        lineNum = lineNum + 1;
      }
      fileReader.close();
    } catch (IOException io) {
      io.printStackTrace();
    }
    buildBag();
  }

  private void createTreeMap(String curLine) {
    String[] allTokens = curLine.trim().split("\\s+");
    List<String> wordPairs = new ArrayList<String>();
    for (int i = 0; i < allTokens.length - 1; ++i) {
      wordPairs.add(allTokens[i] + ", " + allTokens[i + 1]);
    }
    for (String token : wordPairs) {
      allWordsByKey.merge(token, 1, Integer::sum);
    }
  }

  private void buildBag() {
    bag = allWordsByKey.entrySet().stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
            (e1, e2) -> e1, LinkedHashMap::new));
  }

  protected void compare(String input) {
    double sumOfOccourance = 0;
    boolean found = false;
    for (Map.Entry<String, Integer> entry : allWordsByKey.entrySet()) {
      String wordsOfPairs = entry.getKey();

      try {
        String first = wordsOfPairs.substring(0, input.length());

        if (first.equals(input)) {
          int valueOfOccurrence = entry.getValue();
          sumOfOccourance += valueOfOccurrence;
        }
      } catch (StringIndexOutOfBoundsException e) {

      }
    }

    for (Map.Entry<String, Integer> entry : allWordsByKey.entrySet()) {
      String wordsOfPairs = entry.getKey();

      try {

        String initial = wordsOfPairs.substring(0, input.length());

        if (initial.equals(input)) {
          double support = entry.getValue() / sumOfOccourance;

          if (support > .65) {
            System.out
                .println("Your next word might be " + entry.getKey().substring(input.length() + 1));
            found = true;
          }
        }
      } catch (StringIndexOutOfBoundsException e) {
      }
    }
    if (found) {
      System.out.println("Your next word might be 'the' ");
      System.out.println("Your next word might be 'this' ");
    } else {
      System.out.println("Your next word might be 'the'.");
      System.out.println("Your next word might be 'this'.");
      System.out.println("Your next word might be 'of'.");
    }

  }
}
