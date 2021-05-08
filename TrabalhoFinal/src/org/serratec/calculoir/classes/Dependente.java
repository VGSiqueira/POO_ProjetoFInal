package org.serratec.calculoir.classes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.serratec.calculoir.enums.TipoDependente;
import org.serratec.calculoir.exceptions.DependenteException;

public class Dependente extends Pessoa {
	
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
	
	public void verificarIdade() {
		LocalDate hoje = LocalDate.now();
		long idade = LocalDate.from(super.getDataNascimento()).until(hoje,ChronoUnit.YEARS);
		if (idade<18) {
			System.out.println("Dependente entra na regra de estar abaixo de 18 anos.");
		}else 
		throw new DependenteException("Não entra na regra de dependente abaixo de 18 anos.");
		
	}

}
