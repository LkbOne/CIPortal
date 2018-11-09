package com.test.demo.common.word;

public class WordHelp {
    public String addLine(String line) {
        return "    " + line + "\n";
    }
    public String addUnit(String title, String lines) {
        String unit = title + ":\n";
        unit += lines;
        unit += "\n";
        return unit;
    }
    public String addStrong(String word) {
        return "*" + word + "* :";
    }

}
