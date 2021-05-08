package org.serratec.calculoir.classes;

import java.time.LocalDate;

public class Funcionario extends Pessoa implements CalculoImposto {
	private double salarioBruto;
	private double descontoINSS;
	private double descontoIR;
	private Dependente dependente;
	
		
	public Funcionario(String nome, String cpf, LocalDate dataNascimento, double salarioBruto, double descontoINSS,
			double descontoIR, Dependente dependente) {
		super(nome, cpf, dataNascimento);
		this.salarioBruto = salarioBruto;
		this.descontoINSS = descontoINSS;
		this.descontoIR = descontoIR;
		this.dependente = dependente;
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


	public void setDescontoINSS(double descontoINSS) {
		this.descontoINSS = descontoINSS;
	}


	public double getDescontoIR() {
		return descontoIR;
	}


	public void setDescontoIR(double descontoIR) {
		this.descontoIR = descontoIR;
	}


	public Dependente getDependente() {
		return dependente;
	}


	public void setDependente(Dependente dependente) {
		this.dependente = dependente;
	}


	@Override
	public void calcularINSS() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void calcularIR() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void verificarIdade() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
	
	

}
