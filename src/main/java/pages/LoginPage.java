package pages;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By usuarioID = By.id("user-name");
    private final By contrasenaID = By.id("password");
    private final By btnLoginID = By.id("login-button");
    private final By errorPassXpath = By.xpath("//h3[normalize-space()='Epic sadface: Password is required']");
    private final By errorUserXpath = By.xpath("//h3[normalize-space()='Epic sadface: Username is required']");
    private final By errorInvalidXpath = By
            .xpath("//h3[contains(text(),'Epic sadface: Username and password do not match a')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void escribirUsuario(String texto) {
        type(usuarioID, texto);
    }

    public void escribirContrasena(String texto) {
        type(contrasenaID, texto);
    }

    public void hacerClickLogin() {
        click(btnLoginID);
    }

    public void loginCompleto(String usuario, String constrasena) {
        type(usuarioID, usuario);
        type(contrasenaID, constrasena);
        click(btnLoginID);
    }

    public String obtenerMensajeErrorContrasena() {
        return visible(errorPassXpath).getText();
    }

    public String obtenerMensajeErrorUsuario() {
        return visible(errorUserXpath).getText();
    }

    public String obtenerMensajeErrorInvalido() {
        return visible(errorInvalidXpath).getText();
    }
}
