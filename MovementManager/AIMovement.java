package MovementManager;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import entityManager.TextureObject;

public class AIMovement {
    private final Random random = new Random();
    private float directionX;
    private float directionY;

    public AIMovement() {
        setRandomDirection(); // Initialize with a random diagonal direction
    }

    public void move(TextureObject textureObject, float screenWidth, float screenHeight) {
        float speed = textureObject.getSpeed() * Gdx.graphics.getDeltaTime();
        float newX = textureObject.getX() + directionX * speed;
        float newY = textureObject.getY() + directionY * speed;
        
        float entityWidth = textureObject.getWidth(); 
        float entityHeight = textureObject.getHeight(); 
        float halfEntityWidth = entityWidth / 2;
        float halfEntityHeight = entityHeight / 2;
        if (newX - halfEntityWidth < 0) {
            directionX *= -1;
            newX = halfEntityWidth;
        } else if (newX + halfEntityWidth > screenWidth) {
            directionX *= -1;
            newX = screenWidth - halfEntityWidth;
        }

        if (newY - halfEntityHeight < 0) {
            directionY *= -1;
            newY = halfEntityHeight;
        } else if (newY + halfEntityHeight > screenHeight) {
            directionY *= -1;
            newY = screenHeight - halfEntityHeight;
        }
        
       
        textureObject.setPosition(newX, newY);
        textureObject.setState("moving");
    }
    private void setRandomDirection() {
        do {
            directionX = random.nextBoolean() ? 1 : -1;
            directionY = random.nextBoolean() ? 1 : -1;
        } while (directionX == 0 && directionY == 0); 
    }
    
    public void setDirection(float newDirectionX, float newDirectionY) {
        this.directionX = newDirectionX;
        this.directionY = newDirectionY;
    }
    
    public float getDirectionX() {
    	return directionX;
    }
    
    public float getDirectionY() {
        return directionY;
    }
    
}
