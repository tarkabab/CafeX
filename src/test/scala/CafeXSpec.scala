import org.scalatest._

class CafeXSpec extends FlatSpec {

  "Cafe X" should "have a Menu" in {
    assertCompiles("CafeX.menu")
  }

  "Standard Bill" should "be able to produce a total of the passed item list" in {
    val total = StandardBill("Cola", "Coffee", "Cheese Sandwich").total
    assert(total == 3.5)
  }
  it should "produce 0 when passed an empty item list" in {
    val total = StandardBill().total
    assert(total == 0)
  }
  it should "ignore item(s) not in the menu when calculating the total" in {
    val total = StandardBill("No Such Item", "No Such Item 2", "Cola").total
    assert(total == 0.5)
  }
  it should "be able to calculate Service Charge" in {
    val standardBill = StandardBill()
    assertCompiles("standardBill.serviceCharge")
  }
  it should "not calculate service charge when all purchased items are drinks" in {
    val serviceCharge = StandardBill("Cola", "Coffee").serviceCharge
    assert(serviceCharge == 0)
  }
  it should "calculate 10% service charge rounded to 2 decimal places, when purchased items include any food" in {
    val standardBill = StandardBill("Cola", "Coffee", "Cheese Sandwich")
    val expectedServiceCharge = standardBill.total * 0.1 setScale 2
    assert(standardBill.serviceCharge == expectedServiceCharge)
  }
  it should "calculate 20% service charge, maximum 20Ł, when purchased items include any hot food" in {
    val standardBill = StandardBill("Cola", "Coffee", "Ham Sandwich")
    val expectedServiceCharge = standardBill.total * 0.2
    assert(standardBill.serviceCharge == expectedServiceCharge)
  }


  "The Menu" should "have Cola" in {
    assert(CafeX.menu.findByName("Cola").isDefined)
  }
  it should "have Coffee" in {
    assert(CafeX.menu.findByName("Coffee").isDefined)
  }
  it should "have Cheese Sandwich" in {
    assert(CafeX.menu.findByName("Cheese Sandwich").isDefined)
  }
  it should "have Ham Sandwich" in {
    assert(CafeX.menu.findByName("Ham Sandwich").isDefined)
  }

  "Cola" should "cost 50p" in {
    val cola = CafeX.menu.findByName("Cola").get
    assert(cola.price == 0.5)
  }
  it should "be categorized as Cold" in {
    val cola = CafeX.menu.findByName("Cola").get
    assert(cola.category == "Cold")
  }
  it should "be considered as a Drink" in {
    val cola = CafeX.menu.findByName("Cola").get
    assert(cola.isDrink)
  }

  "Coffee" should "cost Ł1" in {
    val coffee = CafeX.menu.findByName("Coffee").get
    assert(coffee.price == 1)
  }
  it should "be categorized as Hot" in {
    val coffee = CafeX.menu.findByName("Coffee").get
    assert(coffee.category == "Hot")
  }
  it should "be considered as a Drink" in {
    val coffee = CafeX.menu.findByName("Coffee").get
    assert(coffee.isDrink)
  }

  "Cheese Sandwich" should "cost Ł2" in {
    val cheeseSandwich = CafeX.menu.findByName("Cheese Sandwich").get
    assert(cheeseSandwich.price == 2)
  }
  it should "be categorized as Cold" in {
    val cheeseSandwich = CafeX.menu.findByName("Cheese Sandwich").get
    assert(cheeseSandwich.category == "Cold")
  }
  it should "not be considered as a Drink" in {
    val cheeseSandwich = CafeX.menu.findByName("Cheese Sandwich").get
    assert(!cheeseSandwich.isDrink)
  }

  "Ham Sandwich" should "cost Ł4.50" in {
    val hamSandwich = CafeX.menu.findByName("Ham Sandwich").get
    assert(hamSandwich.price == 4.5)
  }
  it should "be categorized as Hot" in {
    val hamSandwich = CafeX.menu.findByName("Ham Sandwich").get
    assert(hamSandwich.category == "Hot")
  }
  it should "not be considered as a Drink" in {
    val hamSandwich = CafeX.menu.findByName("Ham Sandwich").get
    assert(!hamSandwich.isDrink)
  }

}
