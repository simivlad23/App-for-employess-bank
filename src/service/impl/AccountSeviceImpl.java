package service.impl;

import model.Account;
import model.Transaction;
import repository.AccountRepository;
import repository.TransactionRepository;
import service.AccountService;

public class AccountSeviceImpl implements AccountService {

    public AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public AccountSeviceImpl(AccountRepository accountRepository,TransactionRepository transactionRepository) {

        this.transactionRepository=transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.update(account);
    }

    @Override
    public void addAccount(Long id_client, Account c) {

        c.setId_person(id_client);

        accountRepository.create(c);

    }

    @Override
    public void removeAccount(Long idCont) {

        accountRepository.deleteById(idCont);

    }



    @Override
    public void withdraw(String iban, int suma) {

        Account account = accountRepository.findByIBAN(iban);

        if(account.getMoney()-suma<0)
            System.out.println("Fail, You don't hae enough money");
        account.setMoney(account.getMoney()-suma);

        accountRepository.update(account);


    }

    @Override
    public void deposit(String iban, int suma) {



        Account account = accountRepository.findByIBAN(iban);

        System.out.println(account.getMoney());

        account.setMoney(account.getMoney()+suma);

        accountRepository.update(account);
    }

    @Override
    public void transfer(String idCont, String idContDest, int suma) {
        Account account = accountRepository.findByIBAN(idCont);

        if(account.getMoney()-suma<0){
            System.out.println("Fail, You don't hae enough money");
        return;
        }
        account.setMoney(account.getMoney()-suma);

        accountRepository.update(account);

        Account account2 = accountRepository.findByIBAN(idContDest);

        account2.setMoney(account2.getMoney()+suma);

        accountRepository.update(account2);

        Transaction transaction = new Transaction();
        transaction.setId_exp(account.getId());
        transaction.setId_dest(account2.getId());
        transaction.setMoney(suma);

        transactionRepository.create(transaction);
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }
}
