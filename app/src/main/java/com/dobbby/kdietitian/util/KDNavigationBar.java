package com.dobbby.kdietitian.util;

import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.dobbby.kdietitian.R;

/**
 * Created by Dobbby on 2017/6/27.
 */
public class KDNavigationBar implements View.OnClickListener {
    private OnNavigationItemClickedListener listener;
    private ImageView homeImage;
    private ImageView friendsImage;
    private ImageView shopImage;
    private ImageView meImage;
    private TextView homeText;
    private TextView friendsText;
    private TextView shopText;
    private TextView meText;

    @ColorInt
    private int colorSelected;
    @ColorInt
    private int colorNotSelected;

    private boolean isHomeSelected;
    private boolean isFriendsSelected;
    private boolean isShopSelected;
    private boolean isMeSelected;

    private KDNavigationBar(AppCompatActivity activity,
                            OnNavigationItemClickedListener listener) {
        this.listener = listener;
        initComponents(activity);
    }

    public static KDNavigationBar newInstance(AppCompatActivity activity,
                                              OnNavigationItemClickedListener listener) {
        return new KDNavigationBar(activity, listener);
    }

    private void initComponents(AppCompatActivity activity) {
        colorSelected = activity.getResources().getColor(R.color.colorSelected);
        colorNotSelected = activity.getResources().getColor(R.color.colorNotSelected);

        homeImage = (ImageView) activity.findViewById(R.id.navigation_home_img);
        friendsImage = (ImageView) activity.findViewById(R.id.navigation_friends_img);
        shopImage = (ImageView) activity.findViewById(R.id.navigation_shop_img);
        meImage = (ImageView) activity.findViewById(R.id.navigation_me_img);

        homeText = (TextView) activity.findViewById(R.id.navigation_home_txt);
        friendsText = (TextView) activity.findViewById(R.id.navigation_friends_txt);
        shopText = (TextView) activity.findViewById(R.id.navigation_shop_txt);
        meText = (TextView) activity.findViewById(R.id.navigation_me_txt);

        View homeLayout = activity.findViewById(R.id.navigation_home_layout);
        View friendsLayout = activity.findViewById(R.id.navigation_friends_layout);
        View shopLayout = activity.findViewById(R.id.navigation_shop_layout);
        View meLayout = activity.findViewById(R.id.navigation_me_layout);
        View actionButton = activity.findViewById(R.id.navigation_action_button);

        assert homeLayout != null;
        assert friendsLayout != null;
        assert shopLayout != null;
        assert meLayout != null;
        assert actionButton != null;

        homeLayout.setOnClickListener(this);
        friendsLayout.setOnClickListener(this);
        shopLayout.setOnClickListener(this);
        meLayout.setOnClickListener(this);
        actionButton.setOnClickListener(this);

        singleSelectHome();
    }

    private void singleSelectHome() {
        highlightHome();
        downPlayFriends();
        downPlayShop();
        downPlayMe();
    }

    private void singleSelectFriends() {
        highlightFriends();
        downPlayHome();
        downPlayShop();
        downPlayMe();
    }

    private void singleSelectShop() {
        highlightShop();
        downPlayHome();
        downPlayFriends();
        downPlayMe();
    }

    private void singleSelectMe() {
        highlightMe();
        downPlayHome();
        downPlayFriends();
        downPlayShop();
    }

    private void highlightHome() {
        isHomeSelected = true;
        homeImage.setImageResource(R.mipmap.navigation_home_selected);
        homeText.setTextColor(colorSelected);
    }

    private void downPlayHome() {
        isHomeSelected = false;
        homeImage.setImageResource(R.mipmap.navigation_home);
        homeText.setTextColor(colorNotSelected);
    }

    private void highlightFriends() {
        isFriendsSelected = true;
        friendsImage.setImageResource(R.mipmap.navigation_friends_selected);
        friendsText.setTextColor(colorSelected);
    }

    private void downPlayFriends() {
        isFriendsSelected = false;
        friendsImage.setImageResource(R.mipmap.navigation_friends);
        friendsText.setTextColor(colorNotSelected);
    }

    private void highlightShop() {
        isShopSelected = true;
        shopImage.setImageResource(R.mipmap.navigation_shop_selected);
        shopText.setTextColor(colorSelected);
    }

    private void downPlayShop() {
        isShopSelected = false;
        shopImage.setImageResource(R.mipmap.navigation_shop);
        shopText.setTextColor(colorNotSelected);
    }

    private void highlightMe() {
        isMeSelected = true;
        meImage.setImageResource(R.mipmap.navigation_me_selected);
        meText.setTextColor(colorSelected);
    }

    private void downPlayMe() {
        isMeSelected = false;
        meImage.setImageResource(R.mipmap.navigation_me);
        meText.setTextColor(colorNotSelected);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navigation_home_layout:
                if (!isHomeSelected) {
                    singleSelectHome();
                    if (listener != null) {
                        listener.onHomeSelected();
                    }
                }
                break;
            case R.id.navigation_friends_layout:
                if (!isFriendsSelected) {
                    singleSelectFriends();
                    if (listener != null) {
                        listener.onFriendsSelected();
                    }
                }
                break;
            case R.id.navigation_shop_layout:
                if (!isShopSelected) {
                    singleSelectShop();
                    if (listener != null) {
                        listener.onShopSelected();
                    }
                }
                break;
            case R.id.navigation_me_layout:
                if (!isMeSelected) {
                    singleSelectMe();
                    if (listener != null) {
                        listener.onMeSelected();
                    }
                }
                break;
            case R.id.navigation_action_button:
                if (listener != null) {
                    listener.onActionSelected();
                }
                break;
            default:
                break;
        }
    }

    public interface OnNavigationItemClickedListener {
        void onHomeSelected();

        void onFriendsSelected();

        void onShopSelected();

        void onMeSelected();

        void onActionSelected();
    }
}
