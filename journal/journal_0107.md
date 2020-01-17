# Weekly Reflection
### Tyler Jang
### 01/17/20

#### Interesting Notes

##### Lab: Hangman
During lab this week, Maverick Chung and I worked to improve the implementation of three similar hangman projects.
We turned the three classes, HangmanGameAutoGuesser, HangmanGameCleverExecutioner, and HangmanGameInteractiveGuesser, into HangmanGame, Guesser, and Executioner. This involved breaking down the guessing, validation, and checking processes into their respective class owners.

This was my first experience with pair programming. As I mentioned to Maverick when we started, usually when I'm scanning through code with a partner, it's to help debug a compiler or logic error; 
rarely is the purpose to optimize based on object-oriented principles. I found that having someone to talk with helped us ensure that we weren't missing different method implementations and control flow during the optimization process.

##### Starting the Game
As I read through some of the OpenJavaFX documentation and began working on implementing my breakout game, I finally began to grasp the cascading hierarchy used in those game implementations. 
I had worked with a similar package during HackDuke, when we tried to design an Android app interface. 

The concept of the stage, scenes, and nodes was fairly new to me, but its organization seems to be much more scalable for project updates and upgrades.
For example, I've already begun implementing this in the design of my Menu scenes, using the hierarchy as best as I can. So far, it has proved more ideal for controlling
which classes talk with one another at what points. 

#### Summaries

##### "OO in One Sentence"
Andy Hunt and Dave Thomas contended that their are three core principles that summarize Object Oriented programming.
These include: "Keep it DRY, Shy, and Tell the Other Guy." In other words, avoid repeating code, don't write code that reveals too much of itself or uses too much from other classes,
and ensure that names are clear and intentional. The second point is important for security, with regard to permissions as well as for implentation purposes with things like Collections.
The final point reflects one of the core concepts of *Clean Code*, that clean code reflects **care** by its author. 

##### Game Planning
In preparing the plan for my breakout game, I conducted significantly more research than I typically would for an analogous project.
Research is arguably more important than the actual development, as countless projects have failed due to an understanding gap between the developers and end users/clients.
In my case, I had to really learn what some of the more specific tropes of breakout games required. In other words, what made breakout so addictive?

One example of the fruits of my labor included paddle motion and its effects on the bouncer. The bouncer's velocity as it rebounds off the paddle is often partly dependent on the paddles velocity as it approaches the collision.
This transfer of momentum is essential to breakout games. One other component is the angle of rebound, dependent on which side of the paddle is contacted. This effect is vital to control and user interactions during gameplay.

#### Reflection

##### What was difficult about your work this week and why?
I was ill for the beginning of this week, impairing my ability to work on the project. While I could have worked a bit harder to get started early on the game, 
I procrastinated due to my condition, leading to a significant buildup of work. For the next project, especially when I have a group depending on me,
I need to start much earlier and much more thoughtfully.

##### What were the most important things you learned?
This week I became acquainted with OpenJavaFX, a package that I believe is analogous to many other Java GUI designs. This was my first time creating a GUI in Java,
at least from scratch, and I believe this will be useful for future projects, both personal and professional.

##### How will this learning change your work next week?
Next week, I will start much sooner. I did a good job this week of doing the readings early, but I need to plan out the architecture of my projects much earlier for the next assignment.