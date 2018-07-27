
import java.util.*;
import java.io.*;
public class Cluster_Creator
{
	private boolean real;
	private double overall;
	private String fileName;
    private int [][] relationship;
    private File file;
    private String [] kmap;
    private int d;
    private int[] thresholds;
    private String output;
    private String matrixOutput;
    private File index = new File("Index_of_fragment.txt");
    private int[][] indexArray;
    
    
    public Cluster_Creator(String fileName, String output, int [] thresholds, boolean real) throws IOException
    {
    	this.real = real;
    	overall = 0;
        this.fileName = fileName;
        d = countLines();
        file = new File("cluster_rows.txt");
        relationship = new int[d][d];
        indexArray = new int[d][2];
        kmap = new String[d];
        this.thresholds = thresholds;   
        this.output = "Relations/" + output;
        matrixOutput = "Relation_Matrix/" + output;
        
        
        grouper();
        textToArray();
        if (!(real))
        {
        indexArray();
        }
        relation();
        for (int threshold : thresholds)
        {
        	Histogram histo = new Histogram(relationship,fileName);
        	print(threshold);
        }
    }
    public double getOverall()
    {
    	return overall;
    }
    public boolean getReal()
    {
    	return real;
    }
    public String getFileName()
    {
    	return fileName;
    }  
	public int[][] getRelationship() {
		return relationship;
	}
	public void setRelationship(int[][] relationship) {
		this.relationship = relationship;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String[] getKmap() {
		return kmap;
	}
	public void setKmap(String[] kmap) {
		this.kmap = kmap;
	}
	public int getD() {
		return d;
	}
	public void setD(int d) {
		this.d = d;
	}
	public int[] getThreshold() {
		return thresholds;
	}
	public void setThreshold(int [] threshold) {
		this.thresholds = threshold;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getMatrixOutput() {
		return matrixOutput;
	}
	public void setMatrixOutput(String matrixOutput) {
		this.matrixOutput = matrixOutput;
	}
	public File getIndex() {
		return index;
	}
	public void setIndex(File index) {
		this.index = index;
	}
	public int[][] getIndexArray() {
		return indexArray;
	}
	public void setIndexArray(int[][] indexArray) {
		this.indexArray = indexArray;
	}
	public int countLines() throws IOException
    {
    	System.out.println("Count Lines has started");
        String line = "";
        String rmap = "";
        String old = "";
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        scan.nextLine();
        int lines = 0;
        while (scan.hasNextLine()) 
        {
            line = scan.nextLine();
            Scanner lineReader = new Scanner(line);
            lineReader.useDelimiter(",");
            rmap = lineReader.next();
            if (lines == 0 || !(rmap.equals(old)))
            {
                lines++;
            }
            lineReader.close();
            old = rmap;
        }
        scan.close();
        System.out.println(lines);
        return lines;
    }
    public void grouper() throws IOException
    {
    	System.out.println("Grouper has started");
        PrintWriter writer = new PrintWriter(file);
        String line = "";
        String rmap = "";
        String old  = "";
        String cluster = "";
        Scanner reader= new Scanner(new File(fileName));
        reader.nextLine();
        while(reader.hasNextLine())
        {
            line = reader.nextLine();
            Scanner lineReader = new Scanner(line);
            lineReader.useDelimiter(",");
            rmap = lineReader.next();
            cluster = lineReader.next();
            if (!(rmap.equals(old)))
            {
                writer.println();
            }
            writer.print(cluster + " ");
            old = rmap;
            lineReader.close();
        }
        reader.close();
        writer.close();
    }
    public void textToArray() throws IOException
    {
    	System.out.println("Text to array has started");
    	Scanner reader = new Scanner(file);
    	for (int i = 0; i < kmap.length; i ++) 
    	{
    		kmap[i] = reader.nextLine();
    	}
    	reader.close();
    }
    public void indexArray() throws IOException
    {
    	System.out.println("indexArray has started");
    	Scanner scan = new Scanner(index);
    	boolean first = true;
    	int row = 0;
    	
    	while (scan.hasNextLine())
    	{
    		first = true;
    		Scanner lineReader = new Scanner(scan.nextLine());
    		while (lineReader.hasNext())
    		{
	    		if (first)
	    		{
	    			indexArray[row][0] = lineReader.nextInt();
	    			first = false;
	    		}
	    		if (lineReader.hasNext())
	    		{
	    			indexArray[row][1] = lineReader.nextInt();
	    		}
	    		else
	    		{
	    			indexArray[row][1] = indexArray[row][0];
	    		}
    		}
    		lineReader.close();
    		row ++;
    	}
    	scan.close();
    	
    	
    }
    public void relation() throws IOException
    {
        String line, compare, cluster, other;
        System.out.println(kmap[0]);
        for (int i = 0; i < kmap.length; i ++)
        {
        	if (i % 200 == 0)
        	{
        		System.out.printf("On row %d out of %d \n", i,d);
        	}
        	line = kmap[i];
        	Scanner reader = new Scanner(line);
        	while (reader.hasNext())
        	{
        		cluster = reader.next();
	        	for (int j = 0; j < kmap.length; j ++)
	        	{
	        		compare = kmap[j];
	        		Scanner comparer = new Scanner(compare);
	        		while (comparer.hasNext())
	        		{
	        			other = comparer.next();
	        			if (cluster.equals(other))
	        			{
	        				relationship[i][j] += 1;
	        				break;
	        			}
	        		}
	        		comparer.close();
	        	}
        	}
        	reader.close();
        }
    }
    public void print(int threshold) throws IOException
    {
    	PrintWriter relationMatrix = new PrintWriter(new File(matrixOutput + String.format("%dthreshold", threshold) + ".txt"));
    	PrintWriter relations = new PrintWriter(new File(output + String.format("%dthreshold", threshold) + ".txt"));
    	int count = 0;
    	int rmapCounter = 0;
    	int length = 0;
    	int startIndex = 0;
    	int endIndex = 0;
    	double average = 0;
    	double sum = 0;
    	int numRelated = 0;
    	for (int [] k : relationship)
    	{
    		relations.printf("rmap_%d ", count - 1);
    		for (int l : k)
    		{
    			relationMatrix.print(l + " ");
    			if (!(real))
    			{
    			length = indexArray[count][1] - indexArray[count][0] + 1;  			
	    			if (l >= threshold && !(count == rmapCounter))
	    			{
	    				if (indexArray[count][0] <= indexArray[rmapCounter][1] && indexArray[count][1] >= indexArray[rmapCounter][0])
	    				{
	    					if(indexArray[count][0] <= indexArray[rmapCounter][0])
	    					{
	    						startIndex = indexArray[rmapCounter][0];
	    					}
	    					else
	    					{
	    						startIndex = indexArray[count][0];
	    					}
	    					if (indexArray[count][1] >= indexArray[rmapCounter][1])
	    					{
	    						endIndex = indexArray[rmapCounter][1];
	    					}
	    					else
	    					{
	    						endIndex = indexArray[count][1];
	    					}
	    					average = ((double) endIndex - startIndex + 1)/((double)length);
	    				}
	    				else
	    				{
	    					average = 0;
	    				}
	    				sum += average;
	    				numRelated ++; 	
	    				relations.printf(" (rmap_%d, %.3f)", rmapCounter - 1, average);
	    			}
    			}
	    		else
	    		{
	    			if (l >= threshold && !(count == rmapCounter))
	        		{
	    				relations.printf(" rmap_%d", rmapCounter - 1);
	    				numRelated ++;
	       			}
	    		}
    			rmapCounter ++;
    		}
    		relations.println();
    		relationMatrix.println();
    		count ++;
    		rmapCounter = 0;
    	}
    	System.out.println(numRelated);
    	overall = sum/numRelated;
    	System.out.println("With a threshold of " + threshold + " " + sum/numRelated);
    	relationMatrix.close();
    	relations.close();
    	System.out.println("done");    	
    }

}
