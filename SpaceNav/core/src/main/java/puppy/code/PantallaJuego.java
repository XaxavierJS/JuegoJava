package puppy.code;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PantallaJuego implements Screen {

    private GameObjectFactory factory;
	private SpaceNavigation game;
	private OrthographicCamera camera;	
	private SpriteBatch batch;
	private Sound explosionSound;
	private Sound powerUpSound;
	private Music gameMusic;
	private int score;
	private int ronda;
	private int velXAsteroides; 
	private int velYAsteroides; 
	private int cantAsteroides;
	
	private Nave4 nave;
	private  ArrayList<Ball2> balls1 = new ArrayList<>();
	private  ArrayList<Ball2> balls2 = new ArrayList<>();
	private  ArrayList<Bullet> balas = new ArrayList<>();
	private ArrayList<PowerUp> powerUps = new ArrayList<>();
	private float slowDownTimeRemaining = 0;
	
	private float powerUpSpawnTimer = 5f;
    private float powerUpSpawnInterval = 5f;
    
    public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,  
                     int velXAsteroides, int velYAsteroides, int cantAsteroides) {
    this.game = game;
    this.ronda = ronda;
    this.score = score;
    this.velXAsteroides = velXAsteroides;
    this.velYAsteroides = velYAsteroides;
    this.cantAsteroides = cantAsteroides;

    batch = game.getBatch();
    camera = new OrthographicCamera();    
    camera.setToOrtho(false, 800, 640);

    this.factory = new SpaceObjectFactory(); // Inicializar la fábrica

    initializeAudio();

    if (nave == null) {
        createNave(vidas, this.factory); // Crear la nave con la fábrica
    }

    crearAsteroides();
}



    private void initializeAudio() {
        // Configurar audio
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        explosionSound.setVolume(1,0.5f);
        powerUpSound = Gdx.audio.newSound(Gdx.files.internal("powerUpSound.mp3"));
        powerUpSound.setVolume(1,0.5f);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav")); 
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.01f);
        gameMusic.play();
    }
    
    private void createNave(int vidas, GameObjectFactory factory) {
        nave = Nave4.getInstance(
            Gdx.graphics.getWidth() / 2 - 50, 
            30, 
            new Texture(Gdx.files.internal("MainShip3.png")),
            Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
            new Texture(Gdx.files.internal("Rocket2.png")),
            Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")), 
            vidas,
            factory
        );
    }
    
    
    

    private void crearAsteroides() {
        for (int i = 0; i < cantAsteroides; i++) {
            Ball2 asteroid = (Ball2) factory.createAsteroid();
            balls1.add(asteroid);
            balls2.add(asteroid);
        }
    }    
    
    public void dibujaEncabezado() {
        CharSequence str = "Vidas: "+nave.getVidas()+" Ronda: "+ronda;
        game.getFont().getData().setScale(2f);        
        game.getFont().draw(batch, str, 10, 30);
        game.getFont().draw(batch, "Score:"+this.score, Gdx.graphics.getWidth()-150, 30);
        game.getFont().draw(batch, "HighScore:"+game.getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
    }
    
    public void slowDownAsteroids(float duration) {
        slowDownTimeRemaining = duration;
        for (Ball2 asteroid : balls1) {
            asteroid.setSpeedMultiplier(0.5f); // Reduce la velocidad a la mitad
        }
    }

    public void restoreAsteroidSpeed() {
        for (Ball2 asteroid : balls1) {
            asteroid.setSpeedMultiplier(1.0f); // Restaura la velocidad normal
        }
    }
    
    public void destruirAsteroides() {
        balls1.clear();
        balls2.clear();
        avanzarARondaSiguiente();
    }

    private void avanzarARondaSiguiente() {
        nave.disableTripleShot();

        if (nave == null) {
            createNave(nave.getVidas(), factory); // Pasa la fábrica como segundo argumento
        }

        Screen ss = new PantallaJuego(game, ronda + 1, nave.getVidas(), score,
                velXAsteroides + 3, velYAsteroides + 3, cantAsteroides + 10);
        ss.resize(1200, 800);
        game.setScreen(ss);
        dispose();
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        dibujaEncabezado(); // Dibujar encabezado (vidas, puntuación, etc.)

        // Detectar disparo
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            nave.disparar(this);
        }
        nave.move();
        
        procesarPowerUps();
        
        powerUpSpawnTimer -= delta;
        if (powerUpSpawnTimer <= 0) {
            spawnPowerUp(); // Generar un nuevo power-up
        }
        
        // Actualizar balas
        actualizarBalas();

        // Actualizar asteroides
        actualizarAsteroides();

        // Verificar colisiones de la nave
        checkColisionNave();

        // Dibujar nave
        nave.draw(batch);

        // Verificar si la nave está destruida
        if (nave.estaDestruido()) {
            if (score > game.getHighScore()) {
                game.setHighScore(score);
            }
            Screen ss = new PantallaGameOver(game);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }

        batch.end();

        // Verificar condiciones para avanzar de ronda
        if (balls1.size() == 0) {
            avanzarARondaSiguiente();
        }
    }

    private void spawnPowerUp() {
        PowerUp powerUp = factory.createPowerUp(); // Crear power-up usando la fábrica
        powerUps.add(powerUp); // Añadirlo a la lista de power-ups
        powerUpSpawnTimer = powerUpSpawnInterval; // Reiniciar el temporizador de generación
    }
    

    private void actualizarBalas() {
        for (int i = 0; i < balas.size(); i++) {
            Bullet b = balas.get(i);
            b.update();
            for (int j = 0; j < balls1.size(); j++) {    
                if (b.checkCollision(balls1.get(j))) {          
                    explosionSound.play();
                    balls1.remove(j);
                    balls2.remove(j);
                    j--;
                    score += 10;
                }         
            }
            b.draw(batch);
            if (b.isDestroyed()) {
                balas.remove(b);
                i--;
            }
        }
    }

    private void actualizarAsteroides() {
        for (Ball2 ball : balls1) {
            ball.update();
            ball.draw(batch);
        }
        for (int i = 0; i < balls1.size(); i++) {
            Ball2 ball1 = balls1.get(i);
            for (int j = i + 1; j < balls2.size(); j++) {
                Ball2 ball2 = balls2.get(j);
                ball1.checkCollision(ball2);
            }
        }
    }
    
    private void checkColisionNave() {
        for (int i = 0; i < balls1.size(); i++) {
            Ball2 asteroid = balls1.get(i);
            if (nave.checkCollision(asteroid)) {
                nave.handleCollision(asteroid);
                balls1.remove(i); // Elimina el asteroide tras la colisión
                i--;
            }
        }
    }

    private void procesarPowerUps() {
        for (PowerUp powerUp : powerUps) {
            powerUp.draw(batch); 
            if (powerUp.checkCollision(nave)) {
                System.out.println("¡PowerUp recogido!");
                powerUp.applyEffect(this);
                powerUps.remove(powerUp); 
                powerUpSound.play();
                break;
            }
        }
    }
    
    public void aplicarPowerUp(PowerUp powerUp) {
        powerUp.applyEffect(this);
    }

    @Override
    public void show() {
        gameMusic.play();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        explosionSound.dispose();
        gameMusic.dispose();
    }

    public Nave4 getNave() {
        return nave;
    }

    public ArrayList<Bullet> getBalas() {
        return balas;
    }

    public void setVelocidadAsteroides(int velX, int velY) {
        this.velXAsteroides = velX;
        this.velYAsteroides = velY;
    }
    
    public boolean agregarBala(Bullet bb) {
    	return balas.add(bb);
    }
    
    public void checkHerido() {
    	if (!nave.estaHerido()) {
		      // colisiones entre balas y asteroides y su destruccion  
	    	  for (int i = 0; i < balas.size(); i++) {
		            Bullet b = balas.get(i);
		            b.update();
		            for (int j = 0; j < balls1.size(); j++) {    
		              if (b.checkCollision(balls1.get(j))) {          
		            	 explosionSound.play();
		            	 balls1.remove(j);
		            	 balls2.remove(j);
		            	 j--;
		            	 score +=10;
		              }   	  
		  	        }
		                
		            b.draw(batch);
		            if (b.isDestroyed()) {
		                balas.remove(b);
		                i--; //para no saltarse 1 tras eliminar del arraylist
		            }
		      }
    	}
    } 	
}
