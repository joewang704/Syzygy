package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.Array;

import java.io.IOException;

/**
 * Created by wojang on 3/9/15.
 */
public class ItemsXMLReader {
//we cannot make top level classes in Java static, must settle for making all variables and methods
//static instead
    public static Array<Item> itemArray = new Array<Item>();
    public static void parseItems() {
        XmlReader xmlReader = new XmlReader();
        FileHandle itemFile = Gdx.files.local("Items.xml");
        try {
            System.out.println(xmlReader.parse(itemFile));
            for (int i = 0; i < xmlReader.parse(itemFile).getChildCount(); i++) {
                //create new item with xml attributes as parameters and add into itemArray
                XmlReader.Element itemNode = xmlReader.parse(itemFile).getChild(i);
                Item newItem = new Item(i);
                newItem.setType(itemNode.get("type"));
                newItem.setImageFromPath("itemImages/helmets/" + itemNode.get("imgName") + ".png");
                newItem.setAtk(Integer.parseInt(itemNode.get("atk")));
                newItem.setDef(Integer.parseInt(itemNode.get("def")));
                newItem.setSpd(Integer.parseInt(itemNode.get("spd")));
                newItem.setName(itemNode.get("name"));
                itemArray.add(newItem);
            }
            System.out.println(itemArray);
        } catch (IOException e) {
            System.out.println("Error reading items");
        }
    }
}
