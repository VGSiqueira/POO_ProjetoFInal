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
							System.out.println(nomeDependente + " n�o foi adicionado pois possui um parentesco inv�lido.");
						}catch(DateTimeParseException e) {
							System.out.println(nomeDependente + " n�o foi adicionado, pois a data de nascimento � inv�lida.");					
							//continue;
						}catch (DependenteException e) {
							System.out.println(
							dependente.getNome() + " n�o foi adicionado pois possui idade superior a 18 anos.");
							//continue;
						} catch (CPFException e) {
							dependente.setCpf("000000000001");
							System.out.println("O CPF do dependente " + dependente.getNome()
									+ " possui mais de 11 digitos. foi atribuido o valor padr�o de "
									+ dependente.getCpf() + " Fa�a a altera��o no menu.");
						}
						if (funcionario.getDependente().contains(dependente)) {
							System.out.println("O dependente " + dependente.getNome() + " j� foi cadastrado.");
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
									+ " possui mais de 11 digitos. foi atribuido o valor padr�o de "
									+ funcionario.getCpf() + " Fa�a a altera��o no menu.");
						}catch(DateTimeParseException e) {
							System.out.println(nomeFuncionario + " n�o foi cadastrado pois a data de nascimento informada � inv�lida.");
						}catch (InputMismatchException e) {
							System.out.println(nomeFuncionario + " n�o foi cadastrado pois o sal�rio informado � inv�lido.");
						}catch(NumberFormatException e) {
							System.out.println(nomeFuncionario + " n�o foi cadastrado pois o sal�rio informado � inv�lido.");
						}
					}
				} else {
					if (funcionario != null) {
						if (funcionarios.contains(funcionario)) {
							System.out.println("Funcion�rio " + funcionario.getNome() + " j� cadastrado.");
						} else {
							funcionarios.add(funcionario);
							funcionario = null;
						}
					}
				}
			}
			funcionarios.add(funcionario);
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo n�o encontrado, verifique o caminho informado.");
			;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
