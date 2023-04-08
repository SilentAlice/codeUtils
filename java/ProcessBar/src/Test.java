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
//	            "Loading Data" , "complete：" , 0 , allNum);
//		dialog = d;
//	}
//	
//	public void actionPerformed(ActionEvent e)
//    {
//        //以任务的当前完成量设置进度对话框的完成比例
//        dialog.setProgress(count);
//        //如果用户单击了进度对话框的”取消“按钮
//        if (dialog.isCanceled())
//        {
//            //停止计时器
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
//		//创建一个计时器
//		timer = new Timer();
//        {
//            
//        });  
//        timer.start();
//	}
//}
//
