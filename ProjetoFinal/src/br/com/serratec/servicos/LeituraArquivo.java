package br.com.serratec.servicos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import br.com.serratec.classes.Dependente;
import br.com.serratec.classes.Funcionario;
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
						try {
						dependente.verificarIdade();
						dependente.verificarCPF(cpfDependente);
						}catch(DependenteException e) {
							System.out.println(dependente.getNome() + " n�o foi adicionado pois possui idade superior a 18 anos.");
							continue;							
						}catch(CPFException e) {
							dependente.setCpf("000000000001");
							System.out.println("O CPF do dependente " + dependente.getNome() + " possui mais de 11 digitos. foi atribuido o valor padr�o de "+ dependente.getCpf()+ " Fa�a a altera��o no menu.");
						}						
						if(funcionario.getDependente().contains(dependente)) {
							System.out.println("O dependente " + dependente.getNome() + " j� foi cadastrado.");
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
						 try {
						 funcionario.verificarCPF(cpfFuncionario);
						 }catch(CPFException e){
							 funcionario.setCpf("00000000002");
							 System.out.println("O CPF do funcionario " + funcionario.getNome() + " possui mais de 11 digitos. foi atribuido o valor padr�o de "+ funcionario.getCpf()+ " Fa�a a altera��o no menu.");
						 }
					 }
				} else {
					if(funcionario != null) {
						if(funcionarios.contains(funcionario)) {
							System.out.println("Funcion�rio " + funcionario.getNome() + " j� cadastrado.");							
						}else {
							funcionarios.add(funcionario);
							funcionario = null;
						}						
				}	}
			}	
			funcionarios.add(funcionario);
		} catch (FileNotFoundException e) {			
			System.out.println("Arquivo n�o encontrado, verifique o caminho informado.");;
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}	
	}
}