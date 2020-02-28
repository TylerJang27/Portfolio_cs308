# Weekly Reflection
### Tyler Jang
### 02/27/20

#### Interesting Notes

##### A Function Mediator
This week in my work on the backend of the Parser, I was faced with the struggle of how to implement user-defined variables and instructions.
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

##### Fullstack Integration
Prior to taking this class, I somewhat jokingly prided myself on the ability to write an entire codebase in one go (obviously the projects were of a much smaller scale), with minimal debugging.
I of course consciously recognized the value of iterative development, but I enjoyed cranking out a class or complicated function in one sitting.

With all of the previous projects, including simulation, I worked iteratively, regularly connecting and testing my changes with the frontend. This worked well to ensure that if changes needed to be made, they could be made immediately rather than
sitting and festering as bugs, hidden in the code.

With this project, however, we decided early on that we would divide into subteams, focusing on the backend and frontend, with Dennis working to bridge them with the Controller. There were two problems with our implementation of this strategy, however:
 - Dennis was also working on the Parser, and this was completed before any of the controller components were tackled. 
 - We failed to set any firm deadlines to meet up and merge the two ends.
 
This led to the problem where we did not bridge the two until about a day before basic was due, and we had many merge conflicts to deal with, and we also realized that we had each made assumptions slightly inconsistent with our API and design. 
Having learned from this last-minute panic, we will work to make firmer deadlines for the next sprint, and work more iteratively to test, especially since we already have the pipeline set up.

In addition, for the last project, I would like to utilize a slack channel, where we have chats for the backend, the frontend, existing bugs, and pipelining between them.

#### Summaries

##### Java Reflections
Reflections are particularly useful for Parser, given the runtime creation of classes from user-inputted Strings. They have the ability to turn massive if-trees into short, one-two line reflection commands (often with the help of a properties file). 

At the moment, our implementation does not use reflection, but rather uses Maps from Strings to Constructors. Hopefully, Dennis and I will be able to use reflection to improve the parsing process over the next sprint.

During the lab this week, using "Advanced," we were able to put into practice what we had been learning during lecture about reflection. Particularly helpful was using the NanoBrowserView, reading from a properties file and calling the ```makeButton()``` method.
This allows for scalability, particularly because one need only define behavior through methods and add entries to the properties file to add additional buttons. 

##### Lambdas
During the reading this week, lambdas were explored in both a functional and historical capacity. In "Why Lambda Expressions," the author catalogued how Java 8 was the first edition to include functional programming constructs, in which Java was finally allowed to pass around blocks of code, separate from their related objects/classes.
This allowed lambdas to operate later, modifying how one thinks about scope and function calls.

Lambda expressions are particularly useful for connecting classes to distinct behaviors, such as with buttons and function calls, or even modifying sorting algorithms.

One other useful analogous structure mentioned in the readings was method references, indicated by two colons. One example of this was ```Arrays.sort(strings, String::compareToIgnoreCase);```.
This allows use of:
 - Class::staticMethod
 - Class::instanceMethod
 - object:instanceMethod

#### Reflection

##### What was difficult about your work this week and why?
One particular obstacle I encountered this week was when I was attempting to merge my functionality with that of the frontend. Specifically, I was working with Shruthi to 
animate the turtle based on a List of TurtleStatus instances. I kept running into a problem where I wanted the turtle to wraparound the screen, but it kept animating the path instead of jumping. 

The final solution arrived with some refactoring and reordering, but the critical factor for solving it was familiarizing myself with the documentation of Animation, SequentialTransition, and Polyline. Working off of 
intuition can be useful, especially in OOP languages that use meaningful method names, but one rarely arrives at a clean solution just through trial and error. 

I was reminded of the Game project and of how I needed to read the documentation more when working with packages I was unfamiliar with. 

##### What were the most important things you learned?
This week was another exercise in the importance of communication. While communication within the backend and with the controller was great for this sprint, we rarely communicated with the frontend and bridged the gap, leading to many flaws
and difficulties when it came time to combine the two. 

##### How will this learning change your work next week?
We will set firmer deadlines and keep regular communication and testing, further reinforced through intentional Git practices, over this next sprint in order to improve our code cohesion.

