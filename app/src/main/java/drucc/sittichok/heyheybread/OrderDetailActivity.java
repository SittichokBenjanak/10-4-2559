package drucc.sittichok.heyheybread;

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

    }   // readAllorderdetail

    private void bindWiget() {

        detailListView = (ListView) findViewById(R.id.ListViewDetail);

    }   // bindWiget
}
