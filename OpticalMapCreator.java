
import java.util.Random;
public class OpticalMapCreator
{
    private int fragments;
    private double[][] opticalmap;
    private int coverage;
    public OpticalMapCreator(int fragments, int coverage)
    {
       this.fragments = fragments;
       this.coverage = coverage;
       opticalmap = generator();
    }
    public double[][] getMap()
    {
        return opticalmap;
    }
    public void setMap(double [][] opticalmap)
    {
    	this.opticalmap = opticalmap;
    }
    	
    public int getFragments() {
		return fragments;
	}
	public void setFragments(int fragments) {
		this.fragments = fragments;
	}
	public int getCoverage() {
		return coverage;
	}
	public void setCoverage(int coverage) {
		this.coverage = coverage;
	}
	public double[][] generator()
    {
        Random rand = new Random();
        double sizing = 0;
        double fragment = 0;
        double kbp = 0;
        double [][] opticalmap = new double[coverage][fragments];
        for (int i = 0; i < fragments; i ++)
        {
            fragment = (rand.nextDouble() * 39.5) + .5;
            for (int j = 0; j < coverage; j ++)
            {
               sizing = (rand.nextDouble() - .5) * (fragment * 0.05);
               kbp = round(sizing + fragment);
               opticalmap[j][i] = kbp;
            }
        }
        for (double [] row : opticalmap)
        {
            System.out.print("[");
        for (double item : row)
        {
            System.out.printf("%.3f ", item);
        }
        System.out.print("]");
        System.out.println();
        }
        return opticalmap;
    }
    public double round(double fragment)
    {
        int round = (int)(fragment * 1000);
        double newFragment = ((double)round) / 1000;
        return newFragment;
    }
}

