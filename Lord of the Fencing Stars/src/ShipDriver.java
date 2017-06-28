import javax.swing.JFrame;
public class ShipDriver extends JFrame{
		
		public ShipDriver(){
			super("Lord of the Fencing Stars");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.add(new Spacewarp());
			this.pack();
			this.validate();
			
		}
		
		/**
		 * @param args
		 */
		public static void main(String[] args) {
			// TODO Auto-generated method stub

			new ShipDriver().setVisible(true);
		}

	}




