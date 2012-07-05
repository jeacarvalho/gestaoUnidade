package geradorDados;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import utilitarios.UtilGestaoUnidade;

import entidades.SolicitacaoMudanca;

public class PegaListaSMSetor {
	private static final String SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO="|";
	
	@Test
	public void testAtualizaListaSM(){
		String linhaEmProcessamento;
		try {
			File e = new File(UtilGestaoUnidade.getInstanciaUtilitario().getHome() + "/Documentos/Downloads/exportacaoSM.txt");
			BufferedReader in = new BufferedReader(new FileReader(e));
			in.readLine(); //despreza primeira linha com cabeçalho, sempre gerado pelo SGI
			GerenciadorPersistencia persistidor = new GerenciadorPersistencia();
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

				SM.setProjetoSM(linhaEmProcessamento.substring(0, linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)));
				linhaEmProcessamento = linhaEmProcessamento.substring(linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)+1,linhaEmProcessamento.length());

				SM.setEstadoSM(linhaEmProcessamento.substring(0, linhaEmProcessamento.indexOf(SEPARADOR_CAMPOS_ARQUIVO_APROPRIACAO)));
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
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 


		
	}

	
}
