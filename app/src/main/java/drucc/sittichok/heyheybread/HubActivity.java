package drucc.sittichok.heyheybread;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HubActivity extends AppCompatActivity implements View.OnClickListener {

    // Explicit

    private ImageView orderImageView, readImageView,
    editImageView, mapImageView, complacencyImageView;
    private String idString;    // รับค่า Receive id ที่ user login อยู่

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        //Bind Widget
        bindWidget();

        //Image Controller
        imageController();
    }   // onCreate



    private void imageController() {
        idString = getIntent().getStringExtra("ID");
        orderImageView.setOnClickListener(this);
        readImageView.setOnClickListener(this);
        editImageView.setOnClickListener(this);
        mapImageView.setOnClickListener(this);
        complacencyImageView.setOnClickListener(this);
    }

    private void bindWidget() {
        orderImageView = (ImageView) findViewById(R.id.imageView2);
        readImageView = (ImageView) findViewById(R.id.imageView3);
        editImageView = (ImageView) findViewById(R.id.imageView4);
        mapImageView = (ImageView) findViewById(R.id.imageView5);
        complacencyImageView = (ImageView) findViewById(R.id.imageView6);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView2:
                //Order Bread
                Intent objIntent = new Intent(HubActivity.this, showMenuActivity.class);
                objIntent.putExtra("ID", idString);
                startActivity(objIntent);
                break;

            case R.id.imageView3:
                //Read Order
                clickReadOrder();
                break;

            case R.id.imageView4:
                //Edit Account
                Intent intent = new Intent(HubActivity.this, EditUser.class);
                intent.putExtra("ID", idString);
                startActivity(intent);

                break;

            case R.id.imageView5:
                //My Map
                break;

            case R.id.imageView6:
                // OrderHistory
                Intent intent2 = new Intent(HubActivity.this, HistoryActivity.class);
                intent2.putExtra("ID", idString);
                startActivity(intent2);

                break;
        }   //switch
    }   // onClick

    private void clickReadOrder() {
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                MODE_PRIVATE, null);
        Cursor objCursor = objSqLiteDatabase.rawQuery("SELECT * FROM " + ManageTABLE.TABLE_ORDER, null);
        if (objCursor.getCount() > 0) {
            Intent objIntent = new Intent(HubActivity.this, ConfirmOrderActivity.class);
            objIntent.putExtra("Status", true);
            startActivity(objIntent);
        } else {
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.errorDialog(HubActivity.this,"ยังไม่มีการสั่งซื้อ","กรุณาสั่งสินค้าก่อนครับ");
        }
    }
}   // Main Class
