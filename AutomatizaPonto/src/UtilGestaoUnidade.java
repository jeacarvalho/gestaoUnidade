import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;


public class UtilGestaoUnidade {

	
	static Properties props = new Properties();
	
	private String usuario;
	private String passUsuario;
	public String getUsuario() {
		return props.getProperty("usuarioTeste");
	}
	public String getPassUsuario() {
		return props.getProperty("passUsuarioTeste");
	}
	
	private static UtilGestaoUnidade utilitario = null;
	
	public static UtilGestaoUnidade getInstanciaUtilitario(){
		if (utilitario == null){
			utilitario = new UtilGestaoUnidade();
			getProperties();
		}
		return utilitario;
	}
	
	private static Selenium selenium = null;

	public static Selenium getInstanciaSeleniumSGI(){
		if (selenium == null){
			selenium = new DefaultSelenium("localhost", 4444, props.getProperty("caminhoFirefox"), props.getProperty("enderecoSGI"));
			selenium.start();
		}
		return selenium;
	}
	

	private static void getProperties(){
		File file = new File("/home/01553360702/configuracoes.properties");	
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			//lê os dados que estão no arquivo
			props.load(fis);  
			fis.close();
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}

	}
	

	
}
