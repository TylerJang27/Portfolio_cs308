package breakout;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * A class for Power Ups released when certain bricks break
 * Extends Entity, a parent of Ball
 * Spawned in GameManager, upon a brick breaking, creating a Powerup instance based on the Brick's stored powerup int.
 *
 */
public class Powerup extends Entity {

    private static final double SCENE_HEIGHT = Main.HEIGHT;
    private static final String SHIELD_PNG = "Resources/shield.png";
    private static final String MULTI_PNG = "Resources/multi.png";
    private static final String HEAVY_PNG = "Resources/heavy.png";
    private static final String BONUS_PNG = "Resources/bonus_life.png";
    private static final String[] SKINS = {SHIELD_PNG, MULTI_PNG, HEAVY_PNG, BONUS_PNG};

    private static final int FALL_RATE = 5;

    private int powerupID;
    /**
     * ID:
     *      1 = shield
     *      2 = multiball
     *      3 = heavyball
     *      4 = bonus life
     */

    /**
     * Constructor for powerup
     * @param x     Location of centerX
     * @param y     Location of centerY
     * @param id    Type of powerup, 1-4
     * @throws FileNotFoundException if file name invalid (see TextReader.java)
     */
    public Powerup(double x, double y, int id) throws FileNotFoundException {
        super(new Image(new FileInputStream(SKINS[id])), x, y);
        powerupID = id;
        this.setVelocity(0, FALL_RATE);
    }

    /**
     * Signifies the powerup has been caught and returns the powerupID
     * @return powerupID    id corresponding to the desired effect
     */
    public int recover() {
        this.setCenterY(SCENE_HEIGHT * 2);
        return powerupID;
    }

}
