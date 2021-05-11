package br.com.serratec.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.serratec.enums.AliquotaINSS;
import br.com.serratec.enums.AliquotaIR;
import br.com.serratec.interfaces.CalculoImposto;

public class Funcionario extends Pessoa implements CalculoImposto, Comparable<Funcionario> {
	private double salarioBruto;
	private double descontoINSS;
	private double descontoIR;
	private List<Dependente> dependentes = new ArrayList<>();	

	public Funcionario(String nome, String cpf, LocalDate dataNascimento, double salarioBruto) {
		super(nome, cpf, dataNascimento);
		this.salarioBruto = salarioBruto;
	}

	public double getSalarioBruto() {
		return salarioBruto;
	}

	public void setSalarioBruto(double salarioBruto) {
		this.salarioBruto = salarioBruto;
	}

	public double getDescontoINSS() {
		return descontoINSS;
	}

	public double getDescontoIR() {
		return descontoIR;
	}

	public List<Dependente> getDependente() {
		return dependentes;
	}

	public void adicionarDependente(Dependente dependente) {
		dependentes.add(dependente);
	}

	@Override
	public void calcularINSS() {		

		if (salarioBruto <= 1100) {
			descontoINSS = (salarioBruto * AliquotaINSS.PRIMEIRA_FAIXA.getAliquotaINSS() / 100)
					- AliquotaINSS.PRIMEIRA_FAIXA.getValorDeduzir();
		} else if (salarioBruto >= 1100.01 && salarioBruto <= 2203.48) {
			descontoINSS = (salarioBruto * AliquotaINSS.SEGUNDA_FAIXA.getAliquotaINSS() / 100)
					- AliquotaINSS.SEGUNDA_FAIXA.getValorDeduzir();
		} else if (salarioBruto >= 2203.49 && salarioBruto <= 3305.22) {
			descontoINSS = (salarioBruto * AliquotaINSS.TERCEIRA_FAIXA.getAliquotaINSS() / 100)
					- AliquotaINSS.TERCEIRA_FAIXA.getValorDeduzir();
		} else if (salarioBruto >= 3305.23 && salarioBruto <= 6433.57) {
			descontoINSS = (salarioBruto * AliquotaINSS.QUARTA_FAIXA.getAliquotaINSS() / 100)
					- AliquotaINSS.QUARTA_FAIXA.getValorDeduzir();
		} else {
			descontoINSS = (6433.57 * AliquotaINSS.QUARTA_FAIXA.getAliquotaINSS() / 100)
					- AliquotaINSS.QUARTA_FAIXA.getValorDeduzir();
		}
	}

	public double obterBaseDeCalculo() {
		calcularINSS();
		int numeroDependentes = dependentes.size();
		return salarioBruto - descontoINSS - (numeroDependentes * 189.59);
	}

	@Override
	public void calcularIR() {	

		double baseDeCalculo = obterBaseDeCalculo();
		if (baseDeCalculo < 1903.98) {
			descontoIR = (baseDeCalculo * AliquotaIR.PRIMEIRA_FAIXA.getAliquotaIR() / 100)
					- AliquotaIR.PRIMEIRA_FAIXA.getValorDeduzir();
		} else if (baseDeCalculo >= 1903.98 && baseDeCalculo < 2826.66) {
			descontoIR = (baseDeCalculo * AliquotaIR.SEGUNDA_FAIXA.getAliquotaIR() / 100)
					- AliquotaIR.SEGUNDA_FAIXA.getValorDeduzir();
		} else if (baseDeCalculo >= 2826.66 && baseDeCalculo < 3751.06) {
			descontoIR = (baseDeCalculo * AliquotaIR.TERCEIRA_FAIXA.getAliquotaIR() / 100)
					- AliquotaIR.TERCEIRA_FAIXA.getValorDeduzir();
		} else if (baseDeCalculo >= 3751.06 && baseDeCalculo < 4664.68) {
			descontoIR = (baseDeCalculo * AliquotaIR.QUARTA_FAIXA.getAliquotaIR() / 100)
					- AliquotaIR.QUARTA_FAIXA.getValorDeduzir();
		} else {
			descontoIR = (baseDeCalculo * AliquotaIR.QUINTA_FAIXA.getAliquotaIR() / 100)
					- AliquotaIR.QUINTA_FAIXA.getValorDeduzir();
		}
	}
	public double calcularSalarioLiquido() {		
		return salarioBruto - descontoINSS - descontoIR;
	}

	@Override
	public String toString() {
		return "Funcionario - " + super.toString() + ", Salário Bruto: " + salarioBruto;
	}

	@Override
	public int compareTo(Funcionario o) {		
		return this.cpf.compareToIgnoreCase(o.getCpf());
	}

}
