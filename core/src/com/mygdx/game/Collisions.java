package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
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
    public static void enemyHits(Array<Enemy> enemies) {
        for (Bullet bullet: User.userBullets) {
            for (Enemy enemy: enemies) {
                if (bullet.getBounds().overlaps(enemy.getBounds())) {
                    enemy.clearActions();
                    enemy.clearListeners();
                    enemy.remove();
                    bullet.clearActions();
                    bullet.clearListeners();
                    bullet.remove();
                    enemies.removeValue(enemy, true);
                    User.userBullets.removeValue(bullet, true);
                }
            }
        }
    }
}