#  **ANDIE**

## Instructions
* Build and run program via Gradle. Gradle for java is a sophisticated build tool that when used, results in faster run times and less error prone compilation of code. This is important for this project specifically as otherwise Visual Studio code may have issues in locating the language bundle files required for the multilingual feature. 
* Have fun utilizing this image editor.

## About the project:

'ANDIE' stands for A Non-Destructive Image Editor. The purpose of the program is editing and manipulating images - like Photoshop but much simpler. The approach taken in ANDIE is non-destructive image editing. Many image processing operations, such as blurring filters, cannot be reversed because information is lost in the process. Non-destructive editors take the approach of storing the original data (an image in this case) and a sequence of operations. The operations can be applied to a copy of the original to get the desired result, but since the original and the full sequence of operations has been kept, no information is lost. This approach also lets us implement undo operations easily.

This was built with java, within the VS code integrated development environment.

## **Features:**
* Sharpen filter
* Gaussian blur filter
* Median filter
* Image inversion
* Colour channel cycling
* Multilingual support
* Image resize
* Image rotations
* Export image
* Image flipping
* Extended filters
* Filters with negative results
* Emboss and edge detection filters
* Brightness adjustment
* Contrast adjustment
* Block averaging
* Random scattering
* Toolbar for common operations
* Keyboard shortcuts
* Mouse selection of rectangular regions
* Crop to selection
* Drawing functions – rectangle, ellipse, line
* Macros for record and replay of operations


## **Folder Structure**

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `gradle`: the folder to maintain build dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## **Dependency Management**

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## Who has worked on each feature

### Finn
* Sharpen Filter

* Exception Handling

* Image Export

* Emboss filters

* Macros for record and replay operations

* Toolbar for common operations

* Error Avoidance/prevention

* General Support on other features

### Ilgaz
* Gaussian Blur Filter

* Image Resize

* Brightness and contrast

* Drawing functions

* Error Avoidance/prevention

* General Support on other features

### Josh
* Median Filter

* Colour channel cycling

* Filters with negative results

* Crop to selection

* Error Avoidance/prevention

* General Support on other features

### Aiden
* Image Inversion

* Multi-Lingual Support, including keeping it up to date with new feature additions

* Random scattering

* Block averaging

* Error Avoidance/prevention

* General Support on other features

### Matt
* Image Rotations

* Image flip

* Median Filter

* Extended filters

* Mouse selection of rectangular regions

* Crop to selection

* Error Avoidance/prevention

* General Support on other features

## How our code was tested
* Unit testing via the J-Unit5 framework for java
* Print line Testing
* Continuous integration 

## Known issues and future improvements
### Unsaved Changes
- Closing the window always prompts the user to save their changes if they haven't done so, rather than checking if there are unsaved changes to save.
### Colour Cycling
- Colour channels can't yet be specified by the user. Currently they are shifted one place to the right when the cycle colour channel function is triggered.

## Documentation
https://cosc202-jit.cspages.otago.ac.nz/andie/docs/cosc202/andie/package-summary.html