import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SentimentAnalyser {
    private final ArrayList<String> stopWordArray = new ArrayList<>(); // Array list that stores stop words
    final HashMap<String, Integer> wordStore = new HashMap<>(); // HashMap that stores unique words and its score

    public void readTextFile() {
        generateStopWordArray();
        ArrayList<ArrayList<String>> wordList = new ArrayList<>(); // Array list that temporary stores group of words
        String lineRead;

        // This buffered reader is used to read the sentiment data set
        try (BufferedReader br = new BufferedReader(new FileReader("Sentiment.txt"))) {
            while ((lineRead = br.readLine()) != null) {
                // When the line is read, it will split its line into array of words
                String[] wordsArray = lineRead.trim().split("\\s+");

                for (int count = 0; count < wordsArray.length; count++) {
                    // This will remove non-alphabetic characters and convert them to lowercase
                    String word = wordsArray[count].replaceAll("[^a-zA-Z]", "").trim().toLowerCase();

                    for (String index : stopWordArray) {
                        if (word.toLowerCase().equals(index)) {
                            // This will remove the stop words and replace it as ""
                            word = wordsArray[count].replaceAll(word, "").toLowerCase();
                        }

                        // Updates each and every, single word in the array
                        wordsArray[count] = word;
                    }
                }

                // This arraylist is used to store the non-empty words
                ArrayList<String> nonEmptyWords = new ArrayList<>();
                for (String word : wordsArray) {
                    if (!word.isEmpty()) {
                        // Adding non-empty words to nonEmptyWords List
                        nonEmptyWords.add(word);
                    }
                }

                if (!nonEmptyWords.isEmpty()) {
                    // Adding non-empty word list to main wordList
                    wordList.add(nonEmptyWords);
                }
            }

            // Set the score to each and every word stored in the wordList
            for (ArrayList<String> wordGroup : wordList) {
                if (wordGroup.size() > 1) {
                    // Check whether the sentence is positive or negative by calling 0th element
                    String type = wordGroup.get(0);
                    for (int i = 1; i < wordGroup.size(); i++) {
                        String word = wordGroup.get(i);
                        // All the words are directed to set their score
                        setScore(word, type);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading the file!");
        }
    }

    public void generateStopWordArray() {
        // This buffered reader is used to read the stop words data set (Common Words)
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("CommonWords.txt"))) {
            String stopWordRead;
            // Adding stop words into the stop word array
            while ((stopWordRead = bufferedReader.readLine()) != null) {
                stopWordArray.add(stopWordRead.toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("Error reading the stop words file!");
        }
    }

    public void setScore(String key, String type) {
        int currentScore = 0;

        if (wordStore.containsKey(key)) {
            // Get the current score for the word
            currentScore = wordStore.get(key);
        }

        // If the word is positive, increment the score by one
        if (type.equals("positive")) {
            currentScore += 1;
        }
        // If the word is negative, increment the score by minus one
        else if (type.equals("negative")) {
            currentScore -= 1;
        }

        // Update the score for the word in the word store hash map
        wordStore.putData(key, currentScore);
    }

    public void getUserInput() {
        Scanner scan = new Scanner(System.in);

        // Get the user input and convert it into lowercase
        System.out.print("Enter a sentence: ");
        String userSentence = scan.nextLine().toLowerCase();

        // Split the user sentence into an array of words by considering the spaces
        String[] UserWordList = userSentence.trim().split("\\s+");

        int sentimentScore = 0;

        for (String word : UserWordList) {
            String key = word;

            // Get the score for each word in the user sentence
            Integer value = wordStore.get(key);

            // By based on all word scores updating the sentiment score
            if (value != null) {
                sentimentScore = sentimentScore + value;
            }
        }

        // If the sentiment score is greater than 0, print it as a positive sentiment
        if (sentimentScore > 0) {
            System.out.println("Positive Sentiment !!!");
        }
        // If the sentiment score is less than 0, print it as a negative sentiment
        else if (sentimentScore < 0) {
            System.out.println("Negative Sentiment !!!");
        }
        // If the sentiment score is 0, print it as a neutral sentiment
        else {
            System.out.println("Neutral Sentiment !!!");
        }

        // Print the sentiment score
        System.out.printf("Sentiment Score : %d", sentimentScore);
    }
}