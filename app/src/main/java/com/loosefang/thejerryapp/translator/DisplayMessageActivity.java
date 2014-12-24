package com.loosefang.thejerryapp.translator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.loosefang.thejerryapp.R;

import java.util.Map;

public class DisplayMessageActivity extends FragmentActivity {

    private final String PREFKEY = "translate";
    private SharedPreferences translationPrefs;
    public String message;
    public String jerrySpeak;
    public String getJerrySpeak() {return jerrySpeak;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        
        // Get message passed from TranslatorActivity intent
        Intent intent = getIntent();
        message = intent.getStringExtra(TranslatorActivity.EXTRA_MESSAGE);


        Log.i("ENGLISH 1", message);

        jerrySpeak = englishToJerry(message);

        // Create text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(jerrySpeak);
        
        // Set text view as the activity translator_editor_activity
        setContentView(textView);               
    }

    // Translates English to Jerry
    public String englishToJerry(String s) {

        translationPrefs = this.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);

        Map<String, ?> transMap = translationPrefs.getAll();

        Log.i("ENGLISH 2", s);
        String[] splitString = s.split(" ");

        StringBuilder constructString = new StringBuilder();

        for (String engWord : splitString){
            Log.i("ENGLISH 3", engWord);
            if (transMap.containsKey(engWord)) {
                String translatedWord = String.valueOf(transMap.get(engWord));
                constructString.append(translatedWord)
                               .append(" ")
                               .trimToSize();
            } else {
                constructString.append(engWord)
                               .append(" ")
                               .trimToSize();
            }
        }

        s = constructString.toString();

    	  return s;
    	  
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                  Bundle savedInstanceState) {
              View rootView = inflater.inflate(R.layout.activity_display_message,
                      container, false);
              return rootView;
        }
    }
     */

}
