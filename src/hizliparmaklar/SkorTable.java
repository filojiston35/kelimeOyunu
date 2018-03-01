/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hizliparmaklar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author Yusuf
 */
public class SkorTable {
    private String SkorSahibi;
    private String dogru;
    private String yanlis;
    private String sure;
    private String derece;
    private ArrayList<String> skorlar=new ArrayList<String>();

     public SkorTable(String SkorSahibi,String dogru,String yanlis,String sure,String derece)
     {
         this.SkorSahibi=SkorSahibi;
         this.dogru=dogru;
         this.yanlis=yanlis;
         this.sure=sure;
         this.derece=derece;
         SkorYazdir();
     }
     public SkorTable()
     {
         SkorOku();
     }
     private void SkorYazdir()
     {
          try
        {
          String bilgi=SkorSahibi+"\t"+dogru+"\t"+yanlis+"\t"+sure+"\t"+derece+"\n";
           File file = new File("skortable.txt");
           FileWriter fw = new FileWriter("skortable.txt",true);
           BufferedWriter bw = new BufferedWriter(fw);
           bw.write(bilgi);
           bw.close();
         }
        catch(IOException e)
        {
             System.out.println("Dosya yazma başarısız.");
        }
     }
     private void SkorOku()
     {
          try 
       {         
           File fileDir = new File("skortable.txt");

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "ISO-8859-9"));
            String temp;
            while((temp=br.readLine())!=null)
               skorlar.add(temp);
       } 
       catch (IOException e) 
       {
           System.out.println("Dosya Okunamadı.");
       }
     }
    public ArrayList<String> getSkorlar() {
        return skorlar;
    }
}
