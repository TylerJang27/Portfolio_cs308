package slogo.commands.controlcommands;

import slogo.backendexternal.TurtleManifest;
import slogo.backendexternal.TurtleStatus;
import slogo.commands.Command;
import slogo.commands.ControlCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class that implements ControlCommand, used to store a function's commands and variables for later execution.
 * This organization comprises a Mediator design pattern.
 *
 * @author Tyler Jang
 */
public class Function implements ControlCommand {

    public static final int NUM_ARGS = 0;

    private double myVal;
    private List<Variable> myVariables;
    private Collection<Command> myCommands;

    /**
     * Constructor for Function, creating its default variables and commands.
     */
    public Function() {
        myVariables = new ArrayList<>();
        myCommands = new ArrayList<>();
    }

    /**
     * Sets the variables, to be called only by other Commands (typically MakeUserInstruction).
     *
     * @param variables a List of variables for the Function to use.
     */
    protected void setVariables(List<Variable> variables) {
        myVariables = variables;
    }

    /**
     * Sets the commands, to be called only by other Commands (typically MakeUserInstruction).
     *
     * @param commands a Collection of commands for the Function to execute.
     */
    protected void setCommands(List<Command> commands) {
        myCommands = commands;
    }

    /**
     * Sets the value of a variable, to be called only by other Commands (typically RunFunction).
     *
     * @param index the index of the variable to modify.
     * @param val   the new value of the variable being modified.
     */
    protected void setVariableValue(int index, double val) {
        myVariables.get(index).setVal(val);
    }

    /**
     * Retrieves the number of variables required for the function to be called properly.
     *
     * @return the size of myVariables.
     */
    public int getNumVars() {
        return myVariables.size();
    }

    /**
     * Executes the Function, including its Collection of Command instances. This requires that another Command (typically RunFunction) set its variable values.
     *
     * @param manifest a TurtleManifest containing information about all the turtles
     * @return   a List of TurtleStatus instances produced as a result of running the Function.
     */
    @Override
    public List<TurtleStatus> execute(TurtleManifest manifest) {
        List<TurtleStatus> ret = new ArrayList<>();
        for (Command c: myCommands) {
            myVal = Command.executeAndExtractValue(c, manifest, ret);
        }
        return ret;
    }

    /**
     * Returns the value of Function, referring to its last executed command's value.
     *
     * @return the value of the Function.
     */
    @Override
    public double returnValue() {
        return myVal;
    }

}
