import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

/**
 * Name: Ezra Gubbay.
 * ID: 209184308
 * Description - The Paddle class. Represents a paddle in the game that is player-controllable.
 */
public class Paddle implements Sprite, Collidable {

    private KeyboardSensor keyboard;
    private final double movementSpeed = 10.0; // Determines how far the Paddle will move horizontally by keypress.
    private GUI gui;
    private final Block delegatedBlock;

    /**
     * Constructor.
     * creates a delegated Block to manage similar functionalities related to rectangles, drawing, and collision.
     */
    public Paddle() {
        this.delegatedBlock = new Block(new Point(374, 560), 80, 30);
    }

    /**
     * Sets the Paddle's GUI item.
     * This is used to receive the keyboard from the GUI that Game is using, so Paddle can determine current keypress
     * @param gui - The GUI object that Paddle should store.
     */
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    /**
     * Moves the Paddle left.
     * If the Paddle passes the left edge of the game, will move Paddle to the right edge of the game.
     */
    public void moveLeft() {
        double x = this.delegatedBlock.getUpperLeft().getX() - movementSpeed;
        double y = this.delegatedBlock.getUpperLeft().getY();
        if (x > 10) {
            this.delegatedBlock.setUpperLeft(new Point(x, y));
        } else {
            this.delegatedBlock.setUpperLeft(new Point(790 - this.delegatedBlock.getWidth(), y));
        }
    }

    /**
     * Moves the Paddle right.
     * If the Paddle passes the right edge of the game, will move Paddle to the left edge of the game.
     */
    public void moveRight() {
        double x = this.delegatedBlock.getUpperLeft().getX() + movementSpeed;
        double y = this.delegatedBlock.getUpperLeft().getY();
        if (x < 790 - this.delegatedBlock.getWidth()) {
            this.delegatedBlock.setUpperLeft(new Point(x, y));
        } else {
            this.delegatedBlock.setUpperLeft(new Point(10, y));
        }
    }


    /**
     * Notifies the Paddle that time has passed.
     * Paddle determines if the left or right arrow keys are pressed, and moves left or right respectively
     */
    @Override
    public void timePassed() {
        this.keyboard = this.gui.getKeyboardSensor();
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        } else if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Draws the Paddle on the Draw Surface.
     * Paddle utilizes the delegated Block object to do this.
     * @param surface - A DrawSurface object to draw on
     */
    @Override
    public void drawOn(DrawSurface surface) {
        this.delegatedBlock.drawOn(surface);
    }

    /**
     * Returns Paddle's rectangle using the delegated Block.
     * Used to calculate potential collisions.
     * @return Paddle's rectangle.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.delegatedBlock.getCollisionRectangle();
    }

    /**
     * Notifies Paddle that a Ball has hit it.
     * If the Ball collided with Paddle from the top, it bounces in a different angle, depending on the region
     * of the collision.
     * @param collisionPoint - The point of the collision
     * @param currentVelocity - The balls current velocity
     * @return - The new velocity of the ball.
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx(), dy = currentVelocity.getDy();
        // First check if this a horizontal collision
        if (Point.threshold(collisionPoint.getX(), this.delegatedBlock.getUpperLeft().getX())
                || Point.threshold(collisionPoint.getX(),
                        this.delegatedBlock.getUpperLeft().getX() + this.delegatedBlock.getWidth())) {
            // Collision is horizontal
            dx = -1 * currentVelocity.getDx();
            return new Velocity(dx, dy);
        }

        // Otherwise, this is a vertical collision, and we must calculate in which region the collision happened.
        int region = this.getCollisionRegionMultiplier(collisionPoint.getX());
        if (region == 3) {
            // The collision is in region three. In this case, we return the same velocity as a regular Block would,
            // so we will simply delegate this to our Block attribute.
            return this.delegatedBlock.hit(collisionPoint, currentVelocity);
        }

        // We are adding 180 degrees to the region multiple of 30, as the vertical axis is flipped in the computer GUI.
        double angle = 180 + region * 30;
        double speed = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)); // Pythagorean theorem for calculating the speed.
        return Velocity.fromAngleAndSpeed(angle, speed);
    }

    /**
     * Adds Paddle to the Game.
     * @param g - The Game object representing the game we are currently playing.
     */
    @Override
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * Calculates the region where the Ball collided with Paddle.
     * @param collisionX - The X coordinate of the Ball's collision with Paddle.
     * @return The value representing the region in which the collision has occurred.
     */
    private int getCollisionRegionMultiplier(double collisionX) {
        /*
         * Check for each region, starting from left to right, if the ball's collision is in that region.
         * If it is, return a multiplier associated with that region. This will be used to calculate the angle that
         * the ball's velocity should change to.
         */
        int i;
        for (i = 1; i <= 5; i++) {
            if (Point.greaterThanOrEqualThreshold(collisionX, this.delegatedBlock.getUpperLeft().getX() + (((i - 1)
                    * this.delegatedBlock.getWidth()) / 5))
                    && Point.lessThanOrEqualThreshold(collisionX, this.delegatedBlock.getUpperLeft().getX()
                            + (i * this.delegatedBlock.getWidth() / 5))) {
                return i;
            }
        }
        return i;
    }
}
