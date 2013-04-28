import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.util.HashMap;
import java.util.Calendar;
import java.io.File;
import java.io.IOException;

public class analyzer {
	
	// Here the HashMaps will be stored which include image statistics
	public HashMap<String, Integer> days_map = new HashMap<String, Integer>();
	public HashMap<String, Integer> months_map = new HashMap<String, Integer>();
	
	private void getStatistics(File[] files) throws IOException {
		try{
	    for (File file : files) {
	        if (!file.getName().equals(".DS_Store")) { //.DS_Store file causes problems TODO: Find general solution for more problem-files
	    	if (file.isDirectory()) {
	            //.out.println("Directory: " + file.getName());
	            getStatistics(file.listFiles()); // Calls same method again.
	        } else {
	            //System.out.println("File: " + file.getName());
	            Metadata metadata = ImageMetadataReader.readMetadata(file);
	         	// obtain the Exif directory
	            ExifSubIFDDirectory directory = metadata.getDirectory(ExifSubIFDDirectory.class);
	            days_map = analyseDates(directory, days_map, months_map);
	        }
	    }
	        }
	    }
		catch (ImageProcessingException e){
			System.out.println("Unsupported file type. Skipping...");
		}
	}
	
	public analyzer() {}

	public void analyze(String folder) throws ImageProcessingException, IOException {
		System.out.println("Analyzing folder: " + folder);
		File[] files = new File(folder).listFiles();
		getStatistics(files);
		
		System.out.println("Weekdays: " + days_map.toString());
		System.out.println("Months: " + months_map.toString());
	}
	
	public HashMap<String, Integer> analyseDates(ExifSubIFDDirectory dir, 
			HashMap<String, Integer> days_map, 
			HashMap<String, Integer> months_map) {
				
				// query the tag's value
				try {
					java.util.Date date = dir.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					String days_key = utils.get_weekday_string(cal.get(Calendar.DAY_OF_WEEK));
					String month_key = utils.get_month_string(cal.get(Calendar.MONTH)).toString();
					
					// Weekdays
					if (days_map.containsKey(days_key)) {
						int count = (Integer) days_map.get(days_key);
					    days_map.put(days_key, count + 1);
					} else {
						days_map.put(days_key, 1);
					}
					
					// Months
					if (months_map.containsKey(month_key)) {
						int count = (Integer) months_map.get(month_key);
						months_map.put(month_key, count + 1);
					} else {
						months_map.put(month_key, 1);
					}
					
				} catch (NullPointerException e) {
					//Weekdays
					if (days_map.containsKey("Unknown")) {
						int count = (Integer) days_map.get("Unknown");
					    days_map.put("Unknown", count + 1);
					} else {
						days_map.put("Unknown", 1);
					}
					
					//Months
					if (months_map.containsKey("Unknown")) {
						int count = (Integer) months_map.get("Unknown");
					    months_map.put("Unknown", count + 1);
					} else {
						months_map.put("Unknown", 1);
					}
				}
				return days_map;
	}
}
