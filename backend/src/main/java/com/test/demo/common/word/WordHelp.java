package com.test.demo.common.word;

public class WordHelp {
//    public StringBuilder addStrong(StringBuilder word) {
//        word.insert(0,"*");
//        word.append("* :");
////        return "*" + word + "* :";
//        return word;
//    }


//    public StringBuilder addLine(StringBuilder line) {
//        StringBuilder temp = new StringBuilder("    ");
//        return temp.append(line.toString()).append("\n");
//    }
//
//    public StringBuilder addUnit(StringBuilder title, StringBuilder lines) {
//        StringBuilder unit = title.append(":\n");
//        unit.append(lines);
//        unit.append("\n");
//        return unit;
//    }

    public String addLine(String line) {
//        StringBuilder temp = new StringBuilder("    ");
        return "    "+line+"\n";
    }

    public String addUnit(String title, String lines) {
        String unit = title+":\n";
        unit+=lines;
        unit+="\n";
        return unit;
    }
    public String addStrong(String word) {

        return "*" + word + "* :";

    }

}
