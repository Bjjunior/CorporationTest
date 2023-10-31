package br.com.corporation.CorporationTest.model;
/**
 * Realiza a validação de CNPJ.
 * 
 *
 * Esta classe verifica se um CNPJ é válido de acordo com a lógica de cálculo dos dígitos verificadores.
 * 
 * 
 * @version 1.0
 */
public class CnpjValidador {

    /**
     * Verifica se um CNPJ é válido.
     *
     * @param cnpj O CNPJ a ser validado.
     * @return True se o CNPJ for válido, false caso contrário.
     */
    public static boolean isValid(String cnpj) {
        // Verifica se o CNPJ possui 14 dígitos numéricos
        if (cnpj == null || !cnpj.matches("\\d{14}")) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 12; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * (13 - i);
        }
        int resto = soma % 11;
        int digitoVerificador1 = resto < 2 ? 0 : 11 - resto;

        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 13; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * (14 - i);
        }
        resto = soma % 11;
        int digitoVerificador2 = resto < 2 ? 0 : 11 - resto;

        // Verifica se os dígitos verificadores calculados são iguais aos informados
        return Character.getNumericValue(cnpj.charAt(12)) == digitoVerificador1 &&
               Character.getNumericValue(cnpj.charAt(13)) == digitoVerificador2;
    }
}
