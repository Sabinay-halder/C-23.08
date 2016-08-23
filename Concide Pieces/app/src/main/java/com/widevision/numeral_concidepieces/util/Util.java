package com.widevision.numeral_concidepieces.util;


import com.widevision.numeral_concidepieces.R;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

public class Util {
    CGSize cgs = CCDirector.sharedDirector().displaySize();
    public static float generalscalefactor = 1.5f;

    public static float multiplyFactor = 1.0f;
    public static String[] target_image = {"0_target.png", "1_target.png", "2_target.png", "3_target.png", "4_target.png", "5_target.png", "6_target.png", "7_target.png", "8_target.png", "9_target.png", "10_target.png"};
    public static String[] left_image = {"0_left.png", "1_left.png", "2_left.png", "3_left.png", "4_left.png", "5_left.png", "6_left.png", "7_left.png", "8_left.png", "9_left.png", "10_left.png"};
    public static String[] right_image = {"0_right.png", "1_right.png", "2_right.png", "3_right.png", "4_right.png", "5_right.png", "6_right.png", "7_right.png", "8_right.png", "9_right.png", "10_right.png"};
    public static String[] big_image = {"0_big.png", "1_big.png", "2_big.png", "3_big.png", "4_big.png", "5_big.png", "6_big.png", "7_big.png", "8_big.png", "9_big.png", "10_big.png"};
    public static String[] word = {"aeroplane", "bear", "bird_house", "bowl", "brinjal", "bus", "camel", "car", "cheese", "cherry", "chrismas_stick", "cow", "crocodile", "diamond", "dog_house", "doll", "duck", "elephant", "fish", "foot_ball", "fox_2", "fox", "green_bird", "hammer", "hoppo", "horse", "hot_balloon", "kangaroo", "kulhadi", "lady_bug", "big_shoes", "lepord", "lion", "lips", "lorry", "magnet", "mashroom", "monkey", "ostrich", "ostrich_2", "owl", "perfume_bottle", "phawda", "pink_flower", "red_flower", "saras", "snaile", "snow_man", "squirel_2", "squirel", "sun", "tortoise", "warm", "water_pot"};
    public static int[] sound = {R.raw.aeroplane, R.raw.bear, R.raw.bird_house, R.raw.boal, R.raw.brinjal, R.raw.bus, R.raw.camel, R.raw.car, R.raw.cheese, R.raw.strawberry, R.raw.christmas_stick, R.raw.cow, R.raw.crocodile, R.raw.diamond, R.raw.dog_house, R.raw.doll, R.raw.duck, R.raw.elephant, R.raw.fish, R.raw.foot_ball, R.raw.fox, R.raw.fox, R.raw.bird, R.raw.hammer, R.raw.hippo, R.raw.horse, R.raw.hot_balloon, R.raw.kangaroo, R.raw.axe, R.raw.lady_bug, R.raw.shoes, R.raw.lepord, R.raw.lion, R.raw.lips, R.raw.lorry, R.raw.magnet, R.raw.mushroom, R.raw.monkey, R.raw.ostrich, R.raw.ostrich, R.raw.owl, R.raw.perfume, R.raw.spade, R.raw.flower, R.raw.flower, R.raw.crane, R.raw.caterpiler, R.raw.snowman, R.raw.rabbit, R.raw.sqirrel, R.raw.sun, R.raw.tortoise, R.raw.worm, R.raw.woter_pot};

}

