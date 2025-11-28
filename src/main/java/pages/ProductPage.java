package pages;

import core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class ProductPage extends BasePage {
    public final By titleProductXpath = By.xpath("//span[@class='title']");
    public final By product1Xpath = By.xpath("//div[normalize-space()='Sauce Labs Backpack']");
    public final By product2Xpath = By.xpath("//div[normalize-space()='Sauce Labs Bike Light']");
    public final By product3Xpath = By.xpath("//div[normalize-space()='Sauce Labs Bolt T-Shirt']");
    public final By product4Xpath = By.xpath("//div[normalize-space()='Sauce Labs Fleece Jacket']");
    public final By product5Xpath = By.xpath("//div[normalize-space()='Sauce Labs Onesie']");
    public final By product6Xpath = By.xpath("//div[normalize-space()='Test.allTheThings() T-Shirt (Red)']");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String obtenerTituloProductos() {
        return find(titleProductXpath).getText();
    }

    public String obtenerNombreProducto1() {
        return find(product1Xpath).getText();
    }

    public String obtenerNombreProducto2() {
        return find(product2Xpath).getText();
    }

    public String obtenerNombreProducto3() {
        return find(product3Xpath).getText();
    }

    public String obtenerNombreProducto4() {
        return find(product4Xpath).getText();
    }

    public String obtenerNombreProducto5() {
        return find(product5Xpath).getText();
    }

    public String obtenerNombreProducto6() {
        return find(product6Xpath).getText();
    }
}
