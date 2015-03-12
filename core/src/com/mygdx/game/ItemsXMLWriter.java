package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by wojang on 3/9/15.
 */
public class ItemsXMLWriter {
    public static void createItems() {
        FileHandle xmlItems = Gdx.files.local("Items.xml");
        FileHandle internalXmlItems = Gdx.files.internal("Items.xml");
        //only creates Items.xml once (remember this when adding in new items)
        if (!(xmlItems.exists() && xmlItems.equals(internalXmlItems)) &&
                Gdx.app.getType() == Application.ApplicationType.Android) {
            //for android copy Items.xml from internal directory into local directory
            internalXmlItems.copyTo(Gdx.files.local(internalXmlItems.file().getPath()));

            //keeping because this is useful info for later
            //includes reading from regular file and writing to xml file
            /*try {

                //Item information to be read
                FileHandle itemInfo = Gdx.files.internal("ItemsInfo.xml");
                Scanner infoReader = new Scanner(itemInfo.file());

                //Creating xml file
                xmlItems.file().createNewFile();

                //Writing to xml file from item info file
                StringWriter writer = new StringWriter();
                XmlWriter xml = new XmlWriter(writer);
                xml.element("items");

                while(infoReader.hasNextLine()) {
                    String item = infoReader.nextLine();
                    String[] splitItem = item.split(" ", 4);
                    xml.element("item");
                    xml.attribute("type", splitItem[0]);
                    xml.attribute("name", splitItem[1]);
                    xml.attribute("")
                }
                //LIST OF ALL ITEMS

                xml.element("item")
                        .attribute("type", "head")
                        .attribute("name", "bard")
                        .pop()
                        .element("item")
                        .attribute("type", "head")
                        .attribute("name", "crown")
                        .pop()
                        .element("item")
                        .attribute("type", "head")
                        .attribute("name", "full")
                        .pop()
                        .element("item")
                        .attribute("type", "head")
                        .attribute("name", "great")
                        .pop();
                xml.pop();
                xmlItems.writeString(writer.toString(), false);
                System.out.println(writer);

            } catch (IOException e) {
                System.out.println("Failed");
            }*/
        }
    }
}
