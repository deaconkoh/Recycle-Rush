package inputOutputManager;

import com.badlogic.gdx.Input;
import MovementManager.MovementManager;
import entityManager.EntityManager;

import java.util.Map;

public class KeyboardInput extends AbstractInput {
    private final MovementManager movementManager;
    private final EntityManager entityManager;
    
    private static final Map<String, String> MOVEMENT_KEYS = Map.of(
        "MCmoveUp", "up",
        "MCmoveDown", "down",
        "MCmoveLeft", "left",
        "MCmoveRight", "right",
        "SCmoveUp", "up",
        "SCmoveDown", "down",
        "SCmoveLeft", "left",
        "SCmoveRight", "right"
    );

    public KeyboardInput( MovementManager movementManager, EntityManager entityManager) {
    	this.movementManager = movementManager;
    	this.entityManager = entityManager;
        defineBindings();
        defineActions(); 
    }

    @Override
    public void defineBindings() {
    	// Movement keybindings
        setBinding("MCmoveUp", Input.Keys.W);
        setBinding("MCmoveDown", Input.Keys.S);
        setBinding("MCmoveLeft", Input.Keys.A);
        setBinding("MCmoveRight", Input.Keys.D);
        setBinding("SCmoveUp", Input.Keys.UP);
        setBinding("SCmoveDown", Input.Keys.DOWN);
        setBinding("SCmoveLeft", Input.Keys.LEFT);
        setBinding("SCmoveRight", Input.Keys.RIGHT);
    }

    @Override
    public void defineActions() {
    	for (String action : MOVEMENT_KEYS.keySet()) {
            String direction = MOVEMENT_KEYS.get(action);
            String characterType = action.startsWith("MC") ? "MC" : "SC";

            setAction(action, () -> movementManager.moveCharacter(characterType, direction, 
                com.badlogic.gdx.Gdx.graphics.getWidth(), 
                com.badlogic.gdx.Gdx.graphics.getHeight()));
        }
    	setAction("ResetPosition", () -> entityManager.resetEntities());
    }

    @Override
    public void handleInput() {
        for (String action : bindings.keySet()) {
            if (com.badlogic.gdx.Gdx.input.isKeyPressed(bindings.get(action))) {
                executeAction(action);
            }
        }
    }
}
