package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

/**
 * Created by wojang on 2/3/15.
 */
public class Dungeon {

    private HashMap<Vector2, Room> dungeonMap;
    private Array<Portal> unassignedPortals;
    private Array<ClassName> possibleEnemies;

    //TODO populate possibleEnemies based on dungeonID
    public Dungeon(Syzygy game, int dungeonID, int numberOfRooms) {
        dungeonMap = new HashMap();
        unassignedPortals = new Array();
        //make room0
        Room firstRoom = new Room(this, 0, 0, 2);
        addUnassignedPortals(firstRoom);

        dungeonMap.put(firstRoom.getPosition(), firstRoom);

        //creates all rooms,
        for (int i = 1; i < numberOfRooms; i++) {
            Portal randomPortal = unassignedPortals.get(MathUtils.random(unassignedPortals.size - 1));
            Vector2 currentRoomPos = randomPortal.getCurrentRoom().getPosition();
            Room newRoom;

            //if statements to check for which portal we refer to
            //always check if the un linked portal chosen should refer to an already created room.
            if (randomPortal.getPortalPos() == PortalPos.UP) {
                Vector2 newPos = new Vector2(currentRoomPos.x, currentRoomPos.y + 1);
                //if the new room position already exists, link to the already existing room
                if (dungeonMap.containsKey(newPos)) {
                    randomPortal.setNextRoom(dungeonMap.get(newPos));
                    //get the linked-to room and set it's equivalent portal to currentRoom
                    dungeonMap.get(newPos).getBottomPortal().setNextRoom(randomPortal.getCurrentRoom());
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
                    randomPortal.setNextRoom(dungeonMap.get(newPos));
                    dungeonMap.get(newPos).getRightPortal().setNextRoom(randomPortal.getCurrentRoom());
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
                    randomPortal.setNextRoom(dungeonMap.get(newPos));
                    dungeonMap.get(newPos).getLeftPortal().setNextRoom(randomPortal.getCurrentRoom());
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
                    randomPortal.setNextRoom(dungeonMap.get(newPos));
                    dungeonMap.get(newPos).getTopPortal().setNextRoom(randomPortal.getCurrentRoom());
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


    private void addUnassignedPortals(Room room) {
        for (Portal p: room.getPortals()) {
            if (!p.isVisible()) {
                unassignedPortals.add(p);
            }
        }
    }

    public HashMap<Vector2, Room> getDungeonMap() { return dungeonMap; }
    }

