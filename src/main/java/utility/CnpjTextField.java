/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 *
 * @author ari
 */
public class CnpjTextField extends TextField {

    public CnpjTextField() {

        setPromptText("99.999.999/9999-99");
        setMinWidth(125);
        setMaxWidth(125);

        textProperty().addListener((ObservableValue<? extends String> observableValue, String oldValue, String newValue) -> {
            newValue = newValue.replaceAll("[^0-9]", "");
            newValue = newValue.replaceFirst("(\\d{2})(\\d)", "$1.$2");
            newValue = newValue.replaceFirst("(\\d{2})\\.(\\d{3})(\\d)", "$1.$2.$3");
            newValue = newValue.replaceFirst("\\.(\\d{3})(\\d)", ".$1/$2");
            newValue = newValue.replaceFirst("(\\d{4})(\\d)", "$1-$2");
            if (newValue.length() > 18) {
                setText(oldValue);
            } else {
                setText(newValue);
            }
        });
    }

    private boolean valida(String cnpj) {
        // considera-se erro cnpj's formados por uma sequencia de numeros iguais
        cnpj = cnpj.replaceAll("[^0-9]", "");
        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111")
                || cnpj.equals("22222222222222") || cnpj.equals("33333333333333")
                || cnpj.equals("44444444444444") || cnpj.equals("55555555555555")
                || cnpj.equals("66666666666666") || cnpj.equals("77777777777777")
                || cnpj.equals("88888888888888") || cnpj.equals("99999999999999")
                || (cnpj.length() != 14)) {
            return (false);
        }

        char dig13, dig14;
        int sm, i, r, num, peso;

        // "try" - protege o código para eventuais erros de conversao de tipo (int)
        // Calculo do 1o. Digito Verificador
        sm = 0;
        peso = 2;
        for (i = 11; i >= 0; i--) {
            // converte o i-ésimo caractere do cnpj em um número:
            // por exemplo, transforma o caractere '0' no inteiro 0
            // (48 eh a posição de '0' na tabela ASCII)
            num = (int) (cnpj.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso + 1;
            if (peso == 10) {
                peso = 2;
            }
        }

        r = sm % 11;
        if ((r == 0) || (r == 1)) {
            dig13 = '0';
        } else {
            dig13 = (char) ((11 - r) + 48);
        }

        // Calculo do 2o. Digito Verificador
        sm = 0;
        peso = 2;
        for (i = 12; i >= 0; i--) {
            num = (int) (cnpj.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso + 1;
            if (peso == 10) {
                peso = 2;
            }
        }

        r = sm % 11;
        if ((r == 0) || (r == 1)) {
            dig14 = '0';
        } else {
            dig14 = (char) ((11 - r) + 48);
        }

        // Verifica se os dígitos calculados conferem com os dígitos informados.
        if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13))) {
            return (true);
        } else {
            return (false);
        }
    }

    public BooleanBinding cnpjValidoBinding(TextField textField) {
        return Bindings.createBooleanBinding(
                () -> valida(textField.getText()),
                textField.textProperty());
    }

}
