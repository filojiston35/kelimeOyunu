/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hizliparmaklar;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author Yusuf
 */
public class words {
    private static ArrayList<String> words=new ArrayList<String>();

    public words()
    {
        KelimeleriYaz();
        KelimeleriGetir();
    }
    public void KelimeleriYaz()
    {
        try
        {
         String[] newWords={"deneme","merhaba","tamam","nerede","kim","almak","sen","ev","arka","okul","ben","şimdi","zaman","çalışma","ders","kaçak","patates","domates","biber","yumurta",
         "ekmek","çay","kahve","kapı","kedi","köpek","soba","muz","elma","bardak"};
          File file = new File("words2.txt");
           FileWriter fw = new FileWriter(file);
           BufferedWriter bw = new BufferedWriter(fw);
           for(int i=0;i<newWords.length;i++)
               bw.write(newWords[i]+"\n");

           bw.close();
         }
        catch(IOException e)
        {
             System.out.println("Dosya yazma başarısız.");
        }
    }
    public void KelimeleriGetir()
    {
         try 
       {         
           File fileDir = new File("words2.txt");

            BufferedReader br = new BufferedReader(
           new InputStreamReader(new FileInputStream(fileDir), "UTF-8"));
            

           String temp;
           while((temp=br.readLine())!=null)
               words.add(temp);

           br.close();
       } 
       catch (IOException e) 
       {
           System.out.println("Dosya Okunamadı.");
       }
    }

    public static ArrayList<String> getWords() {
        return words;
    }
  
}
