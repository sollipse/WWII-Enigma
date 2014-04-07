package enigma;

/** PROJECT CODE FROM CS61B FA 2012 COURSE **/

/** Class that represents a rotor that has no ratchet and does not advance.
 *  @author Paul Kang
 */
class FixedRotor extends Rotor {

    /** Constructor for fixed rotor. 
    * while similar to rotor, we cannot rotate or interchange our fixed rotor.
    **/
    public FixedRotor(String name, String forward, String backward) {
        super(name, forward, backward, null);
    }
    @Override
    boolean advances() {
        return false;
    }

    @Override
    boolean atNotch() {
        return false;
    }

    /** Fixed rotors do not advance. */
    @Override
    void advance() {
    }

}
