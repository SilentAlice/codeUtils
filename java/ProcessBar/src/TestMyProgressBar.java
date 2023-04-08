import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class TestMyProgressBar extends JFrame implements Runnable,
		ActionListener {

	private MyProgressBar bar;
	private JButton btnStart;
	static TestMyProgressBar tmpb;

	public TestMyProgressBar() {
		setSize(400, 300);
		setLocation(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Time Process");
		setLayout(new FlowLayout());
		btnStart = new JButton("开始");
		this.add(btnStart);
		btnStart.addActionListener(this);
		bar = new MyProgressBar();
		setVisible(true);
	}

	public static void main(String[] args) {
		tmpb = new TestMyProgressBar();
	}

	@Override
	public void run() {
		for (int i = 1; i <= 20; i++) {
			int x = i * 5;
			bar.setCurrentValue(x);
			if (x > 0 && x < 100) {
				btnStart.setEnabled(false);
			}
			if (x == 100) {
				btnStart.setEnabled(true);
			}
			try {
				Thread.sleep(200);
				add(bar);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("开始")) {
			Thread t = new Thread(tmpb);
			t.start();
		}
	}
}
