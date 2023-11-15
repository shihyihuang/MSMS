package org.fit5136.e;

import java.util.ArrayList;

/**
 * Class which is for storing the branch favorite information.
 *
 * @version 1.0.0
 */
public class BranchFav extends Favorite {
    private ArrayList<Branch> branchList;

    public BranchFav() {
        branchList = new ArrayList<Branch>();
    }

    public BranchFav(ArrayList<Branch> branchList) {
        this.branchList = branchList;
    }

    public void addBranch(Branch branch) {
        branchList.add(branch);
    }

    public void removeBranch(Branch branch) {
        branchList.remove(branch);
    }

    public ArrayList<Branch> getBranchList() {
        return branchList;
    }

    public void setBranchList(ArrayList<Branch> branchList) {
        this.branchList = branchList;
    }

    @Override
    public String toString() {
        return "BranchFav{" +
                "branchList=" + branchList +
                '}';
    }
}
