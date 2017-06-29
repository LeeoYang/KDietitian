package com.dobbby.kdietitian.im;

/**
 * Created by Dobbby on 2017/6/28.
 */
public interface OnIMActionListener {
    void showAddFriendQuery(String name);

    void showFriendList();

    void updateFriendList();

    void showNewMessageFromFriend(String name, String message);

    void showNewMessageFromGroup(String group, String name, String message);
}
