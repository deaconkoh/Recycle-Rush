package inputOutputManager;

import java.util.List;

public class IOManager {
    private final List<AbstractInput> inputs;

    public IOManager(List<AbstractInput> inputDevices) {
        this.inputs = inputDevices;
    }

    public void handleInput() {
        if (inputs.isEmpty()) return;
        for (AbstractInput input : inputs) {
            input.handleInput();
        }
    }

}



