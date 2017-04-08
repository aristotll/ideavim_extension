package io.github.hadixlin.iss;

import static io.github.hadixlin.iss.InputSourceSwitcher.getCurrentInputSourceID;

/**
 * Created by hadix on 28/03/2017.
 */
public class SystemInputSource implements InputSource {

    private static final String DEFAULT_INPUT_SOURCE = "com.apple.keylayout.US";
    private static String formerInputSource = "com.apple.keylayout.US";

    public void switchToEnglish() {
        updateFormer();
        InputSourceSwitcher.switchInputSource(DEFAULT_INPUT_SOURCE);
    }

    public void switchToFormer() {
        if (!formerInputSource.equals(getCurrentInputSourceID())) {
            InputSourceSwitcher.switchInputSource(formerInputSource);
        }
    }

    public void updateFormer() {
        formerInputSource = getCurrentInputSourceID();
    }
}