package f21as.coursework.coffeshop.core;

import javax.swing.JPanel;

import f21as.coursework.coffeshop.gui.OrdersFrame;

public class RefreshAnnotationThread implements Runnable {
	
private OrdersFrame ordersFrame;	
	

public RefreshAnnotationThread(OrdersFrame ordersFrame)
{
	this.ordersFrame = ordersFrame;
	
}

private boolean doStop = false;

public synchronized void doStop() {
    this.doStop = true;
}

private synchronized boolean keepRunning() {
    return this.doStop == false;
}

@Override
public void run() {
    while(keepRunning()) {
        // keep doing what this thread should do.
        System.out.println("Running");
        ordersFrame.pressRefresh();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
}



