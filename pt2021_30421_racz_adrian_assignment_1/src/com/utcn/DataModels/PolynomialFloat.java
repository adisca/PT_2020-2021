package com.utcn.DataModels;

import java.util.Vector;

/**
 * Float version of Polynomial.
 * Apart from the vector inherited from Polynomial, it also stores a MonomialFloat vector,
 * with each monomialFloat's position corresponding to their degree.
 */
public class PolynomialFloat extends Polynomial {

    private Vector<MonomialFloat> monomialsFloat;

    ///// Constructors //////////////////////////////////////////////////////

    public PolynomialFloat() {
        super();
        this.monomialsFloat = new Vector<>();
        this.monomialsFloat.add(new MonomialFloat(0.0f, 0));
    }

    public PolynomialFloat(Vector<Monomial> vec) {
        super.setMonomials(new Vector<>());
        for (Monomial monomial : vec) {
            getMonomials().add(new Monomial(monomial.getCoefficient(), monomial.getDegree()));
        }
        synchroniseMonomialsFloat();
        clean();
    }

    ///// Getters ///////////////////////////////////////////////////////////

    public Vector<MonomialFloat> getMonomialsFloat() {
        return monomialsFloat;
    }

    /**
     * Gets the degree of the polynomial, using the size of the monomialsFloat vector.
     *
     * degree = monomialsFloat.size() - 1
     *
     * @return  The degree of the PolynomialFloat
     */
    public Integer getDegreeFloat() {
        return monomialsFloat.size() - 1;
    }

    ///// Setters ///////////////////////////////////////////////////////////

    public void setMonomialsFloat(Vector<MonomialFloat> monomialsFloat) {
        this.monomialsFloat = monomialsFloat;
    }

    /**
     * <strong>Adds a MonomialFloat to the monomials Vector.</strong><p></p>
     *
     * Float version of addMonomial, does the same thing but with MonomialFloats in monomialsFloat vector.
     *
     * @param monomialFloat  MonomialFloat to be added
     */
    public void addMonomialFloat(MonomialFloat monomialFloat) {
        while (getMonomialsFloat().size() < monomialFloat.getDegree()) {
            getMonomialsFloat().add(new MonomialFloat(0.0f, getMonomialsFloat().size()));
        }
        if (getMonomialsFloat().size() == monomialFloat.getDegree())
            getMonomialsFloat().add(new MonomialFloat(monomialFloat.getCoefficientFloat(), monomialFloat.getDegree()));
        else
            getMonomialsFloat().get(monomialFloat.getDegree()).addToCoefficientFloat(monomialFloat.getCoefficientFloat());
        clean();
        synchroniseMonomialsInt();
    }

    ///// Private Functions /////////////////////////////////////////////////

    /**
     * Reduces the size of the Polynomial to match its true degree, Float version.
     */
    private void clean() {
        while (getMonomialsFloat().size() > 1 && getMonomialsFloat().get(getMonomialsFloat().size() - 1).
                getCoefficientFloat().equals(0.0f))
            getMonomialsFloat().remove(getMonomialsFloat().size() - 1);
    }

    ///// Public Functions //////////////////////////////////////////////////

    /**
     * Synchronises monomialsFloat with  monomials.
     *
     * Because monomials and monomialsFloat are not bound by one another,
     * they need to be synchronised when a change happened in one of them.
     * This method copies all Monomials from monomials to monomialsFloat as MonomialFloats
     */
    public void synchroniseMonomialsFloat() {
        monomialsFloat = new Vector<>();
        for (Monomial monomial : super.getMonomials()) {
            monomialsFloat.add(new MonomialFloat(monomial));
        }
    }

    /**
     * Synchronises monomials with  monomialsFloat.
     *
     * Because monomials and monomialsFloat are not bound by one another,
     * they need to be synchronised when a change happened in one of them.
     * This method copies all MonomialFloats from monomialsFloat to monomials as Monomials
     */
    public void synchroniseMonomialsInt() {
        setMonomials(new Vector<>());
        for (MonomialFloat monomial : getMonomialsFloat()) {
            super.addMonomial(monomial);
        }
    }

    ///// Overrides /////////////////////////////////////////////////////////

