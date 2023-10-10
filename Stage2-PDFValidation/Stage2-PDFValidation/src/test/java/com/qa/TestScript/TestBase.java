package com.qa.TestScript;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Driver;
import java.util.concurrent.TimeUnit;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.Pages.FileWebpage;
import com.qa.Resources.Configuration.ConfigProperties;

import io.github.bonigarcia.wdm.WebDriverManager;
public class TestBase 
{
	WebDriver driver;
	
	
	@BeforeClass	
	public static void beforeClass()
	{
		ConfigProperties.initializePropertyFile();
	}
	
	
	@Test
	public void ReadPDFFile() throws IOException, InterruptedException
	{
		
		String sourceOfFile =  ConfigProperties.property.getProperty("SourceFile");
		if(sourceOfFile.equals("local"))
		{
			File file = new File("C:\\Users\\CHITTURI\\Downloads\\Resume - Copy.pdf");
			FileInputStream inputFile = new FileInputStream(file);
			PDDocument pdfdocument = PDDocument.load(inputFile);
			System.out.println("No of pages inside the document is "+pdfdocument.getPages().getCount());
			PDFTextStripper pdftextstriper = new PDFTextStripper();
			String documentname = pdftextstriper.getText(pdfdocument);
			System.out.println(documentname);
			Assert.assertTrue(documentname.startsWith("DHARSHINY S"));
			pdfdocument.close();
			inputFile.close();
		}
		else
		{
			String path = ConfigProperties.property.getProperty("ChromeDriverPath");			
			System.setProperty("webdriver.chrome.driver",path);
			driver = new ChromeDriver();
			driver.get("https://file-examples.com/index.php/sample-documents-download/");
			driver.manage().window().maximize();			
			Thread.sleep(10000);
			FileWebpage filewebpage = new FileWebpage(driver);
			//filewebpage.clickBrowseFileButton();
			Thread.sleep(10000);
			filewebpage.clickDocumentsButton();
			driver.quit();
		}
		
		
	}
	
}
