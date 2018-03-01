/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hizliparmaklar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;




/**
 *
 * @author Yusuf
 */
public class PlayScreen extends javax.swing.JFrame{
    public static Thread GeriSayimThread;
    public static Thread KelimeleriHareketEttirThread;
    public static Thread LabelEkleThread;
    public static Thread ZamanThread;
    private ArrayList<JLabel> labellar=new ArrayList<JLabel>();
    private static Timer myTimer;
    private int kelimeUretimHizi;
    private int kelimeHareketHizi;
    private int difficulty_level=1;
    /**
     * Creates new form PlayScreen
     */
    public PlayScreen(int difficulty_level) {
        
        initComponents();
        CenterScreen();
        BackButton(); 
        jTextField1.setEditable(false);
        this.difficulty_level=difficulty_level;
        if(difficulty_level==1)
        {
             kelimeUretimHizi=1000;
             kelimeHareketHizi=50;
        }
        if(difficulty_level==2)
        {
             kelimeUretimHizi=700;
             kelimeHareketHizi=30;
        }
        if(difficulty_level==3)
        {
             kelimeUretimHizi=500;
             kelimeHareketHizi=10;
        }
        
         GeriSayim();
         GeriSayimThread.start(); 
         
    }
    public void BackButton()
    {
        jLabel1.setIcon(new ImageIcon("image//back.png"));
    }
    public void CenterScreen()
    {
        Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2,dim.height/2-this.getSize().height/2);
    }
    public void labelEkle()
    {
        Random randY=new Random();
        Random wordRandomNumber=new Random();
        ArrayList<String> words;
        words=new words().getWords();
        ArrayList<String> kuyruk=new ArrayList<String>();//ilk 5 y koordinatı buraya eklenecek.
        kuyruk.add(Integer.toString(100));
        int i=1;
             LabelEkleThread=new Thread(){
                 int i=1;
                 @Override
                 public void run()
                 {
                     while(1!=0)
                     {
                     int wordNumb=wordRandomNumber.nextInt(words.size());
                            JLabel label=new JLabel(words.get(wordNumb));
                            label.setName("label"+Integer.toString(i));
                            label.setFont(new Font("Thoma",Font.BOLD,14));
                            
                            Random rand = new Random();
                            int r = 55+rand.nextInt(200);
                            int g = 55+rand.nextInt(200);
                            int b = 55+rand.nextInt(200);
                            Color randomColor=new Color(r,g,b);
                            
                            label.setForeground(randomColor);
                            int y=randY.nextInt(31)*16;//KELİMELERİN ÜST ÜSTE GELMEMESİ İÇİN KONTROL YAPILIYOR.
                            while(kuyruk.contains(Integer.toString(y))==true)
                                y=randY.nextInt(31)*16;
                            if(kuyruk.size()<=5)
                                kuyruk.add(Integer.toString(y));
                            else
                            {
                                kuyruk.remove(5);
                                kuyruk.add(Integer.toString(y));
                            }//KELİMLERİN ÜST ÜSTE GELMEMESİ İÇİN
                            
                            label.setBounds(-20,y,70,20);
                            labellar.add(label); 
                             i++;
                            jPanel1.add(label);
                         try {
                             Thread.sleep(kelimeUretimHizi);
                         } catch (InterruptedException ex) { }
                     }
                            
                 }
             };

    }
    public void Zaman()
    {              
             ZamanThread=new Thread(){
                 int i=0;
                 @Override
                 public void run()
                 {
                     while(1!=0)
                     {
                          jLabel4.setText(Integer.toString(i));
                            i++;
                         try {
                             Thread.sleep(1000);
                         } catch (Exception e) {}
                     }
                    
                 }
             };
    }
    public void KelimeleriHareketEttir()
    {

             KelimeleriHareketEttirThread=new Thread(){
                  int i=0;
                    int X;
                    int Y;
                    @Override
                    public void run() {
                        while(1!=0)
                        {
                         for(JLabel templabel:labellar)
                            {                              
                                X=templabel.getX();
                                 Y=templabel.getY();
                                 templabel.setLocation(X+2, Y);                          
                            }
                          try {
                                   Thread.sleep(kelimeHareketHizi);
                               } catch (InterruptedException ex) { }
                        }
                          
                           
                                           
                    }
             };
        
    }
    public void oyunBitir()
    {
        myTimer=new Timer();
             TimerTask OyunBitir =new TimerTask() {
                    @Override
                    public void run() {
                        
                       if(labellar.get(0).getX()>1267)
                        {                                                    
                           GameOverCagir();
                           myTimer.cancel();
                        }                                    
                    }
                     
             };
             myTimer.schedule(OyunBitir,0,250);
             
    }
    public void GameOverCagir()
    {
         GameOver go=new GameOver(jLabel2.getText(),jLabel3.getText(),jLabel4.getText(),difficulty_level);
         go.setVisible(true);
         KelimeleriHareketEttirThread.suspend();
         LabelEkleThread.suspend();   
         ZamanThread.suspend();
         this.enable(false);
         
    }
    public void GeriSayim()
    {
      GeriSayimThread=new Thread(){
          @Override
          public void run()
          {
              for(int i=3;i>=0;i--)
              {
                  if(i>0)
                  {
                    try {
                      Thread.sleep(1000);
                      jLabel5.setText(Integer.toString(i));
                      } catch (InterruptedException ex) { }
                  }                  
                  if(i==0)
                    {
                      try {
                          Thread.sleep(1000);
                      } catch (InterruptedException ex) {
                          Logger.getLogger(PlayScreen.class.getName()).log(Level.SEVERE, null, ex);
                      }
                      jTextField1.setEditable(true);
                      firstLabelEkle();
                      labelEkle();
                      LabelEkleThread.start();
                      Zaman();
                      ZamanThread.start();
                      KelimeleriHareketEttir();
                      KelimeleriHareketEttirThread.start();
                      oyunBitir();
                       jLabel5.setText((""));                               
                      GeriSayimThread.suspend();
                    }
              }
             
          }
      };
       
    }
    public void GameContinue()
    {
        if(jLabel5.getText()!="")
        GeriSayimThread.resume();
        else
        {
            KelimeleriHareketEttirThread.resume();
             try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                Logger.getLogger(pauseScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
             LabelEkleThread.resume();
             ZamanThread.resume();
        }
        
    }
    public void firstLabelEkle()
    {
         //-İlk kelime ataması
        JLabel firstLabel=new JLabel("ilk");
        firstLabel.setName("label0");
        firstLabel.setForeground(Color.white);
        firstLabel.setFont(new Font("Thoma",Font.BOLD,14));
        firstLabel.setBounds(10,100,70,20);
        
        
        labellar.add(firstLabel);
        jPanel1.add(firstLabel);
        //-ilk kelime ataması
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fast Fingers");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setForeground(new java.awt.Color(153, 153, 153));

        jPanel2.setBackground(new java.awt.Color(0, 153, 204));

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(102, 102, 102));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(51, 255, 51));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 32)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(57, 57, 57))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 32)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(62, 62, 62))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setBackground(new java.awt.Color(51, 51, 255));
        jLabel1.setText(" ");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(51, 204, 255));
        jPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 32)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel4)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 261, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 100)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(546, 546, 546))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(204, 204, 204)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 274, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel5.getAccessibleContext().setAccessibleName("jGeriSayim");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased

       
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER && jLabel5.getText().equals(""))
        {
            int kontrol=0;
            JLabel temp;
            for(int i=0;i<labellar.size();i++)
            {
                temp=labellar.get(i);
                if(temp.getText().equals(jTextField1.getText()))
                {
                    temp.setText("");                                   
                    labellar.remove(temp);
                       
                    int dogruSayisi=Integer.parseInt(jLabel2.getText());
                    jLabel2.setText(Integer.toString(dogruSayisi+1));
                    kontrol=1;
                    break;
                }
                               
            }         
             if(kontrol==0)  
             {
               int yanlisSayisi=Integer.parseInt(jLabel3.getText());
                   jLabel3.setText(Integer.toString(yanlisSayisi+1));
             }
                  
            kontrol=0;    
            jTextField1.setText("");
           
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
         jLabel1.setIcon(new ImageIcon("image\\backHover.png"));
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        jLabel1.setIcon(new ImageIcon("image\\back.png"));
    }//GEN-LAST:event_jLabel1MouseExited

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        if(jLabel5.getText()!="")
        {
            GeriSayimThread.suspend();
        }
        else
        {
            ZamanThread.suspend();
            LabelEkleThread.suspend();
            KelimeleriHareketEttirThread.suspend();   
        }          
        pauseScreen pScreen=new pauseScreen();
        pScreen.setVisible(true);
    }//GEN-LAST:event_jLabel1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
            
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    

}
