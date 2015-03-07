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

    public static void removeBullets(Array<Bullet> bullets) {
        Iterator<Bullet> uIter = bullets.iterator();

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
        for (Enemy enemy: enemies) {
            for (Bullet bullet: User.bullets) {
                if (enemy.overlaps(bullet)) {
                    enemyNumber += enemy.hitAction();
                    bullet.remove();
                    enemies.removeValue(enemy, true);
                    User.bullets.removeValue(bullet, true);
                    System.out.println(enemyNumber);
                    --enemyNumber;
                }
            }
        }
        return enemyNumber;
    }

    public static boolean overlap (Actor first, Actor second) {
        return first.getX() < second.getX() + second.getWidth() && first.getX() + first.getWidth() > second.getX()
                && first.getY() < second.getY() + second.getHeight() && first.getY() + first.getHeight() > second.getY();
    }

    public static boolean moveToward (User user, PortalPos p) {
        return user.getDir() == p.ordinal();
    }
}