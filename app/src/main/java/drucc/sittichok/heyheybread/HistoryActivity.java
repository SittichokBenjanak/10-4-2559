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
    private ListView UserOrderListView;
    private String OrderNumberString, DateOrderString, SumPriceString, StatusString;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        strID = getIntent().getStringExtra("ID");
        bindWidget();   // ตัวแปล UserOrderListView = ตำแหน่งของ ListViewHistory

        readAllorder();


    }   // onCreate

    private void readAllorder() {
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                MODE_PRIVATE, null);
        Cursor objCursor = objSqLiteDatabase.rawQuery("SELECT * FROM tborder WHERE CustomerID = " + strID, null);
        objCursor.moveToFirst();  // ไปอยู่ที่แถวแรก ของ tborder

        String[] NumberOrder = new String[objCursor.getCount()];
        String[] DateOrder = new String[objCursor.getCount()];
        String[] PriceOrder = new String[objCursor.getCount()];
        String[] StatusOrder = new String[objCursor.getCount()];

        for (int i=0; i<objCursor.getCount();i++) {

            NumberOrder[i] = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_id));
            DateOrder[i] = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_OrderDate));
            PriceOrder[i] = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_GrandTotal));
            StatusOrder[i] = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_Status));

            objCursor.moveToNext();

        }   // for

        objCursor.close();

        // Create ListView
        OrderUserAdapter objOrderUserAdapter = new OrderUserAdapter(HistoryActivity.this, NumberOrder, DateOrder, PriceOrder, StatusOrder);

        UserOrderListView.setAdapter(objOrderUserAdapter);


    }   // readAllorder


    private void bindWidget() {

        UserOrderListView = (ListView) findViewById(R.id.ListViewHistory);

    }   // bindWidget



}   // Main Class
