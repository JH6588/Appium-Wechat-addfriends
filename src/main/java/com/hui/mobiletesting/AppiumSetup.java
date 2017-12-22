package com.hui.mobiletesting;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class AppiumSetup {
	private static final String URL = "http://127.0.0.1:4723/wd/hub";
	String platformVersion;
	String udid;

	public AppiumSetup(String platformVersion, String udid) {
		this.platformVersion = platformVersion;
		this.udid = udid;

	}

	public WebDriver getConnectFactory() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "Android Emulator");
		capabilities.setCapability("platformVersion", platformVersion);
		capabilities.setCapability("appPackage", "com.tencent.mm");
		capabilities.setCapability("appActivity", "com.tencent.mm.ui.LauncherUI");
		if (udid != null) {
			capabilities.setCapability("udid", udid);
		}
		WebDriver driver = new AndroidDriver(new URL(URL), capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

}
