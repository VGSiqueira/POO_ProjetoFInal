package br.com.serratec.principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import br.com.serratec.classes.Dependente;
import br.com.serratec.classes.Funcionario;
import br.com.serratec.enums.TipoDependente;
import br.com.serratec.exception.CPFException;
import br.com.serratec.exception.DependenteException;
import br.com.serratec.servicos.CriacaoArquivo;
import br.com.serratec.servicos.LeituraArquivo;

public class MainImposto {
	public static void main(String[] args) {
		Scanner scan = null;
		List<Funcionario> funcionarios = new ArrayList<>();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		scan = new Scanner(System.in);
		System.out.println(
				"Informe o caminho do arquivo para leitura (Ex.: E:\\Documentos\\Leitura\\NomeDoArquivo.txt) : ");
		String caminhoArquivo = scan.nextLine();

		LeituraArquivo ler = new LeituraArquivo();
		ler.lerArquivo(funcionarios, scan, caminhoArquivo);

		int opcao = 0;
		do {
			System.out.println("\n1 - Criar arquivo com os dados dos funcionários:");
			System.out.println("2 - Adicionar funcionário:");
			System.out.println("3 - Adicionar dependente:");
			System.out.println("4 - Excluir Funcionário:");
			System.out.println("5 - Excluir Dependente:");
			System.out.println("6 - Alterar Funcionário:");
			System.out.println("7 - Alterar Dependente:");
			System.out.println("8 - Ordenar:");
			System.out.println("9 - Exibir funcionários e dependentes:");
			System.out.println("10 - Sair:");

			opcao = scan.nextInt();
			scan.nextLine();
			switch (opcao) {
			case 1:
				CriacaoArquivo criar = new CriacaoArquivo();
				System.out.println(
						"Digite o caminho onde deseja salvar o arquivo (Ex.: E:\\Documentos\\Leitura\\NomeDoArquivo.txt):");
				String salvarArquivo = scan.nextLine();
				criar.criarArquivo(funcionarios, salvarArquivo);
				break;
			case 2:
				try {
					System.out.println("Informe o nome do funcionário");
					String nomeFuncionario = scan.nextLine();
					System.out.println("Informe o CPF do funcionário");
					String cpfFuncionario = scan.nextLine();
					System.out.println("Informe a data de nascimento do funcionário");
					String data = scan.nextLine();
					LocalDate dataNascimento = LocalDate.parse(data, df);
					System.out.println("Informe o salário do funcionário");
					double salario = scan.nextDouble();
					Funcionario func = new Funcionario(nomeFuncionario, cpfFuncionario, dataNascimento, salario);
					try {
						func.verificarCPF(cpfFuncionario);
						if (!funcionarios.contains(func)) {
							funcionarios.add(func);
							System.out.println("Funcionário adicionado com sucesso!");
						} else {
							System.out.println("Funcionário já cadastrado.");
						}
					} catch (CPFException e) {
						func.setCpf("00000000003");
						System.out.println("O CPF do funcionario " + func.getNome()
								+ "está em formato inválido. Foi atribuido o valor padrão de " + func.getCpf()
								+ " Faça a alteração no menu.");
						funcionarios.add(func);
					}
				} catch (DateTimeParseException e) {
					System.out.println("Data inválida. O formato correto é dd/MM/yyy.");
				} catch (InputMismatchException e) {
					System.out.println("Salário inválido.");
				}
				scan.nextLine();
				break;
			case 3:
				System.out.println("Informe o CPF do funcionário");
				String cpfFuncDep = scan.nextLine();
				incluirDependente(funcionarios, scan, cpfFuncDep);
				break;
			case 4:
				System.out.println("Informe o CPF do funcionário a remover");
				String funcExcluir = scan.nextLine();
				if (funcionarios.removeIf(r -> r.getCpf().equalsIgnoreCase(funcExcluir))) {
					System.out.println("Funcionário excluido com sucesso");
				} else {
					System.out.println("Funcionário não cadastrado.");
				}
				break;
			case 5:
				System.out.println("Informe o CPF do dependente a excluir");
				String excluir = scan.nextLine();
				for (Funcionario f : funcionarios) {
					if (f.getDependente().removeIf(r -> r.getCpf().equalsIgnoreCase(excluir))) {
						System.out.println("Dependente excluído com sucesso!");
					} else {
						System.out.println("Dependente não cadastrado.");
					}

					break;
				}
				break;
			case 6:
				System.out.println("Informe o CPF do funcionário");
				String cpfAlterar = scan.nextLine();
				alterarFuncionario(scan, funcionarios, cpfAlterar);
				break;
			case 7:
				System.out.println("Informe o CPF do dependente a alterar: ");
				String cpfDep = scan.nextLine();
				alterarDepedente(scan, funcionarios, cpfDep);
				break;
			case 9:
				for (Funcionario f : funcionarios) {
					System.out.println(f);
					for (Dependente d : f.getDependente()) {
						System.out.println(d);
					}
				}
			case 10:
				break;
			default:
				System.out.println("Opção inválida.");
			}

		} while (opcao != 10);

		for (Funcionario f : funcionarios) {
			f.calcularIR();
		}
	}

