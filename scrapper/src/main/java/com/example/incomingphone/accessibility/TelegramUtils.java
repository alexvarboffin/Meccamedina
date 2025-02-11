package com.example.incomingphone.accessibility;

import java.util.Arrays;
import java.util.List;

public class TelegramUtils {
    public static boolean isNotTGIgnore(String text, String contentDescription) {
        String[] tgIgnoreList = new String[]{
                "Switch to night theme", "Switch to day theme", "Profile photo","Set New Photo","Get QR code","Links",
                "New Message", "Open navigation menu", "Search", "Go back", "Show as List","More options",
                "Do you want to clear your search history?", "Cancel", "Clear All", "Emoji, stickers, and GIFs",
                "Broadcast", "Send notifications", "Attach media", "Jump to Date", "Next search result", "Previous search result",



                "REPORT SPAM AND LEAVE", "Close"

        };
        List<String> list = Arrays.asList(tgIgnoreList);

        return !list.contains(text) && !list.contains(contentDescription);
    }
}
