import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class TransactionCost {
	
	static class Trade {
		int currency1;
		int currency2;
		double price;
		
	}
	
	static class wholeTrade {
		int exchange;
		int trade;
		Trade t[];
		
		public wholeTrade(int exchange, int trade) {
			this.exchange = exchange;
			this.trade = trade;
			t = new Trade[trade];
			for (int i = 0; i < trade; i ++) {
				t[i] = new Trade();
			}
		}
		
	}
	
	static int profit(wholeTrade w, int startCurrency) {
		int exchange = w.exchange;
		double percentage[] = new double[exchange];
		Trade pre[] = new Trade[exchange];
		
		for (int i = 0; i < exchange; i ++) {
			percentage[i] = 0;
			pre[i] = new Trade();
		}
		percentage[startCurrency] = 100;
		
		for (int i = 0; i < exchange - 1; i ++) {
			for (Trade tr : w.t) {
				int c1 = tr.currency1;
				int c2 = tr.currency2;
				double price = tr.price;
				double newP = percentage[c1] * price / 100;
				if(newP > percentage[c2]) {
					percentage[c2] = newP;
					pre[c2] = tr;
				}
			}
			
		}
		
		int e = -1;
		for (Trade tr : w.t) {
			int c1 = tr.currency1;
			int c2 = tr.currency2;
			double price = tr.price;
			if(percentage[c1] * price / 100 > percentage[c2]) {
				e = c2;
				break;
			}
		}
		double profit = 100;
		ArrayList<Integer> cycle = new ArrayList<>();
		if (e != -1) {
			for (int i = 0; i < exchange; i ++) {
				e = pre[e].currency1;
			}


			for (int c = e;; c = pre[c].currency1) {
				if (c == e && cycle.size() > 1) {
					break;
				}
				cycle.add(c);
				profit = profit * pre[c].price / 100;
			}
		}
		
		if (e == -1) 	return -1;
		return (int)Math.ceil((1 - Math.pow(100 / profit, 1.0 / cycle.size())) * 100);
	}
		


	public static void main(String[] args) {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		try {
			String[] numbers = bf.readLine().split(" ");
			int exchange = Integer.parseInt(numbers[0]);
			int trade = Integer.parseInt(numbers[1]);
			wholeTrade wt = new wholeTrade(exchange, trade);
			ArrayList<String> currencies = new ArrayList<String>();
			
			for (int i = 0; i < trade; i ++) {
				String[] data = bf.readLine().split(" ");
				String c1 = data[0];
				String c2 = data[1];
				if (!currencies.contains(c1)) {
					currencies.add(c1);
				}
				if (!currencies.contains(c2)) {
					currencies.add(c2);
				}
				wt.t[i].currency1 = currencies.indexOf(c1);
				wt.t[i].currency2 = currencies.indexOf(c2);
				wt.t[i].price = Integer.parseInt(data[2]);
			}
			
			
			
			System.out.println(profit(wt, 0));

	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		

	}

}
