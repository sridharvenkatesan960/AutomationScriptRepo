package Utils;

import org.openqa.selenium.WebDriver;

public class commonUtils {

    public static WebDriver launchBrowser() {
        return DriverManager.getDriver();
    }

    public static void navigateToUrl(String url) {
        DriverManager.getDriver().get(url);
        System.out.println("Navigated to: " + DriverManager.getDriver().getCurrentUrl());
    }

    public static String getPageTitle() {
        String pageTitle = DriverManager.getDriver().getTitle();
        System.out.println("Page Title: " + pageTitle);
        return pageTitle;
    }

    public static void closeBrowser() {
        DriverManager.quitDriver();
    }

    public static String getCurrentUrl() {
        return DriverManager.getDriver().getCurrentUrl();
    }
}
