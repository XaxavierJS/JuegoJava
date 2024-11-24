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