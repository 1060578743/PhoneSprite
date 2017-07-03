adb push %1 /data/local/tmp/script.apk
adb shell am start -n com.lym.xposed/.activity.RunActivity --es filename /data/local/tmp/script.apk
pause