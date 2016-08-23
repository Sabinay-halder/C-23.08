package com.widevision.numeral_concidepieces.bean;

import org.cocos2d.nodes.CCSprite;

/**
 * Created by mercury-three on image1/6/16.
 */

public class RightImageBean {


    CCSprite imageSprite;
    CCSprite boxSprite;
    Integer id;
    Boolean status;

    public RightImageBean(CCSprite imageSprite, CCSprite boxSprite, Integer id, Boolean status) {
        this.imageSprite = imageSprite;
        this.boxSprite = boxSprite;
        this.id = id;
        this.status = status;
    }


    public CCSprite getImageSprite() {
        return imageSprite;
    }

    public void setImageSprite(CCSprite imageSprite) {
        this.imageSprite = imageSprite;
    }

    public CCSprite getBoxSprite() {
        return boxSprite;
    }

    public void setBoxSprite(CCSprite boxSprite) {
        this.boxSprite = boxSprite;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }




}
