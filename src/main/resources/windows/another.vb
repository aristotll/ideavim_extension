Parameter Information:
Declare Function LoadKeyboardLayout Lib "user32" Alias "LoadKeyboardLayoutA" (ByVal pwszKLID As String, ByVal flags As Long) As Long

· pwszKLID
Points to the buffer that specifies the name of the keyboard layout. This name is a string composed from the hexadecimal value of the primary language identifier (low word) and a device identifier (high word). For example, U.S. English has a language identifier of 0x0409, so the primary U.S. English layout is named “00000409”. Variants of U.S. English layout, such as the Dvorak layout, are named “00010409”, “00020409”, and so on. For a list of the primary language identifiers and secondary language identifiers that make up a language identifier, see the MAKELANGID macro.

· Flags
Specifies how the keyboard layout is to be loaded. This parameter can be one of the following values:
KLF_ACTIVATE
If the given layout is not already loaded, the function loads and activates the layout for the current thread, inserting the layout at the head of the keyboard layouts list in front of the previously active layout. If the layout is already loaded and the KLF_REORDER value is not given, the function simply rotates the keyboard layouts list, making the next layout the active layout.

KLF_NOTELLSHELL
Prevents a ShellProc hook procedure from receiving an HSHELL_LANGUAGE hook code when the new layout is loaded. This value is typically used when an application loads multiple layouts, one after another. Applying this value to all but the last layout delays the shell’s processing until all layouts have been added.

KLF_REORDER
Moves the given layout to the head of the keyboard layouts list, making that layout the active layout for the current thread. This value reorders the keyboard layouts list even if KLF_ACTIVATE is not given.

KLF_REPLACELANG
If the new layout has the same language identifier as a current layout, the new layout replaces the current one as the layout for that language. If this value is not given and the layouts have the same language identifiers, the current layout is not replaced and the function returns NULL.

KLF_SUBSTITUTE_OK
Substitues the given keyboard layout with another layout preferred by the user. The substitution occurs only if the registry key HKEY_CURRENT_USER\Keyboard Layout\Substitutes explicitly defines a substitution layout. For example, if the key includes the value name “00000409” with value “00010409”, loading the U.S. English layout (“00000409”) causes the Dvorak U.S. English layout (“00010409”) to be loaded instead. The system uses KLF_SUBSTITUTE_OK when booting and it is recommended that all applications use this value too.

If the function succeeds, the return value is the keyboard layout handle of the layout matched with the requested name or NULL if no matching keyboard is available.

Example:

'This fucntion changes the locale and as a result, the keyboardlayout gets adjusted

'parameters for api's
Const KL_NAMELENGTH As Long = 9 'length of the keyboardbuffer
Const KLF_ACTIVATE As Long = &H1 'activate the layout

'the language constants
Const LANG_NL_STD As String = "00000413"
Const LANG_EN_US As String = "00000409"
Const LANG_DU_STD As String = "00000407"
Const LANG_FR_STD As String = "0000040C"

'api's to adjust the keyboardlayout
Private Declare Function GetKeyboardLayoutName Lib "user32" Alias "GetKeyboardLayoutNameA" (ByVal pwszKLID As String) As Long
Private Declare Function LoadKeyboardLayout Lib "user32" Alias "LoadKeyboardLayoutA" (ByVal pwszKLID As String, ByVal flags As Long) As Long

Public Function SetKbLayout(strLocaleId As String) As Boolean
'Changes the KeyboardLayout
'Returns TRUE when the KeyboardLayout was adjusted properly, FALSE otherwise
'If the KeyboardLayout isn't installed, this function will install it for you
On Error Resume Next
Dim strLocId As String 'used to retrieve current KeyboardLayout
Dim strMsg As String 'used as buffer
Dim lngErrNr As Long 'receives the API-error number

'create a buffer
strLocId = String(KL_NAMELENGTH, 0)
'retrieve the current KeyboardLayout
GetKeyboardLayoutName strLocId
'Check whether the current KeyboardLayout and the
'new one are the same
If strLocId = (strLocaleId & Chr(0)) Then
'If they're the same, we return immediately
SetKbLayout = True
Else
'create buffer
strLocId = String(KL_NAMELENGTH, 0)
'load and activate the layout for the current thread
strLocId = LoadKeyboardLayout((strLocaleId & Chr(0)), KLF_ACTIVATE)
If IsNull(strLocId) Then 'returns NULL when it fails
SetKbLayout = False
Else 'check again
'create buffer
strLocId = String(KL_NAMELENGTH, 0)
'retrieve the current layout
GetKeyboardLayoutName strLocId
If strLocId = (strLocaleId & Chr(0)) Then
SetKbLayout = True
Else
SetKbLayout = False
End If
End If
End If
End Function

Private Sub Form_Load()
'change the current keybour layout to 'English - US'
SetKbLayout LANG_EN_US
End Sub