package MovementManager;

import entityManager.Entity;

public interface InterfaceMovement {
    void Movement(String direction, float screenWidth, float screenHeight, Entity entity);  // Add Entity as a parameter
}
