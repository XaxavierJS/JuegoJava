package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PowerUp {
	
	protected float x;
	protected float y;
	protected Texture texture;
	
	public PowerUp(float x, float y, Texture texture) {
		this.x = x;
		this.y = y;
		this.texture = texture;
	}
	
	public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }
	
	public void applyEffect(PantallaJuego gameScreen) {}
	
	public boolean checkCollision(Nave4 nave) {
        // Verifica colisión con la nave
        return nave.getBounds().overlaps(new Rectangle(x, y, texture.getWidth(), texture.getHeight()));
    }
}
