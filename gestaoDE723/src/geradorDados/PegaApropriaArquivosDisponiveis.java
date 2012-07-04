package geradorDados;


public class PegaApropriaArquivosDisponiveis {
	
	public static void main(String[] args) {
		montaApropriaSmSemBaixarArquivo();
	}
	
	public static void montaApropriaSmSemBaixarArquivo(){
		UtilitarioAnaliseApropriacao util = new UtilitarioAnaliseApropriacao();
		GerenciadorPersistencia persistidor = new GerenciadorPersistencia();
		util.montaApropriacaoArquivo(persistidor);
		//arquivo exportacaoSM já deve estar disponível
		util.atualizaListaSM(persistidor);
		
		//comentario para teste do github
		
	}
}
