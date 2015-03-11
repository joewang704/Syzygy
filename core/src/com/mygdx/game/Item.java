package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by wojang on 3/6/15.
 */
public class Item {
    private int id, activeId;
    private String type;
    private Texture itemImage;
    public Item(int id, int activeId, String type, String itemPath) {
        this.id = id;
        this.activeId = activeId;
        this.type = type;
        itemImage = new Texture(Gdx.files.internal(itemPath));
    }
    public Texture getItemImage() {
        return itemImage;
    }
}
