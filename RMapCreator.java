

import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;
public class RMapCreator
{
    double [][] opticalmap;
    /**
     * Constructor for objects of class RMap
     */
    public RMapCreator(double [][] opticalmap) throws IOException
    {
       this.opticalmap = opticalmap;
       RMapGenerator();
    }
    @Override
	public String toString() {
		return "RMapCreator [opticalmap=" + Arrays.toString(opticalmap) + "]";
	}
    
	public double[][] getOpticalmap() {
		return opticalmap;
	}
	public void setOpticalmap(double[][] opticalmap) {
		this.opticalmap = opticalmap;
	}
	public void RMapGenerator() throws IOException
    {
        PrintWriter rmapFile = new PrintWriter("RMap.txt");
        PrintWriter index = new PrintWriter("Index_of_fragment.txt");
        Random rand = new Random();
        int size = rand.nextInt(10) + 10;
        int count = 0;
        int numRMap = 0;
        int coverage = 0;
        int overall = 0;
        rmapFile.printf("RMAP_%d ",numRMap);
        for (double [] row : opticalmap)
        {
            for (double fragment : row)
            {
                if (count == size)
                {
                    numRMap ++;
                    rmapFile.println();
                    rmapFile.printf("RMAP_%d ",numRMap);
                    index.println();
                    size = rand.nextInt(10) + 10;
                    count = 0;
                }
                rmapFile.printf("%.3f ",fragment);
                index.print(overall + " ");
                count ++;
                overall ++;
            }
            if (coverage == opticalmap.length - 1)
            {
                break;
            }
            rmapFile.println();
            index.println();
            numRMap ++;
            rmapFile.printf("RMAP_%d ",numRMap);
            count = 0;
            overall = 0;
            coverage ++;
        }
        rmapFile.close();
        index.close();
    }
}
