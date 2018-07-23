

import java.util.List;
public class FileParameters 
{
	private List<String> rmap;
	private int k,fragments,coverage;
	private boolean real;
	public FileParameters(List<String> rmap, int k, int fragments, int coverage,boolean real)
	{
		this.real = real;
		this.rmap = rmap;
		this.fragments = fragments;
		this.k = k;
		this.coverage = coverage;
	}
	public boolean getReal()
	{
		return real;
	}
	public int getFragments()
	{
		return fragments;
	}
	public int getK()
	{
		return k;
	}
	public int getCoverage()
	{
		return coverage;
	}
	public List<String> getRmap()
	{
		return rmap;
	}
}
