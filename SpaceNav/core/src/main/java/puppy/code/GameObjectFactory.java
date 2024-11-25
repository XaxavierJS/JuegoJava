package puppy.code;

public interface GameObjectFactory {
    GameObject createAsteroid();
    GameObject createBullet();
    PowerUp createPowerUp();
}
