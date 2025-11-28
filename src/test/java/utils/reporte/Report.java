package utils.reporte;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

public class Report {
    private static Supplier<ExtentTest> supplier;

    private Report() {
    }

    static void bind(Supplier<ExtentTest> s) {
        supplier = s;
    }

    private static ExtentTest T() {
        return supplier == null ? null : supplier.get();
    }

    public static void info(String msg) {
        if (T() != null)
            T().info(msg);
    }

    public static void pass(String msg) {
        if (T() != null)
            T().pass(msg);
    }

    public static void fail(String msg) {
        if (T() != null)
            T().fail(msg);
    }

    public static void warn(String msg) {
        if (T() != null)
            T().warning(msg);
    }

    public static void screenshot(WebDriver driver, String name) {
        if (T() == null || driver == null)
            return;
        try {
            byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            File folder = new File("target/extent/screenshots");
            folder.mkdirs();
            String ts = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
            File file = new File(folder, name + "_" + ts + ".png");
            Files.write(file.toPath(), bytes);
            T().addScreenCaptureFromPath("./screenshots/" + file.getName());
        } catch (Exception e) {
            warn("No se pudo tomar screenshot: " + e.getMessage());
        }
    }
}
