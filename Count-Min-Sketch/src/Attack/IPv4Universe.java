package Attack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Functions.CMS;
import Functions.Insert;
import Functions.Query;

/**
 * Principal class.
 * Creates the CMS and introduce all data indicated on the input file. 
 * Then queries the CMS with all IPv4 universe and save the result in a file.
 * 
 * Adversarial Model: the attacker knows the CMS table, he/she does NOT initialize it with the data. 
 * But, as the hashes change in each execution of the program and it is needed to have the CMS table before performing
 * the queries, we must do all of these operations in this class. But we must know the real adversarial model.
 * 
 * @author SilviaPinilla
 *
 */

public class IPv4Universe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int width;
		int depth;
		String filename_inputdata;
		String filename_outputcms;
		String filename_results;
		
		if(args.length == 5) {
			width = Integer.valueOf(args[0]);
			depth = Integer.valueOf(args[1]);
			filename_inputdata = args[2];
			filename_outputcms = args[3];
			filename_results = args[4];
		}
		else {
			System.out.println("Querying the whole IPv4 universe of Count-Min Sketch\n...\n"
					+ "You must enter 5 arguments separated by an space: \n  - Width 'w' \n  - Depth 'd' \n  "
					+ "- File with CMS input data \n  - Name of the file to save CMS resulting table \n  "
					+ "- Name of the file to save IPs and count \n \n"
					+ "Please, write filenames between double quotes");
			return ;
		}

		// INSERT DATA INTO CMS
		CMS cms = Insert.insertDataCMS(width, depth, filename_inputdata, filename_outputcms);
			
		if(cms != null) {
			
			try {
				// CREATE FILE TO SAVE RESULTS: if it exists, delete it and create it again.
				File file = new File(filename_results);
				if(file.createNewFile() == false) {
					file.delete();
				}
				file.createNewFile();
				
				FileWriter writer = new FileWriter(file);
				writer.write("Count \t\t IP\n");
				
				// QUERY THE CMS WITH THE IPV4 UNIVERSE AND SAVE IT IN PREVIOUS FILE
				
				/* Calculate average value of count. This is calculated using real values.
				 As explained, it should be calculated with values obtained by the attacker, NOT real values.
				 But, as the mean obtained will be very similar, we can do this
				 To calculate the real average value of the attacker, see Filter.java class . */
				long total_count = 0;
				long count_ip = 0;
				for(int i = 0; i < cms.getWidth(); i++) {
					for(int j = 0; j < cms.getDepth(); j++) {
						if(j == 0) {
							count_ip= cms.getTable()[j][i];
						}
						if(cms.getTable()[j][i] < count_ip) {
							count_ip= cms.getTable()[j][i];
						}
					}
					total_count += count_ip;
				}
				float mean = total_count/cms.getWidth();
				
				// This threshold may be modified depending on the data set and computer capacity.
				float threshold = 10 * mean;
				writer.write(threshold + "\n");				
				// Loop to iterate in all IPv4 universe
				for(int h = 0; h < 256; h++) {
					for(int i = 0; i < 256; i++) {
							for(int j = 0; j < 256; j++) {
								for(int k = 0; k < 256; k++) {
									// All IPs
									String ip = String.valueOf(h) + "." + String.valueOf(i) + "." + String.valueOf(j) + "." + String.valueOf(k);
									long count = Query.obtainCount_element(cms, ip);
									if(count >= threshold) {
										writer.write(String.valueOf(count) + "\t" + String.valueOf(ip) + "\n");
									}
								}
							}
						}
					//To see the execution of the program.
					System.out.println(h);
				}
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}


