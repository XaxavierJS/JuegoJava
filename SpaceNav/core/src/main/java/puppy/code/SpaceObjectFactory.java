package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.Random;

public class SpaceObjectFactory implements GameObjectFactory {
    @Override
    public GameObject createAsteroid() {
        return new Ball2(
            new Random().nextInt(Gdx.graphics.getWidth()),
            new Random().nextInt(Gdx.graphics.getHeight() - 50) + 50,
            20 + new Random().nextInt(10),
            3 + new Random().nextInt(3),
            3 + new Random().nextInt(3),
            new Texture(Gdx.files.internal("aGreyMedium4.png"))
        );
    }

    @Override
    public GameObject createBullet() {
        return new Bullet(
            0, 0, // Coordenadas iniciales; estas serán configuradas por la nave
            5, 5, // Velocidad
            new Texture(Gdx.files.internal("Rocket2.png"))
        );
    }

    @Override
    public PowerUp createPowerUp() {
        Random random = new Random();
        int type = random.nextInt(3); // Elegir un tipo de power-up al azar
        switch (type) {
            case 0:
                return new RedStar(random.nextInt(Gdx.graphics.getWidth()), random.nextInt(Gdx.graphics.getHeight()));
            case 1:
                return new BlueStar(random.nextInt(Gdx.graphics.getWidth()), random.nextInt(Gdx.graphics.getHeight()));
            default:
                return new MagicStar(random.nextInt(Gdx.graphics.getWidth()), random.nextInt(Gdx.graphics.getHeight()));
        }
    }
}
