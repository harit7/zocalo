; Script originated by the HM NIS Edit Script Wizard.

; HM NIS Edit Wizard helper defines
!define PRODUCT_NAME "Zocalo"
!define PRODUCT_VERSION "2011.2 Exp"
!define PRODUCT_PUBLISHER "Chris Hibbert"
!define PRODUCT_WEB_SITE "http://zocalo.sourceforge.net"
!define PRODUCT_UNINST_KEY "Software\Microsoft\Windows\CurrentVersion\Uninstall\${PRODUCT_NAME}"
!define PRODUCT_UNINST_ADMIN_KEY "HKLM"
!define PRODUCT_STARTMENU_REGVAL "NSIS:StartMenuDir"

; MUI 1.67 compatible ------
!include "MUI.nsh"
!include "defines.nsh"

; MUI Settings
!define MUI_ABORTWARNING
!define MUI_ICON "${NSISDIR}\Contrib\Graphics\Icons\modern-install.ico"
!define MUI_UNICON "${NSISDIR}\Contrib\Graphics\Icons\modern-uninstall.ico"
!define MUI_HEADERIMAGE
!define MUI_HEADERIMAGE_BITMAP "zocalo\webpages\images\logo.zocalo.BMP"

; WELCOME
!define MUI_WELCOMEPAGE_TITLE "Installing Zocalo Econ Experiments"
!define MUI_WELCOMEPAGE_TITLE_3LINES "Zocalo release $(^PRODUCT_VERSION)"
!define MUI_WELCOMEPAGE_TEXT "Welcome to Zocalo!  This installer will help you configure the Zocalo Econ Experiments software."
!insertmacro MUI_PAGE_WELCOME

; LICENSE
!define MUI_LICENSEPAGE_TEXT_TOP "This is the MIT license from http://opensource.org/licenses.  The only restriction is that you may not remove the copyright notices."
!define MUI_LICENSEPAGE_TEXT_BOTTOM "Click Next to continue."
!define MUI_LICENSEPAGE_BUTTON "Next"
!insertmacro MUI_PAGE_LICENSE "Zocalo\LICENSE.txt"

; DIRECTORY
!insertmacro MUI_PAGE_DIRECTORY

; Macro copied and modified from MUI_PAGE_LICENSE in 'Modern UI/System.nsh'
!macro MUI_PAGE_INSTALLATION InstallationDATA
  !verbose push
  !verbose ${MUI_VERBOSE}

  !insertmacro MUI_PAGE_INIT
  !insertmacro MUI_SET MUI_${MUI_PAGE_UNINSTALLER_PREFIX}LICENSEPAGE

  PageEx ${MUI_PAGE_UNINSTALLER_FUNCPREFIX}license
    PageCallbacks ${MUI_PAGE_UNINSTALLER_FUNCPREFIX}mui.LicensePre_${MUI_UNIQUEID} ${MUI_PAGE_UNINSTALLER_FUNCPREFIX}mui.LicenseShow_${MUI_UNIQUEID} ${MUI_PAGE_UNINSTALLER_FUNCPREFIX}mui.LicenseLeave_${MUI_UNIQUEID}

    Caption " "
    LicenseData "${InstallationDATA}"
    LicenseText "Click Next to continue." "Next"

  PageExEnd
  !insertmacro MUI_FUNCTION_LICENSEPAGE ${MUI_PAGE_UNINSTALLER_FUNCPREFIX}mui.LicensePre_${MUI_UNIQUEID} ${MUI_PAGE_UNINSTALLER_FUNCPREFIX}mui.LicenseShow_${MUI_UNIQUEID} ${MUI_PAGE_UNINSTALLER_FUNCPREFIX}mui.LicenseLeave_${MUI_UNIQUEID}

  !insertmacro MUI_UNSET MUI_LICENSEPAGE_TEXT_TOP
  !insertmacro MUI_UNSET MUI_LICENSEPAGE_TEXT_BOTTOM
  !insertmacro MUI_UNSET MUI_LICENSEPAGE_BUTTON
  !verbose pop
!macroend


