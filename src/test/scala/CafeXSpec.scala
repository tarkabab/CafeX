import org.scalatest._

import scala.math.BigDecimal.RoundingMode.HALF_UP

trait DefaultMenu {
  val defaultMenu: Menu = Menu(
    MenuItem("Cola", isDrink = true, "Cold", 0.5) ::
      MenuItem("Coffee", isDrink = true, "Hot", 1) ::
      MenuItem("Cheese Sandwich", isDrink = false, "Cold", 2) ::
      MenuItem("Ham Sandwich", isDrink = false, "Hot", 4.5) ::
      Nil)
}

trait MenuForRoundingCase {
  val menu: Menu = Menu(
    MenuItem("Straw", isDrink = false, "Cold", 0.05) :: Nil)
}

class CafeXSpec extends FlatSpec {

  "Cafe X" should "have a Menu" in new DefaultMenu {
    assertCompiles("defaultMenu")
  }

  "Standard Bill" should "be able to produce a total of the passed item list" in new DefaultMenu {
    val total = StandardBill(defaultMenu, "Cola", "Coffee", "Cheese Sandwich").total
    assert(total == 3.5)
  }
  it should "produce 0 when passed an empty item list" in new DefaultMenu {
    val total = StandardBill(defaultMenu).total
    assert(total == 0)
  }
  it should "ignore item(s) not in the menu when calculating the total" in new DefaultMenu {
    val total = StandardBill(defaultMenu, "No Such Item", "No Such Item 2", "Cola").total
    assert(total == 0.5)
  }
  it should "be able to calculate Service Charge" in new DefaultMenu {
    val standardBill = StandardBill(defaultMenu)
    assertCompiles("standardBill.serviceCharge")
  }
  it should "not calculate service charge when all purchased items are drinks" in new DefaultMenu {
    val serviceCharge = StandardBill(defaultMenu, "Cola", "Coffee").serviceCharge
    assert(serviceCharge == 0)
  }
  it should "calculate 10% service charge rounded to 2 decimal places, when purchased items include any food" in new DefaultMenu {
    val standardBill = StandardBill(defaultMenu, "Cola", "Coffee", "Cheese Sandwich")
    val expectedServiceCharge = (standardBill.total * 0.1).setScale(2, HALF_UP)
    assert(standardBill.serviceCharge == expectedServiceCharge)
  }
  it should "calculate service charge 0.05 for items: 11 x \"Straw\" cost 5p each" in new MenuForRoundingCase {
    val numberOfItems = 11
    val standardBill = StandardBill(menu, List.fill(numberOfItems)("Straw"):_*)
    val expectedServiceCharge = BigDecimal(numberOfItems * 0.05 * 0.1).setScale(2, HALF_UP)
    assert(standardBill.serviceCharge == expectedServiceCharge)
  }
  it should "calculate service charge 0.05 for items: 9 x \"Straw\" cost 5p each" in new MenuForRoundingCase {
    val numberOfItems = 9
    val standardBill = StandardBill(menu, List.fill(numberOfItems)("Straw"):_*)
    val expectedServiceCharge = BigDecimal(numberOfItems * 0.05 * 0.1).setScale(2, HALF_UP)
    assert(standardBill.serviceCharge == expectedServiceCharge)
  }
  it should "calculate 20% service charge, maximum 20Ł, when purchased items include any hot food" in new DefaultMenu {
    val standardBill = StandardBill(defaultMenu, "Cola", "Coffee", "Ham Sandwich")
    val expectedServiceCharge = (standardBill.total * 0.2).setScale(2, HALF_UP)
    assert(standardBill.serviceCharge == expectedServiceCharge)
  }
  it should "calculate service charge 1.2Ł (20%) for items: \"Cola\" 50p, \"Coffee\" 1Ł, \"Ham Sandwich\" 4.5Ł" in new DefaultMenu {
    val serviceCharge = StandardBill(defaultMenu, "Cola", "Coffee", "Ham Sandwich").serviceCharge
    val expectedServiceCharge = 1.2
    assert(serviceCharge == expectedServiceCharge)
  }
  it should "calculate service charge 20Ł (maximum) for items: 40 x \"Ham Sandwich\" cost 4.5Ł each" in new DefaultMenu {
    val copiousAmount = 40
    val serviceCharge = StandardBill(defaultMenu, List.fill(copiousAmount)("Ham Sandwich"):_*).serviceCharge
    val expectedServiceCharge = 20
    assert(serviceCharge == expectedServiceCharge)
  }


  "The Menu" should "have Cola" in new DefaultMenu {
    assert(defaultMenu.findByName("Cola").isDefined)
  }
  it should "have Coffee" in new DefaultMenu {
    assert(defaultMenu.findByName("Coffee").isDefined)
  }
  it should "have Cheese Sandwich" in new DefaultMenu {
    assert(defaultMenu.findByName("Cheese Sandwich").isDefined)
  }
  it should "have Ham Sandwich" in new DefaultMenu {
    assert(defaultMenu.findByName("Ham Sandwich").isDefined)
  }

  "Cola" should "cost 50p" in new DefaultMenu {
    val cola = defaultMenu.findByName("Cola").get
    assert(cola.price == 0.5)
  }
  it should "be categorized as Cold" in new DefaultMenu {
    val cola = defaultMenu.findByName("Cola").get
    assert(cola.category == "Cold")
  }
  it should "be considered as a Drink" in new DefaultMenu {
    val cola = defaultMenu.findByName("Cola").get
    assert(cola.isDrink)
  }

  "Coffee" should "cost Ł1" in new DefaultMenu {
    val coffee = defaultMenu.findByName("Coffee").get
    assert(coffee.price == 1)
  }
  it should "be categorized as Hot" in new DefaultMenu {
    val coffee = defaultMenu.findByName("Coffee").get
    assert(coffee.category == "Hot")
  }
  it should "be considered as a Drink" in new DefaultMenu {
    val coffee = defaultMenu.findByName("Coffee").get
    assert(coffee.isDrink)
  }

  "Cheese Sandwich" should "cost Ł2" in new DefaultMenu {
    val cheeseSandwich = defaultMenu.findByName("Cheese Sandwich").get
    assert(cheeseSandwich.price == 2)
  }
  it should "be categorized as Cold" in new DefaultMenu {
    val cheeseSandwich = defaultMenu.findByName("Cheese Sandwich").get
    assert(cheeseSandwich.category == "Cold")
  }
  it should "not be considered as a Drink" in new DefaultMenu {
    val cheeseSandwich = defaultMenu.findByName("Cheese Sandwich").get
    assert(!cheeseSandwich.isDrink)
  }

  "Ham Sandwich" should "cost Ł4.50" in new DefaultMenu {
    val hamSandwich = defaultMenu.findByName("Ham Sandwich").get
    assert(hamSandwich.price == 4.5)
  }
  it should "be categorized as Hot" in new DefaultMenu {
    val hamSandwich = defaultMenu.findByName("Ham Sandwich").get
    assert(hamSandwich.category == "Hot")
  }
  it should "not be considered as a Drink" in new DefaultMenu {
    val hamSandwich = defaultMenu.findByName("Ham Sandwich").get
    assert(!hamSandwich.isDrink)
  }

}
