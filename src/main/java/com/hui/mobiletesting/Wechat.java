package com.hui.mobiletesting;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.MobileBy;
import java.net.MalformedURLException;
import java.util.List;

public class Wechat {

	public String acct;
	public String passwd;
	public WebDriver driver;
	public String message;
	public String contact;

	private Wechat(String acct, String passwd, WebDriver driver) {
		super();
		this.acct = acct;
		this.passwd = passwd;
		this.driver = driver;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public WebDriver getWechatLogin() throws MalformedURLException, InterruptedException {
		System.out.println("start ..login");
		// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(5000);
		driver.findElement(By.id("com.tencent.mm:id/ctm")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("com.tencent.mm:id/bqc")).click(); // 用微信号
		Thread.sleep(3000);
		driver.findElements(By.id("com.tencent.mm:id/he")).get(0).sendKeys(acct);
		driver.findElements(By.id("com.tencent.mm:id/he")).get(1).sendKeys(passwd);
		driver.findElement(By.id("com.tencent.mm:id/bqd")).click();
		Thread.sleep(8000);
		List<WebElement> prompt = driver.findElements(By.id("com.tencent.mm:id/akt"));
		if (!prompt.isEmpty()) {
			prompt.get(0).click();
		}

		return driver;
	}

	public void toBack() throws InterruptedException {
		while (true) {
			WebElement back;
			Thread.sleep(2000);
			List<WebElement> back0 = driver.findElements(By.id("com.tencent.mm:id/hb"));
			List<WebElement> back1 = driver.findElements(By.id("com.tencent.mm:id/hj"));
			if (back0.size() > 0) {
				back = back0.get(0);
				back.click();
			} else if (back1.size() > 0) {
				back = (WebElement) back1.get(0);
				back.click();
			} else
				System.out.println("未找到");
			break;

		}

	}

	public int process() throws Exception {

		WebDriver driver = search();
		boolean isNoResult = driver.findElements(MobileBy.AndroidUIAutomator("new UiSelector().text(\"用户不存在\")"))
				.size() > 0;
		// TODO 频繁的判断

		boolean toomuch = false;

		if (isNoResult) {
			System.out.println("查询结果不存在");
			toBack();
			return 0;
		} else if (toomuch) {
			System.out.println("操作频繁了");
			driver.findElement(By.id("com.tencent.mm:id/akt")).click();
			toBack();
			return 2;
		} else {
			driver.findElement(By.id("com.tencent.mm:id/am8")).click();
			Thread.sleep(3);
			List<WebElement> msgInput = driver.findElements(By.id("com.tencent.mm:id/cqy"));
			if (msgInput.size() > 0) {
				msgInput.get(0).clear();
				msgInput.get(0).sendKeys(message);
				Thread.sleep(2000);
				driver.findElement(By.id("com.tencent.mm:id/h1")).click();
			}
		}
		toBack();
		return 1;
	}

	public WebDriver search() throws Exception {
		if (contact == null) {
			throw new Exception("contact 不存在");
		}
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement searchButtone = wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("搜索")));
		searchButtone.click();
		Thread.sleep(2000);
		driver.findElement(By.id("com.tencent.mm:id/he")).sendKeys(contact);
		;
		boolean isFriends = driver.findElements(By.id("com.tencent.mm:id/ahc")).size() > 0;
		if (isFriends)
			System.out.println("重复");
		else
			driver.findElements(By.className("android.widget.LinearLayout")).get(6).click();
		Thread.sleep(3000);
		return driver;

	}

	public static void main(String[] args) throws Exception {
		WebDriver mydriver;
		AppiumSetup myappium = new AppiumSetup("4.4.2", null);
		System.out.println(myappium.udid);
		mydriver = myappium.getConnectFactory();
		Wechat wt = new Wechat("xxxxx", "xxxxxxxxxx", mydriver);  //你的账号和密码
		wt.getWechatLogin();
		wt.setContact("1034002766");
		wt.setMessage("hello");
		wt.process();

	}

}