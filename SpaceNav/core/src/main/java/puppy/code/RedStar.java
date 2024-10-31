package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class RedStar extends PowerUp {
    private static final float SLOW_DURATION = 5.0f; // Duración en segundos
    private boolean active = false;

    public RedStar(int x, int y) {
        super(x, y, new Texture("Estrella_Roja.png")); // Usa la textura de la Red Star
    }

    @Override
    public void applyEffect(PantallaJuego gameScreen) {
        if (!active) {
            active = true;
            gameScreen.slowDownAsteroids(SLOW_DURATION);
        }
    }
}
