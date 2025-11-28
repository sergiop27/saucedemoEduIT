package core;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import java.time.Duration;

import org.openqa.selenium.By;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void goToUrl(String URL) {
        driver.get(URL);
    }

    /**
     * Este método devuelve un WebElement a partir de su locator {@code By}
     * Utiliza {@link ExpectedConditions#presenceOfElementLocated(By)} para
     * asegurarse de que el elemento esté presente en el DOM.
     * 
     * @param locator El {@link By} que identifica al elemento.
     * @return El {@link WebElement} encontrado.
     */
    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Este método busca y devuelve un elemento del DOM que además sea visible.
     * Utiliza {@link ExpectedConditions#visibilityOfElementLocated(By)} para
     * asegurarse de que el elemento esté presente y visible en la página.
     * 
     * @param locator El {@link By} que identifica al elemento.
     * @return El {@link WebElement} visible encontrado.
     */
    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Este método hace click sobre un elemento cuando esté listo para recibir
     * interacción.
     * Utiliza {@link ExpectedConditions#elementToBeClickable(By)} para esperar que
     * el elemento
     * sea visible y habilitado antes de hacer click.
     * 
     * @param locator El {@link By} que identifica al elemento.
     */
    protected WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Limpia y escribe texto en un campo de entrada.
     * Busca el elemento con {@link #visible(By)}, borra su contenido y luego envía
     * el texto especificado.
     * 
     * @param locator El {@link By} que identifica al campo de texto.
     * @param texto   El texto que se quiere escribir.
     */
    protected void type(By locator, String texto) {
        WebElement element = visible(locator);
        element.clear();
        element.sendKeys(texto);
    }

    /**
     * Este método hace click sobre un elemento cuando esté listo para recibir
     * interacción.
     * Utiliza {@link ExpectedConditions#elementToBeClickable(By)} para esperar que
     * el elemento
     * sea visible y habilitado antes de hacer click.
     * 
     * @param locator El {@link By} que identifica al elemento.
     */
    protected void click(By locator) {
        WebElement element = clickable(locator);
        element.click();
    }
}
