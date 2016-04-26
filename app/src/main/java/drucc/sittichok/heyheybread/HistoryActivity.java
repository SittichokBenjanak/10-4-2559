package drucc.sittichok.heyheybread;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    // Explicit
    private String strID;
    private ListView orderUserListView;
    private String OrderNumberString, DateOrderString, SumPriceString, StatusString;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        strID = getIntent().getStringExtra("ID");
        bindWidget();


    }   // onCreate



    private void bindWidget() {

        orderUserListView = (ListView) findViewById(R.id.listView3);

    }   // bindWidget



}   // Main Class
