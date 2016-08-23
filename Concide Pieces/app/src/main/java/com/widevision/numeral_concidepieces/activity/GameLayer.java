package com.widevision.numeral_concidepieces.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.widevision.numeral_concidepieces.R;
import com.widevision.numeral_concidepieces.bean.LeftImageBean;
import com.widevision.numeral_concidepieces.bean.RightImageBean;
import com.widevision.numeral_concidepieces.util.CommonUtil;
import com.widevision.numeral_concidepieces.util.Constant;
import com.widevision.numeral_concidepieces.util.CustomTextView;
import com.widevision.numeral_concidepieces.util.PreferenceConnector;
import com.widevision.numeral_concidepieces.util.Util;

import org.cocos2d.actions.base.CCFiniteTimeAction;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameLayer extends CCLayer {

    public static CCLayer layer;
    public static CCScene scene;
    public static ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
    public static ScheduledExecutorService worker1 = Executors.newSingleThreadScheduledExecutor();
    public static ScheduledExecutorService worker2 = Executors.newSingleThreadScheduledExecutor();
    public int time1, time2;
    protected ArrayList<CCSprite> _upRightImages;
    protected ArrayList<CCSprite> _upRightBox;
    protected ArrayList<CCSprite> _upLeftImages;
    protected ArrayList<CCSprite> _upLeftBox;
    protected ArrayList<CCSprite> imagePart;
    protected ArrayList<CCSprite> life;
    protected CGSize displaysize;
    float CGSX, CGSY;
    float displaywidth, displayheight;
    Context context;
    Dialog dialog;
    private Boolean right_image_status = false;
    private Boolean left_image_status = false;
    private ArrayList<LeftImageBean> leftImageBeens;
    private ArrayList<RightImageBean> rightImageBeens;
    private CCSprite backgroundSprite, leftboxSprite, life_heart, rightboxSprite, leftimage, rightimage, center_wheel_image, home, refresh, center_white_image, target_image, target_text;
    private int current_image_id, life_counter;
    private int mCount;
    private InterstitialAd mInterstitialAd;
    private AdView adView;
    private int counter;


    public GameLayer() {
        this.setIsTouchEnabled(true);

        context = CCDirector.sharedDirector().getActivity();
        time1 = 0;
        time2 = 0;
        displaysize = CCDirector.sharedDirector().displaySize();
        CGSX = displaysize.getWidth();
        CGSY = displaysize.getHeight();
        displaywidth = displaysize.width;
        displayheight = displaysize.height;
        _upLeftImages = new ArrayList<>();
        _upLeftBox = new ArrayList<>();
        _upRightImages = new ArrayList<>();
        _upRightBox = new ArrayList<>();
        rightImageBeens = new ArrayList<>();
        leftImageBeens = new ArrayList<>();
        imagePart = new ArrayList<>();
        life = new ArrayList<>();
        if (PreferenceConnector.readString(context, PreferenceConnector.RELOAD, "").equals("YES")) {
            current_image_id = PreferenceConnector.readInteger(context, PreferenceConnector.CURRENT_NUMBER, 0);
        } else {
            current_image_id = current_image_number();

        }
        life_counter = 2;

        //Create background Sprite
        backgroundSprite = CCSprite.sprite(CommonUtil.getImagePath("bg.png", displaysize));
        backgroundSprite.setPosition(CGPoint.ccp(0, 0));
        backgroundSprite.setTag(1);
        backgroundSprite.setAnchorPoint(0, 0);
        addChild(backgroundSprite);


        //Create center wheel Sprite
        center_wheel_image = CCSprite.sprite(CommonUtil.getImagePath("cloud.png", displaysize));
        center_wheel_image.setPosition(CGPoint.ccp(CGSX / 2, CGSY / 2));
        center_wheel_image.setAnchorPoint(0.5f, 0.5f);
        addChild(center_wheel_image);

      /*  //Create center white Sprite
        center_white_image = CCSprite.sprite(CommonUtil.getImagePath("top_circle.png", displaysize));
        center_white_image.setPosition(CGPoint.ccp(CGSX / 2, CGSY / 2));
        center_white_image.setAnchorPoint(0.5f, 0.5f);
        addChild(center_white_image);*/


        /*CCRepeatForever repeat = CCRepeatForever.action(CCRotateBy.action(6f, 360));
        center_wheel_image.runAction(repeat);*/

        leftboxSprite = CCSprite.sprite(CommonUtil.getImagePath("left_box.png", displaysize));

        //Create target Image Sprite
        target_image = CCSprite.sprite(CommonUtil.getImagePath(Util.target_image[current_image_id], displaysize));
        target_image.setPosition(CGPoint.ccp(leftboxSprite.getContentSize().getWidth() + leftboxSprite.getContentSize().getWidth() / 2, CGSY - leftboxSprite.getContentSize().getHeight() / 2));
        target_image.setAnchorPoint(0.5f, 0.5f);
        addChild(target_image);

        for (int i = 0; i < 3; i++) {
            //Create life Sprite
            life_heart = CCSprite.sprite(CommonUtil.getImagePath("life_heart.png", displaysize));
            life_heart.setPosition(CGPoint.ccp(leftboxSprite.getContentSize().getWidth() + leftboxSprite.getContentSize().getWidth() / 4 + (life_heart.getContentSize().width * i) + (20 * i), (float) (life_heart.getContentSize().getHeight() / 1.7)));
            life_heart.setAnchorPoint(0.5f, 0.5f);
            addChild(life_heart);
            life.add(life_heart);
        }


        //Create target text Sprite
        target_text = CCSprite.sprite(CommonUtil.getImagePath("target.png", displaysize));
        target_text.setPosition(CGPoint.ccp(leftboxSprite.getContentSize().getWidth() + leftboxSprite.getContentSize().getWidth() / 2, CGSY - leftboxSprite.getContentSize().getHeight()));
        target_text.setAnchorPoint(0.5f, 0.5f);
        addChild(target_text);

        //Create home button Sprite
        home = CCSprite.sprite(CommonUtil.getImagePath("home_btn.png", displaysize));
        home.setPosition(CGPoint.ccp(CGSX - leftboxSprite.getContentSize().getWidth() - home.getContentSize().getWidth() + home.getContentSize().getWidth() / 2, CGSY - home.getContentSize().getHeight() / 2));
        home.setAnchorPoint(0.5f, 0.5f);
        addChild(home);


        //Create refresh button Sprite
        refresh = CCSprite.sprite(CommonUtil.getImagePath("refresh_btn.png", displaysize));
        refresh.setPosition(CGPoint.ccp(CGSX - leftboxSprite.getContentSize().getWidth() - (2 * home.getContentSize().getWidth()) + home.getContentSize().getWidth() / 2, CGSY - home.getContentSize().getHeight() / 2));
        refresh.setAnchorPoint(0.5f, 0.5f);
        addChild(refresh);

        SoundEngine.sharedEngine().preloadEffect(context, Util.sound[current_image_id]);
        mCount = PreferenceConnector.readInteger(context, PreferenceConnector.COUNT1, 0);
        if (mCount == 1) {
            show_adMob();

        } else if (mCount == 2) {
            DOWNLOAD_dialog();
        }
        PreferenceConnector.writeInteger(context, PreferenceConnector.COUNT1, ++mCount);
        if (mCount > 2) {
            PreferenceConnector.writeInteger(context, PreferenceConnector.COUNT1, 0);
        }
        this.schedule("gameLogic", 0.75f);

    }

    public static CCScene scene() {
        scene = CCScene.node();
        StartActivity.TAG = "second_layer";
        layer = new GameLayer();
        scene.addChild(layer);

        return scene;
    }

    @Override
    public boolean ccTouchesEnded(MotionEvent event) {
        CGPoint location = CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(), event.getY()));

        if (CGRect.containsPoint(home.getBoundingBox(), location)) {
            ((Activity) context).finish();
        }
        if (CGRect.containsPoint(refresh.getBoundingBox(), location)) {
            PreferenceConnector.writeInteger(context, PreferenceConnector.CURRENT_NUMBER, current_image_id);
            PreferenceConnector.writeString(context, PreferenceConnector.RELOAD, "YES");
            scene.removeAllChildren(true);
            layer = new GameLayer();
            scene.addChild(layer);
        }

        for (int i = 0; i < leftImageBeens.size(); i++) {
            if (CGRect.containsPoint((leftImageBeens.get(i).getBoxSprite().getBoundingBox()), location) && !left_image_status) {
                if (leftImageBeens.get(i).getId() == current_image_id) {
                    CGRect targetRect = CGRect.make(leftImageBeens.get(i).getImageSprite().getPosition().x - (leftImageBeens.get(i).getImageSprite().getContentSize().width), leftImageBeens.get(i).getImageSprite().getPosition().y - (leftImageBeens.get(i).getImageSprite().getContentSize().height),
                            leftImageBeens.get(i).getImageSprite().getContentSize().width, leftImageBeens.get(i).getImageSprite().getContentSize().height);
                    float rectUX = targetRect.origin.x + targetRect.size.width;
                    float rectUY = targetRect.origin.y + targetRect.size.height;
                    CCSprite ccSprite = CCSprite.sprite(CommonUtil.getImagePath(Util.left_image[current_image_id], displaysize));
                    ccSprite.setPosition(CGPoint.ccp(leftImageBeens.get(i).getImageSprite().getPosition().x, leftImageBeens.get(i).getImageSprite().getPosition().y));
                    ccSprite.setAnchorPoint(0.5f, 0.5f);
                    addChild(ccSprite);
                    correctItemMoveLeft(rectUX, rectUY, ccSprite);
                    removeChild(leftImageBeens.get(i).getImageSprite(), true);
                } else {
                    if (life_counter == -1) {
                        showFailAlert();
                    } else {
                        life.get(life_counter).setColor(ccColor3B.ccBLACK);
                        life_counter--;
                    }
                }

            }
        }


        for (int i = 0; i < rightImageBeens.size(); i++) {
            if (CGRect.containsPoint((rightImageBeens.get(i).getBoxSprite().getBoundingBox()), location) && !right_image_status) {
                if (rightImageBeens.get(i).getId() == current_image_id) {
                    CGRect targetRect = CGRect.make(rightImageBeens.get(i).getImageSprite().getPosition().x - (rightImageBeens.get(i).getImageSprite().getContentSize().width), rightImageBeens.get(i).getImageSprite().getPosition().y - (rightImageBeens.get(i).getImageSprite().getContentSize().height),
                            rightImageBeens.get(i).getImageSprite().getContentSize().width, rightImageBeens.get(i).getImageSprite().getContentSize().height);
                    float rectUX = targetRect.origin.x + targetRect.size.width;
                    float rectUY = targetRect.origin.y + targetRect.size.height;

                    CCSprite ccSprite = CCSprite.sprite(CommonUtil.getImagePath(Util.right_image[current_image_id], displaysize));
                    ccSprite.setPosition(CGPoint.ccp(rightImageBeens.get(i).getImageSprite().getPosition().x, rightImageBeens.get(i).getImageSprite().getPosition().y));
                    ccSprite.setAnchorPoint(0.5f, 0.5f);
                    addChild(ccSprite);
                    correctItemMoveRight(rectUX, rectUY, ccSprite);
                    removeChild(rightImageBeens.get(i).getImageSprite(), true);

                } else {
                    if (life_counter == -1) {
                        showFailAlert();
                    } else {
                        life.get(life_counter).setColor(ccColor3B.ccBLACK);
                        life_counter--;
                    }
                }
            } else {


            }

        }


        return true;
    }


    public void gameLogic(float dt) {
        addTarget();

    }


    protected void addTarget() {
        int firstImageId = 0;
        int secondImageId = 0;
        time1 = time1 + 1;
        time2 = time2 + 1;
        if (time1 >= 20) {
            firstImageId = current_image_id;
            time1 = 0;

        } else {
            firstImageId = current_image_number();
        }


        if (time2 >= 24) {
            secondImageId = current_image_id;
            time2 = 0;
        } else {

            secondImageId = current_image_number();
        }


        //Create left box Sprite
        leftboxSprite = CCSprite.sprite(CommonUtil.getImagePath("left_box.png", displaysize));
        leftboxSprite.setPosition(CGPoint.ccp(0, -leftboxSprite.getContentSize().getHeight()));
        leftboxSprite.setAnchorPoint(0, 0);


        //Create left image Sprite
        leftimage = CCSprite.sprite(CommonUtil.getImagePath(Util.left_image[firstImageId], displaysize));
        leftimage.setPosition(CGPoint.ccp(leftboxSprite.getContentSize().getWidth() / 2, (float) (-leftboxSprite.getContentSize().getHeight() / 2)));
        leftimage.setAnchorPoint(0.5f, 0.5f);


        //Create right box Sprite
        rightboxSprite = CCSprite.sprite(CommonUtil.getImagePath("right_box.png", displaysize));
        rightboxSprite.setPosition(CGPoint.ccp(displaywidth - leftboxSprite.getContentSize().getWidth(), -leftboxSprite.getContentSize().getHeight()));
        rightboxSprite.setAnchorPoint(0, 0);


        //Create right image Sprite
        rightimage = CCSprite.sprite(CommonUtil.getImagePath(Util.right_image[secondImageId], displaysize));
        rightimage.setPosition(CGPoint.ccp(displaywidth - leftboxSprite.getContentSize().getWidth() / 2, (float) (-leftboxSprite.getContentSize().getHeight() / 2)));
        rightimage.setAnchorPoint(0.5f, 0.5f);


        addChild(leftboxSprite);
        _upLeftBox.add(leftboxSprite);
        addChild(rightboxSprite);
        _upRightBox.add(rightboxSprite);
        addChild(leftimage);
        _upLeftImages.add(leftimage);
        addChild(rightimage);
        _upRightImages.add(rightimage);

        LeftImageBean leftImageBean = new LeftImageBean(leftimage, leftboxSprite, firstImageId, false);
        leftImageBeens.add(leftImageBean);
        RightImageBean rightImageBean = new RightImageBean(rightimage, rightboxSprite, secondImageId, false);
        rightImageBeens.add(rightImageBean);

        //function call for move srites
        leftSpriteMoveDownward(leftboxSprite);
        leftImageSpriteMoveDownward(leftimage);
        rightSpriteMoveDownward(rightboxSprite);
        rightImageSpriteMoveDownward(rightimage);

    }

    //
    public int current_image_number() {
        Random rd = new Random();
        return rd.nextInt(11);
    }

    //left box sprite move
    public void leftSpriteMoveDownward(CCSprite object) {
        float iconX = object.getPosition().x;
        float realY = CGSY;
        CGPoint realDest = CGPoint.ccp(iconX, realY);
        float realMoveDuration = 5.0f;
        object.runAction(CCSequence.actions(CCMoveTo.action(realMoveDuration, realDest), CCCallFuncN.action(this, "spriteMoveFinishedDown")));


    }


    //left image sprite move
    public void leftImageSpriteMoveDownward(CCSprite object) {
        float iconX = object.getPosition().x;
        float realY = CGSY;
        CGPoint realDest = CGPoint.ccp(iconX, realY);
        float realMoveDuration = 4.55f;
        object.runAction(CCSequence.actions(CCMoveTo.action(realMoveDuration, realDest), CCCallFuncN.action(this, "spriteMoveFinishedDown")));


    }


    //left box sprite move
    public void rightSpriteMoveDownward(CCSprite object) {
        float iconX = object.getPosition().x;
        float realY = CGSY;
        CGPoint realDest = CGPoint.ccp(iconX, realY);
        float realMoveDuration = 5.0f;
        object.runAction(CCSequence.actions(CCMoveTo.action(realMoveDuration, realDest), CCCallFuncN.action(this, "spriteMoveFinishedDown")));


    }


    //left image sprite move
    public void rightImageSpriteMoveDownward(CCSprite object) {
        float iconX = object.getPosition().x;
        float realY = CGSY;
        CGPoint realDest = CGPoint.ccp(iconX, realY);
        float realMoveDuration = 4.55f;
        object.runAction(CCSequence.actions(CCMoveTo.action(realMoveDuration, realDest), CCCallFuncN.action(this, "spriteMoveFinishedDown")));


    }

    // function for move left image to center
    private void correctItemMoveLeft(float rectX, float rectY, CCSprite imageSprite) {
        CCFiniteTimeAction actionBy = CCRotateBy.action(1, 360);
        CCFiniteTimeAction actionAnotherMove = actionBy.reverse();
        imageSprite.runAction(CCSequence.actions(actionBy, actionAnotherMove));
        CGPoint realdis = CGPoint.ccp(CGSX / 2, CGSY / 2);
        imageSprite.runAction(CCSequence.actions(CCMoveTo.action(1f, realdis), CCCallFuncN.action(this, "correctLeftItemMoveFinished")));

    }

    // function for move right image to center
    private void correctItemMoveRight(float rectX, float rectY, CCSprite imageSprite) {
        CCFiniteTimeAction actionBy = CCRotateBy.action(1, 360);
        CCFiniteTimeAction actionAnotherMove = actionBy.reverse();
        imageSprite.runAction(CCSequence.actions(actionBy, actionAnotherMove));
        CGPoint realdis = CGPoint.ccp(CGSX / 2, CGSY / 2);
        imageSprite.runAction(CCSequence.actions(CCMoveTo.action(1f, realdis), CCCallFuncN.action(this, "correctRightItemMoveFinished")));

    }


    public void correctLeftItemMoveFinished(Object sender1) {
        CCSprite labelbottom = (CCSprite) sender1;
        imagePart.add(labelbottom);
        left_image_status = true;
        load_sprite();
    }

    public void correctRightItemMoveFinished(Object sender1) {
        CCSprite labelbottom = (CCSprite) sender1;
        imagePart.add(labelbottom);
        right_image_status = true;
        load_sprite();
    }

    //remove left and right image after reach center og the screen
    public void spriteMoveFinishedDown(Object sender) {
        CCSprite sprite = (CCSprite) sender;
        this.removeChild(sprite, true);
        for (int i = 0; i < leftImageBeens.size(); i++) {
            if (sprite == leftImageBeens.get(i).getImageSprite()) {
                leftImageBeens.get(i).setStatus(true);
                leftImageBeens.remove(i);
                _upLeftBox.remove(i);
                _upLeftImages.remove(i);
            }
        }

        for (int i = 0; i < rightImageBeens.size(); i++) {
            if (sprite == rightImageBeens.get(i).getImageSprite()) {
                rightImageBeens.get(i).setStatus(true);
                rightImageBeens.remove(i);
                _upRightBox.remove(i);
                _upRightImages.remove(i);
            }
        }

    }

    //add new big image after click all the part image
    void load_sprite() {
        Runnable task2 = new Runnable() {
            public void run() {
                if (right_image_status && left_image_status) {
                    removeChild(imagePart.get(0), true);
                    removeChild(imagePart.get(1), true);
                    CCSprite bigImage = CCSprite.sprite(CommonUtil.getImagePath(Util.big_image[current_image_id], displaysize));
                    bigImage.setPosition(CGPoint.ccp(displaywidth / 2, displayheight / 2));
                    bigImage.setAnchorPoint(0.5f, 0.35f);
                    addChild(bigImage);
                    SoundEngine.sharedEngine().playEffect(context, Util.sound[current_image_id]);
                    CCFiniteTimeAction actionBy = CCScaleBy.action(2, 1.2f);
                    CCFiniteTimeAction actionAnotherMove = actionBy.reverse();
                    bigImage.runAction(CCSequence.actions(actionBy, actionAnotherMove));
                    show_popup();
                }
            }
        };
        worker2.schedule(task2, 0, TimeUnit.MICROSECONDS);
    }


    //Reload the game
    void reload_Game() {

        Runnable task3 = new Runnable() {
            public void run() {
                dialog.dismiss();
                scene.removeAllChildren(true);
                layer = new GameLayer();
                scene.addChild(layer);

            }
        };
        worker.schedule(task3, 3, TimeUnit.SECONDS);
    }

    //Show popup after complete the game
    void show_popup() {

        Runnable task2 = new Runnable() {
            public void run() {
                showAlert();
            }
        };
        worker1.schedule(task2, 5, TimeUnit.SECONDS);
    }

    //generate popup
    public void showAlert() {
        CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
            public void run() {
                dialog = new Dialog(CCDirector.sharedDirector().getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                dialog.setContentView(R.layout.popup_dialog);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                dialog.show();
                PreferenceConnector.writeString(context, PreferenceConnector.RELOAD, "NO");
                reload_Game();
            }
        });
    }


    //gameover popup
    public void showFailAlert() {
        CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
            public void run() {
                final Dialog faildialog = new Dialog(CCDirector.sharedDirector().getActivity());
                faildialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                faildialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                faildialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                faildialog.setContentView(R.layout.game_over_dialog);
                faildialog.setCancelable(false);
                faildialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams lp = faildialog.getWindow().getAttributes();
                faildialog.getWindow().setAttributes(lp);
                faildialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                CustomTextView customTextView = (CustomTextView) faildialog.findViewById(R.id.game_over);
                faildialog.show();

                customTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PreferenceConnector.writeString(context, PreferenceConnector.RELOAD, "YES");
                        PreferenceConnector.writeInteger(context, PreferenceConnector.CURRENT_NUMBER, current_image_id);
                        faildialog.dismiss();
                        scene.removeAllChildren(true);
                        layer = new GameLayer();
                        scene.addChild(layer);
                    }
                });

            }
        });
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    private void show_adMob() {
        CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
            public void run() {
                mInterstitialAd = new InterstitialAd(context);
                mInterstitialAd.setAdUnitId(context.getString(R.string.interstitialAd));
                PreferenceConnector.writeInteger(context, PreferenceConnector.COUNT1, 0);
                requestNewInterstitial();
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        requestNewInterstitial();
                    }
                });
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();

                        mInterstitialAd.show();
                    }
                });
            }
        });
    }


    //DOWNLOAD DIALOG
    public void DOWNLOAD_dialog() {
        CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
            public void run() {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                dialog.setContentView(R.layout.ad_popup);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                RelativeLayout layout = (RelativeLayout) dialog.findViewById(R.id.layout);
                ImageView close = (ImageView) dialog.findViewById(R.id.close);
                ImageView download = (ImageView) dialog.findViewById(R.id.download);
                // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                counter = PreferenceConnector.readInteger(context, PreferenceConnector.COUNTER, 0);
                if (counter == 24) {
                    counter = 0;
                }
                layout.setBackgroundResource(Constant.splash[counter]);

                dialog.show();


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PreferenceConnector.writeInteger(context, PreferenceConnector.COUNTER, ++counter);
                        dialog.dismiss();

                    }
                });

                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Constant.packagename[counter])));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + Constant.packagename[counter])));
                        }

                        PreferenceConnector.writeInteger(context, PreferenceConnector.COUNTER, ++counter);
                        dialog.dismiss();

                    }
                });
            }
        });
    }
}



