package br.com.serratec.enums;

public enum TipoDependente {
	FILHO("FILHO"), SOBRINHO("SOBRINHO"), OUTRO("OUTROS");
	
	private String tipoDependente;
	
	private TipoDependente(String tipoDependente) {
		this.tipoDependente = tipoDependente;
	}

	public String getTipoDependente() {
		return tipoDependente;
	}
	
}
