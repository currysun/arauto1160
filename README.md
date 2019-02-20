# BDD Automated Tests for AgileREPORTER
> Good introduction to BDD [here](https://lizkeogh.com/behaviour-driven-development)

## Libraries
- Java 8
- [Selenide](http://selenide.org/) (Selenium API wrapper)
- Java Cucumber
- Spring Test Framework

## Scripts
- `only-run-tags.sh @tag1[,@tag2...] [profile1,profile2]`
    - executes the BDD tests that are have the provided tags and with provided profiles
    - example: `only-run-tags.sh @AnalysisModule it`
- `run-analysisModule.bat`
    - runs @AnalysisModule tests 

## Supported Browsers

- default: portable Chromium v58
- Chrome/Chromium installed on the OS (default path/admin rights)
    - activated by `-Dspring.profiles.active=native,default`
- Firefox 47 and less
- _TODO_: installed IE browser
- _TODO_: portable Firefox 47+ (Marionette driver)


### Enabling Headless Tests

> Note: Headless testing is supported  for Chrome/Chromium  59+
- activate the Spring profile called `chrome-headless` by passing the property 
`-Dspring.profiles.active=chrome-headless`


### Conventions

#### Design

- use fluent interface pattern for `PageObject`-like classes
- encapsulate interaction with any `Selenium`/`Selenide` elements in `PageObject`-like 
classes
- create 1 `PageObject` per page in AR
- create 1 `DialogObject` per dialog in AR when a dialog has a more complicated logic 
(besides filtering)
    - `DialogObject` should only be accessible from the `PageObject` representing the
    page in AR where the dialog appears
- create `ComponentObject`/s when the `PageObject` contains a lot of element fields
and common behaviour can be encapsulated in the `ComponentObject` 
- create a private field for an element on a page that is static and does not change 
    - sometimes these might be hard to figure out visually, but usually 
    the `StaleElementReferenceException` is a hint that the element is dynamic
- create a static private method for dynamic elements
- prefer Selenide asserts over explicit waits or sleeps
    - `$(By.id("elemId")).shouldBe(visible)` 
        - instead of `Selenide.wait(1000)` 
        - `$(By.id("elemId")).waitUntil(visible, timeout)` is useful when custom 
        timeouts are needed 
- prefer using `$(By.id("elemId")).setValue(String)` over 
`$(By.id("elemId")).sendKeys(String)` as `setValue()` is more robust 

#### Naming

- try to use the name of the element rendered on the screen
- add the type of the element at the end of the name
- for dynamic elements, prefix the method name with `find` 
- examples
    - `userNameInput` - input field with the label _Username_
    - `addButton` - button with the label _Add_
    - `openReturnLink` - link/anchor tag with the label _Open Return_
    - `activeCheckbox` - checkbox with the label _Active_
    - `assignReturnsTab` - tab with the label _Assigned Returns_
    - `bobItem` - element from a list of elements, that with the label _Bob_
    - `regulatorDropdown` - dropdown select element with the label _Regulator_
    - `findUserGroupNameItem(String)` - find an element that represents the name
    of a user group in a list of elements, based on a parameter


#### Working with feature files

- use the Cucumber plugin for the IDE to generate step definition methods
    - using this method allows the plugin to generate consistent regular expressions
    - examples
        - number with multiple digits: `(\\d+)`
        - string surrounded by quotes: `"([^"]*)"`
        - date with forward slashes: `(\\d+/\\d+/\\d+)`
            - use `@Transformer` to convert to `LocalDate`
        - list of elements: `(.*)`
            - use `@Delimiter(", ")` for delimiting elements by comma


### Using Groovy Console to drive the browser.

> Note: Groovy needs to be enabled in IntelliJ IDEA

- start a Groovy Console from `Tools > Groovy Console...`
- run

```groovy
import com.codeborne.selenide.Selenide

// set property when Chrome is installed to custom path
// System.setProperty("webdriver.chrome.binary", "$PathToChrome");
System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.win.exe");
System.setProperty("selenide.browser", "com.lombardrisk.config.driver.ChromeGui");
System.setProperty("browser.downloads.dir", "target/downloads");
Selenide.open("http://localhost:8080/agilereporter")
```