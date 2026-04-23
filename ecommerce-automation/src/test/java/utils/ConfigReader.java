package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader - Reads configuration from config.properties file.
 * Singleton pattern ensures file is loaded only once.
 */
public class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties.load(fis);
            log.info("Config file loaded successfully from: {}", CONFIG_PATH);
        } catch (IOException e) {
            log.error("Failed to load config.properties: {}", e.getMessage());
            throw new RuntimeException("Cannot load config.properties file!", e);
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            log.warn("Property '{}' not found in config.properties", key);
        }
        return value;
    }

    public static String getBaseUrl()         { return get("base.url"); }
    public static String getBrowser()         { return get("browser"); }
    public static int    getImplicitWait()    { return Integer.parseInt(get("implicit.wait")); }
    public static int    getExplicitWait()    { return Integer.parseInt(get("explicit.wait")); }
    public static int    getPageLoadTimeout() { return Integer.parseInt(get("page.load.timeout")); }
    public static boolean isHeadless()        { return Boolean.parseBoolean(get("headless")); }
    public static String getScreenshotDir()   { return get("screenshot.dir"); }
    public static String getReportDir()       { return get("report.dir"); }
    public static String getValidUsername()   { return get("valid.username"); }
    public static String getValidPassword()   { return get("valid.password"); }
    public static String getLockedUsername()  { return get("locked.username"); }
    public static String getLockedPassword()  { return get("locked.password"); }
    public static String getProductName()     { return get("product.name"); }
    public static String getFirstName()       { return get("checkout.firstname"); }
    public static String getLastName()        { return get("checkout.lastname"); }
    public static String getZipCode()         { return get("checkout.zipcode"); }
}
