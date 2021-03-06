/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cpd4414.assign2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Bino Oommen Samuel < binooommen37@gmail.com >
 */
public class OrderQueue {

    Queue<Order> orderQueue = new ArrayDeque<>();
    Queue<Order> processQueue = new ArrayDeque<>();

    public void add(Order ord) throws Exception {
        String customerId = ord.getCustomerId();
        String customerName = ord.getCustomerName();
        List<Purchase> listOfPurchase = ord.getListOfPurchases();
        if (customerId.isEmpty() || customerName.isEmpty()) {
            throw new customerEmptyException();
        } else if (listOfPurchase.isEmpty()) {
            throw new listEmptyException();
        }
        orderQueue.add(ord);
        ord.setTimeReceived(new Date());
    }

    public Order next() {
        return orderQueue.element();
    }
    
    public void checkTimeReceivedProdQty(Order ord) throws noQuantityInInventoryException, noRecievedTimeException{
        if (ord.getTimeReceived() == null) {
            throw new noRecievedTimeException();
        }
        for (Purchase item : ord.getListOfPurchases()) {
            // Product quantity from database
            int prodQtyFromDB = Inventory.getQuantityForId(item.getProductId());
            // Product quantity from order
            int qtyFromOrder = item.getQuantity();
            // If qty in order is greater than qty in inventory throw exception
            if (qtyFromOrder > prodQtyFromDB) {
                throw new noQuantityInInventoryException("Quantity for product id " + item.getProductId() + " in the inventory is only " + prodQtyFromDB);
            }
        }
    }

    public void process(Order ord) throws Exception {
        checkTimeReceivedProdQty(ord);  // check prod qty and time recieved in db
        ord.setTimeProcessed(new Date());
        orderQueue.remove(ord);
        processQueue.add(ord);
    }
    
    public void fulfill(Order ord) throws Exception {
        checkTimeReceivedProdQty(ord);  // check prod qty in db
        if (ord.getTimeProcessed() == null) {
            throw new noTimeProcessed();
        }
        ord.setTimeFulfilled(new Date());
    }
}
