package ibf2022.day25.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import ibf2022.day25.model.BankAccount;
import ibf2022.day25.repository.BankAccountRepo;

@Service
public class BankAccountService {
    @Autowired
    BankAccountRepo bankAcctRepo;

    @Transactional(isolation = Isolation.READ_UNCOMMITTED) // code would work even w/o isolation, but it's clearer with
    public Boolean transferMoney(Integer accountFrom, Integer accountTo, Float amount) {
        // use boolean to show if transaction was successful
        Boolean bTransferred = false;
        Boolean bWithdrawn = false;
        Boolean bDeposited = false;
        // Boolean bCanWithdraw = false;

        BankAccount depositAccount = null;
        BankAccount withdrawalAccount = null;
        Boolean bDepositAccountValid = false;
        Boolean bWithdrawalAccountValid = false;

        Boolean bProceed = false;  // declare another variable whether to proceed with withdrawal


        // 1. Check if accounts (withdrawer and depositor) are valid (active)
        withdrawalAccount = bankAcctRepo.retrieveAccountDetails(accountFrom);
        depositAccount = bankAcctRepo.retrieveAccountDetails(accountTo);

        bDepositAccountValid = depositAccount.getIsActive(); // true means valid, false means invalid
        bWithdrawalAccountValid = withdrawalAccount.getIsActive(); 

        if (bDepositAccountValid && bWithdrawalAccountValid) {
            bProceed = true;
        }

        // 2. Check withdrawn account has more money than withdrawal amount
        if (bProceed) {
            if (withdrawalAccount.getBalance() < amount) {
                bProceed = false;
            }
        }

        if (bProceed){
            // 3. Perform the withdrawal (requires transaction)
            bWithdrawn = bankAcctRepo.withdrawAmount(accountFrom, amount);

            bWithdrawn = false; // Darryl showed us how to customize error msg
            if (!bWithdrawn) {
                throw new IllegalArgumentException("Simulate error before withdrawal"); 
            }

            // 4. Perform the deposit (requires transaction)
            bDeposited = bankAcctRepo.depositAmount(accountTo, amount);
        }

        // 5. Check transactions successful
        if (bWithdrawn && bDeposited) {
            bTransferred = true;
        }
        return bTransferred;
    } 
    
    
}
