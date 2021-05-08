package br.com.serratec.enums;

public enum AliquotaIR {
	PRIMEIRA_FAIXA(0, 0), SEGUNDA_FAIXA(7.5, 142.80), TERCEIRA_FAIXA(15, 354.80), QUARTA_FAIXA(22.5, 636.13), QUINTA_FAIXA(27.5, 869.36);
	
	private double aliquotaIR;
	private double valorDeduzir;
	
	private AliquotaIR(double aliquotaIR, double valorDeduzir) {
		this.aliquotaIR = aliquotaIR;
		this.valorDeduzir = valorDeduzir;
	}

	public double getAliquotaIR() {
		return aliquotaIR;
	}

	public double getValorDeduzir() {
		return valorDeduzir;
	}	
}
