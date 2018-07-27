import java.io.*;
import java.util.*;
public class Compare 
{
	private int [] clusterIndex;
	private String [] clusterArray;
	private String [] otherArray;
	private int [] otherIndex;
	private String otherFile, clusterFile;
	private File cluster, other;
	private PrintWriter print;
	private int numRmaps;
	private int [][] venn;
	
	public Compare(String clusterFile, String otherFile, int numRmaps) throws IOException
	{
		this.clusterFile = clusterFile;
		this.otherFile = otherFile;
		this.numRmaps = numRmaps;
		venn = new int[numRmaps][3];
		
		cluster = new File(clusterFile);
		other = new File(otherFile);
		print = new PrintWriter(new File("Comparison.txt"));
		
		int clusterLines = countLines(cluster);
		int otherLines = countLines(other);
		
		clusterArray = new String[clusterLines];
		clusterIndex = new int[clusterLines];
		String [][] clusterPacked = textToArray(clusterLines, cluster);
		for (int i = 0; i < clusterPacked[0].length; i ++)
		{
			clusterIndex[i] = Integer.parseInt(clusterPacked[0][i]);
		}
		clusterArray = clusterPacked[1];
		
		otherArray = new String[otherLines];
		otherIndex = new int[otherLines];
		String [][] otherPacked = textToArray(otherLines,other);
		for (int i = 0; i < otherPacked[0].length; i ++)
		{
			otherIndex[i] = Integer.parseInt(otherPacked[0][i]);
		}
		otherArray = otherPacked[1];
		
		compare();
		print();
	}
	public void print() throws IOException
	{
		int otherSum =0 , clusterSum = 0, bothSum = 0;
		for (int [] venn : venn)
		{
			for (int i = 0; i < venn.length; i ++)
			{
				System.out.print(venn[i] + " ");
				print.print(venn[i] + " ");
				if (i == 0)
				{
					otherSum += venn[i];
				}
				else if (i == 1)
				{
					bothSum += venn[i];
				}
				else
				{
					clusterSum += venn[i];
				}
			}
			System.out.println();
			print.println();
		}
		System.out.printf("Total of other sum: %d \n Total of Cluster sum: %d \n Total of both: %d",otherSum,clusterSum,bothSum);
		print.printf("Total of other sum: %d \n Total of Cluster sum: %d \n Total of both: %d",otherSum,clusterSum,bothSum);
	}
	public void compare() throws IOException
	{
		int index;
		int difference = clusterArray.length - otherArray.length;
		if (difference >= 0)
		{
			for (int i = 0; i < otherIndex.length; i ++)
			{
				index = Arrays.binarySearch(clusterIndex, otherIndex[i]);
				if (index != -1)
				{
					Scanner otherScanner = new Scanner(otherArray[i]);
					while(otherScanner.hasNext())
					{
						if (clusterArray[index].indexOf(" " + otherScanner.next() + " ") == -1)
						{
							venn[otherIndex[i]][0] ++;
						}
						else
						{
							venn[otherIndex[i]][1] ++;
						}
					}
					Scanner clusterScanner = new Scanner(clusterArray[index]);
					while (clusterScanner.hasNext())
					{
						if (otherArray[i].indexOf(" " + clusterScanner.next() + " ") == -1)
						{
							venn[otherIndex[i]][2] ++;
						}
					}
					clusterScanner.close();
					otherScanner.close();
				}
			}
		}
		else
		{
			for (int i = 0; i < clusterIndex.length; i ++)
			{
				index = Arrays.binarySearch(otherIndex, clusterIndex[i]);
				if (index != -1)
				{
					Scanner clusterScanner = new Scanner(clusterArray[i]);
					while(clusterScanner.hasNext())
					{
						if (otherArray[index].indexOf(" " + clusterScanner.next() + " ") == -1)
						{
							venn[clusterIndex[i]][0] ++;
						}
						else
						{
							venn[clusterIndex[i]][1] ++;
						}
					}
					Scanner otherScanner = new Scanner(otherArray[index]);
					while (otherScanner.hasNext())
					{
						if (clusterArray[index].indexOf(" " + otherScanner.next() + " ") == -1)
						{
							venn[clusterIndex[i]][2] ++;
						}
					}
					clusterScanner.close();
					otherScanner.close();
				}
			}
		}
		
		
	}
    public String [][] textToArray(int length, File file) throws IOException
    {
    	String [] index = new String[length];
    	String [] array = new String[length];
    	String [][] pack = new String[2][];
    	System.out.println("Text to array has started");
    	Scanner reader = new Scanner(file);
    	for (int i = 0; i < array.length; i ++) 
    	{
    		index[i] = reader.next().replaceAll("rmap_", "");
    		array[i] = reader.nextLine();
    	}
    	reader.close();
    	pack[0] = index;
    	pack[1] = array;
    	return pack;
    }
	public int countLines(File file) throws IOException
    {
    	System.out.println("Count Lines has started");
        String line = "";
        String rmap = "";
        String old = "";
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
}
