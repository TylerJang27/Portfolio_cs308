# CompSci 308: Game Project Design Review

### Name: Tyler Jang

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci308/current/assign/01_game/):

## Status

### Time Distribution
I spent a fair amount of time before I started planning the classes I would use. I knew I would have some sort of Button class (implemented through MenuBox), a MenuPage scene creator, a GameManager scene creator, and different classes for ToolBar, Ball, Paddle, Brick, Enemy, and Powerup.
I created the skins for these classes early on so I could compile and visualize their implementation each time I added a new method.

Once I started, I spent the most time customizing and refining GameManager. I made it as readable I could, given its many operations.
There are many different components that go into the creation of a game scene, so there are 26 different methods in GameManager. This comprised the majority of my time.
With more time, I would attempt to refactor this and give more of those methods to the specific game element classes (i.e. adding more collision methods to Ball) .

### Non-DRY Code
There are only a few instances of code that was not DRY. I tried as much as I could to minimize and refactor code I was writing when I noticed it was not DRY.

There are some repeated cases in MenuPage.setupMenu(), MenuPage.setupLevel(), and MenuPage.setupHelp() where items are added to root's children. This would probably be better accomplished by adding the items to root's children as soon as they are created rather than in bulk. 
```java
root.getChildren().add(titleText);
root.getChildren().addAll(startBox.getAllNodes());
root.getChildren().addAll(helpBox.getAllNodes());
root.getChildren().addAll(levelBox.getAllNodes());
```

When some of the collisions are detected and processed in GameManager (e.g. paddleCollision()), the x and y velocities are calculated separately and set separately. This would be improved by adding a 
setVelocity() function to Entity rather than using x and y independently.
```java
xVel *= DEFAULT_SPEED / Math.sqrt(xVel * xVel + yVel * yVel);
yVel *= DEFAULT_SPEED / Math.sqrt(xVel * xVel + yVel * yVel);
b.setxVelocity(xVel);
b.setyVelocity(yVel);
```

There are a couple instances of minor repetition like in GameManager.brickCollision() when if statement lines are separated by x and y coordinates:
```java
if (cenX> left && cenX < right) {
        if (cenY + rad > bot && cenY - rad < top) {
            ...
        }
    ...
}
```

### Most Important Bug
The only known bug at this time is that the different MenuPage screen cannot switch between the different scenes (i.e. Help, Levels) after a game has been completed or quit. This is due to how
MenuPage and GameManger handle getMyScene() and pass it to Main, where they are interchanged to transition to the game. The majority of modifications to fix this bug would need to be handled in Main. 

```Main.step()``` handles the majority of this implementation, with an if statement dividing the game loop into ```inGame``` and ```!inGame``` contexts. This needs to be modified to fix the pipeline problems.

### Method 1
MenuPage.setupHelp() is a fairly easy to read method. It returns a Scene with all of the elements of the Help page.
It is set up logically, adding items from top to bottom.
Processes that take many lines of code are handled by other classes or methods (e.g. ```TextReader.readFile```).
The method ends by adding all of the nodes to root in the order they were created..

