# CompSci 308: Parser Project Design Review

### Name: Tyler Jang

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci308/current/assign/03_parser/):

## Overall Design
### High Level Design

The project design is broken down into 9 major distinct parts:
 - The **Controller** starts the application and creates the Parser, Display(s), and TurtleManager(s).
    - The **Parser** is responsible for building Command hierarchies from inputted Strings. It handles different languages as well as bracket setup.
    - Each **Display** has its own Toolbar and TurtleViewManager.
        - Each **Toolbar** controls the different buttons that are pressed.
        - Each **TurtleViewManager** governs the creation and updating of each TurtleView for the runtime simulation.
    - Each **TurtleManager** is responsible for executing Commands to update a TurtleManifest and return a List of TurtleStatus instances.
        - Each **Command** has the capability to execute other Commands and return a double value.
        - The **TurtleManifest** stores information about the created turtles, including a most recent TurtleStatus instance for each ID.
        - Each **TurtleStatus** instance includes information about turtle locations, settings, and has the ability to alter other display settings.

Upon running a command, the commandButton of the Toolbar calls Controller's sendCommand() method, which pipelines the inputted String to the Parser.
The Parser builds the Command hierarchy as necessary, before allowing the Controller to have the TurtleManager execute the Commands, returning a List of TurtleStatus instances.
This List is pipelined to the frontend, where turtle movement is animated, and pen lines are drawn as necessary.

### Adding New Components

To add a new command, a Command implementation must be created in the proper location within the commands package.
Then, the resources files in the parser package must be updated to accommodate the command. These include CommandCounter.properties, and CommandFactory.properties. These files must include the number of arguments that the command takes as well as the path to the class implemented earlier.
If necessary, ConsumerCommand.properties, ControlCommand.properties, MovementCommand.properties, RunnableCommand.properties, or SupplierCommand.properties must also be modified, if the 
Command has any unusual behavior. Finally, the Command must be added to the relevant language files in the same package above in order for the Command to be recognized and parsed.

To add a new component to the frontend, such as a button, the following steps need to be taken.
 - a new private Button must be created in Toolbar.
 - the Button must be added to the scene nodes in the constructor of Toolbar.
 - the Button must be assigned a label in Toolbar.createButtons().
 - the Button must be assigned a behavior either in Toolbar.createButtons() or in Controller.setListeners().
 - the Button must be assigned a label in the buttons.properties file.

To add a new feature such as grouping, the following changes must be made:
 - The commands that allow grouping must have a new execute method that accepts a List of Commands, rather than a fixed number of commands.
 - Parser must have a new method in parseComponents() to parse these groupings ```()```.
 - For good codestyle practices, a new Class, such as GroupFactory, should be created and called from Parser.

Recognizing the large burden that this would have placed on the Parser, and since it was already generally overburdened, this
feature was not implemented.

### Dependencies

There are a couple notable dependencies that are not ideal, but were the best implementation that we could come up with.
Particularly, for implementing the display commands, we created calls in CommandFactory to methods on the frontend. These calls were governed by properties files, but
these methods are then wrapped in Consumers, Suppliers, or Runnables, and passed to the Commands' constructors. This allows the Commands to get information about
the Display. This is a major dependency that bridges the gap between front- and back-ends, but it was the best implementation we could come up with.

Most other dependencies are short and clear, following the hierarchy detailed above.

### API Analysis

Parts of the project's API are well-designed. These include (but are not limited to):
 - Command implementations across different classes.
 - TurtleManager and TurtleManifest as a central manager of these Commands.
 - Parser's use of resources files to map to these different Commands.
 - The frontend's use of resource files for generalized buttons.
 - The use of different Displays for different tabs.

The design is well encapsulated, with the exception of the internal implementation of the Parser.
The different calls to Parser, CommandFactory, FunctionFactory, and VariableFactory are sometimes muddled, and the lack of refactoring led to many of the bugs that we currently have.
This includes the inability to correctly parse bracketed items, leading to problems with implementation of functions, loops, etc.

The Parser is the class that needs the most improvement, each method depends on different methods, and it is difficult to construct a clear hierarchy.
I need to make sure that I do not fall into similar traps in the future.

## Your Design
### High Level Design

My design is broken down into 4 major distinct parts:
 - Each **TurtleManager** is responsible for executing Commands to update a TurtleManifest and return a List of TurtleStatus instances.
    - The **TurtleManifest** stores information about the created turtles, including a most recent TurtleStatus instance for each ID.
    - Each **TurtleStatus** instance includes information about turtle locations, settings, and has the ability to alter other display settings through the use of Runnables.
    - Each **Command** has the capability to execute other Commands and return a double value.
        - To promote good design and code readability, each Command is nested under a sub-interface of Command, including:
            - *BooleanCommand*
            - *ControlCommand*
            - *DisplayCommand*
            - *IdCommand*
            - *MathCommand*
            - *QueriesCommand*
            - *TurtleCommand*

