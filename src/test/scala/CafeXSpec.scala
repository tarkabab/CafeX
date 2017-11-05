import org.scalatest._

class CafeXSpec extends FlatSpec {

  "Cafe X" should "have a Menu" in {
    assertCompiles("CafeX.menu")
  }

  "Standar Bill" should "be able to produce a total of the passed item list" in {
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
  it should "be able to suggest Tip" in {
    val standardBill = StandardBill()
    assertCompiles("standardBill.suggestTip()")
  }

  "The Menu" should "have Cola" in {
    assert(CafeX.menu.contains(MenuItem("Cola", isDrink = true, "Cold", 0.5)))
  }
  it should "have Coffee" in {
    assert(CafeX.menu.contains(MenuItem("Coffee", isDrink = true, "Hot", 1)))
  }
  it should "have Cheese Sandwich" in {
    assert(CafeX.menu.contains(MenuItem("Cheese Sandwich", isDrink = false, "Cold", 2)))
  }
  it should "have Ham Sandwich" in {
    assert(CafeX.menu.contains(MenuItem("Ham Sandwich", isDrink = false, "Hot", 4.5)))
  }

  "Cola" should "cost 50p" in {
    val cola = CafeX.menu.find(_.name == "Cola").get
    assert(cola.price == 0.5)
  }
  it should "be categorized as Cold" in {
    val cola = CafeX.menu.find(_.name == "Cola").get
    assert(cola.category == "Cold")
  }
  it should "be considered as a Drink" in {
    val cola = CafeX.menu.find(_.name == "Cola").get
    assert(cola.isDrink)
  }

  "Coffee" should "cost Ł1" in {
    val coffee = CafeX.menu.find(_.name == "Coffee").get
    assert(coffee.price == 1)
  }
  it should "be categorized as Hot" in {
    val coffee = CafeX.menu.find(_.name == "Coffee").get
    assert(coffee.category == "Hot")
  }
  it should "be considered as a Drink" in {
    val coffee = CafeX.menu.find(_.name == "Coffee").get
    assert(coffee.isDrink)
  }

  "Cheese Sandwich" should "cost Ł2" in {
    val cheeseSandwich = CafeX.menu.find(_.name == "Cheese Sandwich").get
    assert(cheeseSandwich.price == 2)
  }
  it should "be categorized as Cold" in {
    val cheeseSandwich = CafeX.menu.find(_.name == "Cheese Sandwich").get
    assert(cheeseSandwich.category == "Cold")
  }
  it should "not be considered as a Drink" in {
    val cheeseSandwich = CafeX.menu.find(_.name == "Cheese Sandwich").get
    assert(!cheeseSandwich.isDrink)
  }

  "Ham Sandwich" should "cost Ł4.50" in {
    val hamSandwich = CafeX.menu.find(_.name == "Ham Sandwich").get
    assert(hamSandwich.price == 4.5)
  }
  it should "be categorized as Hot" in {
    val hamSandwich = CafeX.menu.find(_.name == "Ham Sandwich").get
    assert(hamSandwich.category == "Hot")
  }
  it should "not be considered as a Drink" in {
    val hamSandwich = CafeX.menu.find(_.name == "Ham Sandwich").get
    assert(!hamSandwich.isDrink)
  }

}
