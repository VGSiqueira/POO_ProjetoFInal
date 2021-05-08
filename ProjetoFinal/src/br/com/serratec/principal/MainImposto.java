package br.com.serratec.principal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.serratec.classes.Dependente;
import br.com.serratec.classes.Funcionario;
import br.com.serratec.enums.TipoDependente;

public class MainImposto {
	public static void main(String[] args) {
		
		List<Funcionario> funcionarios = new ArrayList<>();
		Funcionario func = new Funcionario("Nathan", "12345678952456547", LocalDate.parse("2021-05-08"), 5000);
		Dependente d = new Dependente("Fulano", "12345678944", LocalDate.parse("1980-03-12"), TipoDependente.OUTRO);
		func.adicionarDependente(d);
		funcionarios.add(func);
		
		func.calcularIR();
		System.out.printf("%.2f    -    %.2f", func.getDescontoINSS(), func.getDescontoIR());
		
		//System.out.println(func);
		System.out.println(func.verificarCPF(func.getCpf()));
		//System.out.println(d.verificarIdade());
	}
}
