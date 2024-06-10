import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

public class Paddle implements Sprite, Collidable {

    private KeyboardSensor keyboard;
    private final double movementSpeed = 10.0; // Determines how far the Paddle will move horizontally by keypress.
    private GUI gui;
    private final Block delegatedBlock;

    public Paddle() {
        this.delegatedBlock = new Block(new Point(374, 560), 80, 30);
    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void moveLeft() {
        double x = this.delegatedBlock.upperLeft.getX() - movementSpeed;
        double y = this.delegatedBlock.upperLeft.getY();
        if (x > 10) {
            this.delegatedBlock.upperLeft = new Point(x, y);
        } else {
            this.delegatedBlock.upperLeft = new Point(790 - this.delegatedBlock.getWidth(), y);
        }
    }

    public void moveRight() {
        double x = this.delegatedBlock.upperLeft.getX() + movementSpeed;
        double y = this.delegatedBlock.upperLeft.getY();
        if (x < 790 - this.delegatedBlock.getWidth()) {
            this.delegatedBlock.upperLeft = new Point(x, y);
        } else {
            this.delegatedBlock.upperLeft = new Point(10, y);
        }
    }


    @Override
    public void timePassed() {
        this.keyboard = this.gui.getKeyboardSensor();
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        } else if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    @Override
    public void drawOn(DrawSurface surface) {
        this.delegatedBlock.drawOn(surface);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.delegatedBlock.getCollisionRectangle();
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx(), dy = currentVelocity.getDy();
        // First check if this a horizontal collision
        if (Point.threshold(collisionPoint.getX(), this.delegatedBlock.getUpperLeft().getX()) ||
                Point.threshold(collisionPoint.getX(),
                        this.delegatedBlock.getUpperLeft().getX() + this.delegatedBlock.getWidth())) {
            // Collision is horizontal
            dx = -1 * currentVelocity.getDx();
            return new Velocity(dx, dy);
        }

        // Otherwise, this is a vertical collision, and we must calculate in which region the collision happened.
        int region = this.getCollisionRegionMultiplier(collisionPoint.getX());
        //DEBUG
        System.out.println("Paddle collision region: " + region);
        //DEBUG
        if (region == 3) {
            // The collision is in region three. In this case, we return the same velocity as a regular Block would,
            // so we will simply delegate this to our Block attribute.
            return this.delegatedBlock.hit(collisionPoint, currentVelocity);
        }

        // We are adding 180 degrees to the region multiple of 30, as the vertical axis is flipped in the computer GUI.
        double angle = 180 + region * 30;
        System.out.println(angle);
        double speed = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)); // Pythagorean theorem for calculating the speed.
        return Velocity.fromAngleAndSpeed(angle, speed);
    }

    @Override
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    private int getCollisionRegionMultiplier(double collisionX) {
        /*
         * Check for each region, starting from left to right, if the ball's collision is in that region.
         * If it is, return a multiplier associated with that region. This will be used to calculate the angle that
         * the ball's velocity should change to.
         */
        for (int i = 1; i <= 5; i++) {
            //DEBUG
            System.out.println("Details A: CollisionX - " + collisionX + ", minFactor - " + (this.delegatedBlock.upperLeft.getX() + ((i - 1) * this.delegatedBlock.getWidth()) / 5));
            System.out.println("Details B: CollisionX - " + collisionX + ", maxFactor - " + this.delegatedBlock.upperLeft.getX() + (i * this.delegatedBlock.getWidth() / 5));
            //DEBUG
            if (Point.greaterThanOrEqualThreshold(collisionX, this.delegatedBlock.upperLeft.getX() + (((i - 1) * this.delegatedBlock.getWidth()) / 5)) &&
                    Point.lessThanOrEqualThreshold(collisionX, this.delegatedBlock.upperLeft.getX() +
                            (i * this.delegatedBlock.getWidth() / 5))) {
                return i;
            }
        }

        /*
         * If we reach here, there is very likely an error either with the ball's collision point calculation,
         * or this method was called at a wrong time. In this case, we prefer to turn this into a runtime exception
         * instead of returning some arbitrary value and force debugging of a runtime error that allows the game
         * to continue illegally.
         */
        throw new RuntimeException("Ball has not collided with any region on the Paddle. Wrong call to this function");
    }
}
