package enigma;


/** Class that represents a rotating rotor in the enigma machine.
 *  @author ColeStriler
 */
class MovingRotor extends Rotor {

    /**The rotor's notches.*/
    private String _notches;

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = notches;
    }

    @Override
    boolean atNotch() {
        for (int i = 0; i < _notches.length(); i++) {
            if (alphabet().toInt(_notches.charAt(i)) == this.setting()) {
                return true;
            }
        }
        return false;
    }

    /** ANY rotor that's a 'moving rotor' can rotate.*/
    @Override
    boolean rotates() {
        return true;
    }


    @Override
    void advance() {
        set(this.setting() + 1);
    }


}
