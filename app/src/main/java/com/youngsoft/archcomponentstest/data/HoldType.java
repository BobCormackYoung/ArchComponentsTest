package com.youngsoft.archcomponentstest.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "HoldType_Table")
public class HoldType {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String description;

    public HoldType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static HoldType[] populateHoldTypeData() {
        return new HoldType[]{
                new HoldType("Crimp, General", "Crimping using one of the 3 key crimp techniques (open-hand, half-crimp, or full-crimp)."),
                new HoldType("Full-Crimp, Half-Pad", "Crimping with the thumb locked over the finger-tips. Using half the finger-tip pad."),
                new HoldType("Full-Crimp, Full-Pad", "Crimping with the thumb locked over the finger-tips. Using the entire finger-tip pad."),
                new HoldType("Half-Crimp, Half-Pad", "Crimping with the thumb free and fingers locked at 90deg. Using half the finger-tip pad."),
                new HoldType("Half-Crimp, Full-Pad", "Crimping with the thumb free and fingers locked at 90deg. Using the entire finger-tip pad."),
                new HoldType("Half-Crimp, Two-pads", "Crimping with the thumb free and fingers locked at 90deg. Using the finger up-to and including past the first knuckle."),
                new HoldType("Open-Hand, Half-Pad", "Crimping with the thumb free and fingers not locked. Using half the finger-tip pad."),
                new HoldType("Open-Hand, Full-Pad", "Crimping with the thumb free and fingers not locked. Using the entire finger-tip pad."),
                new HoldType("Open-Hand, Two-pads", "Crimping with the thumb free and fingers not locked. Using the finger up-to and including past the first knuckle."),
                new HoldType("Sloper", "Leveraging the entire hand for friction when there is no definite edge."),
                new HoldType("Pinch", "Pinching a hold between fingers and thumb."),
                new HoldType("Side-Pull", "Holds held from the side, as opposed from above or below."),
                new HoldType("Under-Cling", "Holds held from underneath, as opposed from above or the side."),
                new HoldType("Pocket", "Holds resembling holes, used by inserting as many fingers as possible."),
                new HoldType("Jam", "Holds held by jamming/wedging fingers, hands, or arms inside them."),
        };
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
