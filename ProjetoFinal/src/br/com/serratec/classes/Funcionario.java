package br.com.serratec.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.serratec.enums.AliquotaINSS;
import br.com.serratec.enums.AliquotaIR;
import br.com.serratec.interfaces.CalculoImposto;

public class Funcionario extends Pessoa implements CalculoImposto {
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

		AliquotaINSS aliquotaINSS = null;
		AliquotaINSS valorDeduzir = null;

		if (salarioBruto <= 1100) {
			descontoINSS = (salarioBruto * aliquotaINSS.PRIMEIRA_FAIXA.getAliquotaINSS() / 100)
					- valorDeduzir.PRIMEIRA_FAIXA.getValorDeduzir();
		} else if (salarioBruto >= 1100.01 && salarioBruto <= 2203.48) {
			descontoINSS = (salarioBruto * aliquotaINSS.SEGUNDA_FAIXA.getAliquotaINSS() / 100)
					- valorDeduzir.SEGUNDA_FAIXA.getValorDeduzir();
		} else if (salarioBruto >= 2203.49 && salarioBruto <= 3305.22) {
			descontoINSS = (salarioBruto * aliquotaINSS.TERCEIRA_FAIXA.getAliquotaINSS() / 100)
					- valorDeduzir.TERCEIRA_FAIXA.getValorDeduzir();
		} else if (salarioBruto >= 3305.23 && salarioBruto <= 6433.57) {
			descontoINSS = (salarioBruto * aliquotaINSS.QUARTA_FAIXA.getAliquotaINSS() / 100)
					- valorDeduzir.QUARTA_FAIXA.getValorDeduzir();
		} else {
			descontoINSS = (6433.57 * aliquotaINSS.QUARTA_FAIXA.getAliquotaINSS() / 100)
					- valorDeduzir.QUARTA_FAIXA.getValorDeduzir();
		}
	}

	public double obterBaseDeCalculo() {
		calcularINSS();
		int numeroDependentes = dependentes.size();
		return salarioBruto - descontoINSS - (numeroDependentes * 189.59);
	}

	@Override
	public void calcularIR() {
		AliquotaIR aliquotaIR = null;
		AliquotaIR valorDeduzir = null;

		double baseDeCalculo = obterBaseDeCalculo();
		if (baseDeCalculo < 1903.98) {
			descontoIR = (baseDeCalculo * aliquotaIR.PRIMEIRA_FAIXA.getAliquotaIR() / 100)
					- valorDeduzir.PRIMEIRA_FAIXA.getValorDeduzir();
		} else if (baseDeCalculo >= 1903.98 && baseDeCalculo < 2826.66) {
			descontoIR = (baseDeCalculo * aliquotaIR.SEGUNDA_FAIXA.getAliquotaIR() / 100)
					- valorDeduzir.SEGUNDA_FAIXA.getValorDeduzir();
		} else if (baseDeCalculo >= 2826.66 && baseDeCalculo < 3751.06) {
			descontoIR = (baseDeCalculo * aliquotaIR.TERCEIRA_FAIXA.getAliquotaIR() / 100)
					- valorDeduzir.TERCEIRA_FAIXA.getValorDeduzir();
		} else if (baseDeCalculo >= 3751.06 && baseDeCalculo < 4664.68) {
			descontoIR = (baseDeCalculo * aliquotaIR.QUARTA_FAIXA.getAliquotaIR() / 100)
					- valorDeduzir.QUARTA_FAIXA.getValorDeduzir();
		} else {
			descontoIR = (baseDeCalculo * aliquotaIR.QUINTA_FAIXA.getAliquotaIR() / 100)
					- valorDeduzir.QUINTA_FAIXA.getValorDeduzir();
		}
	}

	@Override
	public String toString() {
		return super.toString() + "Funcionario [salarioBruto=" + salarioBruto + ", dependentes=" + dependentes + "]";
	}

}
