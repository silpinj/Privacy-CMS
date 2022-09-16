package Functions;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Given a file with the data, it calculates the hashes and add it to the table.
 * 
 * @author SilviaPinilla
 *
 */

public class Insert {

	/*
	 * Method that initialize the CMS and introduce all data passed by file.
	 */
	public static CMS insertDataCMS(int width, int depth, String filename_inputdata, String filename_outputdata) {
			
		CMS cms = null;
		if (verifyFile(filename_inputdata) == true) {
			cms = new CMS(width,depth);
			File file = new File(filename_inputdata);
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = reader.readLine();
				line = reader.readLine(); //Read two lines because first line is the header
				while(line != null) {
					String freq = line.split("\t")[0];
					String ip = line.split("\t")[1];
					long ip_dec = ip2dec(ip);
					for(int i = 0; i < Integer.valueOf(freq); i++) {
						cms.update(ip_dec);
					}
					line = reader.readLine();
				}
				reader.close();
				saveResults(cms, filename_outputdata);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return cms;
	}
	
	/*
	 * Method to verify if the file exists and if it is readable.
	 */
	public static boolean verifyFile(String filename) {
		File file = new File(filename);
		
		if(file.isFile() && file.canRead()) {
			return true;
		} else {
			System.out.println("File " + filename + " does not exist");
			return false;
		}
	}
	
	/*
	 * Method to convert an IPv4 address into a decimal number.
	 */
	public static long ip2dec(String ip) {
		String octets[] = ip.split("\\.");
		String ip_binary = "";
		for(int i = 0; i < octets.length; i ++) {
			int part = Integer.valueOf(octets[i]);
			ip_binary += Integer.toBinaryString(part);
		}
		long ip_int = Long.parseLong(ip_binary, 2);
		
		return ip_int;
	}
	
	/*
	 * Method to save the table from CMS into a txt file.
	 */
	public static void saveResults(CMS cms, String filename) {
		File file = new File(filename);
		
		try {
			if(file.createNewFile() == false) {
				file.delete();
			}
			file.createNewFile();
			FileWriter writer = new FileWriter(file);

			for(int i = 0; i < cms.width; i++) {
				for(int j = 0; j < cms.depth; j++) {
					
					long value = cms.table[j][i];
					writer.write(String.valueOf(value));
					
					if(String.valueOf(value).length() == 1) {
						writer.write("     ");
					}
					if(String.valueOf(value).length() == 2) {
						writer.write("    ");
					}
					if(String.valueOf(value).length() == 3) {
						writer.write("   ");
					}
					if(String.valueOf(value).length() == 4) {
						writer.write("  ");
					}
					if(String.valueOf(value).length() == 5) {
						writer.write(" ");
					}
					writer.write("\t\t");
				}
				writer.write("\n");
			}
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
