package puppy.code;

public class DestroyAsteroidsEffect implements EffectStrategy {

    @Override
    public void applyEffect(PantallaJuego gameScreen) {
        gameScreen.destruirAsteroides();
    }
}