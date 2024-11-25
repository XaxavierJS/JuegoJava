package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

public class Ball2 extends GameObject{
	private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private float speedMultiplier = 1.0f;

    public Ball2(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
    	
    	super(x, y, new Sprite(tx));

        // Validar que el borde de la esfera no quede fuera
        if (x - size < 0) this.x = x + size;
        if (x + size > Gdx.graphics.getWidth()) this.x = x - size;

        this.y = y;
        if (y - size < 0) this.y = y + size;
        if (y + size > Gdx.graphics.getHeight()) this.y = y - size;

        spr.setPosition(this.x, this.y);

        // Guardar velocidades originales
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    
    @Override
    protected void move() {
        x += xSpeed * speedMultiplier;
        y += ySpeed * speedMultiplier;

        if (x + xSpeed < 0 || x + xSpeed + spr.getWidth() > Gdx.graphics.getWidth())
            xSpeed = -xSpeed;
        if (y + ySpeed < 0 || y + ySpeed + spr.getHeight() > Gdx.graphics.getHeight())
            ySpeed = -ySpeed;

        spr.setPosition(x, y);
    }

    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    } 
    
    @Override
    public void handleCollision(GameObject other) {
    	if (checkCollision(other)) {
            if (checkCollision(other) && other instanceof Ball2) {
            	Ball2 otherBall = (Ball2) other;
                int tempXSpeed = this.xSpeed;
                this.xSpeed = otherBall.xSpeed;
                otherBall.xSpeed = tempXSpeed;

                int tempYSpeed = this.ySpeed;
                this.ySpeed = otherBall.ySpeed;
                otherBall.ySpeed = tempYSpeed;
            }
        }
    }

    // Getters y Setters para velocidades
    public int getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
    
    public void setSpeedMultiplier(float multiplier) {
    	this.speedMultiplier = multiplier;
    }
}
