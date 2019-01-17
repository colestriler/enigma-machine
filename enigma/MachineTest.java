package enigma;

import org.junit.Test;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;
import static enigma.TestUtils.UPPER;


/** A few tests for Machine.
 *
 @author colestriler */

public class MachineTest {


    /* ***** TESTING UTILITIES ***** */

    /** Cycles for the reflectors*/
    private String cycle1 = "(AELTPHQXRU) (BKNW) "
            +
            "(CMOY) (DFG) (IV) (JZ) (S)";
    private String cycle2 = "(FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)";
    private String cycle3 = "(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)";
    private String cycle4 = "(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)";
    private String cycle5 = "(AJQDVLEOZWIYTS) "
            +
            "(CGMNHFUX) (BPRK) ";
    private String cycle11 = "(AR) (BD) (CO) (EJ) "
            +
            "(FN) (GT) (HK) (IV) (LM) "
            +
            "(PW) (QZ) (SX) (UY)";
    private String plugCycle = "(YF) (ZH)";
    private String betaCycle = "(ALBEVFCYODJWUGNMQTZSKPR) (HIX)";
    private String bCycle = "(AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) "
            +
            "(MP) (RX) (SZ) (TV)";

    /** Permutations for the reflectors */
    private Permutation plubPerm = new Permutation(plugCycle, UPPER);
    private Permutation perm1 = new Permutation(cycle1, UPPER);
    private Permutation perm2 = new Permutation(cycle2, UPPER);
    private Permutation perm3 = new Permutation(cycle3, UPPER);
    private Permutation perm4 = new Permutation(cycle4, UPPER);
    private Permutation perm5 = new Permutation(cycle5, UPPER);
    private Permutation perm11 = new Permutation(cycle11, UPPER);
    private Permutation permBeta = new Permutation(betaCycle, UPPER);
    private Permutation permB = new Permutation(bCycle, UPPER);
    private Permutation permTest = new Permutation("(TD) (KC) (JZ)", UPPER);

    /** Reflectors*/
    private Reflector b = new Reflector("b", permB);
    private Reflector c = new Reflector("c", perm5);

    /** Non-moving rotors */
    private Rotor beta = new Rotor("BETA", permBeta);
    private Rotor gamma = new Rotor("GAMMA", perm5);

    /** Moving Rotors */
    private Rotor name1 = new MovingRotor("I", perm1, "Q");
    private Rotor name2 = new MovingRotor("II", perm2, "E");
    private Rotor name3 = new MovingRotor("III", perm3, "V");
    private Rotor name4 = new MovingRotor("IV", perm4, "J");
    private Rotor name5 = new MovingRotor("V", perm1, "Z");

    /** ALl possible rotors that can be used*/
    private Collection<Rotor> collection = Arrays.asList(
            b, c, beta, gamma, name1,
            name2, name3, name4, name5);

    /** machines */
    private Machine mach0 = new Machine(UPPER,
            5, 3, collection);
    private Machine mach1 = new Machine(UPPER,
            5, 3, collection);
    private Machine mach3 = new Machine(UPPER,
            5, 3, collection);


    /** Used to set the rotors.*/
    private String[] setRotorsTest1 =
            new String[] {"b", "BETA", "I", "II", "III"};
    private String[] setRotorsTest2 =
            new String[] {"c", "GAMMA", "III", "IV", "V"};
    private String[] setRotorsEX =
            new String[] {"b", "BETA", "III", "IV", "I"};



    /* ***** TESTS ***** */


    @Test
    public void convertTest() {

        Machine machTest = new Machine(UPPER, 5, 3, collection);
        machTest.insertRotors(setRotorsTest1);
        machTest.setRotors("AAAA");
        machTest.setPlugboard(permTest);

        String input1 = "IWASSCAREDOFCODINGINJAVA";
        String output1 = "HGJNBOKDWALBFKUCMUTJZUIO";

        assertEquals(output1, machTest.convert(input1));

        mach0.insertRotors(setRotorsEX);
        mach0.setRotors("AXLE");
        mach0.setPlugboard(plubPerm);
        assertEquals("Z", mach0.convert("Y"));

    }


    @Test
    public void testInsertRotor() {

        mach0.insertRotors(setRotorsTest1);
        mach1.insertRotors(setRotorsTest2);

    }

    @Test
    public void setRotorsTest() {

        mach0.insertRotors(setRotorsTest1);
        mach1.insertRotors(setRotorsTest2);
        mach3.insertRotors(setRotorsTest2);

        mach0.setRotors("AXLE");
        assertEquals(0, mach0.getRotors()[1].setting());
        assertEquals(23, mach0.getRotors()[2].setting());
        assertEquals(11, mach0.getRotors()[3].setting());
        assertEquals(4, mach0.getRotors()[4].setting());

        mach1.setRotors("COLE");
        assertEquals(2, mach1.getRotors()[1].setting());
        assertEquals(14, mach1.getRotors()[2].setting());
        assertEquals(11, mach1.getRotors()[3].setting());
        assertEquals(4, mach1.getRotors()[4].setting());
    }

}
