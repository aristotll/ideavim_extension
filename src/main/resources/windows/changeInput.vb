You can use the ActivateKeyboardLayout API function.  This is not quite as simple as just saying ActivateKeyboardLayout("EN") for example.

        Instead, you have to use the keyboard layout identifiers which are essentially arbitrary numbers.

        This code gets the current layout, gets up to 50 other available layouts (these are the ones you have added to your system), activates each of the other layouts one at a time, then resets back to the original.


        Private Declare Function GetKeyboardLayout& Lib "user32" (ByVal dwLayout As Long)
        Private Declare Function GetKeyboardLayoutList& Lib "user32" (ByVal nBuff As Long, lpList As Long)
        Private Declare Function ActivateKeyboardLayout& Lib "user32" (ByVal hkl As Long, ByVal flags As Long)

        Public Sub TestLayout

        Dim lOriginalLayout As Long
        Dim lRet As Long
        Dim i As Integer

        ReDim lLayouts(50) As Long

        'Save current configuration
        lOriginalLayout = GetKeyboardLayout(0)

        'Get the first 50 supported keyboard layouts (50 is max supported for now)
        lRet = GetKeyboardLayoutList(50, lLayouts(0))
        'Loop through all the keyboard layouts
        For i = 0 To UBound(lLayouts)
        If lLayouts(i) = 0 Then
        Exit For
        End If
        'Activate the keyboard layout and get its name
        lRet = ActivateKeyboardLayout(lLayouts(i), 0)
        Next i

        'Restore current configuration
        lRet = ActivateKeyboardLayout(lOriginalLayout, 0)

        End Sub