This method was created in [finished help page](https://coursework.cs.duke.edu/compsci308_2020spring/game_taj26/commit/753bff0b3a7406cf42c00c64708b291194c8bbfc) and added to in
[finished help page description](https://coursework.cs.duke.edu/compsci308_2020spring/game_taj26/commit/78b4390079f4a8abb8596e4a89e76768f2f90bab) to include the instructions loaded in from a text file.
At that point, it was virtually completed, only requiring some cleaning. Each commit for this method was framed logically and included its functionality (start and finish).

```java
private Scene setupHelp() throws FileNotFoundException {
    Group root = new Group();

    MenuText titleText = new MenuText(centerX, sceneHeight / 6, HELP_TEXT, Main.TITLE_FONT);

    String instructions = TextReader.readFile(INSTRUCTIONS_TXT);
    MenuText instructionsText = new MenuText(centerX, sceneHeight / 3, instructions, new Font("Calibri", BOX_SIZE / 12));
    instructionsText.setTextAlignment(TextAlignment.CENTER);

    MenuBox backButton = new MenuBox(sceneWidth / 10, sceneHeight / 10, BOX_HEIGHT, BOX_HEIGHT, Color.DARKGREY, "", new Image(new FileInputStream(BACK_PNG)), backHandler);

    root.getChildren().add(titleText);
    root.getChildren().add(instructionsText);
    root.getChildren().addAll(backButton.getAllNodes());
    return new Scene(root, sceneWidth, sceneHeight, BACKGROUND);
}
```

### Method 2
Main.step() is one of my worse methods. It comprises the game loop and also adds key input handlers to the scenes as necessary.
It also contains the only known bug in the code. I created the inGame variable to make the code more readable and track the game status.
Otherwise, most parts of this method are short and relatively easy to read.

In the commit [finished help page description](https://coursework.cs.duke.edu/compsci308_2020spring/game_taj26/commit/78b4390079f4a8abb8596e4a89e76768f2f90bab),
I added the step function because this was the first time a step was needed to change scenes to the help page.
In the commit [added ball that bounces](https://coursework.cs.duke.edu/compsci308_2020spring/game_taj26/commit/dfe792e22846fc36351f4eeeb0f4874773d59b49),
I modified the step function to accommodate the scene change to that of GameManager, because this was the first time I needed to change the scene and animate it.
Further edits included attempts to debug the scene change problem, but ultimately the changes were reverted in favor of the mostly working code from the previous commit.

The commit messages pertaining to this method could have been clearer, indicating that they changed more than just the implementation, but also the pipeline from Main.step().

```java
private void step (double elapsedTime, Stage stage) throws FileNotFoundException {
    /**
     * BUG: once game has ended (through death or quit), only the Start button works
     * Has something to do with how the Scene references are passed around
     */

    if (!inGame) {
        myScene = myMenu.getMyScene();
        if (myScene.equals(myGameManager.getMyScene())) {
            startGame();
        }
    } else {
        myScene = myGameManager.getMyScene();
        myGameManager.step(elapsedTime);
    }
    stage.setScene(myScene);

    //adds key input
    if (myScene.getOnKeyPressed() == null) {
        myScene.setOnKeyPressed(e -> {
            try {
                handleKeyInput(e.getCode(), elapsedTime);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }
}
```

### Conclusion
I worked in a couple ways to make my code as readable as possible.

**Comments:** Before almost all commits, I ensured that all methods had a block comment attached to them.
This was perhaps overdone (especially for some "getters" and "setters"), but it allowed me to keep track of the purpose of each method, as well as
the elements of their signatures.

**Refactoring Methods:** By far, GameManager is my longest class. I refactored and made intentional decisions with collisions, bricks, and balls to avoid repeated code,
but it is still quite a long class. Two instances in which I refactored code for readability are step() and collision(), which involve calls to many different handlers, a specific one for each type of element. 

```java
public void step(double elapsedTime) throws FileNotFoundException {
    ...
    if (alive()) {
            powerupUpdate(elapsedTime);
            freezeUpdate();
            enemyUpdate(elapsedTime);
            ballUpdate(elapsedTime);
            collision();
            ...
    }
    ...
}
```

```java
private void collision() throws FileNotFoundException {
    wallCollision();
    brickCollision();
    paddleCollision();
    enemyCollision();
    laserCollision();
    powerupCollision();
}
```

## Design

### Adding Levels
To add additional levels, simply add more levelN.txt files to Resources and make the necessary changes to GameManager.setLevel() to load it in correctly, as well with any other elements (e.g. a new Enemy). 
The MenuPage setupLevel() would also be need to be altered to add a button for the new level, as well as adding a cheat key to switch to it in Main.handleKeyInput().

### Non-SHY Code
My code is relatively SHY. Most of the classes have unidirectional dependency (e.g. GameManager depends on Ball but not vice versa). Some exceptions include with MenuPage and GameManager, which depend on each other.
This is because of how I implemented the scene changes, with MenuPage getting GameManager's scenes in order to implement the scene switch. This could be improved with more time on the project.

### Feature 1
Ball collisions and rebounds were something that I implemented during the middle stage of development. It combines several different Classes in order to change direction upon contact with another collidable object.

I created Entity as an abstract class before creating Ball. It included methods to set the x and y velocities, step, and determine the speed and direction. 
Ball inherited from it, with the ability to step and set x and y velocities as well.

My goal was to have the Ball bounce as if off a flat surface for walls and bricks, but change the direction it bounced if rebounding off the paddle.
In order to further implement this, I created wallCollision() and brickCollision() in GameManager that loop through and check for collisions, then triggering a flat collision.
Likewise, paddleCollision() checks for contact with the paddle, in which the x and y velocities are modified based on the angle with which they strike the paddle relative to its center. 
This mostly just includes a calculation with some additional scaling.

This feature's design assumes that contact will come from outside the collidable object (i.e. above in the case of the paddle).
In designing, I assumed that the information from both the Balls and the collidable objects were needed, so I implemented all of the collisions
in GameManager, rather than in Ball itself. This unfortunately added to the number of methods in GameManager.

As more collidable objects were added (e.g. Paddle and Enemy), these also had to be accounted for in GameManager with more methods.

### Feature 2
The Power Ups were an important scalable feature implemented after the Ball and Bricks.

Powerup extended the abstract class Entity, the superclass of Ball. This allowed its motion to be handled simply, with a 0 xVelocity and constant downward yVelocity.
In addition to the Powerup class, Powerup objects needed to be instantiated upon the breaking of bricks, handled in GameManager.destroyBrick(). Their collisions with the Paddle needed to be detected,
and the Power Up effects needed to be introduced. This was handled based on time stamps and IDs, so that the effects would disappear after a fixed length of time.

This design remained relatively constant, and I only used two time stamp variables to minimize the number of related instance variables in GameManager.
Because of the variety of different Power Up effects, I chose to have this implement in GameManager rather than a separate PowerupEffect class.

### Conclusion
Overall, the program uses Main to create the stage and calls MenuPage for menu scene information. This may change depending on buttons clicked in the menu screens.
Once the game has been started (via clicking Start or selecting a level), GameManager builds the level and populates the scene, returning its scene to Main to display.
Main animates/steps for the paddle, balls, etc. in the game, also adding key code handlers when appropriate. Upon the completion of a level, GameManager builds the next level and continues the process.

Upon winning or losing the game, the player is returned to the MenuPage main menu scene, where they can opt to play again. 

All of the scenes use the other classes (e.g. Ball, MenuBox, Paddle) to populate their contexts.

## Alternate Designs

### Possible Alternate Implementations
I wrestled with having Enemy and Brick inherit from the same abstract superclass. I thought this might be more effective, since they have similar behaviors, like
taking damage, increasing the score, or colliding with the Ball, but I eventually decided against it, because of their different shapes.
Brick was based loosely on Rectangle, while Enemy came from Circle. It's possible that I should have created an interface for Collidable, and had both classes implement that interface.

### Telling the Other Guy
As described above in Feature 1, the Ball could have held more responsibility in checking its own collisions, rather than having all the processing happen in Gamemanager.

One other case would be with ToolBar, which took GameManager in its constructor in order to point back to implement the Quit button. This "Told the Other Guy," but was not very SHY, and ultimately could have been better implemented.

### Design Decision 1
I decided to make MenuBox the same general button for almost all clickable buttons in the project.
I also gave it a has-a relationship with MenuText. This led to it having many different constructors, but it was effective in allowing me
to implement standards across the program, such as button colors and fonts, and it greatly streamlined the implementation of my MenuPage class.

I considered making separate boxes for each screen, but this seemed to be more effective, since I could just change a singular setting in a specific case if it came up (a simple 1-line change).

### Design Decision 2
I decided to have GameManager have a List instance variable called balls. This turned out to be a very effective decision because it allowed me to
add more balls easily with the Multiball Power Up, and I was able to loop through all the balls every time I checked for a collision.
It also allowed me to declare that the player had lost a life anytime balls was empty (see GameManager.step()).

Alternatively, I could have just created one ball and added more instance variables for each ball, but this would have been rather clumsy and my foresight served me well for my implementation.

### Conclusions
Aside from some minor pipelining issues between Main, MenuPage, and GameManager, I am rather satisfied with the design of my code, dividing up game elements between many different classes, each largely with their own behavior.
I believe that my code generally exhibits Object-Oriented principles and serves as a good start for the course.

## Overall Conclusion
The best thing I learned during this project is to refactor as I go. In the past, I would wait until I had finished an implementation before refactoring/cleaning code, but it is much more time-effective to do it as one goes, I found from this project.

The worst thing I experienced in this project was difficulty with Scenes and animation. I had limited experience and understanding of the Application setup of
OpenJFX, and this led to more difficulties and bugs.

In the future, I will continue to plan out my designs using many classes to follow Object-Oriented Principles. However, I will need to become more familiar with the libraries I am using,
along with their best practices to ensure I don't run into bugs like I did here.