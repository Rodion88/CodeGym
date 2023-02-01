package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.ResourceBundle;

class WithdrawCommand implements Command{
	private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw");

	@Override
	public void execute() throws InterruptOperationException {
		String currencyCode = ConsoleHelper.askCurrencyCode();
		CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
		ConsoleHelper.writeMessage(res.getString("before"));

		while (true) {
			try{
				ConsoleHelper.writeMessage(res.getString("specify.amount"));
				String s = ConsoleHelper.readString();
				if(s == null){
					ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
				}else {
					try	{
						int amount = Integer.parseInt(s);
						boolean isAmountAvailable = currencyManipulator.isAmountAvailable(amount);
						if (isAmountAvailable) {
							Map<Integer, Integer> denomination = currencyManipulator.withdrawAmount(amount);
							for (Integer item : denomination.keySet()) {
								ConsoleHelper.writeMessage("\t" + item + " - " + denomination.get(item));
							}
							ConsoleHelper.writeMessage(String.format(res.getString("success.format"), amount, currencyCode));
							break;
						}else {
							ConsoleHelper.writeMessage(res.getString("not.enough.money"));
						}
					}catch (NumberFormatException e){
						ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
					}
				}
			}catch (NotEnoughMoneyException notEnoughMoneyException){
				ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
			}
		}
	}
}
