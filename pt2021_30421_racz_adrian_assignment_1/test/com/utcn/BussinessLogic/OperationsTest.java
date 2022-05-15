package com.utcn.BussinessLogic;

import com.utcn.DataModels.Monomial;
import com.utcn.DataModels.MonomialFloat;
import com.utcn.DataModels.Polynomial;
import com.utcn.DataModels.PolynomialFloat;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {

    @Test
    public void polynomialAdditionTest1() {
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();
        Polynomial p3 = new Polynomial();
        Operations op = new Operations();

        p1.addMonomial(new Monomial(1, 0));
        p1.addMonomial(new Monomial(2, 1));
        p1.addMonomial(new Monomial(-1, 2));

        p2.addMonomial(new Monomial(1, 1));
        p2.addMonomial(new Monomial(1, 2));

        p3.addMonomial(new Monomial(1, 0));
        p3.addMonomial(new Monomial(3, 1));

        assertEquals(p3, op.polynomialAddition(p1, p2));
        // -x^2+2*x+1   +   x^2+x = 3*x+1
    }

    @Test
    public void polynomialAdditionTest2() {
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();
        Polynomial p3 = new Polynomial();
        Operations op = new Operations();

        p1.addMonomial(new Monomial(1, 0));
        p1.addMonomial(new Monomial(-8, 1));
        p1.addMonomial(new Monomial(1, 2));
        p1.addMonomial(new Monomial(-3, 4));
        p1.addMonomial(new Monomial(4, 5));

        p2.addMonomial(new Monomial(-1, 0));
        p2.addMonomial(new Monomial(2, 1));
        p2.addMonomial(new Monomial(1, 2));
        p2.addMonomial(new Monomial(-1, 3));
        p2.addMonomial(new Monomial(3, 4));

        p3.addMonomial(new Monomial(-6, 1));
        p3.addMonomial(new Monomial(2, 2));
        p3.addMonomial(new Monomial(-1, 3));
        p3.addMonomial(new Monomial(4, 5));

        assertEquals(p3, op.polynomialAddition(p1, p2));
        // 4*x^5-3*x^4+x^2-8*x+1   +   3*x^4-x^3+x^2+2*x-1  =  4*x^5-x^3+2*x^2-6*x
    }

    @Test
    public void polynomialSubtractionTest1() {
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();
        Polynomial p3 = new Polynomial();
        Operations op = new Operations();

        p1.addMonomial(new Monomial(1, 0));
        p1.addMonomial(new Monomial(2, 1));
        p1.addMonomial(new Monomial(1, 2));

        p2.addMonomial(new Monomial(1, 1));
        p2.addMonomial(new Monomial(1, 2));

        p3.addMonomial(new Monomial(1, 0));
        p3.addMonomial(new Monomial(1, 1));

        assertEquals(p3, op.polynomialSubtraction(p1, p2));
        // x^2+2*x+1   -   x^2+x   =   x+1
    }

    @Test
    public void polynomialSubtractionTest2() {
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();
        Polynomial p3 = new Polynomial();
        Operations op = new Operations();

        p1.addMonomial(new Monomial(1, 0));
        p1.addMonomial(new Monomial(-8, 1));
        p1.addMonomial(new Monomial(1, 2));
        p1.addMonomial(new Monomial(-3, 4));
        p1.addMonomial(new Monomial(4, 5));

        p2.addMonomial(new Monomial(-1, 0));
        p2.addMonomial(new Monomial(2, 1));
        p2.addMonomial(new Monomial(1, 2));
        p2.addMonomial(new Monomial(-1, 3));
        p2.addMonomial(new Monomial(3, 4));

        p3.addMonomial(new Monomial(2, 0));
        p3.addMonomial(new Monomial(-10, 1));
        p3.addMonomial(new Monomial(1, 3));
        p3.addMonomial(new Monomial(-6, 4));
        p3.addMonomial(new Monomial(4, 5));

        assertEquals(p3, op.polynomialSubtraction(p1, p2));
        // 4*x^5-3*x^4+x^2-8*x+1   -   3*x^4-x^3+x^2+2*x-1   =   4*x^5-6*x^4+x^3-10*x+2
    }

    @Test
    public void polynomialMultiplicationTest() {
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();
        Polynomial p3 = new Polynomial();
        Operations op = new Operations();

        p1.addMonomial(new Monomial(1, 0));
        p1.addMonomial(new Monomial(-1, 1));
        p1.addMonomial(new Monomial(3, 2));

        p2.addMonomial(new Monomial(-2, 0));
        p2.addMonomial(new Monomial(1, 1));

        p3.addMonomial(new Monomial(-2, 0));
        p3.addMonomial(new Monomial(3, 1));
        p3.addMonomial(new Monomial(-7, 2));
        p3.addMonomial(new Monomial(3, 3));

        assertEquals(p3, op.polynomialMultiplication(p1, p2));
        // 3*x^2-x+1   *   x-2   =   3*x^3-7*x^2+3*x-2
    }

    @Test
    public void polynomialDivisionTest1() {
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();
        PolynomialFloat p3 = new PolynomialFloat();
        PolynomialFloat p4 = new PolynomialFloat();
        Operations op = new Operations();

        p1.addMonomial(new Monomial(-5, 0));
        p1.addMonomial(new Monomial(6, 1));
        p1.addMonomial(new Monomial(-2, 2));
        p1.addMonomial(new Monomial(1, 3));

        p2.addMonomial(new Monomial(-1, 0));
        p2.addMonomial(new Monomial(1, 2));

        p3.addMonomialFloat(new MonomialFloat(-2f, 0));
        p3.addMonomialFloat(new MonomialFloat(1f, 1));

        p4.addMonomialFloat(new MonomialFloat(-7f, 0));
        p4.addMonomialFloat(new MonomialFloat(7f, 1));

        assertEquals(p3, op.polynomialDivision(p1, p2).get(0));
        assertEquals(p4, op.polynomialDivision(p1, p2).get(1));
        // x^3-2*x^2+6*x-5   :   x^2-1   =   x-2   remainder:   7*x-7
    }

    @Test
    public void polynomialDivisionTest2() {
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();
        PolynomialFloat p3 = new PolynomialFloat();
        PolynomialFloat p4 = new PolynomialFloat();
        Operations op = new Operations();

        p1.addMonomial(new Monomial(-2, 0));
        p1.addMonomial(new Monomial(4, 1));
        p1.addMonomial(new Monomial(2, 2));

        p2.addMonomial(new Monomial(2, 0));

        p3.addMonomialFloat(new MonomialFloat(-1f, 0));
        p3.addMonomialFloat(new MonomialFloat(2f, 1));
        p3.addMonomialFloat(new MonomialFloat(1f, 2));

        p4.addMonomialFloat(new MonomialFloat(0f, 0));

        assertEquals(p3, op.polynomialDivision(p1, p2).get(0));
        assertEquals(p4, op.polynomialDivision(p1, p2).get(1));
        // 2*x^2+4*x-2   :   2   =   x^2+2*x-1   remainder:   0
    }

    @Test
    public void polynomialDerivationTest() {
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();
        Operations op = new Operations();

        p1.addMonomial(new Monomial(-5, 0));
        p1.addMonomial(new Monomial(6, 1));
        p1.addMonomial(new Monomial(-2, 2));
        p1.addMonomial(new Monomial(1, 3));

        p2.addMonomial(new Monomial(6, 0));
        p2.addMonomial(new Monomial(-4, 1));
        p2.addMonomial(new Monomial(3, 2));

        assertEquals(p2, op.polynomialDerivation(p1));
        // ( x^3-2*x^2+6*x-5 )'   =   3*x^2-4*x+6
    }

    @Test
    public void polynomialIntegrationTest() {
        Polynomial p1 = new Polynomial();
        PolynomialFloat p2 = new PolynomialFloat();
        Operations op = new Operations();

        p1.addMonomial(new Monomial(5, 0));
        p1.addMonomial(new Monomial(4, 2));
        p1.addMonomial(new Monomial(1, 3));

        p2.addMonomialFloat(new MonomialFloat(5f, 1));
        p2.addMonomialFloat(new MonomialFloat(4f / 3f, 3));
        p2.addMonomialFloat(new MonomialFloat(1f / 4f, 4));

        assertEquals(p2, op.polynomialIntegration(p1));
        // S( x^3+4*x^2+5 )dx   =   1/4*x^4+4/3*x^3+5*x
    }
}