package puppy.code;

public class TripleShotEffect implements EffectStrategy {

    @Override
    public void applyEffect(PantallaJuego gameScreen) {
        gameScreen.getNave().enableTripleShot();
    }
}