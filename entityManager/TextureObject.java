package entityManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import MovementManager.AIMovement;
import collisionManager.Collidable;

import java.util.List;

public class TextureObject extends Entity {

    private Texture tex;
    private Pixmap pixmap;
    private float width, height;
    private int scoreImpact;
    private AIMovement aiMovement;

    public TextureObject(String texturePath, float speed, float scaleFactor, List<Entity> existingEntities, int scoreImpact) {
        super((float) (Math.random() * Gdx.graphics.getWidth()),
              (float) (Math.random() * Gdx.graphics.getHeight()),
              speed, Color.WHITE, false);
        this.tex = new Texture(Gdx.files.internal(texturePath));
        this.pixmap = new Pixmap(Gdx.files.internal(texturePath));
        this.width = tex.getWidth() * scaleFactor;
        this.height = tex.getHeight() * scaleFactor;
        this.aiMovement = new AIMovement();
        this.speed = speed;

        this.scoreImpact = scoreImpact;  
        setState("idle");
    }

    public void render(SpriteBatch batch) {
        batch.draw(tex, x - width / 2, y - height / 2, width, height);
    }

    public void dispose() {
        tex.dispose();
        if (pixmap != null) {
            pixmap.dispose();
        }
    }

    @Override
    public Pixmap getPixmap() {
        return pixmap;
    }

    public int getScoreImpact() {
        return scoreImpact;
    }

    @Override
    public void Movement(String direction, float screenWidth, float screenHeight) {
        aiMovement.move(this, screenWidth, screenHeight);
    }

    @Override
    public void update() {
        Movement("", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public float getWidth() { return width; }

    @Override
    public float getHeight() { return height; }

    public AIMovement getAIMovement() {
        return this.aiMovement;
    }
}
