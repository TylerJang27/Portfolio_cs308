package cellsociety.Controller;

import cellsociety.Main;
import cellsociety.Model.FireGrid;
import cellsociety.Model.GameOfLifeGrid;
import cellsociety.Model.Grid;
import cellsociety.Model.PercolationGrid;
import cellsociety.Model.PredatorPreyGrid;
import cellsociety.Model.RockPaperScissorsGrid;
import cellsociety.Model.SegregationGrid;
import cellsociety.View.ApplicationView;
import cellsociety.View.CellClickedEvent;
import cellsociety.View.CellStateConfiguration;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Core class of the Controller part of the MVC Model. Reads in data from XML files for the selected simulation as well as
 * styling information. To use this class, have Main.java create an instance of SimulationControl, passing the stage, and call next() for each frame.
 *
 * @author Tyler Jang
 */
public class SimulationControl {

  public static final int DEFAULT_RATE = 5;
  public static final double SIZE = 700;
  public static final int RATE_MAX = 10;

  private static final String STYLE_ADDRESS = "src/resources/Styling.xml";
  private File styleFile;

  private Grid myGrid;
  private Simulation mySim;
  private ApplicationView myApplicationView;
  private boolean paused;
  private int rate = DEFAULT_RATE;
  private int frameStep;
  private int numCols, numRows;
  private static final ResourceBundle RESOURCES = Main.myResources;

  /**
   * Constructor for creating a SimulationControl instance. Defines the styleFile to be used, pauses the Simulation and resets the frame count, and initializes the View.
   * Prompts the user to choose a configuration file.
   *
   * @param primaryStage the stage for the animation
   */
  public SimulationControl(Stage primaryStage) {
    styleFile = new File(STYLE_ADDRESS);
    paused = true;
    frameStep = 0;
    initializeView(primaryStage);
    myApplicationView.logError("Choose a configuration file to start a simulation!");
  }

  /**
   * Pauses the simulation by setting paused to true, halting the progress of next()
   */
  private void pauseSimulation() {
    paused = true;
  }

  /**
   * Calls next() a singular time, but stops the repeated play sequence
   */
  private void stepSimulation() {
    paused = true;
    next(true);
  }

  /**
   * Plays the simulation by setting paused to false, resuming the progress of next()
   */
  private void playSimulation() {
    paused = false;
  }

  /**
   * Steps the simulation by updating the Grid and updating ApplicationView. This should be called with singleStep set to false from outside this class.
   *
   * @param singleStep true if only one step is supposed to occur
   */
  private void next(boolean singleStep) {
    try {
      int stepper = frameStepNext();
      if ((!paused && stepper == 0) || singleStep) {
        myGrid.nextFrame();
        updateViewGrid();
        myApplicationView.displayFrameNumber(myGrid.getFrame());
      }
    } catch (NullPointerException e) {
      myApplicationView.logError(RESOURCES.getString("BadStep"));
    }
  }

  /**
   * Steps the simulation by updating the Grid and updating ApplicationView. The default case for calling from outside this class.
   */
  public void next() {
    next(false);
  }

  /**
   * Updates all the Cells in GridView based off of the values in myGrid
   */
  private void updateViewGrid() {
    List<Point> points = myGrid.getPointList();
    for (Point p : points) {
      int state = myGrid.getState((int) p.getX(), (int) p.getY());
      myApplicationView.updateCell((int) p.getX(), (int) p.getY(), state);
    }
    myApplicationView.updateCellCounts();
  }

  /**
   * Increments frameStep and resets it based on the desired framerate.
   *
   * @return frameStep
   */
  private int frameStepNext() {
    frameStep += 1;
    if (frameStep >= rate) {
      frameStep = 0;
    }
    return frameStep;
  }

  /**
   * Creates a new ApplicationView to start the display.
   *
   * @param primaryStage the stage for the animation
   */
  private void initializeView(Stage primaryStage) {
    EventHandler<MouseEvent> stepHandler = event -> stepSimulation();
    EventHandler<MouseEvent> pauseHandler = event -> pauseSimulation();
    EventHandler<MouseEvent> playHandler = event -> playSimulation();
    EventHandler<MouseEvent> saveHandler = event -> saveFile();
    ChangeListener<? super Number> sliderListener = (observable, oldValue, newValue) -> {
      changeSimulationSpeed(observable.getValue());
    };
    EventHandler<CellClickedEvent> cellClickedHandler = event -> {
      int state = myGrid.cycleState(event.getRow(), event.getColumn());
      myApplicationView.updateCell(event.getRow(), event.getColumn(), state);
    };
    myApplicationView = new ApplicationView(SIZE, primaryStage, playHandler,
        pauseHandler, stepHandler, saveHandler, sliderListener, getFileListener(),
        cellClickedHandler);
  }

