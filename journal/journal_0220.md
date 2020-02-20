# Weekly Reflection
### Tyler Jang
### 02/20/20

#### Interesting Notes

##### Trees and Recursive Commands
Originally, upon scanning the scanning through the project and hypothesizing implementation options, 
we planned to implement two command types. These would be two interfaces, Command and NestedCommand, the latter
a subset of the former. Nested Command would apply to situations like conditionals, loops, and function definitions,
cases where there would be many commands included within the brackets/scope of the singular command.

Upon further examination, as well as Professor Duvall's comments during class, we realized that every command in
Slogo returns a value, whether that be the distance moved, the magnitude turned, or some other value. This enables
virtually every command to be a nested command, therefore eliminating the necessity of a separate NestedCommand interface.
Rather, we will need to change our structure to have every parameter of a Command be its own Command that returns a value. 

This will lead to a tree-like structure, with each Command instance pointing to sub-Commands that would return a value.
This tree construction will mirror that used in CS201 and may draw on some similar data structures and algorithms.

Obviously, this all pertains to internal backend implementation and will all be invisible in terms of the external interface.   

##### Reflections from TA Meeting
On Tuesday, I met with my assigned grader TA for the simulation project. We talked through my code and some possible additions
I could have made. Working as the Configuration and Controller, there was not a large amount of inheritance in my project, although
I implemented it when I could. One of the largest questions I had going into the meeting was regarding my creation of enumerated types
SimType.java and SimStyle.java, both of which had many similar features and values for each simulation type. The TA recognized my concerns,
and he suggested that I create a third centralized enumerated type, which simply called all of the values from both SimType.java and SimStyle.java, centralizing the entire
calling process so that all other parts of the Controller could simply call it.

One other component we discussed was the use of the properties file. I included every possible String from our code (at least from the Controller and Model and most from the
View) in a properties file, and had many ```RESOURCES.getString("Width")``` or similar calls in my code. This became a little repetitive and rather annoying, but
the TA recognized that this led for scalability and flexibility of the XML Parsing. One place where it broke down was with regard to multiple calls of the
same String within a singular class. For this, he recommended that I extract a constant to represent it, thereby shortening my code
and minimizing repetition so that my code could scale.

#### Summaries

##### Introductions to Design Patterns
In the "Introductions to Design Patterns" reading, I read about Christopher Alexander's definition of a Pattern as "a solution to a problem in a context."
Patterns are particularly useful for avoiding repetition and standardizing design. In one example given by the author, two carpenters
were discussing whether to use dovetail joints or mitre joints. He contrasted that with a granular description of the two joint types, and noted that
the experts of the field typically used the shorter, more concise explanations, speaking more qualitatively.

This resonated with a particular experience I've had in this class. When I went over  my code with the TAs for my masterpiece code demonstrations, 
I was most proud of the cases where I spoke in generalities, using abstraction and speaking about the flow and pipelining of my different classes, 
rather than getting into the narrow implementation of searching algorithms, updating, etc. I was less proud when I got into these granular details, and I felt myself occassionally "wading" through my code.

This speaks to the power of simple design patterns and public interfaces. Simplicity always wins.

##### Design Decision: Screen Size
In implementing our Parser, we are striving for as much separation of the Model and View as possible. We want to ensure that this separation allows the back end to have minimally exposed interfaces and to primarily function on its own,
parsing and executing Commands as it would.

One item that was the subject of some discussion, however, was the issue of screen size. Should the backend know how big the display was on the front end?
Or should the front end deal with wrapping issues and other cases?

We discussed this at some length, originally planning to leave the screen size to the front end, especially if the user designed to change it.

As we began to dig into the edge cases, however, we realized a problem. See the following case:
 - Screen size 100x100
 - Corners at (-50, 50), (50, 50), (-50, -50), and (50, -50) 
 - The following commands:
    - fd 50
    - fd 20
    - toward 0 0

The toward command represents a possible problem. If wrap around was not implemented, or if the backend did not know the screen size,
the turtle would have to point back in the direction of travel. But if wrap around were implemented and the above screen size was set,
the turtle would need to point up toward the center, only 30 pixels away. 

This case, along with several others that were brainstormed, convinced us that the backend needed to know the screen size.
Thus, we designated it the Controller's job to communicate this parameter, and we planned to update the API_CHANGES.md doc accordingly.

#### Reflection

##### What was difficult about your work this week and why?
This week involved a host of several technologies/tools with which I was unfamiliar. I originally had very minimal experience with
regular expressions, leading parsing to be its own unique challenge. Indeed, we spent quite a bit of time discussing how to implement a stack or tree that would best accomplish our goals
of parsing commands. I also was unfamiliar with binding and animation. Unlike previous weeks where I had a fair amount of familiar with
interfaces or encapsulation, this was the first time there was such a steep learning curve for such a large-scale project.

##### What were the most important things you learned?
This week involved a fair amount of getting to know my new teammates. I had a very positive experience with my previous team, and after some initial tiptoeing, we got to know each other very well
and came to expect fair work from each other. But with new groups come new people, and with new people come new personalities. I've spent a lot of effort figuring out how to best promote communication and 
organization within this group. I came in with a strong and confident vision for our final output, but I was wary of sharing for fear I might impose my ideas on others. I have tried my best
to promote open communication and ensure a friendly environment for cooperation. I believe I have succeeded, but I also want to continue to promote healthy discourse.

##### How will this learning change your work next week?
Next week, I will continue to work more intensely with my team. We will work to set more fixed deadlines for partial implementation, avoiding waiting until the last minute for full implementation.
We plan to meet this Friday for our first subteam meeting of the backend, but we have kept in close contact over text. Next week will involve even more communication as the deadline nears.