!define MUI_PAGE_HEADER_TEXT "Installation Overview"
!define MUI_PAGE_HEADER_SUBTEXT "Instructions for configuring Zocalo"
!define MUI_LICENSEPAGE_TEXT_TOP "Please review the directions for modifying the configuration parameters."
!define MUI_LICENSEPAGE_TEXT_BOTTOM ""
!define MUI_LICENSEPAGE_BUTTON "Next"

!insertmacro MUI_PAGE_INSTALLATION "Zocalo\INSTALL-short.txt"

  !insertmacro MUI_UNSET MUI_TEXT_LICENSE_TITLE
  !insertmacro MUI_UNSET MUI_TEXT_LICENSE_SUBTITLE

; Instfiles page
!insertmacro MUI_PAGE_INSTFILES

; Finish Page
!define MUI_PAGE_HEADER_TEXT "FINISH PAGE"
!define MUI_FINISHPAGE_NOAUTOCLOSE
!define MUI_FINISHPAGE_TITLE "Congratulations!  Zocalo is installed."
!define MUI_FINISHPAGE_TEXT "Read INSTALL.txt for instructions on running Zocalo.  (run zocalo.bat to start the server.)   See the file CONFIGURATION.txt for details on configuring experiments.  Recent Changes are described in CHANGES.txt"
!insertmacro MUI_PAGE_FINISH

; Uninstaller pages
!insertmacro MUI_UNPAGE_INSTFILES

; Language files
!insertmacro MUI_LANGUAGE "English"

; MUI end ------

 UninstPage uninstConfirm
 UninstPage instfiles

Name "${PRODUCT_NAME} ${PRODUCT_VERSION}"
OutFile "zocalo-Exp-Setup.exe"
InstallDir "$PROGRAMFILES\Zocalo"
ShowInstDetails show
ShowUnInstDetails show

Section "-Install" Install
  SetOutPath "$INSTDIR"
   File "zocalo\*.txt"
   File "zocalo\log4j.properties"
  SetOutPath "$INSTDIR\etc"
   File "zocalo\etc\*.txt"
   File "zocalo\etc\*.pm"
   File "zocalo\etc\svn-version"
   File "zocalo\etc\zocalo.conf"
  SetOutPath "$INSTDIR\jars"
   File "zocalo\jars\*.jar"
  SetOutPath "$INSTDIR\LICENSES"
   File "zocalo\LICENSES\*.txt"
   File "zocalo\LICENSES\*.html"
  SetOutPath "$INSTDIR"
  File "zocalo\bin\*.bat"
  SetOutPath "$INSTDIR\cgi"
  File "zocalo\cgi\*.pl"
  SetOutPath "$INSTDIR\bin"
  File "zocalo\bin\*.bat"
  File "zocalo\bin\*.py"
  File "zocalo\bin\*.pl"
  SetOutPath "$INSTDIR\webpages"
  File "zocalo\webpages\*.js"
   CreateDirectory "zocalo\webpages\charts"
  SetOutPath "$INSTDIR\webpages\stripChart"
   File "zocalo\webpages\stripChart\*.*"
  SetOutPath "$INSTDIR\webpages\dojo"
   File /r dojo "zocalo\webpages\dojo\*.*"
  SetOutPath "$INSTDIR\webpages\images"
   File "zocalo\webpages\images\*.*"
SectionEnd

!include "TextFunc.nsh"
!insertmacro ConfigRead
!insertmacro ConfigWrite

!include LogicLib.nsh
!include "WordFunc.nsh"
!insertmacro StrFilter

!define configFile $INSTDIR\etc\zocalo.conf

!macro updateConfigNum key label prompt
   push $R0  ; old value
   push $R1  ; user provided value
   push $R2  ; update results (ignored)
   push $R3  ; digits contained in R1 
   push $R4  ; hint for prompt
   ${ConfigRead} ${configFile} "${key}: " $R0
   StrCpy $R4  "$\r$\n(was '$R0')"
   ${Do}
      Dialogs::InputBox "${label}" "${prompt}$R4" "OK" "Cancel" 0 ${VAR_R1}
      ${StrFilter} $R1 "1" "" "" $R3
      ${If} $R1 S!= $R3
         StrCpy $R4  "$\r$\n(was '$R0' [enter digits only])"
      ${ElseIf} $R3 != ""
         ${ConfigWrite} ${configFile} "${key}: " $R3 $R2
         ${ExitDo}
      ${Else}
         ${ExitDo}
      ${EndIf}
   ${Loop}
   pop $R4
   pop $R3
   pop $R2
   pop $R1
   pop $R0
