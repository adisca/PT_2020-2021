package com.utcn.DataModels;

import java.util.Objects;

/**
 * Stores the coefficient and degree of a monomial.
 */
public class Monomial {
    private Integer coefficient;
    private Integer degree;

    ///// Constructors /////////////////////////////////////////////////////////

    public Monomial() {
        coefficient = 0;
        degree = 0;
    }

    public Monomial(Integer coefficient, Integer degree) {
        this.coefficient = coefficient;
        this.degree = degree;
    }

    ///// Getters //////////////////////////////////////////////////////////////

    public Integer getCoefficient() {
        return coefficient;
    }

    public Integer getDegree() {
        return degree;
    }

    ///// Setters //////////////////////////////////////////////////////////////

    public void setCoefficient(Integer coefficient) {
        this.coefficient = coefficient;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    ///// Public Functions //////////////////////////////////////////////////////

    /**
     * Adds an Integer to the coefficient.
     *
     * @param coefficient   Integer to be added
     */
    public void addToCoefficient(Integer coefficient) {
        this.coefficient += coefficient;
    }

    ///// Overrides //////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object monomial) {
        if (!(monomial instanceof Monomial))
            return false;
        return ((Monomial) monomial).getDegree().equals(this.getDegree()) && ((Monomial) monomial).getCoefficient().
                equals(this.getCoefficient());
    }

}
