package enigma;

/** This class represents a spinning rotor in an enigma machine.
    
 *  @author Paul Kang
 */
class Rotor {

    /** The name of this ROTOR (Roman numeral I-VIII). */
    protected String _name;
    /** The forwards mapping for this ROTOR. */
    protected String _forwardsPermutation;
    /** The backwards mapping for this ROTOR. */
    protected String _backwardsPermutation;
    /** The NOTCHES for this ROTOR. */
    private String _notches;

    /** Constructs a ROTOR, which has a NAME, a FORWARDS permutation for mapping
     *  letters, a BACKWARDS permutation for extra encryption, and NOTCHES to
     *  allow the rotors to rotate. */
    public Rotor(String name, String forwards,
                 String backwards, String notches) {
        _name = name;
        _forwardsPermutation = forwards;
        _backwardsPermutation = backwards;
        _notches = notches;
    }

    /** Size of alphabet used for plaintext and ciphertext. */
    static final int ALPHABET_SIZE = 26;

    /** Assuming that P is an integer in the range 0..25, returns the
     *  corresponding upper-case letter in the range A..Z. */
    static char toLetter(int p) {
        return (char) (p + 'A');
    }

    /** Assuming that C is an upper-case letter in the range A-Z, return the
     *  corresponding index in the range 0..25. Inverse of toLetter. */
    static int toIndex(char c) {
        return (int) c - 'A';
    }

    /** Returns true iff this rotor has a ratchet and can advance. */
    boolean advances() {
        return true;
    }

    /** Returns true iff this rotor has a left-to-right inverse. */
    boolean hasInverse() {
        return true;
    }

    /** Return my current rotational setting as an integer between 0
     *  and 25 (corresponding to letters 'A' to 'Z').  */
    int getSetting() {
        return _setting;
    }

    /** Set getSetting() to POSN.  */
    void set(int posn) {
        assert 0 <= posn && posn < ALPHABET_SIZE;
        _setting = posn;
    }

    /** Return the conversion of P (an integer in the range 0..25)
     *  according to my permutation. */
    int convertForward(int p) {
        /* Must adjust P to account for current setting of the Rotor */
        int index = (this.getSetting() + p) % ALPHABET_SIZE;
        char translated = this._forwardsPermutation.charAt(index);
        int output = Rotor.toIndex(translated) - this.getSetting();
        if (output < 0) {
            output += ALPHABET_SIZE;
        }
        return output % ALPHABET_SIZE;
    }

    /** Return the conversion of E (an integer in the range 0..25)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        int index = (this.getSetting() + e) % ALPHABET_SIZE;
        char translated = this._backwardsPermutation.charAt(index);
        int output = Rotor.toIndex(translated) - this.getSetting();
        if (output < 0) {
            output += ALPHABET_SIZE;
        }
        return output % ALPHABET_SIZE;
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        for (int i = 0; i < _notches.length(); i++) {
            if (this.getSetting() == Rotor.toIndex(_notches.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /** Advance me one position. */
    void advance() {
        this.set((this.getSetting() + 1) % ALPHABET_SIZE);
    }

    /** My current setting (index 0..25, with 0 indicating that 'A'
     *  is showing). */
    private int _setting;

}
