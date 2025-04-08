package MovementManager;

import entityManager.Entity;
import com.badlogic.gdx.Gdx;

public class PlayerMovement implements InterfaceMovement {

    @Override
    public void Movement(String direction, float screenWidth, float screenHeight, Entity entity) {
        float speed = entity.getSpeed() * Gdx.graphics.getDeltaTime();
        float newX = entity.getX();
        float newY = entity.getY();

        float entityWidth = entity.getWidth();
        float entityHeight = entity.getHeight();

        switch (direction) {
            case "up":
                newY = Math.min(screenHeight - entityHeight / 2, newY + speed);
                break;
            case "down":
                newY = Math.max(entityHeight / 2, newY - speed);
                break;
            case "left":
                newX = Math.max(entityWidth / 2, newX - speed);
                break;
            case "right":
                newX = Math.min(screenWidth - entityWidth / 2, newX + speed);
                break;
        }

        entity.setPosition(newX, newY);
        entity.setState("moving");
    }
}
