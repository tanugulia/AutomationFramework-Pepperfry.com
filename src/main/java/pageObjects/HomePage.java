package pageObjects;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.Config;
import utils.SeleniumUtils;

public class HomePage extends SeleniumUtils {
	private Logger log;
	public GenericPage genericPage;

	public HomePage(WebDriver driver) {
		super(driver);
		log = LogManager.getLogger(this.getClass().getSimpleName());
		genericPage = new GenericPage(driver);
	}

	public void clickOnWishListIcon() {
		try {
			genericPage.closeIFrameIfExists();
			genericPage.checkLoadingSymbol();
			// click on wishlist Icon
			click(Config.getLocator("homeWishlistIcon"));
			log.info("Clicked on wishlist icon");
			genericPage.checkLoadingSymbol();
		} catch (Exception e) {
			log.error("Unable to click on wishlist icon due to " + e.getMessage());
			fail("Unable to click on wishlist icon due to " + e.getMessage());
		}
	}

	public void emptyWishList() {
		try {
			genericPage.closeIFrameIfExists();
			genericPage.checkLoadingSymbol();
			// wait Until wishlist box is visible
			waitUntilElementIsVisible(Config.getLocator("homeWishlistOrCartBox"));
			// check if any items are wishlisted
			if (getElementCount(Config.getLocator("homeWishlistItems")) > 0) {
				// remove items
				List<WebElement> removeItemLink = findElements(Config.getLocator("homeWishlistOrCartRemoveLink"));
				for (WebElement remove : removeItemLink) {
					genericPage.closeIFrameIfExists();
					click(remove);
					log.info("Clicked on remove item link");
					genericPage.checkLoadingSymbol();
				}
			}
			waitFor(2);
			// check if all items are removed
			if (getElementCount(Config.getLocator("homeWishlistItems")) > 0) {
				log.error("Still few items are wishlisted");
				fail("Still few items are wishlisted");
			} else {
				log.info("no items wishlisted");
			}

		} catch (Exception e) {
			log.error("Unable to empty wishlist due to: " + e.getMessage());
			fail("Unable to empty wishlist due to: " + e.getMessage());
		}
	}

	public void clickOnCartIcon() {
		try {
			genericPage.closeIFrameIfExists();
			genericPage.checkLoadingSymbol();
			// click on cart Icon
			click(Config.getLocator("homeCartIcon"));
			log.info("Clicked on cart icon");
			genericPage.checkLoadingSymbol();
		} catch (Exception e) {
			log.error("Unable to click on cart icon due to " + e.getMessage());
			fail("Unable to click on cart icon due to " + e.getMessage());
		}
	}

	public void emptyCart() {
		try {
			genericPage.closeIFrameIfExists();
			genericPage.checkLoadingSymbol();
			// wait until cart box is visible
			waitUntilElementIsVisible(Config.getLocator("homeWishlistOrCartBox"));
			// check if any items are present in the cart
			if (getElementCount(Config.getLocator("homeCartItems")) > 0) {
				// remove items
				List<WebElement> removeItemLink = findElements(Config.getLocator("homeWishlistOrCartRemoveLink"));
				for (WebElement remove : removeItemLink) {
					click(remove);
					log.info("Clicked on remove item link");
					genericPage.checkLoadingSymbol();
				}
			}
			waitFor(1);
			// check if all items are removed
			if (getElementCount(Config.getLocator("homeCartItems")) > 0) {
				log.error("Still few items are present in the cart");
				fail("Still few items are present in the cart");
			} else {
				log.info("no items are present in the cart");
			}

		} catch (Exception e) {
			log.error("Unable to empty cart due to: " + e.getMessage());
			fail("Unable to empty cart due to: " + e.getMessage());
		}
	}

	public void clickOnWishlistOrCartCloseIcon() {
		try {
			genericPage.closeIFrameIfExists();
			if (getAttribute(Config.getLocator("homeWishListOrCartBoxPresence"), "class").contains("active")) {
				// click on close Icon
				click(Config.getLocator("homeWishListOrCartCloseIcon"));
				log.info("Clicked on wishlist or cart close icon");
			} else {
				log.info("Wishlist or cart box is already closed");
			}
		} catch (Exception e) {
			log.error("Unable to click on wishlist or cart close icon due to " + e.getMessage());
			fail("Unable to click on wishlist or cart close icon due to " + e.getMessage());
		}
	}

	public void selectCategory(String category, String subCategory) {
		String categoryTitle, subCategoryValue;
		try {
			genericPage.closeIFrameIfExists();
			// mouse hover over menu
			categoryTitle = Config.getTestData("homeCategoryTitle").replace("categoryTitle", category);
			mouseHover(By.xpath(categoryTitle));
			log.info("mouse hovered over category: " + category);
			// click on sub menu
			subCategoryValue = Config.getTestData("homeSubCategoryTitle")
					.replace("categoryTitle", category.toLowerCase()).replace("subCategoryValue", subCategory);
			click(By.xpath(subCategoryValue));
			log.info("Clicked on sub category: " + subCategory + " under category: " + category);
			waitFor(2);
			genericPage.checkLoadingSymbol();
		} catch (Exception e) {
			log.error("Unable to Clicked on sub category: " + subCategory + " under category: " + category + " due to "
					+ e.getMessage());
			fail("Unable to Clicked on sub category: " + subCategory + " under category: " + category + " due to "
					+ e.getMessage());
		}
	}

