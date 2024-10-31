package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BlueStar extends PowerUp{
	public BlueStar(int x, int y) {
		super(x, y, new Texture(Gdx.files.internal("BlueStar.png")));
	}
	
	@Override
	public void applyEffect(PantallaJuego gameScreen) {
		System.out.println("Efecto de BlueStar aplicado");
	    gameScreen.getNave().enableTripleShot(); // Activa el disparo triple
	}
}
