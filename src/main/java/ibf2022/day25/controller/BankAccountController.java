package ibf2022.day25.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.day25.payload.TransferRequest;
import ibf2022.day25.service.BankAccountService;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {

    @Autowired
    BankAccountService bankAccSvc;

    @PostMapping
    // Using requestparam is bad practice, instead, pass in object (create payload object, separate from model for clarity)
    public ResponseEntity<Boolean> transferMoney (@RequestBody TransferRequest transferRequest) {
        Boolean bTransferred = false;
        bTransferred = bankAccSvc.transferMoney(transferRequest.getAccountFrom(), 
                            transferRequest.getAccountTo(), transferRequest.getAmount());
        
        if (bTransferred) {
            return new ResponseEntity<Boolean>(bTransferred, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>(bTransferred, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
