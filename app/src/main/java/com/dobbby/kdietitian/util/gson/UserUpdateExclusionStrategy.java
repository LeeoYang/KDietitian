package com.dobbby.kdietitian.util.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Created by Dobbby on 2017/6/23.
 * <p>
 * Arrow update items: gender, birthday, nickname.
 */
public class UserUpdateExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        switch (f.getName()) {
            case "gender":
                // fall through
            case "birthday":
                // fall through
            case "nickname":
                return false;
            default:
                return true;
        }
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
