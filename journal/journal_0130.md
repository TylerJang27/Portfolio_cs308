# Weekly Reflection
### Tyler Jang
### 01/30/20

#### Interesting Notes

##### XML Input
During my work on the Simulation project, I was tasked with implementing the XML Reader. Professor Duvall provided
a basic template in class for reading in XML information about video games, but this was my first time working with XML
files in any detail. Meanwhile, I had to create and read from XML files for different Simulation configurations.

I wasn't sure exactly how to go about specifying the fields for the XMLs. I read through the documentation for the different
simulations again and decided on fields that were necessary for each type. While I originally just aggregated all of these into one
long List of fields, I was unsatisfied with the result. Thinking back to the enumerated type introduction in class, I decided
to try my hand at creating an enum SimType, in which I specified each type and the fields related to that type.
Using this method, I was then able to have mySimType as a property of Simulation, allowing me to call mySimType.getFields()
to retrieve the additional fields, then storing the data for those fields in a Map.

This sort of a solution was largely unconventional for me, and while I had a general sense of confidence in the underlying
process, I was uncertain whether or not my implementation would work. So far, it has seemed to be successful and served to reduce
the length and "dirtiness" of my Simulation class substantially.

##### Git-ting Caught Up
While I've used git version control in the past to keep track of work and often to submit it, this class has been my first experience
into the land of branching. Using help from online, Professor Duvall's points, and one of my more experienced teammates, I was able to piece
together a few best practices when it came to merging and pulling (e.g. pull often and always). I've worked to use many commit messages
(my first project had 40+ commits) so that in the event of a merge conflict my teammates and I can diagnose where the problem originated,
and I've also worked to merge my changes periodically to a development branch created by one of my teammates.

While I still have much to learn and will undoubtedly need to memorize the sequence of git commands before long,
I'm confident that I'm making headway in my learning with git.

#### Summaries

##### Model, View, Controller
In designing our Simulation, my team focused heavily on following the Model, View, Controller (MVC) model. This model, as I understand it,
divides an application into its configuration and backend (the model), the display and frontend (the view), and the connecting piece (the controller).
As much as possible, the model will only talk to the controller, and the view will only talk to the controller. This keeps things compartmentalized and allows
for easy modular adjustments. 

For the Simulation, we divided functions based on the MVC model:
 * Mariusz worked on the creating a divider view for the frontend.
 * Thomas worked on the configuration for the model/backend.
 * I worked to connect the two with the controller.

So far, this has seemed to work well, but more reflection will be necessary at the completion of the project.

##### "Replace Conditional with Polymorphism"
One of the key concepts emphasized in class over the past week has been inheritance and polymorphism.
I attempted to use inheritance by creating an abstract Entity class in my Game project, but other than that inheritance was pretty limited.
In this Simulation project, however, we are working to maximize polymorphism. At the very least, we will have an abstract Cell class.
Crucially, this will have the abstract method calculateNextState(), which will determine the cell's next state. However, this state will
vary depending on the type of cell that it is. Using polymorphism, we will have sub-classes that will implement this method, depending on their own
configured parameters.

#### Reflection

##### What was difficult about your work this week and why?
I'm still somewhat unfamiliar with the OpenJFX Application structure. While I was able to put together a solution for the Game
project, it was not without bugs and the "dirtiest" code came when I was interacting with the Application structure. Some of my
teammates have more experience with it, and so I have been deferring to them for the Simulation project, asking as many questions as I
could to gain insight into the best practices for the package. Nevertheless, it's still slow-going and I can't help but feel at each turn
than I'm not quite implementing it correctly.

##### What were the most important things you learned?
My evolving experience with git has been a huge step for me. It will be invaluable for my summer internship, and it is a skill that is
essential in this career, from industry to Open Source. Acquiring an effective understanding and practical experience with
merging and branching is essential, and I trust it will serve me well.

##### How will this learning change your work next week?
While I've been able to commit and merge regularly, I still started this project later than I would have liked.
I should have started on Monday or Tuesday, so that my group could merge more frequently and collaborate more, especially since I 
have been working on the "mid-end" of the project. I need to continue to work to start earlier.