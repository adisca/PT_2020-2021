package com.utcn.DataModels;

import java.util.Vector;

/**
 * Stores a vector of Monomials, with each monomial's position corresponding to their degree.
 */
public class Polynomial {
    private Vector<Monomial> monomials;
    // degree = monomials.size() - 1 , so it doesn't need to be stored separately

    ///// Constructors /////////////////////////////////////////////////////////

    public Polynomial() {
        this.monomials = new Vector<>();
        this.monomials.add(new Monomial(0, 0)); // It is important that the vector
                                                                // has at least one monomial
    }

    public Polynomial(Vector<Monomial> vec) {
        this.monomials = new Vector<>();
        for (Monomial monomial : vec) {
            this.monomials.add(new Monomial(monomial.getCoefficient(), monomial.getDegree()));
        }
        clean();
    }

    ///// Getters /////////////////////////////////////////////////////////////

    /**
     * Gets the degree of the polynomial, using the size of the monomials vector.
     *
     * degree = monomials.size() - 1
     *
     * @return  The degree of the Polynomial
     */
    public Integer getDegree() {
        return this.monomials.size() - 1;
    }

    public Vector<Monomial> getMonomials() {
        return this.monomials;
    }

    /**
     * Takes the Integer degree and returns the Monomial with that degree.
     *
     * @param degree    Integer degree
     * @return          The Monomial with the specified degree
     */
    public Monomial getMonomialByDegree(Integer degree) {
        if (degree > this.monomials.size() - 1)
            return null;
        else
            return this.monomials.get(degree);
    }

    ///// Setters Functions /////////////////////////////////////////////////////

    public void setMonomials(Vector<Monomial> monomials) {
        this.monomials = monomials;
    }

    /**
     * <strong>Adds a Monomial to the monomials Vector.</strong><p></p>
     *
     * The monomials are not simply inserted at the end of the vector, instead they are placed on the position
     * that matches their degree. If the degree of the polynomial is smaller than the monomial, the vector is
     * supplemented with monomials having the coefficient 0 and the degree that matches the position, until
     * the position of the monomial is reached. Monomials added to the vector do not overwrite the predecessor of
     * their position, instead the coefficient is added to the previous one.
     *
     * @param monomial  Monomial to be added
     */
    public void addMonomial(Monomial monomial) {    // Not quite a setter, but close
        while (this.monomials.size() < monomial.getDegree()) {
            this.monomials.add(new Monomial(0, this.monomials.size()));
        }
        if (this.monomials.size() == monomial.getDegree())
            this.monomials.add(new Monomial(monomial.getCoefficient(), monomial.getDegree()));
        else
            this.monomials.get(monomial.getDegree()).addToCoefficient(monomial.getCoefficient());
        clean();
    }

    ///// Private Functions /////////////////////////////////////////////////////////

    /**
     * Reduces the size of the Polynomial to match its true degree
     */
    private void clean() {
        while (this.monomials.size() > 1 && this.monomials.get(this.monomials.size() - 1).getCoefficient().equals(0))
            this.monomials.remove(this.monomials.size() - 1);
    }

    ///// Public Functions //////////////////////////////////////////////////////////

    /**
     *  Increases the degrees of all Monomials by the Integer parameter and shifts them to their new spots.
     *
     * @param howMuch   By how much do you want to increase it
     */
    public void increaseDegree(Integer howMuch) {
        if (howMuch <= 0)
            return;
        Integer index = 0;
        Vector<Monomial> newMonomials = new Vector<>();
        while (index < howMuch) {
            newMonomials.add(new Monomial(0, index++));
        }
        for (Monomial monomial : this.monomials) {
            monomial.setDegree(monomial.getDegree() + howMuch);
            newMonomials.add(monomial);
        }
        this.setMonomials(newMonomials);
    }

    /**
     *  Decrease the degrees of all Monomials by the Integer parameter and shifts them to their new spots.
     *
     * @param howMuch   By how much do you want to decrease it.
     */
    public void decreaseDegree(Integer howMuch) {
        if (howMuch <= 0)
            return;
        Vector<Monomial> newMonomials = new Vector<>();
        for (Monomial monomial : this.monomials) {
            if (monomial.getDegree() - howMuch >= 0) {
                monomial.setDegree(monomial.getDegree() - howMuch);
                newMonomials.add(monomial);
            }
        }
        this.setMonomials(newMonomials);
    }

    ///// Overrides /////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object polynomial) {
        if (!(polynomial instanceof Polynomial))
            return false;
        if (!((Polynomial) polynomial).getDegree().equals(this.getDegree()))
            return false;
        for (Monomial monomial : this.monomials) {
            if (!(monomial.equals(((Polynomial) polynomial).getMonomialByDegree(monomial.getDegree()))))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String output = "";
        for (Monomial monomial : monomials) {
            if (monomial.getCoefficient() != 0) {
                if (monomial.getDegree() == 0) {
                    output = monomial.getCoefficient() + output;
                    if (monomial.getCoefficient() > 0 && monomial != monomials.lastElement())
                        output = "+" + output;
                    continue;
                }
                if (monomial.getDegree() == 1)
                    output = "x" + output;
                else if (monomial.getDegree() > 1)
                    output = "x^" + monomial.getDegree() + output;
                if (Math.abs(monomial.getCoefficient()) > 1)
                    output = monomial.getCoefficient() + "*" + output;
                else if (monomial.getCoefficient() == -1)
                    output = "-" + output;
                if (monomial.getCoefficient() > 0 && monomial != monomials.lastElement())
                    output = "+" + output;
            }
        }
        if (output.equals(""))
            return "0";
        return output;
    }
}
