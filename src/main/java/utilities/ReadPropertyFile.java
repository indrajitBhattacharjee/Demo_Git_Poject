package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadPropertyFile {
	public static void main(String[] args) throws Exception {
		Properties prop = new Properties();
		FileInputStream input = new FileInputStream("path/to/your/property/file.properties");
		prop.load(input);

		String browser = prop.getProperty("browser");
		String url = prop.getProperty("url");

		System.out.println("Browser: " + browser);
		System.out.println("URL: " + url);
	}
}
