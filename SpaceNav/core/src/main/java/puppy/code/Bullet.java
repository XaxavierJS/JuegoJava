package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Bullet extends GameObject{

	private int xSpeed;
	private int ySpeed;
	private boolean destroyed = false; 
	    
    public Bullet(float x, float y, int xSpeed, int ySpeed, Texture tx) {
    	super((int) x, (int) y, new Sprite(tx));
    	
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        
        spr.setPosition(x, y);
    }
    public void setPosition(float x, float y) {
        spr.setPosition(x, y);
    }
    
    @Override
    protected void move() {
    	float newX = spr.getX() + xSpeed;
        float newY = spr.getY() + ySpeed;
        
        if (newX < 0 || newX + spr.getWidth() > Gdx.graphics.getWidth() ||
        		newY < 0 || newY + spr.getHeight() > Gdx.graphics.getHeight()) {
            destroyed = true;
        }
        spr.setPosition(newX, newY);
    }
    
    @Override
    protected void handleCollision(GameObject other) {
    	if (other instanceof Ball2) {
            Ball2 ball = (Ball2) other;

            // Verifica la colisión usando el método genérico checkCollision de GameObject
            if (checkCollision(ball)) {
                // Se destruyen ambos (o al menos esta bala)
                this.destroyed = true;
            }
        }
    }
    
    public boolean isDestroyed() {return destroyed;}
	
}
