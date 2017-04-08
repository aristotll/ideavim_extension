package io.github.hadixlin.iss.windows;

import io.github.hadixlin.iss.InputSource;

/**
 * Name: WindowsInputSource<br>
 * User: Yao<br>
 * Date: 17/4/9<br>
 * Time: 00:23<br>
 */
public class WindowsInputSource implements InputSource {
    private static long former = InputLanguage.ENGLISH_INPUT;

    @Override
    public void switchToEnglish() {
        updateFormer();
        InputLanguage.setEnglishLanguage();
    }

    @Override
    public void switchToFormer() {
        if (!(former == InputLanguage.currentKeyboardLayout())) {
            InputLanguage.previousKeyboardLayout();
        }

    }

    @Override
    public void updateFormer() {
        former = InputLanguage.currentKeyboardLayout();
    }
}
