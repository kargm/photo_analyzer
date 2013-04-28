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
	public HashMap<String, Integer> year_map = new HashMap<String, Integer>();

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
						days_map = analyseDates(directory, days_map, months_map, year_map);
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
		System.out.println("Years: " + year_map.toString());
	}

	public void testKeyAndAdd(HashMap<String, Integer> hashmap, String key) {
		if (hashmap.containsKey(key)) {
			int count = (Integer) hashmap.get(key);
			hashmap.put(key, count + 1);
		} else {
			hashmap.put(key, 1);
		}
	}

	public HashMap<String, Integer> analyseDates(ExifSubIFDDirectory dir, 
			HashMap<String, Integer> days_map, 
			HashMap<String, Integer> months_map,
			HashMap<String, Integer> year_map){

		try {
			java.util.Date date = dir.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String days_key = utils.get_weekday_string(cal.get(Calendar.DAY_OF_WEEK));
			String month_key = utils.get_month_string(cal.get(Calendar.MONTH)).toString();
			String year = "" + cal.get(Calendar.YEAR);

			testKeyAndAdd(year_map, year);
			testKeyAndAdd(days_map, days_key);
			testKeyAndAdd(months_map, month_key);
		} catch (NullPointerException e) {
			testKeyAndAdd(days_map, "Unknown");
			testKeyAndAdd(months_map, "Unknown");
		}
		return days_map;
	}
}
