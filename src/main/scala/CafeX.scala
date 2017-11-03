object CafeX {
  val menu: List[MenuItem] =
    MenuItem("Cola", "Cold", 0.5) ::
    MenuItem("Coffee", "Hot", 1) ::
    MenuItem("Cheese Sandwich", "Cold", 1) ::
    MenuItem("Ham Sandwich", "Hot", 4.5) ::
    Nil
}

case class MenuItem(name: String, category: String, price: BigDecimal) {}