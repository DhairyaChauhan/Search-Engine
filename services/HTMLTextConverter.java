package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class HTMLTextConverter {

//This method converts HTML Files into text documents.
	public static void convertHtmlToText(String projectDir)
			throws IOException, FileNotFoundException, NullPointerException {
		System.out.println("==>>Converting into text from pre-processing HTML files...");
		org.jsoup.nodes.Document doc = null;
		BufferedWriter out = null;
		
		try {
			File dir = new File(projectDir+"\\src\\HTMLfiles\\");
			File[] fileArray = dir.listFiles();
			for (File file : fileArray) {
				doc = Jsoup.parse(file, "UTF-8");
				String str = file.getName().substring(0, file.getName().lastIndexOf('.'));
				out = new BufferedWriter(
						new FileWriter(projectDir+"\\src\\ConvertedTextFiles\\"
								+ str  + ".txt"));
				out.write(fileCreator(file));
				out.close();
			}
		} catch (Exception e) {
			System.out.println(">>Exception on HTMLTextConverted.convertHtmlToText:"+e);
			// TODO: handle exception
		}
	}
	
	public static String fileCreator(File filePath) {
		String data="";
		try
		{
			BufferedReader Object = new BufferedReader(new FileReader(filePath));
			String line = null;

			while ((line = Object.readLine()) != null){
				data+=" "+line;
			}
			Object.close();

		}
		catch(Exception e)
		{
			System.out.println(">>Exception on HTMLTextConverted.fileConvertor:"+e);
		}
		
		return data;
	}
	private HashSet<String> links;

    public HTMLTextConverter() {
        links = new HashSet<String>();
    }

    public void getHTMLLinks(String URL,String projectDir) {

        if (!links.contains(URL)) {
            try {
               // If URL is not present then add into index
                if (links.add(URL)) {
                    System.out.println(URL);
                }

                //gets the HTML code
                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");
                
                for (Element page : linksOnPage) {
                	getHTMLLinks(page.attr("abs:href"), projectDir);
                    File file = new File(projectDir+"\\src\\HTMLfiles\\"+document.title()+".html");
                    file.getParentFile().mkdir();
                    PrintWriter out = new PrintWriter(file);
                    
                    try {
                    	String temp = document.html();
                    	out.write(temp);
                    }catch(Exception e) {
                    	
                    }out.close();
 
               }
                File file = new File(projectDir+"\\src\\HTMLfiles\\"+document.title()+".html");
                file.getParentFile().mkdir();
                PrintWriter out = new PrintWriter(file);
                
                try {
                	String temp = document.html();
                	out.write(temp);
                }catch(Exception e) {
                	
                }
                out.close();
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }
}