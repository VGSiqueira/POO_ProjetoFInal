package br.com.serratec.principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.serratec.classes.Dependente;
import br.com.serratec.classes.Funcionario;
import br.com.serratec.classes.LeituraArquivo;
import br.com.serratec.enums.TipoDependente;

public class MainImposto {
	public static void main(String[] args) {
		Scanner scan = null;
		List<Funcionario> funcionarios = new ArrayList<>();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		scan = new Scanner(System.in);
		System.out.println(
				"Informe o caminho do arquivo para leitura (Ex.: E:\\Documentos\\Leitura\\NomeDoArquivo.txt) : ");
		String caminhoArquivo = scan.nextLine();		

		int opcao = 0;
		do {
			System.out.println("1 - Criar arquivo com os dados dos funcionários:");
			System.out.println("2 - Adicionar funcionário:");
			System.out.println("3 - Adicionar dependente:");
			System.out.println("4 - Excluir Funcionário:");
			System.out.println("5 - Excluir Dependente:");
			System.out.println("6 - Alterar Funcionário:");
			System.out.println("7 - Alterar Dependente:");
			System.out.println("8 - Ordenar:");
			System.out.println("9 - Sair:");
			
			opcao = scan.nextInt();
			scan.nextLine();
			switch (opcao) {
			case 1:
				LeituraArquivo ler = new LeituraArquivo();
				ler.lerArquivo(funcionarios, scan, caminhoArquivo);
				break;
			case 2:
				System.out.println("Informe o nome do funcionário");
				String nomeFuncionario = scan.nextLine();
				System.out.println("Informe o CPF do funcionário");
				String cpfFuncionario = scan.nextLine();
				System.out.println("Informe a dadata de nascimento do funcionário");
				String data = scan.nextLine();
				LocalDate dataNascimento = LocalDate.parse(data, df);
				System.out.println("Informe o salário do funcionário");
				double salario = scan.nextDouble();
				Funcionario func = new Funcionario(nomeFuncionario, cpfFuncionario, dataNascimento, salario);
				if(!funcionarios.contains(func)) {
					funcionarios.add(func);
				}
				break;
			case 3:
				System.out.println("Informe o CPF do funcionário");
				String cpfFuncDep = scan.nextLine();
				incluirDependente(funcionarios, scan, cpfFuncDep);				
				break;
			case 4:
				System.out.println("Informe o CPF do funcionário a remover");
                String funcExcluir = scan.nextLine();
                funcionarios.removeIf(f -> f.getCpf().equalsIgnoreCase(funcExcluir));
                System.out.println("Funcionário excluido com sucesso");
				break;
			case 5:
				System.out.println("Informe o CPF do funcionário");
                String depExcluir = scan.nextLine();
                for(Funcionario f: funcionarios) {
                	f.getDependente().removeIf(d -> d.getCpf().equalsIgnoreCase(depExcluir));
                }                
				break;
			case 6:
				System.out.println("Informe o CPF do funcionário");
				String cpfAlterar = scan.nextLine();
				alterarFuncionario(scan, funcionarios, cpfAlterar);
				break;
			case 7:
				System.out.println("Informe o CPF do funcionário");
				String cpfAlterarDep = scan.nextLine();
				alterarDepedente(scan, funcionarios, cpfAlterarDep);
				break;
			case 9:
				break;
			}

		} while(opcao != 9);

		
	
		/*
		 * CriacaoArquivo criar = new CriacaoArquivo();
		 * criar.criarArquivo(funcionarios);
		 */

		for (Funcionario f : funcionarios) {
			f.calcularIR();
			System.out.println(f);
		}
	}

	private static void alterarDepedente(Scanner scan, List<Funcionario> funcionarios, String cpfAlterarDep) {
		for(Funcionario f: funcionarios) {
			if(f.getCpf().equalsIgnoreCase(cpfAlterarDep)) {
				System.out.println("Informe o CPF do dependente a alterar: ");
				String cpfDep =  scan.nextLine();
				for(Dependente d: f.getDependente()) {
					if(d.getCpf().equalsIgnoreCase(cpfDep)) {
						int opcao = 0;
						do {
							System.out.println("1 - Alterar nome:");
							System.out.println("2 - Alterar CPF:");
							System.out.println("3 - Alterar data de nascimento:");
							System.out.println("4 - Alterar parentesco:");
							System.out.println("5 - Sair:");
							
							opcao = scan.nextInt();
							
							switch(opcao) {
							case 1:
								System.out.println("Informe o novo nome: ");
								String novoNome = scan.nextLine();
								d.setNome(novoNome);
								break;
							case 2:
								System.out.println("Informe o novo CPF: ");
								String novoCpf = scan.nextLine();
								d.setCpf(novoCpf);
								break;
							case 3:
								System.out.println("Informe a data de nascimento: ");
								String nascimento = scan.nextLine();
								DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
								LocalDate dataNascimento = LocalDate.parse(nascimento, df);
								d.setDataNascimento(dataNascimento);
								break;
							case 4:
								System.out.println("Informe o parentesco: ");
								String tipo = scan.nextLine();
								TipoDependente parentesco = TipoDependente.valueOf(tipo);
								d.setParentesco(parentesco);
								break;
							case 5:
								break;
							
							}
					}while(opcao != 5);
				}
			}			
		}			
	}
}
	private static void alterarFuncionario(Scanner scan, List<Funcionario> funcionarios, String cpfAlterar) {		
		for(Funcionario f: funcionarios) {
			if(f.getCpf().equalsIgnoreCase(cpfAlterar)) {
				int opcao = 0;
				do {
					System.out.println("1 - Alterar nome:");
					System.out.println("2 - Alterar CPF:");
					System.out.println("3 - Alterar data de nascimento:");
					System.out.println("4 - Alterar salário:");
					System.out.println("5 - Sair:");
					
					opcao = scan.nextInt();
					
					switch(opcao) {
					case 1:
						System.out.println("Informe o novo nome: ");
						String novoNome = scan.nextLine();
						f.setNome(novoNome);
						break;
					case 2:
						System.out.println("Informe o novo CPF: ");
						String novoCpf = scan.nextLine();
						f.setCpf(novoCpf);
						break;
					case 3:
						System.out.println("Informe a data de nascimento: ");
						String nascimento = scan.nextLine();
						DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						LocalDate dataNascimento = LocalDate.parse(nascimento, df);
						f.setDataNascimento(dataNascimento);
						break;
					case 4:
						System.out.println("Informe o novo salário: ");
						double salario = scan.nextDouble();
						f.setSalarioBruto(salario);
						break;
					case 5:
						break;
					
					}
				}while(opcao != 5);
			}
		}
	
	}

	private static void incluirDependente(List<Funcionario> funcionarios, Scanner scan, String cpfFuncDep) {		
		for(Funcionario f: funcionarios) {
			if(f.getCpf().equalsIgnoreCase(cpfFuncDep)) {
				System.out.println("Informe o nome do dependente");
				String nomeDependente = scan.nextLine();
				System.out.println("Informe o CPF do dependente");
				String cpfDependente = scan.nextLine();
				System.out.println("Informe a dadata de nascimento do dependente");
				String data = scan.nextLine();
				DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate dataNascimento = LocalDate.parse(data, df);
				System.out.println("Informe o parentesco do dependente");
				String tipo = scan.nextLine();
				tipo.toUpperCase();
				TipoDependente tipoDependente = TipoDependente.valueOf(tipo);
				Dependente dep = new Dependente(nomeDependente, cpfDependente, dataNascimento, tipoDependente);				
				f.adicionarDependente(dep);
			}
		}		
	}
}
