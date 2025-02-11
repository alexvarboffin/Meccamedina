package com.example.incomingphone.promo;

public class MyProject {
    String packageName;




    public String getPackageName() {
        return packageName;
    }

    public String[] getNames() {
        return names;
    }

    String[] names;

    public MyProject(String packageName, String[] names, String boardName) {
        this.packageName = packageName;
        this.names = names;
    }
}