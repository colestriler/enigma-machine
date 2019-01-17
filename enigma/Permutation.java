package enigma;


/**
 * Represents a permutation of a range of integers starting at 0 corresponding
 * to the characters of an alphabet.
 *
 * @author ColeStriler
 */
class Permutation {
    /**Alphabet of this permutation.*/
    private Alphabet _alphabet;

    /**Cycles for the permutation.*/
    private String[] _cycles;

    /**
     * Set this Permutation to that specified by CYCLES, a string in the
     * form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     * is interpreted as a permutation in cycle notation.  Characters in the
     * alphabet that are not included in any cycle map to themselves.
     * Whitespace is ignored.
     */

    /**Return the cycles.*/
    String[] getCycles() {
        return _cycles;
    }

    /**
     * Turns string of cycles into permutation.
     * Here we have CYCLES and ALPHABET.
     * */
    Permutation(String cycles, Alphabet alphabet) {

        _alphabet = alphabet;
        String cyclesTemp = cycles.trim();
        cyclesTemp = cyclesTemp.replace("(", "");
        cyclesTemp = cyclesTemp.replace(")", "");
        cyclesTemp = cyclesTemp.replace("+", "");
        _cycles = cyclesTemp.split(" ");
    }

    /**
     * Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     * c0c1...cm.
     */
    public void addCycle(String cycle) {
        int len = _cycles.length;
        String[] setCycle = new String[len + 1];
        for (int i = 0; i < len; i++) {
            setCycle[i] = _cycles[i];
        }
        setCycle[len] = cycle;
        _cycles = setCycle;
    }


    /**
     * Return the value of P modulo the SIZE of this permutation.
     */
    private int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /**
     * Return the value of P modulo the SIZE of this permutation.
     */
    private int wrap(int p, int size) {
        int r = p % size;
        if (r < 0) {
            r += size;
        }
        return r;
    }

    /**
     * Returns the size of the alphabet I permute.
     */
    int size() {
        return _alphabet.size();
    }

    /**
     * Return the result of applying this permutation to P modulo the
     * alphabet size.
     */
    int permute(int p) {
        char pChar = _alphabet.toChar(wrap(p));
        char newPerm;
        for (int i = 0; i < _cycles.length; i++) {
            for (int k = 0; k < _cycles[i].length(); k++) {
                if (_cycles[i].charAt(k) == pChar) {
                    newPerm = _cycles[i].charAt(
                            wrap((k + 1), _cycles[i].length()));
                    return _alphabet.toInt(newPerm);
                }
            }
        }
        return p;
    }


    /**
     * Return the result of applying the inverse of this permutation
     * to  C modulo the alphabet size.
     */
    int invert(int c) {
        char cChar = _alphabet.toChar(wrap(c));
        char newInv;
        for (int i = 0; i < _cycles.length; i++) {
            for (int k = 0; k < _cycles[i].length(); k++) {
                if (_cycles[i].charAt(k) == cChar) {
                    newInv = _cycles[i].charAt(wrap((k - 1),
                            _cycles[i].length()));
                    return _alphabet.toInt(newInv);
                }
            }
        }
        return c;
    }

    /**
     * Return the result of applying this permutation to the index of P
     * in ALPHABET, and converting the result to a character of ALPHABET.
     */
    char permute(char p) {
        int index = _alphabet.toInt(p);
        return _alphabet.toChar(permute(index));
    }

    /**
     * Return the result of applying the inverse of this permutation to C.
     */
    int invert(char c) {
        int index = _alphabet.toInt(c);
        return _alphabet.toChar(invert(index));
    }

    /**
     * Return the alphabet used to initialize this Permutation.
     */
    Alphabet alphabet() {
        return _alphabet;
    }

    /**
     * Return true iff this permutation is a derangement (i.e., a
     * permutation for which no value maps to itself).
     */

    boolean derangement() {
        int total = 0;
        for (int i = 0; i < _cycles.length; i++) {
            if (_cycles[i].length() != 1) {
                total += _cycles[i].length();
            }
        }
        return total == _alphabet.size();
    }


}
