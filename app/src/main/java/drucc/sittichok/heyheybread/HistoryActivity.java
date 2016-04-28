package drucc.sittichok.heyheybread;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        deleteOrder();
        syntborder();

        bindWidget();   // ตัวแปล UserOrderListView = ตำแหน่งของ ListViewHistory

        readAllorder();


    }   // onCreate

    private void syntborder() {
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        int intTimes = 1;
        while (intTimes <= 1) {
            InputStream objInputStream = null;
            String strJSON = null;
            String strURLtborder = "http://swiftcodingthai.com/mos/php_get_tborder_mos.php";
            HttpPost objHttpPost = null;
            // 1 Create InputStream
            try {
                HttpClient objHttpClient = new DefaultHttpClient();
                switch (intTimes) {
                    case 1:
                        objHttpPost = new HttpPost(strURLtborder);
                        break;
                }   // switch

                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();

            } catch (Exception e) {
                Log.d("sss", "InputStream ==> " + e.toString());
            }
            // 2 Create JSON String
            try {
                BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
                StringBuilder objStringBuilder = new StringBuilder();
                String strLine = null;
                while ((strLine = objBufferedReader.readLine()) != null) {
                    objStringBuilder.append(strLine);
                }
                objInputStream.close();
                strJSON = objStringBuilder.toString();

            } catch (Exception e) {
                Log.d("sss", "InputStream ==> " + e.toString());
            }

            // 3 Update JSON String to SQLite
            try {
                JSONArray objJsonArray = new JSONArray(strJSON);
                for (int i=0; i<objJsonArray.length(); i++) {
                    JSONObject object = objJsonArray.getJSONObject(i);
                    switch (intTimes) {
                        case 1:
                            ManageTABLE objManageTABLE = new ManageTABLE(this);
                            String strOrderDate = object.getString(ManageTABLE.COLUMN_OrderDate);
                            String strCustomerID = object.getString(ManageTABLE.COLUMN_CustomerID);
                            String strGrandTotal = object.getString(ManageTABLE.COLUMN_GrandTotal);
                            String strStatus1 = object.getString(ManageTABLE.COLUMN_Status);
                            objManageTABLE.addtbOrder(strOrderDate, strCustomerID, strGrandTotal, strStatus1);
                            break;
                    }

                }   // for

            } catch (Exception e) {
                Log.d("sss", "InputStream ==> " + e.toString());
            }
            intTimes += 1;

        }   // while


    }   // syntborder

    private void deleteOrder() {
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                MODE_PRIVATE, null);
        objSqLiteDatabase.delete(ManageTABLE.TABLE_TBORDER,null,null);

    }   // deleteOrder

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

        // Show orderdetail

        UserOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent objIntent = new Intent(HistoryActivity.this, OrderDetailActivity.class);
                objIntent.putExtra("ID", strID);
                startActivity(objIntent);

            }
        });


    }   // readAllorder


    private void bindWidget() {

        UserOrderListView = (ListView) findViewById(R.id.ListViewHistory);

    }   // bindWidget



}   // Main Class
