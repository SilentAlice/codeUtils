//import java.util.Timer;
//import java.awt.event.*;
//
//import javax.swing.ProgressMonitor;
//
//class Process {
//	Timer timer;
//	int allNum;
//	ProgressMonitor dialog;
//	int count;
//	
//	public Process(int allnum){
//		allNum = allnum;
//		ProgressMonitor d = new ProgressMonitor(null ,  
//	            "Loading Data" , "complete��" , 0 , allNum);
//		dialog = d;
//	}
//	
//	public void actionPerformed(ActionEvent e)
//    {
//        //������ĵ�ǰ��������ý��ȶԻ������ɱ���
//        dialog.setProgress(count);
//        //����û������˽��ȶԻ���ġ�ȡ������ť
//        if (dialog.isCanceled())
//        {
//            //ֹͣ��ʱ��
//            timer.stop();
//            System.exit(0);
//        }else if( StartSearch.count == StartSearch.preCount)
//        {
//        	timer.stop();
//        	dialog.close();
//        	StartSearch.preCount = 0;
//        	StartSearch.count = 0;
//        }
//        StartSearch.preCount = StartSearch.count;
//    }
//	
//	
//	
//	public void init(){
//		int count = 0;
//		//����һ����ʱ��
//		timer = new Timer();
//        {
//            
//        });  
//        timer.start();
//	}
//}
//
