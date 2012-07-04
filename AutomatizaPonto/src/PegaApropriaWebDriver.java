import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class PegaApropriaWebDriver {
	private WebDriver driver;
	private String baseUrl="https://sgi.portalcorporativo.serpro";
	private StringBuffer verificationErrors = new StringBuffer();
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testPegaApropriaWebDriver() throws Exception {
		driver.get("https://sgi.portalcorporativo.serpro");
		driver.findElement(By.id("tbUserId")).clear();
		driver.findElement(By.id("tbUserId")).sendKeys("01553360702");
		driver.findElement(By.id("tbSenha")).clear();
		driver.findElement(By.id("tbSenha")).sendKeys("04osrevdm");
		driver.findElement(By.id("btnAvancar")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [selectFrame]]
		driver.switchTo().frame(driver.findElements(By.tagName("iframe")).get(0));
		driver.findElement(By.cssSelector("html body div#portal div#cabecalho div#menu ul#menulist li b a")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [selectFrame]]
		// ERROR: Caught exception [ERROR: Unsupported command [selectFrame]]
		driver.findElement(By.linkText("Registrar Apropriação dos Subordinados")).click();
		driver.findElement(By.id("Ck_Aprop_Periodo")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [select]]
		driver.findElement(By.id("AP_listagemPessoaTarefa__ctl2_btnalterarTarefa")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [selectFrame]]
		// ERROR: Caught exception [ERROR: Unsupported command [selectFrame]]
		driver.findElement(By.linkText("Solicita")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [selectFrame]]
		// ERROR: Caught exception [ERROR: Unsupported command [selectFrame]]
		driver.findElement(By.linkText("Selecionar Solicitação de Mudança")).click();
		driver.findElement(By.id("ParamtipoConsulta_1")).click();
		driver.findElement(By.id("txtIdSM")).clear();
		driver.findElement(By.id("txtIdSM")).sendKeys("340749");
		driver.findElement(By.id("cmdConfirmar")).click();
		driver.findElement(By.id("linkHistorico")).click();
		driver.findElement(By.xpath("//input[@value='...']")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [waitForPopUp]]
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