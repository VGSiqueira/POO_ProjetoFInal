package br.com.serratec.exception;

public class CPFException extends RuntimeException{
	
	private static final long serialVersionUID = 2163824237277456511L;

	public CPFException(String message) {
		super(message);
	}
}
