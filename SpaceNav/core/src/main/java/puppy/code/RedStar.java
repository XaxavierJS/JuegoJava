package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class RedStar extends PowerUp {

    public RedStar(float x, float y) {
        super(x, y, new Texture(Gdx.files.internal("Estrella_Roja2.png")), new SlowAsteroidsEffect());
    }

    @Override
    protected void onActivate() {
        System.out.println("RedStar activada en la posición (" + x + ", " + y + ").");
    }
}


package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BlueStar extends PowerUp {

    public BlueStar(float x, float y) {
        super(x, y, new Texture(Gdx.files.internal("BlueStar.png")), new TripleShotEffect());
    }

    @Override
    protected void onActivate() {
        System.out.println("BlueStar activada. ¡Prepárate para disparos triples!");
    }
}


package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MagicStar extends PowerUp {

    public MagicStar(float x, float y) {
        super(x, y, new Texture(Gdx.files.internal("MagicStar.png")), new DestroyAsteroidsEffect());
    }

    @Override
    protected void onActivate() {
        System.out.println("MagicStar activada. ¡Destruyendo todos los asteroides!");
    }
}