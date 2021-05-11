package br.com.serratec.classes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class CriacaoArquivo {
	public void criarArquivo(List<Funcionario> funcionarios) {
		try {			
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("dados.csv"));
			for (Funcionario f : funcionarios) {
				f.calcularIR();
				out.append(String.format("%s;%s;%.2f;%.2f;%.2f%n", f.getNome(), f.getCpf(), f.getDescontoINSS(),f.getDescontoIR(), f.calcularSalarioLiquido()));
			}			
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
