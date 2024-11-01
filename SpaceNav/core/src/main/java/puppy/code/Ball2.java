package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

public class Ball2 {
	private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    /*private int xSpeedOriginal;
    private int ySpeedOriginal;*/
    private float speedMultiplier = 1.0f;
    private Sprite spr;

    public Ball2(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        spr = new Sprite(tx);
        this.x = x;

        // Validar que el borde de la esfera no quede fuera
        if (x - size < 0) this.x = x + size;
        if (x + size > Gdx.graphics.getWidth()) this.x = x - size;

        this.y = y;
        if (y - size < 0) this.y = y + size;
        if (y + size > Gdx.graphics.getHeight()) this.y = y - size;

        spr.setPosition(x, y);

        // Guardar velocidades originales
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        /*this.xSpeedOriginal = xSpeed;
        this.ySpeedOriginal = ySpeed;*/
    }
    
    public void update() {
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

    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    public void checkCollision(Ball2 b2) {
        if (spr.getBoundingRectangle().overlaps(b2.spr.getBoundingRectangle())) {
            if (xSpeed == 0) xSpeed += b2.xSpeed / 2;
            if (b2.xSpeed == 0) b2.xSpeed += xSpeed / 2;
            //xSpeed = -xSpeed;
            b2.xSpeed = -b2.xSpeed;

            if (ySpeed == 0) ySpeed += b2.ySpeed / 2;
            if (b2.ySpeed == 0) b2.ySpeed += ySpeed / 2;
            //ySpeed = -ySpeed;
            b2.ySpeed = -b2.ySpeed;
        }
    }

    /*public void reducirVelocidadTemporalmente(float factor, float duration) {
        xSpeed *= factor;
        ySpeed *= factor;

        // Programar la restauración de la velocidad original
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                restaurarVelocidad();
            }
        }, duration);
    }

    private void restaurarVelocidad() {
        xSpeed = xSpeedOriginal;
        ySpeed = ySpeedOriginal;
    }*/

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
