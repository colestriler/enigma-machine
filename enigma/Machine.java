package enigma;

import java.util.Collection;
import java.util.HashSet;

/** Class that represents a complete enigma machine.
 *  @author ColeStriler
 */
class Machine {

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** Total number of rotors. */
    private int _numRotors;

    /** Total number of pawls. */
    private int _numPawls;

    /** The array of rotors that formats the machine. */
    private Rotor[] _rotors;

    /** An ArrayList containing all possible rotors that can be used. */
    private Object[] _allRotors;

    /** The initial plugboard which includes steckered pairs. */
    private Permutation _plugboard;

    /**Keep track of the number of pawls for Main.*/
    private int numPawlsNew = 0;


    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _numPawls = pawls;
        _allRotors = allRotors.toArray();
        _rotors = new Rotor[numRotors];
    }

    /** Return the number of pawls.*/
    int getNumPawls() {
        return numPawlsNew;
    }

    /** Return the rotors.*/
    Rotor[] getRotors() {
        return _rotors;
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _numPawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        for (int i = 0; i < rotors.length; i++) {
            for (int j = 0; j < _allRotors.length; j++) {
                if (rotors[i].equals((((Rotor) _allRotors[j]).name()))) {
                    _rotors[i] = (Rotor) _allRotors[j];
                }
            }
        }
        counter();
        if (_rotors.length != rotors.length) {
            throw new EnigmaException("Misnamed rotors");
        }
    }

    /**Counts the number of pawls for us.*/
    private void counter() {
        numPawlsNew = 0;
        for (Rotor r: _rotors) {
            if (r.rotates()) {
                numPawlsNew++;
            }
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors() - 1 upper-case letters. The first letter refers to the
     *  leftmost rotor setting (not counting the reflector).*/
    void setRotors(String setting) {

        if (setting.length() != numRotors() - 1) {
            throw new EnigmaException("Initial positions string wrong length");
        }
        for (int i = 1; i <= setting.length(); i++) {
            if (!_alphabet.contains(setting.charAt(i - 1))) {
                throw new EnigmaException("Initial positions"
                        +
                        " string not in alphabet");
            }
            _rotors[i].set(setting.charAt(i - 1));
        }

    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    private int convert(int c) {

        advanceRotors();

        int result = _plugboard.permute(c);
        for (int i = _numRotors - 1; i >= 0; i--) {
            result = _rotors[i].convertForward(result);
        }
        for (int i = 1; i <= _numRotors - 1; i++) {
            result = _rotors[i].convertBackward(result);
        }

        result = _plugboard.permute(result);
        return result;
    }

    /**Correct way of double stepping.*/
    private void advanceRotors() {
        HashSet<Rotor> rotorHash = new HashSet<>();
        rotorHash.add(_rotors[_rotors.length - 1]);
        for (int i = 1; i < _rotors.length - 1; i++) {
            Rotor currentRotor = _rotors[i];
            Rotor nextRotor = _rotors[i + 1];
            if (nextRotor.atNotch() && nextRotor.rotates()) {
                rotorHash.add(currentRotor);
                rotorHash.add(nextRotor);
            }
        }
        for (Rotor r: rotorHash) {
            r.advance();
        }
    }

    /**First way of trying to double step.*/
    private void oldAdvanceRotors() {
        for (int i = numRotors() - 1; i > 0; i--) {
            if (_rotors[i].atNotch()) {
                _rotors[i - 1].advance();
                if (_rotors[i] != _rotors[numRotors() - 1]) {
                    _rotors[i].advance();
                }
            }
        }
        _rotors[_numRotors - 1].advance();
    }


    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {

        msg = msg.replaceAll(" ", "");
        String result = "";
        for (int i = 0; i < msg.length(); i++) {
            char converted = _alphabet.toChar(convert(
                    _alphabet.toInt(msg.charAt(i))));
            result += converted;
        }
        return result;
    }


}
