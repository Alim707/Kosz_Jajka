package zadanie3;

public class Back implements Runnable {
	private Main m;
	
	public Back(Main m) {
		this.m=m;
	}

	@Override
	public void run() {
		m.menu();

	}

}
