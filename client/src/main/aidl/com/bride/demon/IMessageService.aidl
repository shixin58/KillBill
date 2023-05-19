// IMessageService.aidl
package com.bride.demon;

// Declare any non-default types here with import statements
import com.bride.demon.model.Message;
import com.bride.demon.MessageReceiveListener;

interface IMessageService {

    void sendMessage(inout Message msg);

    void registerMessageReceiveListener(MessageReceiveListener listener);

    void unregisterMessageReceiveListener(MessageReceiveListener listener);
}