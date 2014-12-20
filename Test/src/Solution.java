import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
	public static void main(String[] args) throws IOException {
		List<Gear> list = new ArrayList<Gear>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String params = br.readLine();
		String[] paramArr = params.split(" ");
		int n = Integer.parseInt(paramArr[0]);
		int D = Integer.parseInt(paramArr[1]);

		String rs = br.readLine();
		String[] rArr = rs.split(" ");

		String cs = br.readLine();
		String[] cArr = cs.split(" ");

		for (int i = 0; i < n; i++) {
			list.add(new Gear(Integer.parseInt(rArr[i]), Integer
					.parseInt(cArr[i]), i+1));
		}

		Collections.sort(list);
		
		int[] best = new int[n];
		Gear bestGear = list.get(n-1);
		int lowestCost = bestGear.getC();
		for(int i=list.size()-1;i>=0;i--){
			Gear candidateGear = list.get(i);
			if (candidateGear.getC() < lowestCost) {
				bestGear = candidateGear;
				lowestCost = candidateGear.getC();
				
			} else if (candidateGear.getC() == lowestCost) {
				if (candidateGear.getR() > bestGear.getR()) {
					bestGear = candidateGear;
				}else if(candidateGear.getR() == bestGear.getR()){
					if(bestGear.getOriginalIndex()>candidateGear.getOriginalIndex()){
						bestGear = candidateGear;
					}
				}
			}
			best[i] = bestGear.getOriginalIndex();
		}

		for (int i = 0; i < rArr.length; i++) {
			

			// use binary search to find the first gear that having
			// thisGear.r+its.r>=D
			int firstInd = binarySearch(list, D, Integer.parseInt(rArr[i]));
			// no match found
			if (firstInd == -1) {
				System.out.print(0 + " ");
			} else {

				System.out.print(best[firstInd] + " ");
			}
		}
	}

	private static int binarySearch(List<Gear> list, int D, int thisR) {
		int low = 0;
		int high = list.size() - 1;
		while (low < high) {
			int mid = (low + high) >>> 1;
			if (list.get(mid).getR() + thisR >= D) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}

		if (list.get(low).getR() + thisR < D) {
			// if after search, low+thisGear is also false
			// then no gear can be used, we return -1
			return -1;
		} else {
			return low;
		}
	}

	private static class Gear implements Comparable<Gear> {
		private int r;
		private int c;
		private int originalIndex;

		public Gear(int r, int c, int originalIndex) {
			super();
			this.r = r;
			this.c = c;
			this.originalIndex = originalIndex;
		}

		public int getR() {
			return r;
		}

		public void setR(int r) {
			this.r = r;
		}

		public int getC() {
			return c;
		}

		public void setC(int c) {
			this.c = c;
		}

		public int getOriginalIndex() {
			return originalIndex;
		}

		public void setOriginalIndex(int originalIndex) {
			this.originalIndex = originalIndex;
		}

		@Override
		public int compareTo(Gear other) {
			// TODO Auto-generated method stub
			return this.r - other.r;
		}
	}
}
