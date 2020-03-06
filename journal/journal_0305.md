# Weekly Reflection
### Tyler Jang
### 03/05/20

#### Interesting Notes

##### Lambdas, Lambdas Everywhere
I have a confession to make: I've always been a fan of the [art of the bodge](https://www.youtube.com/watch?v=lIFE7h3m40U). There is something insanely satisfying about finishing a large
batch of code, pressing "Run," and having it just work. No matter how you got there. Now, I realize that this is not always appropriate, nor is it usually appropriate. This approach to programming
is only really useful in one-off, short programming experiments where the goal is to get code working and never look at it again.

So, I have tried to curtail this instinct to the best of my ability while working on projects in this class. But, then a problem arose. At the beginning of Complete, I began by adding additional functionality to
TurtleModel that would accommodate the existence of multiple turtles. I then created several specific getters and setters so that I could retrieve information about individual turtles. I realized as I was writing it that
it would probably be better to refactor this functionality into a different class, but I postponed it in the interest of getting functionality with turtle-id-related commands. 

Around this time, Lucy and I were also discussing how to best approach the different modifications to be made to the front-end. These included changing the background, pen color, turtle shape, etc. We agreed that the best way to accomplish this and still maintain our existing pipeline
was to add a functional interface to TurtleStatus so that it could directly modify the front-end when it came time to animate (this eliminated the need for a multitude of parameters in TurtleStatus's constructor).

Seeing that progress was going well, I then began to work on implementing the different turtle-id-related commands, such as ```Tell```. While attempting the implementation, I ran into several bugs. But alas, there was a solution within my grasp! I had lambdas functions at my disposal!
I quickly began giving myself access to lambda commands, including several Suppliers and Consumers that would retrieve or set the relevant TurtleStatus instances for the relevant Turtle IDs.

As I continued, however, this problem began to grow out of hand. It got to the point that AskWith had 9 parameters, 8 of which were functional interfaces. Absolute power corrupts absolutely....

And so, as expected, I began refactoring. I broke up TurtleModel into TurtleManager and TurtleManifest classes, which would handle execution and storing TurtleStatus instances, respectively, with all commands taking in TurtleManifest instances rather than TurtleStatus instances.

And thus, the code became exponentially cleaner, and I had learned my lesson in the danger of excessive lambdas.

##### Parser, Oh Parser
In the basic implementation, we were unable to implement reflection for the parser. Because Dennis was juggling both the role of Parser and Controller, we left the implementation as a large if tree. Nevertheless, Parser has seen significant improvements since then. 
Dennis was able to refactor out CommandFactory, FunctionFactory, and VariableFactory, to remove some of the weight and responsibilities of parser. He also created the Translator class to handle different languages of inputs, even allowing the already run commands to be translated
upon changing the language.

In the last couple of days, Lucy was able to improve on this refactoring, adding some reflection into CommandFactory, specifically with the pertinent lines:
```java
Class<?> c = Class.forName(myCommands.get(key));
Class<?> params[] = findParameter(objArray);
return (Command)c.getDeclaredConstructor(params).newInstance(objArray);
``` 

Tonight, we will take this a few steps further, and we will attempt to refactor FunctionFactory and VariableFactory to use reflection and to read from properties files, so that we will no longer need to have
specific if statements dedicated for Commands that have other constructors. 

#### Summaries

##### STUPID TO SOLID
STUPID to SOLID represents the summary of all the design principles we've learned so far in this course. It cautions against replicated code, global variables, and large dependencies. 
It represents a transition from poor and under-engineered code into well-designed and intentional code.

Some points of note include:
 - Proper variable names.
 - Testability of code (and iterative testing).
 - Substitution: any object should be able to be swapped with instances of its sub-classes.
 - Classes should only have one purpose.
 - Minimal dependencies.

Following these core principles and rules of thumb make code more reusable and flexaible.

Indeed, the safest implementation seems to be to plan with these principles and design patterns in mind, and regularly return to attempt to refactor the code in question.

##### Multiple IDs
When designing our implementation of multiple turtle IDs, we weighed several different ideas, with the following key criteria:
 - We did not want the signatures of the front-end methods responsible for animating turtles to change significantly.
 - We did not want constructors of different commands to change significantly.
 - We wanted the sequence of commands run to remain the same (e.g. turtle 1 before turtle 2 and then turtle 1 again).
 - We wanted our implementation to scale.
 
With this in mind, we arrived at a couple of options:
 - Have tell create new TurtleStatus instances in the tree recursion.
    - Pro: simple, quick implementation
    - Con: didn't allow easily for multiple active turtles, failed with subsequent calls (rather than nested calls)
 - Have a list of active turtles and access to their recent statuses in TurtleModel.
    - Pro: relatively simple implementation, reliable 3rd-party storage
    - Con: difficult to update statuses for rapid ID changes
 - Have a class for storing TurtleStatus information and IDs, and pass this to each execute call.
    - Pro: scalable, allowed for delegation of responsibility
    - Con: larger initial overhead

We eventually opted for the final option, as this allowed for scalability, and we divided the responsibilities of TurtleModel into TurtleManager and TurtleManifest.

#### Reflection

##### What was difficult about your work this week and why?
This week has presented a distinct challenge from other weeks. While most weeks in this class have been incredibly busy and quite stressful, this week has been beset with burnout.
With a very challenging midterm in ECE230, along with tenting wrapping up, and of course with the evolving current events, it has been incredibly difficult this week to find the will to work.
While I have been periodically thinking about the design of Parser, I've struggled to find time at a computer when I actually feel confident in my ability to code effectively.

This is particularly troubling as last week was full of powerful, intentional code that I was particularly proud of. I think I've been working very hard for too long, and these are symptoms of burnout.

I hope that with this spring break I can remain healthy and find the will to rejuvenate myself so that I can refocus on my studies.

##### What were the most important things you learned?
This week, as catalogued above, I learned the dangers of excessive lambdas. While their potential is incredible for different implementations, they often don't lend themselves to good design and should be
used very intentionally, especially when working across more than just the front-end or back-end.

##### How will this learning change your work next week?
Next week, I will take time to reflect and my entire progress from this semester. For the next project, I feel very comfortable working on the backend, but I think that I should work to improve my abilities and practice more
with the Controller, an essential and often underrated portion of the program.
