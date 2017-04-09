package io.github.hadixlin.iss.windows;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.*;
import io.github.hadixlin.iss.utils.NotificationHelper;

/**
 * Name: InputLanguage <br>
 * https://msdn.microsoft.com/en-us/library/ms646267(VS.85).aspx#_win32_Languages_Locales_and_Keyboard_Layouts
 * https://technet.microsoft.com/en-us/library/hh825682.aspx
 * User: Yao<br>
 * Date: 17/4/8<br>
 * Time: 20:59<br>
 */
public class InputLanguage {
    public static final int ENGLISH_INPUT = 0x4090409;//eng
    public static final String ENGLISH_LAYOUT = "00000409";
    public static final ExtendedUser32 USER_32;

    static {
        Native.register("user32");
        USER_32 = ExtendedUser32.INSTANCE;
    }

    /**
     * http://baike.baidu.com/item/GetKeyboardLayout?wtp=tt
     * http://jcs.mobile-utopia.com/jcs/55085_User32.java
     */
    public static native int GetKeyboardLayout(int dwLayout);

/*    *//**
     * http://www.pinvoke.net/default.aspx/user32.loadkeyboardlayout
     *//*
    public native static int LoadKeyboardLayout(String pwszKLID, int Flags);*/

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
     * @return If the function succeeds, the return value is the previous input locale identifier.
     * Otherwise, it is zero.
     */
    public native static int ActivateKeyboardLayout(int hkl, int Flags);

    public native static int GetKeyboardLayoutList(int nBuff, int[] lpList);

    public static int currentKeyboardLayout() {
        return GetKeyboardLayout(0);
    }

    public static int previousKeyboardLayout() {
        return ActivateKeyboardLayout(0, 256);
    }

    public static int nextKeyboardLayout() {
        return ActivateKeyboardLayout(1, 256);
    }


    /**
     * 刚才是 previous language 生效 而 switch to English 失效?
     * impossible
     */
    public static void setEnglishLanguage() {
        ActivateKeyboardLayout(ENGLISH_INPUT, 256);
    }

    public static int previousKeyboardLayout(int former) {
        return ActivateKeyboardLayout(former, 256);
    }

    public native static HWND GetActiveWindow();

//    public native static void SendMessage(int hWnd, int msg, int wParam, int lParam);
//    public native static int SendMessage(HWND hWnd, int Msg, int wParam, String lParam);
//    public native static int SendMessage(HWND hWnd, int Msg, WPARAM wParam, LPARAM lParam);


    public static void changeInput(HWND hWnd, int lParam) {
        //https://msdn.microsoft.com/en-us/library/windows/desktop/ms632630(v=vs.85).aspx
        NotificationHelper.notify(hWnd.toString());
        USER_32.SendMessage(hWnd, 80, new WPARAM(1), new LPARAM(lParam));

    }

    public static void changePreviousInput() {
        changeInput(GetActiveWindow(), previousKeyboardLayout());
    }

    public static void changeNextInput() {
        changeInput(GetActiveWindow(), nextKeyboardLayout());
    }


}
