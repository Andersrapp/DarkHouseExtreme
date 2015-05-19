package com.bam.darkhouseextreme.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anders on 2015-04-28.
 */
public class Player implements Serializable {
    private long id;
    private String name;
    private int mapXCoordinate;
    private int mapYCoordinate;
    private List<Item> playerItems = new ArrayList<>();
    private int score;
    private boolean room01, room01a, room02, room11, room12, room13a, room13b, room21, room22, room32, room33;
    private boolean dead;

    public Player() {

    }

    public Player(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMapXCoordinate() {
        return mapXCoordinate;
    }

    public void setMapXCoordinate(int mapXCoordinate) {
        this.mapXCoordinate = mapXCoordinate;
    }

    public int getMapYCoordinate() {
        return mapYCoordinate;
    }

    public void setMapYCoordinate(int mapYCoordinate) {
        this.mapYCoordinate = mapYCoordinate;
    }

    public List<Item> getPlayerItems() {
        return playerItems;
    }

    public void setPlayerItems(List<Item> playerItems) {
        this.playerItems = playerItems;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isRoom01() {
        return room01;
    }

    public void setRoom01(boolean room01) {
        this.room01 = room01;
    }

    public boolean isRoom02() {
        return room02;
    }

    public void setRoom02(boolean room02) {
        this.room02 = room02;
    }

    public boolean isRoom11() {
        return room11;
    }

    public void setRoom11(boolean room11) {
        this.room11 = room11;
    }

    public boolean isRoom12() {
        return room12;
    }

    public void setRoom12(boolean room12) {
        this.room12 = room12;
    }

    public boolean isRoom13a() {
        return room13a;
    }

    public void setRoom13a(boolean room13a) {
        this.room13a = room13a;
    }

    public boolean isRoom13b() {
        return room13b;
    }

    public void setRoom13b(boolean room13b) {
        this.room13b = room13b;
    }

    public boolean isRoom21() {
        return room21;
    }

    public void setRoom21(boolean room21) {
        this.room21 = room21;
    }

    public boolean isRoom22() {
        return room22;
    }

    public void setRoom22(boolean room22) {
        this.room22 = room22;
    }

    public boolean isRoom32() {
        return room32;
    }

    public void setRoom32(boolean room32) {
        this.room32 = room32;
    }

    public boolean isRoom33() {
        return room33;
    }

    public void setRoom33(boolean room33) {
        this.room33 = room33;
    }

    public boolean isRoom01a() {
        return room01a;
    }

    public void setRoom01a(boolean room01a) {
        this.room01a = room01a;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (id != player.id) return false;
        if (mapXCoordinate != player.mapXCoordinate) return false;
        if (mapYCoordinate != player.mapYCoordinate) return false;
        if (score != player.score) return false;
        if (name != null ? !name.equals(player.name) : player.name != null) return false;
        return !(playerItems != null ? !playerItems.equals(player.playerItems) : player.playerItems != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + mapXCoordinate;
        result = 31 * result + mapYCoordinate;
        result = 31 * result + (playerItems != null ? playerItems.hashCode() : 0);
        result = 31 * result + score;
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mapXCoordinate=" + mapXCoordinate +
                ", mapYCoordinate=" + mapYCoordinate +
                ", playerItems=" + playerItems +
                ", score=" + score +
                '}';
    }
}
