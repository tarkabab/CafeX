object CafeX {

  val menu: List[MenuItem] =
    MenuItem("Cola", isDrink = true, "Cold", 0.5) ::
    MenuItem("Coffee", isDrink = true, "Hot", 1) ::
    MenuItem("Cheese Sandwich", isDrink = false, "Cold", 2) ::
    MenuItem("Ham Sandwich", isDrink = false, "Hot", 4.5) ::
    Nil

  def priceOf(itemName: String):Option[BigDecimal] = menu.find(_.name == itemName).map(_.price)
}

case class MenuItem(name: String, isDrink: Boolean, category: String, price: BigDecimal)

case class StandardBill(itemNames: String*) {

  val total: BigDecimal = itemNames.flatMap(CafeX.priceOf).sum

  def suggestTip(): BigDecimal = ??? // TODO: missing calculation information
}
