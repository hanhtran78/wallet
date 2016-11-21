package hanhit.mywalletapp.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hanhit.mywalletapp.ItemActivity;
import hanhit.mywalletapp.MyHandle;
import hanhit.mywalletapp.R;
import hanhit.mywalletapp.database.MyDatabase;
import hanhit.mywalletapp.model.DateOfItem;
import hanhit.mywalletapp.model.Item;

/**
 * Created by HanhTv on 7/17/2016.
 */
public class AdapterReportDetail extends RecyclerView.Adapter<AdapterReportDetail.MyViewHolder> {
    private Activity mContext;
    private List<Object> items;
    private MyHandle myHandle = new MyHandle();
    private MyDatabase myData;
    private Handler mHandler = new Handler();
    private String[] array_icon = null;

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public AdapterReportDetail(Activity mContext, List<Object> objects) {
        this.mContext = mContext;
        this.items = objects;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_date_of_item, parent, false);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custom_item_view_report_detail, parent, false);
                break;
        }

        return new MyViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        try {
            DateOfItem date = (DateOfItem) items.get(position);
            return 0;
        } catch (ClassCastException ex) {
        }

        return 1;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            final Item item = (Item) items.get(position);
            array_icon = myHandle.get_array_icon();

            holder.showImageByName(mContext, array_icon[item.getIdCategoryItem()]);
            holder.txt_name_item.setText(item.getNameItem());
            holder.txt_hour_item.setText(item.getHourItem());
            if (item.getTypeItem() == 0) {
                holder.txt_value_item.setTextColor(mContext.getResources().getColor(R.color.color_income));
            } else {
                holder.txt_value_item.setTextColor(mContext.getResources().getColor(R.color.color_expense));
            }
            String value = item.getValueItem();
            holder.txt_value_item.setText(myHandle.handleStringValue(value) + ",000 VND");

            holder.imageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentEdit = new Intent(mContext, ItemActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", mContext.getString(R.string.title_edit_item));
                    bundle.putSerializable("object", item);
                    intentEdit.putExtra("data", bundle);
                    mContext.startActivityForResult(intentEdit, Activity.RESULT_OK);
                }
            });

            holder.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext)
                            .setView(R.layout.layout_confirm_delete)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    myData = new MyDatabase(mContext);
                                    if (myData.deleteItem(item.getIdItem()) != 0) {
                                        items.remove(position);
                                        Toast.makeText(mContext, mContext.getResources().getString(R.string.msg_delete_success), Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(mContext, mContext.getResources().getString(R.string.msg_delete_success), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
            });
        } catch (ClassCastException ex) {
            myData = new MyDatabase(mContext);
            DateOfItem date_item = (DateOfItem) items.get(position);
            holder.txt_date_item.setText(date_item.getDate());
            BigInteger sum_value = myData.getValueByDate(holder.txt_date_item.getText().toString());
            if (sum_value.compareTo(new BigInteger("0")) >= 0)
                holder.txt_sum_value_item.setTextColor(mContext.getResources().getColor(R.color.color_income));
            else
                holder.txt_sum_value_item.setTextColor(mContext.getResources().getColor(R.color.color_expense));
            holder.txt_sum_value_item.setText(myHandle.handleStringValue(sum_value + "") + ",000 VND");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageItem, imageEdit, imageDelete;
        TextView txt_name_item, txt_date_item, txt_hour_item, txt_value_item, txt_sum_value_item;

        public MyViewHolder(View view) {
            super(view);
            imageItem = (ImageView) view.findViewById(R.id.image_item_report_detail);
            imageEdit = (ImageView) view.findViewById(R.id.image_item_edit_report_detail);
            imageDelete = (ImageView) view.findViewById(R.id.image_item_delete_report_detail);
            txt_name_item = (TextView) view.findViewById(R.id.txt_name_item_report_detail);
            txt_hour_item = (TextView) view.findViewById(R.id.txt_hour_item_report_detail);
            txt_date_item = (TextView) view.findViewById(R.id.txt_date_item_report_detail);
            txt_value_item = (TextView) view.findViewById(R.id.txt_value_item_report_detail);
            txt_sum_value_item = (TextView) view.findViewById(R.id.txt_sum_value_item_report_detail);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    imageEdit.setVisibility(View.VISIBLE);
                    imageDelete.setVisibility(View.VISIBLE);

                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageEdit.setVisibility(View.GONE);
                            imageDelete.setVisibility(View.GONE);
                        }
                    }, 3000);       //3000 = 3s
                    return false;
                }
            });
        }

        public void showImageByName(Activity mActivity, String nameImage) {
            String pkgName = mActivity.getPackageName();
            int resId = mActivity.getResources().getIdentifier(nameImage, "mipmap", pkgName);

            if (resId != 0) {
                imageItem.setImageResource(resId);
            }
        }
    }


}
