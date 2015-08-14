package edu.nyu.scps.jaxon.gallery;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String OBJECT_TYPE = "GalleryObject";
    ListView listView;

    String name = "nothing";
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        listView = (ListView)findViewById(R.id.listView);
        TextView textView = (TextView)findViewById(R.id.empty);
        listView.setEmptyView(textView);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "wgWI2iBOqqtnbU8urBC9yHK38A9XNj1zEos9txP1", "6FZcC7iTbOmEUy4pGXSnj8KU2nCLOyiBBSDJ2kHw");




        //Create an up-to-date PersonAdapter and plug it into the ListView.
        RefreshTask refreshTask = new RefreshTask();
        refreshTask.execute();

    } // END ON CREATE

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Get an up-to-date list of all the rows in the table in the cloud.
    //(Do this in a second thread.)  When done, put the list into a new adapter
    //and display it in the ListView.  (Do this back in the UI thread.)

    private class RefreshTask extends AsyncTask<Void, Void, List<ParseObject>> {
        private Dialog progressDialog;

        //Get all the persons in the table.
        protected List<ParseObject> doInBackground(Void... params) {
            ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>(OBJECT_TYPE);
            parseQuery.orderByAscending("updatedAt");
            try {
                return parseQuery.find();
            } catch (ParseException parseException) {
                Log.e("myTag", "find", parseException);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading...", true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<ParseObject> parseObjects) {
            if (parseObjects != null) {
                GalleryAdapter galleryAdapter = new GalleryAdapter(MainActivity.this, parseObjects);
                listView.setAdapter(galleryAdapter);
            }
            progressDialog.dismiss();
        }
    }


} // END CLASS
