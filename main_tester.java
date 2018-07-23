
/**
 * This class calls the other classes to generate, cluster and test optical map data. 
 *
 * @author Marc Johnson
 * @version V1
 */

import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.clusterers.SimpleKMeans;
import java.util.*;
//import weka.filters.unsupervised.attribute.InterquartileRange;
//import weka.filters.unsupervised.instance.RemoveWithValues;
//import weka.filters.Filter;

import java.io.*;
import weka.core.converters.ArffSaver;
public class main_tester
{
    public static void main(String [] arg) throws Exception
    {
    	Scanner scan = new Scanner(System.in);
    	FileParameters capsule = generator();
        int k = capsule.getK(), fragments = capsule.getFragments(), coverage = capsule.getCoverage();
        List<String> rmap = capsule.getRmap();
        boolean real = capsule.getReal();
        CSVLoader source = new CSVLoader();
        source.setFile(new File("csv_kmers.csv"));
        
        System.out.print(source.getDataSet());
        Instances data = source.getDataSet();
        
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(new File("arff_kmers.arff"));
        saver.writeBatch();
        
        Reader read = new FileReader("arff_kmers.arff");
        
        data = new Instances(read);
        
        /**
        InterquartileRange outliers = new InterquartileRange();
        
        outliers.setExtremeValuesAsOutliers(true);
        outliers.setInputFormat(data);
        
        data = Filter.useFilter(data,outliers);
        RemoveWithValues remove = new RemoveWithValues();
        
        remove.setInputFormat(data);
        remove.setAttributeIndex(String.format("%d", k+1));
        remove.setNominalIndices("last");
        
        data = Filter.useFilter(data, remove);
        data.deleteAttributeAt(k + 1);
        */
        
        SimpleKMeans cluster = new SimpleKMeans();
        cluster.setSeed(101);
        cluster.setPreserveInstancesOrder(true);
        System.out.println("\nEnter the number of clusters");
        int numClusters = scan.nextInt();
        cluster.setNumClusters(numClusters);
        System.out.println("Clustering");
        
        cluster.buildClusterer(data);
        
        int [] clusters = cluster.getAssignments();
        
        String cluster_txt = printcsv(rmap,clusters);
        
        relations(cluster_txt, fragments,k,coverage,numClusters,real);
        scan.close();
    }
    public static void relations(String cluster_txt, int fragments, int k, int coverage, int numClusters,boolean real) throws IOException
    {
    	System.out.println("Print the threshold you want.");
	   	int [] thresholds = {8,10,12,14};
	   	
	   	Date date = new Date();
	   	String time = date.toString();
	   	String formatedTime = time.replaceAll(" ", "_").replaceAll(":", "-");
	   	System.out.println("Print additional details of your file. (No Spaces, Underscore different elements)");
	   	
	   	for (int threshold : thresholds)
	   	{
	   		String fileName = String.format("%dfragments_%dcoverage_%dmers_%dcluster_%dthreshold_outliers%s_%s", fragments,coverage,k,numClusters,threshold,"kept",formatedTime);
	   		new Cluster_Creator(cluster_txt,fileName,threshold,real);
	   		counting(fileName);
	   	}
    }
    public static String printcsv(List<String> rmap, int[] clusters) throws IOException
    {
    	String fileName = "testing.txt";
    	PrintWriter csvFile = new PrintWriter(new File(fileName));
    	csvFile.print("Rmap,Cluster");
    	csvFile.println();
    	for (int i = 0; i <= rmap.size() - 1; i ++)
    	{
    		csvFile.print(rmap.get(i) + ",cluster" + clusters[i]);
    		csvFile.println();
    	}
    	
    	csvFile.close();
    	return fileName;
    }
    public static FileParameters generator() throws IOException
    {
    	Scanner scan = new Scanner(System.in);
    	String file = "darshan";
    	System.out.println("Errors? Y/N");
    	String errors = scan.next();
    	int k,fragments = 0,coverage = 0;
    	boolean real = true;
    	
    	System.out.println("Please enter k");
    	k = scan.nextInt();
    	if (file == "Rmap.txt")
    	{
    	real = false;
    	System.out.println("Please enter the number of fragments");
    	fragments = scan.nextInt();
    	
    	System.out.println("Please enter the coverage");
    	coverage = scan.nextInt();
    	
        OpticalMapCreator opticalmap = new OpticalMapCreator(fragments,coverage);
        double [][] map = opticalmap.getMap();
        if (errors.equalsIgnoreCase("y"))
        {
        error_generator error = new error_generator(map);
        map = error.getMap();
        error.printErrors();
        }
        new RMapCreator(map);
    	}
        k_merCreator kmer = new k_merCreator(k,file);
        
        FileParameters encapsulate = new FileParameters(kmer.getrmap(),k,fragments,coverage,real);
        return encapsulate;
        
        
    }
    public static void counting(String fileName) throws IOException
    {
    	int count = 0;
    	Scanner scan = new Scanner(new File("Relations/"+ fileName));
    	
    	while (scan.hasNextLine())
    	{
    		scan.nextLine();
    		count --;
    	}
    	scan = new Scanner(new File("Relations/"+ fileName));
    	while (scan.hasNext())
    	{
    		scan.next();
    		count ++;
    	}
    	scan.close();
    	System.out.println(count);
    }
}

