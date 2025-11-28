import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ProductPage;
import pages.LoginPage;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import org.testng.Assert;

public class ProductTest {
        public WebDriver driver;
        public WebDriverWait wait;
        LoginPage loginPage;
        ProductPage productPage;

        @BeforeTest(alwaysRun = true)
        public void inicio() {
                System.out.println("--- INICIAN PRUEBAS DE INVENTARIO ---");
        }

        @Parameters("navegador")
        @BeforeMethod(alwaysRun = true)
        public void setup(@Optional("chrome") String navegador) {
                switch (navegador.toLowerCase()) {
                        case "chrome":
                                ChromeOptions options = new ChromeOptions();
                                options.addArguments("start-maximized");
                                options.addArguments("--disable-extensions");
                                options.addArguments("--disable-notifications");
                                options.setAcceptInsecureCerts(true);
                                driver = new ChromeDriver(options);
                                break;
                        case "firefox":
                                FirefoxOptions optionsF = new FirefoxOptions();
                                optionsF.addArguments("start-maximized");
                                optionsF.addArguments("--disable-extensions");
                                optionsF.addArguments("--disable-notifications");
                                driver = new FirefoxDriver(optionsF);
                                break;
                        case "edge":
                        default:
                                EdgeOptions optionsE = new EdgeOptions();
                                optionsE.addArguments("start-maximized");
                                optionsE.addArguments("--disable-extensions");
                                optionsE.addArguments("--disable-notifications");
                                driver = new EdgeDriver(optionsE);
                }

                wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                loginPage = new LoginPage(driver);
                productPage = new ProductPage(driver);
                driver.manage().window().maximize();
                loginPage.goToUrl("https://www.saucedemo.com/");
                System.out.println("Ingresar a la página de SAUCE DEMO");
        }

        @AfterMethod(alwaysRun = true)
        public void close() {
                driver.quit();
        }

        @AfterTest(alwaysRun = true)
        public void fin() {
                System.out.println("--- FINALIZAN PRUEBAS DE INVENTARIO ---");
        }

        @Test(groups = { "INVENTARIO", "REGRESION", "EXITO", "SMOKE" })
        public void inventario_Completo() {
                loginPage.loginCompleto("standard_user", "secret_sauce");

                Assert.assertEquals(productPage.obtenerNombreProducto1(), "Sauce Labs Backpack");
                System.out
                                .println("Se valida que el primer producto es: " + productPage
                                                .obtenerNombreProducto1());

                Assert.assertEquals(productPage.obtenerNombreProducto2(), "Sauce Labs Bike Light");
                System.out
                                .println("Se valida que el segundo producto es: " + productPage
                                                .obtenerNombreProducto2());

                Assert.assertEquals(productPage.obtenerNombreProducto3(), "Sauce Labs Bolt T-Shirt");
                System.out
                                .println("Se valida que el tercer producto es: " + productPage
                                                .obtenerNombreProducto3());

                Assert.assertEquals(productPage.obtenerNombreProducto4(), "Sauce Labs Fleece Jacket");
                System.out
                                .println("Se valida que el cuarto producto es: " + productPage
                                                .obtenerNombreProducto4());

                Assert.assertEquals(productPage.obtenerNombreProducto5(), "Sauce Labs Onesie");
                System.out
                                .println("Se valida que el quinto producto es: " + productPage
                                                .obtenerNombreProducto5());

                Assert.assertEquals(productPage.obtenerNombreProducto6(), "Sauce Labs T-Shirt (Red)");
                System.out
                                .println("Se valida que el sexto producto es: " + productPage
                                                .obtenerNombreProducto6());
        }
}
