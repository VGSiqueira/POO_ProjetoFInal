package org.serratec.calculoir.principal;

import java.time.LocalDate;

import org.serratec.calculoir.classes.Dependente;
import org.serratec.calculoir.classes.Pessoa;
import org.serratec.calculoir.enums.TipoDependente;

public class Teste {
	public static void main(String[] args) {
		Pessoa p = new Dependente("Vinicius", "12345678908", LocalDate.of(2005,4,8), TipoDependente.FILHO);
		
		try {
			p.verificarIdade();
		}catch (Exception e) {
			System.out.println("Não entra na regra.");
		}
}
}
