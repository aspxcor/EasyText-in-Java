package Text2;

import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import java.io.*;
import java.lang.*;
class Notepad extends JFrame{
   private final Color colorvalues[] ={ Color.black, Color.blue, Color.red, Color.green };   
   String styleNames[] = { "Bold", "Italic" };
   String fontNames[] = { "宋体", "华文行楷", "隶书" };
   String[] sizeString = new String[30];
   int[] size = new int[30];
   private JRadioButtonMenuItem colorItems[], fonts[];
   private JCheckBoxMenuItem styleItems[];
   private JTextArea displayText;
   private ButtonGroup fontGroup, colorGroup;
   private int style;
   private JScrollPane scroll;
   private String selectText = "";
   private JComboBox styleBox,fontBox,sizeBox;
   private JPanel toolPanel;
   private int rowNumber = 0;
   private FileDialog fd = new FileDialog(this);
    
   public Notepad()
   {
      super( "我的编译器" );  
      JMenuBar bar = new JMenuBar();
      setJMenuBar( bar );
      JMenu fileMenu = new JMenu( "文件(F)" );
      fileMenu.setMnemonic( 'F' );
       
	          JMenuItem newItem = new JMenuItem( "新建（N）" );
	          newItem.setMnemonic( 'N' );
	          fileMenu.add( newItem );
	          newItem.addActionListener(new ActionListener() {
	    	      public void actionPerformed( ActionEvent event )
	    			{displayText.setText("");
	                 }});
	      
	        JMenuItem openItem = new JMenuItem( "打开(O)" );
	        openItem.setMnemonic( 'O' );
	        fileMenu.add( openItem );
	        openItem.addActionListener( new ActionListener() {
	  	      public void actionPerformed( ActionEvent event )
	  			{
                 fd.setTitle("打开");                  
						if (fd.getFile() != null) {
							File file = new File(fd.getFile());   
							displayText.setText( "");
							try {
								FileReader f = new FileReader(file);
								BufferedReader b = new BufferedReader(f);
								String s;
								try {
									while ((s = b.readLine()) != null) {
									displayText.append(s + "\n");//将给定文本追加到文本域的当前文本（即把读的内容加入文本域）
									}
									f.close();
									b.close();
								} catch (IOException ex) {}
							} catch (FileNotFoundException ex) {}
						   }
						else {return;}
        }});       
		
	        fileMenu.addSeparator();     
	   
	         JMenuItem saveItem = new JMenuItem( "保存(S)" );
	         saveItem.setMnemonic( 'S' );
	         fileMenu.add( saveItem );
	         saveItem.addActionListener(
	           new ActionListener() {
	   	      public void actionPerformed( ActionEvent event )
	   			{   fd.setFile("*.java");
	   			    fd.setMode(FileDialog.SAVE);
					fd.setTitle("保存");
			fd.show();
	        if (fd.getFile() != null) {
					File file = new File(fd.getFile());
						try {
							PrintWriter pw = new PrintWriter(file);
							pw.print(displayText.getText());
							pw.flush();       
							pw.close();
                            } catch (FileNotFoundException ex) {ex.printStackTrace();}
						  }
							else {return;}
	               }});
	     
	        JMenuItem saveAsItem = new JMenuItem( "另存为(Z)" );
	        saveAsItem.setMnemonic( 'Z' );
	        fileMenu.add( saveAsItem );
	        saveAsItem.addActionListener(
	          new ActionListener() {
	  	      public void actionPerformed( ActionEvent event )
	  			{   fd.setFile("*.java");
	  			    fd.setMode(FileDialog.SAVE);
					fd.setTitle("另存为");
			
	        if (fd.getFile() != null) {
					File file = new File(fd.getFile());
						try {
							PrintWriter pw = new PrintWriter(file);
							pw.print(displayText.getText());
							pw.flush();                       
							pw.close();
                            } catch (FileNotFoundException ex) {ex.printStackTrace();}
						  }
							else {return;}
             }});
	        
      fileMenu.addSeparator();    
      
      JMenuItem exitItem = new JMenuItem( "退出(X)" );
      exitItem.setMnemonic( 'x' );
      fileMenu.add( exitItem );
      exitItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent event )
            {
               System.exit( 0 );
            } } );
      bar.add( fileMenu );

      
      JMenu editMenu = new JMenu( "编辑(E)" );
      editMenu.setMnemonic( 'E' );
     
      JMenuItem cutItem = new JMenuItem( "剪切(T)" );
	  cutItem.setMnemonic( 'T' );
	  editMenu.add( cutItem );
	  cutItem.addActionListener(
	     new ActionListener(){
	      public void actionPerformed( ActionEvent event ){
	                selectText = displayText.getSelectedText();
							int start = displayText.getSelectionStart();
							int end = displayText.getSelectionEnd();
							displayText.replaceRange("", start, end);
	   }} );
     
      JMenuItem copyItem = new JMenuItem( "复制(C)" );
      copyItem.setMnemonic( 'C' );
      editMenu.add( copyItem );
      copyItem.addActionListener(  new ActionListener(){
        public void actionPerformed( ActionEvent event ){
         selectText = displayText.getSelectedText();
        } } );
      
      JMenuItem pasteItem = new JMenuItem( "粘贴(P)" );
      pasteItem.setMnemonic( 'P' );
      editMenu.add( pasteItem );
      pasteItem.addActionListener(  new ActionListener(){
        public void actionPerformed( ActionEvent event ){
         int position = displayText.getCaretPosition();
         displayText.insert( selectText,position );
        }} );
      
       editMenu.addSeparator();
     
	        JMenuItem swapItem = new JMenuItem( "替换（R）)" );
	        swapItem.setMnemonic( 'R' );
	        editMenu.add( swapItem );
	        swapItem.addActionListener( new ActionListener(){
	          public void actionPerformed( ActionEvent event ){
				         JPanel swapPanel=new JPanel();
	                     JLabel lookupLabel=new JLabel("要替换的内容");
			   		     JTextField inputText=new JTextField(10);
			   		     JLabel swapLabel=new JLabel("替换为：");
			   		     JTextField changetoText=new JTextField(10);
			   		  swapPanel.add(lookupLabel);
			   		  swapPanel.add(inputText);
			   		  swapPanel.add(swapLabel);
			   		  swapPanel.add(changetoText);
			   		  JOptionPane.showMessageDialog(null,swapPanel);
			   		  String text=displayText.getText();
			           String changeText=text.replaceFirst(inputText.getText(),changetoText.getText());
                    displayText.setText(changeText);
	          } } );
     
	  	        JMenuItem aswapItem = new JMenuItem( "全部替换（Q）)" );
	  	        aswapItem.setMnemonic( 'Q' );
	  	        editMenu.add( aswapItem );
	  	        aswapItem.addActionListener(
	  	         new ActionListener(){
	  	          public void actionPerformed( ActionEvent event ){
	  				         JPanel swapPanel=new JPanel();
	  	                     JLabel lookupLabel=new JLabel("要替换的内容");
	  			   		     JTextField inputText=new JTextField(10);
	  			   		     JLabel swapLabel=new JLabel("替换为：");
	  			   		     JTextField changetoText=new JTextField(10);
	  			   		  swapPanel.add(lookupLabel);
	  			   		  swapPanel.add(inputText);
	  			   		  swapPanel.add(swapLabel);
	  			   		  swapPanel.add(changetoText);
	  			   		  JOptionPane.showMessageDialog(null,swapPanel);
	  			   		  String text=displayText.getText();
	  			          String changeText=text.replaceAll(inputText.getText(),changetoText.getText());
	                      displayText.setText(changeText);
	  	               }} );
	  	              
	   editMenu.addSeparator();
    
	  JMenuItem insertItem=new JMenuItem("插入文本(K)");
	  insertItem.setMnemonic('K');
	  editMenu.add(insertItem);
	  insertItem.addActionListener(
	    new ActionListener(){
	     public void actionPerformed(ActionEvent event){
	  		  JPanel insertPanel=new JPanel();
	  		  JLabel insertLabel=new JLabel("要插入的内容");
	  		  JTextField inputText=new JTextField(10);
	  		  insertPanel.add(insertLabel);
	  		  insertPanel.add(inputText);
	  		  JOptionPane.showMessageDialog(null,insertPanel);
	          int fromIndex=displayText.getCaretPosition();
	          displayText.insert(inputText.getText(),fromIndex);
	  }});
	
	 JMenuItem RemoveItem=new JMenuItem("删除(G)");
	 RemoveItem.setMnemonic('G');
	 editMenu.add(RemoveItem);
	 RemoveItem.addActionListener(new ActionListener(){
	             public void actionPerformed(ActionEvent e)
	             {    int start=displayText.getSelectionStart();
	  			      int end=displayText.getSelectionEnd();
	                 displayText.replaceRange(null,start,end);
	             } });
      editMenu.addSeparator();
      bar.add( editMenu );

      JMenu formatMenu = new JMenu( "格式(R)" );
      formatMenu.setMnemonic( 'R' );
    
      JMenuItem changeItem = new JMenuItem( "自动换行(W)" );
      changeItem.setMnemonic( 'W' );
      formatMenu.add( changeItem );
      changeItem.addActionListener(new ActionListener(){
        boolean var = false;
        public void actionPerformed( ActionEvent event ){
         if(var) var = false;
         else var=true;
         displayText.setLineWrap(var);
        }} );
      
      String colors[] = { "黑色", "蓝色", "红色", "绿色" };
      JMenu colorMenu = new JMenu( "颜色（C）" );
      colorMenu.setMnemonic( 'C' );
      colorItems = new JRadioButtonMenuItem[ colors.length ];
      colorGroup = new ButtonGroup();
      ItemHandler itemHandler = new ItemHandler();
      for ( int count = 0; count < colors.length; count++ ) {
         colorItems[ count ] = new JRadioButtonMenuItem( colors[ count ] );
         colorMenu.add( colorItems[ count ] );
         colorGroup.add( colorItems[ count ] );
         colorItems[ count ].addActionListener( itemHandler );
      }
      colorItems[ 0 ].setSelected( true );
      
      formatMenu.add( colorMenu );
      formatMenu.addSeparator();     
     
      JMenu fontMenu = new JMenu( "字体(n)" );
      fontMenu.setMnemonic( 'n' );
      fonts = new JRadioButtonMenuItem[ fontNames.length ];
      fontGroup = new ButtonGroup();
      for ( int count = 0; count < fonts.length; count++ ) {
         fonts[ count ] = new JRadioButtonMenuItem( fontNames[ count ] );
         fontMenu.add( fonts[ count ] );
         fontGroup.add( fonts[ count ] );
         fonts[ count ].addActionListener( itemHandler );
      }
      fonts[ 0 ].setSelected( true );
      fontMenu.addSeparator();
     
      styleItems = new JCheckBoxMenuItem[ styleNames.length ];
         for ( int count = 0; count < styleNames.length; count++ ) {
         styleItems[ count ] = new JCheckBoxMenuItem( styleNames[ count ] );
         fontMenu.add( styleItems[ count ] );
         StyleHandler styleHandler = new StyleHandler();
         styleItems[ count ].addItemListener( styleHandler );
      }
         
         formatMenu.add( fontMenu );
	     JMenu searchMenu = new JMenu( "查找（S）" );
	     searchMenu.setMnemonic( 'H' );
	     
	     JMenuItem frontItem = new JMenuItem( "向前查找（F）" );
	  	 frontItem.setMnemonic( 'F' );
	  	 searchMenu.add( frontItem );
	  	 frontItem.addActionListener( new ActionListener() {
	  	  	 public void actionPerformed( ActionEvent event )
	  	  	 {JPanel swapPanel=new JPanel();
		     JLabel seekLabel=new JLabel("要查找的内容");
		     JTextField inputText=new JTextField(20);
	         swapPanel.add(seekLabel);
		     swapPanel.add(inputText);
		     JOptionPane.showMessageDialog(null,swapPanel);
		     String text=displayText.getText();
		     int fromIndex=displayText.getCaretPosition();
             int lastfromIndex=text.indexOf(inputText.getText(),fromIndex);
             displayText.setCaretPosition(lastfromIndex);
             displayText.moveCaretPosition(lastfromIndex+inputText.getText().length());
             }});
     
	      JMenuItem backItem = new JMenuItem( "向后查找（B）" );
	  	  backItem.setMnemonic( 'B' );
	  	  searchMenu.add( backItem );
	  	  backItem.addActionListener(
          new ActionListener() {
	  	    public void actionPerformed( ActionEvent event )
	  	  	{JPanel swapPanel=new JPanel();
		     JLabel seekLabel=new JLabel("要查找的内容");
		     JTextField inputText=new JTextField(20);
		     swapPanel.add(seekLabel);
		     swapPanel.add(inputText);
		     JOptionPane.showMessageDialog(null,swapPanel);
		     String text=displayText.getText();
		     int fromIndex=displayText.getCaretPosition();
             int lastfromIndex=text.lastIndexOf(inputText.getText(),fromIndex);
             displayText.setCaretPosition(lastfromIndex);
             displayText.moveCaretPosition(lastfromIndex+inputText.getText().length());//使查找到的子字符串显示出来
             }});
         bar.add( searchMenu ); 
         
         JMenu helpMenu = new JMenu( "帮助(H)" );
         helpMenu.setMnemonic( 'H' );
 
	     JMenuItem aboutItem = new JMenuItem( "关于(A)..." );
	     aboutItem.setMnemonic( 'A' );
	     helpMenu.add( aboutItem );
	     aboutItem.addActionListener( new ActionListener() {
	       public void actionPerformed( ActionEvent event )
	        {  JOptionPane.showMessageDialog( Notepad.this,"简易文本编辑器\ncopyright@2017-7-5--宋小兰", "关于", JOptionPane.PLAIN_MESSAGE );
	         rowNumber = displayText.getRows();
	         JOptionPane.showMessageDialog(null,""+ rowNumber);
	         }} );
  
         JMenuItem helpItem = new JMenuItem( "帮助主题(H)..." );
         helpItem.setMnemonic( 'H' );
         helpMenu.add( helpItem );
         helpItem.addActionListener(new ActionListener(){
           public void actionPerformed( ActionEvent event ){
            JTextArea helpText = new JTextArea(
                "格式里的自动换行说明：单击一次切换功能\n只能实现整个文本的风格，字体等的设置\n"+
                "复制，粘贴可以实现插入到鼠标当前位置\n标题栏显示行数");
            JScrollPane scroller = new JScrollPane(helpText);
            JOptionPane.showMessageDialog(null,scroller);
            }});
         bar.add( helpMenu );

	        for(int i = 0 ; i<size.length;i++){
	         sizeString[i] = "" + (i+6) * 2;
	         size[i] = (i+12)*2;
	        }
	        Container container = getContentPane();
	        container.setLayout(new BorderLayout() );
	        toolPanel = new JPanel();
	        JLabel label1 = new JLabel("字体名称");
	        toolPanel.add(label1);
	        fontBox = new JComboBox(fontNames);
	        fontBox.addItemListener(  new ItemListener(){
	          public void itemStateChanged(ItemEvent event){
	           if( event.getStateChange() == ItemEvent.SELECTED){
	                displayText.setFont( new Font( fontNames[fontBox.getSelectedIndex()],
	                 displayText.getFont().getStyle(), displayText.getFont().getSize() ) );
	           }}});
	        toolPanel.add(fontBox);
	        JLabel label2 = new JLabel("字体风格");
	        toolPanel.add(label2);
	        String style_name[] = {"常规","倾斜","粗体","倾斜加粗体"};//字体风格
	        styleBox = new JComboBox(style_name);
	        styleBox.addItemListener(   new ItemListener(){
	            public void itemStateChanged(ItemEvent event){
	              if( event.getStateChange() == ItemEvent.SELECTED){
	              if(styleBox.getSelectedIndex()==0) style = Font.PLAIN;
	              if(styleBox.getSelectedIndex()==1) style = Font.ITALIC;
	              if(styleBox.getSelectedIndex()==2) style = Font.BOLD;
	              if(styleBox.getSelectedIndex()==3) style = Font.ITALIC+Font.BOLD;
	            displayText.setFont( new Font( displayText.getFont().getName(),
	                 style, displayText.getFont().getSize() ) );
	           }} });
	        toolPanel.add( styleBox );
	        
	        JLabel label3 = new JLabel("字号");
	        toolPanel.add(label3);
	        sizeBox = new JComboBox(sizeString);
	        sizeBox.addItemListener( new ItemListener(){
	          public void itemStateChanged(ItemEvent event){
	           if( event.getStateChange() == ItemEvent.SELECTED){
	               displayText.setFont( new Font( displayText.getFont().getName(),
	                 displayText.getFont().getStyle(), size[sizeBox.getSelectedIndex()] ) );
	           }}} );
	        toolPanel.add(sizeBox);
         container.add( toolPanel, BorderLayout.NORTH );
  
      displayText = new JTextArea();
      displayText.setForeground( colorvalues[ 0 ] );
      displayText.setFont( new Font( "Serif", Font.PLAIN, 24 ) );
      scroll = new JScrollPane( displayText,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
           JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );
      container.add( scroll, BorderLayout.CENTER );
      displayText.addKeyListener(  new KeyListener(){
        public void keyPressed( KeyEvent event ){
         rowNumber = displayText.getLineCount();
         setTitle("总共" + rowNumber +  "行");
        }
        public void keyReleased( KeyEvent event ){ }
        public void keyTyped( KeyEvent event ){}
       }
      );
      setSize(700, 500 );
      setVisible( true );
   }

 public static void main( String args[] )
   {
      Notepad application = new Notepad();
      application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
   }
 private class ItemHandler implements ActionListener {
     public void actionPerformed( ActionEvent event )
     {
        for ( int count = 0; count < colorItems.length; count++ )
           if ( colorItems[ count ].isSelected() ) {
              displayText.setForeground( colorvalues[ count ] );
              break;
           }
        for ( int count = 0; count < fonts.length; count++ )
           if ( event.getSource() == fonts[ count ] ) {
              displayText.setFont(new Font( fonts[ count ].getText(), style, 72 ) );
              break;
           }
        repaint();
     } }
  private class StyleHandler implements ItemListener {
     public void itemStateChanged( ItemEvent e )
     {
       style = 0;
       if ( styleItems[ 0 ].isSelected() )
           style += Font.BOLD;
       if ( styleItems[ 1 ].isSelected() )
           style += Font.ITALIC;
       displayText.setFont(
           new Font( displayText.getFont().getName(), style, 72 ) );
       repaint();
     }}
}