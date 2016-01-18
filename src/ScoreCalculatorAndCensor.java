

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Calculate sentiment score of texts in a raw csv file and store scores against corresponding entries in the file
 * @author Adithya Balasubramanian
 *
 */

public class ScoreCalculatorAndCensor {

	public static int calculateSentimentScore(String text) {

		text = text.toLowerCase();
		int score = 0;
		for (String term : SentimentScoreLookUp.getMap().keySet()) {

			String pattern = "([^a-zA-Z0-9]" + term + "[^a-zA-Z0-9])";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(text);

			int count = 0;
			while (m.find()) {
				count++;
			}
			score += SentimentScoreLookUp.getMap().get(term) * count;
		}
		return score;
	}

	public static void main(String[] args) {
		try {
			 String inputfile = args[0];
             String outputfile = args[1];
             String line = null;
             FileReader fileReader = new FileReader(inputfile);
             BufferedReader bufferedReader = new BufferedReader(fileReader);
             FileWriter fileWriter = new FileWriter(outputfile);
             fileWriter.write("\r\n");
             while ((line = bufferedReader.readLine()) != null) {
                     String[] temp = line.split(",");
                     //System.out.println(line);
                     fileWriter.write(line + "," + ScoreCalculatorAndCensor.calculateSentimentScore(line) + "\r\n");

             }
             fileWriter.flush();
             fileWriter.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
