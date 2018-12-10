package com.example.myhttp;

import android.app.Activity;
import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class CheckErrString {
    private Object cxt;
    private String err = "网络不好，请检查网络";

    public CheckErrString(Activity cxt) {
        this.cxt = cxt;
    }

    public CheckErrString(Fragment cxt) {
        this.cxt = cxt;
    }

    public CheckErrString(Service cxt) {
        this.cxt = cxt;
    }

    public CheckErrString(Context cxt) {
        this.cxt = cxt;
    }

    private interface ErrMessage {
        String errMessage();
    }

    public void showErr(String err) {
        ErrMessage errMessage = errMessageMap.get(err);
        if (errMessage != null) {
            this.err = errMessage.errMessage();
            if (cxt != null && !err.equals("ok")) {
                if (cxt instanceof Fragment) {
                    Toast.makeText(((Fragment) cxt).getActivity(), this.err, Toast.LENGTH_SHORT).show();
                } else if (cxt instanceof Activity) {
                    Toast.makeText((Activity) cxt, this.err, Toast.LENGTH_SHORT).show();
                }
            }
        } else {

        }
    }

    Map<String, ErrMessage> errMessageMap = new HashMap<String, ErrMessage>();

    //这里填errCode
    {
        errMessageMap.put("err_code", new ErrMessage() {
            @Override
            public String errMessage() {
                return "errmessage";
            }
        });

    }
}
