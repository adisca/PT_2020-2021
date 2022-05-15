package com.utcn.DataModels;

/**
 * Float version of Monomial.
 * Apart from the variables inherited from Monomial, it also stores a float coefficient.
 */
public class MonomialFloat extends Monomial {
    private Float coefficientFloat;

    ///// Constructors /////////////////////////////////////////////////////////

    public MonomialFloat(Float coefficient, Integer degree) {
        setCoefficient(coefficient.intValue());
        setDegree(degree);
        coefficientFloat = coefficient;
    }

    public MonomialFloat(Monomial monomial) {
        setCoefficient(monomial.getCoefficient());
        setDegree(monomial.getDegree());
        coefficientFloat = monomial.getCoefficient().floatValue();
    }

    ///// Getters ///////////////////////////////////////////////////////////////

    public Float getCoefficientFloat() {
        return coefficientFloat;
    }

    ///// Setters ///////////////////////////////////////////////////////////////

    public void setCoefficientFloat(Float coefficientFloat) {
        this.coefficientFloat = coefficientFloat;
        setCoefficient(coefficientFloat.intValue());
    }

    ///// Public Functions //////////////////////////////////////////////////////

    /**
     * Adds a Float to the coefficientFloat.
     *
     * Basically the Float version of addToCoefficient().
     *
     * @param coefficientFloat  Float to be added
     */
    public void addToCoefficientFloat(Float coefficientFloat) {
        this.coefficientFloat += coefficientFloat;
        setCoefficient(this.coefficientFloat.intValue());
    }

    @Override
    public boolean equals(Object monomialFloat) {
        if (!(monomialFloat instanceof MonomialFloat))
            return false;
        return super.equals(monomialFloat) &&
                this.getCoefficientFloat().equals(((MonomialFloat) monomialFloat).getCoefficientFloat());
    }
}
