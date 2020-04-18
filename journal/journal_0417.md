# Weekly Reflection
### Tyler Jang
### 04/17/20

#### Interesting Notes

##### Bug Tracking
During this sprint, we finally got around to implementing formalized bug tracking. In our slack channel, we added a new channel entitled,
"#bug-list" in which we have outlined the following format for bugs and issues.
 - BUG/ISSUE #_
 - Reporter:
 - Behavior:
 - Insights/Implications:

As a team, we distinguished between bugs and issues, with the former being problems that prevented the code from running at runtime, and the latter being features that did not work as we logically intended.
This tracking has helped us work on a unified platform and work toward the same goals.

##### Team Calls
I must say, I felt the importance of team building exercises that companies put on. As Sarah mentioned offhandedly during one of our team meetings,
they were the reason she felt the urge to work. The comfort that we have with each other and the enjoyment during our team meetings
has been the main reason we have implemented so much during this project. Having a strong sense of trust and comfort with one another has led to simple and straightforward changes between the different
parts of our project.

#### Summaries

##### Design Decision: Speed
Throughout the majority of this sprint, we have run into considerable latency during our game. Every time our game loaded, every time a move was attempted, and every time a move was processed,
we had to wait some time for the program to respond. We attempted to stop creating new images for the cards, which cleared up some of the memory errors we encountered.
We also attempted to remove our many print statements, as we knew that could have been delaying our game.

Finally, after much debate and trial-and-error, we implemented a basic search loop in the controller (Andrew) to see which cells were new and which were preserved from the previous move.
This made it so that only some of the display cells needed to be updated, and it improved the speed of our program substantially.

##### Hakuna Matata
As a team, we've realized that we've lagged behind our sprint goals for all the sprints. Nevertheless, we have been incredibly proud of our accomplishments in terms of the features and volume and design of code we have implemented.
This is something that we have recognized internally, and we have adjusted our goals internally.

The S/U process is quite beneficial during this time of this extensive stress and uncertainty, and in order to benefit our own mental health, we have recognized that we still want to achieve a viable game with flexibility, but we
will take this entire project as a learning experience into intentional design and testing and reasonable expectations in terms of sprint planning.

#### Reflection

##### What was difficult about your work this week and why?
This week has been a particularly busy week for me. In addition to two midterms and several projects in psychology, I've been tasked with debugging the code I wrote for the last sprint. On top of that, I've been feeling a small bit under the weather.
I'm disappointed with my lack of progress this week, and I'm hoping that I can touch things up for the final sprint.

##### What were the most important things you learned?
Print statements. Virtually everyone on our team had opted for using print statements as our general method of debugging. We had consciously recognized the value of regular JUnit testing and
standard testing data, but very few of us implemented early JUnit tests, hoping to implement more features that would allow these other features to scale. We would have been much better served making
an XML with specified rather than random cards, allowing us to easily test both through an end-to-end user test and through smaller JUnit testing. 

##### How will this learning change your work next week?
Next week we need to make sure to finish our testing much sooner, so that when we pull things together and integrate changes we can be certain each piece works. This has been a concept we have all  been considering throughout the entire development process, but we have lacked the internal leadership
and motivation to fully implement it, unfortunately.
