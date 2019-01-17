package enigma;

import org.junit.Test;


import static org.junit.Assert.*;
import static enigma.TestUtils.UPPER;

public class RotorTest {

    /* ***** TESTING UTILITIES ***** */

    /** Cycles for the reflectors*/
    private String cycle0 = "(AELTPHQXRU) (BKNW) "
            +
            "(CMOY) (DFG) (IV) (JZ) (S)";
    private String cycle3 = "(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)";

    /** Permutations for the reflectors */
    private Permutation perm0 = new Permutation(cycle0, UPPER);
    private Permutation perm3 = new Permutation(cycle3, UPPER);


    /** Moving Rotors */
    private Rotor name1 = new Rotor("I", perm0);
    private Rotor name2 = new Rotor("II", perm3);




    /* ***** TESTS ***** */
    @Test
    public void testConvertForward() {
        name1.set(5);
        name2.set(11);
        assertEquals(8, name1.convertForward(5));
        assertEquals(21, name2.convertForward(8));
    }

    @Test
    public void testConvertBackward() {
        name1.set(5);
        name2.set(11);
        assertEquals(7, name1.convertBackward(9));
        assertEquals(9, name2.convertBackward(25));
    }

}
