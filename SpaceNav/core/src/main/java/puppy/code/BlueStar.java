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