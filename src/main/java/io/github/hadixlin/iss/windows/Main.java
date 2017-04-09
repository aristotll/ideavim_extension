package io.github.hadixlin.iss.windows;

/**
 * Name: test<br>
 * User: Yao<br>
 * Date: 17/4/9<br>
 * Time: 02:52<br>
 */

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.win32.StdCallLibrary;

public class Main {
    public interface Kernel32 extends StdCallLibrary {
        Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("user32",
                Kernel32.class);

        int GetKeyboardLayoutList(int nBuff, Pointer[] x);

        Pointer ActivateKeyboardLayout(Pointer hkl, int flags);

        boolean GetKeyboardLayoutNameA(byte[] name);
    }

    public static void main(String[] args) {
        Pointer[] hkls = new Pointer[10];
        int n = Kernel32.INSTANCE.GetKeyboardLayoutList(10, hkls);
        for (int i = 0; i < n; i++) {
            Kernel32.INSTANCE.ActivateKeyboardLayout(hkls[i], 0);
            byte[] buf = new byte[256];
            System.out.println(Kernel32.INSTANCE.GetKeyboardLayoutNameA(buf));
            String name = Native.toString(buf);
            System.out.println(name);
            // user32
//            User32.INSTANCE.SendMessage();
        }
    }

}
