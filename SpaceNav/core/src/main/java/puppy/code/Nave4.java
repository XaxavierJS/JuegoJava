package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Nave4 extends GameObject{
	
	private boolean destruida = false;
    private int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax=50;
    private int tiempoHerido;
    private int direccion = 0;
    private float velocidadConstante = 5;
     
    private static Nave4 instance;
    private GameObjectFactory factory;
    private boolean tripleShotEnabled = false;
    
    private Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala, int vidas, GameObjectFactory  factory) {
    	super(x, y, new Sprite(tx));
    	
    	this.vidas = vidas;
    	this.sonidoHerido = soundChoque;
    	this.soundBala = soundBala;
    	this.txBala = txBala;
    	spr.setBounds(x, y, 45, 45);
        this.factory = factory;
    }
    
    public static Nave4 getInstance(int x, int y, Texture tx, Sound soundChoque,
                                Texture txBala, Sound soundBala, int vidas,
                                GameObjectFactory factory) {
    if (instance == null) {
        instance = new Nave4(x, y, tx, soundChoque, txBala, soundBala, vidas, factory);
    }
    return instance;
}

    
    public Nave4 getInstance() {
    	return instance;
    }
    
    public void enableTripleShot() {
        this.tripleShotEnabled = true;
        System.out.println("¡Triple disparo activado!");
    }
  


    
    public void move() {
        float x = spr.getX();
        float y = spr.getY();

        // Reiniciar velocidad en cada frame para que no acumule movimiento
        xVel = 0;
        yVel = 0;

        if (!herido) {
            // Cambiar velocidad en función de teclas mantenidas
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                xVel = -velocidadConstante;
                direccion = 3; // Apunta izquierda
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                xVel = velocidadConstante;
                direccion = 1; // Apunta derecha
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                yVel = -velocidadConstante;
                direccion = 2; // Apunta abajo
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                yVel = velocidadConstante;
                direccion = 0; // Apunta arriba
            }

            // Mantener la nave dentro de los bordes de la ventana sin rotación
            float newX = x + xVel;
            float newY = y + yVel;
            
            if (newX < 0) {
                newX = 0;
            } else if (newX + spr.getWidth() > Gdx.graphics.getWidth()) {
                newX = Gdx.graphics.getWidth() - spr.getWidth();
            }
            if (newY < 0) {
                newY = 0;
            } else if (newY + spr.getHeight() > Gdx.graphics.getHeight()) {
                newY = Gdx.graphics.getHeight() - spr.getHeight();
            }

            // Establecer la nueva posición de la nave sin rotación
            spr.setPosition(newX, newY);
        } else {
            spr.setX(spr.getX() + MathUtils.random(-2, 2));
            spr.setX(x);
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }

        /*if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            float balaX = spr.getX() + spr.getWidth() / 2 - 5;
            float balaY = spr.getY() + spr.getHeight() / 2 - 5;
            
            if (tripleShotEnabled) { // Disparo en las tres direcciones
            	Bullet balaCentral = new Bullet(balaX, balaY, 0, 3, txBala);
            	juego.agregarBala(balaCentral);
            	Bullet balaIzquierda = new Bullet(balaX, balaY, -3, 3, txBala);
            	juego.agregarBala(balaIzquierda);
            	Bullet balaDerecha = new Bullet(balaX, balaY, 3, 3, txBala);
            	juego.agregarBala(balaDerecha);
            }
            
            else { // Disparo en la dirección actual
                int balaXVel = 0, balaYVel = 0;
                switch (direccion) {
                case 0: balaYVel = 3; break;    // Disparo arriba
                case 1: balaXVel = 3; break;    // Disparo derecha
                case 2: balaYVel = -3; break;   // Disparo abajo
                case 3: balaXVel = -3; break;   // Disparo izquierda
              }
                Bullet bala = new Bullet(balaX, balaY, balaXVel, balaYVel, txBala);
                juego.agregarBala(bala);
                
           }
            
            soundBala.play();
        }*/
    }

    
    public void disparar(PantallaJuego juego) {
        float balaX = spr.getX() + spr.getWidth() / 2 - 5;
        float balaY = spr.getY() + spr.getHeight() / 2 - 5;

        int balaXVel = 0, balaYVel = 0;

        // Configurar velocidades según la dirección
        switch (direccion) {
            case 0: balaYVel = 3; break;    // Disparo hacia arriba
            case 1: balaXVel = 3; break;    // Disparo hacia la derecha
            case 2: balaYVel = -3; break;   // Disparo hacia abajo
            case 3: balaXVel = -3; break;   // Disparo hacia la izquierda
        }

        // Crear la bala central
        Bullet bullet = (Bullet) factory.createBullet();
        bullet.setPosition(balaX, balaY);
        bullet.setSpeed(balaXVel, balaYVel);
        juego.agregarBala(bullet);

        if (tripleShotEnabled) {
            // Crear balas adicionales para el disparo triple
            Bullet leftBullet = (Bullet) factory.createBullet();
            leftBullet.setPosition(balaX, balaY);
            leftBullet.setSpeed(balaXVel - 1, balaYVel - 1);

            Bullet rightBullet = (Bullet) factory.createBullet();
            rightBullet.setPosition(balaX, balaY);
            rightBullet.setSpeed(balaXVel + 1, balaYVel + 1);

            juego.agregarBala(leftBullet);
            juego.agregarBala(rightBullet);
        }

        soundBala.play();
    }
      
    @Override
    public void handleCollision(GameObject other) {
        if (other instanceof Ball2 && !herido) {
            vidas--;
            herido = true; // Activar estado de invulnerabilidad temporal
            tiempoHerido = tiempoHeridoMax;
            sonidoHerido.play();
            if (vidas <= 0) {
                destruida = true;
            }
        }
    }	
    public void disableTripleShot() {
        this.tripleShotEnabled = false;
    }
    
    public Rectangle getBounds() {
        return spr.getBoundingRectangle();
    }
    
    public boolean estaDestruido() {
       return !herido && destruida;
    }
    public boolean estaHerido() {
 	   return herido;
    }
    
    public static void resetInstance() {
    	instance = null;
    }
    
    public int getVidas() {return vidas;}
    //public boolean isDestruida() {return destruida;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidas(int vidas2) {vidas = vidas2;}
}
