package drucc.sittichok.heyheybread;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HistoryActivity extends AppCompatActivity {

    // Explicit
    private String strID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        showView();

    }   // onCreate

    private void showView() {

        strID = getIntent().getStringExtra("ID");
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase
                .rawQuery("SELECT * FROM tborder WHERE _id = " + "'" + strID + "'", null);


        cursor.close();

    }   // showView
}   // Main Class
