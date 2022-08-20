package com.Slashdot.TakeHomeTest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SlashdotTest {
	WebDriver wd;
	WebDriverWait wait;

	@BeforeMethod
	public void setupDriver() {

		/*
		 * Using WebDriverManager API to Detect the browser version and setup driver
		 * while execution
		 */
		WebDriverManager.chromedriver().setup();

		wd = new ChromeDriver();

		wd.get("https://slashdot.org/");
		wait = new WebDriverWait(wd, 10);

		wd.manage().window().maximize();
	}

	@Test
	public void testSlashdotPage() {

		// 2. Using Collection List to store all the WebElements of articles on the page
		List<WebElement> articles = wd.findElements(By.xpath("//article"));

		System.out.println("Number of articles on Slashdot page: " + articles.size());

		// 3. Unique icons to be stored in a Collection List
		List<WebElement> uniqueIcons = wd.findElements(By.xpath(
				"//div[@class='main-wrap  has-rail-right'] / div / div/div/article//span[@class='topic']//a/img"));

		for (WebElement elements : uniqueIcons) {
			System.out.println("Icon present: " + elements.getAttribute("title"));
		}

		// 4. Vote for some random option on the daily poll

		// Conditionally waiting for the element to be clickable: voting window
		wait.until(ExpectedConditions
				.elementToBeClickable(By.cssSelector("form[id='pollBooth'] :nth-of-type(4) input[type='radio']")));
		WebElement votingPoll = wd
				.findElement(By.cssSelector("form[id='pollBooth'] :nth-of-type(4) input[type='radio']"));
		votingPoll.click();
		wd.findElement(By.cssSelector("form[id='pollBooth']  div button")).click();

		System.out.println(wd
				.findElement(By.cssSelector(
						"div[class='row split-container'] div div:nth-of-type(4) >div[class='poll-bar-label']"))
				.getText());

		// 5. Return the number of people that have voted for that same option

		// Conditionally waiting for the element to be visible
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(
				"div[class='row split-container'] div div:nth-of-type(4) >div[class='poll-bar-bg'] div div")));
		WebElement totalVotesOnPoll = wd.findElement(By.cssSelector(
				"div[class='row split-container'] div div:nth-of-type(4) >div[class='poll-bar-bg'] div div"));
		System.out.println("total votes on poll: " + totalVotesOnPoll.getText());
	}

	@AfterMethod
	public void tearDown() {
		wd.quit();
	}

}
