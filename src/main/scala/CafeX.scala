object CafeX {

  val menu: List[MenuItem] =
    MenuItem("Cola", isDrink = true, "Cold", 0.5) ::
    MenuItem("Coffee", isDrink = true, "Hot", 1) ::
    MenuItem("Cheese Sandwich", isDrink = false, "Cold", 2) ::
    MenuItem("Ham Sandwich", isDrink = false, "Hot", 4.5) ::
    Nil

  def findByName(itemName: String):Option[MenuItem] = menu.find(_.name == itemName)

  def priceOf(itemName: String):Option[BigDecimal] = findByName(itemName).map(_.price)
}

case class MenuItem(name: String, isDrink: Boolean, category: String, price: BigDecimal)

case class StandardBill(itemNames: String*) {

  val total: BigDecimal = itemNames.flatMap(CafeX.priceOf).sum
  val serviceCharge: BigDecimal = {
    var drinksOnly = true
    var haveHotFood = false
    itemNames.flatMap(CafeX.findByName).foreach(menuItem => {
      drinksOnly &= menuItem.isDrink
      haveHotFood |= (menuItem.category == "Hot" && !menuItem.isDrink)
    })
    if(drinksOnly) { 0 }
    else if(!haveHotFood) { total * 0.1 setScale 2}
    else { total * 0.2 min 20 setScale 2}
  }
}
