package com.mygdx.game;

import com.badlogic.gdx.utils.XmlWriter;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by wojang on 3/9/15.
 */
public class ItemsXMLWriter extends StringWriter {
    public static void createItems() throws IOException{
        StringWriter writer = new StringWriter();
        XmlWriter xml = new XmlWriter(writer);
        xml.element("meow")
                .attribute("moo", "cow")
                .element("child")
                .attribute("moo", "cow")
                .element("child")
                .attribute("moo", "cow")
                .text("XML is like violence. If it doesn't solve your problem, you're not using enough of it.")
                .pop()
                .pop()
                .pop();
        System.out.println(writer);
        
    }
}
