package com.loosefang.thejerryapp.translator;

/**
 * Created by Tony on 12/24/2014.
 */
public class TranslateItem {

    private String key;
    private String text;
    public String getKey() {
        return key;
    }

    public String getText() {
        return text;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static TranslateItem getNew() {
/*
        ## Sample code for saving key as timestamp

        Locale locale = new Locale("en_US");
        Locale.setDefault(locale);

        String pattern = "yyyy-MM-dd HH:mm:ss Z";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String key = formatter.format(new Date());
*/
        TranslateItem trans = new TranslateItem();
        trans.setKey("");
        trans.setText("");
        return trans;



    }

    @Override
    public String toString() {
        return this.getText();
    }
}
