import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class AnaliseSMWebDriver {
	private WebDriver driver;
	private String baseUrl="";
	private StringBuffer verificationErrors = new StringBuffer();
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	@Test
	public void testAnaliseSMWebDriver() throws Exception {
		driver.get("https://sgi.portalcorporativo.serpro/");
		driver.findElement(By.id("tbUserId")).clear();
		driver.findElement(By.id("tbUserId")).sendKeys("01553360702");
		driver.findElement(By.id("tbSenha")).clear();
		driver.findElement(By.id("tbSenha")).sendKeys("02osrevdm");
		driver.findElement(By.id("btnAvancar")).click();
		driver.switchTo().frame(driver.findElements(By.xpath("//*[@id=\"menu\"]")).get(0)); 
		driver.findElement(By.linkText("Solicita")).click();
		
		driver.switchTo().frame(driver.findElements(By.xpath("//*[@id=\"frame\"]")).get(0));
		driver.findElement(By.linkText("Selecionar Solicitação de Mudança")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [removeSelection]]
		// ERROR: Caught exception [ERROR: Unsupported command [addSelection]]
		driver.findElement(By.id("cmdConfirmar")).click();
		driver.findElement(By.cssSelector("img[alt=\"Editar esta solicitação de mudança\"]")).click();
		driver.findElement(By.id("linkHistorico")).click();
		driver.findElement(By.cssSelector("a")).click();
		driver.findElement(By.cssSelector("tr.tdHeader6 > td.tdFF > img[alt=\"Editar esta solicitação de mudança\"]")).click();
		driver.findElement(By.id("linkHistorico")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [selectFrame]]
		// ERROR: Caught exception [ERROR: Unsupported command [selectFrame]]
		driver.findElement(By.linkText("Sair")).click();
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