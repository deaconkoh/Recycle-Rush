package entityManager;

import collisionManager.CollisionManager;
import collisionManager.AABBCollisionStrategy;
import collisionManager.CollisionHandling;
import collisionManager.PixelPerfectCollisionStrategy;
import soundManager.SoundManager;
import gameMasterFolder.GameMaster1;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class EntityManager {
    private List<Entity> entities;
    private String state;
    private final CollisionManager collisionManager;
    private final CollisionHandling collisionHandling;
    

    // Constructor that allows you to choose which collision strategy to use
    public EntityManager(SoundManager soundManager, boolean usePixelPerfectCollision) {
        entities = new ArrayList<>();
        this.state = "ACTIVE";
        
        // Choose the collision strategy based on the flag
        if (usePixelPerfectCollision) {
            this.collisionManager = new CollisionManager(soundManager, new PixelPerfectCollisionStrategy());
        } else {
            this.collisionManager = new CollisionManager(soundManager, new AABBCollisionStrategy());
        }
        this.collisionHandling = new CollisionHandling(soundManager);
    }

    // Update entities and check for collisions and out-of-bounds
    public void updateEntities(GameMaster1 gameMaster) {
        for (Entity entity : entities) {
            entity.update();  // Update each entity's state
            collisionManager.checkOutOfBounds(entity, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        collisionManager.checkCollisions(entities, gameMaster);  // Check for collisions
    }

    public void drawEntities(SpriteBatch batch, boolean multiplayer) {
        batch.begin();
        for (Entity entity : entities) {
            if (entity instanceof TextureObject) {
                ((TextureObject) entity).render(batch);
            }
            if (entity instanceof MainC) {
                ((MainC) entity).render(batch);
            }
            if (entity instanceof SideC && multiplayer) {
                ((SideC) entity).render(batch);  // Render SideC only if multiplayer is true
            }
        }
        batch.end();
    }

    // Add a new entity to the list
    public void addEntity(Entity entity) {
        System.out.println("Entity added: " + entity);
        System.out.println("Total entities in EntityManager: " + entities.size());
        entities.add(entity);
    }

    // Get the list of entities
    public List<Entity> getEntities() {
        return entities;
    }

    // Set the state of the entity manager
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // Reset all entities to their original positions
    public void resetEntities() {
        for (Entity entity : this.getEntities()) {
            CollisionHandling.resetEntityPosition(entity);
        }
    }

    // Teleport an entity to a new random position, making sure it doesn't overlap with other entities
    public void teleportEntity(TextureObject textureObject, GameMaster1 gameMaster) {
        float[] newPosition = collisionHandling.randomizePosition(entities);
        textureObject.setPosition(newPosition[0], newPosition[1]);
        gameMaster.updateScore(textureObject.getScoreImpact());  // Update score based on scoreImpact of TextureObject
    }
}
