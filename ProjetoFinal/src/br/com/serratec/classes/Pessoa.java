package br.com.serratec.classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.serratec.exception.CPFException;

public abstract class Pessoa {
	private String nome;
	protected String cpf;
	private LocalDate dataNascimento;

	public Pessoa(String nome, String cpf, LocalDate dataNascimento) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public boolean verificarCPF (String cpf) {
		if(cpf.length() <= 11) {
			return true;
		}else {
			throw new CPFException("CPF inválido");
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this.cpf.equalsIgnoreCase(((Pessoa)obj).getCpf())){
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return String.format("Nome: %s  CPF: %s Data de Nascimento: %s", nome, cpf, dataNascimento.format(df));
	}

}
