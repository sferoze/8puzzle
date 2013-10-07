
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MinPQ<Integer> tester = new MinPQ<Integer>();
		tester.insert(6);
		tester.insert(7);
		tester.insert(3);
		Integer x = tester.delMin();
		
		StdOut.println(x);

	}

}
