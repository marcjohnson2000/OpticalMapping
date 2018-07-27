import java.io.IOException;
public class relation_tester {
	
	public static void main(String args[]) throws IOException
	{
		int [] arr = {5,6,7,8};
		new Cluster_Creator("edited_without_outliers.csv","without_outliers_3mers_320clusters",arr,true);
	}

}
