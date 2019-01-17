package enigma;


import static enigma.EnigmaException.*;

/**
 * An Alphabet that is inputted as a string, not a range..
 *
 * @author ColeStriler
 */
class CharacterRangeSecond extends Alphabet {

    /** The characters in the new alphabet.*/
    private char[] chars;


    /**
     * New character range that can take in a string.
     * CHARACTERS is the alphabet inputted.
     */
    CharacterRangeSecond(String characters) {
        chars = characters.toCharArray();
    }

    /** Returns the size of the alphabet. */
    @Override
    int size() {
        return chars.length;
    }

    /** Returns true if preprocess(CH) is in this alphabet. */
    @Override
    boolean contains(char ch) {
        for (char c: chars) {
            if (c == ch) {
                return true;
            }
        }
        return false;
    }

    /** Returns character number INDEX in the alphabet, where
     *  0 <= INDEX < size(). */
    char toChar(int index) {
        return chars[index];
    }

    /** Returns the index of character preprocess(CH), which must be in
     *  the alphabet. This is the inverse of toChar(). */
    int toInt(char ch) {
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ch) {
                return i;
            }
        }
        throw error("character index out of range");
    }
}
