import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utilitarios.UtilGestaoUnidade;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;

public class AnaliseSMRC extends SeleneseTestCase {
	static Selenium selenium;
	private static final int SEGUNDOS_ESPERA_TELA=10;
	
	public static void main(String[] args) {
		System.out.println("Iniciando recuperação de informações das SM em andamento");
		UtilGestaoUnidade.getInstanciaUtilitario();
		selenium = UtilGestaoUnidade.getInstanciaSeleniumSGI();
		try {
			testAnaliseSMRC();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Finalizando. Arquivo gerado.");
		selenium.stop();
	}
	

	@Before
	public void setUp() throws Exception {
		

		
		UtilGestaoUnidade.getInstanciaUtilitario();
		selenium = UtilGestaoUnidade.getInstanciaSeleniumSGI();
	}

	@Test
	public static void testAnaliseSMRC() throws Exception {
		
//		EnviaEmail carteiro = new EnviaEmail();
//		carteiro.enviaEmailDestinatario("jose-eduardo.carvalho@serpro.gov.br");
		
		selenium.open("/");
		selenium.type("id=tbUserId", UtilGestaoUnidade.getInstanciaUtilitario().getUsuario());
		selenium.type("id=tbSenha", UtilGestaoUnidade.getInstanciaUtilitario().getPassUsuario());
		selenium.click("id=btnAvancar");
		selenium.waitForPageToLoad("30000");
		
		selenium.selectFrame("menu");
		selenium.click("link=Solicita");
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=up");
		selenium.selectFrame("principal");
		
		//selenium.click("link=Selecionar Solicitação de Mudança");
		selenium.click("xpath=/html/body/div/div/div/ul/span/ul[3]/ul/li/a");
		
		
		selenium.waitForPageToLoad("30000");
		selenium.removeSelection("id=lstEstado", "label=Todos");
		selenium.addSelection("id=lstEstado", "label=Em Andamento");
		selenium.click("id=cmdConfirmar");
		selenium.waitForPageToLoad("30000");
		
		
		FileWriter arquivo = new FileWriter(new File("/home/desenvolvimento/SaidaAnaliseSM.txt"));;
		PrintWriter saida = new PrintWriter(arquivo); 

		//cabeçalho arquivo saida
		saida.println("nrSM;" + "projeto;" + "respSM;" + "AssuntoSM;"+ "dataInicioPrevistaSM;"+ "dataTerminoPrevistaSM/Dt ultima acao;" + "Situacao SM;RespUltimaAcao;TextoUltimaAcao" );
		
		//loop todas SM em andamento
		int smAtual = 2;
		
		//para calculo de diferenca entra datas. O calculo abaixo corresponde a um dia em milissegundos
		int tempoDia = 1000 * 60 * 60 * 24;
		
		while(existeSM(smAtual)){
			
			String nrSM = selenium.getText("xpath=/html/body/div/form/table/tbody/tr[10]/td/table/tbody/tr["+smAtual +"]/td");
			System.out.println("Analisando SM: " + nrSM);
			String projeto = selenium.getText("xpath=/html/body/div/form/table/tbody/tr[10]/td/table/tbody/tr["+smAtual +"]/td[4]");
			String respSM = selenium.getText("xpath=/html/body/div/form/table/tbody/tr[10]/td/table/tbody/tr["+smAtual +"]/td[7]");
			String AssuntoSM = selenium.getText("xpath=/html/body/div/form/table/tbody/tr[10]/td/table/tbody/tr["+smAtual +"]/td[8]");
			String dataInicioPrevistaSM = selenium.getText("xpath=/html/body/div/form/table/tbody/tr[10]/td/table/tbody/tr["+smAtual +"]/td[9]");										
			String dataTerminoPrevistaSM = selenium.getText("xpath=/html/body/div/form/table/tbody/tr[10]/td/table/tbody/tr["+smAtual +"]/td[10]/span");

			
			selenium.click("xpath=/html/body/div/form/table/tbody/tr[10]/td/table/tbody/tr["+smAtual+"]/td[12]/img");
			selenium.waitForPageToLoad("30000");
			selenium.click("id=linkHistorico");
			
			
			
			//a data abaixo sera usada como data atual no inicio do processamento da SM e, durante a varredura das açoes de uma sm, como a data da ultima acao
			Calendar dataUltimaAcao = Calendar.getInstance();
			
			//para calculo de sm vencendo em 2 dias
			Calendar dataFutura = Calendar.getInstance();
			dataFutura.set(dataFutura.get(Calendar.YEAR), dataFutura.get(Calendar.MONTH), dataFutura.get(Calendar.DAY_OF_MONTH) + 2);

		    Calendar dataInicioPrevistaSMCalculo = Calendar.getInstance();
		    int ano1 = Integer.parseInt(dataInicioPrevistaSM.substring(6, 10));
		    int mes1 = Integer.parseInt(dataInicioPrevistaSM.substring(3, 5)) - 1;
		    int dia1 = Integer.parseInt(dataInicioPrevistaSM.substring(0, 2));
		    dataInicioPrevistaSMCalculo.set(ano1, mes1, dia1);
			

		    if(dataInicioPrevistaSMCalculo.getTimeInMillis() < getDataAtual().getTimeInMillis()){
		    	//sm ja deveria ser iniciada e entre no relatorio

			    Calendar dataTerminoPrevistaSMCalculo = Calendar.getInstance();
			    ano1 = Integer.parseInt(dataTerminoPrevistaSM.substring(6, 10));
			    mes1 = Integer.parseInt(dataTerminoPrevistaSM.substring(3, 5)) - 1;
			    dia1 = Integer.parseInt(dataTerminoPrevistaSM.substring(0, 2));
			    dataTerminoPrevistaSMCalculo.set(ano1, mes1, dia1);
			    
			    if(dataTerminoPrevistaSMCalculo.getTimeInMillis() < dataUltimaAcao.getTimeInMillis()){
			    	saida.println(nrSM + ";" + projeto+ ";" +respSM+ ";" + AssuntoSM + ";"+ dataInicioPrevistaSM+ ";"+ dataTerminoPrevistaSM+ ";VENCIDA" );
			    }else{
				    if(dataTerminoPrevistaSMCalculo.getTimeInMillis() < dataFutura.getTimeInMillis()){
				    	saida.println(nrSM + ";" + projeto+ ";" +respSM+ ";" + AssuntoSM + ";"+ dataInicioPrevistaSM+ ";" +dataTerminoPrevistaSM+ ";VENCENDO em 2 dias" );
				    }
			    }

			    int acao = 2;//vou pegar apenas a última ação - a primeira na lista de ações

				try {
					String dataAcaoSM =selenium.getText("xpath=/html/body/div/form/table[2]/tbody/tr[8]/td/div/table/tbody/tr[2]/td/table/tbody/tr["+acao +"]/td/span");
					String respAcaoSM =selenium.getText("xpath=/html/body/div/form/table[2]/tbody/tr[8]/td/div/table/tbody/tr[2]/td/table/tbody/tr["+acao +"]/td[4]");
					String textoAcaoSM =selenium.getText("xpath=/html/body/div/form/table[2]/tbody/tr[8]/td/div/table/tbody/tr[2]/td/table/tbody/tr["+acao +"]/td[6]");
					

				    Calendar dataSMCalculo = Calendar.getInstance();
				    int ano = Integer.parseInt(dataAcaoSM.substring(7, 11));
				    int mes = ConverteMesSGI(dataAcaoSM.substring(3, 6));
				    int dia = Integer.parseInt(dataAcaoSM.substring(0, 2));
				    dataSMCalculo.set(ano, mes, dia);
				    long diferenca = dataUltimaAcao.getTimeInMillis() - dataSMCalculo.getTimeInMillis();
				    
				    long diasDiferenca = diferenca / tempoDia;
				    
				    String msgAcao15Dias = "";
				    String msgAcao28Dias = "";
				    String msgAcao7Dias = "";
				    
				    if(acao == 2){
				    	//essa é a primeira ação encontrada, ou seja, a mais recente. Vou dar mensagens diferente para esse caso e para caso de uma ação antiga ter demorado
					    msgAcao15Dias = "ATENCAO - ULTIMA ACAO INFORMADA A MAIS DE 15 DIAS";
					    msgAcao28Dias = "ATENCAO - ULTIMA ACAO INFORMADA A MAIS DE 28 DIAS";
					    msgAcao7Dias = "ATENCAO - ULTIMA ACAO INFORMADA A MAIS DE 7 DIAS";;
				    }else{
					    msgAcao15Dias = "HOUVE ACAO INFORMADA A MAIS DE 15 DIAS";
					    msgAcao28Dias = "HOUVE ACAO INFORMADA A MAIS DE 28 DIAS";
					    msgAcao7Dias = "HOUVE ACAO INFORMADA A MAIS DE 7 DIAS";;
				    	
				    }
				    
				    if(diasDiferenca > 28){
				    	saida.println(nrSM + ";" + projeto+ ";" +respSM+ ";"  + AssuntoSM + ";"+ dataInicioPrevistaSM+ ";" +dia+"/"+(mes+1)+"/"+ano+ ";"+ msgAcao28Dias + ";"+ respAcaoSM  +";"+ textoAcaoSM );
				    }else{
					    if(diasDiferenca > 15){
					    	saida.println(nrSM + ";" + projeto+ ";" +respSM+ ";"  + AssuntoSM + ";"+ dataInicioPrevistaSM+ ";" +dia+"/"+(mes+1)+"/"+ano+ ";"+ msgAcao15Dias + ";" + respAcaoSM  +";"+ textoAcaoSM );
					    }else{
						    if(diasDiferenca > 7){
						    	saida.println(nrSM + ";" + projeto+ ";" +respSM+ ";"  + AssuntoSM + ";"+ dataInicioPrevistaSM+ ";" +dia+"/"+(mes+1)+"/"+ano+ ";"+ msgAcao7Dias + ";" + respAcaoSM  +";"+ textoAcaoSM );
						    }
					    }
				    }
				    
				    dataUltimaAcao = dataSMCalculo;

				} catch (Exception e) {
					System.out.println("achei acao " +acao +" da sm " + nrSM +" e nao conseguiu pegar data e resp acao");
				}		    
		    }

		    smAtual = smAtual + 1;
			//retorna lista sm
			//selenium.selectFrame("principal");
			selenium.click("xpath=/html/body/div/form/div/table/tbody/tr/th/p/a");
			
			selenium.waitForPageToLoad("30000");
		}
		saida.close();
		arquivo.close();

	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
	
	
	private static boolean existeSM(int ordem){
		
		try {
			
			if (selenium.isElementPresent("xpath=/html/body/div/form/table/tbody/tr[10]/td/table/tbody/tr["+ordem+"]/td")){
				return true;
			}else{
				return espera(selenium,"xpath=/html/body/div/form/table/tbody/tr[10]/td/table/tbody/tr["+ordem+"]/td", SEGUNDOS_ESPERA_TELA);
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public  static Calendar getDataAtual(){
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		calendar.setTime(trialTime);
                return  calendar;
	}
	
	private static int ConverteMesSGI(String mes){
		if(mes.equalsIgnoreCase("jan")){
			return Calendar.JANUARY;
		}else if(mes.equalsIgnoreCase("fev")){
			return Calendar.FEBRUARY;
		}else if(mes.equalsIgnoreCase("mar")){
			return Calendar.MARCH;
		}else if(mes.equalsIgnoreCase("abr")){
			return Calendar.APRIL;
		}else if(mes.equalsIgnoreCase("mai")){
			return Calendar.MAY;
		}else if(mes.equalsIgnoreCase("jun")){
			return Calendar.JUNE;
		}else if(mes.equalsIgnoreCase("jul")){
			return Calendar.JULY;
		}else if(mes.equalsIgnoreCase("ago")){
			return Calendar.AUGUST;
		}else if(mes.equalsIgnoreCase("set")){
			return Calendar.SEPTEMBER;
		}else if(mes.equalsIgnoreCase("out")){
			return Calendar.OCTOBER;
		}else if(mes.equalsIgnoreCase("nov")){
			return Calendar.NOVEMBER;
		}else if(mes.equalsIgnoreCase("dez")){
			return Calendar.DECEMBER;
		}else{
			return 99;
		}
		
	}

	private boolean existeAcao(int ordem){
		
		try {
			
			if (selenium.isElementPresent("xpath=/html/body/div/form/table[2]/tbody/tr[8]/td/div/table/tbody/tr[2]/td/table/tbody/tr["+ordem+"]/td/span")){
				return true;
			}else{
				return espera(selenium,"xpath=/html/body/div/form/table[2]/tbody/tr[8]/td/div/table/tbody/tr[2]/td/table/tbody/tr["+ordem+"]/td/span", 1);
			}
		} catch (Exception e) {
			return false;
		}
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
	
	
}