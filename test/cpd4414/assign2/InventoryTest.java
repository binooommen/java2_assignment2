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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bino Oommen Samuel < binooommen37@gmail.com >
 */
public class InventoryTest {

    public InventoryTest() {
    }

    @Test
    public void testGetQuantityForIdReturnsErrorForNegativeID() {
        int id = -100;
        int expResult = -1;
        int result = Inventory.getQuantityForId(id);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetQuantityForIdReturnsValidResultForPositiveID() {
        int id = 4;
        int result = Inventory.getQuantityForId(id);
        assertTrue(result >= 0);
    }

}
