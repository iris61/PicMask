package com.lovecoding.yangying.ImageDetail;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by yangying on 18/3/2.
 */

public class EditTextAddHostReply extends EditText {
    int hostComment = 0;
    int replyToComment = 0;

    public EditTextAddHostReply(Context context) {
        super(context);
    }

    public EditTextAddHostReply(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextAddHostReply(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public EditTextAddHostReply(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setHostComment(int hostComment) {
        this.hostComment = hostComment;
    }

    public void setReplyToComment(int replyToComment) {
        this.replyToComment = replyToComment;
    }

    public int getHostComment() {
        return hostComment;
    }

    public int getReplyToComment() {
        return replyToComment;
    }
}
