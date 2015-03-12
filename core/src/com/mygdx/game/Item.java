package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by wojang on 3/6/15.
 */
public class Item {
    private int id, activeId;
    private String type, desc, name;
    private Texture itemImage;
    private int atk, def, spd;
    private boolean equipped = false;
    public Item(int id) {
        this.id = id;
    }
    public Item(int id, int activeId, String type, String itemPath) {
        this.id = id;
        this.activeId = activeId;
        this.type = type;
        itemImage = new Texture(Gdx.files.internal(itemPath));
    }
    public void setImageFromPath(String itemPath) {
        itemImage = new Texture(Gdx.files.internal(itemPath));
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setAtk(int atk) {
        this.atk = atk;
    }
    public void setDef(int def) {
        this.def = def;
    }
    public void setSpd(int spd) {
        this.spd = spd;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEquipped(boolean equipped) { this.equipped = equipped; }
    public boolean getEquipped() { return equipped; }
    public int getAtk() {
        return atk;
    }
    public int getDef() {
        return def;
    }
    public int getSpd() {
        return spd;
    }
    public String getName() {
        return name;
    }
    public Texture getItemImage() {
        return itemImage;
    }
}
