#  **ANDIE**

## **About the project:**

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

## **Folder Structure**

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `gradle`: the folder to maintain build dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## **Dependency Management**

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
