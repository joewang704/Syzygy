package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

/**
 * Created by wojang on 2/3/15.
 *
 * Rooms are no longer being potentially linked to each other. If a portal would be chosen that
 * would have a prexisting room it tries again. Can replace if needed.
 *   if (dungeonMap.containsKey(newPos)) {
 *     randomPortal.setNextRoom(dungeonMap.get(newPos));
 *     //get the linked-to room and set it's equivalent portal to currentRoom
 *     dungeonMap.get(newPos).getBottomPortal().setNextRoom(randomPortal.getCurrentRoom());
 */
public class Dungeon {

    private HashMap<Vector2, Room> dungeonMap;
    private Array<Portal> unassignedPortals;
    public Array<Class> possibleEnemies;
    public Array<Enemy> bossList;

    //TODO populate possibleEnemies based on dungeonID

    public Dungeon(Syzygy game, int dungeonID, int numberOfRooms, User user) {
        dungeonMap = new HashMap();
        unassignedPortals = new Array<Portal>();
        possibleEnemies = new Array<Class>();
        bossList = new Array<Enemy>();

        //make room0
        Room firstRoom = new Room(this, 0, 0, 2);
        addUnassignedPortals(firstRoom);
        dungeonMap.put(firstRoom.getPosition(), firstRoom);

        //make remaining rooms
        roomCreation(numberOfRooms);
        makeBossRoom(firstRoom, user, game);

        if (dungeonID == 0) {
            possibleEnemies.addAll(Enemy_BigSlime.class, Enemy_Golem.class, Enemy_Slime.class, Enemy_HitDetector.class);
            bossList.add(new Boss_Volans(user));
        }
    }

    /**
     * Adds all portals that do not point to a room to the collection of unassigned portals.
     * unassignedPortals is used in generating new rooms
     * @param room the room from which the portals are being taken
     */
    private void addUnassignedPortals(Room room) {
        for (Portal p: room.getPortals()) {
            if (!p.isVisible()) {
                unassignedPortals.add(p);
            }
        }
    }

    /**
     * Creates each room in the dungeon outside of the root room
     * @param numberOfRooms total number of rooms that will exist w/in dungeon
     */
    private void roomCreation(int numberOfRooms) {
        //creates all rooms,
        for (int i = 1; i < numberOfRooms; i++) {
            Portal randomPortal = unassignedPortals.get(MathUtils.random(unassignedPortals.size - 1));
            Vector2 currentRoomPos = randomPortal.getCurrentRoom().getPosition();
            Room newRoom;

            //if statements to check for which portal we refer to
            //always check if the un linked portal chosen should refer to an already created room.
            if (randomPortal.getPortalPos() == PortalPos.UP) {
                Vector2 newPos = new Vector2(currentRoomPos.x, currentRoomPos.y + 1);
                if (dungeonMap.containsKey(newPos)) {
                    //find another portal!
                    i--;
                } else {
                    //line 1 creates new room at the new positions
                    newRoom = new Room(this, (int) currentRoomPos.x, (int) currentRoomPos.y + 1);
                    //line 2 sets the portal to the room
                    randomPortal.setNextRoom(newRoom);
                    //line 3 sets the room's equivalent portal back to the current room
                    newRoom.getBottomPortal().setNextRoom(randomPortal.getCurrentRoom());
                    //maintain the array of unnassigned portals
                    addUnassignedPortals(newRoom);
                    //add our new room to the map
                    dungeonMap.put(newRoom.getPosition(), newRoom);
                }

            } else if (randomPortal.getPortalPos() == PortalPos.LEFT) {
                Vector2 newPos = new Vector2(currentRoomPos.x - 1, currentRoomPos.y);
                if (dungeonMap.containsKey(newPos)) {
                    //find another portal!
                    i--;
                } else {
                    newRoom = new Room(this, (int) currentRoomPos.x - 1, (int) currentRoomPos.y);
                    randomPortal.setNextRoom(newRoom);
                    newRoom.getRightPortal().setNextRoom(randomPortal.getCurrentRoom());
                    addUnassignedPortals(newRoom);
                    dungeonMap.put(newRoom.getPosition(), newRoom);
                }

            } else if (randomPortal.getPortalPos() == PortalPos.RIGHT) {
                Vector2 newPos = new Vector2(currentRoomPos.x + 1, currentRoomPos.y);
                if (dungeonMap.containsKey(newPos)) {
                    //find another portal!
                    i--;
                } else {
                    newRoom = new Room(this, (int) currentRoomPos.x + 1, (int) currentRoomPos.y);
                    randomPortal.setNextRoom(newRoom);
                    newRoom.getLeftPortal().setNextRoom(randomPortal.getCurrentRoom());
                    addUnassignedPortals(newRoom);
                    dungeonMap.put(newRoom.getPosition(), newRoom);
                }

            } else {
                Vector2 newPos = new Vector2(currentRoomPos.x, currentRoomPos.y - 1);
                if (dungeonMap.containsKey(newPos)) {
                    //find another portal!
                    i--;
                } else {
                    newRoom = new Room(this, (int) currentRoomPos.x, (int) currentRoomPos.y - 1);
                    randomPortal.setNextRoom(newRoom);
                    newRoom.getTopPortal().setNextRoom(randomPortal.getCurrentRoom());
                    addUnassignedPortals(newRoom);
                    dungeonMap.put(newRoom.getPosition(), newRoom);
                }
            }

            //remove the changed portal from the portal array
            unassignedPortals.removeValue(randomPortal, true);
        }
    }

    private void makeBossRoom(Room firstRoom, User user, Game game) {
        Room current = firstRoom;
        Room previous = null;
        int badPortalCtr = 0;
        //finds a placement for the boss room
        //TODO not really random because the navigation is placement in the order of the portals of getPortals()
        while (badPortalCtr < 4) {
            Portal portal = current.getPortals().get(Math.abs(current.hashCode() + badPortalCtr) % 4);
            if (portal != null && portal.getNextRoom() != previous && portal.isVisible()) {
                previous = current;
                current = portal.getNextRoom();
                badPortalCtr = 0;
            } else {
                badPortalCtr++;
            }
        }

        Array<Portal> oldPortals = current.getPortals();

        //should probably have a new screen for bossRooms
        current = new Room_Boss(this, current.getX(), current.getY(), user, game);
        current.setPortals(oldPortals);
        dungeonMap.put(current.getPosition(), current);

        //set all connected rooms equivalent portals back to current rooms
        for (int i = 0; i < current.getPortals().size; i++) {
            Room nextRoom = current.getPortals().get(i).getNextRoom();
            if (nextRoom != null) {
                nextRoom.getPortalByOrdinal(current.getPortals().size - 1 - i).setNextRoom(current);
            }
        }
    }

    public HashMap<Vector2, Room> getDungeonMap() { return dungeonMap; }

    //I'll save this for a rainy day.
//    private Room[] findFurthestRooms() {
//        findFurthestRoomsHelper(null, dungeonMap.get(new Vector2(0, 0)), 1);
//    }

//    private class RoomDepthPairing {
//        Room room;
//        int depth;
//
//        private RoomDepthPairing(Room room, int i) {
//            this.room = room;
//            depth = i;
//        }
//    }




    }

