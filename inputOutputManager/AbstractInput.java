package inputOutputManager;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractInput {
    protected final Map<String, Integer> bindings = new HashMap<>();
    protected final Map<String, Runnable> inputActions = new HashMap<>();
    
    abstract void defineBindings();
    abstract void defineActions();
    abstract void handleInput();
    
    protected void setBinding(String action, int key) {
        bindings.put(action, key);
    }

    protected void setAction(String action, Runnable function) {
        inputActions.put(action, function);
    }

    protected void executeAction(String action) {
        inputActions.getOrDefault(action, () -> {}).run();
    }
}
