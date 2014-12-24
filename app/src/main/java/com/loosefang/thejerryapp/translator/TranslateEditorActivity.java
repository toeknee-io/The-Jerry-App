package com.loosefang.thejerryapp.translator;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.loosefang.thejerryapp.R;

/**
 * Created by Tony on 12/24/2014.
 */
public class TranslateEditorActivity extends Activity {

    private TranslateItem translate;
    private TranslationSource transSource;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translator_editor_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        transSource = new TranslationSource(this);

        Intent intent = this.getIntent();

        translate = new TranslateItem();
/*
        ## Use later for passing translation info between Activities

        translate.setKey(intent.getStringExtra("englishText"));
        translate.setText(intent.getStringExtra("translationText"));

        EditText englishText = (EditText) findViewById(R.id.englishText);
        englishText.setText(translate.getKey());
        englishText.setSelection(translate.getText().length());

        EditText translationText = (EditText) findViewById(R.id.translationText);
        translationText.setText(translate.getText());
        translationText.setSelection(translate.getText().length());
*/
    }

    public void saveAndFinish(View v) {
        EditText getEnglish = (EditText) findViewById(R.id.englishText);
        EditText getTranslation = (EditText) findViewById(R.id.translationText);

        String englishText = getEnglish.getText().toString();
        String translationText = getTranslation.getText().toString();

        translate.setKey(englishText);
        translate.setText(translationText);

        Intent intent = new Intent(this, TranslatorActivity.class);
        intent.putExtra("key", translate.getKey());
        intent.putExtra("text", translationText);

        transSource.update(translate);
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            saveAndFinish(findViewById(R.id.translator_activity_layout));
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        saveAndFinish(findViewById(R.id.translator_activity_layout));
    }

    public void seeTranslations(View view) {
        Intent intent = new Intent(this, DisplayTranslationsActivity.class);
        this.startActivity(intent);
    }

}
