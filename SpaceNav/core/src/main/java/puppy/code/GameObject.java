package puppy.code;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {
	protected int xSpeed;
	protected int ySpeed;
	protected Sprite spr;
	
	public GameObject(int xSpeed, int ySpeed, Sprite spr) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.spr = spr;
        this.spr.setPosition(xSpeed, ySpeed);
    }
	
	public final void update() {
		move();
	}
	
	protected abstract void move();
	protected abstract void handleCollision(GameObject other);
	
	public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }	
	
	public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
	
	public boolean checkCollision(GameObject other) {
        return this.getArea().overlaps(other.getArea());
    }
}
