package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;
/*import com.mygdx.game.Bullet;
import com.mygdx.game.Constants;
import com.mygdx.game.User;*/

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
            if (bullet.getY() + Constants.BULLET_HEIGHT < 0 ||
                    bullet.getY() > Constants.GAMESCREEN_HEIGHT ||
                    bullet.getX() + Constants.BULLET_WIDTH < 0 ||
                    bullet.getX() > Constants.GAMESCREEN_WIDTH) {
                uIter.remove();
            }
        }
    }
    //TODO update collisions because Actors are now NOT Rectangles
    public static int enemyHits(Array<Enemy> enemies, int enemyNumber) {
        for (Bullet bullet: User.userBullets) {
            for (Enemy enemy: enemies) {
                if (enemy.overlaps(bullet)) {
                    enemyNumber += enemy.hitAction();
                    bullet.remove();
                    enemies.removeValue(enemy, true);
                    User.userBullets.removeValue(bullet, true);
                    System.out.println(enemyNumber);
                    return --enemyNumber;
                }
            }
        }
        return enemyNumber;
    }

    public static boolean overlap (Actor first, Actor second) {
        return first.getX() < second.getX() + second.getWidth() && first.getX() + first.getWidth() > second.getX()
                && first.getY() < second.getY() + second.getHeight() && first.getY() + first.getHeight() > second.getY();
    }
}