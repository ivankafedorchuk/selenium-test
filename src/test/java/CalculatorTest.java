import java.util.concurrent.ThreadLocalRandom;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CalculatorTest {

    private static WebDriver driver;

    @BeforeClass
    public static void start() {
        System.setProperty("webdriver.chrome.driver", "C:/bin/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("file://C:/calculator/calc.html");
    }

    @AfterClass
    public static void stop() { driver.quit(); }

    @Before
    public void resetCalculator() { clickButton("C"); }

    private int getRandomDigit() {
        return ThreadLocalRandom.current().nextInt(0,  9 + 1);
    }

    private void clickButton(String caption) {
        driver.findElement(By.xpath("//input[@value='" + caption + "']")).click();
    }

    private String getEditFieldContent() {
        return driver.findElement(By.id("resultsbox")).getAttribute("value");
    }

    @Test
    public void addTwoNumbers(){
        int first_op = getRandomDigit();
        int second_op = getRandomDigit();
        int expected_result = first_op + second_op;

        clickButton(Integer.toString(first_op));
        clickButton("+");
        clickButton(Integer.toString(second_op));
        clickButton("=");
        Assert.assertEquals(expected_result, Integer.parseInt(getEditFieldContent()));
    }

    @Test
    public void substractTwoNumbers() {
        int first_op = getRandomDigit();
        int second_op = getRandomDigit();
        int expected_result = first_op - second_op;

        clickButton(Integer.toString(first_op));
        clickButton("-");
        clickButton(Integer.toString(second_op));
        clickButton("=");
        Assert.assertEquals(expected_result, Integer.parseInt(getEditFieldContent()));
    }

    @Test
    public void multiplyTwoNumbers() {
        int first_op = getRandomDigit();
        int second_op = getRandomDigit();
        int expected_result = first_op * second_op;

        clickButton(Integer.toString(first_op));
        clickButton("x");
        clickButton(Integer.toString(second_op));
        clickButton("=");
        Assert.assertEquals(expected_result, Integer.parseInt(getEditFieldContent()));
    }

    @Test
    public void divideTwoNumbers() {
        int first_op = getRandomDigit();
        int second_op = getRandomDigit();
        while (second_op == 0)
            second_op = getRandomDigit();
        double expected_result = (float) first_op / second_op;

        clickButton(Integer.toString(first_op));
        clickButton("/");
        clickButton(Integer.toString(second_op));
        clickButton("=");
        Assert.assertEquals(expected_result, Double.parseDouble(getEditFieldContent()), 0);
    }
}
