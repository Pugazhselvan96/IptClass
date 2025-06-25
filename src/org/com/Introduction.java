package org.com;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Introduction {

	public static void checkUrl(String href) throws URISyntaxException, IOException {
		URL url = new URI(href).toURL();
		URLConnection openConnection = url.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) openConnection;
		httpURLConnection.setRequestMethod("HEAD");
		httpURLConnection.connect();

		int responseCode = httpURLConnection.getResponseCode();
		if (responseCode >= 400) {
			System.out.println("URL: " + href + " - with error code: " + responseCode);
		} else {
			System.out.println("URL: " + href + " - with success code: " + responseCode);
		}

	}
	public void verifyBrokenLinks() throws URISyntaxException, IOException {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://demoqa.com/broken");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		System.out.println("=============== Check Anchor tag Url =============");
		List<WebElement> aLinks = driver.findElements(By.tagName("a"));
		for (WebElement ele : aLinks) {
			@Nullable
			String url = ele.getDomProperty("href");
			if (url.trim().length() >= 1) {
				checkUrl(url);
			}
		}

		System.out.println("=============== Check Image Url =============");

		List<WebElement> imageLinks = driver.findElements(By.tagName("img"));
		for (WebElement ele : imageLinks) {
			@Nullable
			String url = ele.getDomProperty("src");
			if (url.trim().length() >= 1) {
				checkUrl(url);
			}

		}
		driver.quit();

	}
	

	public static void main(String[] args) throws URISyntaxException, IOException {
		Introduction introduction=new Introduction();
		introduction.verifyBrokenLinks();
	}
}