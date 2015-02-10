package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * Created by wojang on 2/3/15.
 */
public class Dungeon {
    private Array<Room> roomArray;
    public Dungeon(Syzygy game, int dungeonId, int roomNumber) {
        roomArray = new Array<Room>(roomNumber);
        //makes new rooms depending on roomNumber
        roomArray.add(new Room(this, 0, 0, 7));
        for (int i = 1; i < roomNumber; i++) {
            Room newRoom = new Room(this, 7);
            //inclusive
            int randomRoomNumber = MathUtils.random(1, i) - 1;
            Room parentRoom = roomArray.get(randomRoomNumber);
            //add room above
            setPortalReferences(newRoom, parentRoom);
            roomArray.add(newRoom);
        }
    }

        public Array<Room> getRoomArray() {
            return roomArray;
        }

        private void setPortalReferences(Room newRoom, Room parentRoom) {
            boolean portalSet = false;
            while (portalSet == false) {
                int randomPortalNumber = MathUtils.random(1, 4);
                if (randomPortalNumber == 1
                        && parentRoom.getTopPortal() == null) {
                    parentRoom.setTopPortal(newRoom);
                    newRoom.setBottomPortal(parentRoom);
                    newRoom.setPosition(parentRoom.getX(), parentRoom.getY() + 1);
                    portalSet = true;
                } else if (randomPortalNumber == 2
                        && parentRoom.getLeftPortal() == null) {
                    parentRoom.setLeftPortal(newRoom);
                    newRoom.setRightPortal(parentRoom);
                    newRoom.setPosition(parentRoom.getX() - 1, parentRoom.getY());
                    portalSet = true;
                } else if (randomPortalNumber == 3
                        && parentRoom.getRightPortal() == null) {
                    parentRoom.setRightPortal(newRoom);
                    newRoom.setLeftPortal(parentRoom);
                    newRoom.setPosition(parentRoom.getX() + 1, parentRoom.getY());
                    portalSet = true;
                } else if (randomPortalNumber == 4
                        && parentRoom.getBottomPortal() == null) {
                    parentRoom.setBottomPortal(newRoom);
                    newRoom.setTopPortal(parentRoom);
                    newRoom.setPosition(parentRoom.getX(), parentRoom.getY() - 1);
                    portalSet = true;
                }
            }
        }
        /*private boolean checkAllRoomsConnected() {
        for (Room room : roomArray) {
            if (room.getUnassignedPortals() == 4) {
                return false;
            }
        }
        return true;
        }*/
    }

