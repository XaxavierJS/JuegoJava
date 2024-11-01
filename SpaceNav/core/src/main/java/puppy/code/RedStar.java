package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class RedStar extends PowerUp {
    private static final float SLOW_DURATION = 5.0f; // Duración en segundos
    private boolean active = false;

    public RedStar(float x, float y) {
        // Llama al constructor de PowerUp con la textura específica de RedStar
        super(x, y, new Texture(Gdx.files.internal("Estrella_Roja2.png")));
    }

    @Override
    public void applyEffect(PantallaJuego gameScreen) {
        // Aplica el efecto de ralentización una sola vez
        if (!active) {
            active = true;
            gameScreen.slowDownAsteroids(SLOW_DURATION); // Ralentiza los asteroides por SLOW_DURATION segundos
            System.out.println("Efecto de RedStar aplicado: asteroides ralentizados por " + SLOW_DURATION + " segundos.");
        }
    }
}
