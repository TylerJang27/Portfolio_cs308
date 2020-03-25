package slogo.commands.controlcommands;

import slogo.backendexternal.TurtleManifest;
import slogo.backendexternal.TurtleStatus;
import slogo.commands.Command;
import slogo.commands.ControlCommand;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that implements ControlCommand, used to define a function/user instruction, assigning it commands and variables.
 * MakeUserInstruction is used for the TO command. The Function myFunction should be created with the default constructor before calling
 * MakeUserInstruction's constructor. Ensure that this same Function is passed to future RunFunction instances so that a function
 * may have continuity over a program.
 *
 * @author Tyler Jang
 */
public class MakeUserInstruction implements ControlCommand {

    public static final int NUM_ARGS = 3;

    private List<Variable> myVariables;
    private List<Command> myCommands;
    private Function myFunction;

    /**
     * Constructor for MakeUserInstruction, used to apply the variables and commands to the Function instance.
     *
     * @param func      the function to be defined.
     * @param variables the variables to be identified for the function.
     * @param commands  the commands for the function to execute, which may include instances of those variables.
     */
    public MakeUserInstruction(Function func, List<Variable> variables, List<Command> commands) {
        myFunction = func;
        myVariables = variables;
        myCommands = commands;
    }

    /**
     * Executes the MakeUserInstruction, used to apply the variables and commands to the Function.
     *
     * @param manifest a TurtleManifest containing information about all the turtles
     * @return   a List of TurtleStatus instances, containing only the parameter ts.
     */
    @Override
    public List<TurtleStatus> execute(TurtleManifest manifest) {
        myFunction.setVariables(myVariables);
        myFunction.setCommands(myCommands);
        return new LinkedList<>();
    }

    /**
     * Retrieves the value returned by MakeUserInstruction, the status of declaration.
     *
     * @return 1 if the function definition process is successful.
     */
    @Override
    public double returnValue() {
        return 1;
    }
}
