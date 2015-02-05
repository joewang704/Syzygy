package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
/*import com.mygdx.game.Bullet;
import com.mygdx.game.Constants;
import com.mygdx.game.User;*/

import java.util.Iterator;

/**
 * Created by Jay on 2/3/2015.
 * TODO update collisions because Actors are now NOT Rectangles
 * Test comment for pushing.
 */
public class Collisions {
    public static void removeBullets() {
        Iterator<Bullet> uIter = User.userBullets.iterator();
        while (uIter.hasNext()) {
            Bullet bullet = uIter.next();
            bullet.setY(200 * bullet.velocity.y * Gdx.graphics.getDeltaTime());
            bullet.setX(200 * bullet.velocity.x * Gdx.graphics.getDeltaTime());
            if (bullet.getY() + Constants.BULLET_HEIGHT < 0 || bullet.getY() > Constants.GAMESCREEN_HEIGHT) {
                uIter.remove();
            }
        }
    }
/*    //TODO update collisions because Actors are now NOT Rectangles
    public static void enemyHits(Array<Enemy> enemies) {
        for (Bullet bullet: User.userBullets) {
            Iterator<Enemy> eIter = enemies.iterator();
            while (eIter.hasNext()) {
                if (bullet.overlaps(eIter.next())) {
                    eIter.remove();
                    User.userBullets.removeValue(bullet, true);
                }
            }
        }
    }
    */
}