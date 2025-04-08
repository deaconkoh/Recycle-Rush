package entityManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import collisionManager.Collidable;

public abstract class Entity implements Collidable{
	
	protected float x, y;
	protected float originalX, originalY;
	protected float speed;
	protected float originalSpeed;
	protected Color color;
	protected String state;
	protected boolean isAIcontrolled;
	protected float width, height;
	
	public Entity(float x, float y, float speed, Color color, boolean isAIcontrolled) {
		this.x = x;
		this.y = y;
		this.originalX = x;
		this.originalY = y;
		this.speed = speed;
		this.color = color;
		this.state = "default";
		this.isAIcontrolled = isAIcontrolled;
		this.originalSpeed = speed;
	}
	
	public abstract void Movement(String direction, float screenWidth, float screenHeight);
	public abstract void update();
	public abstract Pixmap getPixmap();
	
	public Entity(float x, float y) {
		this(x, y, 0, Color.WHITE, false);
	}

	public void setState(String state) { this.state = state; }
	public String getState() { return state; }
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	public float getLength() { return Math.max(width, height);}
	
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void updatePosition(float deltaX, float deltaY) {
		this.x += deltaX;
		this.y += deltaY;
	}
	
	public void resetPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void multiplySpeed(float multiplier) {
    	this.speed = speed * multiplier;
    }
	
	public void resetSpeed() {
		this.speed = originalSpeed;
	}
	
	public float getX() { return x; }
	public void setX(float x) { this.x = x; }
	public float getY() { return y; }
	public void setY(float y) { this.y = y; }
	public float getSpeed() { return speed; }
	public void setSpeed(float speed) { this.speed = speed; }
	public Color getColor() { return color; }
	public void setColor(Color color) { this.color = color; }
	public boolean isAIcontrolled() { return isAIcontrolled; }
	public float getOriginalX() { return originalX; }
    public float getOriginalY() { return originalY; }
}
