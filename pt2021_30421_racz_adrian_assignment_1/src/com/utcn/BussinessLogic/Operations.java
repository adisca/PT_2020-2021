package com.utcn.BussinessLogic;

import com.utcn.DataModels.Monomial;
import com.utcn.DataModels.MonomialFloat;
import com.utcn.DataModels.Polynomial;
import com.utcn.DataModels.PolynomialFloat;

import java.util.Vector;

/**
 * Implements polynomial operations (addition, subtraction, multiplication, division, derivation, integration)
 */
public class Operations {

    /**
     * Takes two Polynomial parameters and returns their sum.
     *
     * @param polynomial1   First polynomial
     * @param polynomial2   Second polynomial
     * @return              The sum of the polynomials
     */
    public Polynomial polynomialAddition(Polynomial polynomial1, Polynomial polynomial2) {
        Polynomial result = new Polynomial(polynomial1.getMonomials());

        for (Monomial monomial : polynomial2.getMonomials()) {
            result.addMonomial(new Monomial(monomial.getCoefficient(), monomial.getDegree()));
        }

        return result;
    }

    /**
     * Takes two Polynomial parameters and returns the difference between the first and the second.
     *
     * @param polynomial1   First polynomial
     * @param polynomial2   Second polynomial
     * @return              The difference of the polynomials
     */
    public Polynomial polynomialSubtraction(Polynomial polynomial1, Polynomial polynomial2) {
        Polynomial result = new Polynomial(polynomial1.getMonomials());

        for (Monomial monomial : polynomial2.getMonomials()) {
            result.addMonomial(new Monomial(-monomial.getCoefficient(), monomial.getDegree()));
        }

        return result;
    }

    /**
     * Takes two PolynomialFloat parameters and returns the difference between the first and the second.
     *
     * It overloads the normal polynomialSubtraction() and does the same thing, but it works with PolynomialFloat.
     * It is only needed for polynomialDivision(), therefore it is private.
     *
     * @param polynomial1   First polynomial, with float coefficients
     * @param polynomial2   Second polynomial, with float coefficients
     * @return              The difference of the polynomials, with float coefficients
     */
    private PolynomialFloat polynomialSubtraction(PolynomialFloat polynomial1, PolynomialFloat polynomial2) {
        PolynomialFloat result = new PolynomialFloat();
        for (MonomialFloat monomialFloat : polynomial1.getMonomialsFloat()) {
            result.addMonomialFloat(new MonomialFloat(monomialFloat.getCoefficientFloat(),
                    monomialFloat.getDegree()));
        }

        for (MonomialFloat monomialFloat : polynomial2.getMonomialsFloat()) {
            result.addMonomialFloat(new MonomialFloat(-monomialFloat.getCoefficientFloat(),
                    monomialFloat.getDegree()));
        }

        return result;
    }

    /**
     * Takes two Polynomial parameters and returns their product.
     *
     * @param polynomial1   First polynomial
     * @param polynomial2   Second polynomial
     * @return              The product of the polynomials
     */
    public Polynomial polynomialMultiplication(Polynomial polynomial1, Polynomial polynomial2) {
        Polynomial result = new Polynomial();

        for (Monomial monomialP1 : polynomial1.getMonomials()) {
            for (Monomial monomialP2 : polynomial2.getMonomials()) {
                result.addMonomial(new Monomial(monomialP1.getCoefficient() * monomialP2.getCoefficient(),
                        monomialP1.getDegree() + monomialP2.getDegree()));
            }
        }

        return result;
    }