  /**
   * Sets the initial settings for SimulationControl.
   *
   * @param dataFile the File from which to read configuration instructions
   */
  private void initializeModel(File dataFile) {
    mySim = new ConfigParser(RESOURCES.getString("Type")).getSimulation(dataFile);

    rate = mySim.getValueMap().getOrDefault(RESOURCES.getString("Rate"), DEFAULT_RATE);

    numCols = mySim.getValue(RESOURCES.getString("Width"));
    numRows = mySim.getValue(RESOURCES.getString("Height"));

    Map<String, Style> styles = new StyleParser(RESOURCES.getString("Type")).getStyle(styleFile);
    Style style = styles.get(mySim.getType().toString());
    String shapeString = getShapeString();
    List<CellStateConfiguration> cellViewConfiguration = getCellStateConfigurations(style, shapeString);
    myApplicationView.initializeGrid(numRows, numCols, style.getValue(RESOURCES.getString("Outline")), cellViewConfiguration);
    myGrid = createGrid();

    updateViewGrid();
    pauseSimulation();
  }

  /**
   * Extracts ID, and fill (color or image) information from style.
   *
   * @param style       Style containing visualization information
   * @param shapeString the shape of the cells, Hexagon or Rectangle
   * @return
   */
  private List<CellStateConfiguration> getCellStateConfigurations(Style style, String shapeString) {
    List<CellStateConfiguration> cellViewConfiguration = new ArrayList<>();
    for (Map<String, String> params : style.getConfigParameters()) {
      String displayStyle = RESOURCES.getString("Color");
      for (String s : params.keySet()) {
        if (s.equals(RESOURCES.getString("Color")) || s.equals(RESOURCES.getString("Image"))) {
          displayStyle = s;
        }
      }
      cellViewConfiguration.add(new CellStateConfiguration(shapeString, displayStyle, params));
    }
    return cellViewConfiguration;
  }

  /**
   * Extracts information from mySim to determine if shape is Hexagon or Rectangle.
   *
   * @return a String representing the shape of cells
   */
  private String getShapeString() {
    String shapeString;
    int shape = mySim.getValue(RESOURCES.getString("Shape"));
    if (shape == GridParser.HEXAGON) {
      shapeString = RESOURCES.getString("Hexagon");
    } else {
      shapeString = RESOURCES.getString("Rectangle");
    }
    return shapeString;
  }

  /**
   * Creates grid based off of the type of Simulation stored in mySim.
   *
   * @return a subclass of Grid
   */
  private Grid createGrid() {
    String simType = mySim.getType().toString();
    if (simType.equals(RESOURCES.getString("GameOfLife"))) {
      return new GameOfLifeGrid(mySim.getGrid(), mySim.getValueMap());
    } else if (simType.equals(RESOURCES.getString("Percolation"))) {
      return new PercolationGrid(mySim.getGrid(), mySim.getValueMap());
    } else if (simType.equals(RESOURCES.getString("Segregation"))) {
      return new SegregationGrid(mySim.getGrid(), mySim.getValueMap());
    } else if (simType.equals(RESOURCES.getString("PredatorPrey"))) {
      return new PredatorPreyGrid(mySim.getGrid(), mySim.getValueMap());
    } else if (simType.equals(RESOURCES.getString("Fire"))) {
      return new FireGrid(mySim.getGrid(), mySim.getValueMap());
    } else if (simType.equals(RESOURCES.getString("RockPaperScissors"))) {
      return new RockPaperScissorsGrid(mySim.getGrid(), mySim.getValueMap());
    }
    return null;
  }

  /**
   * Writes file to data/ and notes this in the console.
   */
  private void saveFile() {
    WriteXMLFile writer = new WriteXMLFile(mySim, myGrid, rate);
    myApplicationView
        .logError(String.format(RESOURCES.getString("FileSaved"), writer.writeSimulationXML()));
  }

  /**
   * Returns a handler for selecting the file to reset configuration.
   */
  private ChangeListener<File> getFileListener() {
    return new ChangeListener<File>() {
      @Override
      public void changed(ObservableValue<? extends File> observable, File oldValue,
          File newValue) {
        try {
          initializeModel(newValue);
          myApplicationView.logError(RESOURCES.getString("ConsoleReady"));
        } catch (XMLException e) {
          myApplicationView.logError(e.getMessage());
        }
      }
    };
  }

  /**
   * Sets the new simulation rate.
   *
   * @param newValue the new simulation rate, usually on a scale of 1-10
   */
  private void changeSimulationSpeed(Number newValue) {
    rate = (RATE_MAX - newValue.intValue());
  }

  /**
   * Returns a handler for changing the new simulation rate.
   */
  private ChangeListener<? super Number> getSliderListener() {
    return new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {
        changeSimulationSpeed(observable.getValue());
      }
    };
  }
}
