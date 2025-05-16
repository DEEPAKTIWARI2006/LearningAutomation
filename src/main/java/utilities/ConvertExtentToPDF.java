package utilities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v134.page.Page;

public class ConvertExtentToPDF {
	
	public static void ConvertExtentToPDF() throws InterruptedException, FileNotFoundException, IOException {
		Thread.sleep(2000);
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless=new"); // headless mode

		ChromeDriver driver = new ChromeDriver(options);
		DevTools devTools = driver.getDevTools();
		devTools.createSession();

		String reportPath = System.getProperty("user.dir") + "/target/extent-reports/ExtentReport.html";
		driver.get(reportPath);

		devTools.send(Page.enable());
		Page.PrintToPDFResponse pdf = devTools.send(Page.printToPDF(Optional.of(false), // landscape
				Optional.of(false), // displayHeaderFooter
				Optional.of(false), // printBackground
				Optional.empty(), // scale
				Optional.empty(), // paperWidth
				Optional.empty(), // paperHeight
				Optional.empty(), // marginTop
				Optional.empty(), // marginBottom
				Optional.empty(), // marginLeft
				Optional.empty(), // marginRight
				Optional.empty(), // pageRanges
				Optional.empty(), // headerTemplate
				Optional.empty(), // footerTemplate
				Optional.of(false), // preferCSSPageSize
				Optional.empty(), // transferMode
				Optional.of(false), // pageOrigin
				Optional.of(false) // generateTaggedPDF
		));

		byte[] pdfData = Base64.getDecoder().decode(pdf.getData());

		try (FileOutputStream fos = new FileOutputStream(
				System.getProperty("user.dir") + "/target/extent-reports/ExtentReportPDFFormat.pdf")) {
			fos.write(pdfData);
		}

		driver.quit();
		System.out.println("PDF generated successfully.");
	}

}
