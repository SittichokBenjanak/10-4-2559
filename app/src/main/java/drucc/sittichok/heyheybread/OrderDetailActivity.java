package drucc.sittichok.heyheybread;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class OrderDetailActivity extends AppCompatActivity {

    // Explicit
    private String strID;
    private ListView detailListView;
    private String noString,nameString,amountString,priceString, sumpriceString;
    private String strOrderID;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        try {

//            strID = getIntent().getStringExtra("ID");
              strOrderID = getIntent().getStringExtra("NO");
            Log.d("hey","strOrderID ==> "+ strOrderID );
            bindWiget();

            readAllorderdetail();

        } catch (Exception e) {
            Log.d("hey","strOrderID ="+ strOrderID  + e.toString());
        }

    }

    private void readAllorderdetail() {
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                MODE_PRIVATE,null);
        Cursor objCursor = objSqLiteDatabase.rawQuery("SELECT Bread, * FROM tborderdetail INNER JOIN breadTABLE ON (breadTABLE._id = tborderdetail.Product_ID) WHERE OrderNo = " + strOrderID , null);
        objCursor.moveToFirst();

        final String[] Orderdetail = new String[objCursor.getCount()];
        final String[] Productdetail = new String[objCursor.getCount()];
        final String[] Amountdetail = new String[objCursor.getCount()];
        final String[] Pricedetail = new String[objCursor.getCount()];
        final String[] Sumpricedetail = new String[objCursor.getCount()];

        for (int i = 0; i<objCursor.getCount(); i++) {
            Orderdetail[i] = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_OrderDetail_ID));
            Productdetail[i] = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_Bread));
            Amountdetail[i] = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_Amount));
            Pricedetail[i] = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_Price));
            Sumpricedetail[i] = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_PriceTotal));
            // หา ชื่อสินค้า จาก id product
            objCursor.moveToNext();

        }   // for


        objCursor.close();



        //  Create ListView
        final DetailAdapter objDetailAdapter = new DetailAdapter(OrderDetailActivity.this, Orderdetail, Productdetail, Amountdetail, Pricedetail, Sumpricedetail);

        detailListView.setAdapter(objDetailAdapter);


    }   // readAllorderdetail




    private void bindWiget() {

        detailListView = (ListView) findViewById(R.id.ListViewDetail);

    }   // bindWiget
}
