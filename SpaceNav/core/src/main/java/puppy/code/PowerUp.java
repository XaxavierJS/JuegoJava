package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class PowerUp {
    protected float x;
    protected float y;
    protected Texture texture;
    protected EffectStrategy effectStrategy;

    public PowerUp(float x, float y, Texture texture, EffectStrategy effectStrategy) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.effectStrategy = effectStrategy;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void applyEffect(PantallaJuego gameScreen) {
        onActivate();

        if (effectStrategy != null) {
            effectStrategy.applyEffect(gameScreen);
        }
    }


    public boolean checkCollision(Nave4 nave) {
        return nave.getBounds().overlaps(new Rectangle(x, y, texture.getWidth(), texture.getHeight()));
    }
}