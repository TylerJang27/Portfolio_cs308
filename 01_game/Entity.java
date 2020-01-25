package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * An abstract class to handle all the following in-game entities:
 *          Ball
 *          Power Up
 * Entity handles circular elements that will need to have a set speed and direction as they spin and animate.
 * All Entity instances will have a skin image.
 *
 * This code is well designed because the methods are short and simple, and the class hierarchy allows for logical implementation and extension.
 */
public abstract class Entity extends Circle {

    private static double RADIUS = Main.SIZE / 30;
    private double xVelocity;
    private double yVelocity;
    private double speed;

    /**
     * Constructor for Entity, a parent of Ball and Powerup
     * Uses default location near the Paddle on the screen
     * @param image     Skin Image to fill the Entity
     */
    public Entity (Image image) {
        this(image, Main.SIZE, Main.SIZE * 3 / 4);
    }

    /**
     * Expanded constructor for Entity, a parent of Ball and Powerup
     * @param image     Skin Image to fill the Entity
     * @param x         Location of centerX
     * @param y         Location of centerY
     */
    public Entity(Image image, double x, double y) {
        super(x, y, RADIUS);
        this.setFill(new ImagePattern(image));
    }

    /**
     * Steps in the established X and Y directions
     * @param elapsedTime   deltaT
     */
    public void step(double elapsedTime) {
        this.setCenterX(this.getCenterX() + xVelocity * elapsedTime);
        this.setCenterY(this.getCenterY() + yVelocity * elapsedTime);
        this.setRotate(this.getRotate() + speed / 40);
    }

    /**
     * Mutator for xVelocity
     * @param vel   the velocity in the x direction
     */
    public void setxVelocity(double vel) {
        xVelocity = vel;
    }

    /**
     * Mutator for yVelocity
     * @param vel   the velocity in the y direction
     */
    public void setyVelocity(double vel) {
        yVelocity = vel;
    }

    /**
     * Mutator for xVelocity and yVelocity and sets rotation speed based on magnitude of speed
     * @param xVel  the velocity in the x direction
     * @param yVel  the velocity in the y direction
     */
    public void setVelocity(double xVel, double yVel) {
        this.setxVelocity(xVel);
        this.setyVelocity(yVel);
        this.calcSpeed();
    }

    /**
     * Accessor for the y velocity
     * @return xVelocity    the velocity in the x direction
     */
    public double getxVelocity() {
        return xVelocity;
    }

    /**
     * Accessor for the y velocity
     * @return yVelocity    the velocity in the y direction
     */
    public double getyVelocity() {
        return yVelocity;
    }

    /**
     * Calculates the speed and direction based on X and Y velocity
     * Uses speed to set rotation speed.
     */
    public void calcSpeed() {
        speed = Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
    }
}
