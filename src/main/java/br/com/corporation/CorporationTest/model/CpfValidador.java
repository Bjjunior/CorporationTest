package br.com.corporation.CorporationTest.model;
/**
 * Classe utilitária para validação de CPF (Cadastro de Pessoas Físicas).
 * 

 * Fornece métodos para verificar se um número de CPF é válido.

 * 
 * @version 1.0
 */
public class CpfValidador {

    /**
     * Verifica se um número de CPF é válido.
     * 
     * @param cpf O número de CPF a ser validado.
     * @return {@code true} se o CPF for válido, {@code false} caso contrário.
     */
    public static boolean isValid(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int resto = soma % 11;
        int digitoVerificador1 = resto < 2 ? 0 : 11 - resto;

        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        resto = soma % 11;
        int digitoVerificador2 = resto < 2 ? 0 : 11 - resto;

        // Verifica se os dígitos verificadores calculados são iguais aos informados
        return Character.getNumericValue(cpf.charAt(9)) == digitoVerificador1 &&
               Character.getNumericValue(cpf.charAt(10)) == digitoVerificador2;
    }
}
