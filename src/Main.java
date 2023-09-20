public class Main {
    public static void main(String[] args) {
        System.out.println("\t\t\tSentiment Analyser");

        // Calling the sentiment analyser to read file
        SentimentAnalyser sa = new SentimentAnalyser();
        sa.readTextFile();

        // Print the unique word count of the data set
        System.out.println("\nUnique word count - " + sa.wordStore.size() + "\n");

        while (true) {
            // Get the user input and print the result
            sa.getUserInput();
            System.out.println("\n\n");
        }
    }
}
