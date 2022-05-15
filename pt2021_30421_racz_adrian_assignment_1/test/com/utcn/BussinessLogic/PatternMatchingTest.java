package com.utcn.BussinessLogic;

import com.utcn.DataModels.Monomial;
import com.utcn.DataModels.Polynomial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatternMatchingTest {

    @Test
    public void extractPolynomialTest() {
        PatternMatching pm = new PatternMatching();
        String input = "24*X^4+x3 -7a ^23- 8x+7";
        Polynomial p1 = new Polynomial();

        p1.addMonomial(new Monomial(7, 0));
        p1.addMonomial(new Monomial(-8, 1));
        p1.addMonomial(new Monomial(-7, 23));
        p1.addMonomial(new Monomial(1, 3));
        p1.addMonomial(new Monomial(24, 4));

        assertEquals(p1, pm.extractPolynomial(input));
    }
}