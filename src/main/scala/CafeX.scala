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
  val serviceCharge: BigDecimal = calculateServiceCharge(analyzeOrders(itemNames.toList))

  case class BillAnalysisResult(drinksOnly: Boolean, haveHotFood: Boolean, havePremiumItem: Boolean)

  protected def analyzeOrders(orders: List[String]): BillAnalysisResult = {
    itemNames
      .flatMap(menu.findByName)
      .foldLeft(BillAnalysisResult(drinksOnly = true, haveHotFood = false, havePremiumItem = false)) {
        (result, menuItem) => BillAnalysisResult(
          result.drinksOnly & menuItem.isDrink,
          result.haveHotFood | (menuItem.category == "Hot" && !menuItem.isDrink),
          result.havePremiumItem | menuItem.isPremium
        )
      }
  }

  protected def calculateServiceCharge(result: BillAnalysisResult): BigDecimal = {
    if(result.havePremiumItem) { (total * 0.25).setScale(2, roundingMode) min 40 }
    else if(result.drinksOnly) { 0 }
    else if(!result.haveHotFood) { (total * 0.1).setScale(2, roundingMode) }
    else { (total * 0.2).setScale(2, roundingMode) min 20 }
  }
}
