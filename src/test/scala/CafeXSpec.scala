import org.scalatest._

class CafeXSpec extends FlatSpec {

  "Cafe X" should "have a Menu" in {
    assertCompiles("CafeX.menu")
  }

  "The Menu" should "have Cola" in {
    assert(CafeX.menu.contains(MenuItem("Cola", "Cold", 0.5)))
  }
  it should "have Coffee" in {
    assert(CafeX.menu.contains(MenuItem("Coffee", "Hot", 1)))
  }
  it should "have Cheese Sandwich" in {
    assert(CafeX.menu.contains(MenuItem("Cheese Sandwich", "Cold", 1)))
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

  "Cheese Sandwich" should "cost Ł1" in {
    val cheeseSandwich = CafeX.menu.collectFirst{ case item: MenuItem if item.name == "Cheese Sandwich" => item }
    assert(cheeseSandwich.nonEmpty)
    assert(cheeseSandwich.get.price == 1)
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

  "Bill" should "suggestTip" in {
    val bill = Bill(Nil)
    assertCompiles("bill.suggestTip()")
  }
}
