package com.loosefang.thejerryapp.translator;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Tony on 12/24/2014.
 */
public class TranslationSource {

    private static final String PREFKEY = "translate";
    private SharedPreferences translationPrefs;

    public TranslationSource(Context context) {
        translationPrefs = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
    }

    public List<TranslateItem> findAll() {

        Map<String, ?> transMap = translationPrefs.getAll();

        SortedSet<String> keys = new TreeSet<String>(transMap.keySet());

        List<TranslateItem> translateList = new ArrayList<>();
        for(String key : keys) {
            TranslateItem trans = new TranslateItem();
            trans.setKey(key);
            trans.setText("English: " + key + " || " + "Jerry: " + (String) transMap.get(key));
            translateList.add(trans);
        }

        return translateList;

    }

    public boolean update(TranslateItem trans) {

        SharedPreferences.Editor editor = translationPrefs.edit();
        editor.putString(trans.getKey(), trans.getText());
        editor.commit();
/*
        ## Confirm msg

        Context context = getApplicationContext();
        CharSequence text = "You word now save";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
*/
        return true;
    }

    public boolean remove(TranslateItem trans) {

        if (translationPrefs.contains(trans.getKey())) {
            SharedPreferences.Editor editor = translationPrefs.edit();
            editor.remove(trans.getKey());
            editor.commit();

/*
            ## Confirm msg
            Context context = getApplicationContext();
            CharSequence text = "E ff you, I remove you word";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
*/
        }
        return true;

    }    
    
    
    
}
