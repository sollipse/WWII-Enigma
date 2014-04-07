package enigma;

/** Class that represents a complete enigma machine.
 *  @author Paul Kang
 */
class Machine {
    /** A ROTOR array. We will **/
    private Rotor[] _rotors;
    /** Checks for properly configured INITIALIZATION parameters. */
    private boolean _isConfigured;

    /** Creates a Machine, and describes it as unconfigured. */
    public Machine() {
        _isConfigured = false;
    }

    /** Set my rotors to (from left to right) ROTORS.  Initially, the rotor
     *  settings are all 'A'. */
    void replaceRotors(Rotor[] rotors) {
        _rotors = rotors;
    }

    /** Set my rotors according to SETTING, which must be a string of four
     *  upper-case letters. The first letter refers to the leftmost
     *  rotor setting.  */
    void setRotors(String setting) {
        for (int i = 0; i < 4; i++) {
            char currChar = setting.charAt(i);
            if (!Character.isLetter(currChar)) {
                System.out.println("Error: Rotor settings can only "
                                   + "be letters.");
                System.exit(1);
            }
            this._rotors[i + 1].set(Rotor.toIndex(currChar));
        }
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        if (!_isConfigured) {
            System.out.println("Error: Machine has not been configured.");
            System.exit(1);
        }
        String result = "";
        for (int i = 0; i < msg.length(); i++) {
            char currChar = msg.charAt(i);
            if (!Character.isLetter(currChar)) {
                System.out.println("Error: Unrecognized "
                                   + "character given as input");
                System.exit(1);
            }
            int currIndex = Rotor.toIndex(currChar);
            if (this._rotors[3].atNotch()) {
                this._rotors[3].advance();
                this._rotors[2].advance();
            } else if (this._rotors[4].atNotch()) {
                this._rotors[3].advance();
            }
            this._rotors[4].advance();
            for (int j = 4; j >= 0; j--) {
                currIndex = this._rotors[j].convertForward(currIndex);
            }
            for (Rotor rotor : this._rotors) {
                if (rotor.hasInverse()) {
                    currIndex = rotor.convertBackward(currIndex);
                }
            }
            result += Rotor.toLetter(currIndex);
        }
        return result;
    }

    /** Sets the _isConfigured to STATUS. */
    void setConfigureStatus(boolean status) {
        _isConfigured = status;
    }
}
