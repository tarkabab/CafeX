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
    assert(CafeX.menu.contains(MenuItem("Cola", "Cold", 0.5)))
  }
  it should "have Coffee" in {
    assert(CafeX.menu.contains(MenuItem("Coffee", "Hot", 1)))
  }
  it should "have Cheese Sandwich" in {
    assert(CafeX.menu.contains(MenuItem("Cheese Sandwich", "Cold", 2)))
  }
  it should "have Ham Sandwich" in {
    assert(CafeX.menu.contains(MenuItem("Ham Sandwich", "Hot", 4.5)))
  }

  "Cola" should "cost 50p" in {
    val cola = CafeX.menu.collectFirst{ case item: MenuItem if item.name == "Cola" => item }
    assert(cola.nonEmpty)
    assert(cola.get.price == 0.5)
  }
  it should "be categorized as Cold" in {
    val cola = CafeX.menu.collectFirst{ case item: MenuItem if item.name == "Cola" => item }
    assert(cola.nonEmpty)
    assert(cola.get.category == "Cold")
  }

  "Coffee" should "cost Ł1" in {
    val coffee = CafeX.menu.collectFirst{ case item: MenuItem if item.name == "Coffee" => item }
    assert(coffee.nonEmpty)
    assert(coffee.get.price == 1)
  }
  it should "be categorized as Hot" in {
    val coffee = CafeX.menu.collectFirst{ case item: MenuItem if item.name == "Coffee" => item }
    assert(coffee.nonEmpty)
    assert(coffee.get.category == "Hot")
  }

  "Cheese Sandwich" should "cost Ł2" in {
    val cheeseSandwich = CafeX.menu.collectFirst{ case item: MenuItem if item.name == "Cheese Sandwich" => item }
    assert(cheeseSandwich.nonEmpty)
    assert(cheeseSandwich.get.price == 2)
  }
  it should "be categorized as Cold" in {
    val cheeseSandwich = CafeX.menu.collectFirst{ case item: MenuItem if item.name == "Cheese Sandwich" => item }
    assert(cheeseSandwich.nonEmpty)
    assert(cheeseSandwich.get.category == "Cold")
  }

  "Ham Sandwich" should "cost Ł4.50" in {
    val hamSandwich = CafeX.menu.collectFirst{ case item: MenuItem if item.name == "Ham Sandwich" => item }
    assert(hamSandwich.nonEmpty)
    assert(hamSandwich.get.price == 4.5)
  }
  it should "be categorized as Hot" in {
    val hamSandwich = CafeX.menu.collectFirst{ case item: MenuItem if item.name == "Ham Sandwich" => item }
    assert(hamSandwich.nonEmpty)
    assert(hamSandwich.get.category == "Hot")
  }

}
