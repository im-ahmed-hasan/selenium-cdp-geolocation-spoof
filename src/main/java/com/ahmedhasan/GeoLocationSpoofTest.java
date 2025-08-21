package com.ahmedhasan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v138.emulation.Emulation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

public class GeoLocationSpoofTest {

    /*
     * Author: Ahmed Hasan
     * © Ahmed Hasan 2025. All rights reserved.
     *
     * This script uses Chrome DevTools Protocol (CDP) with Selenium 4.35.0
     * to simulate geolocation in the browser — for Kamalganj(Sylhet Region), Bangladesh.
     */

    public static void main(String[] args) {

        // Set Chrome options to allow geolocation without prompt
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--use-fake-ui-for-media-stream");

        // Initialize ChromeDriver
        System.out.println("Starting ChromeDriver...");
        ChromeDriver driver = new ChromeDriver(options);
        try {
            // Create DevTools session for Chrome DevTools Protocol
            DevTools devTools = driver.getDevTools();
            devTools.createSession();

            // --- Set geolocation to Kamalganj(Sylhet Region), Bangladesh ---
            double latitude = 24.353912;
            double longitude = 91.8473433;
            double accuracy = 100.0;
            System.out.println("Setting geolocation to Kamalganj(Sylhet Region), Bangladesh (lat: " + latitude + ", lon: " + longitude + ")...");

            // Override browser geolocation using CDP
            devTools.send(Emulation.setGeolocationOverride(
                    Optional.of(latitude),
                    Optional.of(longitude),
                    Optional.of(accuracy),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()
            ));

            System.out.println("Opening Google Maps...");
            driver.get("https://www.google.com/maps");

            // --- Wait for the map to load and geolocation button to be clickable ---
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            System.out.println("Waiting for the geolocation button to be clickable...");
            WebElement geoButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button[aria-label*='location'], button[aria-label*='Your Location']")
            ));

            System.out.println("Clicking the geolocation button...");
            geoButton.click();
            System.out.println("Geolocation button clicked. The map should now reflect the set location.");
            System.out.println("Waiting 3 seconds to observe the result...");
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
            System.out.println("Thread was interrupted.");
        } finally {
            System.out.println("Test completed. If the map shows Kamalganj(Sylhet Region), Bangladesh, geolocation spoofing was successful.");
            driver.quit();
        }
    }
}