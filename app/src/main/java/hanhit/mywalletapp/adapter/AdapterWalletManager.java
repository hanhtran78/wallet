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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import hanhit.mywalletapp.ItemActivity;
import hanhit.mywalletapp.MyHandle;
import hanhit.mywalletapp.R;
import hanhit.mywalletapp.database.MyDatabase;
import hanhit.mywalletapp.model.Item;

/**
 * Created by HanhTv on 7/23/2016.
 */
public class AdapterWalletManager extends RecyclerView.Adapter<AdapterWalletManager.MyViewHolder> {
    private Activity mContext;
    private ArrayList<Item> items;
    private MyHandle myHandle;
    private MyDatabase myData;
    private Handler mHandler = new Handler();
    private String[] array_icon = null;

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public AdapterWalletManager(Activity mContext, ArrayList<Item> objects) {
        this.mContext = mContext;
        this.items = objects;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_item_view_wallet_manager, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Item item = items.get(position);
        myHandle = new MyHandle();
        array_icon = myHandle.get_array_icon();

        holder.showImageByName(mContext, array_icon[item.getIdCategoryItem()]);
        holder.txt_name_item.setText(item.getNameItem());
        holder.txt_date_item.setText(item.getDateItem());
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
            public void onClick(final View v) {
                new AlertDialog.Builder(mContext)
                        .setView(R.layout.layout_confirm_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                myData = new MyDatabase(mContext);
                                if (myData.deleteItem(item.getIdItem()) != 0) {
                                    items.remove(position);
                                    Toast.makeText(mContext, mContext.getString(R.string.msg_delete_success), Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                    if (items.size() == 0) {
                                        holder.layout_wallet_manager.setVisibility(View.GONE);
                                        holder.txt_no_data_to_show.setVisibility(View.VISIBLE);
                                    } else {
                                        holder.layout_wallet_manager.setVisibility(View.VISIBLE);
                                        holder.txt_no_data_to_show.setVisibility(View.GONE);
                                    }
                                } else {
                                    Toast.makeText(mContext, mContext.getString(R.string.msg_delete_fail) + myData.deleteItem(item.getIdItem()), Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageItem, imageEdit, imageDelete;
        TextView txt_name_item, txt_date_item, txt_value_item, txt_no_data_to_show;
        LinearLayout layout_wallet_manager;

        public MyViewHolder(View view) {
            super(view);
            imageItem = (ImageView) view.findViewById(R.id.image_item_wallet_manager);
            imageEdit = (ImageView) view.findViewById(R.id.image_item_edit_wallet_manager);
            imageDelete = (ImageView) view.findViewById(R.id.image_item_delete_wallet_manager);
            txt_name_item = (TextView) view.findViewById(R.id.txt_name_item_wallet_manager);
            txt_date_item = (TextView) view.findViewById(R.id.txt_date_item_wallet_manager);
            txt_value_item = (TextView) view.findViewById(R.id.txt_value_item_wallet_manager);
            layout_wallet_manager = (LinearLayout) view.findViewById(R.id.linear_layout_wallet_manager);
            txt_no_data_to_show = (TextView) view.findViewById(R.id.txt_no_data_to_show);

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