    /**
     * Takes two PolynomialFloat parameters and returns their product.
     *
     * It overloads the normal polynomialMultiplication() and does the same thing, but it works with PolynomialFloat.
     * It is only needed for polynomialDivision(), therefore it is private.
     *
     *
     * @param polynomial1   First polynomial, with float coefficients
     * @param polynomial2   Second polynomial, with float coefficients
     * @return              The product of the polynomials, with float coefficients
     */
    private PolynomialFloat polynomialMultiplication(PolynomialFloat polynomial1, PolynomialFloat polynomial2) {
        PolynomialFloat result = new PolynomialFloat();

        for (MonomialFloat monomialP1 : polynomial1.getMonomialsFloat()) {
            for (MonomialFloat monomialP2 : polynomial2.getMonomialsFloat()) {
                result.addMonomialFloat(new MonomialFloat(monomialP1.getCoefficientFloat() *
                        monomialP2.getCoefficientFloat(), monomialP1.getDegree() + monomialP2.getDegree()));
            }
        }

        return result;
    }

    /**
     * Takes two Polynomial parameters and returns the result of the first divided by the second.
     *
     * The returned vector contains the quotient on the first position and the remainder on the second. It uses the
     * algorithm described at the laboratory, with the addition that is checks if the second parameter is
     * "polynomial 0".
     *
     * @param polynomial1   First polynomial
     * @param polynomial2   Second polynomial, should be different from "polynomial 0"
     * @return              A vector containing the quotient and the remainder of the division in float values,
     *                      or an empty vector if the second parameter is the "polynomial 0"
     */
    public Vector<PolynomialFloat> polynomialDivision(Polynomial polynomial1, Polynomial polynomial2) {
        PolynomialFloat polynomialFloat1 = new PolynomialFloat(polynomial1.getMonomials());
        PolynomialFloat polynomialFloat2 = new PolynomialFloat(polynomial2.getMonomials());
        Polynomial checkIf0 = new Polynomial();
        PolynomialFloat quotient = new PolynomialFloat();
        Vector<PolynomialFloat> result = new Vector<>();

        checkIf0.addMonomial(new Monomial(0, 0));
        if (polynomial2.equals(checkIf0))
            return new Vector<>();

        while (polynomialFloat1.getDegreeFloat() >= polynomialFloat2.getDegreeFloat() &&
                !(polynomialFloat1.getDegreeFloat() == 0 &&
                polynomialFloat1.getMonomialsFloat().get(0).getCoefficientFloat() == 0f)) {
            PolynomialFloat lastMonomial = new PolynomialFloat();
            lastMonomial.addMonomialFloat(new MonomialFloat(
                    polynomialFloat1.getMonomialByDegree(polynomialFloat1.getDegree()).getCoefficientFloat() /
                            polynomialFloat2.getMonomialByDegree(polynomialFloat2.getDegree()).getCoefficientFloat(),
                    polynomialFloat1.getDegree() - polynomialFloat2.getDegree()));
            quotient.addMonomialFloat(lastMonomial.getMonomialByDegree(lastMonomial.getDegreeFloat()));

            polynomialFloat1 = polynomialSubtraction(polynomialFloat1,
                    polynomialMultiplication(lastMonomial, polynomialFloat2));
        }
        result.add(quotient);
        result.add(polynomialFloat1);    // Remainder
        return result;
    }

    /**
     * Takes one Polynomial parameter and returns its derivative.
     *
     * @param polynomial    The given polynomial
     * @return              The derivative of the polynomial
     */
    public Polynomial polynomialDerivation(Polynomial polynomial) {
        Polynomial result = new Polynomial(polynomial.getMonomials());

        for (Monomial monomial : result.getMonomials()) {
            monomial.setCoefficient(monomial.getCoefficient() * monomial.getDegree());
        }
        result.decreaseDegree(1);

        return result;
    }

    /**
     * Takes one Polynomial parameter and returns its integral.
     *
     * @param polynomial    The given polynomial
     * @return              The integral of the polynomial
     */
    public PolynomialFloat polynomialIntegration(Polynomial polynomial) {
        PolynomialFloat result = new PolynomialFloat(polynomial.getMonomials());

        for (MonomialFloat monomialFloat : result.getMonomialsFloat()) {
            monomialFloat.setCoefficientFloat(monomialFloat.getCoefficientFloat() /
                    (monomialFloat.getDegree().floatValue() + 1f));
        }
        result.increaseDegree(1);

        return result;
    }
}
