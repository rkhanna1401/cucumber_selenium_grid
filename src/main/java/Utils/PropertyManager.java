package Utils;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class PropertyManager {
	
	  private static Properties configProperties;

	    public static Properties getPropertyHelper(String propertyFileName)  {
	    	FileReader reader;
			try {
				reader = new FileReader("configs"+  File.separator +propertyFileName+".properties");
				configProperties=new Properties(); 
				configProperties.load(reader);
			} catch (Exception e) {
				e.printStackTrace();
			}  
	     
	        return configProperties;
	    }


}
