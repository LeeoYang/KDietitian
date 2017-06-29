package com.dobbby.kdietitian.im;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.MultiUserChatException;
import org.jxmpp.stringprep.XmppStringprepException;

/**
 * Created by Dobbby on 2017/6/28.
 */
public interface InstantMessaging extends IMAccount {
    void sendMessage(String from, String to, String content) throws
            XmppStringprepException, SmackException.NotConnectedException,
            InterruptedException;

    void requestAddFriend(String user, String name, String[] groups) throws
            XmppStringprepException, SmackException.NotLoggedInException,
            XMPPException.XMPPErrorException, SmackException.NotConnectedException,
            InterruptedException, SmackException.NoResponseException;

    void confirmAddFriend(String name) throws
            XmppStringprepException, SmackException.NotConnectedException,
            InterruptedException;

    void removeFriend(String user) throws
            XmppStringprepException, SmackException.NotLoggedInException,
            XMPPException.XMPPErrorException, SmackException.NotConnectedException,
            InterruptedException, SmackException.NoResponseException;

    void listenToThisMUC(String name, String groupName) throws
            XmppStringprepException, XMPPException.XMPPErrorException,
            MultiUserChatException.NotAMucServiceException,
            SmackException.NotConnectedException, InterruptedException,
            SmackException.NoResponseException;

    void createMUC(String name, String groupName) throws
            XmppStringprepException, XMPPException.XMPPErrorException,
            SmackException.NotConnectedException, InterruptedException,
            SmackException.NoResponseException,
            MultiUserChatException.MissingMucCreationAcknowledgeException,
            MultiUserChatException.NotAMucServiceException,
            MultiUserChatException.MucAlreadyJoinedException;

    void grantMUCMembership(String user, String groupName) throws
            XmppStringprepException, XMPPException.XMPPErrorException,
            MultiUserChatException.NotAMucServiceException,
            SmackException.NotConnectedException, InterruptedException,
            SmackException.NoResponseException;

    void sendMUCMessage(String name, String groupName, String content) throws
            XmppStringprepException, XMPPException.XMPPErrorException,
            MultiUserChatException.NotAMucServiceException,
            SmackException.NotConnectedException, InterruptedException,
            SmackException.NoResponseException;

    void showMUCMembers(String groupName) throws
            XmppStringprepException, XMPPException.XMPPErrorException,
            SmackException.NotConnectedException, InterruptedException,
            SmackException.NoResponseException,
            MultiUserChatException.NotAMucServiceException;

    void addOnIMActionListener(OnIMActionListener listener);
}
