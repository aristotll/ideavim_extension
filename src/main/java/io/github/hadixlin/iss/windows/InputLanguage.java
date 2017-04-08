package io.github.hadixlin.iss.windows;

import com.sun.jna.Native;

/**
 * Name: InputLanguage <br>
 * https://msdn.microsoft.com/en-us/library/ms646267(VS.85).aspx#_win32_Languages_Locales_and_Keyboard_Layouts
 * User: Yao<br>
 * Date: 17/4/8<br>
 * Time: 20:59<br>
 */
public class InputLanguage {
    public static final int ENGLISH_INPUT = 0x4090409;//eng
    public static final String ENGLISH_LAYOUT = "00000409";

    static {
        Native.register("user32");
    }

    /**
     * http://baike.baidu.com/item/GetKeyboardLayout?wtp=tt
     * http://jcs.mobile-utopia.com/jcs/55085_User32.java
     */
    public static native long GetKeyboardLayout(long dwLayout);

    /**
     * http://www.pinvoke.net/default.aspx/user32.loadkeyboardlayout
     */
    public native static int LoadKeyboardLayout(String pwszKLID, int Flags);

    /**
     * 这个是上一个下一个的
     *
     * @param hkl   be either the handle to a keyboard layout or one of the following values
     *              两种情况
     *              1.layout
     *              2. 0 or 1
     *              上一个为0 下一个为1
     *              以当前 layout 为基准
     *              The input locale identifier must have been loaded by a previous call to the LoadKeyboardLayout function.
     * @param Flags 256
     * @return 成功与否
     * If the function succeeds, the return value is the previous input locale identifier. Otherwise, it is zero.
     */
    public native static int ActivateKeyboardLayout(int hkl, int Flags);

    public native static int GetKeyboardLayoutList(int nBuff, int[] lpList);

    public static long currentKeyboardLayout() {
        return GetKeyboardLayout(0);
    }

    public static long previousKeyboardLayout() {
        return ActivateKeyboardLayout(0, 256);
//        return ActivateKeyboardLayout(HKL, 0);
    }

    public static void setEnglishLanguage() {
        ActivateKeyboardLayout(ENGLISH_INPUT,256);
    }
}
