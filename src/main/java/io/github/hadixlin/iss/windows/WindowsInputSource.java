package io.github.hadixlin.iss.windows;

import io.github.hadixlin.iss.InputSource;
import com.intellij.openapi.diagnostic.Logger;
import io.github.hadixlin.iss.utils.NotificationHelper;

/**
 * Name: WindowsInputSource<br>
 * User: Yao<br>
 * Date: 17/4/9<br>
 * Time: 00:23<br>
 */
public class WindowsInputSource implements InputSource {

    private static int former = InputLanguage.currentKeyboardLayout();
//    private static long former = InputLanguage.ENGLISH_INPUT;//度量不是一个 一个 很大 一个很小 必须放在一起用

    private final Logger logger = Logger.getInstance(WindowsInputSource.class);

    @Override
    public void switchToEnglish() {
        updateFormer();
        InputLanguage.setEnglishLanguage();
    }

    @Override
    public void switchToFormer() {
//        NotificationHelper.notify("switchToFormer:former " + String.valueOf(former));
//        NotificationHelper.notify("switchToFormer:current " + String.valueOf(InputLanguage.currentKeyboardLayout()));
        if (!(former == InputLanguage.currentKeyboardLayout())) {
//            InputLanguage.changePreviousInput();
            InputLanguage.previousKeyboardLayout();
        }

    }

    @Override
    public void updateFormer() {
//        NotificationHelper.notify("updateFormer:former " + former);
        former = InputLanguage.currentKeyboardLayout();
//        logger.debug("updateFormer " + former);
//        NotificationHelper.notify("updateFormer:current " + former);

    }

}
