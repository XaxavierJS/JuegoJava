package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MagicStar extends PowerUp {

    public MagicStar(float x, float y) {
        // Llama al constructor de PowerUp con la textura específica de MagicStar
        super(x, y, new Texture(Gdx.files.internal("MagicStar.png")));
    }

    @Override
    public void applyEffect(PantallaJuego gameScreen) {
        // Imprime un mensaje para confirmar que el efecto se ha activado
        System.out.println("¡Efecto de MagicStar aplicado! Todos los asteroides han sido destruidos.");

        // Llama al método que destruye todos los asteroides en el juego
        gameScreen.destruirAsteroides();
    }
}
