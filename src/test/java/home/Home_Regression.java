package home;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.GenericPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.LogoutPage;
import utils.Config;
import utils.DriverInitialization;

public class Home_Regression extends DriverInitialization {
	public HomePage homePage;
	public LoginPage loginPage;
	public GenericPage genericPage;
	public LogoutPage logoutPage;

	@BeforeClass
	public void initialization() {
		try {
			homePage = new HomePage(driver);
			loginPage = new LoginPage(driver);
			genericPage = new GenericPage(driver);
			logoutPage = new LogoutPage(driver);
			loginPage.loginToTheInstance(Config.getConfigData("username"), Config.getConfigData("password"));
		} catch (Exception e) {
			log.error(e.getMessage());
			homePage.fail(e.getMessage());
		}
	}

	@Test(priority = 1, enabled = true, description = "Validate wishlist item functionality")
	public void validateWishListItemFunctionality() {
		try {
			// select Category
			homePage.selectCategory(Config.getTestData("homeCategoryValue"),
					Config.getTestData("homeSubCategoryValue"));
			// click on wishlist icon
			homePage.clickOnWishListIcon();
			// clear wishlisted items
			homePage.emptyWishList();
			// close wishlist box
			homePage.clickOnWishlistOrCartCloseIcon();
			// validate wishlist item functionality
			if (homePage.wishlistItemAndValidate()) {
				log.info("Successfully validated wishlist item functionality");
			} else {
				log.error("Wishlist item functionality validation failed");
				homePage.fail("Wishlist item functionality validation failed");
			}
		} catch (Exception e) {
			log.error("Failed to validate wishlist functionality due to " + e.getMessage());
			homePage.fail("Failed to validate wishlist functionality due to " + e.getMessage());
		} finally {
			// close wishlist box
			homePage.clickOnWishlistOrCartCloseIcon();
		}
	}

	@Test(priority = 2, enabled = true, description = "Validate cart functionality")
	public void validateCartFunctionality() {
		try {
			// close wishlist box
			homePage.clickOnWishlistOrCartCloseIcon();
			// select Category
			homePage.selectCategory(Config.getTestData("homeCategoryValue"),
					Config.getTestData("homeSubCategoryValue"));
			// click on cart icon
			homePage.clickOnCartIcon();
			// clear cart items
			homePage.emptyCart();
			// close wishlist box
			homePage.clickOnWishlistOrCartCloseIcon();
			// validate wishlist item functionality
			if (homePage.addItemToCartAndValidate()) {
				log.info("Successfully validated cart functionality");
			} else {
				log.error("Add item to cart functionality validation failed");
				homePage.fail("Add item to cart functionality validation failed");
			}
		} catch (Exception e) {
			log.error("Failed to validate cart functionality due to " + e.getMessage());
			homePage.fail("Failed to validate cart functionality due to " + e.getMessage());
		} finally {
			// close cart box
			homePage.clickOnWishlistOrCartCloseIcon();
		}
	}

	@Test(priority = 3, enabled = true, description = "Validate Logout Functionality")
	public void validateLogoutFunctionality() {
		try {
			// logout from the application
			if (logoutPage.logout()) {
				log.info("Logout functionality is validated successfully");
			} else {
				log.error("Logout functionality is not working properly");
				homePage.fail("Logout functionality is not working properly");
			}
		} catch (Exception e) {
			log.error("Failed to validate Logout functionality due to " + e.getMessage());
			homePage.fail("Failed to validate Logout functionality due to " + e.getMessage());
		}
	}
}