// MainC.java
package entityManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import MovementManager.PlayerMovement;

public class MainC extends AbstractPlayerClass {

    public MainC(String texturePath, float scaleFactor, float x, float y, float speed) {
        super(x, y, speed);
        playerMovement = new PlayerMovement();
        setTexture(new Texture(Gdx.files.internal(texturePath)));
        setPixmap(new Pixmap(Gdx.files.internal(texturePath)));
        setWidth(getTexture().getWidth() * scaleFactor);
        setHeight(getTexture().getHeight() * scaleFactor);
        setState("idle");
    }

    @Override
    public void Movement(String direction, float screenWidth, float screenHeight, Entity entity) {
        super.Movement(direction, screenWidth, screenHeight);  // Delegate to the AbstractPlayerClass
    }

    @Override
    public void update() {
        // Custom update logic
    }
}


