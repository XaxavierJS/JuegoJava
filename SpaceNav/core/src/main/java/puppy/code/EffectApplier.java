package puppy.code;

public interface EffectStrategy {
    void applyEffect(PantallaJuego gameScreen);
}


package puppy.code;

public class SlowAsteroidsEffect implements EffectStrategy {
    private static final float SLOW_DURATION = 5.0f;

    @Override
    public void applyEffect(PantallaJuego gameScreen) {
        gameScreen.slowDownAsteroids(SLOW_DURATION);
    }
}


package puppy.code;

public class TripleShotEffect implements EffectStrategy {

    @Override
    public void applyEffect(PantallaJuego gameScreen) {
        gameScreen.getNave().enableTripleShot();
    }
}


package puppy.code;

public class DestroyAsteroidsEffect implements EffectStrategy {

    @Override
    public void applyEffect(PantallaJuego gameScreen) {
        gameScreen.destruirAsteroides();
    }
}