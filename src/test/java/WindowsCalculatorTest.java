import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;



public class WindowsCalculatorTest {

    private HashMap<Character, String> keys = new HashMap<Character, String>() {{
        put('C', "81");
        put('+', "93");
        put('-', "94");
        put('*', "92");
        put('/', "91");
        put('=', "121");
        put('.', "84");
        put('0', "130");
        put('1', "131");
        put('2', "132");
        put('3', "133");
        put('4', "134");
        put('5', "135");
        put('6', "136");
        put('7', "137");
        put('8', "138");
        put('9', "139");
    }};
    private static RemoteWebDriver driver;



    @BeforeClass
    public static void start() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "C:/windows/system32/calc.exe");
        driver = new RemoteWebDriver(new URL("http://localhost:9999"), capabilities);
        driver.findElementByClassName("CalcFrame");
    }

    @AfterClass
    public static void stop() {
        driver.close();
    }

    @Before
    public void resetCalculator() { clickButton('C'); }

    private void clickButton(char caption) {
        driver.findElementById(keys.get(caption)).click();
    }

    private String getEditFieldContent() {
        return driver.findElementById("150").getAttribute("Name");
    }

    private double randomNumber() {
        return truncateDouble(ThreadLocalRandom.current().nextDouble(0, 100));
    }

    private void typeNumber(Double number) {
        for (char c: number.toString().toCharArray()) {
            clickButton(c);
        }
    }

    private double truncateDouble(double number) {
        DecimalFormat formatter = new DecimalFormat("#,#####");
        return Double.valueOf(formatter.format(number));
    }

    @Test
    public void addTwoNumbers() throws InterruptedException {
        double first = randomNumber();
        double second = randomNumber();
        double expected_result = truncateDouble(first + second);

        typeNumber(first);
        clickButton('+');
        typeNumber(second);
        clickButton('=');
        Assert.assertEquals(expected_result, truncateDouble(Double.parseDouble(getEditFieldContent())), 0);
    }

    @Test
    public void substractTwoNumbers() throws InterruptedException {
        double first = randomNumber();
        double second = randomNumber();
        double expected_result = truncateDouble(first - second);

        typeNumber(first);
        clickButton('-');
        typeNumber(second);
        clickButton('=');
        Assert.assertEquals(expected_result, truncateDouble(Double.parseDouble(getEditFieldContent())), 0);
    }

    @Test
    public void multiplyTwoNumbers() throws InterruptedException {
        double first = randomNumber();
        double second = randomNumber();
        double expected_result = truncateDouble(first * second);

        typeNumber(first);
        clickButton('*');
        typeNumber(second);
        clickButton('=');
        Assert.assertEquals(expected_result, truncateDouble(Double.parseDouble(getEditFieldContent())), 0);
    }

    @Test
    public void divideTwoNumbers() throws InterruptedException {
        double first = randomNumber();
        double second = randomNumber();
        double expected_result = truncateDouble(first / second);

        typeNumber(first);
        clickButton('/');
        typeNumber(second);
        clickButton('=');
        double res = truncateDouble(Double.parseDouble(getEditFieldContent()));
        Assert.assertEquals(expected_result, res, 0);
    }
}
