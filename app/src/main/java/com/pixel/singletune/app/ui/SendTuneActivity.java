package com.pixel.singletune.app.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.pixel.singletune.app.ParseConstants;
import com.pixel.singletune.app.R;
import com.pixel.singletune.app.helpers.FileHelper;

/**
 * Created by mrsmith on 5/26/14.
 */
public class SendTuneActivity extends Activity {

    protected Uri mMediaUri;
    protected Uri mTuneArt;
    protected String mFileType;
    protected MenuItem mSendMenuItem;
    protected EditText mTitle;
    protected EditText mCaption;
    protected ImageButton mArtImageButton;
    protected String title;
    protected String caption;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_sendtune);

        mMediaUri = getIntent().getData();
        mFileType = getIntent().getExtras().getString(ParseConstants.KEY_FILE_TYPE);

    }

    protected void onResume(){
        super.onResume();


    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {

        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.send_tune, menu);
        mSendMenuItem = menu.getItem(0);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_send:
                mTitle = (EditText)findViewById(R.id.tune_title);
                mCaption = (EditText)findViewById(R.id.tune_desc);
                String title = mTitle.getText().toString();
                String caption = mCaption.getText().toString();
                if(title.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(R.string.empty_title_error_message)
                        .setTitle(R.string.empty_title_error_title)
                        .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    setProgressBarIndeterminateVisibility(true);
                    ParseObject tune = createTune(title, caption);
                    if (tune == null) {
                        // error
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(R.string.error_selecting_file)
                                .setTitle(R.string.error_selecting_file_title)
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else {
                        send(tune);
                        finish();
                    }
                    return true;
                }

        }
        return super.onOptionsItemSelected(item);
    }

    private void send(ParseObject tune) {
//        TODO: Implement a progressbar here
        tune.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                setProgressBarIndeterminateVisibility(false);
                if (e == null) {
                    // success!
                    Toast.makeText(SendTuneActivity.this, R.string.success_message, Toast.LENGTH_LONG).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SendTuneActivity.this);
                    builder.setMessage(R.string.error_sending_message)
                            .setTitle(R.string.error_selecting_file_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }

    private ParseObject createTune(String title, String caption) {
        ParseObject tune = new ParseObject(ParseConstants.CLASS_TUNES);
        tune.put(ParseConstants.KEY_USER_ID, ParseUser.getCurrentUser().getObjectId());
        tune.put(ParseConstants.KEY_TUNE_TITLE, title);
        tune.put(ParseConstants.KEY_TUNE_DESC, caption);
        tune.put(ParseConstants.KEY_FILE_TYPE, mFileType);

        byte[] fileBytes = FileHelper.getByteArrayFromFile(this, mMediaUri);

        String fileName = FileHelper.getFileName(this, mMediaUri, mFileType);
        ParseFile file = new ParseFile(fileName, fileBytes);
        tune.put(ParseConstants.KEY_FILE, file);

        return tune;
    }
}