    /**
     *  Increases the degrees of all MonomialFloats by the Integer parameter and shifts them to their new spots.
     *
     *  Float version of increaseDegree() from super.
     *
     * @param howMuch   By how much do you want to increase it
     */
    @Override
    public void increaseDegree(Integer howMuch) {
        if (howMuch <= 0)
            return;
        Integer index = 0;
        Vector<MonomialFloat> newMonomials = new Vector<>();
        while (index < howMuch) {
            newMonomials.add(new MonomialFloat(0.0f, index++));
        }
        for (MonomialFloat monomialFloat : monomialsFloat) {
            monomialFloat.setDegree(monomialFloat.getDegree() + howMuch);
            newMonomials.add(monomialFloat);
        }
        this.setMonomialsFloat(newMonomials);
        synchroniseMonomialsInt();
    }

    /**
     * Takes the Integer degree and returns the MonomialFloat with that degree.
     *
     * Float version of getMonomialByDegree() from super.
     *
     * @param degree    Integer degree
     * @return          The MonomialFloat with the specified degree
     */
    @Override
    public MonomialFloat getMonomialByDegree(Integer degree) {
        if (degree > monomialsFloat.size() - 1)
            return null;
        else
            return monomialsFloat.get(degree);
    }

    @Override
    public boolean equals(Object polynomial) {
        if (!(polynomial instanceof PolynomialFloat))
            return false;
        if (!((PolynomialFloat) polynomial).getDegree().equals(this.getDegree()))
            return false;
        for (MonomialFloat monomialFloat : monomialsFloat) {
            if (!(monomialFloat.equals(((PolynomialFloat) polynomial).getMonomialByDegree(monomialFloat.getDegree()))))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String output = "";
        for (MonomialFloat monomialFloat : monomialsFloat) {
            if (monomialFloat.getCoefficientFloat() - monomialFloat.getCoefficientFloat().intValue() == 0f) {
                output = toStringContinuation(output, monomialFloat);
            } else {
                if (monomialFloat.getCoefficientFloat() != 0f) {
                    if (monomialFloat.getDegree() == 0) {
                        output = monomialFloat.getCoefficientFloat() + output;
                        if (monomialFloat.getCoefficientFloat() > 0f && monomialFloat != monomialsFloat.lastElement())
                            output = "+" + output;
                        continue;
                    }
                    if (monomialFloat.getDegree() == 1)
                        output = "x" + output;
                    else if (monomialFloat.getDegree() > 1)
                        output = "x^" + monomialFloat.getDegree() + output;
                    if (Math.abs(monomialFloat.getCoefficientFloat()) != 1f)
                        output = monomialFloat.getCoefficientFloat() + "*" + output;
                    else if (monomialFloat.getCoefficientFloat() == -1f)
                        output = "-" + output;
                    if (monomialFloat.getCoefficientFloat() > 0f && monomialFloat != monomialsFloat.lastElement())
                        output = "+" + output;
                }
            }
        }
        if (output.equals(""))
            return "0";
        return output;
    }

    /**
     * Continuation of toString(), thanks to the "beautiful" 30 lines per method restriction
     */
    private String toStringContinuation(String output, MonomialFloat monomialFloat) {
        if (monomialFloat.getCoefficientFloat().intValue() != 0) {
            if (monomialFloat.getDegree() == 0) {
                output = monomialFloat.getCoefficientFloat().intValue() + output;
                if (monomialFloat.getCoefficientFloat().intValue() > 0 && monomialFloat != monomialsFloat.lastElement())
                    output = "+" + output;
                return output;
            }
            if (monomialFloat.getDegree() == 1)
                output = "x" + output;
            else if (monomialFloat.getDegree() > 1)
                output = "x^" + monomialFloat.getDegree() + output;
            if (Math.abs(monomialFloat.getCoefficientFloat().intValue()) > 1)
                output = monomialFloat.getCoefficientFloat().intValue() + "*" + output;
            else if (monomialFloat.getCoefficientFloat().intValue() == -1)
                output = "-" + output;
            if (monomialFloat.getCoefficientFloat().intValue() > 0 && monomialFloat != monomialsFloat.lastElement())
                output = "+" + output;
        }

        return output;
    }

}
