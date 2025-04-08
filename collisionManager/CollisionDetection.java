package collisionManager;

import entityManager.Entity;

public class CollisionDetection {

    public static boolean isEntityOutofBound(Entity entity, float screenWidth, float screenHeight) {
        return entity.getX() < 0 || entity.getX() > screenWidth || entity.getY() < 0 || entity.getY() > screenHeight;
    }
    
    // Check if a new position overlaps with any existing entity
    public static boolean isOverlapping(float x, float y, Entity otherEntity) {
        float otherX = otherEntity.getX();
        float otherY = otherEntity.getY();
        float otherWidth = otherEntity.getWidth();
        float otherHeight = otherEntity.getHeight();

        return !(x + otherWidth / 2 < otherX - otherWidth / 2 ||
                 x - otherWidth / 2 > otherX + otherWidth / 2 ||
                 y + otherHeight / 2 < otherY - otherHeight / 2 ||
                 y - otherHeight / 2 > otherY + otherHeight / 2);
    }
}
