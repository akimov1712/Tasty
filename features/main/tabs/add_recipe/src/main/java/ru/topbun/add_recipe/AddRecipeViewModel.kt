package ru.topbun.add_recipe

import android.content.Context
import android.net.Uri
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import ru.topbun.add_recipe.AddRecipeState.AddRecipeScreenState
import ru.topbun.add_recipe.AddRecipeState.AddRecipeScreenState.Error
import ru.topbun.android.ScreenModelState
import ru.topbun.android.compressImageUriToByteArray
import ru.topbun.android.wrapperException
import ru.topbun.common.resolve
import ru.topbun.domain.entity.recipe.DifficultyType
import ru.topbun.domain.entity.recipe.IngredientsEntity
import ru.topbun.domain.entity.recipe.RecipeEntity
import ru.topbun.domain.entity.recipe.StepEntity
import ru.topbun.domain.useCases.auth.CheckExistsTokenUseCase
import ru.topbun.domain.useCases.recipe.AddRecipeUseCase
import ru.topbun.domain.useCases.recipe.UploadImageUseCase

class AddRecipeViewModel(
    private val context: Context,
    private val uploadImageUseCase: UploadImageUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
    private val checkTokenExists: CheckExistsTokenUseCase
): ScreenModelState<AddRecipeState>(AddRecipeState()){

    fun addRecipe() = screenModelScope.launch {
        wrapperException({
            updateState { copy(screenState = AddRecipeScreenState.Loading) }
            checkTokenExists()
            if(stateValue.imageUri == null) {
                updateState { copy(screenState = Error("Установите обложку для рецепта")) }
                return@wrapperException
            }
            if(stateValue.title.length < 4){
                updateState { copy(screenState = Error("Название не может быть меньше 4 символов")) }
                return@wrapperException
            }
            val fieldsError = listOf(stateValue.title.trim(), stateValue.protein?.trim(), stateValue.fat?.trim(), stateValue.carbs?.trim())
            if(fieldsError.contains(null) || fieldsError.contains("")) {
                updateState { copy(screenState = Error("Не все поля заполнены")) }
                return@wrapperException
            }

            val previewCompress = compressImageUriToByteArray(context, stateValue.imageUri!!) ?: run{
                updateState { copy(screenState = Error("Установите обложку для рецепта")) }
                return@wrapperException
            }


            val ingredients = stateValue.ingredients.filter { it.value.isNotEmpty() && it.name.isNotEmpty() }
            if (ingredients.isEmpty()){
                updateState { copy(screenState = Error("Укажите ингредиенты")) }
                return@wrapperException
            }

            val steps = stateValue.steps.filter { it.text.isNotEmpty()}.map { step ->
                async {
                    if (step.preview != null){
                        val imageCompress = compressImageUriToByteArray(context, Uri.parse(step.preview)) ?: return@async step
                        val linkImage = uploadImageUseCase(imageCompress).resolve()
                        step.copy(preview = linkImage)
                    } else step
                }
            }.awaitAll()
            if (steps.isEmpty()){
                updateState { copy(screenState = Error("Укажите шаги приготовления")) }
                return@wrapperException
            }

            val previewImageLink = uploadImageUseCase(previewCompress).resolve()

            val recipe = RecipeEntity(
                id = 0,
                userId = null,
                title = stateValue.title.trim(),
                description = stateValue.description.trim(),
                image = previewImageLink,
                isFavorite = false,
                categories = emptyList(),
                timeCooking = stateValue.cookingTime,
                difficulty = stateValue.difficulty,
                carbs = stateValue.carbs?.toIntOrNull(),
                fat = stateValue.fat?.toIntOrNull(),
                protein = stateValue.protein?.toIntOrNull(),
                kcal = stateValue.getKcal(),
                ingredients = ingredients,
                steps = steps,
            )

            addRecipeUseCase(recipe)
            updateState { copy(screenState = AddRecipeScreenState.Success) }
            updateState { AddRecipeState() }
        }){ _, msg ->
            updateState { copy(screenState = Error(msg)) }
        }
    }

    fun addStep() = updateState {
        val newList = steps + StepEntity(0, "", null, 0)
        copy(steps = newList)
    }

    fun removeStep(index: Int) = updateState {
        val newList = steps.toMutableList()
        newList.removeAt(index)
        copy(steps = newList)
    }

    fun changeStepText(text: String, index: Int) = updateState {
        val newList = steps.mapIndexed { i, step ->
            if (index == i) step.copy(text = text) else step
        }
        copy(steps = newList)
    }

    fun changeStepImage(image: Uri?, index: Int) = updateState {
        val newList = steps.mapIndexed { i, ingr ->
            if (index == i) ingr.copy(preview = image?.toString()) else ingr
        }
        copy(steps = newList)
    }

    fun addIngredient() = updateState {
        val newList = ingredients + IngredientsEntity(0, "", "")
        copy(ingredients = newList)
    }

    fun removeIngredient(index: Int) = updateState {
        val newList = ingredients.toMutableList()
        newList.removeAt(index)
        copy(ingredients = newList)
    }

    fun changeIngredientName(name: String, index: Int) = updateState {
        val newList = ingredients.mapIndexed { i, ingr ->
            if (index == i) ingr.copy(name = name) else ingr
        }
        copy(ingredients = newList)
    }

    fun changeIngredientValue(value: String, index: Int) = updateState {
        val newList = ingredients.mapIndexed { i, ingr ->
            if (index == i) ingr.copy(value = value) else ingr
        }
        copy(ingredients = newList)
    }

    fun changeImage(uri: Uri?) = updateState { copy(imageUri = uri) }
    fun changeTitle(title: String) = updateState { copy(title = title, titleIsError = title.length < 4) }
    fun changeDescr(descr: String) = updateState { copy(description = descr) }
    fun changeCookingTime(cookingTime: Int?) = updateState { copy(cookingTime = cookingTime) }
    fun changeDifficulty(difficulty: DifficultyType?) = updateState { copy(difficulty = difficulty) }

    fun changeProtein(protein: String) {
        val protein = protein.ifBlank { null }
        updateState { copy(protein = protein, proteinIsError = protein == null) }
    }

    fun changeCarbs(carbs: String) {
        val carbs = carbs.ifBlank { null }
        updateState { copy(carbs = carbs, carbsIsError = carbs == null) }
    }

    fun changeFat(fat: String) {
        val fat = fat.ifBlank { null }
        updateState { copy(fat = fat, fatIsError = fat == null) }
    }


}