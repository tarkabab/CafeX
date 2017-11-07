import scala.math.BigDecimal
import scala.math.BigDecimal.RoundingMode.HALF_UP

case class CafeX(menu: Menu)

case class Menu(items: List[MenuItem]) {
  private val dictionary: Map[String, MenuItem] =
    items.foldLeft(Map[String, MenuItem]())((d, menuItem) => d + (menuItem.name -> menuItem))

  def findByName(itemName: String): Option[MenuItem] = dictionary.get(itemName)
  def priceOf(itemName: String):Option[BigDecimal] = dictionary.get(itemName).map(_.price)
}

case class MenuItem(name: String, isDrink: Boolean, category: String, price: BigDecimal)

case class StandardBill(menu: Menu, itemNames: String*) {

  val roundingMode: BigDecimal.RoundingMode.Value = HALF_UP
  val total: BigDecimal = itemNames.flatMap(menu.priceOf).sum
  val serviceCharge: BigDecimal = {
    var drinksOnly = true
    var haveHotFood = false
    itemNames.flatMap(menu.findByName).foreach(menuItem => {
      drinksOnly &= menuItem.isDrink
      haveHotFood |= (menuItem.category == "Hot" && !menuItem.isDrink)
    })
    if(drinksOnly) { 0 }
    else if(!haveHotFood) { (total * 0.1).setScale(2, roundingMode)}
    else { (total * 0.2).setScale(2, roundingMode) min 20}
  }
}
