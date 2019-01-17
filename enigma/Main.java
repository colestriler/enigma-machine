package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static enigma.EnigmaException.*;

/**
 * Enigma simulator.
 *
 * @author ColeStriler
 */
public final class Main {

    /**
     * Alphabet used in this machine.
     */
    private Alphabet _alphabet;

    /**
     * Source of input messages.
     */
    private Scanner _input;

    /**
     * Source of machine configuration.
     */
    private Scanner _config;

    /**
     * File for encoded/decoded messages.
     */
    private PrintStream _output;


    /**
     * An ArrayList containing all rotors that can be used.
     */
    private ArrayList<Rotor> _allRotorsMain = new ArrayList<>();

    /**
     * A String containing cycles which readRotor() appends to.
     */
    private String permMain;

    /**
     * The current rotor's _name.
     */
    private String _name;

    /**
     * A string that is temporarily set to NEXT token of _config.
     */
    private String tempStr;

    /**
     * Type and notches of current rotor.
     */
    private String notches;

    /**Number of pawls.*/
    private int pawls;

    /**
     * Process a sequence of encryptions and decryptions, as
     * specified by ARGS, where 1 <= ARGS.length <= 3.
     * ARGS[0] is the _name of a configuration file.
     * ARGS[1] is optional; when present, it names an input file
     * containing messages.  Otherwise, input comes from the standard
     * input.  ARGS[2] is optional; when present, it names an output
     * file for processed messages.  Otherwise, output goes to the
     * standard output. Exits normally if there are no errors in the input;
     * otherwise with code 1.
     */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /**
     * Check ARGS and open the necessary files (see comment on main).
     */
    Main(String[] args) {

        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 "
                    +
                    "command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /**
     * Return a Scanner reading from the file named NAME.
     */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /**
     * Return a PrintStream writing to the file named NAME.
     */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /**
     * Configure an Enigma machine from the contents of configuration
     * file _config and apply it to the messages in _input, sending the
     * results to _output.
     */
    public void process() {

        Machine enigmaMachine = readConfig();
        String step = _input.nextLine();

        while (_input.hasNext()) {
            String setting = step;
            if (!setting.contains("*")) {
                throw new EnigmaException("incorrect setting format");
            }
            if (setting.contains("*") && setting.length() < 2) {
                throw new EnigmaException("No settings");
            }
            setUp(enigmaMachine, setting);

            step = (_input.nextLine());
            step = step.toUpperCase();

            while (step.isEmpty()) {
                step = (_input.nextLine());
                _output.println();
                step = step.toUpperCase();
            }

            while (!(step.contains("*"))) {

                step = step.replaceAll(" ", "");

                String result = enigmaMachine.convert(step);


                if (step.isEmpty()) {
                    _output.println();
                } else {
                    printMessageLine(result);
                }
                if (!_input.hasNext()) {
                    step = "*";
                } else {
                    step = (_input.nextLine());
                    step = step.toUpperCase();
                }
            }
        }
        while (_input.hasNextLine() && _input.nextLine().isEmpty()) {
            _output.println();
        }

    }
    /**
     * Return an Enigma machine configured from the
     * contents of configuration file _config.
     */
    public Machine readConfig() {
        try {

            String alphabet = _config.next();
            if (alphabet.contains("(")
                    || alphabet.contains(")")
                    || alphabet.contains("*")) {
                throw new EnigmaException("incorrect configuration format");
            }

            if (alphabet.contains("-")) {
                _alphabet = new CharacterRange(alphabet.charAt(0),
                        alphabet.charAt(2));
            } else {
                _alphabet = new CharacterRangeSecond(alphabet);
            }
            if (!_config.hasNextInt()) {
                throw new EnigmaException("incorrect "
                        +
                        "configuration format");
            }
            int numRotors = _config.nextInt();

            if (!_config.hasNextInt()) {
                throw new EnigmaException("incorrect "
                        +
                        "configuration format");
            }
            pawls = _config.nextInt();

            tempStr = (_config.next());
            tempStr = tempStr.toUpperCase();

            while (_config.hasNext()) {
                _name = tempStr;
                notches = (_config.next());
                notches = notches.toUpperCase();
                _allRotorsMain.add(readRotor());
            }
            return new Machine(_alphabet, numRotors,
                    pawls, _allRotorsMain);
        } catch (NoSuchElementException excp) {
            throw error("configuration file "
                    +
                    "truncated");
        }
    }

    /**
     * Return a rotor, reading its description from _config.
     */
    private Rotor readRotor() {
        try {
            permMain = "";
            tempStr = (_config.next()).toUpperCase();
            while (tempStr.contains("(") && _config.hasNext()) {
                if ((tempStr.contains("(")) && (!tempStr.contains(")"))) {
                    throw error("Missing a parenthesis");
                }
                permMain = permMain.concat(tempStr + " ");
                tempStr = (_config.next()).toUpperCase();
            }
            if (!_config.hasNext()) {
                permMain = permMain.concat(tempStr + " ");
            }
            if (notches.charAt(0) == 'M') {
                return new MovingRotor(_name,
                        new Permutation(permMain, _alphabet),
                        notches.substring(1));
            } else if (notches.charAt(0) == 'N') {
                return new FixedRotor(_name,
                        new Permutation(permMain, _alphabet));
            } else {
                return new Reflector(_name,
                        new Permutation(permMain, _alphabet));
            }
        } catch (NoSuchElementException excp) {
            throw error("inaccurate rotor description");
        }
    }

    /**
     * Set M according to the specification given on SETTINGS,
     * which must have the format specified in the assignment.
     */
    private void setUp(Machine M, String settings) {

        int machNumRotors = M.numRotors();
        String[] settingsMain = settings.split(" ");

        if (settingsMain.length - 1 < machNumRotors) {
            throw new EnigmaException("Need more "
                    +
                    "arguments in settings");
        }
        String[] rotors = new String[machNumRotors];
        for (int i = 1; i < machNumRotors + 1; i++) {
            rotors[i - 1] = settingsMain[i];
        }

        for (int i = 0; i < rotors.length - 1; i++) {
            for (int j = i + 1; j < rotors.length; j++) {
                if (rotors[i].equals(rotors[j])) {
                    throw new EnigmaException(
                            "Repeated Rotor");
                }
            }
        }

        String cycles = "";
        for (int i = machNumRotors + 2;
             i < settingsMain.length; i++) {
            cycles = cycles.concat(settingsMain[i] + " ");
        }
        M.insertRotors(rotors);
        if (!M.getRotors()[0].reflecting()) {
            throw new EnigmaException("First Rotor "
                    +
                    "should be a reflector");
        }

        for (int i = 0; i < M.getRotors().length - 1; i++) {
            if (!_allRotorsMain.contains(M.getRotors()[i])) {
                throw new EnigmaException("Rotor not in available rotors");
            }
        }

        if (M.getNumPawls() != pawls) {
            throw new EnigmaException("Incorrect "
                    +
                    "number of pawls");
        }
        M.setRotors(settingsMain[machNumRotors + 1]);
        M.setPlugboard(new Permutation(cycles, _alphabet));
    }

    /**
     * Print MSG in groups of five (except that the
     * last group may have fewer letters).
     */
    private void printMessageLine(String msg) {
        int len = msg.length();
        for (int i = 0; i < len; i += 5) {
            int limit = len - i;
            if (limit < 6) {
                _output.println(msg.substring(i, i + limit));
            } else {
                _output.print(msg.substring(i, i + 5) + " ");
            }
        }
    }


}
