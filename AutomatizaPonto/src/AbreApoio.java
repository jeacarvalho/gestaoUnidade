import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import utilitarios.UtilGestaoUnidade;

public class AbreApoio {
	
	public static WebDriver driver;
	public static String baseUrl;

	
	public static void main(String[] args) {
		
	    FirefoxProfile firefoxProfile = new FirefoxProfile();

	    firefoxProfile.setPreference("browser.download.folderList",2);
	    firefoxProfile.setPreference("browser.download.manager.showWhenStarting",false);
	    firefoxProfile.setPreference("browser.download.dir",UtilGestaoUnidade.getInstanciaUtilitario().getHome() + "/apoio/arquivosdia");
	    firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/text");

	    System.out.println("Iniciando log no Apoio");
		driver = new FirefoxDriver(firefoxProfile);
		//baseUrl = "https://www4.receita.fazenda/ApoioWAS/LogonApoioWAS";
		baseUrl = "http://www.apoiowas.serpro/";
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.get(baseUrl + "/");
		driver.findElement(By.linkText("Intranet e Internet - LPAR K")).click();
		driver.findElement(By.name("cpfTemp")).clear();
		driver.findElement(By.name("cpfTemp")).sendKeys(UtilGestaoUnidade.getInstanciaUtilitario().getUsuario());
		driver.findElement(By.name("senhaTemp")).clear();
		driver.findElement(By.name("senhaTemp")).sendKeys(UtilGestaoUnidade.getInstanciaUtilitario().getPassUsuario());
		driver.findElement(By.name("Image7")).click();
		driver.get("https://www4.receita.fazenda/ApoioWAS/ListFiles?dirName=%2Flog%2Fwas6-prd%2FKPCELL.KPNODE.KPS40K");

		GregorianCalendar calendar = new GregorianCalendar();  
		
		int mes = calendar.get(GregorianCalendar.MONTH) +1;
		String mesString;
		if(mes < 9){
			mesString = "0" + Integer.toString(mes);
		}else{
			mesString = Integer.toString(mes);
		}
			
		
		int ano = calendar.get(GregorianCalendar.YEAR);
		String anoString = Integer.toString(ano).substring(2, 4);
		int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		
		String dataIdentificaArquivoAlvo = anoString + mesString + Integer.toString(dia);

		System.out.println("Iniciando baixa de arquivos");
		for (int i = 1; i <30; i++) {
			//lendo todos os arquivos do 40k
			if(existeArquivo(i)){
				String nomeArquivo = driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr["+ i + "]/td[2]/nobr/a")).getText();
				if(nomeArquivo.contains(dataIdentificaArquivoAlvo) && !nomeArquivo.contains("control-")){
					//esse arquivo é de hoje e interessa ser baixado para leitura do log
					if(existeLink(i-1)){
						//i-1, pq no apoio o link está com indice diferente do nome do arquivo, apesar de estarem na mesma linha da table...
						driver.findElement(By.xpath("(//a[contains(text(),'EBCDIC para UNIX')])[" + (i-1) + "]")).click();	
					}
				}
			}
		}
		
		driver.quit();
		
				
	}
	
	public static boolean existeArquivo(int ordem){
		
		try {
			driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr["+ ordem + "]/td[2]/nobr/a"));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public static boolean existeLink(int ordem){
		
		try {
			driver.findElement(By.xpath("(//a[contains(text(),'EBCDIC para UNIX')])[" + ordem + "]"));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	
	
	
}