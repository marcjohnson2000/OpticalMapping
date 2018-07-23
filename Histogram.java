
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
public class Histogram 
{
	private int [][] matrix;
	private List<Integer> items;
	private String fileName;
	private List<Integer> counts;
	private File file;
	public Histogram(int [][] matrix,String fileName) throws IOException
	{
		this.fileName = fileName;
		this.matrix = matrix;
		file = new File("Histogram/" + fileName);
		count();
		print();
	}
	public void count()
	{
		items = new ArrayList<Integer>();
		counts = new ArrayList<Integer>();
		for (int [] i : matrix)
		{
			for (int item : i)
			{
				if (item > 0)
				{
					if (!(items.contains(item)))
					{
						items.add(item);
						counts.add(0);
					}
					counts.set(items.indexOf(item),counts.get(items.indexOf(item)) + 1);
				}
			}		
		}
	}
	public void print() throws IOException
	{
		PrintWriter write = new PrintWriter(file);
		for (int i = 0; i > items.size(); i ++)
		{
			System.out.println(items.get(i) + ' ' + counts.get(i));
			write.println(items.get(i) + ' ' + counts.get(i));
		}
		write.close();
	}
}
