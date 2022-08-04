package com.sandbox.exception;

public class WalletWithSameNameAndCurrencyExist extends BudgetRuntimeException{

    public WalletWithSameNameAndCurrencyExist(String message){
        super(message);
    }

    public WalletWithSameNameAndCurrencyExist(String message, Throwable cause){
        super(message, cause);
    }
}
