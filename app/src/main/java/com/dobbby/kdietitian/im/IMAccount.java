package com.dobbby.kdietitian.im;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.File;

/**
 * Created by Dobbby on 2017/6/28.
 */
interface IMAccount {
    void register(String name, String password) throws
            XmppStringprepException, XMPPException.XMPPErrorException,
            SmackException.NotConnectedException, InterruptedException,
            SmackException.NoResponseException;

    void login(String name, String password);

    void setAvatar(File avatar) throws
            XMPPException.XMPPErrorException, SmackException.NotConnectedException,
            InterruptedException, SmackException.NoResponseException;

    void getAvatar() throws
            XMPPException.XMPPErrorException, SmackException.NotConnectedException,
            InterruptedException, SmackException.NoResponseException;

    void logout();
}
