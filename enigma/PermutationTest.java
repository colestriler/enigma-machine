package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

import static enigma.TestUtils.*;
import java.util.Arrays;
import java.util.Collection;
import static enigma.TestUtils.UPPER;


/** The suite of all JUnit tests for the Permutation class.
 *  @author
 */
public class PermutationTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private Permutation perm2;
    private Permutation perm3;
    private String alpha = UPPER_STRING;

    /** Check that perm has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%cRefl'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%cRefl'", e),
                         c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTING UTILITIES ***** */

    /** Cycles for the reflectors*/
    private String cycle0 = "(AELTPHQXRU) (BKNW) "
            +
            "(CMOY) (DFG) (IV) (JZ) (S)";
    private String cycle5 = "(AJQDVLEOZWIYTS) "
            +
            "(CGMNHFUX) (BPRK) ";
    private String cycle11 = "(AR) (BD) (CO) (EJ) "
            +
            "(FN) (GT) (HK) (IV) (LM) "
            +
            "(PW) (QZ) (SX) (UY)";

    /** Permutations for the reflectors */
    private Permutation perm0 = new Permutation(cycle0, UPPER);
    private Permutation perm5 = new Permutation(cycle5, UPPER);
    private Permutation perm11 = new Permutation(cycle11, UPPER);

    /** Reflectors*/
    private Reflector bRefl = new Reflector("bRefl", perm0);
    private Reflector cRefl = new Reflector("cRefl", perm5);

    /** Non-moving rotors */
    private Rotor beta = new Rotor("BETA", perm0);
    private Rotor gamma = new Rotor("GAMMA", perm5);

    /** Moving Rotors */
    private Rotor name1 = new Rotor("I", perm0);
    private Rotor name2 = new Rotor("II", perm0);
    private Rotor name3 = new Rotor("III", perm0);
    private Rotor name4 = new Rotor("IV", perm0);
    private Rotor name5 = new Rotor("V", perm0);

    /** ALl possible rotors that can be used*/
    private Collection<Rotor> collection = Arrays.asList(
            bRefl, cRefl, beta, gamma, name1,
            name2, name3, name4, name5);

    /** machines */
    private Machine mach0 = new Machine(UPPER,
            5, 3, collection);
    private Machine mach1 = new Machine(UPPER,
            5, 3, collection);
    private Machine mach3 = new Machine(UPPER,
            5, 3, collection);


    /* ***** TESTS ***** */


    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING,
                UPPER_STRING);
    }


    @Test
    public void testPermute() {

        perm = new Permutation(cycle0, UPPER);
        perm2 = new Permutation(cycle5, UPPER);
        perm3 = new Permutation(cycle11, UPPER);

        assertEquals(4, perm.permute(0));
        assertEquals(8, perm2.permute(22));
        assertEquals(4, perm.permute(0));
    }



    @Test
    public void testInvert() {
        perm = new Permutation(cycle0, UPPER);
        perm2 = new Permutation(cycle5, UPPER);
        perm3 = new Permutation(cycle11, UPPER);

        assertEquals(20, perm.invert(0));
        assertEquals(25, perm2.invert(22));
        assertEquals(18, perm3.invert(23));
    }

    @Test
    public void testDerangement() {
        perm = new Permutation(cycle0, UPPER);
        perm2 = new Permutation(cycle5, UPPER);
        perm3 = new Permutation(cycle11, UPPER);

        assertFalse(perm.derangement());
        assertTrue(perm2.derangement());
        assertTrue(perm3.derangement());
    }

}
