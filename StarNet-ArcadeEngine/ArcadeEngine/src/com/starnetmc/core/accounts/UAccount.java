package com.starnetmc.core.accounts;

public class UAccount {

	public static SimpleAccount getSimpleAccountFromAccount(Account a){
		return new SimpleAccount(a.getUUID(), a.getRank(), a.getShards());
	}
	
}