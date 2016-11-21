package hanhit.mywalletapp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hanh.tran on 6/22/2016.
 */
public class MyHandle {

    public MyHandle() {
    }

    public String[] get_array_icon(){
        return new String[]{
                "ic_movie", "ic_shop", "ic_baby", "ic_home", "ic_wallet", "ic_travel", "ic_another"
        };
    }
    public String[] get_array_name_category(){
        return new String[]{
                "Movie", "Shopping", "Baby", "Home", "Wallet", "Travel", "Another"
        };
    }

    public String handleStringValue(String str) {
        boolean sub = false;
        if (str.contains("-")) {
            sub = true;
            str = str.replace("-", "");
        }

        if (str.length() >= 4) {
            int num_comma = str.length() / 3;
            int first_comma = str.length() % 3;

            if (first_comma != 0) {
                for (int i = 0; i < num_comma; i++) {
                    str = new StringBuilder(str).insert(first_comma, ",").toString();
                    first_comma += 4;
                }
            } else {
                for (int i = 0; i < num_comma - 1; i++) {
                    first_comma += 3;
                    str = new StringBuilder(str).insert(first_comma, ",").toString();
                    first_comma += 1;
                }
            }
        }

        if (sub) {
            str = new StringBuilder(str).insert(0, "-").toString();
        }
        return str;
    }

    // Method format date
    public String formatDate(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public String getMonth(String date) {
        String[] times = date.split("-");
        return times[1] + "-" + times[2];
    }

}
