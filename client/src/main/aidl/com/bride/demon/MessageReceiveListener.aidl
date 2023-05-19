// MessageReceiveListener.aidl
package com.bride.demon;

// Declare any non-default types here with import statements
import com.bride.demon.model.Message;

interface MessageReceiveListener {

    void onReceiveMessage(in Message msg);
}