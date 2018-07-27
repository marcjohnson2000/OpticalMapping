import java.io.IOException;
public class CompareTester 
{
	public static void main(String [] args) throws IOException
	{
		String file = "without_outliers_3mers_320clusters6threshold";
		new Compare("Relations/" + file + ".txt","Relations/relations_2",2503);
	}
}
