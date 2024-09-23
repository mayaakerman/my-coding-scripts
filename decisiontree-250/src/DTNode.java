import java.io.Serializable;
import java.util.ArrayList;

class DTNode implements Serializable {
    //Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
    public static final long serialVersionUID = 438L;
    boolean leaf;
    int label = -1;      // only defined if node is a leaf
    int attribute; // only defined if node is not a leaf
    double threshold;  // only defined if node is not a leaf

    DTNode left, right; //the left and right child of a particular node. (null if leaf)

    DTNode() {
        leaf = true;
        threshold = Double.MAX_VALUE;
    }


    // this method takes in a datalist (ArrayList of type datum). It returns the calling DTNode object
    // as the root of a decision tree trained using the datapoints present in the datalist variable and minSizeDatalist.
    // Also, KEEP IN MIND that the left and right child of the node correspond to "less than" and "greater than or equal to" threshold



    // This is a helper method. Given a datalist, this method returns the label that has the most
    // occurrences. In case of a tie it returns the label with the smallest value (numerically) involved in the tie.


    // This method takes in a datapoint (excluding the label) in the form of an array of type double (Datum.x) and
    // returns its corresponding label, as determined by the decision tree




    //given another DTNode object, this method checks if the tree rooted at the calling DTNode is equal to the tree rooted
    //at DTNode object passed as the parameter

    /*
    public boolean equals(Object dt2) {

        if(this == null || dt2 == null || !(dt2 instanceof DecisionTree.DTNode))
            return false;

        DecisionTree.DTNode other = (DecisionTree.DTNode) dt2;

        if (this.leaf && other.leaf) {
            return this.label == other.label;
        }

        if(this.attribute == other.attribute && this.threshold == other.threshold) {
            if (this.left != null && other.left != null)
                this.left.equals(other.left);
            else
                return false

            if(this.right != null && other.right != null)
                this.right.equals(other.right);
            else
                return false;
        }
        else
            return false;
        }
        return false;

    }

     */
    public boolean equals(Object dt2) {
        if(dt2 instanceof DTNode) {
            DTNode other = (DTNode) dt2;
            return equalsHelper(this, other);
        }
        else
            return false;
    }

    private boolean equalsHelper(DTNode node1, DTNode node2) {

        if(node1.leaf && node2.leaf) { // base case
            if(node1.label == node2.label)
                return true;
        }

        if(node1.attribute == node2.attribute && node1.threshold == node2.threshold) {
            if(node1.left != null && node1.right != null && node2.left != null && node2.right != null)
                return equalsHelper(node1.right, node2.right) && equalsHelper(node1.left, node2.left);
        }

        return false;
    }
}
