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
Main.step() is one of my worse methods.

TODO: FINISH THIS _____________________________________________
_____________________________________________________________F
_____________________________________________________________f

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

Combined emphasis with **asterisks and _underscores_**.


You can put links to commits like this: [My favorite commit](https://coursework.cs.duke.edu/compsci308_2019spring/example_bins/commit/ae099c4aa864e61bccb408b285e8efb607695aa2)


## Design

### Adding Levels
To add additional levels, simply add more levelN.txt files to Resources and make the necessary changes to GameManager.setLevel() to load it in correctly, as well with any other elements (e.g. a new Enemy). 
The MenuPage setupLevel() would also be need to be altered to add a button for the new level, as well as adding a cheat key to switch to it in Main.handleKeyInput().

### Non-SHY Code

### Feature 1

### Feature 2

### Conclusion
PULL FROM DESIGN.md

## Alternate Designs

### Possible Alternate Implementations

### Telling the Other Guy

### Design Decision 1

### Design Decision 2

### Conclusions

Here is another way to look at my design:

![This is cool, too bad you can't see it](crc-example.png "An alternate design")

