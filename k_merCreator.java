

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
public class k_merCreator
{
    private int k;
    private double sum;
    private int countMer;

    private File readFile;
    private Scanner read;
    private PrintWriter writer;
    private File writeFile;
    private File csvFile;
    private PrintWriter csv;
    ArrayList<String> rmap;
    public k_merCreator(int k, String fileName) throws IOException
    {
        this.k = k;
        readFile = new File(fileName);
        read = new Scanner(readFile);
        writeFile = new File("kmers.txt");
        csvFile = new File("csv_kmers.csv");
        csv = new PrintWriter(csvFile);
        writer = new PrintWriter(writeFile);
        rmap = new ArrayList<String>();
        print();
        generator();
    }
    @Override
	public String toString() {
		return "k_merCreator [k=" + k + ", sum=" + sum + ", countMer=" + countMer + ", readFile=" + readFile + ", read="
				+ read + ", writer=" + writer + ", writeFile=" + writeFile + ", csvFile=" + csvFile + ", csv=" + csv
				+ ", rmap=" + rmap + "]";
	}
    
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	public int getCountMer() {
		return countMer;
	}
	public void setCountMer(int countMer) {
		this.countMer = countMer;
	}
	public File getReadFile() {
		return readFile;
	}
	public void setReadFile(File readFile) {
		this.readFile = readFile;
	}
	public Scanner getRead() {
		return read;
	}
	public void setRead(Scanner read) {
		this.read = read;
	}
	public PrintWriter getWriter() {
		return writer;
	}
	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}
	public File getWriteFile() {
		return writeFile;
	}
	public void setWriteFile(File writeFile) {
		this.writeFile = writeFile;
	}
	public File getCsvFile() {
		return csvFile;
	}
	public void setCsvFile(File csvFile) {
		this.csvFile = csvFile;
	}
	public PrintWriter getCsv() {
		return csv;
	}
	public void setCsv(PrintWriter csv) {
		this.csv = csv;
	}
	public ArrayList<String> getRmap() {
		return rmap;
	}
	public void setRmap(ArrayList<String> rmap) {
		this.rmap = rmap;
	}
	public ArrayList<String> getrmap()
    {
    	return rmap;
    }
    public void setrmap(ArrayList<String> rmap)
    {
    	this.rmap = rmap;
    }
    
    public void print()
    {
        csv.print("Rmap");
        writer.printf("@RELATION kmers");
        writer.println();
        for (int i = 1; i <= k; i ++)
        {
            csv.printf(",k%d",i);
            writer.printf("@ATTRIBUTE k%d REAL",i);
            writer.println();
        }
        csv.println();
        writer.println();
        writer.println("@DATA");
        writer.println();
        
    }
    public void generator()
    {
            double temp = 0;
            double [][] kmers = new double[0][0];
            int count = 0;
            String name = "";
            ArrayList<ArrayList<Double>> rmaps = new ArrayList<ArrayList<Double>>();
            while (read.hasNext())
            {
                name = read.next();
                System.out.print(name + " ");
                writer.print(name + " ");
                rmaps.add(new ArrayList<Double>());
                while (read.hasNextDouble())
                {   
                    temp = read.nextDouble();
                    System.out.print(temp + " ");
                    rmaps.get(count).add(temp);
                }
                if (rmaps.get(count).size() > k)
                {
                    kmers = kmerGenerator(rmaps.get(count), name);
                }
                for (double [] kmerSet : kmers)
                {
                    writer.print("[");
                    csv.print(name);
                    rmap.add(name);
                    for (double kmer : kmerSet)
                    {
                        csv.print("," + kmer);
                        writer.print(kmer + " ");
                    }
                    csv.println();
                    writer.print("] ");
                }
                System.out.println();
                writer.println();
                count ++;
            }
            
            int coverage = (int)sum/4600;
            
            System.out.println("Coverage: " + coverage);
            System.out.println("Fragments per Optical Map: " + countMer/coverage);
            System.out.println("Number of clusters: " + (countMer/coverage - k + 1));
            System.out.println("done");
            csv.close();
            writer.close();
    }
    public double [][] kmerGenerator(ArrayList<Double> rmap, String name)
    {
        System.out.println();
        double [][] kmers = new double[rmap.size() - k + 1][k];
        for (int i = 0; i < rmap.size() - k + 1; i ++)
        {
            System.out.print("[");
            for (int j = 0; j < k; j ++)
            {
                System.out.print(rmap.get(i + j) + " ");
                kmers[i][j] = rmap.get(i + j);
                sum += kmers[i][j];
                countMer ++;
            }
            System.out.print("]");
            System.out.println();
        }
        return kmers;
    }
}