	private static void alterarDepedente(Scanner scan, List<Funcionario> funcionarios, String cpfDep) {
		for (Funcionario f : funcionarios) {
			for (Dependente d : f.getDependente()) {
				if (d.getCpf().equalsIgnoreCase(cpfDep)) {
					int opcao = 0;

					do {
						System.out.println("\n1 - Alterar nome:");
						System.out.println("2 - Alterar CPF:");
						System.out.println("3 - Alterar data de nascimento:");
						System.out.println("4 - Alterar parentesco:");
						System.out.println("5 - Sair:");

						opcao = scan.nextInt();

						scan.nextLine();
						switch (opcao) {
						case 1:
							System.out.println("Informe o novo nome: ");
							String novoNome = scan.nextLine();
							d.setNome(novoNome);
							System.out.println("Nome alterado!");
							break;
						case 2:
							try {
								System.out.println("Informe o novo CPF: ");
								String novoCpf = scan.nextLine();
								d.verificarCPF(novoCpf);
								d.setCpf(novoCpf);
								System.out.println("CPF alterado!");
							} catch (CPFException e) {
								System.out.println(
										"O CPF do dependente " + d.getNome() + " está em um formato inválido.");
							}
							break;
						case 3:
							try {
								System.out.println("Informe a data de nascimento: ");
								String nascimento = scan.nextLine();
								DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
								LocalDate dataNascimento = LocalDate.parse(nascimento, df);
								d.setDataNascimento(dataNascimento);
								System.out.println("Data de nascimento alterada!");
							} catch (DateTimeParseException e) {
								System.out.println("Data no formato inválido.");
							}
							break;
						case 4:
							System.out
									.println("Informe o parentesco do dependente: 1 - FILHO; 2 - SOBRINHO; 3 - OUTRO");
							int tipo = scan.nextInt();
							scan.nextLine();
							TipoDependente tipoDependente = null;
							if (tipo == 1) {
								tipoDependente = TipoDependente.FILHO;
							} else if (tipo == 2) {
								tipoDependente = TipoDependente.SOBRINHO;
							} else if (tipo == 3) {
								tipoDependente = TipoDependente.OUTRO;
							} else {
								System.out.println("Parentesco inválido.");
								break;
							}
							d.setParentesco(tipoDependente);
							break;
						case 5:
							break;
						default:
							System.out.println("Opção inválida");
						}
					} while (opcao != 5);
				}
			}
		}
	}

