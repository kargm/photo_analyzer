import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import java.io.File;
import java.io.IOException;

public class analyzer {

	private void showFiles(File[] files) throws IOException {
		try{
	    for (File file : files) {
	        if (file.isDirectory()) {
	            System.out.println("Directory: " + file.getName());
	            showFiles(file.listFiles()); // Calls same method again.
	        } else {
	            System.out.println("File: " + file.getName());
	            Metadata metadata = ImageMetadataReader.readMetadata(file);
	            System.out.println(metadata);
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
		System.out.println(files);
		showFiles(files);
	}

}
