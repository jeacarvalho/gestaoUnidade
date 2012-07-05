package geradorDados;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxProfile;

import utilitarios.UtilGestaoUnidade;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;

public class PegaApropriaJunit extends SeleneseTestCase {
	
	Selenium selenium;
	@Before
	public void setUp() throws Exception {
	    FirefoxProfile firefoxProfile = new FirefoxProfile();

	    firefoxProfile.setPreference("browser.download.folderList",2);
	    firefoxProfile.setPreference("browser.download.manager.showWhenStarting",false);
	    firefoxProfile.setPreference("browser.download.dir",UtilGestaoUnidade.getInstanciaUtilitario().getHome() + "/apoio/arquivosdia");
	    firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/text");
		
		
		selenium = new DefaultSelenium("localhost", 4444, "*custom " + UtilGestaoUnidade.getInstanciaUtilitario().getHome() + "/Documentos/Util/firefox/firefox", "https://sgi.portalcorporativo.serpro/");
		selenium.start();

		
	}

	@Test
	public void testPegaApropriaSGI() throws Exception {
		selenium.open("/");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=tbUserId", UtilGestaoUnidade.getInstanciaUtilitario().getUsuario());
		selenium.type("id=tbSenha", UtilGestaoUnidade.getInstanciaUtilitario().getPassUsuario());
		selenium.click("id=btnAvancar");
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("menu");
		selenium.click("link=Extração");
		selenium.selectFrame("relative=up");
		selenium.selectFrame("principal");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Exportação de dados");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=tipoApropriacao");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=dt_Inicial", "1/6/2008");
		selenium.click("id=nc_apdt_tarefa");
		//selenium.click("id=nc_apdescricao_insumo");
		selenium.click("id=nc_aphora_tarefa");
		//selenium.click("id=nc_apidentificacao_projeto");
		selenium.click("id=nc_apobservacao");
		selenium.click("id=nc_apempregado.matricula");
		selenium.click("id=nc_apempregado.nome");
		selenium.click("id=cmdConfirmar");
		Thread.sleep(5000);
		
		UtilitarioAnaliseApropriacao util = new UtilitarioAnaliseApropriacao();
		GerenciadorPersistencia persistidor = new GerenciadorPersistencia();
		util.montaApropriacaoArquivo(persistidor);
		//arquivo exportacaoSM já deve estar disponível
		util.atualizaListaSM(persistidor);
		
	}
	

	
	
	
	
	private String recuperaDescricaoSM (String nrSm){
		selenium.selectFrame("relative=up");
		selenium.selectFrame("menu");
		selenium.click("link=Solicita");
		selenium.selectFrame("relative=up");
		selenium.selectFrame("principal");
		try {
			espera(selenium, "link=Selecionar Solicitação de Mudança", 5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		selenium.click("link=Selecionar Solicitação de Mudança");
		//selenium.waitForPageToLoad("30000");
		try {
			espera(selenium, "id=ParamtipoConsulta_1", 5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		selenium.click("id=ParamtipoConsulta_1");
		selenium.click("//span[@id='ParamtipoConsulta']/label[2]");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=txtIdSM", nrSm);
		String descSM;
		if(!selenium.isTextPresent("Número inválido!") &&
		!selenium.isTextPresent("Campo obrigatório!") ){
			selenium.click("id=cmdConfirmar");
			selenium.waitForPageToLoad("30000");
			if(selenium.isTextPresent("SOLICITAÇÃO DE MUDANÇA NÚMERO")){
				//estou na pag de uma SM
				descSM = selenium.getValue("id=SM_assunto_sm");
			}else{
				//informei uma SM inexistente no sistema
				descSM = "Sm inexistente no SGI";
			}
			
		}else{
			descSM = "Sem descricao para a sm";
		}
		
		return  descSM;
	}
	

	

	
	public static boolean espera(Selenium selenium, String elemento, int timeout ) throws InterruptedException {
		boolean retorno = false;
		
		for (int second = 0;; second++) {
			if (second >= timeout){
				retorno = false;
				break;
			}
			try { 
				if (selenium.isElementPresent(elemento)) {
					retorno = true;
					break; 
				}
			} catch (Exception e) {}
			Thread.sleep(1000);
		}
		return retorno;
	}	

	
	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}