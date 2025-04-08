package MovementManager;

import entityManager.Entity;
import entityManager.EntityManager;
import entityManager.MainC;
import entityManager.SideC;

public class MovementManager {
    private final EntityManager entityManager;

    public MovementManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void moveCharacter(String characterType, String direction, float screenWidth, float screenHeight) {
        for (Entity entity : entityManager.getEntities()) {
        	if (entity instanceof MainC && characterType.equals("MC")) {
                ((MainC) entity).Movement(direction, screenWidth, screenHeight);
            } else if (entity instanceof SideC && characterType.equals("SC")) {
                ((SideC) entity).Movement(direction, screenWidth, screenHeight);
            }
        }
    }
}

