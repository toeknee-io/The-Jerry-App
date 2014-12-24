package com.loosefang.thejerryapp.translator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.loosefang.thejerryapp.R;


public class TranslatorActivity extends FragmentActivity {

    public final static String EXTRA_MESSAGE = "";
    public static final int EDITOR_ACTIVITY_REQUEST = 1001;
    private TranslationSource transSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translator_activity_layout);

        transSource = new TranslationSource(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void addTranslation(View view) {
        Intent intent = new Intent(this, TranslateEditorActivity.class);
        this.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == EDITOR_ACTIVITY_REQUEST && resultCode == RESULT_OK) {
            TranslateItem trans = new TranslateItem();
            trans.setKey(data.getStringExtra("key"));
            trans.setText(data.getStringExtra("text"));
            transSource.update(trans);
        }
    }    
}