The Controller will call a TurtleManager instance's executeCommands() method with a Command hierarchy that has already been parsed and created.
TurtleManager executes each of the commands in turn, updating its TurtleManifest instance and adding to a List of TurtleStatus instances. This TurtleStatus List can
then be used by the frontend to update the display.

### Design Checklist Issues

I have since refactored some of my code or removed extraneous imports, resolving most of the problems with my Command implementations based on the Design Checklist.

The static code analysis tool also throws some errors because of some empty methods, particularly for Commands that take in no arguments (such as Turtle.java).
This is also the case for most of the QueriesCommands.

Those are the only static problems found with the commands and external backend components that I implemented. I tried to fix some of the problems found in parser, such as long methods and lots of nesting loops,
but this was outside the range of my core work.

### Feature #1: User-Defined Functions

Through my work implementing the backend commands, I was faced with the struggle of how to implement user-defined variables and instructions.
I began with the former, and I created two classes extending ControlCommand.java called Variable.java and MakeVariable.java. These two classes
worked in tandem. When the former  executed (through the call of the interface's execute() method), it would do little except retrieve its current value.
When the latter executed, it would modify/set the value of the Variable instance. This worked well, particularly as it allowed the constructor of
MakeVariable.java to take in a Variable instance.

This also integrated well with the implementation of the actual Parser at the time.

When it came to implementing user-defined functions, however, I faced a larger obstacle. Calling a function, required more than just the arguments of
```execute()``` demanded. Calling a function required that its arguments (variable values) be specified. This couldn't just be accomplished with a ```setValue()``` method (or similar), because this would
modify the values of the variables for the entire function, disallowing the function from being called with different parameters (e.g. square(10) and square(20) would draw the same square).

After careful consideration, testing, and examination of the design patterns, I arrived at a solution that most closely resembled a 
[mediator](https://www.oodesign.com/mediator-pattern.html). I implemented MakeUserInstruction.java very similar to MakeVariable.java, where I had
its execution set Variables and Commands for a formerly empty Function.java instance.

Meanwhile, when it came time for the Function to be called later during runtime, rather than calling ```Function.execute()``` (as the variable values had not been set), I used another class,
RunFunction.java, to internally set the values of the variables when ```RunFunction.execute()``` was called. This would then call
```Function.execute()```, executing the Commands internal to the Function, with the required variable values.

This solution proved to be particularly elegant. It hid the implementation and setting of the arguments within RunFunction.java, and it used a design pattern very similar to a mediator.
Both MakeFunction.java and RunFunction.java encapsulates how the Variables and Function.java interact internally, allowing the Parser to simply call a specific set of ```execute()``` methods once all the Constructors have been called.

I was incredibly satisfied with the code, and upon completing it, I immediately recognized the value of the scalability and encapsulation inherent in its setup.

This implementation did, however, require that the Parser would be able to adequately store the different functions that the user has defined, as well as parse them into their parameters/arguments and commands. At the moment, this does not work as intended.
We had an earlier working version that worked with a limited number of parameters, but as it currently stands, the Parser cannot recognize these functions.

### Feature #2: TurtleManifest

In order to deal with the extension of multiple turtles, I modified the older pipeline of chaining TurtleStatus instances together across Commands. This would not suffice, however, as the different
Commands may rely on different turtle IDs, and I wouldn't want a Command's execution to be dependent on another TurtleStatus instance.

In order to deal with this problem, I created a TurtleManifest to be stored in TurtleManager. This TurtleManifest was an argument to every command, allowing the active turtle(s) to be accessed, along with their most recent TurtleStatus instances.
This implementation easily extends to allow for an undo last command option, if the updateTurtleState() method were modified to save the current state in a previous state. Alternatively, a separate TurtleManifest could be created that stores the output of the previous (not the current) command.

The problem with this implementation, however, is that every command effectively accesses the same TurtleManifest upon execution. This leads one to believe that a singleton or a global variable would be a simpler implementation. But, if one arrives at the conclusion that a singleton is a better implementation,
there is likely a different design problem underlying the existing one. For this reason, I believe that my TurtleManifest implementation is not the best implementation, but I have yet to arrive at a different solution that lacks this dependency problem.

## Flexibility
### What Makes It Flexible

The core frontend and backend of this project are flexible, as described above in the "High Level Design" section. The pipeline, however, as it flows between the Controller, Parser, Translator, etc. to get
between the frontend and backend, is not flexible. This can be seen in the remaining problems with attempting to run functions, etc.

### Easier Feature #1: Multiple Workspaces

The frontend and controller did a great job working together to make multiple Displays happen, along with the ability to toggle between workspaces and add new workspaces. What makes this feature particularly
possible is the pipeline to the frontend. Behaviors of classes are encapsulated, and allow for easy scalability.

In terms of my work to contribute to this scalability, having a central TurtleManager that executed a list of commands, allowed for simply the creation of multiple TurtleManagers, each with its own TurtleManifest and other information.
This allowed functionality to be encapsulated and repeated across multiple workspaces.

### Easier Feature #2: Parser and Display Commands

The implementation that we currently have is not ideal, as it relies on a lot of dependencies, but it did allow for flexibility within the context of complete. Because Parser was the class responsible for all centralized parsing of Strings into Commands,
it could handle how the Commands' constructors were called. In order for Commands like SetShape and PenColor to be implemented, Parser only needed to pass them the relevant Consumer, Supplier, or Runnable from the frontend. This allowed for easy implementation of these Commands,
even though some large dependencies still remain.

### Unimplemented Feature #1: Grouping

To add grouping, the following changes would have to be made:
 - The commands that allow grouping must have a new execute method that accepts a List of Commands, rather than a fixed number of commands.
 - Parser must have a new method in parseComponents() to parse these groupings ```()```.
 - For good codestyle practices, a new Class, such as GroupFactory, should be created and called from Parser.

Recognizing the large burden that this would have placed on the Parser, and since it was already generally overburdened, this
feature was not implemented.

Parser was not implemented very flexibly, and while some of its behavior is encapsulated in CommandFactory, FunctionFactory, and VariableFactory, it is difficult to navigate and presents a challenge when 
it comes to this additional feature. To be fair, this would also require some significant work on the part of the Commands, in allowing more constructors.

Thus, our design was not very extensible when it came to this feature, and we probably should have considered such a feature in our original design to promote better flexibility.

### Unimplemented Feature #2: Loading from Files

This feature was attempted, and it was mainly managed with the frontend and with the controller, but we were not able to finalize it, largely due to problems with Parser. In order for the previously run commands to be saved to a text file, along with
other commands to be loaded from a text file, Parser would have needed to handle different types of whitespace accordingly. This is a challenge that it was not ready to face, and when we tried to debug we ended up breaking much of the functionality of lists
for functions and loops.

This is an important feature that we were unfortunately unable to finish for this sprint. We can load very basic text files, but nothing with any sort of brackets or unusual indentation.

## Alternate Designs
### Multiple Turtles Design

In order to implement multiple turtle features, the following classes were effected:
 - All Commands' execute() methods were modified to take in a TurtleManifest instance instead of a TurtleStatus instance.
 - TurtleManager was modified to change how it executes commands.
 - TurtleManifest was created to keep track of the different IDs and their relevant TurtleStatus instances.
 - TurtleViewManager was created on the frontend to keep track of multiple TurtleView instances.
 - TurtleView was created on the frontend for each individual turtle.

This is relatively simplified, and no other major areas required modification for this implementation.

### Project Extension Design

The original design for the most part scaled pretty well. We would have in theory been able to implement all the changes in complete if it hadn't been for the shortcomings for Parser.
We discussed all the different implementations and broke into subteams to discuss each implementation before bringing our conclusions back to the larger group.

Unfortunately, much of the burden fell on Parser, and we were unable to make the final implementation work as intended.

### Design Decision #1: Edges

One of our early considerations in creating TurtleCommand implementations was that we wanted to allow for different types of edges. This included 
toroidal, unlimited, and fixed edge implementations. To do this, we implemented multiple move() and other methods in TurtleCommand. These could then be called accordingly based on the edge mode specified by the frontend.
We were quite happy with this implementation, as it allowed for flexibility. The only downside to this design, as opposed to a fixed value in an XML or properties file, is that it relied on a dependency with the frontend.

Nevertheless, it promoted scalability and allowed the user to customize more features.

### Design Decision #2: User-Defined Functions

As discussed previously, user-defined functions presented a significant obstacle. An alternative design was the inclusion of two classes, one for defining functions and one for running them. This would have caused the functions to have the same arguments across all function calls, however, as these arguments
would be set during parsing instead of during execution. This presented an implementation shortcoming, along with limited flexibility. Thus, we opted for the current implementation, with the mediator design pattern. 

## Conclusions
### Best Feature of Design: User-Defined Functions

Needless to say, I am quite proud of my implementation of user-defined functions. I put considerable thought into my implementation, and it produced a solution that required relatively little actual code.
It was one of the best components that I have ever designed, in my opinion. The process of designing and implementing this functionality impressed upon me the value of spending time planning versus spending time implementing, and I will no doubt learn from it in the future.

### Worst Feature of Design: Parser

Needless to say, I think that the Parser implementation could be improved. This is not Dennis's fault, however, as he shouldered more than his fair share of work. This is ultimately the fault of the team at large for burdening him with such a large task, with what in fact proved to be
the most challenging and largest task of the entire project. Speaking back to the code itself, however, Parser is one of the largest classes, and it and its dependencies rely on some of the longest methods. This could easily be improved, and it currently relies on chains of method calls that are difficult to follow.

In the future, more teamwork is needed to break down these important and monumental structures into more manageable chunks that can be better divided up between group members.

### Moving Forward

Moving forward, I need to do a better job of shouldering the weight of other group members, continuing to communicate, but offering more explicitly to help with different features.

Moving forward, I will keep commenting as I code, which I believe made my code more readable and encouraged me to think more critically about my design.

Moving forward, I need to stop using vague API documentation at the beginning. I feel I am better off taking a stab and suggesting an implementation (even if I have to switch away from it) than if I were to be vague and noncommittal. 