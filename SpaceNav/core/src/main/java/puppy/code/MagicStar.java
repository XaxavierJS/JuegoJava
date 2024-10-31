package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MagicStar extends PowerUp {
    public MagicStar(int x, int y) {
        super(x, y, new Texture(Gdx.files.internal("MagicStar.png"))); 
    }

    @Override
    public void applyEffect(PantallaJuego gameScreen) {
        System.out.println("¡Efecto de MagicStar aplicado! Todos los asteroides han sido destruidos.");
        gameScreen.destruirAsteroides(); // Método que destruye todos los asteroides
    }
}
