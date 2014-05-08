package glb.alg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalcMaxDistance {

	class KeyData {
		int value;
		int index;
		public KeyData(int value, int index) {
			this.value = value;
			this.index = index;
		}
	}
	
	private int[] arr;
	private int length = 0;
	private int maxDistance = 0;
	private int maxIndexForKey0 = 0;
	private int[] maxRightPos;
	private List<KeyData> keyDataList = new ArrayList<KeyData>();
	
	/**
	 * Read input data from console.
	 * @return return true if successfully read data, else return false.
	 */
	public boolean readDataFromSystemIn() {
		Scanner scn = new Scanner(System.in);
		length = scn.nextInt();
		
		arr = new int[length];
		maxRightPos = new int[length];
		arr[0] = scn.nextInt();
		maxRightPos = new int[length];
		KeyData dumyKeyData = new KeyData(arr[0], 0);
		keyDataList.add(dumyKeyData);
		for (int i = 1; i < length; i++) {
			arr[i] = scn.nextInt();
			if (arr[i] > arr[0]) {
				maxIndexForKey0 = i;
				if (maxIndexForKey0 > maxDistance)
					maxDistance = maxIndexForKey0;
			}
			if (arr[i] < dumyKeyData.value) {
				dumyKeyData = new KeyData(arr[i], i);
				keyDataList.add(dumyKeyData);
			}
		}	
		scn.close();
		return true;
	}
	
	/**
	 * Calculate max distance.
	 * @return return max distance. if no result, return -1.
	 */
	public int calulate() {
		if (1 == keyDataList.size() || maxIndexForKey0 == length - 1) {
			System.out.print(length - 1);
			return length - 1;
		}
		if (keyDataList.size() == length) {
			System.out.print(-1);
			return -1;
		}
		KeyData baseKeyData;
		boolean find = true;
		int tempMax = arr[length - 1];
		int tempMaxPos = length - 1;
		maxRightPos[length - 1] = tempMaxPos;
		for (int i = length - 2; i >= 0; i--) {
			if (arr[i] < tempMax) {
				maxRightPos[i] = tempMaxPos;
			} else {
				maxRightPos[i] = i;
				if (arr[i] > tempMax) {
					tempMax = arr[i];
					tempMaxPos = i;
				}
			}
		}
		int currentIndex = maxIndexForKey0;
		for (int listIndex = 1; listIndex < keyDataList.size(); listIndex++) {
			find = true;
			baseKeyData = keyDataList.get(listIndex);
			currentIndex = Math.max(currentIndex, baseKeyData.index	+ maxDistance) + 1;
			if (currentIndex > length - 1)
				break;
			while (find) {
				if (arr[maxRightPos[currentIndex]] > baseKeyData.value) {
					find = true;
					maxDistance = maxRightPos[currentIndex] - baseKeyData.index;					
					if (currentIndex == maxRightPos[currentIndex])
						if (currentIndex < length - 1)
							currentIndex++;
						else						
							find = false;
					else
						currentIndex = maxRightPos[currentIndex];
				} else
					find = false;
			}
		}
		if (0 == maxDistance)
			maxDistance = -1;
		System.out.print(maxDistance);
		return maxDistance;
	}
	
	public static void main(String[] args) {
		CalcMaxDistance cmd = new CalcMaxDistance();
		if (!cmd.readDataFromSystemIn())
			return;
		cmd.calulate();
	}
}
