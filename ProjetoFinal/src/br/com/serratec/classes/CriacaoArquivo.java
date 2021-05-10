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
				for (Dependente d : f.getDependente())
					out.append(String.format("Dependente: %s%n", d));
			}
			out.append("Criado em 10/05/2021\n\n");
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
