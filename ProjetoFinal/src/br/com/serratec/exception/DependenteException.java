package br.com.serratec.exception;

public class DependenteException extends RuntimeException {

	private static final long serialVersionUID = -4312976609746257521L;

	public DependenteException(String message) {
		super(message);
	}
}
