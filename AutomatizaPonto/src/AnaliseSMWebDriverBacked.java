import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;

public class AnaliseSMWebDriverBacked extends SeleneseTestCase {
	Selenium selenium;
	@Before
	public void setUp() throws Exception {
	    FirefoxProfile firefoxProfile = new FirefoxProfile();

	    firefoxProfile.setPreference("browser.download.folderList",2);
	    firefoxProfile.setPreference("browser.download.manager.showWhenStarting",false);
	    firefoxProfile.setPreference("browser.download.dir","/home/01553360702/apoio/arquivosdia");
	    firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/text");
		
		
		WebDriver driver = new FirefoxDriver(firefoxProfile);
		String baseUrl = "https://sgi.portalcorporativo.serpro/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		//selenium.start();
	}

	@Test
	public void testAnaliseSMWebDriverBacked() throws Exception {
		selenium.open("/");
		selenium.type("id=tbUserId", "01553360702");
		selenium.type("id=tbSenha", "01osrevdm");
		selenium.click("id=btnAvancar");
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("menu");
		selenium.click("link=Solicita");
//		selenium.selectFrame("relative=up");
		selenium.selectFrame("xpath=//*[@id=\"subMenu\"]");
//		selenium.selectFrame("principal");
		//selenium.click("link=Selecionar Solicitação de Mudança");
		
		selenium.click("xpath=/html/body/div/div/div/ul/span/ul[3]/ul/li/a");
		selenium.waitForPageToLoad("30000");
		selenium.removeSelection("id=lstEstado", "label=Todos");
		selenium.addSelection("id=lstEstado", "label=Em Andamento");
		selenium.click("id=cmdConfirmar");
		selenium.waitForPageToLoad("30000");
		selenium.click("css=img[alt=\"Editar esta solicitação de mudança\"]");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=linkHistorico");
		selenium.click("css=a");
		selenium.waitForPageToLoad("30000");
		selenium.click("css=tr.tdHeader6 > td.tdFF > img[alt=\"Editar esta solicitação de mudança\"]");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=linkHistorico");
		selenium.selectFrame("relative=up");
		selenium.selectFrame("menu");
		selenium.click("link=Sair");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}