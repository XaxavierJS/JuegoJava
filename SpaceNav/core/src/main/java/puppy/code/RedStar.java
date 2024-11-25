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


