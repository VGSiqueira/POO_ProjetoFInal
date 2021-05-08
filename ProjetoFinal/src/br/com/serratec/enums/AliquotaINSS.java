package br.com.serratec.enums;

public enum AliquotaINSS {
	PRIMEIRA_FAIXA(7.5, 0), SEGUNDA_FAIXA(9, 16.5), TERCEIRA_FAIXA(12, 82.61), QUARTA_FAIXA(14, 148.72);

	private double aliquotaINSS;
	private double valorDeduzir;

	private AliquotaINSS(double aliquotaINSS, double valorDeduzir) {
		this.aliquotaINSS = aliquotaINSS;
		this.valorDeduzir = valorDeduzir;
	}

	public double getAliquotaINSS() {
		return aliquotaINSS;
	}

	public double getValorDeduzir() {
		return valorDeduzir;
	}
	

}
