import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class TesteSMPorNumeroWebDriver {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	@Before
	public void setUp() throws Exception {
		driver = new HtmlUnitDriver();
		baseUrl = "https://sgi.portalcorporativo.serpro/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testESMPorNumeroWebDriver() throws Exception {
		driver.get(baseUrl + "/default.aspx?ReturnUrl=%2ftela_frame.aspx");
		// ERROR: Caught exception [ERROR: Unsupported command [selectFrame]]
		driver.findElement(By.linkText("Solicita")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [selectFrame]]
		// ERROR: Caught exception [ERROR: Unsupported command [selectFrame]]
		driver.findElement(By.linkText("Selecionar Solicitação de Mudança")).click();
		driver.findElement(By.id("ParamtipoConsulta_1")).click();
		driver.findElement(By.id("txtIdSM")).clear();
		driver.findElement(By.id("txtIdSM")).sendKeys("359491");
		driver.findElement(By.id("cmdConfirmar")).click();
		driver.findElement(By.id("linkHistorico")).click();
		driver.findElement(By.id("linkPrincipal")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}