object CafeX {

  val menu: List[MenuItem] =
    MenuItem("Cola", "Cold", 0.5) ::
    MenuItem("Coffee", "Hot", 1) ::
    MenuItem("Cheese Sandwich", "Cold", 2) ::
    MenuItem("Ham Sandwich", "Hot", 4.5) ::
    Nil

  def priceOf(itemName: String):Option[BigDecimal] = menu.find(_.name == itemName).map(_.price)
}

case class MenuItem(name: String, category: String, price: BigDecimal)

case class StandardBill(itemNames: String*) {

  val total: BigDecimal = itemNames.flatMap(CafeX.priceOf).sum

  def suggestTip(): BigDecimal = ??? // TODO: missing calculation information
}
