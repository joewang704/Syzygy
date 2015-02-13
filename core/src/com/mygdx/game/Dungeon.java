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

    public Dungeon(Syzygy game, int dungeonId, int numberOfRooms) {
        dungeonMap = new HashMap();
        unassignedPortals = new Array();
        //make room0
        Room firstRoom = new Room(this, 0, 0, 2);
        addUnassignedPortals(firstRoom);

        dungeonMap.put(firstRoom.getPosition(), firstRoom);

        //creates
        for (int i = 1; i < numberOfRooms; i++) {
            Portal randomPortal = unassignedPortals.get(MathUtils.random(unassignedPortals.size - 1));
            Vector2 currentRoomPos = randomPortal.getCurrentRoom().getPosition();
            Room newRoom = new Room(this, 99, 99);

            //if statements to check for which portal we refer to
            if (randomPortal.getPortalPos() == PortalPos.UP) {
                //line 1 creates new room at the new positions
                newRoom = new Room(this, (int) currentRoomPos.x, (int) currentRoomPos.y + 1);
                //line 2 sets the portal to the room
                randomPortal.setNextRoom(newRoom);
                //line 3 sets the room's equivalent portal back to the current room
                newRoom.getBottomPortal().setNextRoom(randomPortal.getCurrentRoom());

            } else if (randomPortal.getPortalPos() == PortalPos.LEFT) {
                newRoom = new Room(this, (int) currentRoomPos.x - 1, (int) currentRoomPos.y);
                randomPortal.setNextRoom(newRoom);
                newRoom.getRightPortal().setNextRoom(randomPortal.getCurrentRoom());

            } else if (randomPortal.getPortalPos() == PortalPos.RIGHT) {
                newRoom = new Room(this, (int) currentRoomPos.x + 1, (int) currentRoomPos.y);
                randomPortal.setNextRoom(newRoom);
                newRoom.getLeftPortal().setNextRoom(randomPortal.getCurrentRoom());

            } else if (randomPortal.getPortalPos() == PortalPos.DOWN) {
                newRoom = new Room(this, (int) currentRoomPos.x, (int) currentRoomPos.y - 1);
                randomPortal.setNextRoom(newRoom);
                newRoom.getTopPortal().setNextRoom(randomPortal.getCurrentRoom());
            }

            //remove the changed portal from the portal array
            unassignedPortals.removeValue(randomPortal, true);

            //if PortalPos() isn't set this could return a null pointer
            addUnassignedPortals(newRoom);
            dungeonMap.put(newRoom.getPosition(), newRoom);
        }
    }

    public void addUnassignedPortals(Room room) {
        for (Portal p: room.getPortals()) {
            if (!p.isVisible()) {
                unassignedPortals.add(p);
            }
        }
    }

//        private void setPortalReferences(Room newRoom, Room parentRoom) {
//            int randomPortalNumber = MathUtils.random(1, 4);
//            if (randomPortalNumber == 1
//                    && parentRoom.getTopPortal() == null) {
//                parentRoom.setTopPortal(newRoom);
//                newRoom.setBottomPortal(parentRoom);
//                newRoom.setPosition(parentRoom.getX(), parentRoom.getY() + 1);
//                portalSet = true;
//            } else if (randomPortalNumber == 2
//                    && parentRoom.getLeftPortal() == null) {
//                parentRoom.setLeftPortal(newRoom);
//                newRoom.setRightPortal(parentRoom);
//                newRoom.setPosition(parentRoom.getX() - 1, parentRoom.getY());
//                portalSet = true;
//            } else if (randomPortalNumber == 3
//                    && parentRoom.getRightPortal() == null) {
//                parentRoom.setRightPortal(newRoom);
//                newRoom.setLeftPortal(parentRoom);
//                newRoom.setPosition(parentRoom.getX() + 1, parentRoom.getY());
//                portalSet = true;
//            } else if (randomPortalNumber == 4
//                    && parentRoom.getBottomPortal() == null) {
//                parentRoom.setBottomPortal(newRoom);
//                newRoom.setTopPortal(parentRoom);
//                newRoom.setPosition(parentRoom.getX(), parentRoom.getY() - 1);
//                portalSet = true;
//            }
//        }

    public HashMap<Vector2, Room> getDungeonMap() { return dungeonMap; }
        /*private boolean checkAllRoomsConnected() {
        for (Room room : dungeonMap) {
            if (room.getUnassignedPortals() == 4) {
                return false;
            }
        }
        return true;
        }*/
    }

