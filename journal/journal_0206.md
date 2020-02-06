# Weekly Reflection
### Tyler Jang
### 02/05/20

#### Interesting Notes

##### Merge, Pull, Repeat
Simulation was my first time using Git effectively in teams. We decided early as a group that we would have a stable Master branch, a regularly updated Develop branch, and a separate branch
for each of our parts of the project (with Thomas on Model, Mariusz on View, and myself on Controller). We agreed that every merge request would be submitted via the GitLab GUI,
requiring approval from at least one other team member. This was effective in two main ways:
 * Someone other than the lead programmer of a specific feature always knew what new changes were implemented. This allowed modifications to be made for different components to ensure seamless integration,
 and it allowed for clear and measurable divisions in the timeline of the project.
 * In one instance, Thomas made the easy mistake of submitting a merge request to master rather than to develop. This would have merged 30+ changes (where a change is defined as a file being changed), and would have significantly set us
 back while dealing with the merge conflicts. But because the merge requests required approval, we were able to stop the merge request in its tracks.
 
 In addition to abundant merge requests, we also committed early and often, with 105 total commits by the time Basic was due. Our commit messages were generally informative and allowed us to track
 what progress had been made and what still needed to be completed.  

##### Exceptions In Action
This project was also my first time dealing with exceptions in action. I had written one or two try/catch handlers for other projects, but nothing was substantial. I began by implementing an XMLException.java
class very similar to the one provided in spike_simulation. From there, I offered custom messages with the English.properties file, and I threw the relevant XMLException in XMLParser when the input file did not match
the desired input. I will need to increase my exception throwing and handling in the next sprint. At the moment, the error message logged to the console is simply "XML file does not correctly represent simulation."
This is not detailed, and at the moment this does not take into account different possible error cases, such as if the XML file contains text in place of numbers. It mainly just applies to a missing or extraneous field label.

My implementation of exceptions over the next sprint will be informed both by the reading and my experiences from this week. 

#### Summaries

##### Exception Conventions
In addition to working on exceptions for this project, I was also able to read more about their conventions. Specifically, I learned about different implementations following the core philosophy of exceptions:

**[If your method encounters an abnormal condition that it can't handle, it should throw an exception.](https://www.javaworld.com/article/2076721/designing-with-exceptions.html)**

Some Java classes throw EOFExceptions when they read the end of a file, whereas others simply return -1. It all relates to the abnormality of the occurrence compared to the class's usual function.
The article also focused on the difference between exceptions and errors, as errors are only for drastic problems, such as the JVM running out of memory.

Exceptions themselves are divided into checked and unchecked.
 * Check exceptions indicate when a method has done its best but something has ultimately failed. If not caught, they must be declared with a method's throw clause.
 * Unchecked exceptions indicate that a class has been used improperly. They are subclasses or instances of RuntimeExceptions.

##### XML Parsing
This sprint was my first experience parsing XML files. All of my previous experience had been with parsing basic text files. In implementing the Configuration/Controller part of our project,
I initially created a grid field in the XML file with a String representing points, with individual points delimited by commas and the points of the format "x y val". Seeing this in action, I realized how impractical it was
for both normal operation and testing.

Because of this, I opted for a new strategy. I allowed for three methods of grid specification:
 * All XML files ending in a 1 (for testing purposes) used grid option "all," in which every grid point/value had to be specified.
 * All XML files ending in a 2 used grid option "some," in which a finite number of grid points/values were specified using the "x y val" format.
 * All XML files ending in a 3 used grid option "random," in which all cells were filled with a uniform distribution of the different states for that simulation type.
 
This implementation proved to be very useful for testing and proved to yield relatively clean code.

In the next sprint, I plan to add one more option, in which other parameters are specified for a specific random generation. I also need to improve input validation and exception handling. 

#### Reflection

##### What was difficult about your work this week and why?
Work this week was particularly difficult with regard to event handling. Because of my role in the project, I used events to a minimal extent, except pipelining them to the View (Mariusz). I still
feel quite unfamiliar/uncomfortable with them, and I feel like a more thorough informational review is needed so that I can understand the ins and outs of its conventions. This was one of the core elements of
the Application setup with which I lacked familiarity during week 1. I hope that this will improve in the coming weeks.

##### What were the most important things you learned?
The most important thing I learned this week was how to branch and merge with Git. This is a very useful skill that will be invaluable during my future internships and professional roles.
This was my first time putting it into practice, and with intentional management, we were able to minimize merge conflicts and nip them in the bud.

##### How will this learning change your work next week?
Next week, I will work more to handle different exceptions appropriately from the start, especially trying to use cascading try/except handlers as was demonstrated in class for
input validation. If not, I will make sure to pipeline more detailed messages to our project's console. This allows for more resilient and secure programming, as the user should never
receive a stack trace. 
