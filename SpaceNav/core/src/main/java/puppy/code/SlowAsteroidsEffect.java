
package puppy.code;

public class SlowAsteroidsEffect implements EffectStrategy {
    private static final float SLOW_DURATION = 5.0f;

    @Override
    public void applyEffect(PantallaJuego gameScreen) {
        gameScreen.slowDownAsteroids(SLOW_DURATION);
    }
}