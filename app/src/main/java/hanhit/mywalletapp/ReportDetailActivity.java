package hanhit.mywalletapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import hanhit.mywalletapp.adapter.AdapterReportDetail;
import hanhit.mywalletapp.database.MyDatabase;

public class ReportDetailActivity extends AppCompatActivity {

    private TextView mTitle, mMonthReport;
    private RecyclerView recyclerView;
    private List<Object> objects;
//    private ImageView mImage_Next, mImage_Previous;

    private MyDatabase myDB;
    private AdapterReportDetail mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
        init();

        // get and set content for title, value was sent from WalletManagerActivity
        // when press image detail
        Bundle bundle = getIntent().getBundleExtra("data");
        mMonthReport.setText(bundle.getString("title"));

        objects = myDB.getObjects(bundle.getString("month"));
        mAdapter = new AdapterReportDetail(this, objects);
        recyclerView.setAdapter(mAdapter);

//        mImage_Previous.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Do some thing
//            }
//        });
//
//        mImage_Next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Do some thing
//            }
//        });
    }

    public void init() {
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.title_summary_report);
        mMonthReport = (TextView) findViewById(R.id.txt_month_report_detail);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_detail_report);
//        mImage_Next = (ImageView) findViewById(R.id.image_next_report_detail);
//        mImage_Previous = (ImageView) findViewById(R.id.image_previous_report_detail);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        myDB = new MyDatabase(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB.close();
    }
}
