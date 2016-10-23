package com.example.vinam.lightningpay.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vinam on 10/22/2016.
 */

public class ReminderContent {
    public static final List<ReminderContent.ReminderItem> ITEMS = new ArrayList<ReminderItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ReminderContent.ReminderItem> ITEM_MAP = new HashMap<String, ReminderItem>();

    private static final int COUNT = 0;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(ReminderContent.ReminderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static ReminderContent.ReminderItem createDummyItem(int position) {
       return new ReminderContent.ReminderItem(String.valueOf(""), "", makeDetails(0));
       // return  new ReminderContent().ReminderItem(String.valueOf(""));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
       // builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ReminderItem {
        public final String id;
        public final String content;
        public final String details;

        public ReminderItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
