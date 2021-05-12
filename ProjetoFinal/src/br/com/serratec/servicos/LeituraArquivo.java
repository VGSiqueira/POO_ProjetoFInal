package br.com.serratec.servicos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import br.com.serratec.classes.Dependente;
import br.com.serratec.classes.Funcionario;
import br.com.serratec.enums.TipoDependente;
import br.com.serratec.exception.CPFException;
import br.com.serratec.exception.DependenteException;

public class LeituraArquivo {
	public void lerArquivo(List<Funcionario> funcionarios, Scanner scan, String arquivoNome) {
		try {
			File arquivo = new File(arquivoNome);
			scan = new Scanner(new InputStreamReader(new FileInputStream(arquivo), "UTF-8"));

			Funcionario funcionario = null;
			while (scan.hasNext()) {
				String linha = scan.nextLine();
				if (!linha.isEmpty()) {
					if (funcionario != null) {	
						Dependente dependente = null;
						
						String[] dadosDependente = linha.split(";");
						String nomeDependente = dadosDependente[0];
						try {
						String cpfDependente = dadosDependente[1];
						LocalDate dataAniversario = LocalDate.parse(dadosDependente[2]);
						TipoDependente parentesco = TipoDependente.valueOf(dadosDependente[3]);
						dependente = new Dependente(nomeDependente, cpfDependente, dataAniversario,
								parentesco);
						dependente.verificarIdade();
						dependente.verificarCPF(cpfDependente);
						}catch(IllegalArgumentException e) {
							System.out.println(nomeDependente + " não foi adicionado pois possui um parentesco inválido.");
						}catch(DateTimeParseException e) {
							System.out.println(nomeDependente + " não foi adicionado, pois a data de nascimento é inválida.");					
							//continue;
						}catch (DependenteException e) {
							System.out.println(
							dependente.getNome() + " não foi adicionado pois possui idade superior a 18 anos.");
							//continue;
						} catch (CPFException e) {
							dependente.setCpf("000000000001");
							System.out.println("O CPF do dependente " + dependente.getNome()
									+ " possui mais de 11 digitos. foi atribuido o valor padrão de "
									+ dependente.getCpf() + " Faça a alteração no menu.");
						}
						if (funcionario.getDependente().contains(dependente)) {
							System.out.println("O dependente " + dependente.getNome() + " já foi cadastrado.");
						} else if(!funcionario.getDependente().contains(dependente) && dependente != null){
							funcionario.adicionarDependente(dependente);
						}
						continue;
					} else {
						
						String[] dadosFuncionario = linha.split(";");
						String nomeFuncionario = dadosFuncionario[0];
						String cpfFuncionario = dadosFuncionario[1];
						try {
						LocalDate dataAniversario = LocalDate.parse(dadosFuncionario[2]);
						double salarioBruto = Double.parseDouble(dadosFuncionario[3]);
						funcionario = new Funcionario(nomeFuncionario, cpfFuncionario, dataAniversario, salarioBruto);
						
							funcionario.verificarCPF(cpfFuncionario);
						} catch (CPFException e) {
							funcionario.setCpf("00000000002");
							System.out.println("O CPF do funcionario " + funcionario.getNome()
									+ " possui mais de 11 digitos. foi atribuido o valor padrão de "
									+ funcionario.getCpf() + " Faça a alteração no menu.");
						}catch(DateTimeParseException e) {
							System.out.println(nomeFuncionario + " não foi cadastrado pois a data de nascimento informada é inválida.");
						}catch (InputMismatchException e) {
							System.out.println(nomeFuncionario + " não foi cadastrado pois o salário informado é inválido.");
						}catch(NumberFormatException e) {
							System.out.println(nomeFuncionario + " não foi cadastrado pois o salário informado é inválido.");
						}
					}
				} else {
					if (funcionario != null) {
						if (funcionarios.contains(funcionario)) {
							System.out.println("Funcionário " + funcionario.getNome() + " já cadastrado.");
						} else {
							funcionarios.add(funcionario);
							funcionario = null;
						}
					}
				}
			}
			funcionarios.add(funcionario);
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado, verifique o caminho informado.");
			;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
