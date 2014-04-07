package enigma;

/** Class that represents a reflector in the enigma.
 *  @author Paul Kang
 */
class Reflector extends Rotor {

    /** Constructs a REFLECTOR with NAME and PERMUTATION;
     *  A Reflector is a rotor that does not rotate, and simply
     *  passes data back along the machine so that the 
     *  remaining rotors can backwards permute.  */
    public Reflector(String name, String permutation) {
        super(name, permutation, null, null);
    }

    @Override
    boolean hasInverse() {
        return false;
    }

    /** Returns a useless value; should never be called. */
    @Override
    int convertBackward(int unused) {
        throw new UnsupportedOperationException();
    }

}
