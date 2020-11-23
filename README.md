# AOOD-White-Group
ToDo List Proposal

## How to use Github Desktop with Eclipse
### Getting Started
- Install Github Desktop at https://desktop.github.com/ 
- Fork the repository in your browser
- Go to https://github.com/adit-bala/AOOD-White-Group
- Click Fork in the top right
- Click your account
- Leave the tab open
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
- Click choose a branch to merge into main
- Choose upstream/main (upstream = Aditya’s repository with all the updates)
- Click merge upstream/main into main
- Click the blue push origin button to update your forked repository
- Click Fetch Origin
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
