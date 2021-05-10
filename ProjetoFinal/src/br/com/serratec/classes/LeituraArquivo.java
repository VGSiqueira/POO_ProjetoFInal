package br.com.serratec.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import br.com.serratec.enums.TipoDependente;
import br.com.serratec.exception.CPFException;
import br.com.serratec.exception.DependenteException;

public class LeituraArquivo {	
	public void lerArquivo( List<Funcionario> funcionarios, Scanner scan, String arquivoNome ) {
		try {
			File arquivo = new File(arquivoNome);
			scan = new Scanner(new InputStreamReader(new FileInputStream(arquivo),"UTF-8"));
			
			Funcionario funcionario = null;
			while (scan.hasNext()) {
				String linha = scan.nextLine(); 
				if (!linha.isEmpty()) {
					if (funcionario != null) {
						String[] dadosDependente = linha.split(";");
						String nomeDependente = dadosDependente[0];
						String cpfDependente = dadosDependente[1];
						LocalDate dataAniversario = LocalDate.parse(dadosDependente[2]);
						TipoDependente parentesco = TipoDependente.valueOf(dadosDependente[3]);
						Dependente dependente = new Dependente(nomeDependente, cpfDependente, dataAniversario, parentesco);						
						dependente.verificarIdade();
						dependente.verificarCPF(cpfDependente);
						if(funcionario.getDependente().contains(dependente)) {
							System.out.println("O dependente " + dependente.getNome() + " já foi cadastrado.");
						}else {
							funcionario.adicionarDependente(dependente);
						}						
						continue;
					} else {					
						 String[] dadosFuncionario = linha.split(";");
						 String nomeFuncionario = dadosFuncionario[0];					
						 String cpfFuncionario = dadosFuncionario[1];
						 LocalDate dataAniversario = LocalDate.parse(dadosFuncionario[2]);
						 double salarioBruto = Double.parseDouble(dadosFuncionario[3]);
						 funcionario = new Funcionario(nomeFuncionario,cpfFuncionario, dataAniversario, salarioBruto); 
						 funcionario.verificarCPF(cpfFuncionario);
					 }
				} else {
					if(funcionario != null) {
						if(funcionarios.contains(funcionario)) {
							System.out.println("Funcionário " + funcionario.getNome() + " já cadastrado.");							
						}else {
							funcionarios.add(funcionario);
							funcionario = null;
						}						
				}	}
			}	
			funcionarios.add(funcionario);
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}catch(DependenteException e) {
			System.out.println("Verifique o arquivo! O sistema não aceita dependentes com mais de 18 anos!");
		}catch(CPFException e) {
			System.out.println("Verifique o arquivo! O CPF deve ter 11 digitos");
		}
	
	}
}
