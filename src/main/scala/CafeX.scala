import scala.math.BigDecimal
import BigDecimal.RoundingMode.Value
import scala.math.BigDecimal.RoundingMode.HALF_UP

case class CafeX(menu: Menu)

case class Menu(items: List[MenuItem]) {
  private val dictionary: Map[String, MenuItem] =
    items.foldLeft(Map[String, MenuItem]())((d, menuItem) => d + (menuItem.name -> menuItem))

  def findByName(itemName: String): Option[MenuItem] = dictionary.get(itemName)
  def priceOf(itemName: String):Option[BigDecimal] = dictionary.get(itemName).map(_.price)
}

case class MenuItem(name: String, isDrink: Boolean, isPremium: Boolean, category: String, price: BigDecimal)

case class StandardBill(menu: Menu, itemNames: String*) {

  val roundingMode: Value = HALF_UP
  val total: BigDecimal = itemNames.flatMap(menu.priceOf).sum
  val serviceCharge: BigDecimal = {
    val (drinksOnly, haveHotFood, havePremiumItem) = analyzeOrders(itemNames.toList)
    calculateServiceCharge(drinksOnly, haveHotFood, havePremiumItem)
  }

  private def calculateServiceCharge(drinksOnly: Boolean, haveHotFood: Boolean, havePremiumItem: Boolean): BigDecimal = {
    if(havePremiumItem) { (total * 0.25).setScale(2, roundingMode) min 40 }
    else if(drinksOnly) { 0 }
    else if(!haveHotFood) { (total * 0.1).setScale(2, roundingMode) }
    else { (total * 0.2).setScale(2, roundingMode) min 20 }
  }

  private def analyzeOrders(orders: List[String]): (Boolean, Boolean, Boolean) = {
    var drinksOnly = true
    var haveHotFood = false
    var havePremiumItem = false
    itemNames.flatMap(menu.findByName).foreach(menuItem => {
      drinksOnly &= menuItem.isDrink
      havePremiumItem |= menuItem.isPremium
      haveHotFood |= (menuItem.category == "Hot" && !menuItem.isDrink)
    })
    (drinksOnly, haveHotFood, havePremiumItem)
  }
}
