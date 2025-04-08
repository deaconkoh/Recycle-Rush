package collisionManager;

import entityManager.AbstractPlayerClass;
import entityManager.Entity;
import entityManager.MainC;
import entityManager.SideC;
import entityManager.TextureObject;
import gameMasterFolder.GameMaster1;
import soundManager.SoundManager;

import java.util.List;


public class CollisionManager {
    private CollisionHandling collisionHandling;
    private SoundManager soundManager;
    private CollisionStrategy collisionStrategy;  // Reference to the strategy

    public CollisionManager(SoundManager soundManager, CollisionStrategy collisionStrategy) {
        this.soundManager = soundManager;
        this.collisionHandling = new CollisionHandling(soundManager);
        this.collisionStrategy = collisionStrategy;  // Set the chosen strategy
    }

    public void checkCollisions(List<Entity> entities, GameMaster1 gameMaster) {
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                Entity entityA = entities.get(i);
                Entity entityB = entities.get(j);

                // Check if both entities are of the correct type before casting
                if ((entityA instanceof MainC || entityA instanceof SideC) && entityB instanceof TextureObject) {
                    // Handle collision between MainC or SideC and TextureObject
                    if (collisionStrategy.checkCollision((TextureObject) entityB, (AbstractPlayerClass) entityA)) {
                        handleCollision((TextureObject) entityB, gameMaster);
                    }
                } else if (entityA instanceof TextureObject && entityB instanceof TextureObject) {
                    // Handle collision between two TextureObject entities with improved resolution
                    if (collisionStrategy.checkCollision((TextureObject) entityA, (TextureObject) entityB)) {
                        collisionHandling.handleTextureObjectCollision((TextureObject) entityA, (TextureObject) entityB);
                    }
                }
            }
        }
    }

    private void handleCollision(TextureObject textureObject, GameMaster1 gameMaster) {
        if (soundManager != null) {
            soundManager.playCollisionSound();
        }
        gameMaster.getEntityManager().teleportEntity(textureObject, gameMaster);
    }

    public void checkOutOfBounds(Entity entity, float screenWidth, float screenHeight) {
        if (entity.getX() < 0 || entity.getX() > screenWidth || entity.getY() < 0 || entity.getY() > screenHeight) {
            entity.setX(Math.max(0, Math.min(entity.getX(), screenWidth)));
            entity.setY(Math.max(0, Math.min(entity.getY(), screenHeight)));
        }
    }


}