	public void selectFilter(String filter, String subFilter) {
		try {
			genericPage.closeIFrameIfExists();
			genericPage.checkLoadingSymbol();
			// click on sub filter
			String subFilterXpath = Config.getTestData("homeSubFilterXpath").replace("filterText", filter)
					.replace("subFilterValue", subFilter);
			click(By.xpath(subFilterXpath));
			log.info("Successfully clicked on filter: " + subFilter + " under category: " + filter);
			genericPage.checkLoadingSymbol();
		} catch (Exception e) {
			log.error("Failed to Click on filter: " + subFilter + " under category: " + filter + " due to "
					+ e.getMessage());
			fail("Failed to Click on filter: " + subFilter + " under category: " + filter + " due to "
					+ e.getMessage());
		}
	}

	public boolean wishlistItemAndValidate() {
		boolean flag = false;
		int itemNumber = 0;
		List<WebElement> wishListIcons, itemTitles;
		String itemText;
		try {
			genericPage.closeIFrameIfExists();
			genericPage.checkLoadingSymbol();
			// wishlist item
			wishListIcons = findElements(Config.getLocator("homeWishlistItemsIcon"));
			click(wishListIcons.get(itemNumber));
			// get item title
			itemTitles = findElements(Config.getLocator("homeItemsTitle"));
			itemText = getText(itemTitles.get(itemNumber));
			log.info("itemText " + itemText);
			// expand wishlist box to validate if item is added to wishlist or not
			waitFor(1);
			clickOnWishListIcon();
			// wait Until wishlist box is visible
			waitUntilElementIsVisible(Config.getLocator("homeWishlistOrCartBox"));
			waitFor(1);
			// check if any items are wishlisted
			waitUntilElementIsVisible(Config.getLocator("homeWishlistItems"));
			if (getElementCount(Config.getLocator("homeWishlistItems")) > 0) {
				List<WebElement> wishListItemsText = findElements(
						Config.getLocator("homeWishListItemsTitleInWishListBox"));
				for (WebElement wishListItemTitle : wishListItemsText) {
					// check if the item title matches in the wishlist items box
					log.info("wishListItemTitle " + getText(wishListItemTitle));
					if (itemText.substring(0, 10).contains(getText(wishListItemTitle).substring(0, 10))) {
						flag = true;
						log.info("Wishlisted item with title: " + itemText + " found in the wishlist box");
						break;
					}
				}
			} else {
				log.error("No items wishlisted");
			}
		} catch (Exception e) {
			log.error("Failed to validate wishlisted item due to " + e.getMessage());
			fail("Failed to validate wishlisted item due to " + e.getMessage());
		}
		return flag;
	}

	public boolean addItemToCartAndValidate() {
		boolean flag = false;
		int itemNumber = 0;
		List<WebElement> allItemsImage, addToCartLinks, itemTitles, cartItemsTitle;
		String itemText;
		try {
			genericPage.closeIFrameIfExists();
			genericPage.checkLoadingSymbol();
			itemTitles = findElements(Config.getLocator("homeItemsTitle"));
			allItemsImage = findElements(Config.getLocator("homeItemsImage"));
			addToCartLinks = findElements(Config.getLocator("homeItemsAddToCartLink"));
			// get item title
			itemText = getText(itemTitles.get(itemNumber));
			log.info("itemText " + itemText);
			// mouse hover over item
			mouseHover(allItemsImage.get(itemNumber));
			waitFor(0.5f);
			// click on Add To Cart link
			click(addToCartLinks.get(itemNumber));
			// wait Until cart box is visible
			waitUntilElementIsVisible(Config.getLocator("homeWishlistOrCartBox"));
			waitFor(1);
			// check if any items are present in the cart
			waitUntilElementIsVisible(Config.getLocator("homeCartItems"));
			if (getElementCount(Config.getLocator("homeCartItems")) > 0) {
				cartItemsTitle = findElements(Config.getLocator("homeCartItemsTitle"));
				for (WebElement cartItemText : cartItemsTitle) {
					// check if the item title matches with the items present in cart
					log.info("cart item text " + getText(cartItemText));
					if (itemText.substring(0, 10).contains(getText(cartItemText).substring(0, 10))) {
						flag = true;
						log.info("Item present in cart with with title: " + itemText);
						break;
					}
				}
			} else {
				log.error("No items present in the cart");
			}
		} catch (Exception e) {
			log.error("Failed to validate item in cart due to " + e.getMessage());
			fail("Failed to validate item in cart due to " + e.getMessage());
		}
		return flag;
	}

}