	private static void alterarFuncionario(Scanner scan, List<Funcionario> funcionarios, String cpfAlterar) {
		for (Funcionario f : funcionarios) {
			if (f.getCpf().equalsIgnoreCase(cpfAlterar)) {
				int opcao = 0;
				do {
					System.out.println("1 - Alterar nome:");
					System.out.println("2 - Alterar CPF:");
					System.out.println("3 - Alterar data de nascimento:");
					System.out.println("4 - Alterar salário:");
					System.out.println("5 - Sair:");

					opcao = scan.nextInt();

					scan.nextLine();
					switch (opcao) {
					case 1:
						System.out.println("Informe o novo nome: ");
						String novoNome = scan.nextLine();
						f.setNome(novoNome);
						System.out.println("Nome alterado!");
						break;
					case 2:
						try {
							System.out.println("Informe o novo CPF: ");
							String novoCpf = scan.nextLine();
							f.verificarCPF(novoCpf);
							f.setCpf(novoCpf);
							System.out.println("CPF alterado!");
						} catch (CPFException e) {
							System.out.println("O CPF do funcionário " + f.getNome() + " está em um formato inválido.");
						}
						break;
					case 3:
						try {
							System.out.println("Informe a data de nascimento: ");
							String nascimento = scan.nextLine();
							DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
							LocalDate dataNascimento = LocalDate.parse(nascimento, df);
							f.setDataNascimento(dataNascimento);
							System.out.println("Data de nascimento alterada!");
						} catch (DateTimeParseException e) {
							System.out.println("Data no formato inválido.");
						}
						break;
					case 4:
						try {
							System.out.println("Informe o novo salário: ");
							double salario = scan.nextDouble();
							f.setSalarioBruto(salario);
							System.out.println("Salário alterado!");
						} catch (InputMismatchException e) {
							System.out.println("Salário inválido.");
						}
						scan.nextLine();
						break;
					case 5:
						break;
					default:
						System.out.println("Opção inválida.");

					}
				} while (opcao != 5);
			}
		}
	}

	private static void incluirDependente(List<Funcionario> funcionarios, Scanner scan, String cpfFuncDep) {
		for (Funcionario f : funcionarios) {
			if (f.getCpf().equalsIgnoreCase(cpfFuncDep)) {
				Dependente dep = null;
				try {
					System.out.println("Informe o nome do dependente");
					String nomeDependente = scan.nextLine();
					System.out.println("Informe o CPF do dependente");
					String cpfDependente = scan.nextLine();
					System.out.println("Informe a dadata de nascimento do dependente");
					String data = scan.nextLine();
					DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate dataNascimento = LocalDate.parse(data, df);

					System.out.println("Informe o parentesco do dependente: 1 - FILHO; 2 - SOBRINHO; 3 - OUTRO");
					int tipo = scan.nextInt();
					scan.nextLine();
					TipoDependente tipoDependente = null;
					if (tipo == 1) {
						tipoDependente = TipoDependente.FILHO;
					} else if (tipo == 2) {
						tipoDependente = TipoDependente.SOBRINHO;
					} else if (tipo == 3) {
						tipoDependente = TipoDependente.OUTRO;
					} else {
						System.out.println("Parentesco inválido.");
						break;
					}
					dep = new Dependente(nomeDependente, cpfDependente, dataNascimento, tipoDependente);

					dep.verificarIdade();
					dep.verificarCPF(cpfDependente);
					if (!f.getDependente().contains(dep)) {
						f.getDependente().add(dep);
						System.out.println("Dependente adicionado com sucesso!");
					} else {
						System.out.println("Dependente já cadastrado.");
					}					
				} catch (DependenteException e) {
					System.out.println(dep.getNome() + " não pode ser um dependente, pois tem mais de 18 anos.");
				} catch (CPFException e) {
					dep.setCpf("00000000005");
					f.adicionarDependente(dep);
					System.out.println("O CPF do dependente " + dep.getNome()
							+ " está em formato inválido. Foi atribuido o valor padrão de " + dep.getCpf()
							+ " Faça a alteração no menu.");
				} catch (DateTimeParseException e) {
					System.out.println("Data inválida.");
				}
			}
		}
	}
}
