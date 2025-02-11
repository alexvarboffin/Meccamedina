package com.example.incomingphone.promo;

public class Pinterests {


    private static String v1 = "com.christianquotestoinspire.bibleverses.motivation";
    private static String v2 = "com.walhalla.islamicquotes";
    //    PinterestUsers data=new PinterestUsers();
//    {
//        List<User> users=new ArrayList<>();
//        users.add(new User());
//        users.add(new User());
//
//        data.setUsers(users);
//    }
    public static String[] pentries = new String[]{
            v1,
            v2
    };

    public static MyProject christQuotesEn_ = new MyProject(
            "com.christianquotestoinspire.bibleverses.motivation",
            new String[]{"Christian Quotes - Motivation"},
            "Christian Quotes Ideas");


    public static MyProject islamicquotes_ = new MyProject(
            "com.walhalla.islamicquotes",
            new String[]{"Islamic Inspirational Quotes"},
            "PinterestUsers.Mayson188.b1.boardName");


    public static MyProject getProjectByName(String project) {
        if (v1.equals(project)){
            return christQuotesEn_;
        }else if(v2.equals(project)){
            return islamicquotes_;
        }

        return christQuotesEn_;
    }
}
