package br.com.serratec.servicos;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import br.com.serratec.classes.Funcionario;

public class CriacaoArquivo {
	public void criarArquivo(List<Funcionario> funcionarios, String salvarArquivo) {
		try {			
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(salvarArquivo));
			out.append("nome;cpf;descontoINSS;descondoIR;salarioLiquido\n");
			for (Funcionario f : funcionarios) {
				f.calcularIR();
				out.append(String.format("%s;%s;%.2f;%.2f;%.2f%n", f.getNome(), f.getCpf(), f.getDescontoINSS(),f.getDescontoIR(), f.calcularSalarioLiquido()));
			}			
			out.close();
			System.out.println("Arquivo criado com sucesso! verifique o seu diretório.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
