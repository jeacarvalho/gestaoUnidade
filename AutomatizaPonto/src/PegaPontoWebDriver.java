import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class PegaPontoWebDriver {
	private WebDriver driver;
	private String baseUrl="";
	
	static final String SEPARADOR_ARQUIVO = "|";
	
	private StringBuffer verificationErrors = new StringBuffer();
	@Before
	public void setUp() throws Exception {
		driver = new HtmlUnitDriver();
		//driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test
	public void testPegaPontoWebDriver() throws Exception {
		String urlSiscop = "http://siscop.portalcorporativo.serpro/";
		driver.get(urlSiscop);
		driver.findElement(By.name("tx_cpf")).clear();
		driver.findElement(By.name("tx_cpf")).sendKeys("01553360702");
		driver.findElement(By.name("tx_senha")).clear();
		driver.findElement(By.name("tx_senha")).sendKeys("10osrevdm");
		driver.findElement(By.name("BotaoOk")).click();
		
		GregorianCalendar calendar = new GregorianCalendar();  
		int diasMes = calendar.get(GregorianCalendar.DAY_OF_MONTH) + 1; 
		
		//int diasMes = 34;
/*		para loop de dias no ponto de um funcionário. Vou pegar até último dia antes de hoje. 
		Como temos outros campos antes do início do ponto em si, temos que somar 3 para cair na linha certa da página de retorno
*/		
		int mes = calendar.get(GregorianCalendar.MONTH); //de 0 (jan) a 11(dez)
		int ano = calendar.get(GregorianCalendar.YEAR);
		
		
		String mesAnalise = Integer.toString(mes + 1) + "/01/" + Integer.toString(ano); //mes +1 pq calendar retorna mes de 0 a 11
		//String mesAnalise = "11/01/2011";
		FileWriter arquivo = new FileWriter(new File("/home/desenvolvimento/SaidaPonto.txt"));;
		PrintWriter saida = new PrintWriter(arquivo); 
		
		saida.println("nome" + SEPARADOR_ARQUIVO + "setor" + SEPARADOR_ARQUIVO+ "matricula" + SEPARADOR_ARQUIVO + "mesPonto" + SEPARADOR_ARQUIVO + "Total");
		
		//todo o DERJO => 0 a 35. DE7CC => 16 a 16
		
/*		 SUPDE/DERJO Lotação: 052048000	
		 SUPDE/DERJO/DE7GP Lotação: 052048001	
		 SUPDE/DERJO/DE7GD Lotação: 052048002	
		 SUPDE/DERJO/DE7CJ Lotação: 052048003	
		 SUPDE/DERJO/DE7CS Lotação: 052048004	
		 SUPDE/DERJO/DE7DF Lotação: 052048005	
		 SUPDE/DERJO/DE7FJ Lotação: 052048006	
		 SUPDE/DERJO/DE7CT Lotação: 052048007	
		 SUPDE/DERJO/DE7CW Lotação: 052048008	
		 SUPDE/DERJO/DE7PF Lotação: 052048009	
		 SUPDE/DERJO/DE7PJ Lotação: 052048010	
		 SUPDE/DERJO/DE7CD Lotação: 052048011	
		 SUPDE/DERJO/DE7MF Lotação: 052048012	
		 SUPDE/DERJO/DE7TR Lotação: 052048013	
		 SUPDE/DERJO/DE7DW Lotação: 052048014	
		 SUPDE/DERJO/DE7CX Lotação: 052048015	
		 SUPDE/DERJO/DE7CC Lotação: 052048016	
		 SUPDE/DERJO/DE7PE Lotação: 052048017	
		 SUPDE/DERJO/DE7DI Lotação: 052048018	
		 SUPDE/DERJO/DE7SD Lotação: 052048019	
		 SUPDE/DERJO/DE7EX Lotação: 052048020	
		 SUPDE/DERJO/DE7IC Lotação: 052048021	
		 SUPDE/DERJO/DE7IM Lotação: 052048022	
		 SUPDE/DERJO/DE7MT Lotação: 052048023	
		 SUPDE/DERJO/DE7SA Lotação: 052048024	
		 SUPDE/DERJO/DE7FA Lotação: 052048025	
		 SUPDE/DERJO/DE7PA Lotação: 052048026	
		 SUPDE/DERJO/DE7PS Lotação: 052048027	
		 SUPDE/DERJO/DE7PD Lotação: 052048028	
		 SUPDE/DERJO/DE7CM Lotação: 052048029	
		 SUPDE/DERJO/DE7PO Lotação: 052048030	
		 SUPDE/DERJO/DE7AT Lotação: 052048031	
		 SUPDE/DERJO/DE7CE Lotação: 052048032	
		 SUPDE/DERJO/DE7SG Lotação: 052048033	
		 SUPDE/DERJO/DE7GI Lotação: 052048034	
		 SUPDE/DERJO/DEMP7 Lotação: 052048035	
*/
		
		
		for (int Lotacao = 16; Lotacao <= 16; Lotacao++) {
				String sLotacao = "";
				if(Lotacao <10){
					sLotacao = "0" + Integer.toString(Lotacao);
				}else{
					sLotacao = Integer.toString(Lotacao);
				}
				driver.get(urlSiscop+"ConListEmp.asp?Dt_Ponto=" + mesAnalise +  "&Regional=09&Lotacao=0520480"+ sLotacao +"&SubLotacao=-1&ValRel=FPonto&auditor=N");
				String setor = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[2]/table[2]/tbody/tr/td[2]/p[2]/font/b")).getText();
				System.out.println("Obtendo dados de: " + setor + " Lotação: 0520480"+ sLotacao );
				
				int funcionario = 2;
				
				
				while(existeFuncionario(funcionario) && funcionario<=100){
					
					//int totalMinutos = 0; //aqui quando for total do mes do funcionario
					driver.findElement(By.xpath("/html/body/table/tbody/tr/td[2]/table[2]/tbody/tr/td[2]/table/tbody/tr["+funcionario+"]/td/font/a")).click();
					String nomeFuncionario = driver.findElement(By.xpath("/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[2]/tbody/tr/td[2]/font")).getText();  
					String mesPonto = driver.findElement(By.xpath("/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table/tbody/tr/td[2]/font")).getText();
					String matricula = driver.findElement(By.xpath("/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[2]/tbody/tr/td[4]/font")).getText();
					
					//para todos os dias
					 
					for (int dia = 4; dia < diasMes; dia++) {
						int totalMinutos = 0; //aqui quando for total por dia
						String pontoExcecao =driver.findElement(By.xpath("/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[2]/tbody/tr[3]/td[6]/font")).getText();
						String horaUm =driver.findElement(By.xpath("/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[2]/font")).getText();
						String codificacaoHoraUm = driver.findElement(By.xpath("/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[3]/font")).getText();
						String horaDois =driver.findElement(By.xpath("/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[4]/font")).getText();
						String codificacaoHoraDois = driver.findElement(By.xpath("/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[5]/font")).getText();						
						String horaTres =driver.findElement(By.xpath("/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[6]/font")).getText();
						String codificacaoHoraTres = driver.findElement(By.xpath("/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[7]/font")).getText();
						String horaQuatro =driver.findElement(By.xpath("/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[8]/font")).getText();
						String codificacaoHoraQuatro = driver.findElement(By.xpath("/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[9]/font")).getText();
						String horaCinco =driver.findElement(By.xpath("/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[10]/font")).getText();
						String horaSeis =driver.findElement(By.xpath("/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[12]/font")).getText();
						String horaSete =driver.findElement(By.xpath("/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[14]/font")).getText();
						String horaOito =driver.findElement(By.xpath("/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[16]/font")).getText();

						if(!horaUm.trim().equalsIgnoreCase("DSR") && !horaUm.trim().equalsIgnoreCase("FE") && !horaUm.equalsIgnoreCase("DDSR")){
							
							//trata chefes
							if (pontoExcecao.trim().equalsIgnoreCase("SIM") && horaUm.trim().equalsIgnoreCase("") && horaDois.trim().equalsIgnoreCase("") && horaTres.trim().equalsIgnoreCase("") &&
								horaQuatro.trim().equalsIgnoreCase("")){
								horaUm = "08:30";
								horaDois = "12:30";
								horaTres = "13:30";
								horaQuatro = "17:30";
							}
							
							//teletrabalhador
							// 
							if (codificacaoHoraUm.trim().equalsIgnoreCase("56") && horaUm.trim().equalsIgnoreCase("") && horaDois.trim().equalsIgnoreCase("") && horaTres.trim().equalsIgnoreCase("") &&
								horaQuatro.trim().equalsIgnoreCase("")){
								horaUm = "08:30";
								horaDois = "12:30";
								horaTres = "13:30";
								horaQuatro = "17:30";
							}

							if(codificacaoHoraUm.trim().equalsIgnoreCase("12")){
								horaUm = "08:30";
							}

							if(codificacaoHoraDois.trim().equalsIgnoreCase("12")){
								horaDois = "12:30";
							}
							
							if(codificacaoHoraTres.trim().equalsIgnoreCase("12")){
								horaTres = "13:30";
							}
							
							if(codificacaoHoraQuatro.trim().equalsIgnoreCase("12")){
								horaQuatro = "17:30";
							}




							if(horaUm.trim().equalsIgnoreCase("") && !horaDois.trim().equalsIgnoreCase("")) {
								horaUm = "08:30";
							}
							if(horaDois.trim().equalsIgnoreCase("") && !horaUm.trim().equalsIgnoreCase("")) {
								horaDois = "12:30";
							}
							if(horaTres.trim().equalsIgnoreCase("") && !horaQuatro.trim().equalsIgnoreCase("") && !horaDois.trim().equalsIgnoreCase("")){
								horaTres = Integer.toString(Integer.parseInt(horaDois.substring(0, 2)) + 1) + ":" + horaDois.substring(3, 5);
							}
							if(horaQuatro.trim().equalsIgnoreCase("") && !horaTres.trim().equalsIgnoreCase("")) {
								horaQuatro = "17:30";
							}
							
							totalMinutos = calculaDiferencaEmMinutos(horaUm, horaDois) + calculaDiferencaEmMinutos(horaTres, horaQuatro) + calculaDiferencaEmMinutos(horaCinco, horaSeis) + calculaDiferencaEmMinutos(horaSete, horaOito) + totalMinutos;    
							
							//aqui para imprimir total por dia do funcionario
							if (totalMinutos == 0){
								totalMinutos = 1;
							}
							saida.println(nomeFuncionario + SEPARADOR_ARQUIVO + setor + SEPARADOR_ARQUIVO +matricula + SEPARADOR_ARQUIVO + mesPonto + SEPARADOR_ARQUIVO + Integer.toString(dia - 3) + SEPARADOR_ARQUIVO + Integer.toString(totalMinutos));
							
						}
					}
					//aqui para imprimir total do mes, ate dois dias atras, do funcionario
/*					if (totalMinutos == 0){
						totalMinutos = 1;
					}
					saida.println(nomeFuncionario + SEPARADOR_ARQUIVO + setor + SEPARADOR_ARQUIVO +matricula + SEPARADOR_ARQUIVO + mesPonto + SEPARADOR_ARQUIVO + Integer.toString(totalMinutos));
*/					

					driver.get(urlSiscop+"ConListEmp.asp?Dt_Ponto=" + mesAnalise +  "&Regional=09&Lotacao=0520480"+ sLotacao +"&SubLotacao=-1&ValRel=FPonto&auditor=N");
					funcionario = funcionario + 1;
				}				
				
		}
		
		saida.close();
		arquivo.close();


		
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean existeFuncionario(int ordem){
		
		try {
			driver.findElement(By.xpath("/html/body/table/tbody/tr/td[2]/table[2]/tbody/tr/td[2]/table/tbody/tr["+ordem+"]/td/font/a"));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	
	public int calculaDiferencaEmMinutos(String horaPonto1, String horaPonto2){
		SimpleDateFormat  ds = new SimpleDateFormat ("HH:mm"); 
		
		if(horaPonto1.trim().equalsIgnoreCase("") || horaPonto1.trim().equalsIgnoreCase("FI")){
			horaPonto1 = "00:00";
		}

		if(horaPonto2.trim().equalsIgnoreCase("")){
			horaPonto2 = "00:00";
		}
		
		Date hora1;
		Date hora2;
		int retorno = 0;
		try {
			hora1 = ds.parse(horaPonto1);
			hora2 = ds.parse(horaPonto2);
			long intervalo = hora2.getTime()-hora1.getTime(); // em milisegundos
			retorno = (int) intervalo/60000;
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		
		return retorno;
		
	}
}