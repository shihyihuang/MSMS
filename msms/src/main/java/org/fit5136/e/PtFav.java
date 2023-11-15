package org.fit5136.e;

import java.util.ArrayList;

/**
 * Class which is for storing the PT favorite information.
 *
 * @version 1.0.0
 */
public class PtFav extends Favorite {
    private ArrayList<Pt> ptList;

    public PtFav() {
        ptList = new ArrayList<Pt>();
    }

    public PtFav(ArrayList<Pt> ptList) {
        this.ptList = ptList;
    }

    public void addPt(Pt pt) {
        ptList.add(pt);
    }

    public void removePt(Pt pt) {
        ptList.remove(pt);
    }

    public ArrayList<Pt> getPtList() {
        return ptList;
    }

    public void setPtList(ArrayList<Pt> ptList) {
        this.ptList = ptList;
    }

    @Override
    public String toString() {
        return "PtFav{" +
                "ptList=" + ptList +
                '}';
    }
}
