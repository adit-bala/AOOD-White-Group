# To-Do List Application
A school project for the course Advanced Object-Oriented Design G/T. As Team Leader, I led the development of a To-Do List Application along with 7 other group members. 

## How to Install
- Download the Jar File and run it

## Repository Organization
```
AOOD-White-Group
|   ToDoList.jar - Executable Jar
|   ActionItemEntry.java - Represents an item in the action item list
|   ClosedActionItems.java - Allows users to view already completed ActionItems
|   CommentScreen.java - Allows the user to enter a comment
|   ConfirmationPopup.java - Shows confirmation boxes for the user for various tasks
|   EditActionItemScreen.java - Displays ways for users to manipulate ActionItem data.
|   HistoryScreen.java - Shows all events in an Action Item’s history
|   MainScreen.java - Displays screen with the list
|   MenuBar.java - Displays different menu options
|   PrintActionItemScreen.java - Displays preview of printing one ActionItem
|   PrintScreen.java - Allows user to print out an action item
|   Window.java - Displays current screen
│   backend
    └───ActionItem.java - Represents an Action Item or Task
    └───CommentChangeEvent.java - Represents a change of the action item’s comment
    └───FileUtilities.Java - Handles all modification of files
    └───FontLoader.java - Used to load in fonts
    └───HistoryEvent.java - Represents the history of an event
    └───Priority.java - Represents different Priorties for an item
    └───PriorityChangeEvent.java - Represents a change of priority of the action item
    └───TitleChangeEvent.java - Represents a change of the action item’s title
    └───ToDoList.java - Represents a user’s to do list
│   res
    └───Chivo - Fonts
    └───EBGaramond - Fonts
```
# Previous Instructions for Team Members
### Create a new Java Project in Eclipse
- File > New > Java Project
- Name it ToDoList
- Click Finish
### Clone the forked repository into your project
- Go back to your forked repository in Github and click Code
- Click Open with GitHub Desktop
- On the local path field click Choose…
- Navigate to where the Java Project you just made is stored (if you don’t know, in Eclipse do File > Switch Workspace > Other… and look at the workspace field)
- Click Select Folder
- Click clone
### Reformat your Java Project
- Go back to Eclipse
- Right click on your project and click refresh
- Delete the src folder
- Right click AOOD-White-Group, click refactor, and rename to src
- Right click src > Build Path > Use as Source Folder
### Fix Github Desktop stuff
- Go back to Github Desktop which probably says “Can’t find AOOD White Group”
- Click locate and click the new src folder


## How to submit Code Changes

### Update your project with everyone’s changes
In Github Desktop:
- Click Current Branch main at the top
- Click THE BUTTON AT THE BOTTOM **choose a branch to merge into main**
- Choose upstream/main (upstream = Aditya’s repository with all the updates)
- Click merge upstream/main into main
- Click the blue push origin button to update your forked repository
Now make sure to right click your project in Eclipse and choose Refresh to get the changes

### Push to Origin (aka update your repository with your changes)
- Open Github Desktop
- Your modifications should be reflected on the screen
- In the top comment box add what the code is doing (ex. Changed Font of ActionItem) and add any other concerns in the description
- Click on Commit to main and then Push to Origin
- Your code should now be reflected in **your** forked Github repo, but not the main repo

### Pull Request to Main Repo (aka update Aditya's repository with your changes
- In your repo, there should be a gray box talking about commits being ahead or behind of the main branch. In that box, click make a pull request. 
- Add a comment about what you are trying to add and then submit the pull request
