package utils.reporte;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.qameta.allure.Allure;

public class ExtentListener implements ITestListener {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> tesThreadLocal = new ThreadLocal<>();

    static ExtentTest getTest() {
        return tesThreadLocal.get();
    }

    @Override
    public void onStart(ITestContext context) {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir")
                    + File.separator + "target"
                    + File.separator + "extent"
                    + File.separator + "REPORTE-PRUEBAS.html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("SauceDemo - Reporte");
            spark.config().setDocumentTitle("Reporte de pruebas de la pagina SauceDemo");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("PROYECTO", "SAUCE DEMO");
            extent.setSystemInfo("QA", "SERGIO PACE");
            extent.setSystemInfo("SYSTEM", "WINDOWS");
            extent.setSystemInfo("AMBIENTE", "QA - PRUEBAS");
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String name = result.getMethod().getMethodName();
        ExtentTest test = extent.createTest(name);
        tesThreadLocal.set(test);

        Report.bind(() -> tesThreadLocal.get());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (getTest() != null) {
            getTest().log(Status.PASS, "Test PASÓ correctamente");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (getTest() != null) {
            getTest().log(Status.SKIP, "Test SKIPPEADO");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Report.fail(result.getThrowable() == null
                ? "Fallo"
                : result.getThrowable().toString());

        WebDriver driver = resolveDriver(result);

        if (driver != null) {
            try {
                byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                Allure.getLifecycle().addAttachment(
                        "Screenshot on failure",
                        "image/png",
                        "png",
                        screenshotBytes);
            } catch (Exception e) {
                Report.warn("No se pudo adjuntar screenshot a Allure: " + e.getMessage());
            }

            takeAndAttachScreenshot(driver, result.getMethod().getMethodName());
        }

    }

    private void takeAndAttachScreenshot(WebDriver driver, String baseName) {
        if (getTest() == null)
            return;

        try {
            byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            // Guardamos una copia en disco para que se vea desde el HTML
            File folder = new File("target/extent/screenshots");
            folder.mkdirs();

            String ts = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));

            File file = new File(folder, baseName + "_" + ts + ".png");
            Files.write(file.toPath(), bytes);

            // Ruta relativa desde el HTML a la imagen
            String rel = "./screenshots/" + file.getName();
            getTest().addScreenCaptureFromPath(rel);
        } catch (Exception e) {
            Report.warn("No se pudo adjuntar screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }

    // 👉 5) REFLECTION PARA ENCONTRAR EL driver EN EL TEST
    private WebDriver resolveDriver(ITestResult result) {
        Object test = result.getInstance();
        try {
            Field f = test.getClass().getDeclaredField("driver");
            f.setAccessible(true);
            return (WebDriver) f.get(test);
        } catch (Exception ignored) {
            return null;
        }
    }
}
