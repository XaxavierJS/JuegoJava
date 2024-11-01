package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BlueStar extends PowerUp {
    private boolean active = false;

    public BlueStar(float x, float y) {
        // Llama al constructor de PowerUp con la textura específica de BlueStar
        super(x, y, new Texture(Gdx.files.internal("BlueStar.png")));
    }

    @Override
    public void applyEffect(PantallaJuego gameScreen) {
        // Imprime un mensaje de depuración para confirmar que el efecto se ha activado
        System.out.println("Efecto de BlueStar aplicado");

        // Aplica el efecto solo si no está activo, evita que se aplique múltiples veces
        if (!active) {
            active = true;
            gameScreen.getNave().enableTripleShot(); // Activa el disparo triple en la nave
        }
    }
}
