package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class PowerUp implements EffectApplier {

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

    @Override
    public abstract void applyEffect(PantallaJuego gameScreen);

    public boolean checkCollision(Nave4 nave) {
        // Check collision with the ship
        return nave.getBounds().overlaps(new Rectangle(x, y, texture.getWidth(), texture.getHeight()));
    }
}
