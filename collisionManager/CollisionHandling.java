package collisionManager;

import java.util.List;

import com.badlogic.gdx.Gdx;

import MovementManager.AIMovement;
import entityManager.Entity;
import entityManager.MainC;
import entityManager.SideC;
import entityManager.TextureObject;
import soundManager.SoundManager;

public class CollisionHandling {
    private SoundManager soundManager;

    // Constructor to receive SoundManager instance
    public CollisionHandling(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    public void handleCollision(MainC circle, SideC square, CollisionStrategy collisionStrategy) {
        if (collisionStrategy.checkCollision(circle, square)) {
            if (soundManager != null) {
                soundManager.playCollisionSound();
            }
            resetEntityPosition(circle);
            resetEntityPosition(square);
        }
    }   
    

    public void handleOutOfBounds(Entity entity, float screenWidth, float screenHeight) {
        if (CollisionDetection.isEntityOutofBound(entity, screenWidth, screenHeight)) {
            entity.setX(Math.max(0, Math.min(entity.getX(), screenWidth)));
            entity.setY(Math.max(0, Math.min(entity.getY(), screenHeight)));
        }
    }

    public static void resetEntityPosition(Entity entity) {
        entity.setPosition(entity.getOriginalX(), entity.getOriginalY());
    }


    public void changeDirection(TextureObject obj) {
        AIMovement aiMovement = obj.getAIMovement();
        if (aiMovement != null) {
            float velocityX = aiMovement.getDirectionX();
            float velocityY = aiMovement.getDirectionY();
            // Reverse both directions for a simple bounce effect
            aiMovement.setDirection(-velocityX, -velocityY);
        }
    }
    
    public float[] randomizePosition(List<Entity> existingEntities) {
        float newX, newY;
        boolean overlap;
        do {
            overlap = false;
            newX = (float) (Math.random() * Gdx.graphics.getWidth());
            newY = (float) (Math.random() * Gdx.graphics.getHeight());
            for (Entity otherEntity : existingEntities) {
                if (otherEntity instanceof TextureObject && CollisionDetection.isOverlapping(newX, newY, otherEntity)) {
                    overlap = true;
                    break;
                }
            }
        } while (overlap);
        return new float[]{newX, newY};
    }
    
    
    
    public void handleTextureObjectCollision(TextureObject obj1, TextureObject obj2) {
        // Calculate the differences between centers
        float dx = obj1.getX() - obj2.getX();
        float dy = obj1.getY() - obj2.getY();

        // Calculate overlap along X and Y axes (using half widths/heights)
        float overlapX = (obj1.getWidth() / 2 + obj2.getWidth() / 2) - Math.abs(dx);
        float overlapY = (obj1.getHeight() / 2 + obj2.getHeight() / 2) - Math.abs(dy);

        // If there is an overlap on both axes, resolve collision along the axis of minimum penetration.
        if (overlapX > 0 && overlapY > 0) {
            // Reduce the separation distance to make the objects bounce off sooner
            float separationFactor = 0.5f; // Reduce this value to decrease the gap
            if (overlapX < overlapY) {
                float separation = overlapX * separationFactor; // Adjust separation factor
                if (dx > 0) {
                    obj1.setX(obj1.getX() + separation);
                    obj2.setX(obj2.getX() - separation);
                } else {
                    obj1.setX(obj1.getX() - separation);
                    obj2.setX(obj2.getX() + separation);
                }
            } else {
                float separation = overlapY * separationFactor; // Adjust separation factor
                if (dy > 0) {
                    obj1.setY(obj1.getY() + separation);
                    obj2.setY(obj2.getY() - separation);
                } else {
                    obj1.setY(obj1.getY() - separation);
                    obj2.setY(obj2.getY() + separation);
                }
            }
        }

        // Reverse directions to create a bounce effect.
        changeDirection(obj1);
        changeDirection(obj2);
    }

}
