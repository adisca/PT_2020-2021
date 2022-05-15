package com.utcn.BussinessLogic;

import com.utcn.DataModels.Monomial;
import com.utcn.DataModels.Polynomial;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implements pattern matching of regular expressions
 */
public class PatternMatching {

    /**
     * Takes a string and returns a copy without whitespaces.
     *
     * @param input     Input string
     * @return          A copy of same string without whitespaces
     */
    private String removeSpaces(String input) {
        return input.replaceAll("\\s+", "");
    }

    /**
     * Takes a string and returns the polynomial described by it.
     *
     * It uses regex to extract every Monomial from the string and add them together in a Polynomial.
     *
     * @param input     Input string
     * @return          The polynomial resulted from the string
     */
    public Polynomial extractPolynomial(String input) {

        input = removeSpaces(input);
        String stringPattern = "(([-]?[0-9]+)?[*]?([-])?([a-zA-Z])?[\\^]?([0-9]+)?)";
        Pattern pattern = Pattern.compile(stringPattern);
        Matcher matcher = pattern.matcher(input);
        Polynomial polynomial = new Polynomial();

        while (matcher.find()) {
            if (!(matcher.group(2) == null && matcher.group(4) == null && matcher.group(5) == null)) {
                Monomial monomial = new Monomial();

                if (matcher.group(2) != null)
                    monomial.setCoefficient(Integer.valueOf(matcher.group(2)));
                else if (matcher.group(3) != null)
                    monomial.setCoefficient(-1);
                else
                    monomial.setCoefficient(1);

                if (matcher.group(4) == null)
                    monomial.setDegree(0);
                else if (matcher.group(5) == null)
                    monomial.setDegree(1);
                else
                    monomial.setDegree(Integer.valueOf(matcher.group(5)));

                polynomial.addMonomial(monomial);
            }
        }
        return polynomial;
    }

}
