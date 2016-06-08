package main.weka.utils;

import java.io.*;
import weka.core.*;
import weka.clusterers.*;
import weka.filters.*;
import weka.filters.unsupervised.attribute.Remove;

public class ClassesToClusters {
  public static void main(String[] args) throws Exception {
    // load data
    Instances data = new Instances(new BufferedReader(new FileReader(args[0])));
    data.setClassIndex(data.numAttributes() - 1);

    // generate data for clusterer (w/o class)
    Remove filter = new Remove();
    filter.setAttributeIndices("" + (data.classIndex() + 1));
    filter.setInputFormat(data);
    Instances dataClusterer = Filter.useFilter(data, filter);

    // train clusterer
    EM clusterer = new EM();
    // set further options for EM, if necessary...
    clusterer.buildClusterer(dataClusterer);

    // evaluate clusterer
    ClusterEvaluation eval = new ClusterEvaluation();
    eval.setClusterer(clusterer);
    eval.evaluateClusterer(data);

    // print results
    System.out.println(eval.clusterResultsToString());
  }
}