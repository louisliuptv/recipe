### 1. Key Architectural Decisions (Clean Architecture + MVI Pattern)
```
├── core/                    # Base MVI classes
├── di/                      # Dependency injection
├── data/                    # Data layer (DTOs, mappers, repositories implement, datasource)
├── domain/                  # Domain layer (models, repositories interfaces, use cases)
└── presentation/            # UI layer (Compose screens, MVI ViewModel)
```
- Uses `Event`, `State`, and `Effect` for predictable state management and unidirectional Data Flow(Event → State → Effect)
- Domain layer handles business logic, like sorting recipes by total time
- Data layer handles JSON parsing via Gson and provides repositories
- Presentation layer renders Compose UI
- Hilt easy DI for viewmodel, repository, usecase, datasource
- Unit testing for viewmodel and usecase
- Using maestro script for automatic testing portrait Detail Screen and landscape List Screen.


### 2. Trade-off 
- Remove RecipeRepositoryUseCase that wrapper around `getRecipes()`, the UserCase are mainly used for heavy flow, simple apis like  `getRecipes()` is lightweight and easy to include more apis and flows into repository(usecase is designed for only one flow).  
- Remove UI Status model because this exercise is simple UI that doesn't require specific UI format like different time format display in List/Detail Screen.Although domain model is enough in this case, UI Status model is often necessary in real project.


### 3. What I Would Improve With More Time

### 1. **Pagination**
- Implement `PagingSource` + `Pager` for large recipe lists

### 2. **Modularization**
- Split into `data`, `domain`, `presentation`, `design system` modules

### 3. **Two ViewModel**
- Implement RecipeListViewModel and RecipeDetailViewModel
- Use Navigation Component when navigating between List and Detail screens

### 3. **More Unit Testing**
- Data Layer like datasource, mapping, repository should be tested in isolation

### 4. **Implement Atomic Design for UI components**
- Split Screen into Templates, Organisms, Molecules and Atoms, add more reusable UI to design system module


## Running Tests

```bash
# Unit tests
./gradlew app:testDebugUnitTest

# Maestro automatic tests
maestro test maestro/recipe_flow.yaml
```

## Environment Setup

**Requirements:**
- Android Studio Ladybug or later
- Java 17+
- Android SDK 34+
- Gradle 8.7+

