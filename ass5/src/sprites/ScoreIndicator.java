package sprites;

import biuoop.DrawSurface;
import play.Counter;
import geometry.Point;
import play.Game;

import java.awt.Color;

public class ScoreIndicator implements Sprite {
    private final Counter score;
    private final Point upperLeft;
    private final Color color;
    private final int fontSize;

    public ScoreIndicator(Point upperLeft, Counter score) {
        this.score = score;
        this.upperLeft = upperLeft;
        this.color = Color.BLACK;
        this.fontSize = 20;
    }

    @Override
    public void drawOn(DrawSurface d) {
        int x = (int)this.upperLeft.getX(), y = (int)this.upperLeft.getY();
        String text = "Score: " + this.score.getValue();
        int fontSize = this.fontSize;
        d.setColor(this.color);
        d.drawText(x, y, text, fontSize);
    }

    @Override
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    @Override
    public void timePassed() {

    }


}
