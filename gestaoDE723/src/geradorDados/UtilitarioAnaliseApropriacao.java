package geradorDados;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import utilitarios.UtilGestaoUnidade;

import entidades.ApropriacaoDia;
import entidades.SolicitacaoMudanca;

public class UtilitarioAnaliseApropriacao {
	private static final String SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO="|";
	public void montaApropriacaoArquivo(GerenciadorPersistencia persistidor){
		String linhaEmProcessamento;
		try {
			File e = new File(UtilGestaoUnidade.getInstanciaUtilitario().getHome() + "/Documentos/Downloads/exportacao.txt");
			BufferedReader in = new BufferedReader(new FileReader(e));
			in.readLine(); //despreza primeira linha com cabeçalho, sempre gerado pelo SGI
			
			while (in.ready()) {
				linhaEmProcessamento = in.readLine();
				
				ApropriacaoDia apropriacao = new ApropriacaoDia();

				apropriacao.setData(normalizaDataApropria(linhaEmProcessamento.substring(0, linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO))));
				linhaEmProcessamento = linhaEmProcessamento.substring(linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)+1,linhaEmProcessamento.length());
				apropriacao.setMinutos(calculaDiferencaEmMinutos("", linhaEmProcessamento.substring(0, linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO))));
				linhaEmProcessamento = linhaEmProcessamento.substring(linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)+1,linhaEmProcessamento.length());
				apropriacao.setMatricula(linhaEmProcessamento.substring(0, linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)));
				linhaEmProcessamento = linhaEmProcessamento.substring(linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)+1,linhaEmProcessamento.length());
				apropriacao.setProjeto(linhaEmProcessamento.substring(0, linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)));
				linhaEmProcessamento = linhaEmProcessamento.substring(linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)+1,linhaEmProcessamento.length());

				apropriacao.setNomeEmpregado(linhaEmProcessamento.substring(0, linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)));
				linhaEmProcessamento = linhaEmProcessamento.substring(linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)+1,linhaEmProcessamento.length());
				apropriacao.setSm(trataCampoSm(linhaEmProcessamento.substring(0, linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO))));
					
				persistidor.persisteClasse(apropriacao);
				
			}
			
			in.close();
			e.delete();//apaga arquivo para na próxima execução os downloads terem o nome esperado
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 


		
	}
	
	
	public void atualizaListaSM(GerenciadorPersistencia persistidor){
		String linhaEmProcessamento;
		try {
			File e = new File(UtilGestaoUnidade.getInstanciaUtilitario().getHome() + "/Documentos/Downloads/exportacao(1).txt");
			BufferedReader in = new BufferedReader(new FileReader(e));
			in.readLine(); //despreza primeira linha com cabeçalho, sempre gerado pelo SGI

			String smAtual="";
			while (in.ready()) {
				linhaEmProcessamento = in.readLine();
				
				SolicitacaoMudanca SM = new SolicitacaoMudanca();
				SM.setAssuntoSM(linhaEmProcessamento.substring(0, linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)));
				linhaEmProcessamento = linhaEmProcessamento.substring(linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)+1,linhaEmProcessamento.length());

				SM.setDataInicioSM(linhaEmProcessamento.substring(0, linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)));
				linhaEmProcessamento = linhaEmProcessamento.substring(linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)+1,linhaEmProcessamento.length());

				SM.setDataFimPrevistaSM(linhaEmProcessamento.substring(0, linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)));
				linhaEmProcessamento = linhaEmProcessamento.substring(linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)+1,linhaEmProcessamento.length());

				SM.setDataFimRealizadaSM(linhaEmProcessamento.substring(0, linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)));
				linhaEmProcessamento = linhaEmProcessamento.substring(linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)+1,linhaEmProcessamento.length());

				SM.setEstadoSM(linhaEmProcessamento.substring(0, linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)));
				linhaEmProcessamento = linhaEmProcessamento.substring(linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)+1,linhaEmProcessamento.length());

				SM.setProjetoSM(linhaEmProcessamento.substring(0, linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)));
				linhaEmProcessamento = linhaEmProcessamento.substring(linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)+1,linhaEmProcessamento.length());

				SM.setNumeroSM(linhaEmProcessamento.substring(0, linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)));
				linhaEmProcessamento = linhaEmProcessamento.substring(linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)+1,linhaEmProcessamento.length());

				SM.setResponsavelSM(linhaEmProcessamento.substring(0, linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)));
				linhaEmProcessamento = linhaEmProcessamento.substring(linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)+1,linhaEmProcessamento.length());

				if(smAtual.equalsIgnoreCase("")){
					//primeira vez no loop. Aqui pq nao consegui fazer o hibernate alterar as SM ja existentes e o arquivo tem duplicata
					smAtual = SM.getNumeroSM();
					persistidor.persisteClasse(SM);
				}else{
					if(!smAtual.equalsIgnoreCase(SM.getNumeroSM())){
						persistidor.persisteClasse(SM);
						smAtual = SM.getNumeroSM();
					}
				}
				
				
				
				
			}
			
			in.close();
			e.delete();//apaga arquivo para na próxima execução os downloads terem o nome esperado
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 


		
	}

	
	
	private String normalizaDataApropria(String data){
		String temp = "";
		String dia = "";
		//dia
		temp = data.substring(0, data.indexOf("/"));
		dia = (temp.length() == 2) ? temp : "0" + temp;
		data = data.substring(data.indexOf("/")+1, data.length());
		//mes
		String mes = "";
		temp = data.substring(0, data.indexOf("/"));
		mes = (temp.length() == 2) ? temp : "0" + temp;
		data = data.substring(data.indexOf("/")+1, data.length());
		//ano
		String ano = "";
		ano = data.substring(0, 4);
		
		
		return ano+mes+dia;
	}
	private int calculaDiferencaEmMinutos(String horaPonto1, String horaPonto2){
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
	
	private String trataCampoSm(String campoSm){
		String resultado="";
		resultado = campoSm.toUpperCase().trim();
		resultado = resultado.replace("SM", "");
		resultado = resultado.replace("-", "");
		resultado = resultado.replace(" ", "");
		
		if(resultado.contains(";")){
			//retiraresultador o % de conclusao
			resultado = resultado.substring(0, resultado.indexOf(";"));
		}

		int tamMaximoCampoSM = (resultado.length() > 6) ? 6 : resultado.length();
		
		resultado = resultado.substring(0, tamMaximoCampoSM).trim();
		return resultado;
	}
}
