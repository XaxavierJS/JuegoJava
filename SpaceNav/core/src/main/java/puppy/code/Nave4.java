package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;


public class Nave4 {
	
	private boolean destruida = false;
    private int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax=50;
    private int tiempoHerido;
    private int direccion = 0;
    private float velocidadConstante = 5;
    
    private boolean tripleShotEnabled = false;
    
    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala, int vidas) {
    	this.vidas = vidas;
    	sonidoHerido = soundChoque;
    	this.soundBala = soundBala;
    	this.txBala = txBala;
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	//spr.setOriginCenter();
    	spr.setBounds(x, y, 45, 45);

    }
    
    public void enableTripleShot() {
        this.tripleShotEnabled = true;
        System.out.println("¡Triple disparo activado!");
    }
    
    /*public void shoot(ArrayList<Bullet> bullets) {
        if (tripleShotEnabled) {
            // Misil central: va directamente hacia arriba
            bullets.add(new Bullet(spr.getX(), spr.getY(), 0, 3, txBala));
            
            // Misil izquierdo: va hacia arriba y un poco hacia la izquierda
            bullets.add(new Bullet(spr.getX(), spr.getY(), -3, 3, txBala));
            
            // Misil derecho: va hacia arriba y un poco hacia la derecha
            bullets.add(new Bullet(spr.getX(), spr.getY(), 3, 3, txBala));
        } else {
            // Disparo único en línea recta hacia arriba
            bullets.add(new Bullet(spr.getX(), spr.getY(), 0, 5, txBala));
        }
    }*/


    
    public void draw(SpriteBatch batch, PantallaJuego juego) {
        float x = spr.getX();
        float y = spr.getY();
        float velocidadConstante = 5; // Ajusta la velocidad constante a tu preferencia.

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
            spr.draw(batch);
        } else {
            spr.setX(spr.getX() + MathUtils.random(-2, 2));
            spr.draw(batch);
            spr.setX(x);
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
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
        }
    }

      
    public boolean checkCollision(Ball2 b) {
        if(!herido && b.getArea().overlaps(spr.getBoundingRectangle())){
        	// rebote
            if (xVel ==0) xVel += b.getXSpeed()/2;
            if (b.getXSpeed() ==0) b.setXSpeed(b.getXSpeed() + (int)xVel/2);
            //xVel = - xVel;
            b.setXSpeed(-b.getXSpeed());
            
            if (yVel ==0) yVel += b.getySpeed()/2;
            if (b.getySpeed() ==0) b.setySpeed(b.getySpeed() + (int)yVel/2);
            //yVel = - yVel;
            b.setySpeed(- b.getySpeed());
            // despegar sprites
      /*      int cont = 0;
            while (b.getArea().overlaps(spr.getBoundingRectangle()) && cont<xVel) {
               spr.setX(spr.getX()+Math.signum(xVel));
            }   */
        	//actualizar vidas y herir
            vidas--;
            herido = true;
  		    tiempoHerido=tiempoHeridoMax;
  		    sonidoHerido.play();
            if (vidas<=0) 
          	    destruida = true; 
            return true;
        }
        return false;
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
    
    public int getVidas() {return vidas;}
    //public boolean isDestruida() {return destruida;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidas(int vidas2) {vidas = vidas2;}
}
