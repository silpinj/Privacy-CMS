package Attack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that compares the results obtained by the attacker after querying the IPv4 universe
 * in IPv4Universe.java class with the real input data to obtain the percentage of true positives. 
 * Positives are the ones that have a count higher or equal than the threshold established.
 * 
 * This class obtains the probability for IPv4 address with 1 count, 2 count, 3 count ... 
 * above the threshold. Then, it obtains the positive concentration for addresses with the same number 
 * of counts.
 * 
 * @author SilviaPinilla
 *
 */
public class CountStatistics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String filename_positives;
		String filename_truepositives;
		String filename_results;
		
		if(args.length == 3) {
			filename_positives = args[0];
			filename_truepositives = args[1];
			filename_results = args[2];
		} else {
			System.out.println("You must enter 3 arguments:\n  - File with positives obtained and its count "
					+ "\n  - File with true values (File with CMS input data) \n  - File to store the results");
			return ;
		}
		
		try {
			File positives = new File(filename_positives);
			File truepositives = new File(filename_truepositives);
			
			// Create file to save positive concentration
			File results = new File(filename_results);
			FileWriter writer = new FileWriter(results);
			writer.write("Count/IP \t Positive Concentration \n");
			
			// Read both files to save the content in a array.
			// First, read file obtained by the attacker
			BufferedReader reader = new BufferedReader(new FileReader(positives));
			String line = reader.readLine(); //Read two lines because first line is the header
			line = reader.readLine(); //Threshold
			line = reader.readLine();
			
			// ArrayList to save the count to obtain min and max value to iterate through it.
			// Save only unique values (not repeated)
			ArrayList<Long> counts = new ArrayList<Long>();
			
			//ArrayList to save IPv4 addresses and their count
			ArrayList<String[]> array_positives = new ArrayList<String[]> ();
			
			while(line != null) {
				String parts[] = line.split("\t");
				String count = parts[0];
				String ip = parts[1];
				
				String value[] = {count,ip};
				array_positives.add(value);
				
				if(counts.contains(Long.valueOf(count)) == false) {
					counts.add(Long.valueOf(count));
				}
				
				line = reader.readLine();
			}
			reader.close();
			
			//Order counts from minimum to maximum
			Collections.sort(counts);
			
			// Same with file with true values
			BufferedReader reader_true = new BufferedReader(new FileReader(truepositives));
			String line2 = reader_true.readLine();
			line2 = reader_true.readLine(); //Read two lines because first line is the header
			ArrayList<String[]> array_truepositives = new ArrayList<String[]> ();
			
			while(line2 != null) {
				String parts[] = line2.split("\t");
				String count = parts[0];
				String ip = parts[1];
				
				String value[] = {count,ip};
				array_truepositives.add(value);
				
				line2 = reader_true.readLine();
			}
			reader_true.close();
			
			// Comparing positives obtained with true positives and calculate the probability of success.
			// The comparison is done between the elements with the same count above the average.
			long min_value = Collections.min(counts); //min_value ~= threshold
			long max_value = Collections.max(counts);
			
			for (int h = 0; h < counts.size(); h++) {
				
				float num_elements = 0;
				float num_truepositives = 0;
				
				long k = counts.get(h);
				
				// To know the execution of the program
				System.out.println(k + "/" + max_value);
				
				for(int i = 0; i < array_positives.size(); i++) {
					// Obtain count and IP for each element of IPs obtained by the attacker
					Long count_obtained = Long.valueOf(array_positives.get(i)[0]);
					String ip_obtained = array_positives.get(i)[1];
					
					//For all IPs with the same count indicated by k
					if(count_obtained == k) {
					
						// Find IPv4 address of each element on the true values and check if the count is correct.
						for (int j = 0; j < array_truepositives.size(); j++) {
							
							Long count_true = Long.valueOf(array_truepositives.get(j)[0]);
							String ip_true = array_truepositives.get(j)[1];
							
							if(ip_obtained.equals(ip_true)) {
								num_elements += 1;
								// As elements of file with positives have a count >= threshold, the minimum value
								// of all the elements can be considered as the threshold.
								if(count_true >= min_value ) {
									num_truepositives += 1;
								}
							}
						}
					}
				}
				if(num_elements != 0) {
					float probability = (num_truepositives/num_elements);
					writer.write(String.valueOf(k) + "\t\t\t" + String.valueOf(probability) + "\n");
				}
			}
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
