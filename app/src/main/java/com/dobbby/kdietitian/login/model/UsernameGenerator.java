package com.dobbby.kdietitian.login.model;

/**
 * Created by Dobbby on 2017/6/23.
 */
public class UsernameGenerator implements IUsernameGenerator {

    @Override
    public String generateUsernameFromTel(String tel) {
        return "86" + tel;
    }
}
