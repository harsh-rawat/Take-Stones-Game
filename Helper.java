public class Helper {
	
	/** 
    * Class constructor.
    */
	private Helper () {}

	/**
	* This method is used to check if a number is prime or not
	* @param x A positive integer number
	* @return boolean True if x is prime; Otherwise, false
	*/
	public static boolean isPrime(int x) {
		
		if(x == 1) return false;
		
		int len = (int)Math.sqrt(x);
		for(int index = 2; index <= len; index++) {
			if(x%index == 0) return false;
		}
		
		return true;
	}

	/**
	* This method is used to get the largest prime factor 
	* @param x A positive integer number
	* @return int The largest prime factor of x
	*/
	public static int getLargestPrimeFactor(int x) {
		
		boolean nums[] = new boolean[x+1];
		for(int i = 0; i <= x; i++)
			nums[i] = true;
		
		for(int prime = 2; prime*prime <= x; prime++) {
			
			if(nums[prime]) {
				for(int i = prime*prime; i <= x ; i+= prime)
					nums[i] = false;
			}	
		}
		
		for(int index = x; index >= 2; index--) {
			if(nums[index] && x % index == 0) return index;
		}
		
		return 1;
    }
	
}