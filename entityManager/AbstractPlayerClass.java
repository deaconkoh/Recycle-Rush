// AbstractPlayerClass.java
package entityManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import MovementManager.InterfaceMovement;
import MovementManager.PlayerMovement;

public abstract class AbstractPlayerClass extends Entity implements InterfaceMovement {
    private Texture tex;
    private Pixmap pixmap;
    private float width, height;
    protected PlayerMovement playerMovement;

    public AbstractPlayerClass(float x, float y, float speed) {
        super(x, y, speed, Color.WHITE, false);
        playerMovement = new PlayerMovement();
    }

    @Override
    public void Movement(String direction, float screenWidth, float screenHeight) {
        playerMovement.Movement(direction, screenWidth, screenHeight, this);  // Delegate movement
    }
    
    public PlayerMovement getPlayerMovement() {
        return playerMovement;
    }

    public void setState(String state) {
        this.state = state;
    }

    public float getLength() {
        return Math.max(getWidth(), getHeight());
    }

    public void render(SpriteBatch batch) {
        if (tex != null) {
            batch.draw(tex, getX() - width / 2, getY() - height / 2, width, height);
        }
    }

    public void dispose() {
        if (tex != null) {
            tex.dispose();
            tex = null;
        }
        if (pixmap != null) {
            pixmap.dispose();
            pixmap = null;
        }
    }

    // Getter and Setter for Texture
    public Texture getTexture() {
        return tex;
    }

    public void setTexture(Texture tex) {
        this.tex = tex;
    }

    // Getter and Setter for Pixmap
    public Pixmap getPixmap() {
        return pixmap;
    }

    public void setPixmap(Pixmap pixmap) {
        this.pixmap = pixmap;
    }

    // Getter and Setter for width & height
    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
