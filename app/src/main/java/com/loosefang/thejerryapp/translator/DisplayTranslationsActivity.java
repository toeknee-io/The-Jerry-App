package com.loosefang.thejerryapp.translator;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loosefang.thejerryapp.R;

import java.util.List;

/**
 * Created by Tony on 12/24/2014.
 */
public class DisplayTranslationsActivity extends ListActivity {

    private TranslationSource transSource;
    List<TranslateItem> transList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_translations);
        transSource = new TranslationSource(this);
        refreshDisplay();
    }

    private void refreshDisplay() {
        transList = transSource.findAll();
        ArrayAdapter<TranslateItem> adapter =
                new ArrayAdapter<TranslateItem>(this, R.layout.translate_items_list, transList);
        setListAdapter(adapter);

    }

    protected void onListItemClick(ListView l, View v, int position, long id) {

        Context context = getApplicationContext();
        CharSequence text = "E ff you, translation deleted";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);

        TranslateItem trans = transList.get(position);
        transSource.remove(trans);
        refreshDisplay();
        toast.show();

    }

}
