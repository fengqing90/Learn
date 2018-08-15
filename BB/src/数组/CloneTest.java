package 数组;

import java.io.Serializable;

public class CloneTest implements Cloneable,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6002173158252113217L;
	public Head head;

	public CloneTest() {
	};

	public String body;

	@Override
	protected Object clone() throws CloneNotSupportedException {
		CloneTest ct = (CloneTest) super.clone();
		ct.head = head.clone();
		return ct;
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		CloneTest c1 = new CloneTest();
//		c1.head = new Head();
		
		CloneTest c2 = (CloneTest) c1.clone();
		String result3 = (c1 == c2) ? "假克隆" : "真克隆";
		String result4 = (c1.head == c2.head) ? "假克隆" : "真克隆";
		System.out.println(result3);
		System.out.println(result4);
	}
}

class Head implements Cloneable {
	@Override
	protected Head clone() throws CloneNotSupportedException    {
		return (Head) super.clone();
	}
}