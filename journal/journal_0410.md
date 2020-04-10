# Weekly Reflection
### Tyler Jang
### 04/10/20

#### Interesting Notes

##### Last Minute
Regrettably during this sprint, we fell into the trap of working and debugging at the last minute. We weren't able to get a full
display of cards until 2:50am last night, intensely stunting our progress when it came to our ability to reach our goals for Sprint #1.

I think that part of this is due to the adjustment in deadlines; personally, I was used to hitting the grind at the end of the week in preparation for a deadline on
Sunday night. The Thursday night deadline came as a bit of a surprise.

Regardless, we need to work earlier to integrate our components in the future so that we have more time to debug and improve our project.

##### Pair Programming at Its Finest
As part of this project, we've done a fair amount of scripting or pseudo-scripting in XML files. We have documented all of this in properties files, and we plan to
document it further in an example_rules.xml file. For now, though, in terms of defining the regex, Andrew and I have worked incredibly closely in pair programming.

During this sprint, Andrew shared his screen with him editing the XML files for different regex and cell rules. He handled much of the brainwork in designing regex
that would be accommodating to as many games as possible. As he did that, I helped discuss with him, and I also implemented the lambda functions built in the factories
in order to ensure that condition rules were met and to ensure that the actions and phase transitions were handled appropriately.

This worked very well and led to a very extensible factory design (although we are still debugging a few of the errors in its interactions with cells).

#### Summaries

##### XML Files
In addition to lambda functions, I had to work extensively with XML files and parsing. I used a tutorial by [mkyong](https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/) extensively.
This helped improve my knowledge of attributes, parts of elements containing a name declaration (e.g. <staff id="2001">); nodes, a generic XML component; textValues, text content contained in nodes; tags, the names of nodes; etc.

This has been a very powerful learning experience for parsing XML files that built on my earlier experience from simulation.

##### Understanding the Cells and Decks
Admittedly, it took me a while to understand the cell hierarchy implemented in our project. Below is the hierarchy, as well as the reasoning for it.
 - Offset: a direction from a cell (south, southeast, west, none, etc.).
    - Multiple different offsets (and standardized in an enumerated type) allow for different angles of offsets like for different solitaire games.
 - Cells: a meaningful pile or group of cards/decks/cells. Importantly, contains a map of Offsets to other Cells (including none to its deck).
    - Allows for meaningful groupings of decks and allows for groups of cards (like mini-stacks in solitaire) to be grouped and moved together.
 - Deck: each cell contains a deck (which may or may not be empty). The top card is the one visible to the user, whereas the bottom card is the one closest to the table.
    - Allows for meaningful groupings of cards that can be shuffled or pulled from. Deck's cannot include offset cards relative to one another.

#### Reflection

##### What was difficult about your work this week and why?
This week was most challenging because we set too lofty goals for ourselves. We worked great, and made enormous amounts of progress on our project, but our plan to have
3 games complete by the end of Sprint #1 was simply unrealistic. We will need to more appropriately plan for our sprints in the future.

##### What were the most important things you learned?
As I mentioned last week, I had spoken with Andrew about the desire to follow professional design practices as much as possible.
Specifically, we both shared an interest in:
 - holding regular meetings
 - keeping diligent meeting notes
 - following commenting practices early and often
 - being as intentional and communicative as possible
 
We both had desired to follow these practices during our previous projects, but due to other commitments and time constraints, it had been incredibly difficult.
We were able to follow this very well during the planning stage, but it has broken down during the past week or so. In the rush to finish implementation, commenting has gone by the wayside.
In an effort to solo code or pair program, regular meetings have slowed. Other common AGILE practices have decayed slightly in our group, especially with how busy we've been.

I recognize the reality of the situation, but I will try more diligently in the future to stay on track. 

##### How will this learning change your work next week?
This week, I've learned an incredible amount about lambdas. I had to implement 11 different factories to handle different parsing components of card games.

Lambdas are something that I had only heard of when entering this class, and this was my first chance to do a deep dive, with lambdas passed around as parameters and arguments and then evaluated at runtime.

This is a skill I will take beyond this class, as over the summer I will be working more with functional programming languages.
