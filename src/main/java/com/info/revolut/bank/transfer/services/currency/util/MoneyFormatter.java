package com.info.revolut.bank.transfer.services.currency.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MoneyFormatter {

	private static final DecimalFormat DECIMAL_FORMAT =
			new DecimalFormat("##0.00", new DecimalFormatSymbols(Locale.ENGLISH));

	static {
		DECIMAL_FORMAT.setRoundingMode(RoundingMode.DOWN);
	}

	public static String format(BigDecimal amount) {
		return DECIMAL_FORMAT.format(amount);
	}

	public static BigDecimal parse(String amount, int scale) {
		return new BigDecimal(amount).setScale(scale, RoundingMode.DOWN);
	}
}
