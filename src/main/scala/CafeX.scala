object CafeX {

  val menu = Menu(
    MenuItem("Cola", isDrink = true, "Cold", 0.5) ::
    MenuItem("Coffee", isDrink = true, "Hot", 1) ::
    MenuItem("Cheese Sandwich", isDrink = false, "Cold", 2) ::
    MenuItem("Ham Sandwich", isDrink = false, "Hot", 4.5) ::
    Nil)
}

case class Menu(items: List[MenuItem]) {
  private val dictionary: Map[String, MenuItem] =
    items.foldLeft(Map[String, MenuItem]())((d, menuItem) => d + (menuItem.name -> menuItem))

  def findByName(itemName: String): Option[MenuItem] = dictionary.get(itemName)
  def priceOf(itemName: String):Option[BigDecimal] = dictionary.get(itemName).map(_.price)
}

case class MenuItem(name: String, isDrink: Boolean, category: String, price: BigDecimal)

case class StandardBill(itemNames: String*) {

  val total: BigDecimal = itemNames.flatMap(CafeX.menu.priceOf).sum
  val serviceCharge: BigDecimal = {
    var drinksOnly = true
    var haveHotFood = false
    itemNames.flatMap(CafeX.menu.findByName).foreach(menuItem => {
      drinksOnly &= menuItem.isDrink
      haveHotFood |= (menuItem.category == "Hot" && !menuItem.isDrink)
    })
    if(drinksOnly) { 0 }
    else if(!haveHotFood) { total * 0.1 setScale 2}
    else { total * 0.2 min 20 setScale 2}
  }
}
