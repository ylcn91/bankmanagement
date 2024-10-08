package org.doksanbir.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@EqualsAndHashCode(callSuper = true) // buna bakılması gerekiyor neden?
@NoArgsConstructor
@Slf4j
public class CustomerAccount extends Account {

    private String customerName;
    private String customerAddress;
    private String customerEmail;

   public CustomerAccount(String customerName, String customerAddress,String customerEmail) {
       super();
       this.customerName = customerName;
       this.customerAddress = customerAddress;
       this.customerEmail = customerEmail;
       log.info("Customer account created: {}", customerName);
   }

   //TODO: add more field to this method to print customer account summary.
    @Override
    public void printAccountSummary() {
        log.info("Customer account summary: {}", customerName);
        log.info("Account number: {}", getAccountNumber());
        log.info("Balance: {}", getBalance());
        log.info("Customer name: {}", customerName);
        log.info("Customer address: {}", customerAddress);
    }
}
