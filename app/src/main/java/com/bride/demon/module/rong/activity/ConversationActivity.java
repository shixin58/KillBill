package com.bride.demon.module.rong.activity;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.bride.demon.R;

import java.util.Locale;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

/**
 * <p>Created by shixin on 2019-08-01.
 */
public class ConversationActivity extends FragmentActivity {

    private Conversation.ConversationType mConversationType;
    private String mTargetId;
    private String mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);

        Uri inUri = getIntent().getData();
        mConversationType = Conversation.ConversationType.valueOf(inUri.getLastPathSegment().toUpperCase(Locale.US));
        mTargetId = inUri.getQueryParameter("targetId");
        mTitle = inUri.getQueryParameter("title");

        FragmentManager fragmentManage = getSupportFragmentManager();
        ConversationFragment fragement = (ConversationFragment) fragmentManage.findFragmentById(R.id.conversation);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragement.setUri(uri);
    }
}
