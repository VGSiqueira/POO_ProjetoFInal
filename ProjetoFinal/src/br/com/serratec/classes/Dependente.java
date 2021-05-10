package br.com.serratec.classes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import br.com.serratec.enums.TipoDependente;
import br.com.serratec.exception.DependenteException;

public class Dependente extends Pessoa implements Comparable<Dependente> {

	private TipoDependente parentesco;

	public Dependente(String nome, String cpf, LocalDate dataNascimento, TipoDependente parentesco) {
		super(nome, cpf, dataNascimento);
		this.parentesco = parentesco;
	}

	public TipoDependente getParentesco() {
		return parentesco;
	}

	public void setParentesco(TipoDependente parentesco) {
		this.parentesco = parentesco;
	}

	public boolean verificarIdade() {		
		LocalDate hoje = LocalDate.now();
		long idade = LocalDate.from(super.getDataNascimento()).until(hoje, ChronoUnit.YEARS);
		if (idade < 18) {
			return true;
		}else {
			throw new DependenteException("O dependente não pode ter mais de 18 anos.");
		}		
	}	

	@Override
	public String toString() {
		return super.toString() + "Dependente [parentesco=" + parentesco + "]";
	}

	@Override
	public int compareTo(Dependente o) {		
		return this.cpf.compareToIgnoreCase(o.getCpf());
	}


}