!macroend

!macro updateConfigDomain key label prompt
   push $R0  ; old value
   push $R1  ; user provided value
   push $R2  ; update results (ignored)
   push $R3  ; characters contained in R1 
   push $R4  ; hint for prompt
   ${ConfigRead} ${configFile} "${key}: " $R0
   StrCpy $R4  "$\r$\n(was '$R0')"
   ${Do}
      Dialogs::InputBox "${label}" "${prompt}$R4" "OK" "Cancel" 0 ${VAR_R1}
      ${StrFilter} $R1 "12" ".-" "" $R3
      ${If} $R1 S!= $R3
         StrCpy $R4  "$\r$\n(was '$R0' [enter letters, digits, and '.' only])"
      ${ElseIf} $R3 != ""
         ${ConfigWrite} ${configFile} "${key}: " $R3 $R2
         ${ExitDo}
      ${Else}
         ${ExitDo}
      ${EndIf}
   ${Loop}
   pop $R4
   pop $R3
   pop $R2
   pop $R1
   pop $R0
!macroend

Section "Customize" Customize
   !insertmacro updateConfigNum "server.port" "Server Port Number" "Enter Port Number"
SectionEnd

Section -Post   Post
  WriteUninstaller "$INSTDIR\uninst.exe"
  WriteRegStr ${PRODUCT_UNINST_ADMIN_KEY} "${PRODUCT_UNINST_KEY}" "DisplayName" "$(^Name)"
  WriteRegStr ${PRODUCT_UNINST_ADMIN_KEY} "${PRODUCT_UNINST_KEY}" "UninstallString" "$INSTDIR\uninst.exe"
  WriteRegStr ${PRODUCT_UNINST_ADMIN_KEY} "${PRODUCT_UNINST_KEY}" "DisplayVersion" "${PRODUCT_VERSION}"
  WriteRegStr ${PRODUCT_UNINST_ADMIN_KEY} "${PRODUCT_UNINST_KEY}" "URLInfoAbout" "${PRODUCT_WEB_SITE}"
  WriteRegStr ${PRODUCT_UNINST_ADMIN_KEY} "${PRODUCT_UNINST_KEY}" "Publisher" "${PRODUCT_PUBLISHER}"
SectionEnd

Function un.onUninstSuccess
  HideWindow
  MessageBox MB_ICONINFORMATION|MB_OK "$(^Name) was successfully removed from your computer."
FunctionEnd

Function un.onInit
  MessageBox MB_ICONQUESTION|MB_YESNO|MB_DEFBUTTON2 "Are you sure you want to completely remove $(^Name) and all of its components?" IDYES +2
  Abort
FunctionEnd

Section Uninstall
  Delete "$INSTDIR\uninst.exe"
  Delete "$INSTDIR\jars\*.jar"
  Delete "$INSTDIR\jars\LICENSES\*.txt"
  Delete "$INSTDIR\etc\*.*"
  Delete "$INSTDIR\bin\*.*"
  Delete "$INSTDIR\logs\*.*"
  Delete "$INSTDIR\*.*"
  Delete "$INSTDIR\webpages\charts\*.*"
  Delete "$INSTDIR\webpages\images\*.*"
  Delete "$INSTDIR\webpages\*.*"
  RMDir "$INSTDIR\jars\LICENSES"
  RMDir "$INSTDIR\jars"
  RMDir "$INSTDIR\etc"
  RMDir "$INSTDIR\webpages\charts"
  RMDir "$INSTDIR\webpages\images"
  RMDir "$INSTDIR\webpages"
  RMDir "$INSTDIR\bin"
  RMDir "$INSTDIR\logs"

  DeleteRegKey ${PRODUCT_UNINST_ADMIN_KEY} "${PRODUCT_UNINST_KEY}"
  SetAutoClose true
SectionEnd
