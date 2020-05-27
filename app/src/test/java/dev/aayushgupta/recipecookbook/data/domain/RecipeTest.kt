package dev.aayushgupta.recipecookbook.data.domain

import dev.aayushgupta.recipecookbook.data.database.asDomainModel
import dev.aayushgupta.recipecookbook.utils.provideSampleRecipes
import org.hamcrest.Matchers.`is`
import org.junit.Assert.*
import org.junit.Test

class RecipeTest {

    // TODO: Write more elaborate tests

    @Test
    fun testOneRecipeAndDbRecipeConversion() {
        // Given a list of recipes
        val recipe = provideSampleRecipes()[0]

        // Convert them to DbRecipe and back
        val result = recipe.asDatabaseModel().asDomainModel()

        // Then check if resulting list is same
        assertThat(result, `is`(recipe))
    }

    @Test
    fun testListRecipeAndDbRecipeConversion() {
        // Given a list of recipes
        val recipes = provideSampleRecipes()

        // Convert them to DbRecipe and back
        val results = recipes.asDatabaseModel().asDomainModel()

        // Then check if resulting list is same
        assertThat(results, `is`(recipes))
    }

    /*
    TODO
        NOTE:
        Currently this test will fail as we have introduced a special char into the list of steps.
        This special char is the same as the separator used during domain conversion.
        Hence, it leads to duplicate entries when the model is converted back:
        "pour water | keep it" becomes "pour water ", " keep it"
        Solutions for this are 3 fold:
            - Firstly, We can ensure that no special chars are permitted while entering steps.
                - Issue with this is, now you cant use &, ' or " which users might want to use.
            - Secondly, We can write a custom json adapter which will esacape all spl. chars in strings.
                - Will make conversion impl. more sophisticated as I'll be writing a json adapter for only one field.
            - Thridly, Wrap each step into a data class.
              So, instead of being List<String>, entries will be List<Step> where Step has a String member.
                - This seems the most easiest solution to implement.
            - Finally, Once we move to using proper relations, instead of cramming all embedded classes as joined json strings,
              the need for conversion functions will become obsolete as Domain and DB model will be identical eventually.
                - Thats too far in the future.
     */
    @Test
    fun testSpecialRecipeWithDelimiterAndDbRecipe() {
        // Given special case of recipe
        val recipe = Recipe(
            title = "Noodles", cuisine = "Chinese",
            description = "Noodles", type = RecipeType.MAIN,
            cookingTime = RecipeTime(value = 45F, unit = TimeUnit.MINUTES),
            flavor = FlavorType.SAVORY,
            ingredients = listOf(
                Ingredient("pepper", 1F, MeasureUnit.TABLE_SPOON),
                Ingredient("chili", 1F, MeasureUnit.PINCH),
                Ingredient("noodles", 1F, MeasureUnit.CUP)
            ),
            steps = listOf(
                "Boil noodles",
                "add chili|asd",
                "add peppers",
                "serve hot"
            ),
            images = listOf(
                RecipeImage("url1", isFeature = false, isLocal = false),
                RecipeImage("url2", isFeature = false, isLocal = false),
                RecipeImage("url3", isFeature = false, isLocal = false)
            )
        )

        // Convert to db model and back
        val result = recipe.asDatabaseModel().asDomainModel()

        // Check if same
        assertThat(result, `is`(recipe))
    }

}