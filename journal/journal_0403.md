# Weekly Reflection
### Tyler Jang
### 04/03/20

#### Interesting Notes

##### New Features and Communication
This has thus far been the best communication I've experienced during a group project in a long time.
It has helped that we all knew each other before this project started, so we don't feel conflicted about stepping
on each others' toes, and we know each others' strengths and weaknesses. It also makes us much more willing to speak and meet with one another,
especially in this time of social distancing. The company is appreciated.

Specifically, the progression has been as follows:
 - We began by communicating our expectations for the project, along with different components that would be necessary and different components that could act as extensions.
 - We divided into meaningful roles for each person, along with ensuring that the boundaries our interfaces would be met with not only ideal code interfaces but also good interpersonal communication interfaces.
 - We then held daily meetings, flushing out the architecture and pipeline of our program, reinforcing and deliberating on expectations, implementations, etc.
 - We have continued to meeting, flushing out our planning documentation and ensuring our interfaces were well thought-out for our current stage.

##### Interfacing with Maverick
Andrew and I are working on both the Controller and Data. We arrived at this decision because both of these segments are essential and merit having redundancies (i.e. 2 pairs of eyes ensuring minimal bugs and intentional design).

Maverick is working on the core Back End/Engine by himself. We have worked intentionally to ensure that our data and the means with which we provide it to the engine are easy to be read.
Last night we took this to its ultimate stage with pair interface programming leading up to our DESIGN_PLAN.md submission.

As we were working on the APIs, we worked in 5 distinct stages:
 - Andrew and I communicated what our expectations and use cases would be for reading in game rules and data
 - Andrew and I communicated with Maverick to unify our expectations
 - I drafted up an example interface for the IRuleSet.java interface
 - Maverick renamed this IPhaseMachine.java to be more informative and adjusted the hierarchy, adding additional interfaces to better inform the structure of the rules
 - I went back through and commented his classes to ensure that both Andrew and I understood their purpose and possible implementation
 
 This has been monumental in defining our planning and ensuring that we are prepared to have well-communicated interfaces. I will check back regularly within the following weeks to see just how effective these have been in terms of our results.

#### Summaries

##### Game Programming Gems
Needless to say, one of the core components of this final project is the use of xml/text/properties files to include the data and operation of the different game types. We decided early on that we wanted to take this as far as possible (Maverick: "I want to build a project that can play ANY card game.").
Since then, we have been hard at work designing the different logic and assets necessary to be parsed and generated at runtime from the data files. 

The reading this week regarding data-driven design in games echoes the following hierarchy, with each level having more data-driven design than the last:
 - Text files for languages
 - XML files for constants and other behavior (including camera, default locations, etc.)
 - Don't hard code any behavior or component
 - Scripting control flow through XML files
 - Overkill: too much in scripts
 
 We've been striving for the second-to-last item, without straying into the final item. We will continue to monitor our progress on that goal.

##### Design Decision: Phase Set-Up
In designing the logic of our game, we decided fairly early on that we wanted to implement a phase machine. This was the counsel specifically of Maverick, and it seemed to make sense for our rules setup.

We ultimately decided to have a Phase Machine, which has different Phases, each with their own rules for card movement and phase transition. Each rule would have conditions and actions (both for cards and phase transitions.) This information will be stored in the rules XML for each game, 
with additional regex governing these different rules.

Other solutions we considered were:
 - A Phase Machine with every cell, and each cell having its own rules
    - While this initially appeared, simpler, we realized that this would have led to a lot of duplicated data and probably duplicated code as many games have cells that are very similar but would have just led to duplication.
 - More broad phases (i.e. the game solitaire would only have the phases Start-Play-End)
    - We decided this was too broad, and it had lots of implicit transitions (i.e. scores, validation, etc.) that would occur without really being accounted for in the design. It was better to be more explicit and have more phases (e.g. validation, reject, etc.)

#### Reflection

##### What was difficult about your work this week and why?
This week has been particularly difficult with my family's move to Arizona. There's been considerable uncertainty with regard to the availability and safety of travel, our living conditions in Arizona, along with access to basic necessities in Arizona.

Fortunately, after some initial stress and uncertainty, things have seemed ot settle down, and I think I will be able to resume work "as normal." It's still not ideal, but I'm fortunate that my family still has security for now.  

##### What were the most important things you learned?
When we first formed our teams, I spoke with Andrew about the desire to follow professional design practices as much as possible.
Specifically, we both shared an interest in:
 - holding regular meetings
 - keeping diligent meeting notes
 - following commenting practices early and often
 - being as intentional and communicative as possible
 
We both had desired to follow these practices during our previous projects, but due to other commitments and time constraints, it had been incredibly difficult.
With the benefit here of holding each other accountable, however, we have been doing a great job this far with communication. We have had team-wide or subteam-wide meetings almost every day, and we have kept meeting notes and stored these in the doc folder. I'm very satisfied with how things have been going, and I hope to continue these practices.

##### How will this learning change your work next week?
Our communication this week has been stellar. I would like to continue to work in a similar capacity in the following weeks. A couple of areas in which I see potential for improvement, however, include
 - involving the other back end subteam (i.e. Maverick) more thoroughly, especially since there will be extensive overlap with his components
 - documenting the above involvement so that both parties can have something to which they can refer back
 - beginning to look into Unit Testing options to improve our design practices and reliability 
