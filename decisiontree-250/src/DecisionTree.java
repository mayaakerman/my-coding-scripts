import java.io.Serializable;
import java.text.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DecisionTree implements Serializable {

	DTNode rootDTNode;
	int minSizeDatalist; //minimum number of datapoints that should be present in the dataset so as to initiate a split
	
	// Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
	public static final long serialVersionUID = 343L;
	
	public DecisionTree(ArrayList<Datum> datalist , int min) {
		minSizeDatalist = min;
		rootDTNode = (new DTNode()).fillDTNode(datalist);
	}

	class DTNode implements Serializable{
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
		DTNode fillDTNode(ArrayList<Datum> datalist) {

			if(datalist.size() == 1) {
				DTNode newLeaf = new DTNode();
				newLeaf.label = datalist.get(0).y;
				newLeaf.left = null;
				newLeaf.right = null;
				return newLeaf;
			}
			else if(datalist.size() >= minSizeDatalist) {

				// check if all data items have the same label
				int count = 0;
				int dataLabel = -1;

				for(Datum data : datalist) {
					if(data.y != dataLabel) {
						dataLabel = data.y;
						count ++;
					}
				}
				if(count == 1) {
					DTNode newLeaf = new DTNode();
					newLeaf.label = dataLabel;
					newLeaf.left = null;
					newLeaf.right = null;
					//System.out.println("new leaf");
					return newLeaf;
				}
				else {
					double[] attributeTest = findBestSplit(datalist);

					if(attributeTest[0]  == -1.0) {
						DTNode newLeaf = new DTNode();
						newLeaf.label = findMajority(datalist);
						newLeaf.leaf = true;
						newLeaf.left = null;
						newLeaf.right = null;
						System.out.println("new leaf");
						return newLeaf;
					}
					//split datalist into 2 subsets according to test answers
					DTNode newNode = new DTNode();
					newNode.leaf = false;
					newNode.attribute = (int) attributeTest[0];
					newNode.threshold = attributeTest[1];
					ArrayList<Datum> data1 = new ArrayList<Datum>();
					ArrayList<Datum> data2 = new ArrayList<Datum>();

					for(Datum d : datalist) {
						if(d.x[newNode.attribute] < newNode.threshold) {
							data1.add(d);
						}
						else
							data2.add(d);
					}
					newNode.left = fillDTNode(data1);
					newNode.right = fillDTNode(data2);
					return newNode;

					//System.out.println("left subtree)  size: " + data1.size());
					//System.out.println("right subtree)  size: " + data2.size());
					//System.out.println("new node:  left subtree size: " + data1.size() +"   right subtree size: " + data2.size());
				}
			}
			else {
				DTNode newLeaf = new DTNode();
				newLeaf.label = findMajority(datalist);
				newLeaf.leaf = true;
				newLeaf.left = null;
				newLeaf.right = null;
				//System.out.println("new leaf");
				return newLeaf;
			}

		}

		private double[] findBestSplit(ArrayList<Datum> datalist) {
			double bestAvgEntropy = Double.POSITIVE_INFINITY;
			int bestAttribute = -1;
			double bestThreshold = -1;

			//CALCULATE ENTROPY OF INPUT DATALIST
			double inputEntropy = calcEntropy(datalist);
			//System.out.println("size of node: " + datalist.size() + "  entropy: " + inputEntropy);

			for(int i = 0; i < datalist.get(0).x.length; i++) { //for each attribute in x
				for(Datum data : datalist) { //for each data point in the list

					//split the datalist into 2 sublists D1 and D2 using test condition (attribute)
					double testCondition = data.x[i];
					ArrayList<Datum> D1 = new ArrayList<Datum>();
					ArrayList<Datum> D2 = new ArrayList<Datum>();
					for(Datum d : datalist) {
						if(d.x[i] < testCondition)
							D1.add(d);
						else
							D2.add(d);
					}
					//calculate current avg entropy based on the split
					double w1 = (double)D1.size() / (double)datalist.size();
					double w2 = (double)D2.size() / (double)datalist.size();
					double currAvgEntropy = (w1 * calcEntropy(D1)) + (w2 * calcEntropy(D2));
					//find best avg entropy
					if(currAvgEntropy < bestAvgEntropy) {
						bestAvgEntropy = currAvgEntropy;
						bestAttribute = i;
						bestThreshold = testCondition;
					}
				}
			}
			double[] results = new double[2];
			if(bestAvgEntropy == inputEntropy) { // if min avg entropy = input datalist entropy, do nothing and create a leaf
				results[0] = -1.0;
				results[1] = -1.0;
			}
			else {
				results[0] = bestAttribute;
				results[1] = bestThreshold;
			}
			return results;
		}



		// This is a helper method. Given a datalist, this method returns the label that has the most
		// occurrences. In case of a tie it returns the label with the smallest value (numerically) involved in the tie.
		int findMajority(ArrayList<Datum> datalist) {
			
			int [] votes = new int[2];

			//loop through the data and count the occurrences of datapoints of each label
			for (Datum data : datalist)
			{
				votes[data.y]+=1;
			}
			
			if (votes[0] >= votes[1])
				return 0;
			else
				return 1;
		}




		// This method takes in a datapoint (excluding the label) in the form of an array of type double (Datum.x) and
		// returns its corresponding label, as determined by the decision tree
		int classifyAtNode(double[] xQuery) {

			if(this.leaf) { //if node is a leaf = base case
				return this.label;
			}
			else {
				if (xQuery[this.attribute] < this.threshold){
					return this.left.classifyAtNode(xQuery);
				}
				else
					return this.right.classifyAtNode(xQuery);
			}
		}


		//given another DTNode object, this method checks if the tree rooted at the calling DTNode is equal to the tree rooted
		//at DTNode object passed as the parameter
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



	//Given a dataset, this returns the entropy of the dataset
	double calcEntropy(ArrayList<Datum> datalist) {
		double entropy = 0;
		double px = 0;
		float [] counter= new float[2];
		if (datalist.size()==0)
			return 0;
		double num0 = 0.00000001,num1 = 0.000000001;

		//calculates the number of points belonging to each of the labels
		for (Datum d : datalist)
		{
			counter[d.y]+=1;
		}
		//calculates the entropy using the formula specified in the document
		for (int i = 0 ; i< counter.length ; i++)
		{
			if (counter[i]>0)
			{
				px = counter[i]/datalist.size();
				entropy -= (px*Math.log(px)/Math.log(2));
			}
		}

		return entropy;
	}


	// given a datapoint (without the label) calls the DTNode.classifyAtNode() on the rootnode of the calling DecisionTree object
	int classify(double[] xQuery ) {
		return this.rootDTNode.classifyAtNode( xQuery );
	}

	// Checks the performance of a DecisionTree on a dataset
	// This method is provided in case you would like to compare your
	// results with the reference values provided in the PDF in the Data
	// section of the PDF
	String checkPerformance( ArrayList<Datum> datalist) {
		DecimalFormat df = new DecimalFormat("0.000");
		float total = datalist.size();
		float count = 0;

		for (int s = 0 ; s < datalist.size() ; s++) {
			double[] x = datalist.get(s).x;
			int result = datalist.get(s).y;
			if (classify(x) != result) {
				count = count + 1;
			}
		}

		return df.format((count/total));
	}


	//Given two DecisionTree objects, this method checks if both the trees are equal by
	//calling onto the DTNode.equals() method
	public static boolean equals(DecisionTree dt1,  DecisionTree dt2)
	{
		boolean flag = true;
		flag = dt1.rootDTNode.equals(dt2.rootDTNode);
		return flag;
	}

}
