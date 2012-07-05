import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import utilitarios.UtilGestaoUnidade;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import com.thoughtworks.selenium.Selenium;

public class PegaPonto extends SeleneseTestCase {
	Selenium selenium;
	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://siscop.portalcorporativo.serpro/");
		selenium.start();
		

	}
	
	

	@Test
	public void testPegaPonto() throws Exception {
		//loga
		selenium.open("/");
		selenium.type("name=tx_cpf", UtilGestaoUnidade.getInstanciaUtilitario().getUsuario());
		selenium.type("name=tx_senha", UtilGestaoUnidade.getInstanciaUtilitario().getPassUsuario());
		selenium.click("name=BotaoOk");
		selenium.waitForPageToLoad("30000");
		
		GregorianCalendar calendar = new GregorianCalendar();  
		//int diasMes = calendar.get(GregorianCalendar.DAY_OF_MONTH) + 3; 
		
		int diasMes = 35;
/*		para loop de dias no ponto de um funcionário. Vou pegar até último dia antes de hoje. 
		Como temos outros campos antes do início do ponto em si, temos que somar 3 para cair na linha certa da página de retorno
*/		
		int mes = calendar.get(GregorianCalendar.MONTH); 
		int ano = calendar.get(GregorianCalendar.YEAR);
		
		
		//String mesAnalise = Integer.toString(mes + 1) + "/01/" + Integer.toString(ano);
		String mesAnalise = "08/01/2011";
		FileWriter arquivo = new FileWriter(new File("/home/desenvolvimento/SaidaPonto.txt"));;
		PrintWriter saida = new PrintWriter(arquivo); 
		
		
		try {
			for (int Lotacao = 16; Lotacao <= 16; Lotacao++) {
				String sLotacao = "";
				if(Lotacao <=0){
					sLotacao = "0" + Integer.toString(Lotacao);
				}else{
					sLotacao = Integer.toString(Lotacao);
				}
				
				//selenium.open("/ConListEmp.asp?Dt_Ponto=" + mesAnalise +  "&Regional=09&Lotacao=052048016&SubLotacao=-1&ValRel=FPonto&auditor=N");
				selenium.open("/ConListEmp.asp?Dt_Ponto=" + mesAnalise +  "&Regional=09&Lotacao=0520480"+ sLotacao +"&SubLotacao=-1&ValRel=FPonto&auditor=N");
				selenium.waitForPageToLoad("30000");
				
				int funcionario = 2;
				
				while(existeFuncionario(funcionario) && funcionario<=100){
					selenium.click("xpath=/html/body/table/tbody/tr/td[2]/table[2]/tbody/tr/td[2]/table/tbody/tr["+funcionario+"]/td/font/a");
					selenium.waitForPageToLoad("30000");
					String nomeFuncionario = selenium.getText("xpath=/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[2]/tbody/tr/td[2]/font");
					String mesPonto = selenium.getText("xpath=/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table/tbody/tr/td[2]/font");
					
					//para todos os dias
					 
					for (int dia = 4; dia < diasMes; dia++) {
						String horaUm = selenium.getText("xpath=/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[2]/font");
						String horaDois = selenium.getText("xpath=/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[4]/font");
						String horaTres = selenium.getText("xpath=/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[6]/font");
						String horaQuatro = selenium.getText("xpath=/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[8]/font");
						String horaCinco = selenium.getText("xpath=/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[10]/font");
						String horaSeis = selenium.getText("xpath=/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[12]/font");
						String horaSete = selenium.getText("xpath=/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[14]/font");
						String horaOito = selenium.getText("xpath=/html/body/center/table/tbody/tr/td/table[2]/tbody/tr/td/table[3]/tbody/tr["+dia+"]/td[16]/font");
						if(!horaUm.equalsIgnoreCase("DSR") && !horaUm.equalsIgnoreCase("FE")){
							
							if(horaUm.equalsIgnoreCase("") && !horaDois.equalsIgnoreCase("")) {
								horaUm = "08:30";
							}
							if(horaDois.equalsIgnoreCase("") && !horaUm.equalsIgnoreCase("")) {
								horaDois = "12:30";
							}
							if(horaTres.equalsIgnoreCase("") && !horaQuatro.equalsIgnoreCase("") && !horaDois.equalsIgnoreCase("")){
								horaTres = Integer.toString(Integer.parseInt(horaDois.substring(0, 2)) + 1) + ":" + horaDois.substring(3, 5);
							}
							if(horaQuatro.equalsIgnoreCase("") && !horaTres.equalsIgnoreCase("")) {
								horaQuatro = "17:30";
							}
							
							int totalMinutos = calculaDiferencaEmMinutos(horaUm, horaDois) + calculaDiferencaEmMinutos(horaTres, horaQuatro) + calculaDiferencaEmMinutos(horaCinco, horaSeis) + calculaDiferencaEmMinutos(horaSete, horaOito);    
							
							saida.println(nomeFuncionario + ";" + mesPonto + ";" + Integer.toString(dia - 3) + ";" + horaUm + ";" + horaDois + ";" + horaTres + 
									";" + horaQuatro + ";" + horaCinco + ";" + horaSeis + ";" + horaSete + ";" + horaOito + ";" + Integer.toString(totalMinutos));
						}
					}
					//selenium.open("/ConListEmp.asp?Dt_Ponto=" + mesAnalise +  "&Regional=09&Lotacao=052048016&SubLotacao=-1&ValRel=FPonto&auditor=N");
					selenium.open("/ConListEmp.asp?Dt_Ponto=" + mesAnalise +  "&Regional=09&Lotacao=0520480"+ sLotacao +"&SubLotacao=-1&ValRel=FPonto&auditor=N");
					selenium.waitForPageToLoad("30000");	
					funcionario = funcionario + 1;
				}
			

			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		saida.close();
		arquivo.close();
		
		
	}

	private boolean existeFuncionario(int ordem){
		
		try {
			if (selenium.isElementPresent("xpath=/html/body/table/tbody/tr/td[2]/table[2]/tbody/tr/td[2]/table/tbody/tr["+ordem+"]/td/font/a")){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
	
	public int calculaDiferencaEmMinutos(String horaPonto1, String horaPonto2){
		SimpleDateFormat  ds = new SimpleDateFormat ("HH:mm"); 
		
		if(horaPonto1.equalsIgnoreCase("")){
			horaPonto1 = "00:00";
		}

		if(horaPonto2.equalsIgnoreCase("")){
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