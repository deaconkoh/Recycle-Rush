package collisionManager;

public class AABBCollisionStrategy implements CollisionStrategy {
    @Override
    public boolean checkCollision(Collidable obj1, Collidable obj2) {
        float p1_left = obj1.getX() - obj1.getWidth() / 2;
        float p1_bottom = obj1.getY() - obj1.getHeight() / 2;
        float p1_right = obj1.getX() + obj1.getWidth() / 2;
        float p1_top = obj1.getY() + obj1.getHeight() / 2;

        float p2_left = obj2.getX() - obj2.getWidth() / 2;
        float p2_bottom = obj2.getY() - obj2.getHeight() / 2;
        float p2_right = obj2.getX() + obj2.getWidth() / 2;
        float p2_top = obj2.getY() + obj2.getHeight() / 2;

        return !(p1_right < p2_left || p1_left > p2_right || p1_top < p2_bottom || p1_bottom > p2_top);
    }
}
