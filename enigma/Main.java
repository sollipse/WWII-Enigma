package enigma;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;


/** Enigma simulator.
 *  @author Paul Kang
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified in the input from the standard input.  Print the
     *  results on the standard output. Exits normally if there are
     *  no errors in the input; otherwise with code 1. */
    public static void main(String[] unused) {
        Machine M;
        BufferedReader input =
            new BufferedReader(new InputStreamReader(System.in));

        buildRotors();

        M = null;

        try {
            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                if (isConfigurationLine(line)) {
                    M = new Machine();
                    configure(M, line);
                } else {
                    if (M == null) {
                        System.out.println("Error: Machine not initialized");
                        System.exit(1);
                    }
                    printMessageLine(M.convert(standardize(line)));
                }
            }
        } catch (IOException excp) {
            System.err.printf("Input error: %s%n", excp.getMessage());
            System.exit(1);
        }
    }

    /** Return true iff LINE is an Enigma configuration line. */
    private static boolean isConfigurationLine(String line) {
        return line.startsWith("*");
    }

    /** Configure M according to the specification given on CONFIG,
     *  which must have the format specified in the assignment. */
    private static void configure(Machine M, String config) {
        String[] settings = config.split("\\s");
        if (settings.length != 7) {
            System.out.println("Wrong number of configurations given.");
            System.exit(1);
        }
        Rotor[] chosenRotors  = new Rotor[5];
        for (int i = 1; i < 6; i++) {
            chosenRotors[i - 1] = _rotors.get(standardize(settings[i]));
        }
        if (!(chosenRotors[0] instanceof Reflector)) {
            System.out.println("Error: Reflector must be in the first slot.");
            System.exit(1);
        }
        if (!(chosenRotors[1] instanceof FixedRotor)) {
            System.out.println("Error: FixedRotor must be in the second slot.");
            System.exit(1);
        }
        for (int i = 2; i < 5; i++) {
            if ((chosenRotors[i] instanceof Reflector)
                || chosenRotors[i] instanceof FixedRotor) {
                System.out.println("Error: Only regular rotors "
                                   + "may be in slots 3-5.");
                System.exit(1);
            }
        }
        for (int i = 0; i < chosenRotors.length; i++) {
            for (int j = 0; j < chosenRotors.length; j++) {
                if (i != j && chosenRotors[i] == chosenRotors[j]) {
                    System.out.println("Error: Can't use the same "
                                       + "rotor more than once.");
                    System.exit(1);
                }
            }
        }
        M.replaceRotors(chosenRotors);
        String position = settings[6];
        M.setRotors(standardize(settings[6]));
        M.setConfigureStatus(true);
    }

    /** Return the result of converting LINE to all upper case,
     *  removing all blanks and tabs. */
    private static String standardize(String line) {
        return  line.trim().replace(" ", "").toUpperCase();
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private static void printMessageLine(String msg) {
        int i;
        if (msg.length() <= 5) {
            System.out.println(msg);
        } else {
            for (i = 0; i < msg.length(); i += 5) {
                if (i + 5 < msg.length()) {
                    System.out.print(msg.substring(i, i + 5) + " ");
                }
            }
            i -= 5;
            if (i <= msg.length()) {
                System.out.print(msg.substring(i, msg.length()) + "\n");
            }
        }
    }

    /** Maps specific ROTOR objects to their respective names. */
    private static HashMap<String, Rotor> _rotors =
        new HashMap<String, Rotor>();

    /** Shortcut for PermutationData.ROTOR_SPEC. */
    private static String[][] nick = PermutationData.ROTOR_SPECS;

    /** Number of rotors we are working with. */
    private static int numRotors = nick.length - 1;

    /** Create all the necessary rotors. */
    private static void buildRotors() {
        for (int i = 0; i < 8; i++) {
            _rotors.put(nick[i][0], new Rotor(nick[i][0], nick[i][1],
                                              nick[i][2], nick[i][3]));
        }
        _rotors.put(nick[8][0], new FixedRotor(nick[8][0], nick[8][1],
                                               nick[8][2]));
        _rotors.put(nick[9][0], new FixedRotor(nick[9][0], nick[9][1],
                                               nick[9][2]));
        _rotors.put(nick[10][0], new Reflector(nick[10][0], nick[10][1]));
        _rotors.put(nick[numRotors][0], new Reflector(nick[numRotors][0],
                                                      nick[numRotors][1]));
    }

}
