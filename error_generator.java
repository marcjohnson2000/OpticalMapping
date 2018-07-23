
import java.util.Arrays;
import java.util.Random;
public class error_generator 
{
	private double [][] opticalmap;
	private int countAdd, countDel;
	public error_generator(double [][] opticalmap)
	{
		this.opticalmap = opticalmap;
		error();
		print();
	}
	public double[][] getOpticalmap() {
		return opticalmap;
	}

	public void setOpticalmap(double[][] opticalmap) {
		this.opticalmap = opticalmap;
	}

	public int getCountAdd() {
		return countAdd;
	}

	public void setCountAdd(int countAdd) {
		this.countAdd = countAdd;
	}
	public int getCountDel() {
		return countDel;
	}
	public void setCountDel(int countDel) {
		this.countDel = countDel;
	}

	public double [][] getMap()
	{
		return opticalmap;
	}
	public void print()
	{
		for (double [] i : opticalmap)
		{
			for (double j : i)
			{
				System.out.print(j + " ");
			}
			System.out.println();
		}
	}
	public void printErrors()
	{
		System.out.printf("Adding: %d Deletion: %d",countAdd,countDel);
	}
	private double deletionError(double fragment1, double fragment2)
	{
		return fragment1 + fragment2;  
	}
	private double [] addingError(double fragment)
	{
		Random rand = new Random();
		double fragment1 = rand.nextDouble() * fragment;
		double fragment2 = fragment - fragment1;
		fragment1 = round(fragment1);
		fragment2 = round(fragment2);
		double [] frags = {fragment1,fragment2};
		return frags;
	}
	private void error()
	{
		Random random = new Random();
		int rand = 0;
		int rand2 = 0;
		double [] temp;
		double [] frags;
		int l = 0;
		double frag;
		for (int i = 0; i < opticalmap.length; i ++)
		{
			for (int j = 0; j < opticalmap[i].length; j ++)
			{
				rand = random.nextInt(8) + 1;
				rand2 = random.nextInt(5) + 1;
				if (rand == 8)
				{
					countAdd ++;
					frags = addingError(opticalmap[i][j]);
					temp = new double[opticalmap[i].length + 1];
					for (int k = 0; k < opticalmap[i].length; k ++)
					{
						if (k != j)
						{
							temp[l] = opticalmap[i][k];
						}
						else
						{
							temp[l] = frags[0];
							temp[l+1] = frags[1];
							l ++;
						}
						l ++;
					}
					l = 0;
				    opticalmap[i] = temp;
				}
				else if (rand2 == 5 && j != 0)
				{
					countDel ++;
					temp = new double[opticalmap[i].length - 1];
					frag = deletionError(opticalmap[i][j], opticalmap[i][j-1]);
					for (int k = 0; k < temp.length; k ++)
					{
						if (k != j-1)
						{
							temp[k] = opticalmap[i][l];
						}
						else
						{
							temp[l] = frag;
							l ++;
						}
						l ++;
					}
					l = 0;
					opticalmap[i] = temp;
				}
			}
			
		}
	}
    public double round(double fragment)
    {
        int round = (int)(fragment * 1000);
        double newFragment = ((double)round) / 1000;
        return newFragment;
    }
	@Override
	public String toString() {
		return "error_generator [opticalmap=" + Arrays.toString(opticalmap) + ", countAdd=" + countAdd + ", countDel="
				+ countDel + "]";
	}
    
}
