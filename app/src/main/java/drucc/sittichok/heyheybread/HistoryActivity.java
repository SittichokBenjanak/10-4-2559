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
    private ListView orderUser;
    private String OrderNumber, DateOrder, SumPrice, Status;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        bindWidget();

        readOrder();

        showView();


    }   // onCreate

    private void readOrder() {

        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                MODE_PRIVATE, null);
        Cursor objCursor = objSqLiteDatabase.rawQuery("SELECT * FROM tborder WHERE _id = " + "'" + strID + "'", null);
        objCursor.moveToFirst();
        OrderNumber = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_id));
        DateOrder = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_OrderDate));
        SumPrice = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_GrandTotal));
        Status = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_Status));

        String[] OrderNumber = new String[objCursor.getCount()];
        String[] DateOrder = new String[objCursor.getCount()];
        String[] SumPrice = new String[objCursor.getCount()];
        String[] status = new String[objCursor.getCount()];

        for (int i=0; i<objCursor.getCount(); i++) {

            OrderNumber[i] = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_id));
            DateOrder[i] = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_Date));
            SumPrice[i] = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_GrandTotal));
            status[i] = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_Status));

            objCursor.moveToNext();

        }   // for
        objCursor.close();

    }   //  readOrder

    private void bindWidget() {

        orderUser = (ListView) findViewById(R.id.listView3);

    }   // bindWidget


    private void showView() {

        strID = getIntent().getStringExtra("ID");
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase
                .rawQuery("SELECT * FROM tborder WHERE _id = " + "'" + strID + "'", null);


        cursor.close();

    }   // showView
}   // Main Class
