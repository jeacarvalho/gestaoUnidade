import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Teste {
	public static void main(String[] args) throws IOException {
		File e = new File("/home/01553360702/Documentos/Downloads/exportacao(2).txt");
		try {
			BufferedReader in = new BufferedReader(new FileReader(e));
			while(in.ready()){
				System.out.println(in.readLine());
				
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}
}
