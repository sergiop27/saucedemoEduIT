import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.ProductPage;
import utils.data.LoginDataReader;
import utils.model.LoginData;
import utils.reporte.ExtentListener;
import utils.reporte.Report;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

@Listeners(ExtentListener.class)
public class LoginTest {
    public WebDriver driver;
    public WebDriverWait wait;
    LoginPage loginPage;
    ProductPage productPage;

    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider(Method method) throws IOException {
        String testName = method.getName();
        LoginData[] allData = LoginDataReader.readLoginData();

        List<Object[]> result = new ArrayList<>();
        for (LoginData data : allData) {
            if (data.getIdCaso() == null)
                continue;

            if (data.getIdCaso().equalsIgnoreCase(testName)) {
                int rep = data.getRepeticiones() > 0 ? data.getRepeticiones() : 1;
                for (int i = 0; i < rep; i++) {
                    result.add(new Object[] { data });
                }
            }
        }

        return result.toArray(new Object[0][0]);
    }

    @BeforeTest(alwaysRun = true)
    public void inicio() {
        System.out.println("--- INICIAN PRUEBAS DE LOGIN ---");
    }

    @Parameters("navegador")
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional("chrome") String navegador) {
        if (navegador == null) {
            navegador = System.getProperty("NAVEGADOR", "CHROME");
        }

        switch (navegador.toLowerCase()) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-maximized");
                options.addArguments("--headless");
                options.addArguments("--disable-extensions");
                options.addArguments("--disable-notifications");
                options.setAcceptInsecureCerts(true);
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                FirefoxOptions optionsF = new FirefoxOptions();
                optionsF.addArguments("start-maximized");
                optionsF.addArguments("--headless");
                optionsF.addArguments("--disable-extensions");
                optionsF.addArguments("--disable-notifications");
                driver = new FirefoxDriver(optionsF);
                break;
            case "edge":
            default:
                EdgeOptions optionsE = new EdgeOptions();
                optionsE.addArguments("start-maximized");
                optionsE.addArguments("--headless");
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
        System.out.println("--- FINALIZAN PRUEBAS DE LOGIN ---");
    }

    @Test(dataProvider = "loginData", groups = { "LOGIN", "REGRESION", "EXITO", "SMOKE" })
    public void login_Exitoso(LoginData data) {
        Report.info("COMIENZA TEST LOGIN EXITOSO: " + data.getDescripcion() + " | USUARIO: " + data.getUsername());
        loginPage.escribirUsuario(data.getUsername());
        loginPage.escribirContrasena(data.getPassword());
        loginPage.hacerClickLogin();
        Report.info("SE COMPLETAN LOS DATOS DE LOGIN Y HACE CLICK EN EL BOTON");

        Assert.assertEquals(productPage.obtenerTituloProductos(), "Products");
        System.out
                .println("Se valida que se loguea correctamente ingresando a la pagina de: " + productPage
                        .obtenerTituloProductos());
        Report.pass("INGRESO CORRECTAMENTE A LA PANTALLA DE INVENTARIO");
        Report.screenshot(driver, "login-exitoso");
    }

    @Test(dataProvider = "loginData", groups = { "LOGIN", "REGRESION", "FALLA" })
    public void login_Fallido_Contrasena(LoginData data) {
        Report.info("COMIENZA TEST LOGIN FALLIDO POR CONTRASEÑA: " + data.getDescripcion() + " | USUARIO: "
                + data.getUsername());
        loginPage.escribirUsuario(data.getUsername());
        loginPage.hacerClickLogin();
        Report.info("SOLO SE COMPLETA EL USUARIO Y SE HACE CLICK EN EL BOTON");

        Assert.assertEquals(loginPage.obtenerMensajeErrorContrasena(), "Epic sadface: Password is required");
        System.out.println(
                "Se valida que la contraseña es obligatoria con el siguiente mensaje: " + loginPage
                        .obtenerMensajeErrorContrasena());
        Report.pass("SE VALIDA MENSAJE DE CONTRASEÑA REQUERIDA");
        Report.screenshot(driver, "login-contrasena-requerida");
    }

    @Test(dataProvider = "loginData", groups = { "LOGIN", "REGRESION", "FALLA" })
    public void login_Fallido_Usuario(LoginData data) {
        Report.info("COMIENZA TEST LOGIN FALLIDO POR USUARIO: " + data.getDescripcion() + " | USUARIO: "
                + data.getUsername());
        loginPage.escribirContrasena(data.getPassword());
        loginPage.hacerClickLogin();
        Report.info("SOLO SE COMPLETA LA CONTRASEÑA Y SE HACE CLICK EN EL BOTON");

        Assert.assertEquals(loginPage.obtenerMensajeErrorUsuario(), "Epic sadface: Username is required");
        System.out.println(
                "Se valida que la contraseña es obligatoria con el siguiente mensaje: " + loginPage
                        .obtenerMensajeErrorUsuario());
        Report.pass("SE VALIDA MENSAJE DE USUARIO REQUERIDO");
        Report.screenshot(driver, "login-usuario-requerido");
    }

    @Test(dataProvider = "loginData", groups = { "LOGIN", "REGRESION", "FALLA" })
    public void login_Fallido_Invalido(LoginData data) {
        Report.info("COMIENZA TEST LOGIN FALLIDO POR CONTRASEÑA INVALIDA: " + data.getDescripcion() + " | USUARIO: "
                + data.getUsername());
        loginPage.escribirUsuario(data.getUsername());
        loginPage.escribirContrasena(data.getPassword());
        loginPage.hacerClickLogin();
        Report.info("SE COMPLETAN LOS DATOS DE LOGIN CON UNA CONTRASEÑA INVALIDAD Y HACE CLICK EN EL BOTON");

        Assert.assertEquals(loginPage.obtenerMensajeErrorInvalido(),
                "Epic sadface: Username and password do not match any user in this service");
        System.out.println(
                "Se valida que la contraseña es obligatoria con el siguiente mensaje: " + loginPage
                        .obtenerMensajeErrorInvalido());
        Report.pass("SE VALIDA MENSAJE DE USUARIO Y CONTRASEÑA NO COINCIDEN");
        Report.screenshot(driver, "login-contrasena-invalida");
    }
}