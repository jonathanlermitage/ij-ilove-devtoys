package lermitage.intellij.ilovedevtoys.tools;

import com.thedeanda.lorem.LoremIpsum;

public class LoremIpsumTools {

    public static String generateLoremIpsum(int nbWords) {
        String lorem = new LoremIpsum().getWords(nbWords);
        return lorem.substring(0, 1).toUpperCase() + lorem.substring(1) + ".";
    }
}
